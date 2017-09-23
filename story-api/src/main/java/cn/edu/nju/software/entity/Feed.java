package cn.edu.nju.software.entity;

import cn.edu.nju.software.entity.feed.MessageType;
import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/9/15.
 */
@Data
public class Feed {
    private Integer id;
    private Integer mid;//信息相关实体的ID
    private Integer fid;//发送方
    private Integer tid;//接收方
    private String content;
    private MessageType type;
    private int valid = 1;
    private int isRead = 0;//是否已读
    private Date createTime;
    private Date updateTime;
}
