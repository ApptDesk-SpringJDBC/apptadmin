package com.telappoint.apptadmin.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.OneDateResourceWorkingHoursResponse;
import com.telappoint.apptadmin.model.OneDateWorkingHoursResponse;
import com.telappoint.apptadmin.model.ResourceWorkingHoursRequest;
import com.telappoint.apptadmin.model.ResourceWorkingHrsResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.DateUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ResourceEditHoursOneDateController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(ResourceEditHoursOneDateController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
		
	@RequestMapping(value="showResourceEditHoursOneDate", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showResourceEditHoursOneDatePage(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_RESOURCE_EDIT_HOURS_ONE_DATE_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("locations",apptAdminService.getAllLocationsBasicData(homeBean));
			//modelView.addObject("resources",apptAdminService.getAllResourcesBasicData(homeBean));
			modelView.addObject("breakTimeDurationMap",AdminUtils.getBreakTimeDuration(homeBean.getUserLoginResponse().getBlockTimeInMins()));
			modelView.addObject("minsList",AdminUtils.getMinsList(homeBean.getUserLoginResponse().getBlockTimeInMins()));
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_ONE_DATE_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_ONE_DATE_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_ONE_DATE_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_ONE_DATE_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showResourceEditHoursOneDate", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="validateDateAgainestClosedDaysAndHolidays",method = RequestMethod.GET)
	public @ResponseBody String proceedResourceDateRangeWorkingHoursUpdate(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam String locationId,@RequestParam String date,
			HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{
			baseResponse = new BaseResponse();
			baseResponse.setStatus(true);
		}catch (Exception e) {
			baseResponse = new BaseResponse();	
			logger.error(((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"validateDateAgainestClosedDaysAndHolidays", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
	
	@RequestMapping(value="getSuggestedOneDateResourceWorkingHrs",method = RequestMethod.GET)
	public @ResponseBody String getSuggestedOneDateResourceWorkingHrs(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam String locationId,
			@RequestParam String resourceId,
			@RequestParam String date,
			HttpServletRequest request) throws Exception {
		OneDateWorkingHoursResponse resourceWorkingHrs = null;	
		System.out.println("\n -----------getSuggestedOneDateResourceWorkingHrs---------------Start--------- "+new Date()+" ---------------------------");
		try{
			date = DateUtils.getStringFromDate(DateUtils.getDateFromString(date,DateUtils.DATE_FORMAT_MMDDYYYY),DateUtils.DATE_FORMAT_YYYYMMDD);
			resourceWorkingHrs = apptAdminService.getSuggestedOneDateResourceWorkingHrs(homeBean, locationId, resourceId,date);
		}catch (Exception e) {
			resourceWorkingHrs = new OneDateWorkingHoursResponse();	
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCE_SHOW_SUGGESTED_WORKING_HOURS_PAGE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getSuggestedOneDateResourceWorkingHrs", request,e);
		}
		System.out.println("------------------getSuggestedOneDateResourceWorkingHrs------------------End--------------------------\n");
		return AdminUtils.getJSONDataStr(resourceWorkingHrs); 
	}
	
	@RequestMapping(value="updateOneDateResourceWorkingHrs",method = RequestMethod.POST)
	public @ResponseBody String updateOneDateResourceWorkingHrs(@ModelAttribute("homeBean") HomeBean homeBean, 
			@ModelAttribute ResourceWorkingHoursRequest resourceWorkingHoursRequest,
			@RequestParam String resourceSelectedList,
			@RequestParam String locationSel,
			HttpServletRequest request) throws Exception {
		ResourceWorkingHrsResponse resourceWorkingHrsResponse = null;	
		System.out.println("\n -----------updateOneDateResourceWorkingHrs---------------Start--------- "+new Date()+" ---------------------------");
		try{
			resourceWorkingHoursRequest.setSelectedResourceIds(populateResourceIds(resourceSelectedList));
			populateResourceWorkingHrsRequestBackEndRequiredData(resourceWorkingHoursRequest,request);
			resourceWorkingHoursRequest.setLocationId(Integer.parseInt(locationSel));
			System.out.println("updateResourceWorkingHours : Request Data :  "+AdminUtils.getJSONDataStr(resourceWorkingHoursRequest));
			resourceWorkingHrsResponse = apptAdminService.updateOneDateResourceWorkingHrs(homeBean,resourceWorkingHoursRequest);
		}catch (Exception e) {
			resourceWorkingHrsResponse = new ResourceWorkingHrsResponse();	
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCE_SHOW_SUGGESTED_WORKING_HOURS_PAGE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateOneDayResourceWorkingHrs", request,e);
		}
		System.out.println("------------------updateOneDayResourceWorkingHrs------------------End--------------------------\n");
		return AdminUtils.getJSONDataStr(resourceWorkingHrsResponse); 
	}
	
	private void populateResourceWorkingHrsRequestBackEndRequiredData(ResourceWorkingHoursRequest resourceWorkingHoursRequest,HttpServletRequest request){
		//Day Details 
		if(resourceWorkingHoursRequest.isDayOpenClose()){
			resourceWorkingHoursRequest.setDayOpen(true);
			resourceWorkingHoursRequest.setSelectedStartTime(resourceWorkingHoursRequest.getDayStartTimeHr()+":"+resourceWorkingHoursRequest.getDayStartTimeMin()+" "+resourceWorkingHoursRequest.getDayStartTimeAmPm());
			resourceWorkingHoursRequest.setSelectedEndTime(resourceWorkingHoursRequest.getDayEndTimeHr()+":"+resourceWorkingHoursRequest.getDayEndTimeMin()+" "+resourceWorkingHoursRequest.getDayEndTimeAmPm());
			if(resourceWorkingHoursRequest.isDayBreakTimeOpenClose()){
				resourceWorkingHoursRequest.setBreakTimeOpen(true);
				resourceWorkingHoursRequest.setSelectedBreakTime(resourceWorkingHoursRequest.getDayBreakTimeHr()+":"+resourceWorkingHoursRequest.getDayBreakTimeMin()+" "+resourceWorkingHoursRequest.getDayBreakTimeAmPm());
				resourceWorkingHoursRequest.setSelectedDuration(Integer.parseInt(resourceWorkingHoursRequest.getDayBreakDuration()));
			} else {
				resourceWorkingHoursRequest.setBreakTimeOpen(false);
			}
		} else {
			resourceWorkingHoursRequest.setDayOpen(false);
		}
		 
		List<String> oneDateDates = new ArrayList<String>();
		String moreDatesCount = request.getParameter("moreDatesCount");
		int noOfDates = 0;
		if(moreDatesCount!=null && moreDatesCount.length()>0){
			noOfDates = Integer.parseInt(moreDatesCount);
		}		
		
		oneDateDates.add(DateUtils.getStringFromDate(DateUtils.getDateFromString(request.getParameter("startDate0"),DateUtils.DATE_FORMAT_MMDDYYYY),DateUtils.DATE_FORMAT_YYYYMMDD));
		for(int i=1;i<=noOfDates;i++){
			oneDateDates.add(DateUtils.getStringFromDate(DateUtils.getDateFromString(request.getParameter("startDate"+i),DateUtils.DATE_FORMAT_MMDDYYYY),DateUtils.DATE_FORMAT_YYYYMMDD));
		}
		resourceWorkingHoursRequest.setDates(oneDateDates);
	}
	
	@RequestMapping(value="getOneDateResourceWorkingHrsDetails",method = RequestMethod.GET)
	public @ResponseBody String getOneDateResourceWorkingHrsDetails(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		System.out.println("\n -----------getOneDateResourceWorkingHrsDetails---------------Start--------- "+new Date()+" ---------------------------");
		OneDateResourceWorkingHoursResponse oneDateResourceWorkingHoursResponse = null;
		try{
			oneDateResourceWorkingHoursResponse = apptAdminService.getOneDateResourceWorkingHrsDetails(homeBean);
		}catch (Exception e) {
			oneDateResourceWorkingHoursResponse = new OneDateResourceWorkingHoursResponse();
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCE_SHOW_SUGGESTED_WORKING_HOURS_PAGE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getOneDateResourceWorkingHrsDetails", request,e);
		}
		System.out.println("------------------getOneDateResourceWorkingHrsDetails------------------End--------------------------\n");
		return AdminUtils.getJSONDataStr(oneDateResourceWorkingHoursResponse); 
	}
	
	@RequestMapping(value="clearResourceWorkingHoursOneDateSessionData",method = RequestMethod.GET)
	public @ResponseBody String clearResourceWorkingHoursDateRangeSessionData(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		try{
			homeBean.setDateRangeResourceHoursRequest(null);
		}catch (Exception e) {
			logger.error(((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
		}
		return "Success"; 
	}
	
	@RequestMapping(value="proceedResourceOneDateWorkingHoursUpdate",method = RequestMethod.GET)
	public @ResponseBody String proceedResourceOneDateWorkingHoursUpdate(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		ResourceWorkingHrsResponse resourceWorkingHrsResponse = null;
		try{
			ResourceWorkingHoursRequest resourceHoursRequest = homeBean.getOneDateResourceHoursRequest();
			if(resourceHoursRequest!=null) {
				resourceHoursRequest.setContinueUpdate(true);
			}
			resourceWorkingHrsResponse = apptAdminService.updateOneDateResourceWorkingHrs(homeBean,resourceHoursRequest);
		}catch (Exception e) {
			resourceWorkingHrsResponse = new ResourceWorkingHrsResponse();	
			logger.error(((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"proceedResourceOneDateWorkingHoursUpdate", request,e);
		}
		return AdminUtils.getJSONDataStr(resourceWorkingHrsResponse); 
	}
 }
