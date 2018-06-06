package com.telappoint.apptadmin.controller;

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
import com.telappoint.apptadmin.model.Location;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class LocationController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(LocationController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent	apptAdminCacheComponent;
		
	@RequestMapping(value="locations", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showAdminLocationsPage(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_LOCATIONS_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("locationResponse",apptAdminService.getAllLocationsBasicData(homeBean));
			modelView.addObject("dynamicFieldDisplayData",apptAdminCacheComponent.getDynamicFieldDisplayData(homeBean,"location"));
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_LOCATIONS_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_LOCATIONS_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_LOCATIONS_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_LOCATIONS_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showAdminLocationsPage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value=JspPageNameConstants.APPTADMINDESK_SAVE_LOCATION, method = RequestMethod.POST)
	public @ResponseBody String saveLocation(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute("location") Location location, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{			
			baseResponse = apptAdminService.saveLocation(homeBean, location);
			getUpdatedBaseResponse(baseResponse,"Location details saved sucessfully!","Error while saving location");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while saving location");
			logger.error(ErrorCodesConstants.ERROR_CODE_LOCATIONS_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"saveLocation", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value=JspPageNameConstants.APPTADMINDESK_GET_LOCATION_BY_ID,method = RequestMethod.GET)
	public @ResponseBody String getLocationById(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		Location location = null;	
		try{
			location = apptAdminService.getCompleteLocationDataById(homeBean,id);
		}catch (Exception e) {
			location = new Location();	
			logger.error(ErrorCodesConstants.ERROR_CODE_LOCATION_BY_ID.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getLocationById", request,e);
		}
		System.out.println("Location Data ::::: "+AdminUtils.getJSONDataStr(location));
		return AdminUtils.getJSONDataStr(location); 
	}
	
	@RequestMapping(value=JspPageNameConstants.APPTADMINDESK_UPDATE_LOCATION, method = RequestMethod.POST)
	public @ResponseBody String updateLocation(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute("location") Location location, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{	
			baseResponse = apptAdminService.updateLocation(homeBean, location);
			getUpdatedBaseResponse(baseResponse,"Location details updated sucessfully!","Error while updating location");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while updating location");
			logger.error(ErrorCodesConstants.ERROR_CODE_LOCATIONS_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateLocation", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value="deleteLocation",method = RequestMethod.GET)
	public @ResponseBody String deleteLocation(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;	
		try{
			baseResponse = apptAdminService.deleteLocation(homeBean,id);
			getUpdatedBaseResponse(baseResponse,"Location details deleted sucessfully!","Error while deleting location");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while deleting location");	
			logger.error(ErrorCodesConstants.ERROR_CODE_LOCATION_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"deleteLocation", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
	
	@RequestMapping(value="unDeleteLocation",method = RequestMethod.GET)
	public @ResponseBody String unDeleteLocation(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;		
		try{
			baseResponse = apptAdminService.unDeleteLocation(homeBean,id);
			getUpdatedBaseResponse(baseResponse,"Location details undeleted sucessfully!","Error while undeleting location");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while undeleting location");	
			logger.error(ErrorCodesConstants.ERROR_CODE_LOCATION_UN_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"unDeleteLocation", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
 }
