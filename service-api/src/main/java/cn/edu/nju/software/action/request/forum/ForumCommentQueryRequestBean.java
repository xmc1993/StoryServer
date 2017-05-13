package cn.edu.nju.software.action.request.forum;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

import cn.edu.nju.software.action.request.PageRequestBean;

@Api("")
public class ForumCommentQueryRequestBean extends PageRequestBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2456185863725004301L;
	
	@ApiModelProperty(value="论坛id")
	private Long forumId;
	private String appId;
	private Long businessId;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

}
