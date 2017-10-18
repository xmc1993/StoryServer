package cn.edu.nju.software.filter;

import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.exception.LoginException;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.ObjectAndByte;
import cn.edu.nju.software.util.TokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
 * @author caoxudong
 * @see
 */
public class AccessTokenValidationInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenValidationInterceptor.class);
    //白名单 TODO 改为配置 通用
    private static List<String> whileList = new ArrayList();
    static {
        whileList.add("/user/getStoryListByPage");
        whileList.add("/user/getStoryById");
        whileList.add("/user/storiesByFuzzyQuery");
        whileList.add("/user/getStoryListByTitle");
        whileList.add("/user/getStoryIdListBySecondLevelTagId");
        whileList.add("/user/getStoryIdListByFirstLevelTagId");
        whileList.add("/user/draftStories");
        whileList.add("/user/getBadgeListByTypeId");
        whileList.add("/user/getMostPopularStoryByPage");
        whileList.add("/user/getMostPopularByPage");
        whileList.add("/user/getPopularOriginalStoryListByPage");
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("************:" + request.getRequestURI());
        //白名单的uri可以过滤
        if(whileList.contains(request.getRequestURI())){
            String AccessToken = request.getHeader(TokenConfig.DEFAULT_ACCESS_TOKEN_HEADER_NAME);
            if (AccessToken != null) {
                this.checkLogin(request, response, handler);
            }
        }else {
            this.checkLogin(request, response, handler);
        }
        return super.preHandle(request, response, handler);
    }

    private boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String AccessToken = request.getHeader(TokenConfig.DEFAULT_ACCESS_TOKEN_HEADER_NAME);
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            byte[] bytes = jedis.get(AccessToken.getBytes());
            if (bytes == null) {
                response.setStatus(401);
                throw new LoginException("登录失效");
            } else {
                User user = (User) ObjectAndByte.toObject(bytes);
                user.setAccessToken(AccessToken);
                if (user == null) {
                    response.setStatus(401);
                    throw new LoginException("登录失效");
                } else {
                    request.setAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME, user);
                    //刷新token的时间
//                    jedis.set(AccessToken.getBytes(), bytes);
//                    jedis.expire(AccessToken.getBytes(), 60 * 60 * 24 * 30);//缓存用户信息30天
                }
            }

        } catch (Exception e) {
            response.setStatus(401);
            throw new LoginException("登录失效");
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return true;
    }
}
