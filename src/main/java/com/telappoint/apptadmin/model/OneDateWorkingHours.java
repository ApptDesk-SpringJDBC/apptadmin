package com.telappoint.apptadmin.model;

public class OneDateWorkingHours {	
	
	private boolean dayOpen;
	private boolean breakTimeOpen;
	private String selectedStartTime;
	private String selectedEndTime;
	private String selectedBreakTime;
	private int selectedDuration;
	
	public boolean isDayOpen() {
		return dayOpen;
	}
	public void setDayOpen(boolean dayOpen) {
		this.dayOpen = dayOpen;
	}
	public boolean isBreakTimeOpen() {
		return breakTimeOpen;
	}
	public void setBreakTimeOpen(boolean breakTimeOpen) {
		this.breakTimeOpen = breakTimeOpen;
	}
	public String getSelectedStartTime() {
		return selectedStartTime;
	}
	public void setSelectedStartTime(String selectedStartTime) {
		this.selectedStartTime = selectedStartTime;
	}
	public String getSelectedEndTime() {
		return selectedEndTime;
	}
	public void setSelectedEndTime(String selectedEndTime) {
		this.selectedEndTime = selectedEndTime;
	}
	public String getSelectedBreakTime() {
		return selectedBreakTime;
	}
	public void setSelectedBreakTime(String selectedBreakTime) {
		this.selectedBreakTime = selectedBreakTime;
	}
	public int getSelectedDuration() {
		return selectedDuration;
	}
	public void setSelectedDuration(int selectedDuration) {
		this.selectedDuration = selectedDuration;
	}
}
