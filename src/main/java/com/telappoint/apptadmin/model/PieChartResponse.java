package com.telappoint.apptadmin.model;

/**
 * 
 * @author Balaji N
 *
 */
public class PieChartResponse extends BaseResponse {
	private String pieChartDate;  // MM/dd/YYYY
	private String resources; // comma separated resources.
	private String noOfConfirmedAppts; // comma separated confirmed appts.
	private String selectedLocationId; 
	private String selectedDate;
	
	public String getPieChartDate() {
		return pieChartDate;
	}
	public void setPieChartDate(String pieChartDate) {
		this.pieChartDate = pieChartDate;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public String getNoOfConfirmedAppts() {
		return noOfConfirmedAppts;
	}
	public void setNoOfConfirmedAppts(String noOfConfirmedAppts) {
		this.noOfConfirmedAppts = noOfConfirmedAppts;
	}
	public String getSelectedLocationId() {
		return selectedLocationId;
	}
	public void setSelectedLocationId(String selectedLocationId) {
		this.selectedLocationId = selectedLocationId;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
}
