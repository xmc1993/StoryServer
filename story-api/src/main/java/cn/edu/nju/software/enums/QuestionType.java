package cn.edu.nju.software.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xmc1993 on 2016/12/1.
 */
public class QuestionType {
    public static final int SINGLE = 0;
    public static final int MULTIPLE = 1;
    public static final int JUDGE = 2;
    public static final Map<Integer, String> TYPE_MAP = new HashMap();

    static {
        TYPE_MAP.put(SINGLE, "单选题");
        TYPE_MAP.put(MULTIPLE, "多选题");
        TYPE_MAP.put(JUDGE, "判断题");
    }
}
