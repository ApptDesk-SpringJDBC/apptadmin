package com.telappoint.apptadmin.client.contants;


/**
 * 
 * @author Murali G
 * 
 */

public enum ApptAdminRestConstants {	
		
		//Home Page Related Functionality
		REST_SERVICE_LOGIN_AUTH("loginAuthenticate"),
		
		//Home Page Related Functionality
		REST_SERVICE_GET_CLIENT_DETAILS("getClientDetails?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_ADMIN_HOME_PAGE("getAdminHomePage?clientCode=@clientCodeParam@&loginUserId=@loginUserIdParam@"),
		REST_SERVICE_GET_GAUGE_CHART("getGaugeChart?clientCode=@clientCodeParam@&locationId=@locationIdParam@&startDate=@startDateParam@&endDate=@endDateParam@"),
		REST_SERVICE_GET_STAKED_CHART("getStackedChart?clientCode=@clientCodeParam@&locationId=@locationIdParam@&resourceId=@resourceIdParam@&stackChartType=@stackedChartTypeParam@"),
		REST_SERVICE_GET_PIE_CHART("getPieChart?clientCode=@clientCodeParam@&locationId=@locationIdParam@&selectedDate=@selectedDateParam@"),
		REST_SERVICE_GET_APPT_SYS_CONFIG("getApptSysConfig?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_DISPLAY_NAMES("getDisplayNames?clientCode=@clientCodeParam@&device=@deviceParam@"),
		REST_SERVICE_GET_PRIVILAGED_PAGE_NAMES("getPrivilegedPageNames?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_SERVICE_LOCATION_APPT_DATES_WINDOW("getServiceLocationApptDatesWindow?clientCode=@clientCodeParam@&locationId=@locationIdParam@"),
		REST_SERVICE_GET_UPDATE_SERVICE_LOCATION_APPT_DATES_WINDOW("updateServiceLocationApptDatesWindow"),
		REST_SERVICE_GET_UPDATE_LOCATION_APPT_DATES_WINDOW("updateLocationsApptDates"),
		REST_SERVICE_GET_UPDATE_APPT_RESTRICT_DATES("updateApptRestrictDates?clientCode=@clientCodeParam@&apptStartDate=@apptStartDateParam@&apptEndDate=@apptEndDateParam@"),
		REST_SERVICE_GET_UPDATE_APPT_PER_SEASON_DETAILS("updateApptPerSeasonDetails?clientCode=@clientCodeParam@&termStartDate=@termStartDateParam@&termEndDate=@termEndDateParam@&noApptPerTerm=@noApptPerTermParam@"),
		REST_SERVICE_GET_UPDATE_SCHEDULER_CLOSED_STATUS("updateScheduleClosedStatus?clientCode=@clientCodeParam@&closedStatus=@closedStatusParam@"),
		
		//Generic Functionality
		REST_SERVICE_GET_DYNAMIC_DISPLAY_FIELDS("getDynamicFieldDisplayData?clientCode=@clientCodeParam@&pageName=@pageNameParam@"),
		REST_SERVICE_GET_ACTIVE_LOCATION_DROP_DOWN_DATA("getActiveLocationDropDownData?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_ACTIVE_RESOURCE_DROP_DOWN_DATA("getActiveResourceDropDownData?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_ACTIVE_SERVICE_DROP_DOWN_DATA("getActiveServiceDropDownData?clientCode=@clientCodeParam@"),
		
		//Location Related Functionality
		REST_SERVICE_GET_LOCATION_BY_ID("getLocationById?clientCode=@clientCodeParam@&locationId=@locationIdParam@"),
		REST_SERVICE_GET_LOCATION_LIST_FOR_LOGIN_USER("getLocationList?clientCode=@clientCodeParam@&loginUserId=@loginUserIdParam@"),
		
		REST_SERVICE_GET_LOCATIONS_BASIC_DATA("getAllLocationsBasicData?clientCode=@clientCodeParam@"),
		REST_SERVICE_SAVE_LOCATION("addLocation"),
		REST_SERVICE_GET_COMPLETE_LOCATION_DATA_BY_ID("getCompleteLocationDataById?clientCode=@clientCodeParam@&locationId=@locationIdParam@"),
		REST_SERVICE_UPDATE_LOCATION("updateLocation"),
		REST_SERVICE_DELETE_LOCATION("deleteLocation?clientCode=@clientCodeParam@&locationId=@locationIdParam@"),
		REST_SERVICE_UN_DELETE_LOCATION("unDeleteLocation?clientCode=@clientCodeParam@&locationId=@locationIdParam@"),
				
		//Resource Related Functionality
		REST_SERVICE_GET_RESOURCE_BY_ID("getResourceById?clientCode=@clientCodeParam@&resourceId=@resourceIdParam@"),
		REST_SERVICE_GET_RESOURCE_LIST_FOR_LOGIN_USER("getResourceList?clientCode=@clientCodeParam@&loginUserId=@loginUserIdParam@"),
		REST_SERVICE_GET_RESOURCE_LIST_BY_LOCATION_ID_FOR_LOGIN_USER("getResourceListByLocationId?clientCode=@clientCodeParam@&locationId=@locationIdParam@&loginUserId=@loginUserIdParam@"),
		REST_SERVICE_GET_RESOURCE_BASIC_DATA("getAllResourcesBasicData?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_RESOURCE_PREFIX_LIST("getResourcePrefixList?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_RESOURCE_TITLE_LIST("getResourceTitleList?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_RESOURCE_TYPE_LIST("getResourceTypeList?clientCode=@clientCodeParam@"),
		REST_SERVICE_SAVE_RESOURCE("addResource"),
		REST_SERVICE_GET_COMPLETE_RESOURCE_DATA_BY_ID("getCompleteResourceDataById?clientCode=@clientCodeParam@&resourceId=@resourceIdParam@"),
		REST_SERVICE_UPDATE_RESOURCE("updateResource"),
		REST_SERVICE_DELETE_RESOURCE("deleteResource?clientCode=@clientCodeParam@&resourceId=@resourceIdParam@"),
		REST_SERVICE_UN_DELETE_RESOURCE("unDeleteResource?clientCode=@clientCodeParam@&resourceId=@resourceIdParam@"),
		
		//Service Related Functionality
		REST_SERVICE_GET_SERVICE_BY_ID("getServiceById?clientCode=@clientCodeParam@&serviceId=@serviceIdParam@"),
		REST_SERVICE_GET_SERVICE_LIST_FOR_LOGIN_USER("getServiceList?clientCode=@clientCodeParam@&loginUserId=@loginUserIdParam@&onlyActive=@onlyActiveParam@"),
		REST_SERVICE_GET_SERVICE_LIST_BY_LOCATION_ID_FOR_LOGIN_USER("getServiceListByLocationId?clientCode=@clientCodeParam@&locationId=@locationIdParam@&loginUserId=@loginUserIdParam@"),
		REST_SERVICE_GET_SERVICE_LIST_BY_RESOURCE_ID_FOR_LOGIN_USER("getServiceListByResourceId?clientCode=@clientCodeParam@&resourceId=@resourceIdParam@&loginUserId=@loginUserIdParam@"),
		
		REST_SERVICE_GET_SERVICES_BASIC_DATA("getAllServicesBasicData?clientCode=@clientCodeParam@"),
		REST_SERVICE_SAVE_SERVICE("addService"),
		REST_SERVICE_GET_COMPLETE_SERVICE_DATA_BY_ID("getCompleteServiceDataById?clientCode=@clientCodeParam@&serviceId=@serviceIdParam@"),
		REST_SERVICE_UPDATE_SERVICE("updateService"),
		REST_SERVICE_DELETE_SERVICE("deleteService?clientCode=@clientCodeParam@&serviceId=@serviceIdParam@"),
		REST_SERVICE_UN_DELETE_SERVICE("unDeleteService?clientCode=@clientCodeParam@&serviceId=@serviceIdParam@"),
		REST_SERVICE_GET_LOCATIONS_BY_SERVICE_ID_TO_CLOSE_SERVICE_STATUS("getLocationsByServiceIdToCloseServiceStatus?clientCode=@clientCodeParam@&serviceId=@serviceIdParam@"),
		
		//User Related Functionality
		REST_SERVICE_GET_USERS("getUsers?clientCode=@clientCodeParam@"),
		REST_SERVICE_SAVE_USER("addUser"),
		REST_SERVICE_GET_USER_DATA_BY_ID("getUserById?clientCode=@clientCodeParam@&userId=@userIdParam@"),
		REST_SERVICE_UPDATE_USER("updateUser"),
		REST_SERVICE_DELETE_USER("deleteUser?clientCode=@clientCodeParam@&userId=@userIdParam@"),
		REST_SERVICE_GET_PASSWORD_COMPLEXITY("getPasswordComplexity?clientCode=@clientCodeParam@"),
		REST_SERVICE_VALIDATE_USER("userExists?clientCode=@clientCodeParam@&userId=@userIdParam@&userName=@userNameParam@"),
		REST_SERVICE_GET_PASSWORD_COMPLEXITY_BY_USERNAME("getPasswordComplexityLogicByUserName?clientCode=@clientCodeParam@&userName=@userNameParam@"),
		
		//Call Report Related Functionality
		REST_SERVICE_GET_IN_BOUND_CALL_LOGS("getInBoundCallLogs?clientCode=@clientCodeParam@&fromDate=@fromDateParam@&toDate=@toDateParam@&callerId="),
		REST_SERVICE_GET_OUT_BOUND_CALL_LOGS("getOutBoundCallLogs?clientCode=@clientCodeParam@&fromDate=@fromDateParam@&toDate=@toDateParam@&callerId="),
		REST_SERVICE_GET_TRANS_STATES("getTransStates?clientCode=@clientCodeParam@&transId=@transIdParam@"),
		
		//Search Related Functionality
		REST_SERVICE_SEARCH_BY_FIRST_NAME_LAST_NAME("searchByFirstLastName?clientCode=@clientCodeParam@&firstName=@firstNameParam@&lastName=@lastNameParam@"),
		REST_SERVICE_SEARCH_BY_CONFIRMATION_NUMBER("searchByConfirmationNumber?clientCode=@clientCodeParam@&confirmationNumber=@confirmationNumberParam@"),
		REST_SERVICE_SEARCH_BY_ACCOUNT_NUMBER("searchByAccountNumber?clientCode=@clientCodeParam@&accountNumber=@accountNumberParam@"),
		REST_SERVICE_SEARCH_BY_CONTACT_PHONE("searchByContactPhone?clientCode=@clientCodeParam@&contactPhone=@contactPhoneParam@"),
		REST_SERVICE_SEARCH_BY_CALLER_ID("searchByCallerId?clientCode=@clientCodeParam@&callerId=@callerIdParam@"),
		REST_SERVICE_SEARCH_BY_ATTRIB1("searchByAttrib1?clientCode=@clientCodeParam@&attrib1=@attrib1Param@"),
		REST_SERVICE_SEARCH_BY_DOB("searchByDOB?clientCode=@clientCodeParam@&dob=@dobParam@"),
		REST_SERVICE_SEARCH_BY_HOUSE_HOLD_ID("searchByHouseHoldId?clientCode=@clientCodeParam@&houseHoldId=@houseHoldIdParam@"),
		REST_SERVICE_GET_SEARCH_DROP_DOWN_LIST("getSearchDropDownList?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_VERIFY_PAGE_DATA("getVerifyPageData?clientCode=@clientCodeParam@"),
		//Search Appointments Related Functionality
		REST_SERVICE_GET_APPOINTMENTS_BY_CUSTOMER_ID("getAppointmentsByCustomerId?clientCode=@clientCodeParam@&customerId=@customerIdParam@"),
		REST_SERVICE_GET_APPOINTMENT_STATUS_DROP_DOWN_LIST("getAppointmentStatusDropDownList?clientCode=@clientCodeParam@"),
		REST_SERVICE_UPDATE_APPOINTMENT_STATUS("updateAppointmentStatus?clientCode=@clientCodeParam@&scheduleId=@scheduleIdParam@&status=@statusParam@&userName=@userNameParam@"),
		REST_SERVICE_CANCEL_APPOINTMENT("cancelAppointment?clientCode=@clientCodeParam@&scheduleId=@scheduleIdParam@&langCode=@langCodeParam@"),
		//Search - Customers related Functionality
		REST_SERVICE_GET_CUSTOMERS_BY_CUSTOMER_ID("getCustomersById?clientCode=@clientCodeParam@&customerId=@customerIdParam@"),
		//Search - Customers Activity related Functionality
		REST_SERVICE_GET_CUSTOMER_ACTIVITY_BY_CUSTOMER_ID("getCustomerActivitiesByCustomerId?clientCode=@clientCodeParam@&customerId=@customerIdParam@"),
		//Search - HouseHold related Functionality
		REST_SERVICE_GET_HOUSE_HOLD_INFO_BY_CUSTOMER_ID("getHouseHoldInfoByCustomerId?clientCode=@clientCodeParam@&customerId=@customerIdParam@"),
		REST_SERVICE_MERGE_HOUSEHOLD_ID("mergeHouseHoldId?clientCode=@clientCodeParam@&fromHouseHoldIds=@fromHouseHoldIdsParam@&mergeToHouseHoldId=@mergeToHouseHoldIdParam@"),
		REST_SERVICE_SPLIT_HOUSEHOLD_ID("splitHouseHoldId?clientCode=@clientCodeParam@&customerIds=@customerIdsParam@&newHouseHoldId=@newHouseHoldIdParam@&assignNewHouseholdID=@assignNewHouseholdIDParam@"),
		REST_SERVICE_UPDATE_HOUSEHOLD_ID("updateHouseHoldId?clientCode=@clientCodeParam@&customerId=@customerIdParam@&newHouseHoldId=@newHouseHoldIdParam@"),
		//Search Pledge Related Functionality
		REST_SERVICE_GET_PLEDGE_HISTORY_BY_CUSTOMER_ID("getPledgeHistory?clientCode=@clientCodeParam@&customerId=@customerIdParam@&device=@deviceParam@&langCode=@langCodeParam@&transId=@transIdParam@"),
				
		//Reports related Functionality
		//Reports - Appointment Report related Functionality
		REST_SERVICE_GET_DYNAMIC_INCLUDE_REPORTS_DATA("getDynamicIncludeReportsData?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_APPOINTMENT_REPORT("getAppointmentReport?clientCode=@clientCodeParam@&fromDate=@fromDateParam@&toDate=@toDateParam@&locationIds=@locationIdsParam@&resourceIds=@resourceIdsParam@&serviceIds=@serviceIdsParam@&apptStatus=@apptStatusParam@"),
		//Reports - Summary Report related Functionality 
		REST_SERVICE_GET_SUMMARY_REPORT("getSummaryReport?clientCode=@clientCodeParam@&fromDate=@fromDateParam@&toDate=@toDateParam@&reportCategory=@reportCategoryParam@"),
		//Reports - Itemized Report related Functionality
		REST_SERVICE_GET_SUMMARY_STATISTICS_REPORT("getItemizedReport?clientCode=@clientCodeParam@&fromDate=@fromDateParam@&toDate=@toDateParam@&locationId=@locationIdParam@&serviceId=@serviceIdParam@&reportCategory=@reportCategoryParam@"),
		REST_SERVICE_GET_SUMMARY_STATISTICS_REPORT_TEMPLATE("getItemizedReportTemplate?clientCode=@clientCodeParam@&fromDate=@fromDateParam@&toDate=@toDateParam@&locationId=@locationIdParam@&serviceId=@serviceIdParam@&reportCategory=@reportCategoryParam@"),
		//Reports - Automatic Email related Functionality
		REST_SERVICE_GET_APPT_REPOT_CONFIG("getAppointmentReportConfig?clientCode=@clientCodeParam@&userName=@userNameParam@"),
		REST_SERVICE_ADD_APPT_REPOT_CONFIG("addAppointmentReportConfig"),
		REST_SERVICE_DELETE_APPT_REPOT_CONFIG("deleteApptReportConfigById?clientCode=@clientCodeParam@&configId=@configIdParam@"),
		
		//Reports - Pledge Report related Functionality
		REST_SERVICE_GET_PLEDGE_REPORT("getPledgeReport?clientCode=@clientCodeParam@&fromDate=@fromDateParam@&toDate=@toDateParam@&locationId=@locationIdParam@&groupByIntake=@groupByIntakeParam@&groupByFundSource=@groupByFundSourceParam@&groupByVendor=@groupByVendorParam@&resourceId=@resourceIdParam@&fundSourceId=@fundSourceIdParam@"),
		REST_SERVICE_GET_DYNAMIC_PLEDGE_REPORTS_DATA("getDynamicPledgeResults?clientCode=@clientCodeParam@"),
		REST_SERVICE_SAVE_PLEDGE_DETAILS("addCustomerPledgeDetails"),
		REST_SERVICE_UPDATE_PLEDGE_DETAILS("updateCustomerPledgeDetails"),
		REST_SERVICE_GET_CUST_PLEDGE_STATUS_LIST("getCustomerPledgeStatusList?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_CUST_PLEDGE_FUND_SOURCE_LIST("getCustomerPledgeFundSourceList?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_CUST_PLEDGE_VENDOR_LIST("getCustomerPledgeVendorList?clientCode=@clientCodeParam@&fundId=@fundIdParam@"),
		
		//Table Print View related Functionality
		REST_SERVICE_GET_TABLE_PRINT_VIEW("getTablePrintView?clientCode=@clientCodeParam@&locationId=@locationIdParam@&resourceIds=@resourceIdsParam@&date=@dateParam@"),
			
		//Calendar Related
		REST_SERVICE_GET_RESOURCE_SERVICE_LIST("getResourceServiceList?clientCode=@clientCodeParam@&locationId=@locationIdParam@&loginUserId=@loginUserIdParam@"),
		REST_SERVICE_GET_CALENDER_AVAILABLE_DATES("getAvailableDates?"),
		REST_SERVICE_GET_CALENDER_AVAILABLE_DATES_NEW("getJSCalendarAvailability?"),
		//Daily Calendar
		REST_SERVICE_GET_DAILY_CALENDAR("getDailyCalendar?clientCode=@clientCodeParam@&calendarDate=@calendarDateParam@&locationId=@locationIdParam@&resourceIds=@resourceIdsParam@"),
		//Weekly Calendar
		REST_SERVICE_GET_WEEKLY_CALENDAR("getWeeklyCalendar?clientCode=@clientCodeParam@&calendarDate=@calendarDateParam@&locationId=@locationIdParam@&resourceIds=@resourceIdsParam@"),
		//Monthly Calendar
		REST_SERVICE_GET_MONTHLY_CALENDAR("getMonthlyCalendar?clientCode=@clientCodeParam@&calendarDate=@calendarDateParam@&locationId=@locationIdParam@&resourceIdStr=@resourceIdStrParam@"),
		//Appointment Related		
		REST_SERVICE_GET_CUSTOMER_REG_DETAILS("getCustomerRegistrationDetails?clientCode=@clientCodeParam@&langCode=@langCodeParam@"),
		REST_SERVICE_CREATE_CUSTOMER("createCustomer"),
		REST_SERVICE_UPDATE_CUSTOMER("updateCustomer"),
		REST_SERVICE_CREATE_UPDATE_CUSTOMER("createOrUpdateCustomer"),
		REST_SERVICE_GET_FUTURE_APPTS("getFutureAppointments?clientCode=@clientCodeParam@&customerId=@customerIdParam@"),
		REST_SERVICE_GET_PAST_APPTS("getPastAppointments?clientCode=@clientCodeParam@&customerId=@customerIdParam@"),
		REST_SERVICE_HOLD_APPT("holdAppointment?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&locationId=@locationIdParam@&resourceId=@resourceIdParam@&procedureId=@procedureIdParam@&departmentId=@departmentIdParam@&serviceId=@serviceIdParam@&customerId=@customerIdParam@&apptDateTime=@apptDateTimeParam@&transId=@transIdParam@"),
		REST_SERVICE_RELEASE_HOLD_APPT("releaseHoldAppointment"),
		REST_SERVICE_HOLD_APPT_SERVICE_LIST("getSameServiceBlockList?clientCode=@clientCodeParam@&locationId=@locationIdParam@&resourceId=@resourceIdParam@&serviceId=@serviceIdParam@"),
		REST_SERVICE_RESCHEDULE_APPT("rescheduleAppointment?clientCode=@clientCodeParam@&oldscheduleId=@oldscheduleId@&device=@deviceParam@&langCode=@langCodeParam@&locationId=@locationIdParam@&resourceId=@resourceIdParam@&procedureId=@procedureIdParam@&departmentId=@departmentIdParam@&serviceId=@serviceIdParam@&customerId=@customerIdParam@&apptDateTime=@apptDateTimeParam@&transId=@transIdParam@"),
		REST_SERVICE_CONFIRM_APPT("confirmAppointment"),
		REST_SERVICE_CANCEL_APPT("cancelAppointment?clientCode=@clientCodeParam@&langCode=@langCodeParam@&scheduleId=@scheduleIdParam@"),
		REST_SERVICE_SAVE_BOOKED_APPT("updateConfirmAppointment?"),
		REST_SERVICE_GET_CUSTOMER_NAMES("getCustomerNames?clientCode=@clientCodeParam@&customerName=@customerNameParam@"),
		REST_SERVICE_GET_CUSTOMER_NAMES_AUTO_SUGGEST("getAutoSuggestCustomerNames?clientCode=@clientCodeParam@&customerName=@customerNameParam@"),

		//Resource - Edit Hours - Date Range functionality
		REST_SERVICE_GET_SUGGESTED_RESOURCE_WORKING_HOURS("getSuggestedResourceWorkingHours?clientCode=@clientCodeParam@&locationId=@locationIdParam@&resourceIds=@resourceIdsParam@&fromDate=@fromDateParam@&toDate=@toDateParam@"),
		REST_SERVICE_GET_UPDATE_RESOURCE_WORKING_HOURS("updateResourceWorkingHours"),
		
		//Resource - Edit Hours - Date Range functionality
		REST_SERVICE_GET_SUGGESTED_ONE_DATE_RESOURCE_WORKING_HOURS("getOneDateResourceWorkingHrs?clientCode=@clientCodeParam@&locationId=@locationIdParam@&resourceId=@resourceIdParam@&date=@dateParam@"),
		REST_SERVICE_GET_UPDATE_ONE_DATE_RESOURCE_WORKING_HOURS("updateOneDateResourceWorkingHrs"),
		REST_SERVICE_GET_ONE_DATE_RESOURCE_WORKING_HOURS_DETAILS("getOneDateResourceWorkingHoursDetails?clientCode=@clientCodeParam@"),

		//User Activity Log functionality
		REST_SERVICE_GET_USER_ACTIVITY_LOG("getUserActivityLogs?clientCode=@clientCodeParam@&userId=@userIdParam@&startDate=@startDateParam@&endDate=@endDateParam@"),
		
		//Privilege Settings functionality
		REST_SERVICE_GET_ACCESSES_PRIVILEGES("getAccessPrivilege?clientCode=@clientCodeParam@"),
		REST_SERVICE_GET_PRIVILEGE_PAGE_MAPPING("getPrivilegePageMapping?clientCode=@clientCodeParam@&accessPrivilegeId=@accessPrivilegeIdParam@"),
		REST_SERVICE_GET_PRIVILEGE_SETTINGS("getPrivilegeSettings?clientCode=@clientCodeParam@&accessPrivilegeName=@accessPrivilegeNameParam@"),
		REST_SERVICE_UPDATE_PRIVILEGE_SETTINGS("updatePrivilegeSettings"),
		;
		
	private String requestURI;

	ApptAdminRestConstants(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getRequestURI() {
		return requestURI;
	}
}
