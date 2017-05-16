package cn.edu.nju.software.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/16.
 */
public class Admin implements Serializable{
    private static final long serialVersionUID = -2837545145321233714L;
    private Integer id;
    private String username;//用户名
    private String password;//密码
    private String accessToken;//story token
    private Date expireTime;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
}

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
