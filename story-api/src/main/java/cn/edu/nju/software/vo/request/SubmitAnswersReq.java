package cn.edu.nju.software.vo.request;

import java.util.List;

/**
 * Created by xmc1993 on 16/12/23.
 */
public class SubmitAnswersReq {
    private String appId;
    private Integer examineId;
    private Integer userId;
    private List<List<Integer>> userOptions;
    private Integer useTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getExamineId() {
        return examineId;
    }

    public void setExamineId(Integer examineId) {
        this.examineId = examineId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<List<Integer>> getUserOptions() {
        return userOptions;
    }

    public void setUserOptions(List<List<Integer>> userOptions) {
        this.userOptions = userOptions;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }
}
