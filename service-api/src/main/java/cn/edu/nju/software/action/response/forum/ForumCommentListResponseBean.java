package cn.edu.nju.software.action.response.forum;

import java.io.Serializable;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Api("")
public class ForumCommentListResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9077607745369840008L;

	@ApiModelProperty(value="评论内容")
	private String content;
	@ApiModelProperty(value="帖子id")
	private Long forumId;
	@ApiModelProperty(value="被评论人id")
	private Long userId;
	@ApiModelProperty(value="评论人id")
	private Long answerUserId;
	@ApiModelProperty(value="被评论人名称")
	private String userName;
	@ApiModelProperty(value="评论人名称")
	private String answerUserName;
	private String parent;
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getForumId() {
		return forumId;
	}
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAnswerUserId() {
		return answerUserId;
	}
	public void setAnswerUserId(Long answerUserId) {
		this.answerUserId = answerUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAnswerUserName() {
		return answerUserName;
	}
	public void setAnswerUserName(String answerUserName) {
		this.answerUserName = answerUserName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}

}
