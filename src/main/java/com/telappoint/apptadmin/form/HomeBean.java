package com.telappoint.apptadmin.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.apptadmin.model.CalendarSessionData;
import com.telappoint.apptadmin.model.ResourceHoursRequest;
import com.telappoint.apptadmin.model.ResourceWorkingHoursRequest;
import com.telappoint.apptadmin.model.UserLoginResponse;

/**
 * @author Murali G
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class HomeBean {
	
	private String transId;
	private String clientName;
	private UserLoginResponse userLoginResponse;
	private boolean selectedKeepMeLoggedIn;
	
	//private boolean calendarDataExists;
	private CalendarSessionData calendarSessionData = new CalendarSessionData();
	
	private ResourceHoursRequest dateRangeResourceHoursRequest = null;
	private ResourceWorkingHoursRequest oneDateResourceHoursRequest = null;
	
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public UserLoginResponse getUserLoginResponse() {
		return userLoginResponse;
	}
	public void setUserLoginResponse(UserLoginResponse userLoginResponse) {
		this.userLoginResponse = userLoginResponse;
	}
	public boolean isSelectedKeepMeLoggedIn() {
		return selectedKeepMeLoggedIn;
	}
	public void setSelectedKeepMeLoggedIn(boolean selectedKeepMeLoggedIn) {
		this.selectedKeepMeLoggedIn = selectedKeepMeLoggedIn;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/*public boolean isCalendarDataExists() {
		return calendarDataExists;
	}
	public void setCalendarDataExists(boolean calendarDataExists) {
		this.calendarDataExists = calendarDataExists;
	}*/
	public CalendarSessionData getCalendarSessionData() {
		return calendarSessionData;
	}
	public void setCalendarSessionData(CalendarSessionData calendarSessionData) {
		this.calendarSessionData = calendarSessionData;
	}
	public ResourceHoursRequest getDateRangeResourceHoursRequest() {
		return dateRangeResourceHoursRequest;
	}
	public void setDateRangeResourceHoursRequest(
			ResourceHoursRequest dateRangeResourceHoursRequest) {
		this.dateRangeResourceHoursRequest = dateRangeResourceHoursRequest;
	}
	public ResourceWorkingHoursRequest getOneDateResourceHoursRequest() {
		return oneDateResourceHoursRequest;
	}
	public void setOneDateResourceHoursRequest(
			ResourceWorkingHoursRequest oneDateResourceHoursRequest) {
		this.oneDateResourceHoursRequest = oneDateResourceHoursRequest;
	}		
}
