package com.stylefeng.guns.rest.way.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;

/**
 * Time Util
 * 
 * @author dyson
 * @since 2014-09-04
 * 
 */
public class TimeUtil {

	/**
	 * get Timestamp By Any Days
	 * 
	 * @return 2015-03-14 20:30:24.92 Timestamp
	 */
	public static Timestamp getTimestampByAnyDays(int days) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Calendar cc = Calendar.getInstance();
		cc.setTime(now);
		cc.add(Calendar.DAY_OF_MONTH, days);
		return new Timestamp(cc.getTime().getTime());
	}

	/**
	 * get Timestamp By Any Days and Any Timestamp
	 * 
	 * @return 2015-03-14 20:30:24.92 Timestamp
	 */
	public static Timestamp getTimestampByAnyDays(int days, Timestamp now) {
		Calendar cc = Calendar.getInstance();
		cc.setTime(now);
		cc.add(Calendar.DAY_OF_MONTH, days);
		return new Timestamp(cc.getTime().getTime());
	}

	/**
	 * get tomorrow date string
	 * 
	 * @return "yyyy-MM-dd"
	 */
	public static String getTomorrowDateString() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.add(Calendar.DATE, +1);
		String today = data.format(cal.getTime());
		return today;
	}

	/**
	 * get today date string
	 * 
	 * @return "yyyy-MM-dd"
	 */
	public static String getTodayDateString() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String today = data.format(cal.getTime());
		return today;
	}

	/**
	 * 将Date类型时间转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		String time;
		SimpleDateFormat formater = new SimpleDateFormat();
		formater.applyPattern("yyyy-MM-dd");
		time = formater.format(date);
		return time;
	}

	/**
	 * get yesterday date string
	 * 
	 * @return "yyyy-MM-dd"
	 */
	public static String getYesterdayDateString() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.add(Calendar.DATE, -1);
		String today = data.format(cal.getTime());
		return today;
	}

	/**
	 * get the day before yesterday date string
	 * 
	 * @return "yyyy-MM-dd"
	 */
	public static String getDayBeforeYesterdayDateString() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.add(Calendar.DATE, -2);
		String today = data.format(cal.getTime());
		return today;
	}

	/**
	 * get now Month string
	 * 
	 * @return "yyyy-MM"
	 */
	public static String getNowMonthString() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String month = data.format(cal.getTime());

		return month;
	}

	/**
	 * get now time string
	 * 
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getNowTimeString() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String startTime = data.format(cal.getTime());

		return startTime;
	}

	/**
	 * @Description: TODO 获取当前时间日期的后i天
	 * 
	 * @param int i
	 */
	public static String getNumToday(int i) {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.add(Calendar.DATE, i);
		String today = data.format(cal.getTime());
		return today;
	}

	/**
	 * 
	 * 
	 * @Description: TODO Timestamp to String
	 * 
	 * @param Timestamp
	 *            ts
	 * 
	 * @return String
	 * 
	 */
	public static String timestampToStringHql(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tsStr = sdf.format(ts);
		return tsStr;
	}

	/**
	 * get now time timestamp class
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getTimestamp() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String startTime = data.format(cal.getTime());

		Timestamp ts = null;
		try {
			ts = new Timestamp(data.parse(startTime).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * 
	 * 
	 * @Description: TODO get time area：hour of day
	 * 
	 * @param Timestamp
	 *            ts
	 * 
	 * @return int
	 * 
	 * @throws
	 */
	public static int getTimestampHour(Timestamp ts) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * 
	 * @Description: TODO Timestamp to String
	 * 
	 * @param Timestamp
	 *            ts
	 * 
	 * @return String
	 * 
	 */
	public static String timestampToString(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		tsStr = sdf.format(ts);
		return tsStr;
	}

	/**
	 * 
	 * 
	 * @Description: TODO Timestamp to String Date
	 * 
	 * @param Timestamp
	 *            ts
	 * 
	 * @return String
	 * 
	 */
	public static String timestampToStringDate(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		tsStr = sdf.format(ts);
		return tsStr;
	}

	/**
	 * 
	 * 
	 * @Description: TODO Timestamp to Date
	 * 
	 * @param Timestamp
	 *            ts
	 * 
	 * @return String
	 * 
	 */
	public static String timestampToDate(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tsStr = sdf.format(ts);

		return tsStr.substring(0, 10);
	}

	/**
	 * 
	 * 
	 * @Description: TODO Timestamp to time
	 * 
	 * @param Timestamp
	 *            ts
	 * 
	 * @return String
	 * 
	 */
	public static String timestampToTime(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tsStr = sdf.format(ts);
		return tsStr.substring(11, tsStr.length());
	}

	/**
	 * 
	 * 
	 * @Description: TODO 获取当前有效时长的时间毫秒数
	 * 
	 * @param Timestamp
	 *            ts 时间
	 * @param Long
	 *            delayTime 有效时长
	 * @return Long 有效时长的时间毫秒数 默认为当前时间的毫秒数
	 */
	public static Long getExpriedMilliseconds(Timestamp ts, long delayTime) {

		String tsStr = "";

		SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss",
				new Locale("zh", "cn"));
		tsStr = m_format.format(ts);

		SimpleDateFormat timeformat = new SimpleDateFormat(
				"yyyy-MM-dd,HH:mm:ss");
		long t = ts.getTime();
		try {
			t = timeformat.parse(tsStr).getTime() + delayTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 
	 * 
	 * @Description: TODO 门店是否为营业时间
	 * 
	 * @param openTime
	 *            开店时间
	 * @param closeTime
	 *            关店时间
	 * 
	 * 
	 * @return 1：营业中 0：打烊
	 * 
	 * @throws ParseException
	 */
	public static int compareOpen(String openTime, String closeTime)
			throws Exception {

		if (openTime.contains("00:00:00") && closeTime.contains("00:00:00")) {
			return 1;
		}

		if (closeTime.contains("00:00:00")) {
			closeTime = "23:59:59";
		}

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String nowTime = df.format(cal.getTime());

		Date now = df.parse(nowTime);
		Date open = df.parse(openTime);
		Date close = df.parse(closeTime);

		Date zore1 = df.parse("23:59:59");
		Date zore2 = df.parse("00:00:00");

		if (close.getTime() <= open.getTime()) {
			/**
			 * 跨凌晨
			 */
			// openTime 到 23:59:59
			if (now.getTime() >= open.getTime()
					&& now.getTime() <= zore1.getTime()) {
				return 1;
			}

			// 00:00:00 到 closeTime
			if (now.getTime() >= zore2.getTime()
					&& now.getTime() <= close.getTime()) {
				return 1;
			}

		} else {
			/**
			 * 未跨凌晨
			 */
			// openTime 到 closeTime
			if (now.getTime() >= open.getTime()
					&& now.getTime() <= close.getTime()) {
				return 1;
			}
		}

		return 0;
	}

	public static String timeToTimeString(Time ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		tsStr = sdf.format(ts);
		return tsStr.substring(0, tsStr.length());
	}

	public static String timeToShortString(Time ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		tsStr = sdf.format(ts);
		return tsStr.substring(0, tsStr.length());
	}

	/**
	 * String to Timestamp
	 * 
	 * @Description: TODO 时间字符串转Timestamp
	 * 
	 * @param String
	 *            time
	 * 
	 * @return Timestamp
	 */
	public static Timestamp stringToTimestamp(String time) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(time);
		return ts;
	}

	/**
	 * 返回指定年月的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		if (year == null) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == null) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(year, month - 1, 1);

		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(calendar.getTime());

		return firstDayOfMonth;
	}

	/**
	 * 返回指定年月的月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());

		return lastDayOfMonth;
	}

	/**
	 * 传递时间格式，然后减去
	 * 
	 * @param time
	 * @param mm
	 * @return
	 * @throws ParseException
	 */
	public static String timeReduce(String time, int mm) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = sdf.parse(time);
		long l = d1.getTime() + mm * 60 * 1000;
		return sdf.format((new Date(l)));
	}

	/**
	 * 获得日期的时分秒
	 * 
	 * @param ts
	 * @return
	 */
	public static String timestampToDayHour(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		tsStr = sdf.format(ts);
		return tsStr;
	}

	/**
	 * 输入字符串获获得当前是周几
	 * 
	 * @param ts
	 * @return
	 */
	public static String getWeekDayName(String time) {

		Date date = null;
		SimpleDateFormat formater = new SimpleDateFormat();
		formater.applyPattern("yyyy-MM-dd");
		try {
			date = formater.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
		String dateWeek = dateFm.format(date);
		return dateWeek;
	}
	/**
	 * 获得当前星期1,2,3这种形式
	 * @param date
	 * @return
	 */
	public static int getWeekOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (week < 0) {
			week = 0;
		}
		return week;
	}
	
	/**
	 * 将星期一星期二星期三转换成1,2,3这种形式
	 * 
	 * @param week
	 * @return
	 */
	public static int getWeekDayValue(String week) {
		
		int type = 0;
		if (week.equals("星期一")) {
			type = 1;
		} else if (week.equals("星期二")) {
			type = 2;
		} else if (week.equals("星期三")) {
			type = 3;
		} else if (week.equals("星期四")) {
			type = 4;
		} else if (week.equals("星期五")) {
			type = 5;
		} else if (week.equals("星期六")) {
			type = 6;
		} else if (week.equals("星期日")) {
			type = 7;
		}
		return type;
	}


	/**
	 * 获得2017年1月1号格式字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String timeYearMouth(String time) {
		String newTime = "";
		String arr[] = time.split("-");
		newTime = arr[0] + "年" + arr[1] + "月" + arr[2] + "日";
		return newTime;

	}

	/**
	 * 获得日期的时分
	 * 
	 * @param ts
	 * @return
	 */
	public static String timestampHourMinute(Timestamp ts) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		tsStr = sdf.format(ts);
		return tsStr;
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Time StringToTime(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Time time = null;
		try {
			time = new Time(sdf.parse(dateStr).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 如果i>0 startTime>endTime
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static int compareTime(String startTime, String endTime)
			throws Exception {
		startTime = "2017-02-22 " + startTime;

		endTime = "2017-02-22 " + endTime;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date dateTime1 = dateFormat.parse(startTime);

		Date dateTime2 = dateFormat.parse(endTime);

		int i = dateTime1.compareTo(dateTime2);
		return i;
	}

	public static Time stringToTime(String timeStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Time btime = new Time(sdf.parse(timeStr).getTime());
			return btime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将2017-3-4转成2017-03-04
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String stringTimeToString(String time) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(time);
		SimpleDateFormat formater = new SimpleDateFormat();
		formater.applyPattern("yyyy-MM-dd");
		String time1 = formater.format(date);
		return time1;
	}
	/**
	 * 获得指定日期的上周周末
	 * @param date
	 * @return
	 */
	public static String getLastWeekMonday(Date date) {
		Date a = DateUtils.addDays(date, -1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(a);
		cal.add(Calendar.WEEK_OF_YEAR, 0);// 一周
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormater.format(cal.getTime());

	}
	/**
	 * 判断当前日期是星期几<br>
	 * <br>
	 * 
	 * @param pTime
	 *            修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public static String dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		if (dayForWeek == 1) {
			return "星期一";
		} else if (dayForWeek == 2) {
			return "星期二";
		} else if (dayForWeek == 3) {
			return "星期三";
		} else if (dayForWeek == 4) {
			return "星期四";
		} else if (dayForWeek == 5) {
			return "星期五";
		} else if (dayForWeek == 6) {
			return "星期六";
		} else {
			return "星期日";
		}
	}
	/**
	 * 	 * TODO 判断今天是否为1号
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean isLastDayOfMonth(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE)));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}
	/**
	 * 判断注册天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {

	       if (null == fDate || null == oDate) {

	           return -1;

	       }

	       long intervalMilli = oDate.getTime() - fDate.getTime();

	       return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	    }
	/**
	 * 获得上个月最后一天
	 * @param date
	 * @return
	 */
	public static String getLastMonthMonday(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		    Calendar cale = Calendar.getInstance();   
		      cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
		      String lastDay  = format.format(cale.getTime());
		      
		         
		      return  lastDay;
	}
	/**
	 * 相差天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }  
}