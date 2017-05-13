package cn.edu.nju.software.vo.response;

import java.io.Serializable;

public class ArticleStatsResponseBean extends UserBrowseResponseBean implements Serializable{

	private static final long serialVersionUID = 8666927415708622992L;
	private Integer id;
	private String title;
	private String author;
	private String introduction;
	private String cover;
	private String content;
	private Integer allowComment = 0;
	private Integer isOnlyVip;// 外键
	private Integer categoryId = 0;
	private Integer commentNum = 0;
	private Integer viewNum = 0;
	private Integer collectNum = 0;
	private Integer businessId;// 外键

	//Add on 2016/07/21
	private String cover_2;
	private String cover_3;
	private String state = "";
	private int listType = 2;//类型 只有listType 3 返回三张图片

	//Add on 2016/10/09
	private Integer advOptions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}

	public Integer getIsOnlyVip() {
		return isOnlyVip;
	}

	public void setIsOnlyVip(Integer isOnlyVip) {
		this.isOnlyVip = isOnlyVip;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getCover_2() {
		return cover_2;
	}

	public void setCover_2(String cover_2) {
		this.cover_2 = cover_2;
	}

	public String getCover_3() {
		return cover_3;
	}

	public void setCover_3(String cover_3) {
		this.cover_3 = cover_3;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public Integer getAdvOptions() {
		return advOptions;
	}

	public void setAdvOptions(Integer advOptions) {
		this.advOptions = advOptions;
	}

}
