package cn.edu.nju.software.entity;

import java.util.Date;

public class ContinuousLoginPrompt {
    private Integer id;

    private Integer userId;

    private String prompt;

    private Date promptStartTime;

    private Date promptEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt == null ? null : prompt.trim();
    }

    public Date getPromptStartTime() {
        return promptStartTime;
    }

    public void setPromptStartTime(Date promptStartTime) {
        this.promptStartTime = promptStartTime;
    }

    public Date getPromptEndTime() {
        return promptEndTime;
    }

    public void setPromptEndTime(Date promptEndTime) {
        this.promptEndTime = promptEndTime;
    }
}