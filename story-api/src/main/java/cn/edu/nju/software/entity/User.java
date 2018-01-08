package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mobile作为唯一的业务主键
 */
@Data
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
	private Integer listenCount = 0;//收听数
	private Integer listenedCount = 0;//被收听数
	private Integer likeStoryCount = 0;//喜欢的故事数
	private Integer valid = 1;//用于软删除
	//总的录用时间
	private String totalRecordTime = "0'0\"";
	//消息推送的deviceToken
	private String deviceToken;
}
