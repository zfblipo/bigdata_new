package com.xingnext.bigdata.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

	// 将时间戳转为字符串
	public static String getStrTime(String cc_time) {
		if ("null".equals(cc_time) || "".equals(cc_time)) {
			return "0000-00-00";
		}
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	// 将时间戳转为字符串
	public static String getYMDHMTime(String cc_time) {
		if ("null".equals(cc_time) || "".equals(cc_time)) {
			return "0000-00-00 00:00";
		}
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm",Locale.CHINA);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	// 将时间戳转为字符串
	public static String getMDHMTime(String cc_time) {
		if ("null".equals(cc_time) || "".equals(cc_time)) {
			return "00-00 00:00";
		}
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm",Locale.CHINA);
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	// 将时间戳转为字符串
	public static String getMDTime(String cc_time) {
		if ("null".equals(cc_time) || "".equals(cc_time)) {
			return "MM月dd日";
		}
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}
	
	/*将字符串转为时间戳*/
    public static String getStringToCurrlut(String time) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    	
    	Date date = null;
    	try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return (date.getTime()/1000L)+"";
    }

	/*将字符串转为Date*/
	public static Date getStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/*将字符串转为Date*/
	public static Calendar stringToCalendar(String time) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

		Date date = null;
		try {
			date = sdf.parse(time);
			calendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}

	// 将时间戳转为字符串
	public static String getYMDTime(String cc_time) {
		if ("null".equals(cc_time) || "".equals(cc_time)) {
			return "yyyy年MM月dd日";
		}
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	public static String getNowDate(){
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		re_StrTime = sdf.format(new Date());

		return re_StrTime;
	}

	public static String getYMDFromDate(Date date){
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		re_StrTime = sdf.format(date);

		return re_StrTime;
	}

	public static String getYMDFromCalender(Calendar calendar){
		String re_StrTime = null;
		if(calendar == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		re_StrTime = sdf.format(calendar.getTime());

		return re_StrTime;
	}

}
