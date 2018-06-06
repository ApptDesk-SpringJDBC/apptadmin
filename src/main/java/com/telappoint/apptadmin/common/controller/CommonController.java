package com.telappoint.apptadmin.common.controller;

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

import com.telappoint.apptadmin.constants.ErrorCodesConstants;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.LocationResponse;
import com.telappoint.apptadmin.model.Resource;
import com.telappoint.apptadmin.model.ResourceResponse;
import com.telappoint.apptadmin.model.ServiceResponse;
import com.telappoint.apptadmin.model.ServiceVO;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.StringUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class CommonController  extends ApptAdminHelperController {

	private static Logger logger = Logger.getLogger(CommonController.class);

	@Autowired
	private ApptAdminService apptAdminService;	

	@RequestMapping(value="populateResource", method = RequestMethod.GET)
	public ModelAndView populateResource(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam("locationId") String locationId,@RequestParam("inputType") String inputType,
			@RequestParam(value="onlyActive",required=false) String onlyActive,HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView();
		List<Resource> resourceList =  new ArrayList<Resource>();
		try{
			resourceList = apptAdminService.getResourceListByLocationIdForLoginUser(homeBean,locationId);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"populateResource", request,e);
		}
		modelView.addObject("resourceList",resourceList);
		modelView.addObject("inputType",getInputType(inputType));
		modelView.setViewName("populate-resource");
		return modelView;
	}

	@RequestMapping(value="populateServiceByLocationId", method = RequestMethod.GET)
	public ModelAndView populateServiceByLocationId(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam("locationId") String locationId,@RequestParam(value="onlyActive",required=false) String onlyActive,
			@RequestParam(value="inputType",required=false) String inputType,HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView();
		List<ServiceVO> serviceList =  new ArrayList<ServiceVO>();
		try{	
			serviceList = apptAdminService.getServiceListByLocationIdForLoginUser(homeBean,locationId);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"populateServiceByLocationId", request,e);
		}
		modelView.addObject("serviceList",serviceList);
		modelView.addObject("inputType",getInputType(inputType));
		modelView.setViewName("populate-services");
		return modelView;		
	}

	@RequestMapping(value = "populateServiceByResourceId", method = RequestMethod.GET)
	public ModelAndView populateServiceByResourceId(@ModelAttribute(JspPageNameConstants.HOME_BEAN) HomeBean homeBean,
			@RequestParam String resourceId,@RequestParam(value="onlyActive",required=false) String onlyActive,
			@RequestParam(value="inputType",required=false) String inputType,HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView();
		List<ServiceVO> serviceList =  new ArrayList<ServiceVO>();
		try {       
			serviceList = apptAdminService.getServiceListByResourceIdForLoginUser(homeBean,resourceId);
		}catch (Exception e) {
			logger.error("Error :" + e, e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"populateServiceByResourceId", request,e);
		}  
		modelView.addObject("serviceList", serviceList);
		modelView.addObject("inputType",getInputType(inputType));
		modelView.setViewName("populate-services");
		return modelView;	
	}
	
	@RequestMapping(value="getResourceListByLocationId", method = RequestMethod.GET)
	public @ResponseBody String getResourceListByLocationId(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam("locationId") String locationId,HttpServletRequest request) {
		String json = "";
		try{
			List<Resource> resourceList = apptAdminService.getResourceListByLocationIdForLoginUser(homeBean,locationId);
			json = AdminUtils.getJSONDataStr(resourceList);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getResourceListByLocationId", request,e);
		}
		return json;
	}
	
	@RequestMapping(value="getServiceListByLocationId", method = RequestMethod.GET)
	public @ResponseBody String getServiceListByLocationId(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam("locationId") String locationId,HttpServletRequest request) {
		String json = "";
		try{	
			List<ServiceVO> serviceList = apptAdminService.getServiceListByLocationIdForLoginUser(homeBean,locationId);
			json = AdminUtils.getJSONDataStr(serviceList);
		}catch (Exception e) {
			logger.error("Error : "+e.getMessage(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getServiceListByLocationId", request,e);
		}
		return json;		
	}
	
	@RequestMapping(value = "getServiceListByResourceIds", method = RequestMethod.GET)
	public @ResponseBody String getServiceListByResourceIds(@ModelAttribute(JspPageNameConstants.HOME_BEAN) HomeBean homeBean,
			@RequestParam String resourceIds,HttpServletRequest request) {
		String json = "";
		try {      
			System.out.println("resourceIds :::::::::: "+resourceIds);
			List<ServiceVO> serviceList = apptAdminService.getServiceListByResourceIdForLoginUser(homeBean,resourceIds);
			json = AdminUtils.getJSONDataStr(serviceList);
		}catch (Exception e) {
			logger.error("Error :" + e, e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getServiceListByResourceId", request,e);
		} 
		System.out.println("json ::: "+json);
		return json;	
	}

	@RequestMapping(value = "getServiceListByResourceId", method = RequestMethod.GET)
	public @ResponseBody String getServiceListByResourceId(@ModelAttribute(JspPageNameConstants.HOME_BEAN) HomeBean homeBean,
			@RequestParam String resourceId,@RequestParam(value="onlyActive",required=false) String onlyActive,
			@RequestParam(value="inputType",required=false) String inputType,HttpServletRequest request) {
		String json = "";
		try {       
			List<ServiceVO> serviceList = apptAdminService.getServiceListByResourceIdForLoginUser(homeBean,resourceId);
			json = AdminUtils.getJSONDataStr(serviceList);
		}catch (Exception e) {
			logger.error("Error :" + e, e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getServiceListByResourceId", request,e);
		} 
		return json;	
	}
	
	@RequestMapping(value="getActiveLocationDropDownData",method = RequestMethod.GET)
	public @ResponseBody String getActiveLocationDropDownData(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		LocationResponse locationResponse = null;	
		try{
			locationResponse = apptAdminService.getActiveLocationDropDownData(homeBean);
		}catch (Exception e) {
			locationResponse = new LocationResponse();	
			locationResponse.setStatus(false);
			logger.error(ErrorCodesConstants.ERROR_CODE_GET_ALL_LOCATIONS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getActiveLocationDropDownData", request,e);
		}
		return AdminUtils.getJSONDataStr(locationResponse); 
	}
	
	@RequestMapping(value="getActiveResourceDropDownData",method = RequestMethod.GET)
	public @ResponseBody String getActiveResourceDropDownData(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		ResourceResponse resourceResponse = null;	
		try{
			resourceResponse = apptAdminService.getActiveResourceDropDownData(homeBean);
		}catch (Exception e) {
			resourceResponse = new ResourceResponse();	
			resourceResponse.setStatus(false);
			logger.error(ErrorCodesConstants.ERROR_CODE_GET_ALL_LOCATIONS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getActiveResourceDropDownData", request,e);
		}
		return AdminUtils.getJSONDataStr(resourceResponse); 
	}
	
	@RequestMapping(value="getActiveServiceDropDownData",method = RequestMethod.GET)
	public @ResponseBody String getActiveServiceDropDownData(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		ServiceResponse serviceResponse = null;	
		try{
			serviceResponse = apptAdminService.getActiveServiceDropDownData(homeBean);
		}catch (Exception e) {
			serviceResponse = new ServiceResponse();	
			serviceResponse.setStatus(false);
			logger.error(ErrorCodesConstants.ERROR_CODE_GET_ALL_LOCATIONS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getActiveServiceDropDownData", request,e);
		}
		return AdminUtils.getJSONDataStr(serviceResponse); 
	}

	private String getInputType(String inputType) {
		if(StringUtils.isEmpty(inputType)){
			inputType = "DropDown";
		}
		return inputType;
	}
}
