package cn.edu.nju.software.entity;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@Api(value="返回数据类")
public class ResponseData<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8960296554212928922L;
	@ApiModelProperty(value="返回状态")
	private int status;
	@ApiModelProperty(value="返回类型")
	private String type;
	@ApiModelProperty(value="返回数据")
	private T obj;
	@ApiModelProperty(value="错误返回说明")
	private String errorMes;
	@ApiModelProperty(value="分页查询条目总数")
	private Integer count;//分页查询的条目总数

	public ResponseData() {
		type = "json";
		errorMes = "";
		status = 0;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public String getErrorMes() {
		return errorMes;
	}

	public void setErrorMes(String errorMes) {
		this.errorMes = errorMes;
	}

	public void jsonFill(int status, String errorMsg, T obj) {
		this.status = status;
		this.errorMes = errorMsg;
		this.obj = obj;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
