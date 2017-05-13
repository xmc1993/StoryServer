package cn.edu.nju.software.vo;

import java.util.ArrayList;
import java.util.List;

public class PageComment {
	private int pageCount = 0;
	private int currentPage = 0;
	private List<CommentVo> pageData = new ArrayList<>();

	public PageComment(int pageCount, int currentPage, List<CommentVo> pageData) {
		this.pageCount = pageCount;
		this.currentPage = currentPage;
		this.pageData = pageData;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<CommentVo> getPageData() {
		return pageData;
	}

}
