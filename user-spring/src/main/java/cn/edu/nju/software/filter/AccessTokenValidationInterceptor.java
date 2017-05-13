package cn.edu.nju.software.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.software.entity.Business;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.exception.LoginException;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.service.user.UserBusinessService;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.ObjectAndByte;
import cn.edu.nju.software.util.TokenConfig;

/**
 * <p>
 * 访问token校验拦截器。在校验成功后，会设置相应的请求属性。
 * 
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
 * @since
 * @see
 */
public class AccessTokenValidationInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(AccessTokenValidationInterceptor.class);

	@Autowired
	private AppUserService userService;
	@Autowired
	private UserBusinessService businessService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("************:" + request.getRequestURI());
		this.checkLogin(request, response, handler);
		return super.preHandle(request, response, handler);
	}
	
	private boolean checkLogin(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception{

		String appId = request.getParameter("appId");
		String mobile = request.getParameter("mobile");
		String accessToken = request.getParameter("accessToken");
		
		if(appId!=null&&mobile!=null&&accessToken!=null&&appId!=""&&mobile!=""&&accessToken!=""&&
				!"".equals(appId)&&!"".equals(mobile)&&!"".equals(accessToken)){

			Business business = businessService.getBusinessByAppId(appId);
			if (business == null) {
				response.setStatus(401);
				throw new LoginException("不存在该客户");
			}

			User user = userService.getUserByMobileOrId(mobile);
			if (user == null) {
				response.setStatus(401);
				throw new LoginException("不存在该用户");
			}
			String tokenDB = user.getAccessToken();
			String tokenParam = accessToken;
			if (tokenDB == null || !tokenDB.equals(tokenParam)) {
				response.setStatus(401);
				throw new LoginException("登录失效");
			}
			request.setAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME, user);
		}else{
			String AccessToken = request.getHeader("Sin-Access-Token");
			try {
				byte[] bytes = JedisUtil.getJedis().get(AccessToken.getBytes());
				if(bytes==null){
					response.setStatus(401);
					throw new LoginException("登录失效");
				}else{
					User user = (User) ObjectAndByte.toObject(bytes);
					if(user==null){
						response.setStatus(401);
						throw new LoginException("登录失效");
					}else{
						request.setAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME, user);
						JedisUtil.getJedis().set(AccessToken.getBytes(), bytes);
						JedisUtil.getJedis().expire(AccessToken.getBytes(), 60*60*24*30);//缓存用户信息30天
					}
				}
				
			} catch (Exception e) {
				response.setStatus(401);
				throw new LoginException("登录失效");
			}
		}

		return true;
	}
}
