package com.centaline.trans.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 日期格式化，默认日期格式日期格式化，默认日期格式yyyy-MM-dd
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatDate(Date date, String format) {
		if(format == null) {
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/**
	 * 将符合格式的时间字符串转换成Date
	 * 
	 * @param str
	 * @return Date
	 * 
	 * @author 
	 */
	public static Date strToFullDate(String str) {
		if(str == null || str.intern().length()==0) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 *  加上多少个月
	 * 
	 * 
	 */
	public static Date plusMonth(Date date ,int month) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");//格式化对象
		  Calendar calendar = Calendar.getInstance();//日历对象
		  calendar.setTime(date);//设置当前日期
		  calendar.add(Calendar.MONTH, month);//月份减一
		  
		  String str =  sdf.format(calendar.getTime());
		  try {
			return sdf.parse(str);
		  } catch (ParseException e) {
			e.printStackTrace();
		  }
		  return null;
	}
	public static Date getFirstDayOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);//设置当前日期
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	public static Date getFirstDayOfTheMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.plusMonth(new Date(), -1));
	}
}
