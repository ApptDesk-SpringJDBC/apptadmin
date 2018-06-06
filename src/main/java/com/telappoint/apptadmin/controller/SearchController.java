package com.telappoint.apptadmin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.ErrorCodesConstants;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.ErrorBean;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.AppointmentDetails;
import com.telappoint.apptadmin.model.AppointmentStatusDropDownResponse;
import com.telappoint.apptadmin.model.AppointmentsDataResponse;
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.CancelAppointResponse;
import com.telappoint.apptadmin.model.CustomerActivityResponse;
import com.telappoint.apptadmin.model.CustomerPledge;
import com.telappoint.apptadmin.model.CustomerPledgeFundSource;
import com.telappoint.apptadmin.model.CustomerPledgeFundSourceResponse;
import com.telappoint.apptadmin.model.CustomerPledgeRequest;
import com.telappoint.apptadmin.model.CustomerPledgeResponse;
import com.telappoint.apptadmin.model.CustomerPledgeStatusResponse;
import com.telappoint.apptadmin.model.CustomerPledgeVendorResponse;
import com.telappoint.apptadmin.model.CustomersResponse;
import com.telappoint.apptadmin.model.Location;
import com.telappoint.apptadmin.model.Resource;
import com.telappoint.apptadmin.model.SearchResponse;
import com.telappoint.apptadmin.model.VerifyPageData;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class SearchController extends ApptAdminHelperController { 

	private Logger logger = Logger.getLogger(SearchController.class);

	@Autowired
	private ApptAdminService apptAdminService;

	@RequestMapping(value="search", method = RequestMethod.GET)
	public ModelAndView showSearchPage(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam(required=false) String searchType,HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_SEARCH_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("searchDropDownList",apptAdminService.getSearchDropDownList(homeBean));
			populateDisplayNames(homeBean, modelView);
			if(searchType==null || "".equals(searchType)){
				searchType = "APPOINTENT_DETAILS";
			}
			modelView.addObject("searchType",searchType);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showSearchPage", request,e);
		}
		modelView.setViewName(targetPage);
		return modelView;  
	}

	@RequestMapping(value="getSearchResponse", method = RequestMethod.GET)
	public @ResponseBody String getInBoundCallReport(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam(value="searchSelectionType",required=false) String searchSelectionType, //Search By
			@RequestParam(value="firstName",required=false) String firstName,@RequestParam(value="lastName",required=false) String lastName, //Search By - FIRSTNAME_LASTNAME
			@RequestParam(value="confirmationNumber",required=false) String confirmationNumber, //Search By - CONFIRMATION_NO
			@RequestParam(value="accountNumber",required=false) String accountNumber, //Search By - SSN / SSN_LAST_7
			@RequestParam(value="contactPhone",required=false) String contactPhone, //Search By - CONTACT_PHONE
			@RequestParam(value="callerId",required=false) String callerId, //Search By - CALLER_ID
			@RequestParam(value="energyAccNO",required=false) String attrib1, //Search By - ENERGY_ACCT_NO - attrib1
			@RequestParam(value="dob",required=false) String dob, //Search By - DOB
			@RequestParam(value="houseHoldId",required=false) String houseHoldId, //Search By - HOUSE_HOLD
			HttpServletRequest request) throws Exception {		
		SearchResponse searchResponse = null;
		try{
			searchResponse = apptAdminService.getSearchResponse(homeBean, searchSelectionType, firstName, lastName, confirmationNumber, accountNumber, contactPhone, callerId, attrib1, dob, houseHoldId);
			searchResponse.setStatus(true);
		}catch (Exception e) {
			searchResponse =  new SearchResponse();
			searchResponse.setStatus(false);
			searchResponse.setMessage("Error while retriving Search data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getSearchResponse", request,e);
		}
		System.out.println("getSearchResponse ::: "+AdminUtils.getJSONDataStr(searchResponse));
		return AdminUtils.getJSONDataStr(searchResponse);  
	}

	//Search - Appointments related Functionality
	@RequestMapping(value="getAppointmentsByCustomerId", method =RequestMethod.GET)
	public @ResponseBody String getAppointmentsByCustomerId(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam("customerId") String customerId, HttpServletRequest request) throws Exception {
		SearchResponse searchResponse = null;
		try{
			searchResponse = apptAdminService.getAppointmentsByCustomerId(homeBean, customerId);
			searchResponse.setStatus(true);
			//populateDummyAppts(searchResponse);
		}catch (Exception e) {
			searchResponse =  new SearchResponse();
			searchResponse.setStatus(false);
			searchResponse.setMessage("Error while retriving Appointments data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getAppointmentsByCustomerId", request,e);
		}
		System.out.println("searchResponse ::: "+AdminUtils.getJSONDataStr(searchResponse));
		return AdminUtils.getJSONDataStr(searchResponse);  
	}

	/*@RequestMapping(value="showPopUp", method = RequestMethod.GET)
	public ModelAndView showPopUp(@ModelAttribute("homeBean") HomeBean homeBean,ModelMap model, HttpServletRequest request) throws Exception {
		String targetPage = "search/showpopup";		
		ModelAndView modelView = new ModelAndView();	
		try{
			SearchResponse searchResponse = new SearchResponse();
			model.put("searchResponse", searchResponse);			
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showSearchPage", request,e);
		}
		modelView.setViewName(targetPage);
		return modelView;	    
	}*/

	//TODO: needs to delete
	/*private void populateDummyAppts(SearchResponse searchResponse){
		List<SearchData> searchDatas = new ArrayList<SearchData>();
		SearchData searchData = null;
		for (Integer i = 1; i <= 5; i++) {
			searchData = new SearchData();
			searchData.setLocationName("Location : "+i);
			searchData.setResourceName("Resource : "+i);
			searchData.setServiceName("Service : "+i);
			searchData.setApptDateTime("29/05/1983 12:00:00 ");
			searchData.setApptStatus("Status : "+i);
			searchData.setApptMethod("Method : "+i);
			searchData.setSchedulerId(i.longValue());
			searchData.setConfirmNumber(i.longValue());
			searchDatas.add(searchData);
		}
		List<SearchData> searchDatasList = searchResponse.getSearchApptList();
		if(searchDatasList==null){
			searchDatasList = new ArrayList<SearchData>();
		}
		searchDatasList.addAll(searchDatas);
		searchResponse.setSearchApptList(searchDatasList);
	}*/

	@RequestMapping(value="getAppointmentStatusDropDownList", method =RequestMethod.GET)
	public @ResponseBody String getAppointmentStatusDropDownList(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		AppointmentStatusDropDownResponse appointmentStatusDropDownResponse = null;
		try{
			appointmentStatusDropDownResponse = apptAdminService.getAppointmentStatusDropDownList(homeBean);
			appointmentStatusDropDownResponse.setStatus(true);
		}catch (Exception e) {
			appointmentStatusDropDownResponse =  new AppointmentStatusDropDownResponse();
			appointmentStatusDropDownResponse.setStatus(false);
			appointmentStatusDropDownResponse.setMessage("Error while retriving Appointments Status data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getAppointmentStatusDropDownList", request,e);
		}
		System.out.println("appointmentStatusDropDownResponse ::: "+AdminUtils.getJSONDataStr(appointmentStatusDropDownResponse));
		return AdminUtils.getJSONDataStr(appointmentStatusDropDownResponse);  
	}

	@RequestMapping(value="updateAppointmentStatus", method =RequestMethod.GET)
	public @ResponseBody String updateAppointmentStatus(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("scheduleId") String scheduleId,@RequestParam("status") String status, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{
			baseResponse = apptAdminService.updateAppointmentStatus(homeBean,scheduleId,status);
			getUpdatedBaseResponse(baseResponse,"Appointment Status updated successfully!","Error while updating Appointment Status");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while updating Appointment Status");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateAppointmentStatus", request,e);
		}
		System.out.println("baseResponse ::: "+AdminUtils.getJSONDataStr(baseResponse));
		return AdminUtils.getJSONDataStr(baseResponse);  
	}

	@RequestMapping(value="cancelAppointment", method =RequestMethod.GET)
	public @ResponseBody String cancelAppointment(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("scheduleId") String scheduleId,HttpServletRequest request) throws Exception {
		CancelAppointResponse cancelAppointResponse = null;
		try{
			cancelAppointResponse = apptAdminService.cancelAppointment(homeBean,scheduleId);
			getUpdatedBaseResponse(cancelAppointResponse,"Appointment cancelled updated successfully!","Error while cancelling Appointment");
		}catch (Exception e) {
			cancelAppointResponse = new CancelAppointResponse();
			cancelAppointResponse.setStatus(false);
			cancelAppointResponse.setMessage("Error while cancelling Appointment");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"cancelAppointment", request,e);
		}
		System.out.println("cancelAppointResponse ::: "+AdminUtils.getJSONDataStr(cancelAppointResponse));
		return AdminUtils.getJSONDataStr(cancelAppointResponse);  
	}

	@RequestMapping(value="getCustomersById", method =RequestMethod.GET)
	public @ResponseBody String getCustomersById(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("customerId") String customerId, HttpServletRequest request) throws Exception {
		CustomersResponse customersResponse = null;
		try{
			customersResponse = apptAdminService.getCustomersById(homeBean, customerId);
			customersResponse.setStatus(true);
		}catch (Exception e) {
			customersResponse =  new CustomersResponse();
			customersResponse.setStatus(false);
			customersResponse.setMessage("Error while retriving Customers data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getCustomersById", request,e);
		}
		System.out.println("customersResponse ::: "+AdminUtils.getJSONDataStr(customersResponse));
		return AdminUtils.getJSONDataStr(customersResponse);  
	}

	@RequestMapping(value="getCustomerActivitiesByCustomerId", method =RequestMethod.GET)
	public @ResponseBody String getCustomerActivitiesByCustomerId(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("customerId") String customerId, HttpServletRequest request) throws Exception {
		CustomerActivityResponse customerActivityResponse = null;
		try{
			customerActivityResponse = apptAdminService.getCustomerActivitiesByCustomerId(homeBean, customerId);
			customerActivityResponse.setStatus(true);
		}catch (Exception e) {
			customerActivityResponse =  new CustomerActivityResponse();
			customerActivityResponse.setStatus(false);
			customerActivityResponse.setMessage("Error while retriving Customers data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getCustomersById", request,e);
		}
		System.out.println("customerActivityResponse ::: "+AdminUtils.getJSONDataStr(customerActivityResponse));
		return AdminUtils.getJSONDataStr(customerActivityResponse);  
	}

	//Search - HouseHold related Functionality
	@RequestMapping(value="getHouseHoldInfoByCustomerId", method =RequestMethod.GET)
	public @ResponseBody String getHouseHoldInfoByCustomerId(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("customerId") String customerId, HttpServletRequest request) throws Exception {
		CustomersResponse customersResponse = null;
		try{
			customersResponse = apptAdminService.getHouseHoldInfoByCustomerId(homeBean, customerId);
			customersResponse.setStatus(true);
		}catch (Exception e) {
			customersResponse =  new CustomersResponse();
			customersResponse.setStatus(false);
			customersResponse.setMessage("Error while retriving Customers Household Info data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getHouseHoldInfoByCustomerId", request,e);
		}
		System.out.println("customersResponse ::: "+AdminUtils.getJSONDataStr(customersResponse));
		return AdminUtils.getJSONDataStr(customersResponse);  
	}

	@RequestMapping(value="mergeHouseHoldId", method =RequestMethod.GET)
	public @ResponseBody String mergeHouseHoldId(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("fromHouseHoldIds") String fromHouseHoldIds,@RequestParam("mergeToHouseHoldId") String mergeToHouseHoldId,HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{
			baseResponse = apptAdminService.mergeHouseHoldId(homeBean, fromHouseHoldIds, mergeToHouseHoldId);
			getUpdatedBaseResponse(baseResponse,"Households merged successfully!","Error while merging Households");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while merging Households");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"mergeHouseHoldId", request,e);
		}
		System.out.println("baseResponse ::: "+AdminUtils.getJSONDataStr(baseResponse));
		return AdminUtils.getJSONDataStr(baseResponse);  
	}

	@RequestMapping(value="splitHouseHoldId", method =RequestMethod.GET)
	public @ResponseBody String splitHouseHoldId(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("customerIds") String customerIds,@RequestParam("newHouseHoldId") String newHouseHoldId,@RequestParam("assignNewHouseholdID") String assignNewHouseholdID,HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{
			baseResponse = apptAdminService.splitHouseHoldId(homeBean, customerIds, newHouseHoldId, assignNewHouseholdID);
			getUpdatedBaseResponse(baseResponse,"Households splitted successfully!","Error while spliting Households");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while spliting Households");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"splitHouseHoldId", request,e);
		}
		System.out.println("baseResponse ::: "+AdminUtils.getJSONDataStr(baseResponse));
		return AdminUtils.getJSONDataStr(baseResponse);  
	}

	@RequestMapping(value="updateHouseHoldId", method =RequestMethod.GET)
	public @ResponseBody String updateHouseHoldId(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("customerId") String customerId,@RequestParam("newHouseHoldId") String newHouseHoldId,HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{
			baseResponse = apptAdminService.updateHouseHoldId(homeBean, customerId, newHouseHoldId);
			getUpdatedBaseResponse(baseResponse,"HouseholdId updated successfully!","Error while updating HouseholdId");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while updating HouseholdId");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateHouseHoldId", request,e);
		}
		System.out.println("baseResponse ::: "+AdminUtils.getJSONDataStr(baseResponse));
		return AdminUtils.getJSONDataStr(baseResponse);  
	}

	//Search - Pledge related Functionality
	@RequestMapping(value="getPledgeHistoryByCustomerId", method =RequestMethod.GET)
	public @ResponseBody String getPledgeHistoryByCustomerId(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam("customerId") String customerId, HttpServletRequest request) throws Exception {
		CustomerPledgeResponse pledgeHistory = null;
		try{
			//pledgeHistory = getDummyData(); 
			pledgeHistory = apptAdminService.getPledgeHistoryByCustomerId(homeBean, customerId);
			pledgeHistory.setStatus(true);
		}catch (Exception e) {
			e.printStackTrace();
			pledgeHistory =  new CustomerPledgeResponse();
			pledgeHistory.setStatus(false);
			pledgeHistory.setMessage("Error while retriving Pledge data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getPledgeHistoryByCustomerId", request,e);
		}
		System.out.println("getPledgeHistoryByCustomerId ::: "+AdminUtils.getJSONDataStr(pledgeHistory));
		return AdminUtils.getJSONDataStr(pledgeHistory);  
	}

	/*private CustomerPledgeResponse getDummyData(){
		CustomerPledgeResponse pledgeHistory = new CustomerPledgeResponse();
		List<CustomerPledge> customerPledgeList = new ArrayList<CustomerPledge>();
		for(Integer i=1;i<11;i++){
			CustomerPledge customerPledge = new CustomerPledge();
			customerPledge.setAccountNumber("ACC "+i);
			customerPledge.setCustomerId(i.longValue());
			customerPledge.setHouseHoldId(i.longValue());

			customerPledgeList.add(customerPledge);
		}
		pledgeHistory.setCustomerPledgeList(customerPledgeList);
		pledgeHistory.setErrorFlag("N");
		pledgeHistory.setStatus(true);
		return pledgeHistory;
	}*/

	@RequestMapping(value="add-cust-pledge-details", method = RequestMethod.GET)
	public ModelAndView addCustomerPledgeDetails(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam String custId,
			@RequestParam String scheduleId,HttpServletRequest request) throws Exception {
		String targetPage = "search/add-cust-pledge-details";	
		ModelAndView modelView = new ModelAndView();	
		try{
			CustomerPledgeRequest customerPledgeReq = new CustomerPledgeRequest();
			CustomersResponse customersResponse = apptAdminService.getCustomersById(homeBean,custId);
			AppointmentsDataResponse appointmentsDataResponse = apptAdminService.getPastAppointments(homeBean,custId);
			CustomerPledgeStatusResponse customerPledgeStatus = apptAdminService.getCustomerPledgeStatusList(homeBean);
			CustomerPledgeFundSourceResponse fundSources = apptAdminService.getCustomerPledgeFundSourceList(homeBean);
			List<Location> locationList = apptAdminService.getLocationListForLoginUser(homeBean);
			List<Resource> resourceList =  new ArrayList<Resource>();
			VerifyPageData verifyPageData = null;
			if(scheduleId!=null && !"-1".equals(scheduleId) && !"undefined".equals(scheduleId)){
				verifyPageData = apptAdminService.getAppointmentInfo(homeBean,custId,scheduleId,homeBean.getTransId());
			}
			List<CustomerPledge> customerPledgeList = new ArrayList<CustomerPledge>();
			if(fundSources!=null && fundSources.getCustomerPledgeFundSourceList()!=null && fundSources.getCustomerPledgeFundSourceList().size()>0){
				CustomerPledge customerPledge = null;
				CustomerPledgeVendorResponse customerPledgeVendors = null;
				for(CustomerPledgeFundSource fundSource : fundSources.getCustomerPledgeFundSourceList()){
					customerPledge = new CustomerPledge();
					String fundId = fundSource.getFundId();
					customerPledgeVendors = apptAdminService.getCustomerPledgeVendorList(homeBean, fundId);
					customerPledge.setFundId(Long.getLong(fundId));
					customerPledge.setScheduleId(Long.getLong(scheduleId));
					customerPledge.setFundName(fundSource.getFundName());
					customerPledge.setVendorList(customerPledgeVendors.getCustomerPledgeVendorList());
					customerPledge.setPmtUpdateBy(String.valueOf(homeBean.getUserLoginResponse().getLoginUserId()));
					customerPledge.setPmtUpdateByName(homeBean.getUserLoginResponse().getFirstName()+" "+homeBean.getUserLoginResponse().getLastName());
					if(verifyPageData!=null) {
						//customerPledge.setLocationId(String.valueOf(verifyPageData.getLocationId()));
						//customerPledge.setResourceId(String.valueOf(verifyPageData.getResourceId()));
						resourceList =  apptAdminService.getResourceListByLocationIdForLoginUser(homeBean,String.valueOf(customerPledge.getLocationId()));
					}
					customerPledge.setLocationList(locationList);
					customerPledge.setResourceList(resourceList);
					customerPledgeList.add(customerPledge);
				}
			}			
			customerPledgeReq.setCustomer(customersResponse.getCustomerList().get(0));
			customerPledgeReq.setCustomerPledgeList(customerPledgeList);
			modelView.addObject("customerPledgeReq",customerPledgeReq);
			modelView.addObject("customerPastAppts",appointmentsDataResponse!=null ? appointmentsDataResponse.getBookedAppts() : new ArrayList<AppointmentDetails>());
			modelView.addObject("customerPledgeStatus",customerPledgeStatus.getCustomerPledgeStatusList());	
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"addCustomerPledgeDetails", request,e);
		}
		modelView.setViewName(targetPage);
		return modelView;  
	}

	@RequestMapping(value="saveCustomerPledgeDetails", method = RequestMethod.POST)
	public @ResponseBody String saveCustomerPledgeDetails(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute CustomerPledgeRequest customerPledgeReq, HttpServletRequest request) throws Exception {
		CustomerPledgeResponse customerPledgeResponse = null;
		try{			
			customerPledgeResponse = apptAdminService.addCustomerPledgeDetails(homeBean, customerPledgeReq);
			getUpdatedBaseResponse(customerPledgeResponse,"Pledge details saved sucessfully!","Error while saving Pledge details");
		}catch (Exception e) {
			customerPledgeResponse = new CustomerPledgeResponse();
			customerPledgeResponse.setStatus(false);
			customerPledgeResponse.setMessage("Error while saving Pledge details");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"addCustomerPledgeDetails", request,e);
		}
		return AdminUtils.getJSONDataStr(customerPledgeResponse);  
	}

	@RequestMapping(value="edit-cust-pledge-details", method = RequestMethod.POST)
	public @ResponseBody String editCustomerPledgeDetails(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute CustomerPledgeRequest customerPledgeReq, HttpServletRequest request) throws Exception {
		CustomerPledgeResponse customerPledgeResponse = null;
		try{			
			customerPledgeResponse = apptAdminService.updateCustomerPledgeDetails(homeBean, customerPledgeReq);
			getUpdatedBaseResponse(customerPledgeResponse,"Pledge details updated sucessfully!","Error while updating Pledge details");
		}catch (Exception e) {
			customerPledgeResponse = new CustomerPledgeResponse();
			customerPledgeResponse.setStatus(false);
			customerPledgeResponse.setMessage("Error while updating Pledge details");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateCustomerPledgeDetails", request,e);
		}
		return AdminUtils.getJSONDataStr(customerPledgeResponse);  
	}

	@RequestMapping(value="updateCustomerPledgeDetails", method = RequestMethod.POST)
	public @ResponseBody String updateCustomerPledgeDetails(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute CustomerPledgeRequest customerPledgeReq, HttpServletRequest request) throws Exception {
		CustomerPledgeResponse customerPledgeResponse = null;
		try{			
			customerPledgeResponse = apptAdminService.updateCustomerPledgeDetails(homeBean, customerPledgeReq);
			getUpdatedBaseResponse(customerPledgeResponse,"Pledge details updated sucessfully!","Error while updating Pledge details");
		}catch (Exception e) {
			customerPledgeResponse = new CustomerPledgeResponse();
			customerPledgeResponse.setStatus(false);
			customerPledgeResponse.setMessage("Error while updating Pledge details");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateCustomerPledgeDetails", request,e);
		}
		return AdminUtils.getJSONDataStr(customerPledgeResponse);  
	}
}
