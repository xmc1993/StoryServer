package cn.edu.nju.software.filter;

import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.exception.LoginException;
import cn.edu.nju.software.service.AdminPowerService;
import cn.edu.nju.software.util.TokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
 */
public class AdminAccessTokenValidationInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(AdminAccessTokenValidationInterceptor.class);
    @Autowired
    private AdminPowerService adminPowerService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
        Object obj = redisTemplate.opsForValue().get(AccessToken);
        if (null == obj) {
            response.setStatus(401);
            throw new LoginException("session invalid");
        }else {
            request.setAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME, obj);
            setPowerCodes(request, (Admin)obj);
            redisTemplate.expire(AccessToken, 60 * 60 * 6, TimeUnit.SECONDS);//刷线session时间：6小时
        }

        return true;
    }

    /**
     * 在request中设置权限码
     *
     * @param request
     * @param admin
     */
    private void setPowerCodes(HttpServletRequest request, Admin admin) {
        List<Integer> powerCodeList = adminPowerService.getAdminPowerCodeListByAdminId(admin.getId());
        request.setAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_POWERCODES, powerCodeList);
    }
}
