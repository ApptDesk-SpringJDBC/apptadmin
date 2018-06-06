package com.telappoint.apptadmin.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResourceWorkingHoursRequest {
	private String clientCode;
	private boolean dayOpen;
	private boolean breakTimeOpen;
	private List<String> dates;
	private String selectedStartTime;
	private String selectedEndTime;
	private String selectedBreakTime;
	private Integer selectedDuration;
	private List<Integer> selectedResourceIds;
	private boolean continueUpdate;
	private String notifyCheckBox;
	private String userName;
	private Integer locationId;
	
	//One Day Related
    @JsonIgnore
    private boolean dayOpenClose;  
    @JsonIgnore
    private String dayStartTimeHr;
    @JsonIgnore
    private String dayStartTimeMin;
    @JsonIgnore
    private String dayStartTimeAmPm;
    @JsonIgnore
    private String dayEndTimeHr;
    @JsonIgnore
    private String dayEndTimeMin;
    @JsonIgnore
    private String dayEndTimeAmPm;
    @JsonIgnore
    private boolean dayBreakTimeOpenClose;
    @JsonIgnore
    private String dayBreakTimeHr;
    @JsonIgnore
    private String dayBreakTimeMin;
    @JsonIgnore
    private String dayBreakTimeAmPm;
    @JsonIgnore
    private String dayBreakDuration; 
    
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
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	
	public List<Integer> getSelectedResourceIds() {
		return selectedResourceIds;
	}
	public void setSelectedResourceIds(List<Integer> selectedResourceIds) {
		this.selectedResourceIds = selectedResourceIds;
	}
	public boolean isContinueUpdate() {
		return continueUpdate;
	}
	public void setContinueUpdate(boolean continueUpdate) {
		this.continueUpdate = continueUpdate;
	}
	public String getNotifyCheckBox() {
		return notifyCheckBox;
	}
	public void setNotifyCheckBox(String notifyCheckBox) {
		this.notifyCheckBox = notifyCheckBox;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setSelectedDuration(Integer selectedDuration) {
		this.selectedDuration = selectedDuration;
	}
	public boolean isDayOpenClose() {
		return dayOpenClose;
	}
	public void setDayOpenClose(boolean dayOpenClose) {
		this.dayOpenClose = dayOpenClose;
	}
	public String getDayStartTimeHr() {
		return dayStartTimeHr;
	}
	public void setDayStartTimeHr(String dayStartTimeHr) {
		this.dayStartTimeHr = dayStartTimeHr;
	}
	public String getDayStartTimeMin() {
		return dayStartTimeMin;
	}
	public void setDayStartTimeMin(String dayStartTimeMin) {
		this.dayStartTimeMin = dayStartTimeMin;
	}
	public String getDayStartTimeAmPm() {
		return dayStartTimeAmPm;
	}
	public void setDayStartTimeAmPm(String dayStartTimeAmPm) {
		this.dayStartTimeAmPm = dayStartTimeAmPm;
	}
	public String getDayEndTimeHr() {
		return dayEndTimeHr;
	}
	public void setDayEndTimeHr(String dayEndTimeHr) {
		this.dayEndTimeHr = dayEndTimeHr;
	}
	public String getDayEndTimeMin() {
		return dayEndTimeMin;
	}
	public void setDayEndTimeMin(String dayEndTimeMin) {
		this.dayEndTimeMin = dayEndTimeMin;
	}
	public String getDayEndTimeAmPm() {
		return dayEndTimeAmPm;
	}
	public void setDayEndTimeAmPm(String dayEndTimeAmPm) {
		this.dayEndTimeAmPm = dayEndTimeAmPm;
	}
	public boolean isDayBreakTimeOpenClose() {
		return dayBreakTimeOpenClose;
	}
	public void setDayBreakTimeOpenClose(boolean dayBreakTimeOpenClose) {
		this.dayBreakTimeOpenClose = dayBreakTimeOpenClose;
	}
	public String getDayBreakTimeHr() {
		return dayBreakTimeHr;
	}
	public void setDayBreakTimeHr(String dayBreakTimeHr) {
		this.dayBreakTimeHr = dayBreakTimeHr;
	}
	public String getDayBreakTimeMin() {
		return dayBreakTimeMin;
	}
	public void setDayBreakTimeMin(String dayBreakTimeMin) {
		this.dayBreakTimeMin = dayBreakTimeMin;
	}
	public String getDayBreakTimeAmPm() {
		return dayBreakTimeAmPm;
	}
	public void setDayBreakTimeAmPm(String dayBreakTimeAmPm) {
		this.dayBreakTimeAmPm = dayBreakTimeAmPm;
	}
	public String getDayBreakDuration() {
		return dayBreakDuration;
	}
	public void setDayBreakDuration(String dayBreakDuration) {
		this.dayBreakDuration = dayBreakDuration;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
}
