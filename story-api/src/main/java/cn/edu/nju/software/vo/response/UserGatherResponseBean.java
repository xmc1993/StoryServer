package cn.edu.nju.software.vo.response;

import cn.edu.nju.software.entity.User;

import java.io.Serializable;

public class UserGatherResponseBean extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3520856411650865022L;

	private Long time;
	private Integer allCount;
	private Integer userCount;
	
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getAllCount() {
		return allCount;
	}
	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
}
