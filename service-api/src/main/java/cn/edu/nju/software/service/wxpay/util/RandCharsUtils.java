package cn.edu.nju.software.service.wxpay.util;

/**
 * Created by xmc1993 on 2017/4/23.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RandCharsUtils {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 生成随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int number = 0;
        for (int i = 0; i < length; i++) {
            number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String timeStart() {
        return df.format(new Date());
    }


    public static String timeExpire() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 30);
        return df.format(now.getTimeInMillis());
    }


    public static void main(String[] args) {


        System.out.println(timeStart());
        System.out.println(timeExpire());
    }

}

