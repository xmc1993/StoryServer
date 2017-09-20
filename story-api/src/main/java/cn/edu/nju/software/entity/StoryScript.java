package cn.edu.nju.software.entity;

import java.util.Date;

public class StoryScript {
    private Integer id;

    private Integer storyid;

    private Date createtime;

    private Date updatetime;

    private Integer valid;

    private Integer roleid;

    private String obligate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getObligate() {
        return obligate;
    }

    public void setObligate(String obligate) {
        this.obligate = obligate == null ? null : obligate.trim();
    }
}