package cn.edu.nju.software.vo;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public class WorksVo {
    private Integer id;
    private Integer storyId;
    private Integer userId;
    private String url;//音频的url
    private Integer likeCount = 0;//点赞数
    private Integer storyTitle;
    private Integer useName;

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(Integer storyTitle) {
        this.storyTitle = storyTitle;
    }

    public Integer getUseName() {
        return useName;
    }

    public void setUseName(Integer useName) {
        this.useName = useName;
    }
}
