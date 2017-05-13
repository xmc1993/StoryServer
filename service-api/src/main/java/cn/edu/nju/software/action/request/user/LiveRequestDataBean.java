package cn.edu.nju.software.action.request.user;

import java.io.Serializable;

public class LiveRequestDataBean implements Serializable {
	
	private static final long serialVersionUID = -5371297289130552856L;
	
	private String id;
	private String url;
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
