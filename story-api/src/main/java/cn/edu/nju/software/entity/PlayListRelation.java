package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Data
public class PlayListRelation {
    private Integer id;
    private Integer worksId;//外键
    private Integer playListId;//playListId为0时代表是默认的我喜欢的列表
    private Integer userId;//外键
    private Date createTime;
    private Date updateTime;

}
