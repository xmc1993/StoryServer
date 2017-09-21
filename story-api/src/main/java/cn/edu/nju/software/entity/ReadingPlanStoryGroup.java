package cn.edu.nju.software.entity;

import java.util.Date;

public class ReadingPlanStoryGroup {
    private Integer id;

    private Integer readingplanid;

    private Integer storyid;

    private Date createtime;

    private Date updatetime;

    private Integer myorder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReadingplanid() {
        return readingplanid;
    }

    public void setReadingplanid(Integer readingplanid) {
        this.readingplanid = readingplanid;
    }

    public Integer getStoryid() {
        return storyid;
    }

    public void setStoryid(Integer storyid) {
        this.storyid = storyid;
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
}