package cn.edu.nju.software.vo;

public class CommentVo {
	private String type;
	private Integer commentId;
	private String userImgUrl;
	private String userNickname;
	private String commentContent;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentId == null) ? 0 : commentId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CommentVo other = (CommentVo) obj;
		if (commentId == null) {
			if (other.commentId != null) return false;
		}
		else if (!commentId.equals(other.commentId)) return false;
		if (type == null) {
			if (other.type != null) return false;
		}
		else if (!type.equals(other.type)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentVo [type=" + type + ", commentId=" + commentId + ", userImgUrl=" + userImgUrl
				+ ", userNickname=" + userNickname + ", commentContent=" + commentContent + "]";
	}

}
