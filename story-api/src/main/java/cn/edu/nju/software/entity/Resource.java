package cn.edu.nju.software.entity;

import cn.edu.nju.software.enums.ResourceType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/16.
 */
@Data
public class Resource implements Serializable{
    private Integer id;
    private String name;
    private String description;
    private String url;//其余信息值
    private ResourceType resourceType;//数据类型
    private int valid = 1;
    private Date createTime;
    private Date updateTime;
}
