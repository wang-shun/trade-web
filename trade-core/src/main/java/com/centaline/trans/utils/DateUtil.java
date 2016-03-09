package com.centaline.trans.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
