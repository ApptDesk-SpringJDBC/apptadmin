package com.telappoint.apptadmin.model;

public class HoldAppt {
    private Long scheduleId;
    private String errorFlag;
    private String errorMessage;
    private String displayDateTime;
    private boolean status = true;
    
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
	public String getDisplayDateTime() {
        return displayDateTime;
    }

    public void setDisplayDateTime(String displayDateTime) {
        this.displayDateTime = displayDateTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public HoldAppt(Long scheduleId, String displayDateTime, String errorMessage) {
        this.scheduleId = scheduleId;
        this.displayDateTime = displayDateTime;
        this.errorMessage = errorMessage;
    }


    public HoldAppt(Long scheduleId, String displayDateTime, String errorMessage, boolean status) {
        this.scheduleId = scheduleId;
        this.displayDateTime = displayDateTime;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    @Override
    public String toString() {
        return "HoldAppt [scheduleId=" + scheduleId + ", errorMessage=" + errorMessage + ", displayDateTime=" + displayDateTime + "]";
    }

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
}