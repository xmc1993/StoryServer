package cn.edu.nju.software.action.request.user;

import java.io.Serializable;
import java.util.List;

public class PipelineRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5472570653993671074L;
	private String id;
	private String pipeline;
	private Integer code;
	private String desc;
	private String reqid;
	private String inputBucket;
	private String inputKey;
	private List<Items> items;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPipeline() {
		return pipeline;
	}
	public void setPipeline(String pipeline) {
		this.pipeline = pipeline;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getReqid() {
		return reqid;
	}
	public void setReqid(String reqid) {
		this.reqid = reqid;
	}
	public String getInputBucket() {
		return inputBucket;
	}
	public void setInputBucket(String inputBucket) {
		this.inputBucket = inputBucket;
	}
	public String getInputKey() {
		return inputKey;
	}
	public void setInputKey(String inputKey) {
		this.inputKey = inputKey;
	}
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}

}

