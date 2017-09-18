package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Baby;

/**
* @author zs
* @version 创建时间：2017年9月14日 下午3:46:18
*/
public class UserBaseFollowBabyVo extends UserBaseFollowVo {
	private Baby baby;

	public Baby getBaby() {
		return baby;
	}

	public void setBaby(Baby baby) {
		this.baby = baby;
	}
	
}
