package cn.edu.nju.software.vo;

/**
 * Created by xmc1993 on 17/2/27.
 */
public class MessageVo {
    private int remainingNum = -1;
    private int hasBeenSentNum = -1;

    public MessageVo(int remainingNum, int hasBeenSentNum) {
        this.remainingNum = remainingNum;
        this.hasBeenSentNum = hasBeenSentNum;
    }

    public int getRemainingNum() {
        return remainingNum;
    }

    public void setRemainingNum(int remainingNum) {
        this.remainingNum = remainingNum;
    }

    public int getHasBeenSentNum() {
        return hasBeenSentNum;
    }

    public void setHasBeenSentNum(int hasBeenSentNum) {
        this.hasBeenSentNum = hasBeenSentNum;
    }
}
