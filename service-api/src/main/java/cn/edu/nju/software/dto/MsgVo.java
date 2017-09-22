package cn.edu.nju.software.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/9/22.
 */
@Data
public class MsgVo<T> {
    private String userName;
    private Integer userId;
    private String headImgUrl;
    private Date time;
    private T data;
}
