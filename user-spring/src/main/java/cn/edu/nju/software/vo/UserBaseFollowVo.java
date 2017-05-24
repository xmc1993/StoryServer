package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.UserBase;

/**
 * Created by xmc1993 on 2017/5/25.
 */
public class UserBaseFollowVo extends UserBase{
    private int status = 0;//0代表没关系 1代表关注 2代表被关注 3代表互相关注

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
