package com.telappoint.apptadmin.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.telappoint.apptadmin.form.CalendarRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.telappoint.apptadmin.client.contants.ApptAdminContants;
import com.telappoint.apptadmin.client.contants.ApptAdminRestConstants;
import com.telappoint.apptadmin.client.json.ResponseModel;
import com.telappoint.apptadmin.constants.CommonConstants;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.form.UserLoginTO;
import com.telappoint.apptadmin.model.AccessPrivilegeResponse;
import com.telappoint.apptadmin.model.AdminLogin;
import com.telappoint.apptadmin.model.AppointmentInfoResponse;
import com.telappoint.apptadmin.model.AppointmentReportConfig;
import com.telappoint.apptadmin.model.AppointmentStatusDropDownResponse;
import com.telappoint.apptadmin.model.AppointmentsDataResponse;
import com.telappoint.apptadmin.model.ApptSysConfig;
import com.telappoint.apptadmin.model.ApptSysConfigResponse;
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.CalanderAvailabilityResponse;
import com.telappoint.apptadmin.model.CalendarAvailability;
import com.telappoint.apptadmin.model.CalendarSessionData;
import com.telappoint.apptadmin.model.CancelAppointResponse;
import com.telappoint.apptadmin.model.Client;
import com.telappoint.apptadmin.model.ConfirmAppointmentRequest;
import com.telappoint.apptadmin.model.ConfirmAppointmentResponse;
import com.telappoint.apptadmin.model.CustomerActivityResponse;
import com.telappoint.apptadmin.model.CustomerNamesResponse;
import com.telappoint.apptadmin.model.CustomerPledgeFundSourceResponse;
import com.telappoint.apptadmin.model.CustomerPledgeRequest;
import com.telappoint.apptadmin.model.CustomerPledgeResponse;
import com.telappoint.apptadmin.model.CustomerPledgeStatusResponse;
import com.telappoint.apptadmin.model.CustomerPledgeVendorResponse;
import com.telappoint.apptadmin.model.CustomerRegistrationRepsonse;
import com.telappoint.apptadmin.model.CustomerRequest;
import com.telappoint.apptadmin.model.CustomersResponse;
import com.telappoint.apptadmin.model.DailyCalendarResponse;
import com.telappoint.apptadmin.model.DisplayNames;
import com.telappoint.apptadmin.model.DisplayNamesResponse;
import com.telappoint.apptadmin.model.DynamicFieldDisplayResponse;
import com.telappoint.apptadmin.model.DynamicSearchDropDownResponse;
import com.telappoint.apptadmin.model.GaugeChartResponse;
import com.telappoint.apptadmin.model.HoldAppt;
import com.telappoint.apptadmin.model.HomePageResponse;
import com.telappoint.apptadmin.model.InBoundCallsResponse;
import com.telappoint.apptadmin.model.Location;
import com.telappoint.apptadmin.model.LocationResponse;
import com.telappoint.apptadmin.model.LocationsApptDatesRequest;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarResponse;
import com.telappoint.apptadmin.model.OneDateResourceWorkingHoursResponse;
import com.telappoint.apptadmin.model.OneDateWorkingHoursResponse;
import com.telappoint.apptadmin.model.OutBoundCallsResponse;
import com.telappoint.apptadmin.model.PasswordComplexity;
import com.telappoint.apptadmin.model.PerDateAppts;
import com.telappoint.apptadmin.model.PieChartResponse;
import com.telappoint.apptadmin.model.PrivilegeSettingResponse;
import com.telappoint.apptadmin.model.PrivilegeSettings;
import com.telappoint.apptadmin.model.PrivilegedPageNamesResponse;
import com.telappoint.apptadmin.model.Resource;
import com.telappoint.apptadmin.model.ResourceHoursRequest;
import com.telappoint.apptadmin.model.ResourcePrefixResponse;
import com.telappoint.apptadmin.model.ResourceResponse;
import com.telappoint.apptadmin.model.ResourceServiceResponse;
import com.telappoint.apptadmin.model.ResourceTitleResponse;
import com.telappoint.apptadmin.model.ResourceTypeResponse;
import com.telappoint.apptadmin.model.ResourceWorkingHoursRequest;
import com.telappoint.apptadmin.model.ResourceWorkingHrs;
import com.telappoint.apptadmin.model.ResourceWorkingHrsResponse;
import com.telappoint.apptadmin.model.SaveCustomerResponse;
import com.telappoint.apptadmin.model.SearchResponse;
import com.telappoint.apptadmin.model.ServiceLocationApptDatesRequest;
import com.telappoint.apptadmin.model.ServiceLocationApptDatesResponse;
import com.telappoint.apptadmin.model.ServiceResponse;
import com.telappoint.apptadmin.model.ServiceVO;
import com.telappoint.apptadmin.model.StackedChartResponse;
import com.telappoint.apptadmin.model.TransStateResponse;
import com.telappoint.apptadmin.model.UserActivityLogsResponse;
import com.telappoint.apptadmin.model.UserLoginResponse;
import com.telappoint.apptadmin.model.UserResponse;
import com.telappoint.apptadmin.model.VerifyPageData;
import com.telappoint.apptadmin.model.WeeklyCalendarResponse;
import com.telappoint.apptadmin.restclient.ApptAdminRestClient;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.EmailComponent;
import com.telappoint.apptadmin.utils.PropertyUtils;

/**
 * 
 * @author Murali G
 * 
 */

@Service
public class ApptAdminServiceImpl implements ApptAdminService {

	private Logger logger = Logger.getLogger(ApptAdminServiceImpl.class);

	@Autowired
	private EmailComponent emailComponent;

	//TODO : Don't delete below commented, this is for reference
	//GaugeChartResponse gaugeChartResponse = AdminUtils.getDataFromJSON(responseModel.getData(),GaugeChartResponse.class);
	//List<GaugeChartResponse> gaugeChartResponseList = AdminUtils.getListDataFromJSON(responseModel.getData(),GaugeChartResponse[].class);
	//Map<String, String> gaugeChartResponseMap = AdminUtils.getMapDataFromJSON(responseModel.getData(),String.class,String.class);	

	//Login Page related functionality
	@Override
	public UserLoginResponse loginAuthenticate(UserLoginTO loginForm) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_LOGIN_AUTH.getRequestURI();
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,loginForm,"loginAuthenticate");
		logger.debug("getAdminHomePage : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),UserLoginResponse.class);
	}

	//Home Page related functionality	
	@Override
	public Client getClientDetails(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CLIENT_DETAILS.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getClientDetails");
		logger.debug("getClientDetails : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),Client.class);
	}

	@Override
	public HomePageResponse getAdminHomePage(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_ADMIN_HOME_PAGE.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAdminHomePage");
		logger.debug("getAdminHomePage : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),HomePageResponse.class);
	}

	@Override
	public GaugeChartResponse getGaugeChart(HomeBean homeBean,String locationId,String startDate,String endDate) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_GAUGE_CHART.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@startDateParam@",startDate);
		endPointUrl = endPointUrl.replaceAll("@endDateParam@",endDate);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getGaugeChart");
		logger.debug("getGaugeChart : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),GaugeChartResponse.class);
	}

	@Override
	public StackedChartResponse getStackedChart(HomeBean homeBean,String locationId, String resourceId,String stackedChartType) throws Exception {
		try {
			String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_STAKED_CHART.getRequestURI());
			endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
			endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);
			endPointUrl = endPointUrl.replaceAll("@stackedChartTypeParam@",stackedChartType);

			ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getStackedChart");
			logger.debug("getStackedChart : response :  "+responseModel.getData());
			return AdminUtils.getDataFromJSON(responseModel.getData(),StackedChartResponse.class);
		} catch (final Exception excep) {
			logger.error("Error while getting getStackedChart: " + excep.getMessage());
			return null;
		}
	}

	@Override
	public PieChartResponse getPieChart(HomeBean homeBean,String locationId,String selectedDate) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PIE_CHART.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@selectedDateParam@",selectedDate);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPieChart");
		logger.debug("getPieChart : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),PieChartResponse.class);
	}

	@Override
	public ApptSysConfig getApptSysConfigs(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_APPT_SYS_CONFIG.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getApptSysConfigs");
		logger.debug("getApptSysConfigs : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ApptSysConfigResponse.class).getApptSysConfig();
	}

	@Override
	public DisplayNames getDisplayNames(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_DISPLAY_NAMES.getRequestURI());

		endPointUrl = endPointUrl.replaceAll("@deviceParam@","admin");
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getDisplayNames");
		logger.debug("getDisplayNames : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),DisplayNamesResponse.class).getDisplayNames();
	}

	@Override
	public PrivilegedPageNamesResponse getAccessPrivilegedPageNames(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PRIVILAGED_PAGE_NAMES.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAccessPrivilegedPageNames");
		logger.debug("getAccessPrivilegedPageNames : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),PrivilegedPageNamesResponse.class);
	}

	@Override
	public ServiceLocationApptDatesResponse getServiceLocationApptDatesWindow(HomeBean homeBean,String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SERVICE_LOCATION_APPT_DATES_WINDOW.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getServiceLocationApptDatesWindow");
		logger.debug("getServiceLocationApptDatesWindow : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceLocationApptDatesResponse.class);
	}

	@Override
	public BaseResponse updateServiceLocationApptDatesWindow(HomeBean homeBean,ServiceLocationApptDatesRequest serviceLocApptDatesReq) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_GET_UPDATE_SERVICE_LOCATION_APPT_DATES_WINDOW.getRequestURI();
		serviceLocApptDatesReq.setClientCode(homeBean.getUserLoginResponse().getClientCode());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,serviceLocApptDatesReq,"updateServiceLocationApptDatesWindow");
		logger.debug("updateServiceLocationApptDatesWindow ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse updateLocationsApptDates(HomeBean homeBean,LocationsApptDatesRequest locationApptReq) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_GET_UPDATE_LOCATION_APPT_DATES_WINDOW.getRequestURI();
		locationApptReq.setClientCode(homeBean.getUserLoginResponse().getClientCode());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,locationApptReq,"updateLocationsApptDates");
		logger.debug("updateLocationsApptDates ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse updateApptRestrictDates(HomeBean homeBean,String apptStartDate,String apptEndDate) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_UPDATE_APPT_RESTRICT_DATES.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@apptStartDateParam@",apptStartDate);
		endPointUrl = endPointUrl.replaceAll("@apptEndDateParam@",apptEndDate);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"updateApptRestrictDates");
		logger.debug("updateApptRestrictDates : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse updateApptPerSeasonDetails(HomeBean homeBean,String termStartDate,String termEndDate,String noApptPerTerm) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_UPDATE_APPT_PER_SEASON_DETAILS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@termStartDateParam@",termStartDate);
		endPointUrl = endPointUrl.replaceAll("@termEndDateParam@",termEndDate);
		endPointUrl = endPointUrl.replaceAll("@noApptPerTermParam@",noApptPerTerm);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"updateApptPerSeasonDetails");
		logger.debug("updateApptPerSeasonDetails : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse updateScheduleClosedStatus(HomeBean homeBean,String closedStatus) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_UPDATE_SCHEDULER_CLOSED_STATUS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@closedStatusParam@",closedStatus);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"updateScheduleClosedStatus");
		logger.debug("updateScheduleClosedStatus : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	//Generic Functionality
	@Override
	public DynamicFieldDisplayResponse getDynamicFieldDisplayData(HomeBean homeBean,String pageName) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_DYNAMIC_DISPLAY_FIELDS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@pageNameParam@",pageName);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getDynamicFieldDisplayData");
		logger.debug("getDynamicFieldDisplayData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),DynamicFieldDisplayResponse.class);
	}

	@Override
	public LocationResponse getActiveLocationDropDownData(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_ACTIVE_LOCATION_DROP_DOWN_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getActiveLocationDropDownData");
		System.out.println("getActiveLocationDropDownData : response :  "+responseModel.getData());
		logger.debug("getActiveLocationDropDownData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),LocationResponse.class);
	}

	@Override
	public ResourceResponse getActiveResourceDropDownData(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_ACTIVE_RESOURCE_DROP_DOWN_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getActiveResourceDropDownData");
		System.out.println("getActiveResourceDropDownData : response :  "+responseModel.getData());
		logger.debug("getActiveResourceDropDownData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceResponse.class);
	}

	@Override
	public ServiceResponse getActiveServiceDropDownData(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_ACTIVE_SERVICE_DROP_DOWN_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getActiveServiceDropDownData");
		System.out.println("getActiveServiceDropDownData : response :  "+responseModel.getData());
		logger.debug("getActiveServiceDropDownData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class);
	}

	//Location Related Functionality
	@Override
	public Location getLocationById(HomeBean homeBean,String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_LOCATION_BY_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getLocationById");
		logger.debug("getLocationById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),LocationResponse.class).getLocation();
	}

	@Override
	public List<Location> getLocationListForLoginUser(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_LOCATION_LIST_FOR_LOGIN_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getLocationListForLoginUser");
		logger.debug("getLocationListForLoginUser : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),LocationResponse.class).getLocationList();
	}

	@Override
	public LocationResponse getAllLocationsBasicData(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_LOCATIONS_BASIC_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAllLocationsBasicData");
		System.out.println("getAllLocationsBasicData : response :  "+responseModel.getData());
		logger.debug("getAllLocationsBasicData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),LocationResponse.class);
	}

	@Override
	public LocationResponse saveLocation(HomeBean homeBean,Location location) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_SAVE_LOCATION.getRequestURI();
		location.setClientCode(homeBean.getUserLoginResponse().getClientCode());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,location,"saveLocation");
		logger.debug("saveLocation ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),LocationResponse.class);
	}

	@Override
	public Location getCompleteLocationDataById(HomeBean homeBean,String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_COMPLETE_LOCATION_DATA_BY_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCompleteLocationDataById");
		logger.debug("getCompleteLocationDataById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),LocationResponse.class).getLocation();
	}

	@Override
	public BaseResponse updateLocation(HomeBean homeBean,Location location) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_UPDATE_LOCATION.getRequestURI();
		location.setClientCode(homeBean.getUserLoginResponse().getClientCode());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,location,"updateLocation");
		logger.debug("updateLocation ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse deleteLocation(HomeBean homeBean,String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_DELETE_LOCATION.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"deleteLocation");
		logger.debug("deleteLocation : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse unDeleteLocation(HomeBean homeBean,String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_UN_DELETE_LOCATION.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"unDeleteLocation");
		logger.debug("unDeleteLocation : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	//Resource Related Functionality
	@Override
	public Resource getResourceById(HomeBean homeBean,String resourceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_BY_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourceById");
		logger.debug("getResourceById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceResponse.class).getResource();
	}

	@Override
	public List<Resource> getResourceListForLoginUser(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_LIST_FOR_LOGIN_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourceListForLoginUser");
		logger.debug("getResourceListForLoginUser : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceResponse.class).getResourceList();
	}

	@Override
	public List<Resource> getResourceListByLocationIdForLoginUser(HomeBean homeBean, String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_LIST_BY_LOCATION_ID_FOR_LOGIN_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		System.out.println("getResourceListByLocationIdForLoginUser : endPointUrl :  "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourceListByLocationIdForLoginUser");
		System.out.println("getResourceListByLocationIdForLoginUser : response :  "+responseModel.getData());
		logger.debug("getResourceListByLocationIdForLoginUser : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceResponse.class).getResourceList();
	}

	@Override
	public ResourceResponse getAllResourcesBasicData(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_BASIC_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAllResourcesBasicData");
		System.out.println("getAllResourcesBasicData : response :  "+responseModel.getData());
		logger.debug("getAllResourcesBasicData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceResponse.class);
	}

	@Override
	public ResourcePrefixResponse getResourcePrefixList(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_PREFIX_LIST.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourcePrefixList");
		System.out.println("getResourcePrefixList : response :  "+responseModel.getData());
		logger.debug("getResourcePrefixList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourcePrefixResponse.class);
	}

	@Override
	public ResourceTitleResponse getResourceTitleList(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_TITLE_LIST.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourceTitleList");
		System.out.println("getResourceTitleList : response :  "+responseModel.getData());
		logger.debug("getResourceTitleList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceTitleResponse.class);
	}

	@Override
	public ResourceTypeResponse getResourceTypeList(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_TYPE_LIST.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourceTypeList");
		System.out.println("getResourceTypeList : response :  "+responseModel.getData());
		logger.debug("getResourceTypeList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceTypeResponse.class);
	}

	@Override
	public ResourceResponse saveResource(HomeBean homeBean,Resource resource) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_SAVE_RESOURCE.getRequestURI();
		resource.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,resource,"saveResource");
		System.out.println("saveResource : response :  "+responseModel.getData());
		System.out.println("------------------------------------------------\n\n");
		logger.debug("saveResource ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceResponse.class);
	}

	@Override
	public Resource getCompleteResourceDataById(HomeBean homeBean,String resourceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_COMPLETE_RESOURCE_DATA_BY_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCompleteResourceDataById");
		System.out.println("getCompleteResourceDataById : response :  "+responseModel.getData());
		logger.debug("getCompleteResourceDataById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceResponse.class).getResource();
	}

	@Override
	public BaseResponse updateResource(HomeBean homeBean,Resource resource) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_UPDATE_RESOURCE.getRequestURI();
		resource.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		System.out.println("\n\n------------------------------------------------");
		System.out.println("updateResource ::: "+AdminUtils.getJSONDataStr(resource));
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,resource,"updateResource");
		System.out.println("updateResource ::: response :  "+responseModel.getData());
		System.out.println("------------------------------------------------\n\n");
		logger.debug("updateResource ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse deleteResource(HomeBean homeBean,String resourceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_DELETE_RESOURCE.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"deleteResource");
		System.out.println("deleteResource : response :  "+responseModel.getData());
		logger.debug("deleteResource : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse unDeleteResource(HomeBean homeBean,String resourceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_UN_DELETE_RESOURCE.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"unDeleteResource");
		System.out.println("unDeleteResource : response :  "+responseModel.getData());
		logger.debug("unDeleteResource : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	//Service Related Functionality
	@Override
	public ServiceVO getServiceById(HomeBean homeBean,String serviceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SERVICE_BY_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getServiceById");
		logger.debug("getServiceById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class).getService();
	}

	@Override
	public List<ServiceVO> getServiceListForLoginUser(HomeBean homeBean,String onlyActive) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SERVICE_LIST_FOR_LOGIN_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
		endPointUrl = endPointUrl.replaceAll("@@onlyActiveParam@",onlyActive);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getServiceListForLoginUser");
		logger.debug("getServiceListForLoginUser : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class).getServiceList();
	}

	@Override
	public List<ServiceVO> getServiceListByLocationIdForLoginUser(HomeBean homeBean, String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SERVICE_LIST_BY_LOCATION_ID_FOR_LOGIN_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getServiceListByLocationIdForLoginUser");
		logger.debug("getServiceListByLocationIdForLoginUser : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class).getServiceList();
	}

	@Override
	public List<ServiceVO> getServiceListByResourceIdForLoginUser(HomeBean homeBean, String resourceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SERVICE_LIST_BY_RESOURCE_ID_FOR_LOGIN_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",(resourceId!=null ? resourceId.replaceAll(",", "%2C") : ""));
		System.out.println("endPointUrl ::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getServiceListByResourceIdForLoginUser");
		logger.debug("getServiceListByResourceIdForLoginUser : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class).getServiceList();
	}

	@Override
	public ServiceResponse getAllServicesBasicData(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SERVICES_BASIC_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAllServicesBasicData");
		System.out.println("getAllServicesBasicData : response :  "+responseModel.getData());
		logger.debug("getAllServicesBasicData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class);
	}

	@Override
	public ServiceResponse saveService(HomeBean homeBean,ServiceVO service) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_SAVE_SERVICE.getRequestURI();
		service.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		System.out.println("saveService ::: "+AdminUtils.getJSONDataStr(service));
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,service,"saveService");
		System.out.println("saveService : response :  "+responseModel.getData());
		logger.debug("saveService ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class);
	}

	@Override
	public ServiceVO getCompleteServiceDataById(HomeBean homeBean,String serviceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_COMPLETE_SERVICE_DATA_BY_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCompleteServiceDataById");
		System.out.println("getCompleteServiceDataById : response :  "+responseModel.getData());
		logger.debug("getCompleteServiceDataById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class).getService();
	}

	@Override
	public BaseResponse updateService(HomeBean homeBean,ServiceVO service) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_UPDATE_SERVICE.getRequestURI();
		service.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		System.out.println("updateService ::: "+AdminUtils.getJSONDataStr(service));
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,service,"updateService");
		System.out.println("updateService ::: response :  "+responseModel.getData());
		logger.debug("updateService ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse deleteService(HomeBean homeBean,String serviceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_DELETE_SERVICE.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"deleteService");
		System.out.println("deleteService : response :  "+responseModel.getData());
		logger.debug("deleteService : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse unDeleteService(HomeBean homeBean,String serviceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_UN_DELETE_SERVICE.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"unDeleteService");
		System.out.println("unDeleteService : response :  "+responseModel.getData());
		logger.debug("unDeleteService : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public LocationResponse getLocationsByServiceIdToCloseServiceStatus(HomeBean homeBean,String serviceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_LOCATIONS_BY_SERVICE_ID_TO_CLOSE_SERVICE_STATUS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getLocationsByServiceIdToCloseServiceStatus");
		System.out.println("getLocationsByServiceIdToCloseServiceStatus : response :  "+responseModel.getData());
		logger.debug("getLocationsByServiceIdToCloseServiceStatus : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),LocationResponse.class);
	}

	//Call Report Related Functionality
	@Override
	public InBoundCallsResponse getInBoundCallLogs(HomeBean homeBean,String fromDate,String toDate) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_IN_BOUND_CALL_LOGS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getInBoundCallLogs");
		System.out.println("getInBoundCallLogs : response :  "+responseModel.getData());
		logger.debug("getInBoundCallLogs : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),InBoundCallsResponse.class);
	}

	@Override
	public OutBoundCallsResponse getOutBoundCallLogs(HomeBean homeBean,String fromDate,String toDate) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_OUT_BOUND_CALL_LOGS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getOutBoundCallLogs");
		System.out.println("getOutBoundCallLogs : response :  "+responseModel.getData());
		logger.debug("getOutBoundCallLogs : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),OutBoundCallsResponse.class);
	}

	@Override
	public TransStateResponse getTransStates(HomeBean homeBean,String transId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_TRANS_STATES.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@transIdParam@",transId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getTransStates");
		System.out.println("getTransStates : response :  "+responseModel.getData());
		logger.debug("getTransStates : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),TransStateResponse.class);
	}

	//User Related Functionality
	@Override
	public UserResponse getUsers(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_USERS.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getUsers");
		System.out.println("getUsers : response :  "+responseModel.getData());
		logger.debug("getUsers : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),UserResponse.class);
	}

	@Override
	public BaseResponse saveUser(HomeBean homeBean,AdminLogin adminLogin) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_SAVE_USER.getRequestURI();
		adminLogin.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		System.out.println("saveUser ::: "+AdminUtils.getJSONDataStr(adminLogin));
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,adminLogin,"saveUser");
		System.out.println("saveUser : response :  "+responseModel.getData());
		logger.debug("saveUser ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public AdminLogin getUserById(HomeBean homeBean,String serviceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_USER_DATA_BY_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@userIdParam@",serviceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getUserById");
		System.out.println("getUserById : response :  "+responseModel.getData());
		logger.debug("getUserById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),UserResponse.class).getAdminLogin();
	}

	@Override
	public BaseResponse updateUser(HomeBean homeBean,AdminLogin adminLogin) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_UPDATE_USER.getRequestURI();
		adminLogin.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		System.out.println("updateUser ::: "+AdminUtils.getJSONDataStr(adminLogin));
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,adminLogin,"updateUser");
		System.out.println("updateUser ::: response :  "+responseModel.getData());
		logger.debug("updateUser ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse deleteUser(HomeBean homeBean,String userId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_DELETE_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@userIdParam@",userId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"deleteUser");
		System.out.println("deleteUser : response :  "+responseModel.getData());
		logger.debug("deleteUser : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public PasswordComplexity getPasswordComplexity(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PASSWORD_COMPLEXITY.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPasswordComplexity");
		System.out.println("getPasswordComplexity : response :  "+responseModel.getData());
		logger.debug("getPasswordComplexity : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),PasswordComplexity.class);
	}
	
	@Override
	public PasswordComplexity getPasswordComplexityLogicByUserName(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PASSWORD_COMPLEXITY_BY_USERNAME.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@userNameParam@",homeBean.getUserLoginResponse().getUserName());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPasswordComplexity");
		System.out.println("getPasswordComplexity : response :  "+responseModel.getData());
		logger.debug("getPasswordComplexity : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),PasswordComplexity.class);
	}

	@Override
	public BaseResponse validateUserName(HomeBean homeBean,String userId, String userName) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_VALIDATE_USER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@userIdParam@",userId);
		endPointUrl = endPointUrl.replaceAll("@userNameParam@",userName);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"validateUserName");
		System.out.println("validateUserName : response :  "+responseModel.getData());
		logger.debug("validateUserName : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	//Search Related Functionality
	@Override
	public SearchResponse getSearchResponse(HomeBean homeBean,String searchSelectionType,String firstName,String lastName
			,String confirmationNumber,String accountNumber,String contactPhone,String callerId,String attrib1,String dob,String houseHoldId) throws Exception {
		if("FIRSTNAME_LASTNAME".equalsIgnoreCase(searchSelectionType)){
			return searchByFirstLastName(homeBean, firstName, lastName);
		} else if("CONFIRMATION_NO".equalsIgnoreCase(searchSelectionType)){
			return searchByConfirmationNumber(homeBean, confirmationNumber);
		} else if("SSN".equalsIgnoreCase(searchSelectionType)){
			return searchByAccountNumber(homeBean, accountNumber);
		} else if("SSN_LAST_7".equalsIgnoreCase(searchSelectionType)){
			return searchByAccountNumber(homeBean, accountNumber);
		} else if("CONTACT_PHONE".equalsIgnoreCase(searchSelectionType)){
			return searchByContactPhone(homeBean, contactPhone);
		} else if("CALLER_ID".equalsIgnoreCase(searchSelectionType)){
			return searchByCallerId(homeBean, callerId);
		} else if("ENERGY_ACCT_NO".equalsIgnoreCase(searchSelectionType)){
			return searchByAttrib1(homeBean, attrib1);
		} else if("DOB".equalsIgnoreCase(searchSelectionType)){
			return searchByDOB(homeBean, dob);
		} else if("HOUSE_HOLD".equalsIgnoreCase(searchSelectionType)){
			return searchByHouseHoldId(homeBean, houseHoldId);
		} else {
			return null;
		}
	}

	@Override
	public SearchResponse searchByFirstLastName(HomeBean homeBean,String firstName,String lastName) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_FIRST_NAME_LAST_NAME.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@firstNameParam@",firstName);
		endPointUrl = endPointUrl.replaceAll("@lastNameParam@",lastName);
		return performSearchOperation(endPointUrl,"searchByFirstLastName");
	}

	@Override
	public SearchResponse searchByConfirmationNumber(HomeBean homeBean,String confirmationNumber) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_CONFIRMATION_NUMBER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@confirmationNumberParam@",confirmationNumber);
		return performSearchOperation(endPointUrl,"searchByConfirmationNumber");
	}

	@Override
	public SearchResponse searchByAccountNumber(HomeBean homeBean,String accountNumber) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_ACCOUNT_NUMBER.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@accountNumberParam@",accountNumber);
		return performSearchOperation(endPointUrl,"searchByAccountNumber");
	}

	@Override
	public SearchResponse searchByContactPhone(HomeBean homeBean,String contactPhone) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_CONTACT_PHONE.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@contactPhoneParam@",contactPhone);
		return performSearchOperation(endPointUrl,"searchByContactPhone");
	}

	@Override
	public SearchResponse searchByCallerId(HomeBean homeBean,String callerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_CALLER_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@callerIdParam@",callerId);
		return performSearchOperation(endPointUrl,"searchByCallerId");
	}

	@Override
	public SearchResponse searchByAttrib1(HomeBean homeBean,String attrib1) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_ATTRIB1.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@attrib1Param@",attrib1);
		return performSearchOperation(endPointUrl,"searchByAttrib1");
	}

	@Override
	public SearchResponse searchByDOB(HomeBean homeBean,String dob) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_DOB.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@dobParam@",dob);		
		return performSearchOperation(endPointUrl,"searchByDOB");
	}

	@Override
	public SearchResponse searchByHouseHoldId(HomeBean homeBean,String houseHoldId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SEARCH_BY_HOUSE_HOLD_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@houseHoldIdParam@",houseHoldId);		
		return performSearchOperation(endPointUrl,"searchByHouseHoldId");
	}

	private SearchResponse performSearchOperation(String endPointUrl,String method) throws Exception{
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,method);
		System.out.println(method+" : response :  "+responseModel.getData());
		logger.debug(method+" : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),SearchResponse.class);
	}

	@Override
	public DynamicSearchDropDownResponse getSearchDropDownList(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SEARCH_DROP_DOWN_LIST.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getSearchDropDownList");
		System.out.println("getSearchDropDownList : response :  "+responseModel.getData());
		logger.debug("getSearchDropDownList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),DynamicSearchDropDownResponse.class);
	}

	//Search - Appointments related Functionality
	@Override
	public SearchResponse getAppointmentsByCustomerId(HomeBean homeBean,String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_APPOINTMENTS_BY_CUSTOMER_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);		

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAppointmentsByCustomerId");
		System.out.println("getAppointmentsByCustomerId : response :  "+responseModel.getData());
		logger.debug("getAppointmentsByCustomerId : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),SearchResponse.class);
	}
	
	@Override
	public AppointmentStatusDropDownResponse getAppointmentStatusDropDownList(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_APPOINTMENT_STATUS_DROP_DOWN_LIST.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAppointmentStatusDropDownList");
		System.out.println("getAppointmentStatusDropDownList : response :  "+responseModel.getData());
		logger.debug("getAppointmentStatusDropDownList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),AppointmentStatusDropDownResponse.class);
	}

	@Override
	public BaseResponse updateAppointmentStatus(HomeBean homeBean,String scheduleId,String status) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_UPDATE_APPOINTMENT_STATUS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@statusParam@",status);
		endPointUrl = endPointUrl.replaceAll("@userNameParam@",homeBean.getUserLoginResponse().getUserName());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"updateAppointmentStatus");
		System.out.println("updateAppointmentStatus : response :  "+responseModel.getData());
		logger.debug("updateAppointmentStatus : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public CancelAppointResponse cancelAppointment(HomeBean homeBean,String scheduleId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_CANCEL_APPOINTMENT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@","us-en");

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"cancelAppointment");
		System.out.println("cancelAppointment : response :  "+responseModel.getData());
		logger.debug("cancelAppointment : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CancelAppointResponse.class);
	}

	//Search - Customers related Functionality
	@Override
	public CustomersResponse getCustomersById(HomeBean homeBean,String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CUSTOMERS_BY_CUSTOMER_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		logger.info("getCustomersById URL: " + endPointUrl);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCustomersById");
		logger.info("getCustomersById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomersResponse.class);
	}

	//Search - Customers Activity related Functionality
	@Override
	public CustomerActivityResponse getCustomerActivitiesByCustomerId(HomeBean homeBean,String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CUSTOMER_ACTIVITY_BY_CUSTOMER_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);		

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCustomerActivitiesByCustomerId");
		System.out.println("getCustomerActivitiesByCustomerId : response :  "+responseModel.getData());
		logger.debug("getCustomerActivitiesByCustomerId : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerActivityResponse.class);
	}

	//Search - HouseHold related Functionality
	@Override
	public CustomersResponse getHouseHoldInfoByCustomerId(HomeBean homeBean,String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_HOUSE_HOLD_INFO_BY_CUSTOMER_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);		

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getHouseHoldInfoByCustomerId");
		System.out.println("getHouseHoldInfoByCustomerId : response :  "+responseModel.getData());
		logger.debug("getHouseHoldInfoByCustomerId : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomersResponse.class);
	}

	@Override
	public BaseResponse mergeHouseHoldId(HomeBean homeBean,String fromHouseHoldIds,String mergeToHouseHoldId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_MERGE_HOUSEHOLD_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromHouseHoldIdsParam@",fromHouseHoldIds);
		endPointUrl = endPointUrl.replaceAll("@mergeToHouseHoldIdParam@",mergeToHouseHoldId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"mergeHouseHoldId");
		System.out.println("mergeHouseHoldId : response :  "+responseModel.getData());
		logger.debug("mergeHouseHoldId : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse splitHouseHoldId(HomeBean homeBean,String customerIds,String newHouseHoldId,String assignNewHouseholdID) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_SPLIT_HOUSEHOLD_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdsParam@",customerIds);
		endPointUrl = endPointUrl.replaceAll("@newHouseHoldIdParam@",newHouseHoldId);
		endPointUrl = endPointUrl.replaceAll("@assignNewHouseholdIDParam@",assignNewHouseholdID);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"splitHouseHoldId");
		System.out.println("splitHouseHoldId : response :  "+responseModel.getData());
		logger.debug("splitHouseHoldId : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public BaseResponse updateHouseHoldId(HomeBean homeBean,String customerId,String newHouseHoldId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_UPDATE_HOUSEHOLD_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll("@newHouseHoldIdParam@",newHouseHoldId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"updateHouseHoldId");
		System.out.println("updateHouseHoldId : response :  "+responseModel.getData());
		logger.debug("updateHouseHoldId : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}
	
	//Search - Pledge related Functionality
	@Override
	public CustomerPledgeResponse getPledgeHistoryByCustomerId(HomeBean homeBean,String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PLEDGE_HISTORY_BY_CUSTOMER_ID.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);		
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",ApptAdminContants.ONLINE.getValue());
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",ApptAdminContants.LANG_CODE.getValue());
		endPointUrl = endPointUrl.replaceAll("@transIdParam@","1234");
		System.out.println("getPledgeHistoryByCustomerId : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPledgeHistoryByCustomerId");
		System.out.println("getPledgeHistoryByCustomerId : response :  "+responseModel.getData());
		logger.debug("getPledgeHistoryByCustomerId : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerPledgeResponse.class);
	}

	//Reports related Functionality
	//Reports - Appointment Reports related Functionality
	@Override
	public String getDynamicIncludeReportsData(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_DYNAMIC_INCLUDE_REPORTS_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getDynamicIncludeReportsData");
		System.out.println("getDynamicIncludeReportsData : response :  "+responseModel.getData());
		logger.debug("getDynamicIncludeReportsData : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	@Override
	public String getAppointmentReport(HomeBean homeBean,String fromDate,String toDate,String locationIds,String resourceIds,String serviceIds,String apptStatus) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_APPOINTMENT_REPORT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);
		endPointUrl = endPointUrl.replaceAll("@locationIdsParam@",locationIds);
		endPointUrl = endPointUrl.replaceAll("@resourceIdsParam@",resourceIds);
		endPointUrl = endPointUrl.replaceAll("@serviceIdsParam@",serviceIds);
		endPointUrl = endPointUrl.replaceAll("@apptStatusParam@",apptStatus);
		System.out.println("getAppointmentReport : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAppointmentReport");
		System.out.println("getAppointmentReport : response :  "+responseModel.getData());
		logger.debug("getAppointmentReport : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	//Reports - Itemized Reports related Functionality
	@Override
	public String getSummaryReport(HomeBean homeBean,String fromDate,String toDate,String reportCategory) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SUMMARY_REPORT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);
		endPointUrl = endPointUrl.replaceAll("@reportCategoryParam@",reportCategory);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getSummaryReport");
		System.out.println("getSummaryReport : response :  "+responseModel.getData());
		logger.debug("getSummaryReport : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	//Reports - Summary Reports related Functionality
	@Override
	public String getSummaryStatisticsReport(HomeBean homeBean,String fromDate,String toDate,String locationId,String serviceId,String reportCategory) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SUMMARY_STATISTICS_REPORT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);
		endPointUrl = endPointUrl.replaceAll("@reportCategoryParam@",reportCategory);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getSummaryStatisticsReport");
		System.out.println("getSummaryStatisticsReport : response :  "+responseModel.getData());
		logger.debug("getSummaryStatisticsReport : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getItemizedReportTemplate(HomeBean homeBean,String fromDate,String toDate,String locationId,String serviceId,String reportCategory) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SUMMARY_STATISTICS_REPORT_TEMPLATE.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);
		endPointUrl = endPointUrl.replaceAll("@reportCategoryParam@",reportCategory);

		System.out.println("getItemizedReportTemplate : endPointUrl :  "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getItemizedReportTemplate");
		System.out.println("getItemizedReportTemplate : response :  "+responseModel.getData());
		logger.debug("getItemizedReportTemplate : response :  "+responseModel.getData());
		return (LinkedHashMap<String,Object>)responseModel.getData();
	}

	//Reports - Automatic Email related Functionality
	@Override
	public String getAppointmentReportConfig(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_APPT_REPOT_CONFIG.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@userNameParam@",homeBean.getUserLoginResponse().getUserName());		

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAppointmentReportConfig");
		System.out.println("getAppointmentReportConfig : response :  "+responseModel.getData());
		logger.debug("getAppointmentReportConfig : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	@Override
	public String addAppointmentReportConfig(HomeBean homeBean,AppointmentReportConfig apptReportConfig) throws Exception {
		apptReportConfig.setUserName(homeBean.getUserLoginResponse().getUserName());
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_ADD_APPT_REPOT_CONFIG.getRequestURI();
		apptReportConfig.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,apptReportConfig,"addAppointmentReportConfig");
		System.out.println("addAppointmentReportConfig ::: response :  "+responseModel.getData());
		logger.debug("addAppointmentReportConfig ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	@Override
	public String deleteApptReportConfigById(HomeBean homeBean,String configId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_DELETE_APPT_REPOT_CONFIG.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@configIdParam@",configId);		

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"deleteApptReportConfigById");
		System.out.println("deleteApptReportConfigById : response :  "+responseModel.getData());
		logger.debug("deleteApptReportConfigById : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	//Reports - Pledge Report related Functionality
	@Override
	public String getPledgeReport(HomeBean homeBean,String fromDate,String toDate,String locationId,String resourceId,String fundSourceId,String pledgeReportRequestType) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PLEDGE_REPORT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);
		endPointUrl = endPointUrl.replaceAll("@fundSourceIdParam@",fundSourceId);

		if(pledgeReportRequestType!=null && !"Complete".equals(pledgeReportRequestType)){
			if("Group_By_Resource".equals(pledgeReportRequestType)){
				endPointUrl = endPointUrl.replaceAll("@groupByIntakeParam@","Y");
				endPointUrl = endPointUrl.replaceAll("@groupByFundSourceParam@", "N");
				endPointUrl = endPointUrl.replaceAll("@groupByVendorParam@","N");
			}else if("Group_By_Fund_Source".equals(pledgeReportRequestType)){
				endPointUrl = endPointUrl.replaceAll("@groupByIntakeParam@","N");
				endPointUrl = endPointUrl.replaceAll("@groupByFundSourceParam@", "Y");
				endPointUrl = endPointUrl.replaceAll("@groupByVendorParam@","N");
			}else if("Group_By_Vendor".equals(pledgeReportRequestType)){
				endPointUrl = endPointUrl.replaceAll("@groupByIntakeParam@","N");
				endPointUrl = endPointUrl.replaceAll("@groupByFundSourceParam@", "N");
				endPointUrl = endPointUrl.replaceAll("@groupByVendorParam@","Y");
			}else {
				endPointUrl = endPointUrl.replaceAll("@groupByIntakeParam@","N");
				endPointUrl = endPointUrl.replaceAll("@groupByFundSourceParam@", "N");
				endPointUrl = endPointUrl.replaceAll("@groupByVendorParam@","N");
			}
		}else{
			endPointUrl = endPointUrl.replaceAll("@groupByIntakeParam@","N");
			endPointUrl = endPointUrl.replaceAll("@groupByFundSourceParam@", "N");
			endPointUrl = endPointUrl.replaceAll("@groupByVendorParam@","N");
		}
		System.out.println("getPledgeReport : endPointUrl :  "+endPointUrl);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getSummaryStatisticsReport");
		System.out.println("getPledgeReport : response :  "+responseModel.getData());
		logger.debug("getPledgeReport : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	@Override
	public String getDynamicPledgeResults(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_DYNAMIC_PLEDGE_REPORTS_DATA.getRequestURI());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getDynamicPledgeResult");
		System.out.println("getDynamicPledgeResults : response :  "+responseModel.getData());
		logger.debug("getDynamicPledgeResults : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}
	
	@Override
	public CustomerPledgeResponse addCustomerPledgeDetails(HomeBean homeBean,CustomerPledgeRequest customerPledgeReq) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_SAVE_PLEDGE_DETAILS.getRequestURI();
		customerPledgeReq.setClientCode(homeBean.getUserLoginResponse().getClientCode());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,customerPledgeReq,"addCustomerPledgeDetails");
		logger.debug("addCustomerPledgeDetails ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerPledgeResponse.class);
	}
	
	@Override
	public CustomerPledgeResponse updateCustomerPledgeDetails(HomeBean homeBean,CustomerPledgeRequest customerPledgeReq) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_UPDATE_PLEDGE_DETAILS.getRequestURI();
		customerPledgeReq.setClientCode(homeBean.getUserLoginResponse().getClientCode());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,customerPledgeReq,"updateCustomerPledgeDetails");
		logger.debug("updateCustomerPledgeDetails ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerPledgeResponse.class);
	}
	
	@Override
	public CustomerPledgeStatusResponse getCustomerPledgeStatusList(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CUST_PLEDGE_STATUS_LIST.getRequestURI());
		System.out.println("getCustomerPledgeStatusList ::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCustomerPledgeStatusList");
		System.out.println("getCustomerPledgeStatusList : response :  "+responseModel.getData());
		logger.debug("getCustomerPledgeStatusList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerPledgeStatusResponse.class);
	}
	
	@Override
	public CustomerPledgeFundSourceResponse getCustomerPledgeFundSourceList(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CUST_PLEDGE_FUND_SOURCE_LIST.getRequestURI());
		System.out.println("getCustomerPledgeFundSourceList ::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCustomerPledgeFundSourceList");
		System.out.println("getCustomerPledgeFundSourceList : response :  "+responseModel.getData());
		logger.debug("getCustomerPledgeFundSourceList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerPledgeFundSourceResponse.class);
	}
	
	@Override
	public CustomerPledgeVendorResponse getCustomerPledgeVendorList(HomeBean homeBean,String fundId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CUST_PLEDGE_VENDOR_LIST.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@fundIdParam@",fundId);
		System.out.println("getCustomerPledgeVendorList ::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCustomerPledgeVendorList");
		System.out.println("getCustomerPledgeVendorList : response :  "+responseModel.getData());
		logger.debug("getCustomerPledgeVendorList : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerPledgeVendorResponse.class);
	}

	//Table Print View related Functionality
	@Override
	public String getTablePrintView(HomeBean homeBean,String locationId,String resourceIds,String date) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_TABLE_PRINT_VIEW.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@resourceIdsParam@",resourceIds);
		endPointUrl = endPointUrl.replaceAll("@dateParam@",date);
		System.out.println("URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getTablePrintView");
		System.out.println("getTablePrintView : response :  "+responseModel.getData());
		logger.debug("getTablePrintView : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData());
	}

	//Calendar Related
	@Override
	public ResourceServiceResponse getResourceServiceList(HomeBean homeBean,String locationId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_RESOURCE_SERVICE_LIST.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@loginUserIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
		System.out.println("getResourceServiceList :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourceServiceList");
		System.out.println("getResourceServiceList : response :  "+responseModel.getData());
		logger.debug("getResourceServiceList : response :  "+responseModel.getData());

		GsonBuilder gb = new GsonBuilder();
		gb.setPrettyPrinting();
		gb.registerTypeAdapter(Resource.class, new ResourceSerializer());
		Gson gson = gb.create();

		String jsonResponse = gson.toJson(responseModel.getData());
		logger.debug("getResourceServiceList jsonResponse :: "+jsonResponse);
		ResourceServiceResponse resourceServiceResponse = gson.fromJson(jsonResponse, new TypeToken<ResourceServiceResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());

		return resourceServiceResponse;
	}

	@Override
	public Map<String, CalendarAvailability> getAvailableDates(HomeBean homeBean, final String queryString) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CALENDER_AVAILABLE_DATES_NEW.getRequestURI());
        CalanderAvailabilityResponse calanderAvailabilityResponse = null;
        final Map<String, CalendarAvailability> availabilityMap = new HashMap<>();
        try {
            ResponseModel responseModel = null;
            endPointUrl = endPointUrl.concat(queryString);
            logger.info("getAvailableDates :::: URL :::::::::: " + endPointUrl);
            responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getResourceServiceList");

            GsonBuilder gb = new GsonBuilder();
            gb.setPrettyPrinting();
            gb.registerTypeAdapter(Resource.class, new ResourceSerializer());
            Gson gson = gb.create();

            final String jsonResponse = gson.toJson(responseModel.getData());
            calanderAvailabilityResponse = gson.fromJson(jsonResponse, CalanderAvailabilityResponse.class);
            logObjectForDebug("getAvailableDates - response :  ", responseModel.getData());

            final Map<String, PerDateAppts> perDateAppts = calanderAvailabilityResponse.getPerDateAppts();
            perDateAppts.keySet().stream().forEach(s -> {
                final PerDateAppts perDateAppts1 = perDateAppts.get(s);
                final CalendarAvailability calendarAvailability = new CalendarAvailability();

                availabilityMap.put(s, processAvailability(s, perDateAppts1));
            });
            logObjectForDebug("AvailabilityMap after transforming: ", availabilityMap);
		} catch (final Exception excep){
			logger.error("Error while getting available dates",excep);
		}

		return availabilityMap;
	}

    private CalendarAvailability processAvailability(final String s, final PerDateAppts perDateAppts1) {
        final CalendarAvailability calendarAvailability = new CalendarAvailability();
        calendarAvailability.setDate(s);
        if("Y".equals(perDateAppts1.getIsClosed())){
            calendarAvailability.setAvailabilityStr("closed");
            calendarAvailability.setToolTipMessage("Closed");
        }
        if("Y".equals(perDateAppts1.getIsHoliday())){
            calendarAvailability.setAvailabilityStr("holiday");
            calendarAvailability.setToolTipMessage("Holiday");
        }
        if("Y".equals(perDateAppts1.getIsNotAvailable())){
            calendarAvailability.setAvailabilityStr("not-available");
            calendarAvailability.setToolTipMessage("Not Available");
        }
        if("Y".equals(perDateAppts1.getIsSlotAvailable())){
            calendarAvailability.setAvailabilityStr("available");
            calendarAvailability.setToolTipMessage("Available");
        }
        if("Y".equals(perDateAppts1.getIsFullyBooked())){
            calendarAvailability.setAvailabilityStr("booked-full");
            calendarAvailability.setToolTipMessage("Booked Full");
        }
        return calendarAvailability;
    }

    //This is to avoid error when we are using Object as MAP key with JSON response
	static public class ResourceSerializer implements JsonSerializer<Resource>,
	JsonDeserializer<Resource> {
		@Override
		public Resource deserialize(JsonElement je, Type t, JsonDeserializationContext ctx)throws JsonParseException {
			Resource resource = new Resource();

			if (je.isJsonObject()) {
				resource = new Resource();				
			} else {
				resource = new GsonBuilder().create().fromJson(je.getAsString(), new TypeToken<Resource>() {
					private static final long serialVersionUID = 1L;
				}.getType());
			}
			return resource;
		}

		@Override
		public JsonElement serialize(Resource data, Type type, JsonSerializationContext jsonSerializationContext) {
			return  new JsonObject();
		}
	}

	//Daily Calendar
	@Override
	public DailyCalendarResponse getDailyCalendar(HomeBean homeBean,String calendarDate,String locationId,String resourceIds) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_DAILY_CALENDAR.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@calendarDateParam@",calendarDate);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@resourceIdsParam@",resourceIds);
		logObjectForInfo("getDailyCalendar :::: URL :::::::::: ", endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getDailyCalendar");
		logObjectForDebug("getDailyCalendar : response :  ", responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),DailyCalendarResponse.class);
	}

	//Weekly Calendar
	@Override
	public WeeklyCalendarResponse getWeeklyCalendar(final HomeBean homeBean, final String calendarDate, final String locationId, final String resourceIds) throws Exception {

		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_WEEKLY_CALENDAR.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@calendarDateParam@",calendarDate);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@resourceIdsParam@",resourceIds);

		logger.info("getWeeklyCalendar :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getWeeklyCalendar");
		logger.info("getWeeklyCalendar : response :  "+responseModel.getData());
		if(homeBean.getCalendarSessionData() == null){
			homeBean.setCalendarSessionData(new CalendarSessionData());
		}
		return AdminUtils.getDataFromJSON(responseModel.getData(),WeeklyCalendarResponse.class);
	}

	//Monthly Calendar
	@Override
	public MonthlyCalendarResponse getMonthlyCalendar(final HomeBean homeBean, final CalendarRequest calendarRequest) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_MONTHLY_CALENDAR.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@calendarDateParam@",calendarRequest.getCalendarDate());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",calendarRequest.getLocationId());
		endPointUrl = endPointUrl.replaceAll("@resourceIdStrParam@",calendarRequest.getSelectedResourceIds());
		logger.debug("getMonthlyCalendar :::: URL :::::::::: "+endPointUrl);
		final ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getMonthlyCalendar");
		logger.debug("getMonthlyCalendar : response :  "+responseModel.getData());
		logger.debug("getMonthlyCalendar : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),MonthlyCalendarResponse.class);
	}

	//Appointment Related
	@Override
	public CustomerRegistrationRepsonse getCustomerRegistrationDetails(HomeBean homeBean, String langCode) throws Exception {		 
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CUSTOMER_REG_DETAILS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",langCode);
		System.out.println("getCustomerRegistrationDetails :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCustomerRegistrationDetails");
		System.out.println("getCustomerRegistrationDetails : response :  "+responseModel.getData());
		logger.debug("getCustomerRegistrationDetails : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CustomerRegistrationRepsonse.class);
	}

	@Override
	public BaseResponse createCustomer(final HomeBean homeBean, final CustomerRequest customerRequest) throws Exception {
		try {
			String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_CREATE_CUSTOMER.getRequestURI();
			customerRequest.setClientCode(homeBean.getUserLoginResponse().getClientCode());
			logger.info("createCustomer URL: " + endPointUrl + "\n Customer Data: " + customerRequest.toString());
			final ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,customerRequest,"createCustomer");
			logger.info("createCustomer : response :  "+responseModel.getData());
			return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
		} catch (final Exception excep) {
			logger.error("Error while saving customer.",excep);
		}
		return null;
	}

    @Override
    public SaveCustomerResponse createOrUpdateCustomer(final HomeBean homeBean, final CustomerRequest customerRequest) throws Exception {
        SaveCustomerResponse customerResponse = null;
        try {
            String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_CREATE_UPDATE_CUSTOMER.getRequestURI();
            customerRequest.setClientCode(homeBean.getUserLoginResponse().getClientCode());
            logger.info("createCustomer URL: " + endPointUrl + "\n Customer Data: " + customerRequest.toString());
            final ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,customerRequest,"createOrUpdateCustomer");
            logger.info("createCustomer : response :  "+responseModel.getData());
            customerResponse =  AdminUtils.getDataFromJSON(responseModel.getData(),SaveCustomerResponse.class);
        } catch (final Exception excep) {
            logger.error("Error while saving customer.",excep);
        }
        return customerResponse;
    }

	@Override
	public BaseResponse updateCustomer(HomeBean homeBean,CustomerRequest customerRequest) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_UPDATE_CUSTOMER.getRequestURI();
		customerRequest.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		System.out.println("updateCustomer :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,customerRequest,"updateCustomer");
		System.out.println("updateCustomer : response :  "+responseModel.getData());
		logger.debug("updateCustomer : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	@Override
	public AppointmentsDataResponse getFutureAppointments(HomeBean homeBean,String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_FUTURE_APPTS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		System.out.println("getFutureAppointments :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getFutureAppointments");
		System.out.println("getFutureAppointments : response :  "+responseModel.getData());
		logger.debug("getFutureAppointments : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),AppointmentsDataResponse.class);
	}

	@Override
	public AppointmentsDataResponse getPastAppointments(HomeBean homeBean,String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PAST_APPTS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		System.out.println("getPastAppointments :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPastAppointments");
		System.out.println("getPastAppointments : response :  "+responseModel.getData());
		logger.debug("getPastAppointments : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),AppointmentsDataResponse.class);
	}

	@Override
	public HoldAppt holdAppointment(HomeBean homeBean, String locationId,String resourceId, String procedureId, String departmentId,String serviceId, String customerId, String apptDateTime,String device, String langCode, String transId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_HOLD_APPT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",device);
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",langCode);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);		
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);
		endPointUrl = endPointUrl.replaceAll("@procedureIdParam@",procedureId);		
		endPointUrl = endPointUrl.replaceAll("@departmentIdParam@",departmentId);
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);		
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll("@apptDateTimeParam@",apptDateTime);
		endPointUrl = endPointUrl.replaceAll("@transIdParam@",transId);
		
		System.out.println("holdAppointment :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"holdAppointment");
		System.out.println("holdAppointment : response :  "+responseModel.getData());
		logger.debug("holdAppointment : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),HoldAppt.class);
	}

	@Override
	public BaseResponse releaseHoldAppointment(final HomeBean homeBean, final HttpServletRequest request, final String queryString) throws Exception {
        try {
            String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_RELEASE_HOLD_APPT.getRequestURI());
			endPointUrl = endPointUrl.concat(queryString);

			logger.debug("releaseHoldAppointment :::: URL :::::::::: "+endPointUrl);
			final ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"releaseHoldAppointment");
			request.getSession().removeAttribute(CommonConstants.HOLD_APPT_SCHDID_SESSION_CONST);
			logger.debug("releaseHoldAppointment : response :  "+responseModel.getData());
			return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

	@Override
	public List<ServiceVO> holdAppointmentServiceList(final HomeBean homeBean, final  String locationId, final String resourceId, final String serviceId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_HOLD_APPT_SERVICE_LIST.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"holdAppointment");
		logger.debug("holdAppointment : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ServiceResponse.class).getServiceList();
	}

	@Override
	public ConfirmAppointmentResponse rescheduleAppointment(HomeBean homeBean,String locationId, String resourceId, String procedureId,
															String departmentId, String serviceId, String customerId,String apptDateTime,
															String device, String langCode,String transId, String oldscheduleId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_RESCHEDULE_APPT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",device);
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",langCode);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);		
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceId);
		endPointUrl = endPointUrl.replaceAll("@procedureIdParam@",procedureId);		
		endPointUrl = endPointUrl.replaceAll("@departmentIdParam@",departmentId);
		endPointUrl = endPointUrl.replaceAll("@serviceIdParam@",serviceId);		
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll("@apptDateTimeParam@",apptDateTime);
		endPointUrl = endPointUrl.replaceAll("@transIdParam@",transId);
		endPointUrl = endPointUrl.replaceAll("@oldscheduleId@",oldscheduleId);
		System.out.println("rescheduleAppointment :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"rescheduleAppointment");
		System.out.println("rescheduleAppointment : response :  "+responseModel.getData());
		logger.debug("rescheduleAppointment : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ConfirmAppointmentResponse.class);
	}

	@Override
	public ConfirmAppointmentResponse confirmAppointment(final HomeBean homeBean, final ConfirmAppointmentRequest confirmApptReq) throws Exception {
		final String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_CONFIRM_APPT.getRequestURI();
		confirmApptReq.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		logObjectForDebug("confirmAppointment data : ", confirmApptReq);
		final ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,confirmApptReq,"confirmAppointment");
		logObjectForDebug("confirmAppointment : response :  ", responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ConfirmAppointmentResponse.class);
	}

	@Override
	public CancelAppointResponse cancelAppointment(HomeBean homeBean,String langCode, String scheduleId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_CANCEL_APPT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",langCode);
		System.out.println("cancelAppointment :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"cancelAppointment");
		System.out.println("cancelAppointment : response :  "+responseModel.getData());
		logger.debug("cancelAppointment : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CancelAppointResponse.class);
	}
	
	@Override
	public CustomerNamesResponse getCustomerNames(HomeBean homeBean, final String customerName) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_CUSTOMER_NAMES_AUTO_SUGGEST.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@customerNameParam@",customerName);
		logger.debug("getCustomerNames :::: URL :::::::::: "+endPointUrl);
		final ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getCustomerNames");
		logger.debug("getCustomerNames : response :  "+responseModel.getData());
		final CustomerNamesResponse dataFromJSON = AdminUtils.getDataFromJSON(responseModel.getData(), CustomerNamesResponse.class);
		return dataFromJSON;
	}
	@Override
	public CancelAppointResponse cancelAppointment(HomeBean homeBean, String langCode, String scheduleId, String customerId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_CANCEL_APPT.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",langCode);
		System.out.println("cancelAppointment :::: URL :::::::::: "+endPointUrl);
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"cancelAppointment");
		System.out.println("cancelAppointment : response :  "+responseModel.getData());
		logger.debug("cancelAppointment : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),CancelAppointResponse.class);
	}

	@Override
	public BaseResponse updateConfirmAppointment(final HomeBean homeBean, final String url) throws Exception{

        logObjectForInfo("updateConfirmAppointment url: ", url);
        final ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(url,"updateConfirmAppointment");
        logObjectForDebug("updateConfirmAppointment response: ", responseModel);
        return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	//Resource - Edit Hours - Date Range functionality
	@Override
	public ResourceWorkingHrs getSuggestedResourceWorkingHours(HomeBean homeBean,String locationId,String resourceIds,String fromDate,String toDate) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SUGGESTED_RESOURCE_WORKING_HOURS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@resourceIdsParam@",resourceIds);
		endPointUrl = endPointUrl.replaceAll("@fromDateParam@",fromDate);
		endPointUrl = endPointUrl.replaceAll("@toDateParam@",toDate);
		System.out.println("getSuggestedResourceWorkingHours : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getSuggestedResourceWorkingHours");
		System.out.println("getSuggestedResourceWorkingHours : response :  "+responseModel.getData());
		logger.debug("getSuggestedResourceWorkingHours : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceWorkingHrs.class);
	}

	@Override
	public ResourceWorkingHrsResponse updateResourceWorkingHours(HomeBean homeBean,ResourceHoursRequest resourceHoursRequest) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_GET_UPDATE_RESOURCE_WORKING_HOURS.getRequestURI();
		resourceHoursRequest.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		System.out.println("updateResourceWorkingHours : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,resourceHoursRequest,"updateResourceWorkingHours");
		System.out.println("updateResourceWorkingHours : response :  "+responseModel.getData());
		logger.debug("updateResourceWorkingHours ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceWorkingHrsResponse.class);
	}

	//Resource - Edit Hours - One Date functionality
	@Override
	public OneDateWorkingHoursResponse getSuggestedOneDateResourceWorkingHrs(HomeBean homeBean,String locationId,String resourceIds,String date) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_SUGGESTED_ONE_DATE_RESOURCE_WORKING_HOURS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@resourceIdParam@",resourceIds);
		endPointUrl = endPointUrl.replaceAll("@dateParam@",date);
		System.out.println("getSuggestedOneDateResourceWorkingHrs : endPointUrl :  "+endPointUrl);
		
		final ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getSuggestedOneDateResourceWorkingHrs");
		System.out.println("getSuggestedOneDateResourceWorkingHrs : response :  "+responseModel.getData());
		logger.debug("getSuggestedOneDateResourceWorkingHrs : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),OneDateWorkingHoursResponse.class);
	}
	
	@Override
	public ResourceWorkingHrsResponse updateOneDateResourceWorkingHrs(HomeBean homeBean,ResourceWorkingHoursRequest resourceWorkingHoursRequest) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_GET_UPDATE_ONE_DATE_RESOURCE_WORKING_HOURS.getRequestURI();
		resourceWorkingHoursRequest.setClientCode(homeBean.getUserLoginResponse().getClientCode());
		resourceWorkingHoursRequest.setUserName(homeBean.getUserLoginResponse().getUserName());
		System.out.println("updateOneDateResourceWorkingHrs : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,resourceWorkingHoursRequest,"updateOneDateResourceWorkingHrs");
		System.out.println("updateOneDateResourceWorkingHrs ::: response :  "+responseModel.getData());
		logger.debug("updateOneDateResourceWorkingHrs ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),ResourceWorkingHrsResponse.class);
	}
	
	@Override
	public OneDateResourceWorkingHoursResponse getOneDateResourceWorkingHrsDetails(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_ONE_DATE_RESOURCE_WORKING_HOURS_DETAILS.getRequestURI());
		System.out.println("getOneDateResourceWorkingHrsDetails : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getOneDateResourceWorkingHrsDetails");
		System.out.println("getOneDateResourceWorkingHrsDetails : response :  "+responseModel.getData());
		logger.debug("getOneDateResourceWorkingHrsDetails : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),OneDateResourceWorkingHoursResponse.class);
	}
	
	@Override
	public void sendErrorEmail(String clientCode,String requestURI,HttpServletRequest request,Exception ex) {
		emailComponent.sendEmail(clientCode,requestURI,request,ex);
	}

    @Override
    public VerifyPageData getAppointmentInfo(final HomeBean homeBean, final String customerId,
                                             final String scheduleId, final String transId) throws Exception {

        final String queryString = new StringBuffer("&device=").append(CommonConstants.ADMIN_DEVICE)
                .append("&langCode=").append("us-en").append("&customerId=").append(customerId)
                .append("&scheduleId=").append(scheduleId).append("&transId=1").toString();

        String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_VERIFY_PAGE_DATA.getRequestURI());
        endPointUrl = endPointUrl.concat(queryString);

        logger.info("getAppointmentInfo :::: URL :::::::::: "+endPointUrl);
        final ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getVerifyPageData");
        logger.info("cancelAppointment : response :  "+responseModel.getData());
        logger.debug("cancelAppointment : response :  "+responseModel.getData());

        return AdminUtils.getDataFromJSON(responseModel.getData(),AppointmentInfoResponse.class).getVerifyPageData();
    }

    //User Activity Log functionality
    @Override
	public UserActivityLogsResponse getUserActivityLogs(HomeBean homeBean,String startDate,String endDate) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_USER_ACTIVITY_LOG.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@userIdParam@",String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
		endPointUrl = endPointUrl.replaceAll("@startDateParam@",startDate);
		endPointUrl = endPointUrl.replaceAll("@endDateParam@",endDate);
		System.out.println("getUserActivityLogs : endPointUrl :  "+endPointUrl);

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getUserActivityLogs");
		System.out.println("getUserActivityLogs : response :  "+responseModel.getData());
		logger.debug("getUserActivityLogs : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),UserActivityLogsResponse.class);
	}
    
    //Privilege Settings functionality   
    @Override
	public AccessPrivilegeResponse getAccessPrivilege(HomeBean homeBean) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_ACCESSES_PRIVILEGES.getRequestURI());
		System.out.println("getAccessPrivilege : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getAccessPrivilege");
		System.out.println("getAccessPrivilege : response :  "+responseModel.getData());
		logger.debug("getAccessPrivilege : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),AccessPrivilegeResponse.class);
	}
    
    @Override
	public AccessPrivilegeResponse getPrivilegePageMapping(HomeBean homeBean,String accessPrivilegeId) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PRIVILEGE_PAGE_MAPPING.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@accessPrivilegeIdParam@",accessPrivilegeId);
		System.out.println("getPrivilegePageMapping : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPrivilegePageMapping");
		System.out.println("getPrivilegePageMapping : response :  "+responseModel.getData());
		logger.debug("getPrivilegePageMapping : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),AccessPrivilegeResponse.class);
	}
    
    @Override
	public PrivilegeSettingResponse getPrivilegeSettings(HomeBean homeBean,String accessPrivilegeName) throws Exception {
		String endPointUrl = AdminUtils.populateBasicRequestParamsDetails(homeBean,ApptAdminRestConstants.REST_SERVICE_GET_PRIVILEGE_SETTINGS.getRequestURI());
		endPointUrl = endPointUrl.replaceAll("@accessPrivilegeNameParam@",accessPrivilegeName);
		System.out.println("getPrivilegeSettings : endPointUrl :  "+endPointUrl);
		
		ResponseModel responseModel = ApptAdminRestClient.getInstance().performGETRequest(endPointUrl,"getPrivilegeSettings");
		System.out.println("getPrivilegeSettings : response :  "+responseModel.getData());
		logger.debug("getPrivilegeSettings : response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),PrivilegeSettingResponse.class);
	}
    
    @Override    
	public BaseResponse updatePrivilegeSettings(HomeBean homeBean,PrivilegeSettings privilegeSettings) throws Exception {
		String endPointUrl = PropertyUtils.getApptAdminRestServiceEndPointURL() + ApptAdminRestConstants.REST_SERVICE_UPDATE_PRIVILEGE_SETTINGS.getRequestURI();
		privilegeSettings.setClientCode(homeBean.getUserLoginResponse().getClientCode());

		ResponseModel responseModel = ApptAdminRestClient.getInstance().performPOSTRequest(endPointUrl,privilegeSettings,"updatePrivilegeSettings");
		logger.debug("updatePrivilegeSettings ::: response :  "+responseModel.getData());
		return AdminUtils.getDataFromJSON(responseModel.getData(),BaseResponse.class);
	}

	private void logObjectForInfo(final String message, final Object obj){
		final String jsonString = new GsonBuilder().create().toJson(obj);
		logger.info(message + jsonString);
	}

    private void logObjectForDebug(final String message, final Object obj){
		final String jsonString = new GsonBuilder().create().toJson(obj);
		logger.debug(message + jsonString);
	}

	/*public static void main(String[] args) throws Exception {
		final HomeBean homeBean = new HomeBean();
		final String calendarDate = "05/06/2017";
		final String locationId = "1";
		final String resourceIds = "10";

		ApptAdminService service = new ApptAdminServiceImpl();
		final WeeklyCalendarResponse response = service.getWeeklyCalendar(homeBean, calendarDate, locationId, resourceIds);
		System.out.println(response);
	}*/
	
	public static void main(String[] args) {
		ResourceWorkingHoursRequest request = new ResourceWorkingHoursRequest();
		List<String> dates = new ArrayList<String>();
		dates.add("01/05/2017");
		dates.add("02/05/2017");
		List<Integer> selectedResourceIds = new ArrayList<Integer>();
		selectedResourceIds.add(1);
		selectedResourceIds.add(2);
		request.setBreakTimeOpen(false);
		request.setClientCode("CC");
		request.setContinueUpdate(false);
		request.setDates(dates);
		request.setDayOpen(false);
		request.setNotifyCheckBox("Y");
		request.setSelectedBreakTime("12");
		request.setSelectedDuration(1);
		request.setSelectedEndTime("12");
		request.setSelectedResourceIds(selectedResourceIds);
		request.setSelectedStartTime("12");
		request.setUserName("UName");
		
		System.out.println(AdminUtils.getJSONDataStr(request));
	}
}
