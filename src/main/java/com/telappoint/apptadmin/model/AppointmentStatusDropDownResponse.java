package com.telappoint.apptadmin.model;

import java.util.List;

public class AppointmentStatusDropDownResponse extends BaseResponse {
	private List<AppointmentStatusData> appointmentStatusList;

	public List<AppointmentStatusData> getAppointmentStatusList() {
		return appointmentStatusList;
	}

	public void setAppointmentStatusList(List<AppointmentStatusData> appointmentStatusList) {
		this.appointmentStatusList = appointmentStatusList;
	}
}
