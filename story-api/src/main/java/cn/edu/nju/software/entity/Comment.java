package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer id;

    private Integer ambitusId;

    private Integer userId;

    private String content;

    private Integer likeCount=0;

    private Integer cream=0;

    private String picUrls;

    private Date createTime;

    private Integer state;

    private String userHeadImgUrl;

    private String userName;

    private boolean agree=false;

}