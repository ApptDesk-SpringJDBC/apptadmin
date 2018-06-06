package com.telappoint.apptadmin.service;

import com.telappoint.apptadmin.form.CalendarRequest;
import com.telappoint.apptadmin.model.MonthlyCalendar;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarData;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarResponse;

import java.util.List;


public interface MonthlyCalenderService {

    MonthlyCalendar getMonthlyCalendar(MonthlyCalendarResponse response, CalendarRequest calendarRequest);

}
