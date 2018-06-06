package com.telappoint.apptadmin.model;

public class CalendarAvailability {

    private String date;
    private String availabilityStr;
    private String toolTipMessage;

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getAvailabilityStr() {
        return availabilityStr;
    }

    public void setAvailabilityStr(final String availabilityStr) {
        this.availabilityStr = availabilityStr;
    }

    public String getToolTipMessage() {
        return toolTipMessage;
    }

    public void setToolTipMessage(final String toolTipMessage) {
        this.toolTipMessage = toolTipMessage;
    }
}
