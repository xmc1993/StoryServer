package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/16.
 */
@Data
public class Daily implements Serializable{
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private String picUrl;
    private Integer visibility;
    private Integer deleted = 0;
    private Integer draft = 0;//是否为草稿
    private Date createTime;
    private Date updateTime;
}
