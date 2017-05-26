package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
public class SoundEffectTagRelation {
    private Integer id;
    private Integer soundEffectId;//外键
    private Integer tagId;//外键
    private Date createTime;
    private Date updateTime;
}
