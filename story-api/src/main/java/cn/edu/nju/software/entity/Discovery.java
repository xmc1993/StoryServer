package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Kt on 2017/6/26.
 */
@Data
public class Discovery {
    private Integer id;
    private String title;
    private String pictureUrl;
    private String webUrl;
    private String description;
    private Date createTime;
    private Date updateTime;
}
