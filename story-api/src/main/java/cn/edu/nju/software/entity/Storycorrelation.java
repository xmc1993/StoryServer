package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
public class StoryCorrelation {
    private Integer id;
    private Integer storyIdA;//故事A
    private Integer storyIdB;//故事B
    private Integer count = 0;//既喜欢A又喜欢B故事的数量
    private Double coefficient = 0.0;//既喜欢A又喜欢B故事的数量
    private Date createTime;
    private Date updateTime;
}
