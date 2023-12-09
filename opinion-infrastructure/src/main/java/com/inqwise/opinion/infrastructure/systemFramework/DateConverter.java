/**
 * 
 */
package com.inqwise.opinion.infrastructure.systemFramework;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @author basil
 * 
 */
public class DateConverter {

	/**
	 * 
	 */
	private static Format displayDateFormatter = new SimpleDateFormat("dd-MMM-yy");
	
	public static Calendar parseTimestamp(String timestamp) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US);
		Date date = dateFormat.parse(timestamp);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String getDate(String date, String format) throws Exception {
		SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(format);
		return dateFormat.format(parseTimestamp(date).getTime());
	}

	public static String getDate(Date date, String format) throws Exception {
		java.sql.Timestamp timestampDate = new Timestamp(date.getTime());
		SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(format);
		return dateFormat.format(parseTimestamp(timestampDate.toString()).getTime());
	}
	
	public static Date trim(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return trim(cal);
	}
	
	public static Date trim(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date shiftToTheEndOfTheDay(Date date) {
		if(null == date)return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return shiftToTheEndOfTheDay(calendar);
	}

	public static Date shiftToTheEndOfTheDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date shiftToTheEndOfTheMonth(Date date) {
		if(null == date)return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return shiftToTheEndOfTheDay(calendar);
	}

	public static Date shiftToTheEndOfTheMonthOrToday(Date date) {
		if(null == date)return null;
		Date result;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		
		Calendar today = Calendar.getInstance();
		if(calendar.after(today)){
			result = shiftToTheEndOfTheDay(today);
		} else {
			result = calendar.getTime(); 
		}
		return result;
	}
	
	public static Date shiftToTheStartOfTheMonth(Date date) {
		if(null == date)return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return trim(calendar);
	}
	
	public static Date addDateOffset(Date date, Integer offsetInMiliseconds){
		Date result = date;
		if(null != offsetInMiliseconds && null != date){
			result = DateUtils.addMilliseconds(date, offsetInMiliseconds); 
		}
		
		return result;
	}
	
	public static Date removeDateOffset(Date date, Integer offsetInMiliseconds){
		Date result = date;
		if(null != offsetInMiliseconds && null != date){
			result = DateUtils.addMilliseconds(date, -1 * offsetInMiliseconds); 
		}
		
		return result;
	}

	private static ConcurrentHashMap<String, SimpleDateFormat> dateFormatters = new ConcurrentHashMap<>();
	public static String getDateFormat(Date date, String format) {
		if(null == date || null == format){
			return null;
		} else {
			SimpleDateFormat formatter = null;
			if(null == (formatter = dateFormatters.get(format))){
				formatter = new SimpleDateFormat(format);
				dateFormatters.putIfAbsent(format, formatter);
			}
			
			return formatter.format(date);
		}
	}
}
