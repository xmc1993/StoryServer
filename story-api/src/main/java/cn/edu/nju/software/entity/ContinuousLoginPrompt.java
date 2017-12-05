package cn.edu.nju.software.entity;

public class ContinuousLoginPrompt {
    private Integer id;

    private String prompt;

    private Integer promptStartTime;

    private Integer promptEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt == null ? null : prompt.trim();
    }

    public Integer getPromptStartTime() {
        return promptStartTime;
    }

    public void setPromptStartTime(Integer promptStartTime) {
        this.promptStartTime = promptStartTime;
    }

    public Integer getPromptEndTime() {
        return promptEndTime;
    }

    public void setPromptEndTime(Integer promptEndTime) {
        this.promptEndTime = promptEndTime;
    }
}