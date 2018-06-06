package com.telappoint.apptadmin.model;

import java.util.List;
/**
 * 
 * @author Murali
 *
 */
public class AppointmentReportResponse extends BaseResponse {
	private List<AppointmentReportData> appointmentReportDataList;

	public List<AppointmentReportData> getAppointmentReportDataList() {
		return appointmentReportDataList;
	}

	public void setAppointmentReportDataList(List<AppointmentReportData> appointmentReportDataList) {
		this.appointmentReportDataList = appointmentReportDataList;
	}
	
}
