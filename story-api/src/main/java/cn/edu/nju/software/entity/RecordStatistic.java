package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/7/13.
 */
@Data
public class RecordStatistic {
    private Integer id;
    private Integer userId;
    private Date lastRecordTime; //上一次录制时间
    private Integer curCount = 0;
    private Integer historyMaxCount = 0;
    private Date createTime;
    private Date updateTime;
}
