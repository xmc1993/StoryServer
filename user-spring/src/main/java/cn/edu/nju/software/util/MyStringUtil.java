package cn.edu.nju.software.util;

import java.util.ArrayList;
import java.util.List;


/**
 * 字符串处理工具类
 * @author liuyu
 *
 */
public class MyStringUtil {
	/**
	 * 将字符串按特定符号分隔，并转为List<Integer>
	 * @param target
	 * @param regex
	 * @return
	 */
	public static List<Integer> strToIntList(String target,String regex){
		List<Integer> list = new ArrayList<>();
		if(target==null || target.equals("undefined")){
			return list;
		}
		String[] strArr = target.split(regex);
		
		//字符串以分隔符号开头
		int i = 0;
		if(strArr[0].trim().equals("")){
			i=1;			
		}
		for(;i<strArr.length;i++){
			try{
				list.add(Integer.parseInt(strArr[i].trim()));
			}catch(NumberFormatException e){
				e.printStackTrace();
			}									
		}	
		return list;
	}
	
	public static void main(String[] args){
		List list = new ArrayList<String>();
		list.add("大伟哥");
		list.add(null);
		list.add("哈哈哈");
		System.out.println(list);
	}
}
