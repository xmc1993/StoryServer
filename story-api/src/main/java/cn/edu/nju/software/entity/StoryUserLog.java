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
}
