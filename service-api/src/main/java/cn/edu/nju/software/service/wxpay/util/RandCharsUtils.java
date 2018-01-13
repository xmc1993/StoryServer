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
    
    /**
     * 生成随机数字串
     * @param length
     * @return
     */
    public static String getRandomNumber(int length) {
        String base = "0123456789";
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

    	
    	//System.out.println(getRandomNumber(4));

        String test = "[\"{\\\"icon\\\":\\\"http:\\/\\/47.93.242.215\\/source\\/icons\\/ZEV1loYmIRTj5JMX.png\\\",\\\"tagId\\\":\\\"[]\\\",\\\"answerName\\\":\\\"讲故事\\\"}\",\"{\\\"icon\\\":\\\"http:\\/\\/47.93.242.215\\/source\\/icons\\/xqWyxJApV9ZcHyeV.png\\\",\\\"tagId\\\":\\\"[]\\\",\\\"answerName\\\":\\\"听故事\\\"}\"]";
/*        String test = "[\"{\\\"icon\\\":\\\"http:\\/\\/120.27.219.173\\/source\\/icons\\/ScLpVRcl06WpSmAt.png\\\",\\\"tagId\\\":\\\"[100055,100060,100075,100070]\\\",\\\"answerName\\\":\\\"可爱卡通\\\"}\",\"{\\\"icon\\\":\\\"http:\\/\\/120.27.219.173\\/source\\/icons\\/rnXi1wpFwbvLp0Zy.png\\\",\\\"tagId\\\":\\\"[100052,100054,100055,100057,100049,100050,100065,100118,100037,100072,100073,100078,100120]\\\",\\\"answerName\\\":\\\"生物\\\"}\",\"{\\\"icon\\\":\\\"http:\\/\\/120.27.219.173\\/source\\/icons\\/yZnTGLOBxTq42voN.png\\\",\\\"tagId\\\":\\\"[100051,100055,100057,100060,100061,100062,100063,100068,100070,100075,100071]\\\",\\\"answerName\\\":\\\"幽默\\\"}\"]";
        System.out.println(test);
        System.out.println(StringEscapeUtils.unescapeJava(test));*/
//        System.out.println(timeStart());
//        System.out.println(timeExpire());
/*        System.out.print("张");*/

        }


    }



