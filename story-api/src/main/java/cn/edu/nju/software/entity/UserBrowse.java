package cn.edu.nju.software.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Author: dalec
 * Created at: 16/9/28
 */
public class UserBrowse implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2767725464492541032L;
	private Integer id;
    private Date statTime;
    private String statType;
    private Integer statId;
    private Integer statSize;
    private Integer businessId;

    // 2016-09-28 add
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatTime() {
        return statTime;
    }

    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }

    public Integer getStatId() {
        return statId;
    }

    public void setStatId(Integer statId) {
        this.statId = statId;
    }

    public Integer getStatSize() {
        return statSize;
    }

    public void setStatSize(Integer statSize) {
        this.statSize = statSize;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBrowse stat = (UserBrowse) o;
        return Objects.equals(id, stat.id) &&
                Objects.equals(statTime, stat.statTime) &&
                Objects.equals(statType, stat.statType) &&
                Objects.equals(statId, stat.statId) &&
                Objects.equals(statSize, stat.statSize) &&
                Objects.equals(businessId, stat.businessId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statTime, statType, statId, statSize, businessId);
    }

    @Override
    public String toString() {
        return "Stat{" +
                "id='" + id + '\'' +
                ", statTime='" + statTime + '\'' +
                ", statType='" + statType + '\'' +
                ", statId='" + statId + '\'' +
                ", statSize=" + statSize +
                ", businessId=" + businessId +
                '}';
    }
}
