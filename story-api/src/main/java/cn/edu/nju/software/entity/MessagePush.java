package cn.edu.nju.software.entity;

import java.util.Date;

public class MessagePush {
    private Integer id;

    private String ticker;

    private String title;

    private String text;

    private Integer destinationId;

    private Date startTime;

    private Date expireTime;

    private Date createTime;

    private Integer pushType;

    private String extraField;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker == null ? null : ticker.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Integer getDestinationid() {
        return destinationId;
    }

    public void setDestinationid(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Date getStarttime() {
        return startTime;
    }

    public void setStarttime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpiretime() {
        return expireTime;
    }

    public void setExpiretime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPushtype() {
        return pushType;
    }

    public void setPushtype(Integer pushType) {
        this.pushType = pushType;
    }

    public String getExtrafield() {
        return extraField;
    }

    public void setExtrafield(String extraField) {
        this.extraField = extraField == null ? null : extraField.trim();
    }
}