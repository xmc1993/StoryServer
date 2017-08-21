package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/8/21.
 */
@Data
public class AdminPower {
    private Integer id;
    private Integer adminId;
    private String codeId;
    private Date createTime;
    private Date updateTime;
    /**
     * 1 增
     * 2 删
     * 3 改
     * 4 查
     * 6 故事标签分类
     * 7 故事标签
     */
}
