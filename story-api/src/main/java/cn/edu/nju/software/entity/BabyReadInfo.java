package cn.edu.nju.software.entity;

public class BabyReadInfo {
    private Integer id;

    private Integer storyid;

    private String iocnurl;

    private Integer valid;

    private String content;

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

    public String getIocnurl() {
        return iocnurl;
    }

    public void setIocnurl(String iocnurl) {
        this.iocnurl = iocnurl == null ? null : iocnurl.trim();
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}