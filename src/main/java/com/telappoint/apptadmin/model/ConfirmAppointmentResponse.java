package com.telappoint.apptadmin.model;

public class ConfirmAppointmentResponse extends BaseResponse {
	private Long confirmation;
	private String displayKeys;
	private String displayValues;
	
	public Long getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(Long confirmation) {
		this.confirmation = confirmation;
	}
	
	public String getDisplayValues() {
		return displayValues;
	}
	public void setDisplayValues(String displayValues) {
		this.displayValues = displayValues;
	}
	public String getDisplayKeys() {
		return displayKeys;
	}
	public void setDisplayKeys(String displayKeys) {
		this.displayKeys = displayKeys;
	}
}
