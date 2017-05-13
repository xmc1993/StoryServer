package cn.edu.nju.software.vo;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public class StoryBaseVo {
    private String id;
    private String title;
    private String author;
    private String press;//出版社
    private String guide;//阅读指导
    private String coverUrl;
    private String preCoverUrl;
    private String backgroundUrl;//录制背景图url
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

}
