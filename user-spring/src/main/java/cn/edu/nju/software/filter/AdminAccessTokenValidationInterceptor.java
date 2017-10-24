package cn.edu.nju.software.filter;

import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.exception.LoginException;
import cn.edu.nju.software.service.AdminPowerService;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.ObjectAndByte;
import cn.edu.nju.software.util.TokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 访问token校验拦截器。在校验成功后，会设置相应的请求属性。
 * <p>
 * <p>
 * 待校验的token及其设置的属性如下所示：
 * <ul>
 * <li>access token: jwt字符串，存储于HTTP请求头中，
 * 请求头的名字由应用参数"token.access-token.holder-name"指定，默认值为"accessToken"。
 * 验证成功后，会更新access token的最后访问时间， 并将用户标识写入到请求属性中，
 * 属性名为由应用参数"token.access-token.user-id-request-attribute-name"指定。</li>
 * </ul>
 *
 */
public class AdminAccessTokenValidationInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(AdminAccessTokenValidationInterceptor.class);
    @Autowired
    private AdminPowerService adminPowerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("************:" + request.getRequestURI());
        this.checkLogin(request, response, handler);
        return super.preHandle(request, response, handler);
    }

    private boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String AccessToken = request.getHeader("Authorization");
        Jedis jedis = JedisUtil.getJedis();
        try {
            byte[] bytes = jedis.get(AccessToken.getBytes());
            if (bytes == null) {
                response.setStatus(401);
                throw new LoginException("session invalid");
            } else {
                Admin admin = (Admin) ObjectAndByte.toObject(bytes);
                if (admin == null) {
                    response.setStatus(401);
                    throw new LoginException("session invalid");
                } else {
                    request.setAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME, admin);
                    //设置权限码
                    setPowerCodes(request, admin);
                    //刷新token的时间
                    jedis.expire(AccessToken.getBytes(), 60 * 60 * 6);//缓存用户信息30天
                }
            }

        } catch (Exception e) {
            response.setStatus(401);
            throw new LoginException("login fail");
        }finally {
            jedis.close();
        }

        return true;
    }

    /**
     * 在request中设置权限码
     * @param request
     * @param admin
     */
    private void setPowerCodes(HttpServletRequest request, Admin admin){
        String key = "PowerCodes-" + admin.getId();
        Jedis jedis = JedisUtil.getJedis();
        byte[] bytes = jedis.get(key.getBytes());
        List<Integer> powerCodeList;
        if (bytes == null) {
            powerCodeList = adminPowerService.getAdminPowerCodeListByAdminId(admin.getId());
            jedis.set(key.getBytes(), ObjectAndByte.toByteArray(powerCodeList));
        }else {
            powerCodeList = (List<Integer>) ObjectAndByte.toObject(bytes);
        }
        request.setAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_POWERCODES, powerCodeList);
        jedis.close();
    }
}
