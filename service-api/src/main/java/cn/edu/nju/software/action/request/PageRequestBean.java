package cn.edu.nju.software.action.request;

import java.io.Serializable;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModelProperty;

import cn.edu.nju.software.util.Pagination;

@Api(value = "")
public class PageRequestBean implements Serializable  {
	
	private static final long serialVersionUID = -5460916727522078000L;

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

	/**
	 * 当前页，默认0
	 */
	@ApiModelProperty(value="当前页，默认1")
	private Integer page = DEFAULT_PAGE;

	/**
	 * 每页多少条，默认10条
	 */
	@ApiModelProperty(value="每页多少条，默认10条")
	private Integer pageSize = DEFAULT_SIZE;
	
	private String qorder;
	private String qdesc;
	private Integer currentSize;

	public Integer getPage() {
		if(page==null){
			return DEFAULT_PAGE;
		}else if(page.equals(0) || page==0){
			return DEFAULT_PAGE;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		if(pageSize==null){
			return DEFAULT_SIZE;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getQorder() {
		return qorder;
	}

	public void setQorder(String qorder) {
		this.qorder = qorder;
	}

	public String getQdesc() {
		return qdesc;
	}

	public void setQdesc(String qdesc) {
		this.qdesc = qdesc;
	}

	public Integer getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(Integer currentSize) {
		this.currentSize = currentSize;
	}
	

	public static Pagination getPagination(PageRequestBean bean,Integer count){
		Pagination page = new Pagination();
		page.setCurrPage(bean.getPage());
		page.setPageSize(bean.getPageSize());
		page.setTotalCount(count.longValue());
		page.setTotalPage((int) Math.ceil((count.doubleValue())/bean.getPageSize()));
		page.setNext(page.getCurrPage()>=page.getTotalPage()?false:true);
		return page;
	}

	@Override
	public String toString() {
		return "" + page +  + pageSize +  qorder  + qdesc
				 + currentSize ;
	}
	
}
