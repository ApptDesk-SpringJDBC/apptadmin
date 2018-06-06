package com.telappoint.apptadmin.model;


import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarData;

import java.util.List;

public class MonthlyCalendar {

    private List<MonthlyCalendarData> monthlyCalendarDataList;

    private List<List<MonthlyCalendarData>> monthlyCalendarDataRowsColumns;

    private String currentDate;

    private boolean showAllResourceCount;

    public boolean isShowAllResourceCount() {
        return showAllResourceCount;
    }

    public void setShowAllResourceCount(final boolean showAllResourceCount) {
        this.showAllResourceCount = showAllResourceCount;
    }

    public List<List<MonthlyCalendarData>> getMonthlyCalendarDataRowsColumns() {
        return monthlyCalendarDataRowsColumns;
    }

    public void setMonthlyCalendarDataRowsColumns(final List<List<MonthlyCalendarData>> monthlyCalendarDataRowsColumns) {
        this.monthlyCalendarDataRowsColumns = monthlyCalendarDataRowsColumns;
    }

    public void setMonthlyCalendarDataList(final List<MonthlyCalendarData> monthlyCalendarDataList) {
        this.monthlyCalendarDataList = monthlyCalendarDataList;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(final String currentDate) {
        this.currentDate = currentDate;
    }

}
