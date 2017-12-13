package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kt on 2017/6/25.
 */
@Data
public class App implements Serializable{
    private Integer id;
    private String version;
    private String updateHint;
    private String fileSize;
    private String url;
    private Date createTime;
    private Date updateTime;
}
