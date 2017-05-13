package cn.edu.nju.software.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmc1993 on 2017/4/17.
 */
public class ParseAnswerUtil {
    public static List<Integer> deFormatAnswer(int origin) {
        List<Integer> result = new ArrayList<>();
        if (origin > 0) {//兼容历史数据
            //处理选择题情况
            int[] flags = {1000, 100, 10, 1};
            for (int i = 0; i < flags.length; i++) {
                origin -= flags[i];
                if (origin >= 0) {
                    result.add(i);
                } else {
                    origin += flags[i];
                }
            }

        } else {
            origin = -origin;
            String temp = "" + origin;
            System.out.println(temp);
            char[] chs = temp.toCharArray();
            int length = chs.length -1;
            for (int i = (length); i >= 0; i--) {
                if (chs[i] == '1') {
                    result.add(length - i);
                }
            }
        }

        return result;
    }
}
