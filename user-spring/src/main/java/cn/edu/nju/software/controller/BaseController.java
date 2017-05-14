package cn.edu.nju.software.controller;

import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.exception.LoginException;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.ObjectAndByte;
import cn.edu.nju.software.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);


	public Business islogin(Jedis jedis,HttpServletRequest request){
		logger.info("islogin{}",request.getSession().getId());
		Cookie[] c = request.getCookies();
		if(c!=null){
			for (Cookie cookie : c) {
				logger.info("islogin cookie{}",cookie.getValue());
				if(cookie.getName().equals("JSESSIONID")){
					byte[] obj = jedis.get(cookie.getValue().getBytes());
					if(obj==null){
						continue;
					}else{
						return (Business) SerializeUtil.unserialize(obj);
					}
				}
			}
		}
		return null;
	}
	
	public String isloginSession(Jedis jedis,HttpServletRequest request,HttpServletResponse response){
		logger.info("isloginSession{}",request.getSession().getId());
		String sessionId = "";
		Cookie[] c = request.getCookies();
		if(c!=null){
			for (Cookie cookie : c) {
				logger.info("isloginSession cookie{}",cookie.getValue());
				if(cookie.getName().equals("JSESSIONID")){
					byte[] obj = jedis.get(cookie.getValue().getBytes());
					if(obj==null){
						continue;
					}else{
						sessionId = cookie.getValue();
					}
				}
			}
			for (Cookie cookie : c) {
				cookie.setMaxAge(0);
				cookie.setValue(null);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			Cookie cookie = new Cookie("JSESSIONID", sessionId);
			cookie.setPath("/");
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
		}
		return null;
	}

	
	protected User getLongUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String AccessToken = request.getHeader("Access-Token");
		try {
			byte[] bytes = JedisUtil.getJedis().get(AccessToken.getBytes());
			if(bytes==null){
				response.setStatus(401);
				throw new LoginException("登录失效");
			}else{
				JedisUtil.getJedis().set(AccessToken.getBytes(), bytes);
				JedisUtil.getJedis().expire(AccessToken.getBytes(), 60*60*24*30);//缓存用户信息30天
			}
			User user = (User) ObjectAndByte.toObject(bytes);
			if(user==null){
				response.setStatus(401);
				throw new LoginException("登录失效");
			}
			return user;
		} catch (Exception e) {
			response.setStatus(401);
			throw new LoginException("登录失效");
		}
	}
	

	
	protected String getLongUserMobile(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String AccessToken = request.getHeader("Access-Token");
		try {
			byte[] bytes = JedisUtil.getJedis().get(AccessToken.getBytes());
			if(bytes==null){
				response.setStatus(401);
				throw new LoginException("登录失效");
			}else{
				JedisUtil.getJedis().set(AccessToken.getBytes(), bytes);
				JedisUtil.getJedis().expire(AccessToken.getBytes(), 60*60*24*30);//缓存用户信息30天
			}
			User user = (User) ObjectAndByte.toObject(bytes);
			if(user==null){
				response.setStatus(401);
				throw new LoginException("登录失效");
			}
			return user.getMobile();
		} catch (Exception e) {
			response.setStatus(401);
			throw new LoginException("登录失效");
		}
	}
}
