package cn.edu.nju.software.action.request.forum;

import java.io.Serializable;
import java.util.List;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Api("")
public class ForumSaveRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -499414748031742872L;
	private Long id;
	@ApiModelProperty(value="内容")
	private String content;
	@ApiModelProperty(value="类型")
	private Integer type;
	@ApiModelProperty(value="图片类型中图片,多个用英文逗号分开")
	private String imgs;
	private Long businessId;
	private Long userId;
	
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
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
