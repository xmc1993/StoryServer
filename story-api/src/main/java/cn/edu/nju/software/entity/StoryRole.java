package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/16.
 */
@Data
public class StoryRole implements Serializable{
    private Integer id;
    private Integer storyId;//故事ID
    private String name;
    private String icon;//图标
    private String audio;//音频
    private String extra;//其余信息值
    private Date createTime;
    private Date updateTime;
}
