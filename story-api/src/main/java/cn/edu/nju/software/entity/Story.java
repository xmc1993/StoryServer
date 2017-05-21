package cn.edu.nju.software.entity;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public class Story {
    private Integer id;
    private String title;
    private String author;
    private String content;
    private String press;//出版社
    private String guide;//阅读指导
    private String coverUrl;
    private String preCoverUrl;
    private String backgroundUrl;//录制背景图url
    private String originSoundUrl;//原始音效url
    private String price;
    private Date createTime;
    private Date updateTime;
    private Integer valid = 1;//用于软删除

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getPreCoverUrl() {
        return preCoverUrl;
    }

    public void setPreCoverUrl(String preCoverUrl) {
        this.preCoverUrl = preCoverUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOriginSoundUrl() {
        return originSoundUrl;
    }

    public void setOriginSoundUrl(String originSoundUrl) {
        this.originSoundUrl = originSoundUrl;
    }
}
