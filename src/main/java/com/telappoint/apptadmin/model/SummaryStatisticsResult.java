package com.telappoint.apptadmin.model;

import java.util.Map;

/**
 * @author Balaji 
 * 
 */

public class SummaryStatisticsResult  {
	private String day;
	private String week;
	private String month;
	private String monthName;
	private int totalAppointments;
	private Map<Integer,Integer> apptStatusWithApptCount;

	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getTotalAppointments() {
		return totalAppointments;
	}
	public void setTotalAppointments(int totalAppointments) {
		this.totalAppointments = totalAppointments;
	}
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public Map<Integer,Integer> getApptStatusWithApptCount() {
		return apptStatusWithApptCount;
	}
	public void setApptStatusWithApptCount(Map<Integer,Integer> apptStatusWithApptCount) {
		this.apptStatusWithApptCount = apptStatusWithApptCount;
	}	
}