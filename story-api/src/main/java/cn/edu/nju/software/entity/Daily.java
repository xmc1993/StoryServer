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
    //多图片上传时存的位置,添加了多图片上传的功能，3.1.1版本后图片都存这，为了维持老版本，picUrl不删
    private String picUrls;
    private Integer visibility;
    private Integer deleted = 0;
    private Integer draft = 0;//是否为草稿
    private Integer readCount = 0;//阅读数
    private Date createTime;
    private Date updateTime;
}
