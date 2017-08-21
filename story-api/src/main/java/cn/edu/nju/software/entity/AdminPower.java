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
    private Integer codeId;
    private Date createTime;
    private Date updateTime;
}
