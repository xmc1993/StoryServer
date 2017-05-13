package cn.edu.nju.software.action.request.user;

import java.io.Serializable;

public class LiveRequestBean implements Serializable {
	
	private static final long serialVersionUID = -116850202108324567L;
	
	private	String message;
	private String updatedAt;
	private LiveRequestDataBean data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public LiveRequestDataBean getData() {
		return data;
	}
	public void setData(LiveRequestDataBean data) {
		this.data = data;
	}
}


