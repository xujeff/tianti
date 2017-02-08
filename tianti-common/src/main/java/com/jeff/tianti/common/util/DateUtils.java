package com.jeff.tianti.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils{
	
private final static Log LOG = LogFactory.getLog(DateUtils.class);
	
	public final static DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public final static DateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final static DateFormat serialFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public final static DateFormat dfLessonDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public final static DateFormat dfHm = new SimpleDateFormat("HH:mm");
	
	public final static DateFormat dfYMD = new SimpleDateFormat("yyyyMMdd");
	
	public final static DateFormat dfYMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 查询开始时间
	 * @param source
	 * @return
	 */
	public static Date getStartDate(String source){
		if(StringUtils.isNotBlank(source)){
			source = source.trim() + " 00:00:00";
			return parseDateTime(source);
		}
		return null;
	}
	
	public static Date getStartDate(Date date){
		String source = dfDate.format(date);
		return getStartDate(source);
	}
	
	/**
	 * 查询结束时间
	 * @param source
	 * @return
	 */
	public static Date getEndDate(String source){
		if(StringUtils.isNotBlank(source)){
			source = source.trim() + " 23:59:59";
			return parseDateTime(source);
		}
		return null;
	}
	
	public static Date getEndDate(Date date){
		String source = dfDate.format(date);
		return getEndDate(source);
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public static Date parseDateTime(String source){
		if(StringUtils.isBlank(source)){
			return null;
		}
		Date date = null;
		try {
			date = dfDateTime.parse(source.trim());
		} catch (ParseException e) {
			LOG.error("解析时间异常：["+source+"] " + e.getMessage());
		}
		return date;
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public static Date parseDate(String source){
		if(StringUtils.isBlank(source)){
			return null;
		}
		Date date = null;
		try {
			date = dfDate.parse(source.trim());
		} catch (ParseException e) {
			LOG.error("解析时间异常：["+source+"] " + e.getMessage());
		}
		return date;
	}
	
	/**
	 * 
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String source, String pattern){
		if(StringUtils.isBlank(source)){
			return null;
		}
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(source.trim());
		} catch (ParseException e) {
			LOG.error("解析时间异常：["+source+"] " + e.getMessage());
		}
		return date;
	}
	
	/**
	 * 根据当前时间生成序列号
	 * @return
	 */
	public synchronized static String serialNumber(){
		return serialFormatter.format(new Date());
	}

	/**
	 * 月第一天
	 * @param date
	 * @return
	 */
	public static Date getMonthStartDate(Date date){
		Date d = new Date(date.getTime());
		d = setHours(d, 0);
		d = setMinutes(d, 0);
		d = setSeconds(d, 1);
		return setDays(d, 1);
	}
	
	/**
	 * 月最后一天
	 * @param date
	 * @return
	 */
	public static Date getMonthEndDate(Date date){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		int maxMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, maxMonth);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
	
	/**
	 * 当前星期的第一天
	 * @param date
	 * @return
	 */
	public static Date getWeekStartDate(Date date){
		GregorianCalendar c = new GregorianCalendar(Locale.CHINA);
		c.setTime(date);
		c.setFirstDayOfWeek(RANGE_WEEK_MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		return c.getTime();
	}
	
	/**
	 * 当前星期的最后一天
	 * @param date
	 * @return
	 */
	public static Date getWeekEndDate(Date date){
		GregorianCalendar c = new GregorianCalendar(Locale.CHINA);
		c.setTime(date);
		c.setFirstDayOfWeek(RANGE_WEEK_MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
	
	public static Date getLessonDate(String source){
		if(StringUtils.isBlank(source)){
			return null;
		}
		Date date = null;
		try {
			date = dfLessonDate.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 两个时间之间相差的月
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(Date startDate, Date endDate){

        int result = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(startDate);
        c2.setTime(endDate);

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return result;

    }
	
	/**
	 * 日期间隔多少天
	 * @param early
	 * @param late
	 * @return
	 */
	public static final int daysBetween(Date early, Date late) { 
	     
        java.util.Calendar calst = java.util.Calendar.getInstance();   
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        calst.setTime(early);   
        caled.setTime(late);   
         //设置时间为0时   
         calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         calst.set(java.util.Calendar.MINUTE, 0);   
         calst.set(java.util.Calendar.SECOND, 0);   
         caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         caled.set(java.util.Calendar.MINUTE, 0);   
         caled.set(java.util.Calendar.SECOND, 0);   
        //得到两个日期相差的天数   
         int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst   
                .getTime().getTime() / 1000)) / 3600 / 24;   
         
        return days;   
   } 

	public static void main(String [] args){
		try {
			Date currentDate = dfDateTime.parse("2016-10-16 14:19:40");
			Date startDate = DateUtils.getWeekStartDate(currentDate);
			Date endDate = DateUtils.getWeekEndDate(currentDate);
			System.out.println(startDate + "--" + endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
