package cn.edu.nju.software.util;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Description ： String 工具类
 * 
 * Author ： junli.zhang
 * 
 * Time : 2013-07-15
 */
public class StringUtil {


	/**
	 * 判断字符串是否为空
	 *
	 * @param value
	 * @return true or false
	 */
	public static boolean isEmpty(String value) {
		return (value == null || "".equals(value.trim()) || "null".equals(value.trim()));
	}

	/**
	 * 判断Long是否为null或0
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Long value) {
		return (value == null || value == 0l);
	}
	
	/**
	 * 判断整形是否为null或0
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Integer value) {
		return (value == null || value == 0);
	}

	/**
	 * 判断List是否为空
	 *
	 * @param list
	 * @return true or false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
		if (list == null || list.size() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 判断Set是否为空
	 *
	 * @param set
	 * @return true or false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Set set) {
		if (set == null || set.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断Map是否为空
	 *
	 * @param map
	 * @return true or false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map map) {
		if (map == null || map.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断Map是否为空
	 *
	 * @param value
	 * @return true or false
	 */
	public static boolean isEmpty(Object Value) {
		if (Value == null || Value.equals("null"))
			return true;
		else
			return false;
	}

	/**
	 * 判断数组是否为空
	 *
	 * @param value
	 * @return true or false
	 */
	public static boolean isEmpty(Object[] Value) {
		if (Value == null || Value.length == 0)
			return true;
		else
			return false;
	}

	/**
	 * 根据指定的正则表达式校验字符串
	 *
	 * @param reg
	 *            正则表达式
	 * @param string
	 *            拼配的字符串
	 * @return
	 */
	public static boolean startCheck(String reg, String string) {
		boolean tem = false;

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);

		tem = matcher.matches();
		return tem;
	}



	/**
	 * 获取十位随机数
	 * @return	String	十位随机数
	 */
	public static String getRandom()
	{
		String retStr = "";
		for(int i = 0; i < 10; i++)
		{
			retStr += new Random().nextInt(10);
		}
		return retStr;
	}
	
	/**
	 * 方法描述：获取Request中的 InputStream内容
	 * @param in			request InputStream
	 * @return
	 * vti-fei	2013-12-7		下午3:09:50
	 */
	public static String getRequestInputStream(InputStream in) {

		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer(0);
		try {
			
			reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));

	        String lines;
	        while ((lines = reader.readLine()) != null) {
	            lines = new String(lines.getBytes(), "utf-8");
	            sb.append(lines);
	        }
//	        System.out.println(sb);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
}
