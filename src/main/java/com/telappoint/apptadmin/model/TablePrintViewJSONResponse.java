package com.telappoint.apptadmin.model;

/**
 * @author Murali G
 */
public class TablePrintViewJSONResponse extends BaseResponse {
	
	private String tablePrintViewResponse;
	private String calendarDateDisplay;
	private String date;
	
	public String getTablePrintViewResponse() {
		return tablePrintViewResponse;
	}
	public void setTablePrintViewResponse(String tablePrintViewResponse) {
		this.tablePrintViewResponse = tablePrintViewResponse;
	}
	public String getCalendarDateDisplay() {
		return calendarDateDisplay;
	}
	public void setCalendarDateDisplay(String calendarDateDisplay) {
		this.calendarDateDisplay = calendarDateDisplay;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
}
