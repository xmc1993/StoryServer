package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
public class Story {
    private Integer id;
    private String title;
    private String author;
    private String content;
    private String press;//出版社
    private String guide;//故事说明
    private String coverUrl;
    private String preCoverUrl;
    private String backgroundUrl;//录制背景图url
    private String originSoundUrl;//原始音效url
    private String readGuide; //阅读指导
    private String price;
    private Date createTime;
    private Date updateTime;
    private Integer valid = 1;//用于软删除
    private String duration;
    private Integer recommend = 0;//是否被推荐 0 否 1 是
    private Integer tellCount = 0;//
    private Integer defaultBackGroundMusicId;//
    private Integer likeCount;
    private Integer draft;
}
