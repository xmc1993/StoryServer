package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Data
public class SoundEffect {//音效
    private Integer id;
    private String description;
    private String icon;
    private String url;
    private Date createTime;
    private Date updateTime;
    private Integer valid = 1;//用于软删除
}
