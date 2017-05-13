package cn.edu.nju.software.action.request.forum;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

import cn.edu.nju.software.action.request.PageRequestBean;

@Api("")
public class ForumQueryRequestBean extends PageRequestBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2456185863725004301L;
	
	@ApiModelProperty(value="排序类型[10000-活跃度在前，时间在后，降序,order_time_desc-10001,order_time_asc-10002，order_hot_desc-10003,order_hot_asc-10004]")
	private Integer orderType;

	private String appId;
	private Long businessId;
	private Long userId;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return ""+ orderType +  appId +  businessId+super.toString();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
