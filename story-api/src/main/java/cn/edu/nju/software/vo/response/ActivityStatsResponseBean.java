package cn.edu.nju.software.vo.response;

import cn.edu.nju.software.entity.UserBrowse;

import java.io.Serializable;

public class ActivityStatsResponseBean extends UserBrowse implements Serializable{

	private static final long serialVersionUID = 4121331493049836462L;
	private Integer id;
	private String title;
	private String author;
	private String introduction;
	private String cover;
	private String detailImgUrl;
	private String content;
	private String price = "0";
	private Integer isVipFree = 0;
	private Integer isOnlyVip = 0;
	private String scale = "";// 规模
	private String time;// 时间
	private String location;// 地点
	private String phone;// 联系电话
	private Integer categoryId;// 分类编号
	private Integer businessId;// 客户编号

	//Add on 2016/07/21
	private String cover_2;
	private String cover_3;
	private String state = "";
	private int listType = 2;//类型 只有listType 3 返回三张图片

	//Add on 2016/07/27
	private Integer allowComment = 0;
	private Integer commentNum = 0;
	private Integer collectNum = 0;

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

	public String getDetailImgUrl() {
		return detailImgUrl;
	}

	public void setDetailImgUrl(String detailImgUrl) {
		this.detailImgUrl = detailImgUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getIsVipFree() {
		return isVipFree;
	}

	public void setIsVipFree(Integer isVipFree) {
		this.isVipFree = isVipFree;
	}

	public Integer getIsOnlyVip() {
		return isOnlyVip;
	}

	public void setIsOnlyVip(Integer isOnlyVip) {
		this.isOnlyVip = isOnlyVip;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public Integer getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public Integer getAdvOptions() {
		return advOptions;
	}

	public void setAdvOptions(Integer advOptions) {
		this.advOptions = advOptions;
	}
}
