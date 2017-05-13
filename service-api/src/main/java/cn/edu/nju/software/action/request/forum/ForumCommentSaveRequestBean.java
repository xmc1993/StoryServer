package cn.edu.nju.software.action.request.forum;

import java.io.Serializable;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Api("")
public class ForumCommentSaveRequestBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8987069397687077874L;
	@ApiModelProperty(value="内容")
	private String content;
	@ApiModelProperty(value="被评论人id")
	private Long userId;
	@ApiModelProperty(value="帖子id")
	private Long forumId;
	@ApiModelProperty(value="评论人id，自动获取，不用传值")
	private Long answerUserId;
	private Long businessId;
	private String appId;
	@ApiModelProperty(value="评论父级")
	private String parent;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getForumId() {
		return forumId;
	}
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	public Long getAnswerUserId() {
		return answerUserId;
	}
	public void setAnswerUserId(Long answerUserId) {
		this.answerUserId = answerUserId;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
}
