package cn.edu.nju.software.entity;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public class Story {
    private String id;
    private String title;
    private String author;
    private String content;
    private String press;//出版社
    private String guide;//阅读指导
    private String coverUrl;
    private String preCoverUrl;
    private String background;//录制背景
    private String price;
    private String valid;//用于软删除

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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
