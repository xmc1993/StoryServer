package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Kt on 2017/6/25.
 */
@Data
public class WorkUserLog {
    private Integer id;
    private Integer userId;
    private Integer workId;
    private Integer storyId;
    private String channel = "default";
    private Date accessTime;

    public WorkUserLog() {

    }

    public WorkUserLog(Integer userId, Integer workId, String channel, Date accessTime) {
        this.userId = userId;
        this.workId = workId;
        this.channel = channel;
        this.accessTime = accessTime;
    }
}
