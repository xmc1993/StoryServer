package cn.edu.nju.software.vo.response;

import cn.edu.nju.software.entity.UserBrowse;

import java.io.Serializable;

public class UserBrowseResponseBean extends UserBrowse implements Serializable {
	
	private static final long serialVersionUID = 6507072642041466334L;
	private Object data;
	private UserStatsResponseBean user;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public UserStatsResponseBean getUser() {
		return user;
	}
	public void setUser(UserStatsResponseBean user) {
		this.user = user;
	}

}
