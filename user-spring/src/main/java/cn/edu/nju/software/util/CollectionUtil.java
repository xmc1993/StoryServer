package cn.edu.nju.software.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类
 *
 * @author liuyu
 * @create 2017-08-29 下午12:47
 */
public class CollectionUtil {
    /**
     * 获取list的fildName字段
     * @param list
     * @param clazz 泛型类
     * @param filedName 泛型类型的一个成员对象，要求是整形
     * @param <T>
     * @return
     */
    public static <T> List<Integer> getIdListFromEntityList(List<T> list,Class<T> clazz,String filedName){
        List<Integer> idList = new ArrayList<>();
        for (T t:list
             ) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields
                 ) {
                field.setAccessible(true);
                if(field.getName().equals(filedName)){
                    try {
                        int id = field.getInt(t);
                        idList.add(id);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return idList;
    }

}
