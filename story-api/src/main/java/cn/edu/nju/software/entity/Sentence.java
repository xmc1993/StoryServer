package cn.edu.nju.software.entity;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public class Sentence {
    private Integer id;
    private String content;
    private Integer soundEffectId;//外键对应音效的url

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
        this.content = content;
    }

    public Integer getSoundEffectId() {
        return soundEffectId;
    }

    public void setSoundEffectId(Integer soundEffectId) {
        this.soundEffectId = soundEffectId;
    }
}
