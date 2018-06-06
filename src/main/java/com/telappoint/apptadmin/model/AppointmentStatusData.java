package com.telappoint.apptadmin.model;

public class AppointmentStatusData {
	
	private Integer id;
	private String status;
	private Integer statusVal;
	private String denied;
	private String reportDisplay;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getStatusVal() {
		return statusVal;
	}
	public void setStatusVal(Integer statusVal) {
		this.statusVal = statusVal;
	}
	public String getDenied() {
		return denied;
	}
	public void setDenied(String denied) {
		this.denied = denied;
	}
	public String getReportDisplay() {
		return reportDisplay;
	}
	public void setReportDisplay(String reportDisplay) {
		this.reportDisplay = reportDisplay;
	}
	
}
