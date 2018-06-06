package com.telappoint.apptadmin.controller;

import java.util.Date;

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
import com.telappoint.apptadmin.model.ResourceHoursRequest;
import com.telappoint.apptadmin.model.ResourceWorkingHrs;
import com.telappoint.apptadmin.model.ResourceWorkingHrsResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ResourceEditHoursDateRangeController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(ResourceEditHoursDateRangeController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
		
	@RequestMapping(value="showResourceEditHoursDateRange", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showResourceEditHoursDateRangePage(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_RESOURCE_EDIT_HOURS_DATE_RANGE_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("locations",apptAdminService.getAllLocationsBasicData(homeBean));
			//modelView.addObject("resources",apptAdminService.getAllResourcesBasicData(homeBean));
			modelView.addObject("breakTimeDurationMap",AdminUtils.getBreakTimeDuration(homeBean.getUserLoginResponse().getBlockTimeInMins()));
			modelView.addObject("minsList",AdminUtils.getMinsList(homeBean.getUserLoginResponse().getBlockTimeInMins()));
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_DATE_RANGE_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_DATE_RANGE_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_DATE_RANGE_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_RESOURCE_EDIT_HOURS_DATE_RANGE_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showResourceEditHoursDateRangePage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="getSuggestedResourceWorkingHours",method = RequestMethod.GET)
	public @ResponseBody String getSuggestedResourceWorkingHours(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam String locationId,
			@RequestParam String resourceIds,
			@RequestParam String fromDate,
			@RequestParam String toDate,
			HttpServletRequest request) throws Exception {
		ResourceWorkingHrs resourceWorkingHrs = null;	
		System.out.println("\n --------getSuggestedResourceWorkingHours--------------Start------------- "+new Date()+" ---------------------------");
		try{
			resourceWorkingHrs = apptAdminService.getSuggestedResourceWorkingHours(homeBean, locationId, resourceIds, fromDate, toDate);
		}catch (Exception e) {
			resourceWorkingHrs = new ResourceWorkingHrs();	
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCE_SHOW_SUGGESTED_WORKING_HOURS_PAGE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getSuggestedResourceWorkingHours", request,e);
		}
		System.out.println("-----------------getSuggestedResourceWorkingHours-------------------End--------------------------\n");
		return AdminUtils.getJSONDataStr(resourceWorkingHrs); 
	}
	
	@RequestMapping(value="updateResourceWorkingHours",method = RequestMethod.POST)
	public @ResponseBody String updateResourceWorkingHours(@ModelAttribute("homeBean") HomeBean homeBean, 
			@ModelAttribute ResourceHoursRequest resourceHoursRequest,
			@RequestParam String resourceSelectedList,
			HttpServletRequest request) throws Exception {
		ResourceWorkingHrsResponse resourceWorkingHrsResponse = null;	
		System.out.println("\n -----------updateResourceWorkingHours---------------Start--------- "+new Date()+" ---------------------------");
		try{
			resourceHoursRequest.setSelectedResourceIds(populateResourceIds(resourceSelectedList));
			populateResourceWorkingHrsRequestBackEndRequiredData(resourceHoursRequest);
			System.out.println("updateResourceWorkingHours : Request Data :  "+AdminUtils.getJSONDataStr(resourceHoursRequest));
			resourceWorkingHrsResponse = apptAdminService.updateResourceWorkingHours(homeBean,resourceHoursRequest);
		}catch (Exception e) {
			resourceWorkingHrsResponse = new ResourceWorkingHrsResponse();	
			logger.error(ErrorCodesConstants.ERROR_CODE_RESOURCE_SHOW_SUGGESTED_WORKING_HOURS_PAGE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateResourceWorkingHours", request,e);
		}
		System.out.println("------------------updateResourceWorkingHours------------------End--------------------------\n");
		return AdminUtils.getJSONDataStr(resourceWorkingHrsResponse); 
	}
	
	private void populateResourceWorkingHrsRequestBackEndRequiredData(ResourceHoursRequest resourceHoursRequest){
		//SunDay Details
		if(resourceHoursRequest.isSunDayOpenClose()){
			resourceHoursRequest.setIs_sun_open("Y");
			resourceHoursRequest.setSun_start_time(resourceHoursRequest.getSunDayStartTimeHr()+":"+resourceHoursRequest.getSunDayStartTimeMin()+" "+resourceHoursRequest.getSunDayStartTimeAmPm());
			resourceHoursRequest.setSun_end_time(resourceHoursRequest.getSunDayEndTimeHr()+":"+resourceHoursRequest.getSunDayEndTimeMin()+" "+resourceHoursRequest.getSunDayEndTimeAmPm());
			if(resourceHoursRequest.isSunDayBreakTimeOpenClose()){
				resourceHoursRequest.setIs_sun_no_break_time("Y");
				resourceHoursRequest.setSun_break_time_1(resourceHoursRequest.getSunDayBreakTimeHr()+":"+resourceHoursRequest.getSunDayBreakTimeMin()+" "+resourceHoursRequest.getSunDayBreakTimeAmPm());
				resourceHoursRequest.setSun_break_time_1_mins(Integer.parseInt(resourceHoursRequest.getSunDayBreakDuration()));
			} else {
				resourceHoursRequest.setIs_sun_no_break_time("N");
			}
		} else {
			resourceHoursRequest.setIs_sun_open("N");
		}		
				
		//MonDay Details
		if(resourceHoursRequest.isMonDayOpenClose()){
			resourceHoursRequest.setIs_mon_open("Y");
			resourceHoursRequest.setMon_start_time(resourceHoursRequest.getMonDayStartTimeHr()+":"+resourceHoursRequest.getMonDayStartTimeMin()+" "+resourceHoursRequest.getMonDayStartTimeAmPm());
			resourceHoursRequest.setMon_end_time(resourceHoursRequest.getMonDayEndTimeHr()+":"+resourceHoursRequest.getMonDayEndTimeMin()+" "+resourceHoursRequest.getMonDayEndTimeAmPm());
			if(resourceHoursRequest.isMonDayBreakTimeOpenClose()){
				resourceHoursRequest.setIs_mon_no_break_time("Y");
				resourceHoursRequest.setMon_break_time_1(resourceHoursRequest.getMonDayBreakTimeHr()+":"+resourceHoursRequest.getMonDayBreakTimeMin()+" "+resourceHoursRequest.getMonDayBreakTimeAmPm());
				resourceHoursRequest.setMon_break_time_1_mins(Integer.parseInt(resourceHoursRequest.getMonDayBreakDuration()));	
			} else {
				resourceHoursRequest.setIs_mon_no_break_time("N");
			}
		} else {
			resourceHoursRequest.setIs_mon_open("N");
		}		
		
		//TuesDay Details
		if(resourceHoursRequest.isTuesDayOpenClose()){
			resourceHoursRequest.setIs_tue_open("Y");
			resourceHoursRequest.setTue_start_time(resourceHoursRequest.getTuesDayStartTimeHr()+":"+resourceHoursRequest.getTuesDayStartTimeMin()+" "+resourceHoursRequest.getTuesDayStartTimeAmPm());
			resourceHoursRequest.setTue_end_time(resourceHoursRequest.getTuesDayEndTimeHr()+":"+resourceHoursRequest.getTuesDayEndTimeMin()+" "+resourceHoursRequest.getTuesDayEndTimeAmPm());
			if(resourceHoursRequest.isTuesDayBreakTimeOpenClose()){
				resourceHoursRequest.setIs_tue_no_break_time("Y");
				resourceHoursRequest.setTue_break_time_1(resourceHoursRequest.getTuesDayBreakTimeHr()+":"+resourceHoursRequest.getTuesDayBreakTimeMin()+" "+resourceHoursRequest.getTuesDayBreakTimeAmPm());
				resourceHoursRequest.setTue_break_time_1_mins(Integer.parseInt(resourceHoursRequest.getTuesDayBreakDuration()));
			} else {
				resourceHoursRequest.setIs_tue_no_break_time("N");
			}
		} else {
			resourceHoursRequest.setIs_tue_open("N");
		}		
				
		//WednesDay Details
		if(resourceHoursRequest.isWednesDayOpenClose()){
			resourceHoursRequest.setIs_wed_open("Y");
			resourceHoursRequest.setWed_start_time(resourceHoursRequest.getWednesDayStartTimeHr()+":"+resourceHoursRequest.getWednesDayStartTimeMin()+" "+resourceHoursRequest.getWednesDayStartTimeAmPm());
			resourceHoursRequest.setWed_end_time(resourceHoursRequest.getWednesDayEndTimeHr()+":"+resourceHoursRequest.getWednesDayEndTimeMin()+" "+resourceHoursRequest.getWednesDayEndTimeAmPm());
			if(resourceHoursRequest.isWednesDayBreakTimeOpenClose()){
				resourceHoursRequest.setIs_wed_no_break_time("Y");
				resourceHoursRequest.setWed_break_time_1(resourceHoursRequest.getWednesDayBreakTimeHr()+":"+resourceHoursRequest.getWednesDayBreakTimeMin()+" "+resourceHoursRequest.getWednesDayBreakTimeAmPm());
				resourceHoursRequest.setWed_break_time_1_mins(Integer.parseInt(resourceHoursRequest.getWednesDayBreakDuration()));	
			} else {
				resourceHoursRequest.setIs_wed_no_break_time("N");
			}
		} else {
			resourceHoursRequest.setIs_wed_open("N");
		}		
				
		//ThursDay Details
		if(resourceHoursRequest.isThursDayOpenClose()){
			resourceHoursRequest.setIs_thu_open("Y");
			resourceHoursRequest.setThu_start_time(resourceHoursRequest.getThursDayStartTimeHr()+":"+resourceHoursRequest.getThursDayStartTimeMin()+" "+resourceHoursRequest.getThursDayStartTimeAmPm());
			resourceHoursRequest.setThu_end_time(resourceHoursRequest.getThursDayEndTimeHr()+":"+resourceHoursRequest.getThursDayEndTimeMin()+" "+resourceHoursRequest.getThursDayEndTimeAmPm());
			if(resourceHoursRequest.isThursDayBreakTimeOpenClose()){
				resourceHoursRequest.setIs_thu_no_break_time("Y");
				resourceHoursRequest.setThu_break_time_1(resourceHoursRequest.getThursDayBreakTimeHr()+":"+resourceHoursRequest.getThursDayBreakTimeMin()+" "+resourceHoursRequest.getThursDayBreakTimeAmPm());
				resourceHoursRequest.setThu_break_time_1_mins(Integer.parseInt(resourceHoursRequest.getThursDayBreakDuration()));
			} else {
				resourceHoursRequest.setIs_thu_no_break_time("N");
			}
		} else {
			resourceHoursRequest.setIs_thu_open("N");
		}
		
		//FriDay Details
		if(resourceHoursRequest.isFriDayOpenClose()){
			resourceHoursRequest.setIs_fri_open("Y");
			resourceHoursRequest.setFri_start_time(resourceHoursRequest.getFriDayStartTimeHr()+":"+resourceHoursRequest.getFriDayStartTimeMin()+" "+resourceHoursRequest.getFriDayStartTimeAmPm());
			resourceHoursRequest.setFri_end_time(resourceHoursRequest.getFriDayEndTimeHr()+":"+resourceHoursRequest.getFriDayEndTimeMin()+" "+resourceHoursRequest.getFriDayEndTimeAmPm());
			if(resourceHoursRequest.isFriDayBreakTimeOpenClose()){
				resourceHoursRequest.setIs_fri_no_break_time("Y");
				resourceHoursRequest.setFri_break_time_1(resourceHoursRequest.getFriDayBreakTimeHr()+":"+resourceHoursRequest.getFriDayBreakTimeMin()+" "+resourceHoursRequest.getFriDayBreakTimeAmPm());
				resourceHoursRequest.setFri_break_time_1_mins(Integer.parseInt(resourceHoursRequest.getFriDayBreakDuration()));	
			} else {
				resourceHoursRequest.setIs_fri_no_break_time("N");
			}
		} else {
			resourceHoursRequest.setIs_fri_open("N");
		}		
		
		//SaturDay Details
		if(resourceHoursRequest.isSaturDayOpenClose()){
			resourceHoursRequest.setIs_sat_open("Y");
			resourceHoursRequest.setSat_start_time(resourceHoursRequest.getSaturDayStartTimeHr()+":"+resourceHoursRequest.getSaturDayStartTimeMin()+" "+resourceHoursRequest.getSaturDayStartTimeAmPm());
			resourceHoursRequest.setSat_end_time(resourceHoursRequest.getSaturDayEndTimeHr()+":"+resourceHoursRequest.getSaturDayEndTimeMin()+" "+resourceHoursRequest.getSaturDayEndTimeAmPm());
			if(resourceHoursRequest.isSaturDayBreakTimeOpenClose()){
				resourceHoursRequest.setIs_sat_no_break_time("Y");
				resourceHoursRequest.setSat_break_time_1(resourceHoursRequest.getSaturDayBreakTimeHr()+":"+resourceHoursRequest.getSaturDayBreakTimeMin()+" "+resourceHoursRequest.getSaturDayBreakTimeAmPm());
				resourceHoursRequest.setSat_break_time_1_mins(Integer.parseInt(resourceHoursRequest.getSaturDayBreakDuration()));
			} else {
				resourceHoursRequest.setIs_sat_no_break_time("N");
			}
		} else {
			resourceHoursRequest.setIs_sat_open("N");
		}
	}
	
	@RequestMapping(value="clearResourceWorkingHoursDateRangeSessionData",method = RequestMethod.GET)
	public @ResponseBody String clearResourceWorkingHoursDateRangeSessionData(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		try{
			homeBean.setDateRangeResourceHoursRequest(null);
		}catch (Exception e) {
			logger.error(((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
		}
		return "Success"; 
	}
	
	@RequestMapping(value="proceedResourceDateRangeWorkingHoursUpdate",method = RequestMethod.GET)
	public @ResponseBody String proceedResourceDateRangeWorkingHoursUpdate(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		ResourceWorkingHrsResponse resourceWorkingHrsResponse = null;
		try{
			ResourceHoursRequest resourceHoursRequest = homeBean.getDateRangeResourceHoursRequest();
			if(resourceHoursRequest!=null) {
				resourceHoursRequest.setContinueUpdate(true);
			}
			resourceWorkingHrsResponse = apptAdminService.updateResourceWorkingHours(homeBean,resourceHoursRequest);
		}catch (Exception e) {
			resourceWorkingHrsResponse = new ResourceWorkingHrsResponse();	
			logger.error(((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"proceedResourceDateRangeWorkingHoursUpdate", request,e);
		}
		return AdminUtils.getJSONDataStr(resourceWorkingHrsResponse); 
	}
 }
