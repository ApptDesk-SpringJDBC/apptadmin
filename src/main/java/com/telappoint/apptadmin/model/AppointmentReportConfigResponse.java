package com.telappoint.apptadmin.model;
import java.util.List;

/**
 * 
 * @author Murali G
 *
 */

public class AppointmentReportConfigResponse extends BaseResponse {
	private List<AppointmentReportConfig> apptReportConfigList;

	public List<AppointmentReportConfig> getApptReportConfigList() {
		return apptReportConfigList;
	}

	public void setApptReportConfigList(List<AppointmentReportConfig> apptReportConfigList) {
		this.apptReportConfigList = apptReportConfigList;
	}
	
}
