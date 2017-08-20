package cn.edu.nju.software.util;

import javax.servlet.http.HttpServletRequest;

import cn.edu.nju.software.entity.User;

public class UserChecker {
	/**
	 * 检查请求属性中是否包含User对象
	 * @param request
	 */
	public static User checkUser(HttpServletRequest request){
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            user = new User();
            user.setId(-1);
        }
        return user;
	}
}
