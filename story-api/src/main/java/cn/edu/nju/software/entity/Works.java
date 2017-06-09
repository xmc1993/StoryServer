package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Data
public class Works {
    private Integer id;
    private Integer storyId;
    private String storyTitle;//字段冗余
    private Integer userId;
    private String username;//字段冗余
    private String url;//音频的url
    private Integer likeCount = 0;//点赞数
    private Date createTime;
    private Date updateTime;
    private Integer valid = 1;//用于软删除
    private String headImgUrl;
    private String coverUrl;
}
