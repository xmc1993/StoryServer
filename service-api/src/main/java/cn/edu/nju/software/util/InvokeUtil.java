package cn.edu.nju.software.util;

import java.lang.reflect.Field;

/**
 * Created by xmc1993 on 2017/4/17.
 */
public class InvokeUtil {

    public static Object getValue(Object o, String fieldName) throws Exception {
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.getName().equals(fieldName)){
                field.setAccessible(true);
                return field.get(o);
            }
        }

        throw new Exception("没有找到指定的字段！");

    }
}
