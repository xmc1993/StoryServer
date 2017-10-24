package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author zj
 */
@Data
public class InitImage {
    private Integer id;
    private String imgName;
    private String imgUrl;
    private Integer isShow=0;
    private Date createTime;
    private Date updateTime;
    private Integer valid;
}
