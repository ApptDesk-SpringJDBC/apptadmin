package com.telappoint.apptadmin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

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
import com.telappoint.apptadmin.model.LocationResponse;
import com.telappoint.apptadmin.model.ServiceResponse;
import com.telappoint.apptadmin.model.ServiceVO;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ServiceController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(ServiceController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent	apptAdminCacheComponent;
	
	@RequestMapping(value="services", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showServicesPage(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_SERVICES_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("serviceResponse",apptAdminService.getAllServicesBasicData(homeBean));
			modelView.addObject("dynamicFieldDisplayData",apptAdminCacheComponent.getDynamicFieldDisplayData(homeBean,"service"));
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_SERVICES_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_SERVICES_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_SERVICES_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_SERVICES_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showServicesPage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="saveService", method = RequestMethod.POST)
	public @ResponseBody String saveService(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute ServiceVO service, HttpServletRequest request) throws Exception {
		ServiceResponse baseResponse = null;
		try{			
			baseResponse = apptAdminService.saveService(homeBean, service);
			getUpdatedBaseResponse(baseResponse,"Service details saved sucessfully!","Error while saving Service");
		}catch (Exception e) {
			baseResponse = new ServiceResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while saving Service");
			logger.error(ErrorCodesConstants.ERROR_CODE_SERVICES_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"saveService", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value="getServiceById",method = RequestMethod.GET)
	public @ResponseBody String getServiceById(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		ServiceVO service = null;	
		try{
			service = apptAdminService.getCompleteServiceDataById(homeBean,id);
		}catch (Exception e) {
			service = new ServiceVO();	
			logger.error(ErrorCodesConstants.ERROR_CODE_SERVICES_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getServiceById", request,e);
		}
		return AdminUtils.getJSONDataStr(service); 
	}
	
	@RequestMapping(value="updateService", method = RequestMethod.POST)
	public @ResponseBody String updateService(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute ServiceVO service, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;
		try{	
			baseResponse = apptAdminService.updateService(homeBean, service);
			getUpdatedBaseResponse(baseResponse,"Service details updated sucessfully!","Error while updating Service");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while updating Service");
			logger.error(ErrorCodesConstants.ERROR_CODE_SERVICES_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updateService", request,e);
		}
	    return AdminUtils.getJSONDataStr(baseResponse);  
	}
	
	@RequestMapping(value="deleteService",method = RequestMethod.GET)
	public @ResponseBody String deleteService(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;	
		try{
			baseResponse = apptAdminService.deleteService(homeBean,id);
			getUpdatedBaseResponse(baseResponse,"Service details deleted sucessfully!","Error while deleting Service");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while deleting Service");	
			logger.error(ErrorCodesConstants.ERROR_CODE_SERVICES_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"deleteService", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
	
	@RequestMapping(value="unDeleteService",method = RequestMethod.GET)
	public @ResponseBody String unDeleteService(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id, HttpServletRequest request) throws Exception {
		BaseResponse baseResponse = null;		
		try{
			baseResponse = apptAdminService.unDeleteService(homeBean,id);
			getUpdatedBaseResponse(baseResponse,"Service details undeleted sucessfully!","Error while undeleting Service");
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while undeleting Service");	
			logger.error(ErrorCodesConstants.ERROR_CODE_SERVICES_UN_DELETE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"unDeleteService", request,e);
		}
		return AdminUtils.getJSONDataStr(baseResponse); 
	}
	
	@RequestMapping(value="getLocationsByServiceIdToCloseServiceStatus",method = RequestMethod.GET)
	public @ResponseBody String getLocationsByServiceIdToCloseServiceStatus(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("id") String id,HttpServletRequest request) throws Exception {
		LocationResponse locationResponse = null;	
		try{
			locationResponse = apptAdminService.getLocationsByServiceIdToCloseServiceStatus(homeBean,id);
		}catch (Exception e) {
			locationResponse = new LocationResponse();	
			locationResponse.setStatus(false);
			logger.error(ErrorCodesConstants.ERROR_CODE_GET_ALL_LOCATIONS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getLocationsByServiceIdToCloseServiceStatus", request,e);
		}
		return AdminUtils.getJSONDataStr(locationResponse); 
	}
	
	@RequestMapping(value="getBreakTimeDuration",method = RequestMethod.GET)
	public @ResponseBody String getBreakTimeDuration(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("blockTimeInMin") String blockTimeInMin,HttpServletRequest request) throws Exception {
		Map<String, String> breakTimeDurDropDownMap = new LinkedHashMap<String, String>();
		try{
			breakTimeDurDropDownMap = AdminUtils.getBreakTimeDuration(Integer.parseInt(blockTimeInMin));
		}catch (Exception e) {
			breakTimeDurDropDownMap = new LinkedHashMap<String, String>();
			logger.error(ErrorCodesConstants.ERROR_CODE_GET_ALL_LOCATIONS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getLocationsByServiceIdToCloseServiceStatus", request,e);
		}
		return AdminUtils.getJSONDataStr(breakTimeDurDropDownMap); 
	}
 }
