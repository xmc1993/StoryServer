package cn.edu.nju.software.entity;

import java.util.Date;

public class StoryTopicRelation {
    private Integer id;

    private Integer storytopicId;

    private Integer storyId;

    private Integer valid;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getstorytopicId() {
        return storytopicId;
    }

    public void setstorytopicId(Integer storytopicId) {
        this.storytopicId = storytopicId;
    }

    public Integer getstoryId() {
        return storyId;
    }

    public void setstoryId(Integer storyId) {
        this.storyId = storyId;
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
}