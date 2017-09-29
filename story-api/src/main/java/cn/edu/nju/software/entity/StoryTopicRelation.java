package cn.edu.nju.software.entity;

import java.util.Date;

public class StoryTopicRelation {
    private Integer id;

    private Integer storytopicId;

    private Integer storyId;

    private Integer valid;

    private Date createtime;

    private Date updatetime;

    private Integer myorder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getMyorder() {
        return myorder;
    }

    public void setMyorder(Integer myorder) {
        this.myorder = myorder;
    }

	public Integer getstorytopicId() {
		return storytopicId;
	}

	public void setstorytopicId(Integer storyTopicId) {
		this.storytopicId = storyTopicId;
	}

	public Integer getstoryId() {
		return storyId;
	}

	public void setstoryId(Integer storyId) {
		this.storyId = storyId;
	}
}