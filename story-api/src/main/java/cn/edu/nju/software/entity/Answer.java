package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/16.
 */
@Data
public class Answer implements Serializable{
    private Integer id;
    private Integer userId;//对应的勋章种类
    private Integer questionId;
    private Integer content;
    private Date createTime;
    private Date updateTime;
}
