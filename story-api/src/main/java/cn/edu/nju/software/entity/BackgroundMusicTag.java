package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
public class BackgroundMusicTag {
    private Integer id;
    private String content;
    private Date createTime;
    private Date updateTime;
    private int valid = 1;//用于软删除

}
