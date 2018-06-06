package com.telappoint.apptadmin.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Murali G
 * 
 */
public class DateUtils {

	public static String DATETIME_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	public static String TIME_FORMAT_HHMMSS = "hh:mm:ss";
	public static String TIME_FORMAT_HHMMSS_CAP = "HH:mm:ss";
	public static String TIME_FORMAT_TWELVE_HRS = "hh:mm a";
	public static String TIME_FORMAT_TWENTY_FOUR_HRS = "HH:mm";
	public static String DATE_FORMAT_DDMMMYYYY = "dd-MMM-yyyy";
	public static String DATE_FORMAT_YYYYDDMM = "yyyy-dd-MM";
	public static String DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy";
	public static String DATE_FORMAT_DDMMYYYY = "dd-MM-yyyy";
	public static String DATE_FORMAT_EEEEMMMDDYYYY = "EEEE, MMM dd, yyyy";
	public static String TIME_FORMAT_HHMM_CAP = "HH:mm";
	
	private static final Logger logger = Logger.getLogger(DateUtils.class);

	private static Map<String, ThreadLocal<DateFormat>> tlDateFormatMap = new HashMap<String, ThreadLocal<DateFormat>>();

	public static String convert12To24HoursFormat(String twelveHourTime) throws ParseException {
		ThreadLocal<DateFormat> time12Format = getSimpleDateFormat(TIME_FORMAT_TWELVE_HRS);
		ThreadLocal<DateFormat> time24Format = getSimpleDateFormat(TIME_FORMAT_TWENTY_FOUR_HRS);
		return time24Format.get().format(time12Format.get().parse(twelveHourTime));
	}

	public static String convert24To12HoursFormat(String twentyFourHourTime) throws ParseException {
		ThreadLocal<DateFormat> time24Format = getSimpleDateFormat(TIME_FORMAT_TWENTY_FOUR_HRS);
		ThreadLocal<DateFormat> time12Format = getSimpleDateFormat(TIME_FORMAT_TWELVE_HRS);
		return time12Format.get().format(time24Format.get().parse(twentyFourHourTime));
	}

	public static ThreadLocal<DateFormat> getSimpleDateFormat(String dateTimeFormatStr) {
		ThreadLocal<DateFormat> tldf = null;
		try {
			if (tlDateFormatMap.containsKey(dateTimeFormatStr)) {
				tldf = tlDateFormatMap.get(dateTimeFormatStr);
				return tldf;
			}
			tldf = getThreadLocal(dateTimeFormatStr);
			tlDateFormatMap.put(dateTimeFormatStr, tldf);
			return tldf;
		} catch (Exception e) {
			logger.error("Error:" + e, e);
		}
		return tldf;
	}

	public static ThreadLocal<DateFormat> getThreadLocal(final String dateTimeForamtStr) {
		final ThreadLocal<DateFormat> tldf_ = new ThreadLocal<DateFormat>() {
			@Override
			protected DateFormat initialValue() {
				return new SimpleDateFormat(dateTimeForamtStr);
			}
		};
		return tldf_;
	}
	
	public static String getStringFromDate(Date date, String formate) {
		String dateTimeString = "";
		if (date != null) {
			try {
				ThreadLocal<DateFormat> dateFormat = getSimpleDateFormat(formate);
				dateTimeString = dateFormat.get().format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dateTimeString;
	}
	
	public static Date getDateFromString(String dateTimeString, String formate) {
		Date date = null;
		if (dateTimeString != null) {
			try {
				ThreadLocal<DateFormat> dateFormat = getSimpleDateFormat(formate);
				date = dateFormat.get().parse(dateTimeString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	public static Date addDays(Date date, int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static String convert12To24HoursHHMMSSFormat(String twelveHourTime) throws ParseException {
		ThreadLocal<DateFormat> time12Format = getSimpleDateFormat(TIME_FORMAT_TWELVE_HRS);
		ThreadLocal<DateFormat> time24Format = getSimpleDateFormat(TIME_FORMAT_HHMMSS_CAP);
		return time24Format.get().format(time12Format.get().parse(twelveHourTime));
	}
	
	public static String convert12To24HoursHHMMFormat(String twelveHourTime) throws ParseException {
		ThreadLocal<DateFormat> time12Format = getSimpleDateFormat(TIME_FORMAT_TWELVE_HRS);
		ThreadLocal<DateFormat> time24Format = getSimpleDateFormat(TIME_FORMAT_HHMM_CAP);
		return time24Format.get().format(time12Format.get().parse(twelveHourTime));
	}
	
	public static String getMonthStartDate(String format) throws ParseException {
	   LocalDate today = LocalDate.now();
	   DateTimeFormatter formatters = DateTimeFormatter.ofPattern(format);
	   return today.withDayOfMonth(1).format(formatters); 
	}
	
	public static String getMonthEndDate(String format) throws ParseException {
	   LocalDate today = LocalDate.now();
	   DateTimeFormatter formatters = DateTimeFormatter.ofPattern(format);
	   return today.withDayOfMonth(today.lengthOfMonth()).format(formatters);
	}
	
	public static void main(String... args) throws Exception {
		System.out.println("getMonthStartDate :: " + getMonthStartDate(DATE_FORMAT_MMDDYYYY));
		System.out.println("getMonthEndDate :: " + getMonthEndDate(DATE_FORMAT_MMDDYYYY));		   
	}
}
