package cn.edu.nju.software.entity;

import java.util.Date;

import lombok.Data;

/**
* @author zs
* @version 创建时间：2017年9月12日 上午11:56:57
*/
@Data
public class UsageStatistic {
    private Integer id;
    private Integer userId;
    private Date lastUsageTime; //上一次使用的时间
    private Integer curCount = 0;
    private Integer historyMaxCount = 0;
    private Date createTime;
    private Date updateTime;
}
