package com.telappoint.apptadmin.constants;

/**
 * 
 * @author Murali G
 * 
 */
public enum ErrorCodesConstants {
	
	ERROR_CODE_ADVANCED_SETTINGS_SHOW("SHOW_ADVANCED_SETTINGS",101,"Error in show Advanced settings page/function"),
	
	ERROR_CODE_LOCATIONS_SHOW("LOCATIONS_SHOW",201,"Error in show locations page/function"),
	ERROR_CODE_LOCATIONS_SAVE("LOCATIONS_SAVE",202,"Error in save location page/function"),
	ERROR_CODE_LOCATION_BY_ID("LOCATION_BY_ID",203,"Error in get location page/function"),
	ERROR_CODE_LOCATIONS_UPDATE("LOCATIONS_UPDATE",204,"Error in update location page/function"),
	ERROR_CODE_LOCATION_DELETE("LOCATION_DELETE",205,"Error in delete location page/function"),
	ERROR_CODE_LOCATION_UN_DELETE("LOCATION_UN_DELETE",206,"Error in un delete location page/function"),
	ERROR_CODE_GET_ALL_LOCATIONS("GET_ALL_LOCATIONS",203,"Error in getting all locations page/function"),
	
	ERROR_CODE_SERVICES_SHOW("SERVICES_SHOW",301,"Error in show Services page/function"),
	ERROR_CODE_SERVICES_SAVE("SERVICES_SAVE",302,"Error in save Service page/function"),
	ERROR_CODE_SERVICE_BY_ID("SERVICE_BY_ID",303,"Error in get Service page/function"),
	ERROR_CODE_SERVICES_UPDATE("SERVICES_UPDATE",304,"Error in update Service page/function"),
	ERROR_CODE_SERVICES_DELETE("SERVICES_DELETE",305,"Error in delete Service page/function"),
	ERROR_CODE_SERVICES_UN_DELETE("SERVICES_UN_DELETE",306,"Error in un delete Service page/function"),
	ERROR_CODE_GET_ALL_SERVICES("GET_ALL_SERVICES",203,"Error in getting all Services page/function"),
	
	ERROR_CODE_RESOURCES_SHOW("RESOURCES_SHOW",401,"Error in show Resources page/function"),
	ERROR_CODE_RESOURCES_SAVE("RESOURCES_SAVE",402,"Error in save Resource page/function"),
	ERROR_CODE_RESOURCES_BY_ID("RESOURCES_BY_ID",403,"Error in get Resource page/function"),
	ERROR_CODE_RESOURCES_UPDATE("RESOURCES_UPDATE",404,"Error in update Resource page/function"),
	ERROR_CODE_RESOURCES_DELETE("RESOURCES_DELETE",405,"Error in delete Resource page/function"),
	ERROR_CODE_RESOURCES_UN_DELETE("RESOURCES_UN_DELETE",406,"Error in un delete Resource page/function"),
	
	ERROR_CODE_CALL_REPORT_SHOW("CALL_REPORT_SHOW",501,"Error in show Call Report page/function"),
	ERROR_CODE_CALL_REPORT_IN_BOUND("CALL_REPORT_IN_BOUND",502,"Error in InBound Call Report page/function"),
	ERROR_CODE_CALL_REPORT_OUT_BOUND("CALL_REPORT_OUT_BOUND",503,"Error in OutBound Call Report page/function"),
	
	ERROR_CODE_USERS_SHOW("USERS_SHOW",601,"Error in show Users page/function"),
	ERROR_CODE_USERS_SAVE("USERS_SAVE",602,"Error in save User page/function"),
	ERROR_CODE_USERS_BY_ID("USERS_BY_ID",603,"Error in get User page/function"),
	ERROR_CODE_USERS_UPDATE("USERS_UPDATE",604,"Error in update User page/function"),
	ERROR_CODE_USERS_DELETE("USERS_DELETE",605,"Error in delete User page/function"),
	ERROR_CODE_VALIDATE_USER("VALIDATE_USER",606,"Error in Validate User page/function"),
	
	ERROR_CODE_SEARCH_PAGE_SHOW("SEARCH_PAGE_SHOW",701,"Error in show Search page/function"),
	
	ERROR_CODE_REPORTS_PAGE_SHOW("REPORTS_PAGE_SHOW",801,"Error in show Reports page/function"),
	
	ERROR_CODE_TABLE_PRINT_VIEW_SHOW("TABLE_PRINT_VIEW_SHOW",901,"Error in show Table Print View page/function"),
	
	ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW("DAILY_CALENDAR_PAGE_SHOW",1001,"Error in show Daily Calendar page/function"),
	ERROR_CODE_WEEKLY_CALENDAR_PAGE_SHOW("WEEKLY_CALENDAR_PAGE_SHOW",1002,"Error in show Weekly Calendar page/function"),
	ERROR_CODE_MONTHLY_CALENDAR_PAGE_SHOW("MONTHLY_CALENDAR_PAGE_SHOW",1003,"Error in show Monthly Calendar page/function"),
	
	ERROR_CODE_RESOURCE_EDIT_HOURS_DATE_RANGE_PAGE_SHOW("RESOURCE_EDIT_HOURS_DATE_RANGE_PAGE_SHOW",1101,"Error in show resource edit hours - date range page/function"),
	ERROR_CODE_RESOURCE_EDIT_HOURS_ONE_DATE_PAGE_SHOW("RESOURCE_EDIT_HOURS_ONE_DATE_PAGE_SHOW",1102,"Error in show resource edit hours - one date page/function"),
	
	ERROR_CODE_RESOURCE_SHOW_SUGGESTED_WORKING_HOURS_PAGE("RESOURCE_SHOW_SUGGESTED_WORKING_HOURS_PAGE",1103,"Error in resource suggested working hours page/function"),
	
	ERROR_CODE_USER_ACTIVITY_LOG_PAGE_SHOW("USER_ACTIVITY_LOG_PAGE_SHOW",1201,"Error in show User Activity Log page/function"),
	
	ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW("UPRIVILEGE_SETTINGS_PAGE_SHOW",1201,"Error in show Privilege Settings page/function"),
	;
	
    private String page;
	private int errorCode;
	private String description;

	private ErrorCodesConstants(String page,int errorCode,String description) {
		this.page = page;
		this.errorCode = errorCode;
		this.description = description;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}