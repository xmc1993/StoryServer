package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Data
public class TagUserLog {
    private Integer id;
    private Integer tagId;
    private Integer userId;
    private Integer babyId = 0;
    private Integer totalCount = 0;
    private Date createTime;
    private Date updateTime;
}
