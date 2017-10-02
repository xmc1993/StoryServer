package cn.edu.nju.software.dto;

import cn.edu.nju.software.util.StringUtil;

/**
 * Created by xmc1993 on 2017/10/3.
 */
public class TimeVo {
    private String timeString = "";
    private Integer seconds = 0;

    public TimeVo(String str) {
        this.timeString = str;
        int sc = analyze(str);
        if (sc <= 0) {
            this.seconds = 0;
            this.timeString = "0'0\"";
        }else {
            this.seconds = sc;
        }
    }

    @Override
    public String toString() {
        return timeString;
    }

    public int getSeconds() {
        return seconds;
    }

    /**
     * 减少时间
     * @param str
     * @return
     */
    public boolean increase(String str) {
        if (str == null) return false;
        int sc = analyze(str);
        if (sc == -1) return false;
        seconds += sc;
        format();
        return true;
    }

    /**
     * 增加时间
     * @param str
     * @return
     */
    public boolean reduce(String str) {
        if (str == null) return false;
        int sc = analyze(str);
        if (sc == -1) return false;
        seconds -= sc;
        seconds = seconds > 0 ? seconds : 0;
        format();
        return true;
    }

    /**
     * 格式化时间
     */
    private void format(){
        int m = this.seconds/60;
        int s = this.seconds%60;
        this.timeString = m + "'" + s + "\"";
    }

    /**
     * 解析字段
     *
     * @param str
     * @return
     */
    private int analyze(String str) {
        if (StringUtil.isEmpty(str) || str.equals("0")) return 0;
        try {
            String[] arr = str.split("'");
            String minutes = arr[0];
            String seconds = arr[1].substring(0, arr[1].length() - 1);
            int count = 0;
            count += Integer.valueOf(minutes) * 60;
            count += Integer.valueOf(seconds);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
