package com.telappoint.apptadmin.model;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsDataResponse extends BaseResponse {
	private List<AppointmentDetails> apptDetails = new ArrayList<AppointmentDetails>();
	private String message;

	public List<AppointmentDetails> getBookedAppts() {
		return apptDetails;
	}

	public void setBookedAppts(List<AppointmentDetails> bookedAppts) {
		this.apptDetails = bookedAppts;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
