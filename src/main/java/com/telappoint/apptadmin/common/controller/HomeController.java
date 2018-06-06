package com.telappoint.apptadmin.common.controller;

/**
 * 
 * @author Murali G
 * 
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.GaugeChartResponse;
import com.telappoint.apptadmin.model.LocationsApptDatesRequest;
import com.telappoint.apptadmin.model.PieChartResponse;
import com.telappoint.apptadmin.model.ServiceLocationApptDatesRequest;
import com.telappoint.apptadmin.model.ServiceLocationApptDatesResponse;
import com.telappoint.apptadmin.model.StackedChartResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class HomeController  extends ApptAdminHelperController {
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent apptAdminCacheComponent;
	
	private Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping(value = JspPageNameConstants.APPTADMINDESK_HOME_PAGE, method = RequestMethod.GET)
	public ModelAndView showHomePage(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		try {
			populateHomePageDetails(request, modelView, homeBean);
		}catch(Exception e){
			logger.error("Error : "+((e.getMessage() !=null)?e.getMessage():""),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showHomePage", request,e);
		}
		return modelView;
	}
	
	@RequestMapping(value="getGaugeChart", method = RequestMethod.GET)
	public @ResponseBody String getGaugeChart(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam String locationId,@RequestParam String startDate,@RequestParam String endDate) throws Exception {
		String json = "";
		try{
			GaugeChartResponse gaugeChartResponse = apptAdminService.getGaugeChart(homeBean,locationId,startDate,endDate) ;
			json = AdminUtils.getJSONDataStr(gaugeChartResponse);
		}catch(Exception e){
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getGaugeChart", request,e);
		}	
		logger.debug("getGaugeChart :::::  Front End :::::: Response Data :::::::: "+json);
		return json;
	}
	
	@RequestMapping(value="getStackedChart", method = RequestMethod.GET)
	public @ResponseBody String getStackedChart(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam String locationId,@RequestParam String resourceId,@RequestParam String stackedChartType) throws Exception {
		String json = "";
		try{
			StackedChartResponse stackedChartResponse = apptAdminService.getStackedChart(homeBean,locationId,resourceId,stackedChartType) ;
			json = AdminUtils.getJSONDataStr(stackedChartResponse);
		}catch(Exception e){
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getStackedChart", request,e);
		}	
		logger.debug("getStackedChart :::::  Front End :::::: Response Data :::::::: "+json);
		return json;
	}
	
	@RequestMapping(value="getPieChart", method = RequestMethod.GET)
	public @ResponseBody String getPieChart(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam String locationId,@RequestParam String selectedDate) throws Exception {
		String json = "";
		try{
			PieChartResponse pieChartResponse = apptAdminService.getPieChart(homeBean,locationId,selectedDate) ;
			json = AdminUtils.getJSONDataStr(pieChartResponse);
		}catch(Exception e){
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getPieChart", request,e);
		}	
		logger.debug("getPieChart :::::  Front End :::::: Response Data :::::::: "+json);
		return json;
	}
	
	@RequestMapping(value="updateScheduleClosedStatus", method = RequestMethod.GET)
	public @ResponseBody String updateScheduleClosedStatus(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam String closedStatus) throws Exception {
		String json = "";
		try{
			BaseResponse baseResponse = apptAdminService.updateScheduleClosedStatus(homeBean,closedStatus) ;
			json = AdminUtils.getJSONDataStr(baseResponse);
		}catch(Exception e){
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateScheduleClosedStatus", request,e);
		}	
		logger.debug("updateScheduleClosedStatus :::::  Front End :::::: Response Data :::::::: "+json);
		return json;
	}
	
	@RequestMapping(value="updateApptPerSeasonDetails", method = RequestMethod.GET)
	public @ResponseBody String updateApptPerSeasonDetails(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam String term_start_date,@RequestParam String term_end_date,@RequestParam String no_appt_per_term) throws Exception {
		String json = "";
		try{
			BaseResponse baseResponse = apptAdminService.updateApptPerSeasonDetails(homeBean,term_start_date,term_end_date,no_appt_per_term);
			json = AdminUtils.getJSONDataStr(baseResponse);
		}catch(Exception e){
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateApptPerSeasonDetails", request,e);
		}	
		logger.debug("updateApptPerSeasonDetails :::::  Front End :::::: Response Data :::::::: "+json);
		return json;
	}
	
	@RequestMapping(value="updateApptRestrictDates", method = RequestMethod.GET)
	public @ResponseBody String updateApptRestrictDates(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request,
			@RequestParam String appt_start_date,@RequestParam String appt_end_date) throws Exception {
		String json = "";
		try{
			BaseResponse baseResponse = apptAdminService.updateApptRestrictDates(homeBean, appt_start_date, appt_end_date);
			json = AdminUtils.getJSONDataStr(baseResponse);
		}catch(Exception e){
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateApptRestrictDates", request,e);
		}	
		logger.debug("updateApptRestrictDates :::::  Front End :::::: Response Data :::::::: "+json);
		return json;
	}
	
	@RequestMapping(value="updateLocationsApptDates", method = {RequestMethod.POST})
	public @ResponseBody String updateLocationsApptDates(@ModelAttribute("locationsApptDatesRequest")LocationsApptDatesRequest locationsApptDatesRequest, 
			@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		String json = "";
		try{
			BaseResponse baseResponse = apptAdminService.updateLocationsApptDates(homeBean,locationsApptDatesRequest);
			json = AdminUtils.getJSONDataStr(baseResponse);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateLocationsApptDates", request,e);
		}		
	    return json;	    
	}
	
	@RequestMapping(value="loadServiceLocationApptDatesWindow", method = RequestMethod.GET)
	public ModelAndView loadServiceLocationApptDatesWindow(@RequestParam String locationId,@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();	
		try{
			ServiceLocationApptDatesResponse  serviceLocationResponse  = apptAdminService.getServiceLocationApptDatesWindow(homeBean,locationId);
			modelView.addObject("serviceLocationResponse",serviceLocationResponse);	
			modelView.addObject("displayNames",apptAdminCacheComponent.getDisplayNames(homeBean));
			ServiceLocationApptDatesRequest serviceLocApptDatesReq = new ServiceLocationApptDatesRequest();
			serviceLocApptDatesReq.setServiceLocationList(serviceLocationResponse.getServiceLocationList());
			modelView.addObject("serviceLocApptDatesReq",serviceLocApptDatesReq);	
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"loadServiceLocationApptDatesWindow", request,e);
		}
		modelView.setViewName("restrict_loc_ser_appt_window");
	    return modelView;	    
	}
	
	@RequestMapping(value="updateServiceLocationApptDatesWindow", method = {RequestMethod.POST})
	public @ResponseBody String updateServiceLocationApptDatesWindow(@ModelAttribute("serviceLocApptDatesReq")ServiceLocationApptDatesRequest serviceLocApptDatesReq, 
			@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		String json = "";
		try{
			BaseResponse baseResponse = apptAdminService.updateServiceLocationApptDatesWindow(homeBean,serviceLocApptDatesReq);
			json = AdminUtils.getJSONDataStr(baseResponse);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateServiceLocationApptDatesWindow", request,e);
		}		
	    return json;	    
	}
	
	@RequestMapping(value="activateSession", method = RequestMethod.GET)
	public @ResponseBody String activateSession(HttpServletRequest request) throws Exception {
		String json = "";
		try{
			HttpSession session = request.getSession(false);
			session.getAttribute("homeBean"); //Accessing Session
			json = "{\"status\": true ,\"response\": \""+"Sucesses"+"\"}";
		}catch(Exception e){
			json = "{\"status\":false ,\"response\": \""+e.getMessage()+"\"}";
		}	
		return json;
	}
}
