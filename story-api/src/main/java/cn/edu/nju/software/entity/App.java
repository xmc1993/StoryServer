package cn.edu.nju.software.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Kt on 2017/6/25.
 */
@Data
public class App {
    private Integer id;
    private String version;
    private String updateHint;
    private String fileSize;
    private String url;
    private Date createTime;
    private Date updateTime;
    private Integer ifUpdate;
}
