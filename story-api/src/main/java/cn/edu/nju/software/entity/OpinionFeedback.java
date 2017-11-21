package cn.edu.nju.software.entity;

import java.util.Date;

public class OpinionFeedback {
    private Integer id;

    private String model;

    private String appVersion;

    private String androidVersion;

    private String networkEnvironment;

    private String connection;

    private Integer userId;

    private String description;

    private String picUrls;

    private String opinionType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getappVersion() {
        return appVersion;
    }

    public void setappVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getandroidVersion() {
        return androidVersion;
    }

    public void setandroidVersion(String androidVersion) {
        this.androidVersion = androidVersion == null ? null : androidVersion.trim();
    }

    public String getnetworkEnvironment() {
        return networkEnvironment;
    }

    public void setnetworkEnvironment(String networkEnvironment) {
        this.networkEnvironment = networkEnvironment == null ? null : networkEnvironment.trim();
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection == null ? null : connection.trim();
    }

    public Integer getuserId() {
        return userId;
    }

    public void setuserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getpicUrls() {
        return picUrls;
    }

    public void setpicUrls(String picUrls) {
        this.picUrls = picUrls == null ? null : picUrls.trim();
    }

    public String getopinionType() {
        return opinionType;
    }

    public void setopinionType(String opinionType) {
        this.opinionType = opinionType == null ? null : opinionType.trim();
    }

    public Date getcreateTime() {
        return createTime;
    }

    public void setcreateTime(Date createTime) {
        this.createTime = createTime;
    }
}