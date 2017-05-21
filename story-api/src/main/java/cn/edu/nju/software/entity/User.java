package cn.edu.nju.software.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * mobile作为唯一的业务主键
 */
public class User implements Serializable {
	private static final long serialVersionUID = -2837545145328393714L;
	private Integer id;
	private String nickname;//用户名
	private String accessToken;//story token
	private String sex;//性别
	private String headImgUrl;//头像
	private String wxUnionId;//微信的唯一id
	private Date createTime;
	private Date updateTime;

	//预留字段
	private String mobile;//手机号
	private String verifyCode;//验证码
	private Date expireTime;//验证码的过期事件
	private String password;//密码
	private String company;//公司
	private String city;//城市
	private String email;//邮箱
	private String weChatOpenId;//Add on 2017/4/11 微信的openId
	private String weChatAccessToken;//微信的accessToken
	private String weChatRefreshToken;//微信的refreshToken
	private String deviceId;//用于游客登录

	//后续添加字段
	private Integer followerCount = 0;//粉丝数
	private Integer followeeCount = 0;//关注的人数
	private Integer workCount = 0;//作品数
	private Integer likeCount = 0;//喜欢的作品数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getWxUnionId() {
		return wxUnionId;
	}

	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeChatOpenId() {
		return weChatOpenId;
	}

	public void setWeChatOpenId(String weChatOpenId) {
		this.weChatOpenId = weChatOpenId;
	}

	public String getWeChatAccessToken() {
		return weChatAccessToken;
	}

	public void setWeChatAccessToken(String weChatAccessToken) {
		this.weChatAccessToken = weChatAccessToken;
	}

	public String getWeChatRefreshToken() {
		return weChatRefreshToken;
	}

	public void setWeChatRefreshToken(String weChatRefreshToken) {
		this.weChatRefreshToken = weChatRefreshToken;
	}

	public Integer getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	public Integer getFolloweeCount() {
		return followeeCount;
	}

	public void setFolloweeCount(Integer followeeCount) {
		this.followeeCount = followeeCount;
	}

	public Integer getWorkCount() {
		return workCount;
	}

	public void setWorkCount(Integer workCount) {
		this.workCount = workCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
