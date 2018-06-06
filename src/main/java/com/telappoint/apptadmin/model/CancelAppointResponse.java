package com.telappoint.apptadmin.model;

public class CancelAppointResponse extends BaseResponse {
	private boolean isCancelled;
	private String displayKeys;
	private String displayValues;
	

	public boolean isCancelled() {
		return isCancelled;
	}
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	public String getDisplayKeys() {
		return displayKeys;
	}
	public void setDisplayKeys(String displayKeys) {
		this.displayKeys = displayKeys;
	}
	public String getDisplayValues() {
		return displayValues;
	}
	public void setDisplayValues(String displayValues) {
		this.displayValues = displayValues;
	}
}
