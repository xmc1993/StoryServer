package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Works;

/**
 * Created by zhangsong on 2017/11/17.
 */
public class ShareWorkWithreadPlan extends Works {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
