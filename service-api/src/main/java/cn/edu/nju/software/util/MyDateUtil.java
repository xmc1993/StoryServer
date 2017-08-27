package cn.edu.nju.software.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {
	public static String dateToString(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		 return sdf.format(date);
	}
	
	/**
	 * 获取第(i+1)天24点时间戳
	 * @param time
	 * @return
	 */
	public static long dateToUnix(int i){
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.DATE, i);
		cal.set(Calendar.HOUR_OF_DAY, 23); 
		cal.set(Calendar.SECOND, 59); 
		cal.set(Calendar.MINUTE, 59); 
		cal.set(Calendar.MILLISECOND, 0); 
		return  cal.getTimeInMillis(); 			
	}
	
	public static void main(String[] args){
		String a = dateToString(new Date(),"yyyyMMdd");
		System.out.println(dateToUnix(1));	
		SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String d = format.format(dateToUnix(1));
		System.out.println(d);
	}
}
