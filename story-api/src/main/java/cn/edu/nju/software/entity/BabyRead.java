package cn.edu.nju.software.entity;

import java.util.Date;

public class BabyRead {
    private Integer id;

    private Integer babyreadinfoid;

    private Integer babyid;

    private String url;

    private Integer valid;

    private Date creattime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBabyreadinfoid() {
        return babyreadinfoid;
    }

    public void setBabyreadinfoid(Integer babyreadinfoid) {
        this.babyreadinfoid = babyreadinfoid;
    }

    public Integer getBabyid() {
        return babyid;
    }

    public void setBabyid(Integer babyid) {
        this.babyid = babyid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}