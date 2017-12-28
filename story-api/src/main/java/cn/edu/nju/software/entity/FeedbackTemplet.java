package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FeedbackTemplet {
    private Integer id;

    private String content;

    private Date createTime;

    private Date updateTime;

    private String description;

}