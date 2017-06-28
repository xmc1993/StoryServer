package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Kt on 2017/6/25.
 */
@Data
public class StoryUserLog {
    private Integer id;
    private Integer userId;
    private Integer storyId;
    private String channel;
    private Date accessTime;

    public StoryUserLog() {
    }

    public StoryUserLog(Integer userId, Integer storyId, String channel, Date accessTime) {
        this.userId = userId;
        this.storyId = storyId;
        this.channel = channel;
        this.accessTime = accessTime;
    }
}
