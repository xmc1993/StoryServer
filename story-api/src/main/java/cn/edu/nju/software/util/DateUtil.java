 package cn.edu.nju.software.util;

 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;

 import java.text.DateFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.*;

 /***
  * Description：日期处理工具类
  *
  * Author: junli.zhang
  *
  * Time: 2013-07-15
  */
 public class DateUtil {

     /** The log. */
     private static Log log = LogFactory.getLog(DateUtil.class);

     private static final String patternDateMinutes = "yyyy-MM-dd HH:mm";

     /**
      * 生成日期解析对象
      * @param 	pattern		转换格式
      * @return	DateFormat	日期解析对象
      */
     private static DateFormat doDateFormat(String pattern)
     {
         return new SimpleDateFormat(pattern);
     }

     /**
      * 日期转换到字符串
      * @param 	paramDate		要转换的日期
      * @param 	pattern			转换格式：例：yyyy-MM-dd
      * @return	String			日期字符串
      */
     public static String dateToString(Date paramDate, String pattern)
     {
         return doDateFormat(pattern).format(paramDate);
     }

     /**
      * 获取当前日期(字符串格式)
      * @param 	pattern		转换格式：例：yyyy-MM-dd
      * @return	String		日期字符串
      */
     public static String getCurrDate(String pattern)
     {
         return dateToString(new Date(), pattern);
     }

     /**
      * 方法描述：格式化日期为pattern形式
      * @param myDate			日期字符串
      * @param pattern			转化的格式
      * @return String			返回字符串
      * vti-fei	2013-12-9		下午8:48:03
      */
     public static String formatDateTime(String myDate, String pattern) {
         if (myDate.length() > 10)
             myDate = myDate.substring(0, 10);
         Date date = getDateByString(myDate, pattern);
         return formatDateTime(date, pattern, Locale.US);
     }

     /**
      * 方法描述：格式化日期为标准形式
      * @param myDate
      * @param pattern
      * @return
      * vti-fei	2013-12-9		下午8:47:54
      */
     public static String formatDateTime(Date myDate, String pattern) {
         // SimpleDateFormat fd = new SimpleDateFormat(pattern, Locale.CHINA);
         SimpleDateFormat fd = new SimpleDateFormat(pattern);
         return (fd.format(myDate));
     }

     /**
      * 方法描述：格式化日期为标准形式
      * @param myDate
      * @param pattern
      * @param localcode
      * @return
      * vti-fei	2013-12-9		下午8:47:45
      */
     public static String formatDateTime(Date myDate, String pattern, Locale localcode) {
         SimpleDateFormat fd = new SimpleDateFormat(pattern, localcode);
         return (fd.format(myDate));
     }

     /**
      * 方法描述：格式化日期为pattern形式
      * @param str				日期字符串
      * @param pattern			转化的格式
      * @return Date				返回日期类型
      * vti-fei	2013-12-9		下午8:48:49
      */
     public static Date getDateByString(String str, String pattern) {
         SimpleDateFormat sdf = null;
         try {
             sdf = new SimpleDateFormat(pattern);
         } catch (Exception ex) {
             sdf = new SimpleDateFormat(patternDateMinutes);
         }
         try {
             return sdf.parse(str);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }

     /**
      * 方法描述：根据pattern时间格式返回，strDate时间字符串对应的日期类型对象
      * @param aMask				pattern时间格式
      * @param strDate			时间字符串
      * @return Date				日期对象
      * @throws ParseException
      * vti-fei	2013-12-16		下午6:42:26
      */
     public static final Date convertStringToDate(String pattern, String strDate) throws ParseException {
         SimpleDateFormat df = null;
         Date date = null;
         df = new SimpleDateFormat(pattern);

         try {
             date = df.parse(strDate);
         } catch (ParseException pe) {
             throw pe;
         }
         return (date);
     }

     /**
      * 方法描述：根据pattern时间格式返回，strDate时间字符串对应的日期类型对象
      * @param pattern
      * @param strDate
      * @return
      * @throws ParseException
      */
     public static final java.sql.Date convertStringToSqlDate(String pattern, String strDate) throws ParseException {
         SimpleDateFormat df = null;
         Date date = null;
         df = new SimpleDateFormat(pattern);

         try {
             date = df.parse(strDate);
         } catch (ParseException pe) {
             throw pe;
         }
         java.sql.Date data = new java.sql.Date(date.getTime());
         return (data);
     }

     /**
      * 方法描述：获取date增加i天后的日期
      * @param pattern			返回的日期格式
      * @param date				日期字符串
      * @param i					添加的天数
      * @return
      * vti-fei	2013-12-16		下午6:54:54
      */
     public static String addDay(String pattern, String date, int i) {
         try {
             GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
                     Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8,10)));
             gCal.add(Calendar.DATE, i);
             return (new SimpleDateFormat(pattern)).format(gCal.getTime());
         } catch (Exception e) {
             log.debug("DateUtil.addDay():" + e.toString());
             return getDate(pattern);
         }
     }

     /**
      * 方法描述：获取date增加i小时后的日期
      * @param pattern			返回的日期格式
      * @param date				日期字符，日期格式为yyyyMMddHHmmss
      * @param i					添加的小时数
      * @return
      * vti-fei	2013-12-16		下午6:54:54
      */
     public static String addHour(String pattern, String date, int i) {
         try {
             GregorianCalendar gCal = new GregorianCalendar();
             gCal.setTime(getDateByString(date, pattern));
             gCal.add(Calendar.HOUR_OF_DAY, i);
             return (new SimpleDateFormat(pattern)).format(gCal.getTime());
         } catch (Exception e) {
             log.debug("DateUtil.addHour():" + e.toString());
             return getDate(pattern);
         }
     }

     /**
      * 方法描述：获取date增加i分钟后的日期
      * @param pattern			返回的日期格式
      * @param date				日期字符，日期格式为yyyyMMddHHmmss
      * @param i					添加的分钟数
      * @return
      * vti-fei	2013-12-16		下午6:54:54
      */
     public static String addMinute(String pattern, String date, int i) {
         try {
             GregorianCalendar gCal = new GregorianCalendar();
             gCal.setTime(getDateByString(date, pattern));
             gCal.add(Calendar.MINUTE, i);
             return (new SimpleDateFormat(pattern)).format(gCal.getTime());
         } catch (Exception e) {
             log.debug("DateUtil.addMinute():" + e.toString());
             return getDate(pattern);
         }
     }

     /**
      * 方法描述：获得服务器当前日期，以格式为：pattern的日期字符串形式返回.
      * @param pattern			日期格式
      * @return
      * vti-fei	2013-12-16		下午7:02:57
      */
     public static String getDate(String pattern) {
         try {
             return (new SimpleDateFormat(pattern)).format(Calendar.getInstance().getTime());
         } catch (Exception e) {
             log.debug("DateUtil.getDate():" + e.getMessage());
             return "";
         }
     }

     /**
      * 方法描述：获取当前日期 + 传递的小时和分钟
      * @param hh_mm				小时_分钟字符串
      * @return
      * vti-fei	2014-1-5		下午1:34:34
      */
     public static Date getCurrentDate4HHmm(String hh_mm) {
         try {
             String pattern = "yyyy-MM-dd HH:mm:ss";
             String currrent_str = getCurrDate(pattern).substring(0, 10) + " " + hh_mm + ":00";
             return convertStringToDate(pattern, currrent_str);
         } catch (ParseException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
             return null;
         }
     }

     /**
      * 取日期对应的星期
      *
      * @param date
      * @return
      */
     public static int getDate4WeekInt(Date date) {
         // int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);//get
         // Date
         Calendar c = Calendar.getInstance();
         if (date != null)
             c.setTime(date);
         int day = c.get(Calendar.DAY_OF_WEEK)-1;// get week
         return 0 == day ? 7:day;
     }

     /**
      * 取日期对应的星期
      *
      * @param date
      * @return
      */
     public static String getDate4Week(Date date) {
         // int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);//get
         // Date
         Calendar c = Calendar.getInstance();
         if (date != null)
             c.setTime(date);
         int day = c.get(Calendar.DAY_OF_WEEK);// get week
         String week = null;
         switch (day) {

         case 1:
             week = "星期天";
             break;

         case 2:
             week = "星期一";
             break;

         case 3:
             week = "星期二";
             break;

         case 4:
             week = "星期三";
             break;

         case 5:
             week = "星期四";
             break;

         case 6:
             week = "星期五";
             break;

         case 7:
             week = "星期六";
             break;
         }

         return week;
     }

     /**
      * 方法描述：获取闹钟最新提醒时间
      * @param HH_mm				闹钟小时：分钟
      * @param rept_times		重复次数，如[1,2,3,4,5]
      * @return
      * @throws ParseException
      * vti-fei	2014-1-5		下午7:24:17
      */
     public static Date getNextAlarmTime(String HH_mm, String[] rept_times) {

         try {
             String pattern = "yyyy-MM-dd HH:mm:ss";
             Date nextAlarmTime = getCurrentDate4HHmm(HH_mm);
             if (null == rept_times || 0 == rept_times.length) {//如果没有重复闹钟，就只返回当前时间
                 return nextAlarmTime;
             }

             int current_date_week = getDate4WeekInt(new Date());
             int[] diff_days = new int[rept_times.length];
             for (int i = 0; i < rept_times.length; i++) {
                 int rept_int = Integer.parseInt(rept_times[i]);
                 if (0 == rept_int) {
                     return null;
                 }
                 int diff = rept_int - current_date_week;
                 if (diff < 0) {
                     diff = 7 - Math.abs(diff);
                 } else if (0 == diff && new Date().after(nextAlarmTime)) {
                     diff = 7;
                 }
                 diff_days[i] = diff;
             }

             Arrays.sort(diff_days);

             String currrent_str = getCurrDate(pattern);

             String next_date_str = addDay(pattern, currrent_str, diff_days[0]);
             next_date_str = next_date_str.substring(0, 10) + " " + HH_mm + ":00";

             return convertStringToDate("yyyy-MM-dd HH:mm:ss", next_date_str);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }

     /**
      * 方法描述：比较date1比date2两个时间相差的秒数
      * @param date1
      * @param date2
      * @return
      * vti-fei	2014-1-15		下午9:41:50
      */
     public static long diffDateSecond(Date date1, Date date2) {
         long date1L = date1.getTime();
         long date2L = date2.getTime();
         return (date1L - date2L)/1000;
     }

     private static DateUtil instance;

     public static DateUtil getInstance() {
         // return instance;
         if (instance == null) {
             synchronized (DateUtil.class) {
                 instance = new DateUtil();
             }
         }
         return instance;
     }

     /**
      * 返回某年某月中的最大天
      */
     public static int getMaxDay(String year, String month) {
         int day = 0;
         try {
             int iyear = Integer.parseInt(year);
             int imonth = Integer.parseInt(month);
             if (imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7
                     || imonth == 8 || imonth == 10 || imonth == 12) {
                 day = 31;
             } else if (imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11) {
                 day = 30;
             } else if ((0 == (iyear % 4)) && (0 != (iyear % 100)) || (0 == (iyear % 400))) {
                 day = 29;
             } else {
                 day = 28;
             }
             return day;
         } catch (Exception e) {
             log.debug("DateUtil.getMaxDay():" + e.toString());
             return 1;
         }
     }

     /**
      * 根据两时间差判断提示内容
      * @param date1
      * @param date2
      * @return
      */
     public static String timeTeminder(Date date1,Date date2){
         String timeTeminder = "1个月前";
         if(date2==null){
             return timeTeminder;
         }
         long time = diffDateSecond(date1, date2)*1000;
         long day = time / (24 * 60 * 60 * 1000);
         long hour = (time / (60 * 60 * 1000) - day * 24);
         long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
         if(day>365){
             timeTeminder = day/365+"年前";
         }else if(day>30 && day<=365){
             timeTeminder = day/30+"个月前";
         }else if(day>=1 && day<=30){
             timeTeminder = day+"天前";
         }else if(day<1 && hour<=23 && hour>=1){
             timeTeminder = hour+"个小时以前";
         }else if(day<1 && min<=59 && min>=30){
             timeTeminder = "30分钟前";
         }else if(day<1 && min<30 && min>=20){
             timeTeminder = "20分钟前";
         }else if(day<1 && min<20 && min>=10){
             timeTeminder = "10分钟前";
         }else if(day<1 && min<10){
             timeTeminder = "刚刚";
         }
         return timeTeminder;
     }

     /**
      * 根据两时间差判断提示内容
      * @param date1
      * @param str   格式为yyyy-mm-dd
      * @return
      */
     public static String timeTeminder(Date date1,String str){
         String timeTeminder = "1个月前";
         Date date2 = null;
         try {
             date2 = DateUtil.convertStringToDate("yyyy-MM-dd",str);
         } catch (ParseException e) {
             e.printStackTrace();
         } finally {
             if(date2==null){
                 return timeTeminder;
             }
         }
         long time = diffDateSecond(date1, date2)*1000;
         long day = time / (24 * 60 * 60 * 1000);
         long hour = (time / (60 * 60 * 1000) - day * 24);
         long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
         if(day>365){
             timeTeminder = day/365+"年前";
         }else if(day>30 && day<=365){
             timeTeminder = day/30+"个月前";
         }else if(day>=1 && day<=30){
             timeTeminder = day+"天前";
         }else if(day<1 && hour<=23 && hour>=1){
             timeTeminder = hour+"个小时以前";
         }else if(day<1 && min<=59 && min>=30){
             timeTeminder = "30分钟前";
         }else if(day<1 && min<30 && min>=20){
             timeTeminder = "20分钟前";
         }else if(day<1 && min<20 && min>=10){
             timeTeminder = "10分钟前";
         }else if(day<1 && min<15){
             timeTeminder = "刚刚";
         }
         return timeTeminder;
     }

     public static Integer getMonthPoor(Date date1,Date date2){
         long time = diffDateSecond(date1, date2)*1000;
         long day = time / (24 * 60 * 60 * 1000);
         return (int) (day/30);
     }

     /**
      * 根据时间长度获取小时，分钟，秒数
      * @param time
      * @return
      */
     public static String acquireTimeToStrng(Long time){
         long day = time / (24 * 60 * 60 * 1000);
         long hour = (time / (60 * 60 * 1000) - day * 24);
         long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
         long s = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
         long ms = (time - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                 - min * 60 * 1000 - s * 1000);
         StringBuffer sb = new StringBuffer();
         log.info(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
                 + "毫秒");
         if(!StringUtil.isEmpty(hour)){
             sb.append(hour + "小时");
         }
         if(!StringUtil.isEmpty(min)){
             sb.append(min + "分");
         }
         if(!StringUtil.isEmpty(s)){
             sb.append(s + "秒");
         }
         if(StringUtil.isEmpty(sb.toString().trim().length())){
             sb.append("无使用记录");
         }
         return sb.toString();
     }

     public static void main(String[] args) {
         long time = 1758762l;//diffDateSecond(new Date(), getDateByString("2014-01-01 12:12:12","yyyy-MM-dd HH:mm:ss"))*1000;
         long day = time / (24 * 60 * 60 * 1000);
         long hour = (time / (60 * 60 * 1000) - day * 24);
         long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
         long s = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
         long ms = (time - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                 - min * 60 * 1000 - s * 1000);
         System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
                 + "毫秒");
         System.out.println(timeTeminder(new Date(), new Date()));
         //ThumbnailTool
         try {
             DateUtil.class.newInstance();
             Date alarm_time = getNextAlarmTime("12:34", "7".split(","));
             String alarm_time_str = formatDateTime(alarm_time, "yyyy-MM-dd HH:mm:ss");
             System.out.println(alarm_time_str);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 }

