package com.telappoint.apptadmin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.telappoint.apptadmin.form.CalendarRequest;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.form.UserLoginTO;
import com.telappoint.apptadmin.model.*;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarResponse;

/**
 * @author Murali G
 * 
 */
public interface ApptAdminService {
	
	public void sendErrorEmail(String clientCode, String requestURI,HttpServletRequest request, Exception ex);

	//Login Page related functionality
	public UserLoginResponse loginAuthenticate(UserLoginTO loginForm) throws Exception;
	
	//Home Page related functionality
	public Client getClientDetails(HomeBean homeBean) throws Exception;
	public HomePageResponse getAdminHomePage(HomeBean homeBean)throws Exception;
	public GaugeChartResponse getGaugeChart(HomeBean homeBean, String locationId,String startDate, String endDate) throws Exception;
	public StackedChartResponse getStackedChart(HomeBean homeBean, String locationId,String resourceId,String stackedChartType) throws Exception;
	public PieChartResponse getPieChart(HomeBean homeBean, String locationId,String selectedDate) throws Exception;
	public ApptSysConfig getApptSysConfigs(HomeBean homeBean) throws Exception;
	public DisplayNames getDisplayNames(HomeBean homeBean) throws Exception;
	public PrivilegedPageNamesResponse getAccessPrivilegedPageNames(HomeBean homeBean)throws Exception;
	public ServiceLocationApptDatesResponse getServiceLocationApptDatesWindow(HomeBean homeBean,String locationId) throws Exception;
	public BaseResponse updateServiceLocationApptDatesWindow(HomeBean homeBean,ServiceLocationApptDatesRequest serviceLocApptDatesReq)throws Exception;
	public BaseResponse updateApptRestrictDates(HomeBean homeBean,String apptStartDate, String apptEndDate) throws Exception;
	public BaseResponse updateApptPerSeasonDetails(HomeBean homeBean,String termStartDate, String termEndDate, String noApptPerTerm)throws Exception;
	public BaseResponse updateLocationsApptDates(HomeBean homeBean,LocationsApptDatesRequest locationApptReq) throws Exception;
	public BaseResponse updateScheduleClosedStatus(HomeBean homeBean,String closedStatus) throws Exception;
	
	//Generic Functionality
	public DynamicFieldDisplayResponse getDynamicFieldDisplayData(HomeBean homeBean,String pageName) throws Exception;
	public LocationResponse getActiveLocationDropDownData(HomeBean homeBean) throws Exception;
	public ResourceResponse getActiveResourceDropDownData(HomeBean homeBean) throws Exception;
	public ServiceResponse getActiveServiceDropDownData(HomeBean homeBean) throws Exception;
	
	//Location Related Functionality
	public Location getLocationById(HomeBean homeBean, String locationId) throws Exception;
	public List<Location> getLocationListForLoginUser(HomeBean homeBean) throws Exception;
	public LocationResponse getAllLocationsBasicData(HomeBean homeBean)throws Exception;
	public Location getCompleteLocationDataById(HomeBean homeBean, String locationId)throws Exception;
	public LocationResponse saveLocation(HomeBean homeBean, Location location)throws Exception;
	public BaseResponse updateLocation(HomeBean homeBean, Location location)throws Exception;
	public BaseResponse deleteLocation(HomeBean homeBean, String locationId)throws Exception;
	public BaseResponse unDeleteLocation(HomeBean homeBean, String locationId)throws Exception;
	
	//Resource Related Functionality
	public Resource getResourceById(HomeBean homeBean, String resourceId)throws Exception;
	public List<Resource> getResourceListForLoginUser(HomeBean homeBean) throws Exception;
	public List<Resource> getResourceListByLocationIdForLoginUser(HomeBean homeBean, String locationId)throws Exception;
	
	public ResourceResponse getAllResourcesBasicData(HomeBean homeBean)	throws Exception;
	public ResourceTitleResponse getResourceTitleList(HomeBean homeBean)throws Exception;
	public ResourceTypeResponse getResourceTypeList(HomeBean homeBean)throws Exception;
	public ResourcePrefixResponse getResourcePrefixList(HomeBean homeBean) throws Exception;
	public ResourceResponse saveResource(HomeBean homeBean, Resource resource) throws Exception;
	public Resource getCompleteResourceDataById(HomeBean homeBean, String resourceId)throws Exception;
	public BaseResponse updateResource(HomeBean homeBean, Resource resource) throws Exception;
	public BaseResponse deleteResource(HomeBean homeBean, String resourceId) throws Exception;
	public BaseResponse unDeleteResource(HomeBean homeBean, String resourceId) throws Exception;
	
	//Service Related Functionality
	public ServiceVO getServiceById(HomeBean homeBean, String serviceId)throws Exception;
	public List<ServiceVO> getServiceListForLoginUser(HomeBean homeBean,String onlyActive)throws Exception;
	public List<ServiceVO> getServiceListByLocationIdForLoginUser(HomeBean homeBean,String locationId) throws Exception;
	public List<ServiceVO> getServiceListByResourceIdForLoginUser(HomeBean homeBean,String resourceId) throws Exception;
	
	public ServiceResponse getAllServicesBasicData(HomeBean homeBean) throws Exception;
	public ServiceResponse saveService(HomeBean homeBean, ServiceVO service)throws Exception;
	public ServiceVO getCompleteServiceDataById(HomeBean homeBean, String serviceId)throws Exception;
	public BaseResponse updateService(HomeBean homeBean, ServiceVO service)throws Exception;
	public BaseResponse deleteService(HomeBean homeBean, String serviceId)throws Exception;
	public BaseResponse unDeleteService(HomeBean homeBean, String serviceId)throws Exception;
	public LocationResponse getLocationsByServiceIdToCloseServiceStatus(HomeBean homeBean, String serviceId) throws Exception;
	
	//Call Report Related Functionality
	public InBoundCallsResponse getInBoundCallLogs(HomeBean homeBean, String fromDate,String toDate) throws Exception;
	public OutBoundCallsResponse getOutBoundCallLogs(HomeBean homeBean,String fromDate, String toDate) throws Exception;
	public TransStateResponse getTransStates(HomeBean homeBean, String transId)throws Exception;
	
	//User Related Functionality
	public UserResponse getUsers(HomeBean homeBean) throws Exception;
	public BaseResponse saveUser(HomeBean homeBean, AdminLogin adminLogin) throws Exception;
	public AdminLogin getUserById(HomeBean homeBean, String serviceId) throws Exception;
	public BaseResponse updateUser(HomeBean homeBean, AdminLogin adminLogin) throws Exception;
	public BaseResponse deleteUser(HomeBean homeBean, String resourceId) throws Exception;
	public PasswordComplexity getPasswordComplexity(HomeBean homeBean) throws Exception;
	public PasswordComplexity getPasswordComplexityLogicByUserName(HomeBean homeBean) throws Exception;
	public BaseResponse validateUserName(HomeBean homeBean, String userId,String userName) throws Exception;

	//Search Related Functionality
	public SearchResponse getSearchResponse(HomeBean homeBean,String searchSelectionType, String firstName, String lastName,
			String confirmationNumber, String accountNumber,String contactPhone, String callerId, String attrib1, String dob,String houseHoldId) throws Exception;
	public SearchResponse searchByFirstLastName(HomeBean homeBean, String firstName,String lastName) throws Exception;
	public SearchResponse searchByConfirmationNumber(HomeBean homeBean,String confirmationNumber) throws Exception;
	public SearchResponse searchByAccountNumber(HomeBean homeBean, String accountNumber)throws Exception;
	public SearchResponse searchByContactPhone(HomeBean homeBean, String contactPhone)throws Exception;
	public SearchResponse searchByCallerId(HomeBean homeBean, String callerId)throws Exception;
	public SearchResponse searchByAttrib1(HomeBean homeBean, String attrib1)throws Exception;
	public SearchResponse searchByDOB(HomeBean homeBean, String dob) throws Exception;
	public SearchResponse searchByHouseHoldId(HomeBean homeBean, String houseHoldId)throws Exception;
	public DynamicSearchDropDownResponse getSearchDropDownList(HomeBean homeBean)throws Exception;
	//Search Appointments Related Functionality
	public SearchResponse getAppointmentsByCustomerId(HomeBean homeBean, String customerId) throws Exception;
	public AppointmentStatusDropDownResponse getAppointmentStatusDropDownList(HomeBean homeBean) throws Exception;
	public BaseResponse updateAppointmentStatus(HomeBean homeBean, String scheduleId,String status) throws Exception;
	public CancelAppointResponse cancelAppointment(HomeBean homeBean, String scheduleId)throws Exception;
	//Search - Customers related Functionality
	public CustomersResponse getCustomersById(HomeBean homeBean, String customerId)throws Exception;
	//Search - Customers Activity related Functionality
	public CustomerActivityResponse getCustomerActivitiesByCustomerId(HomeBean homeBean, String customerId) throws Exception;
	//Search - HouseHold related Functionality
	public CustomersResponse getHouseHoldInfoByCustomerId(HomeBean homeBean,String customerId) throws Exception;
	public BaseResponse mergeHouseHoldId(HomeBean homeBean, String fromHouseHoldIds,String mergeToHouseHoldId) throws Exception;
	public BaseResponse splitHouseHoldId(HomeBean homeBean, String customerIds,String newHouseHoldId, String assignNewHouseholdID)throws Exception;
	public BaseResponse updateHouseHoldId(HomeBean homeBean, String customerId,String newHouseHoldId) throws Exception;
	//Search - Pledge related Functionality
	public CustomerPledgeResponse getPledgeHistoryByCustomerId(HomeBean homeBean,String customerId) throws Exception;
	 
	//Reports related Functionality
	//Reports - Appointment Reports related Functionality
	public String getDynamicIncludeReportsData(HomeBean homeBean)throws Exception;
	public String getAppointmentReport(HomeBean homeBean,String fromDate, String toDate, String locationIds,String resourceIds, String serviceIds, String apptStatus)throws Exception;	
	//Reports - Itemized Reports related Functionality
	public String getSummaryReport(HomeBean homeBean, String fromDate,String toDate, String reportCategory) throws Exception;
	//Reports - Summary Reports related Functionality
	public	String getSummaryStatisticsReport(HomeBean homeBean,String fromDate, String toDate, String locationId,String serviceId, String reportCategory) throws Exception;
	public Map<String, Object> getItemizedReportTemplate(HomeBean homeBean, String fromDate,String toDate, String locationId, String serviceId,String reportCategory) throws Exception;
	//Reports - Automatic Email related Functionality
	public String getAppointmentReportConfig(HomeBean homeBean) throws Exception;
	public String addAppointmentReportConfig(HomeBean homeBean,AppointmentReportConfig apptReportConfig) throws Exception;
	public String deleteApptReportConfigById(HomeBean homeBean, String configId)throws Exception;
	//Reports - Pledge Report related Functionality
	public String getPledgeReport(HomeBean homeBean, String fromDate, String toDate,String locationId, String resourceId, String fundSourceId,String pledgeReportRequestType) throws Exception;
	public String getDynamicPledgeResults(HomeBean homeBean) throws Exception;
	public CustomerPledgeResponse addCustomerPledgeDetails(HomeBean homeBean,CustomerPledgeRequest customerPledgeReq) throws Exception;
	public CustomerPledgeResponse updateCustomerPledgeDetails(HomeBean homeBean,CustomerPledgeRequest customerPledgeReq) throws Exception;
	public CustomerPledgeStatusResponse getCustomerPledgeStatusList(HomeBean homeBean) throws Exception;
	public CustomerPledgeFundSourceResponse getCustomerPledgeFundSourceList(HomeBean homeBean) throws Exception;
	public CustomerPledgeVendorResponse getCustomerPledgeVendorList(HomeBean homeBean, String fundId) throws Exception;
	
	//Table Print View related Functionality
	public String getTablePrintView(HomeBean homeBean, String locationId,String resourceIds, String date) throws Exception;
	
	//Calendar Related
	public ResourceServiceResponse getResourceServiceList(HomeBean homeBean,String locationId) throws Exception;

	Map<String, CalendarAvailability> getAvailableDates(HomeBean homeBean, String queryString) throws Exception;

	//Daily Calendar
	public DailyCalendarResponse getDailyCalendar(HomeBean homeBean, String calendarDate,String locationId, String resourceIds) throws Exception;
	//Weekly Calendar
	public WeeklyCalendarResponse getWeeklyCalendar(HomeBean homeBean, String calendarDate,String locationId, String resourceIds) throws Exception;

	//Monthly Calendar
	MonthlyCalendarResponse getMonthlyCalendar(HomeBean homeBean, CalendarRequest calendarRequest) throws Exception;

	//Appointment Related
	public CustomerRegistrationRepsonse getCustomerRegistrationDetails(HomeBean homeBean,String langCode) throws Exception;
	public BaseResponse createCustomer(HomeBean homeBean,CustomerRequest customerRequest) throws Exception;
	public SaveCustomerResponse createOrUpdateCustomer(HomeBean homeBean,CustomerRequest customerRequest) throws Exception;
	public BaseResponse updateCustomer(HomeBean homeBean,CustomerRequest customerRequest) throws Exception;
	public AppointmentsDataResponse getFutureAppointments(HomeBean homeBean,String customerId)  throws Exception;
	public AppointmentsDataResponse getPastAppointments(HomeBean homeBean,String customerId)  throws Exception;
	public HoldAppt holdAppointment(HomeBean homeBean, String locationId, String resourceId,String procedureId, String departmentId, String serviceId, String customerId,String apptDateTime, String device, String langCode, String transId) throws Exception;
	public BaseResponse releaseHoldAppointment(HomeBean homeBean, final HttpServletRequest request, final String queryString) throws Exception;
	public List<ServiceVO> holdAppointmentServiceList(HomeBean homeBean, String locationId, String resourceId, String serviceId) throws Exception;
	public ConfirmAppointmentResponse rescheduleAppointment(HomeBean homeBean, String locationId, String resourceId, String procedureId, String departmentId, String serviceId,String customerId, String apptDateTime, String device, String langCode, String transId, String oldscheduleId) throws Exception;
	public ConfirmAppointmentResponse confirmAppointment(HomeBean homeBean,ConfirmAppointmentRequest confirmApptReq) throws Exception;
	BaseResponse updateConfirmAppointment(final HomeBean homeBean, final String url) throws Exception;
	public CancelAppointResponse cancelAppointment(HomeBean homeBean, String langCode, String scheduleId) throws Exception;
	public CustomerNamesResponse getCustomerNames(HomeBean homeBean,String customerName) throws Exception;

	public CancelAppointResponse cancelAppointment(HomeBean homeBean, String langCode, String scheduleId, String customerId) throws Exception;

	//Resource - Edit Hours - Date Range functionality
	public ResourceWorkingHrs getSuggestedResourceWorkingHours(HomeBean homeBean, String locationId, String resourceIds,	String fromDate, String toDate) throws Exception;
	public ResourceWorkingHrsResponse updateResourceWorkingHours(HomeBean homeBean,ResourceHoursRequest resourceHoursRequest) throws Exception;
 
	//Resource - Edit Hours - One Date functionality
	public OneDateWorkingHoursResponse getSuggestedOneDateResourceWorkingHrs(HomeBean homeBean,String locationId, String resourceIds, String date)throws Exception;
	public ResourceWorkingHrsResponse updateOneDateResourceWorkingHrs(HomeBean homeBean,ResourceWorkingHoursRequest resourceWorkingHoursRequest)throws Exception;
	public OneDateResourceWorkingHoursResponse getOneDateResourceWorkingHrsDetails(HomeBean homeBean)throws Exception;
	
	public VerifyPageData getAppointmentInfo(HomeBean homeBean, String customerId, String scheduleId, String transId) throws Exception;
	
	//User Activity Log functionality
	public UserActivityLogsResponse getUserActivityLogs(HomeBean homeBean, String startDate,String endDate) throws Exception;

	//Privilege Settings functionality    
	public AccessPrivilegeResponse getAccessPrivilege(HomeBean homeBean)throws Exception;
	public AccessPrivilegeResponse getPrivilegePageMapping(HomeBean homeBean,String accessPrivilegeId) throws Exception;
	public PrivilegeSettingResponse getPrivilegeSettings(HomeBean homeBean,String accessPrivilegeName) throws Exception;
	public BaseResponse updatePrivilegeSettings(HomeBean homeBean,PrivilegeSettings privilegeSettings) throws Exception;
	
}
