package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/16.
 */
@Data
public class PlayList implements Serializable{
    private Integer id;
    private String name;
    private Integer userId;
    private String cover;//封面
    private Date createTime;
    private Date updateTime;
}
