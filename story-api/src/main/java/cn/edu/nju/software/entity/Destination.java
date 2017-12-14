package cn.edu.nju.software.entity;

import java.util.Date;

public class Destination {
    private Integer id;

    private String content;

    private Integer destinationType;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String extraField;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getDestinationtype() {
        return destinationType;
    }

    public void setDestinationtype(Integer destinationType) {
        this.destinationType = destinationType;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updateTime;
    }

    public void setUpdatetime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getExtrafield() {
        return extraField;
    }

    public void setExtrafield(String extraField) {
        this.extraField = extraField == null ? null : extraField.trim();
    }
}