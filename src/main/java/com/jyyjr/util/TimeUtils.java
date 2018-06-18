package com.jyyjr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	/**
	 * 用时间戳获取小时
	 * @param time
	 * @return
	 */
	public static int getHour(long time){
		long ctime = time*1000L;
	    Date date = new Date(ctime);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	    String strTime = sdf.format(date);
	    int h = Integer.parseInt(strTime.substring(11, 13));
	    int m = Integer.parseInt(strTime.substring(14, 16));
	    int s = Integer.parseInt(strTime.substring(17));
	    if(m==0 && s==0){
	    	return h;
	    }else{
	    	return h+1;
	    }
	}
	
	/**
	 * 用时间戳获取相应格式时间
	 * @param time
	 * @return
	 */
	public static String dateTime(long time,String sdfDate){
		long ctime = time*1000L;
	    Date date = new Date(ctime);
	    SimpleDateFormat sdf = new SimpleDateFormat(sdfDate);
	    String strTime = sdf.format(date);
	    return strTime;
	}
	
	
	/**
	 * 根据日期格式获取时间戳(秒)
	 * @param time
	 * @return
	 */
	public static long timeStamp(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long ctime =0;
		try {
			Date date = sdf.parse(time);
			ctime = date.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ctime;
		
	}
	
	/**
	 * 获取当天零点的秒数
	 * @return
	 */
	public static long zeroPoint(long ctime){
		long current=ctime*1000;//当前时间毫秒数  
        //long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date = new Date(current);
		String time = sdf.format(date);
		long t1= 0;
		try {
			Date zero = sdf.parse(time);
			t1 = zero.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return t1;
	}
	
	/**
	 * 获取指定日期当天的零点秒数
	 * @return
	 */
	public static long getZeroPoint(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long time = 0;
		try {
			Date d = sdf.parse(date);
			time = d.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	
	/**
	 * 获取时间差（天数）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDayReduce(String startTime,String endTime){
		Long sTime = Long.parseLong(startTime)*1000L;
		Long eTime = Long.parseLong(endTime)*1000L;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date sDate = new Date(sTime);
		Date eDate = new Date(eTime);
		String time1 = sdf.format(sDate);
		String time2 = sdf.format(eDate);
		long t1=0;
		long t2=0;
		try {
			Date zero1 = sdf.parse(time1);
			Date zero2 = sdf.parse(time2);
			t1 = zero1.getTime();
			t2 = zero2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) ((t2-t1)/86400000);
	}
	
	/**
	 * 获取两个日期差的月份数
	 * @param maxTime
	 * @param minTime
	 * @return
	 */
	public static int differenceMonth(String maxTime,String minTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
			bef.setTime(sdf.parse(maxTime));
			aft.setTime(sdf.parse(minTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        int num = Math.abs(month + result);
        if (num==0) {
			num =1;
		}
        return num;
	}
	
	/**
	 * 向前推几个月
	 * @param time
	 * @param month
	 * @return
	 */
	public static long reduceDate(long time,int month) {
		long startTime = time*1000;
		Date date = new Date(startTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MONTH, -month);
		Date updateDate2 = calendar.getTime();
		long nowTime = updateDate2.getTime()/1000;
		return nowTime;
		
	}
	
	public static void main(String[] args) {
		TimeUtils ct = new TimeUtils();
		//获取小时
		int h = getHour(1498047125);
		//获取日期格式
		String strTime = dateTime(1524720621,"yyyy-MM-dd");
		//获取时间戳
		long ctime = timeStamp("2017-08-31 23:00:00");
		//获取当天零点的毫秒数
		long zeroTime = zeroPoint(1525673591);
		//获取时间差天数
		int dayReduce = getDayReduce("1501346425", "1505209156");
		//获取指定日期当天的零点毫秒数
		long time = getZeroPoint("2017-11-01");
		
		int num = differenceMonth("2018-03-31","2018-03-14");
		long reduceDate = reduceDate(1525622400,6);
		System.out.println(reduceDate);
	}
}
