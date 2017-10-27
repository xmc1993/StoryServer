package cn.edu.nju.software.vo;

import lombok.Data;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Data
public class UserBaseVo {
    private Integer id;
    private String nickname;//用户名
    private String sex;//性别
    private String headImgUrl;//头像
    //后续添加字段
    private Integer followerCount = 0;//粉丝数
    private Integer followeeCount = 0;//关注的人数
    private Integer workCount = 0;//作品数
}
