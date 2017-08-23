package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/16.
 */
@Data
public class StoryPopularFactor implements Serializable{
    private Integer id;
    private Integer storyId;
    private Integer factor = 0;
    private Date createTime;
    private Date updateTime;
}
