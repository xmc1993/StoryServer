package cn.edu.nju.software.action.request.user;

import java.io.Serializable;

public class Items implements Serializable{
	private static final long serialVersionUID = 6803346922552880555L;
	private String cmd;
	private Integer code;
	private String desc;
	private String hash;
	private String key;
	private Integer returnOld;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
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
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getReturnOld() {
		return returnOld;
	}
	public void setReturnOld(Integer returnOld) {
		this.returnOld = returnOld;
	}
}