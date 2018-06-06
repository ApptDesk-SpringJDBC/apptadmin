package com.telappoint.apptadmin.model;

import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarResponse;

/**
 * 
 * @author Murali G
 *
 */
public class CalendarSessionData {

	private DailyCalendarResponse dailyCalendarResponse  = null;
	private WeeklyCalendarResponse weeklyCalendarResponse = null;
	private MonthlyCalendarResponse monthlyCalendarResponse = null;
	
	public DailyCalendarResponse getDailyCalendarResponse() {
		return dailyCalendarResponse;
	}
	public void setDailyCalendarResponse(DailyCalendarResponse dailyCalendarResponse) {
		this.dailyCalendarResponse = dailyCalendarResponse;
	}
	public WeeklyCalendarResponse getWeeklyCalendarResponse() {
		return weeklyCalendarResponse;
	}
	public void setWeeklyCalendarResponse(
			WeeklyCalendarResponse weeklyCalendarResponse) {
		this.weeklyCalendarResponse = weeklyCalendarResponse;
	}
	public MonthlyCalendarResponse getMonthlyCalendarResponse() {
		return monthlyCalendarResponse;
	}
	public void setMonthlyCalendarResponse(
			MonthlyCalendarResponse monthlyCalendarResponse) {
		this.monthlyCalendarResponse = monthlyCalendarResponse;
	}
}