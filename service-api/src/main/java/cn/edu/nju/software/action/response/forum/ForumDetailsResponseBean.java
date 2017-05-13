package cn.edu.nju.software.action.response.forum;

import java.io.Serializable;
import java.util.List;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Api("")
public class ForumDetailsResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9077607745369840008L;
	
	private Long id;
	@ApiModelProperty(value="内容")
	private String content;
	@ApiModelProperty(value="类型")
	private Integer type;
	@ApiModelProperty(value="图片类型中图片")
	private List<String> imgs;
	private Long businessId;
	@ApiModelProperty(value="时间")
	private String date;

	@ApiModelProperty(value="用户")
	private String userName;
	@ApiModelProperty(value="用户头像")
	private String userImage;
	@ApiModelProperty(value="用户id")
	private Long userId;

	@ApiModelProperty(value="评论")
	private List<ForumCommentListResponseBean> comment;
	@ApiModelProperty(value="点赞")
	private List<String> praise;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<String> getImgs() {
		return imgs;
	}
	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public List<ForumCommentListResponseBean> getComment() {
		return comment;
	}
	public void setComment(List<ForumCommentListResponseBean> comment) {
		this.comment = comment;
	}
	public List<String> getPraise() {
		return praise;
	}
	public void setPraise(List<String> praise) {
		this.praise = praise;
	}

}
