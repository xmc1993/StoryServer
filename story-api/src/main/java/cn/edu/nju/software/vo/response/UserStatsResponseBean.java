package cn.edu.nju.software.vo.response;

import cn.edu.nju.software.entity.UserBrowse;

import java.io.Serializable;

public class UserStatsResponseBean extends UserBrowse implements Serializable {
	
	private static final long serialVersionUID = 9213498153006204155L;
	private Integer id;
	private String mobile;
	private Integer validation = 0;
	private String nickname;
	private String file;
	private String company;
	private String location;
	private Integer invitationCount = 0;
	private String sex;
	private String weixin;
	private Integer collectNum = 0;
	private Integer activityNum = 0;
	private String verifyCode;
	private Integer businessId;
	private Integer isVip = 0;
	private Integer isAdmin = 0;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getValidation() {
		return validation;
	}
	public void setValidation(Integer validation) {
		this.validation = validation;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getInvitationCount() {
		return invitationCount;
	}
	public void setInvitationCount(Integer invitationCount) {
		this.invitationCount = invitationCount;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public Integer getCollectNum() {
		return collectNum;
	}
	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}
	public Integer getActivityNum() {
		return activityNum;
	}
	public void setActivityNum(Integer activityNum) {
		this.activityNum = activityNum;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
}
