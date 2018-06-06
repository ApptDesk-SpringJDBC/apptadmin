package com.telappoint.apptadmin.model.monthlycalendar;


import com.telappoint.apptadmin.model.ResourceData;

import java.util.List;

public class MonthlyCalendarData {

    private boolean notAvailable;
    private String calendarDate;
    private int calendarDayOfWeek;
    private int calendarWeekOfMonth;
    private int weeksInMonth;
    private boolean createNewRow = false;
    private boolean createClosingTag;
    private int day;
    private boolean disabledField;
    private int resourceColorId;
    private String cellCssColorKey;
    private List<ResourceData> resourceDataList;

    public String getCellCssColorKey() {
        return cellCssColorKey;
    }

    public void setCellCssColorKey(final String cellCssColorKey) {
        this.cellCssColorKey = cellCssColorKey;
    }

    public boolean isDisabledField() {
        return disabledField;
    }

    public void setDisabledField(final boolean disabledField) {
        this.disabledField = disabledField;
    }

    public boolean isCreateClosingTag() {
        return createClosingTag;
    }

    public void setCreateClosingTag(final boolean createClosingTag) {
        this.createClosingTag = createClosingTag;
    }

    public boolean getCreateNewRow() {
        return createNewRow;
    }

    public void setCreateNewRow(final boolean createNewRow) {
        this.createNewRow = createNewRow;
    }

    public int getDay() {
        return day;
    }

    public void setDay(final int day) {
        this.day = day;
    }

    public boolean isNotAvailable() {
        return notAvailable;
    }

    public void setNotAvailable(final boolean notAvailable) {
        this.notAvailable = notAvailable;
    }

    public int getWeeksInMonth() {
        return weeksInMonth;
    }

    public void setWeeksInMonth(final int weeksInMonth) {
        this.weeksInMonth = weeksInMonth;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(final String calendarDate) {
        this.calendarDate = calendarDate;
    }

    public int getCalendarDayOfWeek() {
        return calendarDayOfWeek;
    }

    public void setCalendarDayOfWeek(final int calendarDayOfWeek) {
        this.calendarDayOfWeek = calendarDayOfWeek;
    }

    public int getCalendarWeekOfMonth() {
        return calendarWeekOfMonth;
    }

    public void setCalendarWeekOfMonth(final int calendarWeekOfMonth) {
        this.calendarWeekOfMonth = calendarWeekOfMonth;
    }

    public int getResourceColorId() {
        return resourceColorId;
    }

    public void setResourceColorId(final int resourceColorId) {
        this.resourceColorId = resourceColorId;
    }


    public List<ResourceData> getResourceDataList() {
        return resourceDataList;
    }

    public void setResourceDataList(final List<ResourceData> resourceDataList) {
        this.resourceDataList = resourceDataList;
    }
}
