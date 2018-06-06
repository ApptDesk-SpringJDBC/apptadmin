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
import com.telappoint.apptadmin.model.PrivilegeSettingResponse;
import com.telappoint.apptadmin.model.PrivilegeSettings;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class PrivilegeSettingsController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(PrivilegeSettingsController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent apptAdminCacheComponent;
	
	@RequestMapping(value="showAcessesPrivilegePage", method = RequestMethod.GET)
	public ModelAndView showAcessesPrivilegePage(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_PRIVILEGE_SETTINGS_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("accessPrivilegeResponse",apptAdminService.getAccessPrivilege(homeBean));
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showAcessesPrivilegePage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="getPrivilegeSettingsPage", method = RequestMethod.GET)
	public ModelAndView getPrivilegeSettingsPage(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam String accessPrivilegeId,
			@RequestParam String accessPrivilegeName,HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_GET_PRIVILEGE_SETTINGS_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("privilegePageMappingResponse",apptAdminService.getPrivilegePageMapping(homeBean,accessPrivilegeId));
			PrivilegeSettingResponse privilegeSettingResponse = apptAdminService.getPrivilegeSettings(homeBean,accessPrivilegeName);
			/*if(privilegeSettingResponse!=null && privilegeSettingResponse.isStatus() && privilegeSettingResponse.getPrivilegeSetting()!=null){
				DisplayNames displayNames = apptAdminCacheComponent.getDisplayNames(homeBean);
				if(displayNames!=null){
					Map<String,List<JSPPagesPrivileges>> updatedPrivilegeSetting = new HashMap<String,List<JSPPagesPrivileges>>();
					Map<String,List<JSPPagesPrivileges>> privilegeSetting = privilegeSettingResponse.getPrivilegeSetting();
					for(String key : privilegeSetting.keySet()){
						List<JSPPagesPrivileges> updatedJSPPagesPrivileges = new ArrayList<JSPPagesPrivileges>();
						List<JSPPagesPrivileges> jspPagesPrivileges = privilegeSetting.get(key);
						for(JSPPagesPrivileges jspPagesPrivilege : jspPagesPrivileges){
							jspPagesPrivilege.setJspPages(updateDisplayNames(key,displayNames));
							updatedJSPPagesPrivileges.add(jspPagesPrivilege);
						}
						key = updateDisplayNames(key,displayNames);
						updatedPrivilegeSetting.put(key, updatedJSPPagesPrivileges);
					}					
					privilegeSettingResponse.setPrivilegeSetting(updatedPrivilegeSetting);
				}
			}*/
			modelView.addObject("privilegeSettingResponse",privilegeSettingResponse);
			modelView.addObject("displayNames",apptAdminCacheComponent.getDisplayNames(homeBean));
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showPrivilegeSettingsPage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "updatePrivilegeSettings")
	public @ResponseBody String updatePrivilegeSettings(@ModelAttribute("homeBean") HomeBean homeBean, 
			@ModelAttribute PrivilegeSettings privilegeSettings,HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try{
			baseResponse = apptAdminService.updatePrivilegeSettings(homeBean, privilegeSettings);
		}catch (Exception e) {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage("Error while updating Privilege Settings!");
			logger.error(ErrorCodesConstants.ERROR_CODE_PRIVILEGE_SETTINGS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"updatePrivilegeSettings", request,e);
		}
		System.out.println(AdminUtils.getJSONDataStr(baseResponse));
		return AdminUtils.getJSONDataStr(baseResponse);
	}
	
	/*private String updateDisplayNames(String displayValue,DisplayNames displayNames){
		if(displayValue!=null){ 
			if(displayValue.contains("LABEL_LOCATIONS")){ 
				displayValue = displayValue.replaceAll("LABEL_LOCATIONS",displayNames.getLocationsName());
			} else if(displayValue.contains("LABEL_LOCATION")){ 
				displayValue = displayValue.replaceAll("LABEL_LOCATION",displayNames.getLocationName());
			}else if(displayValue.contains("LABEL_RESOURCES")){
				displayValue = displayValue.replaceAll("LABEL_RESOURCES",displayNames.getResourcesName());
			}else if(displayValue.contains("LABEL_RESOURCE")){ 
				displayValue = displayValue.replaceAll("LABEL_RESOURCE",displayNames.getResourceName());
			}else if(displayValue.contains("LABEL_SERVICES")){ 
				displayValue = displayValue.replaceAll("LABEL_SERVICES",displayNames.getServicesName());
			}else if(displayValue.contains("LABEL_SERVICE")){ 
				displayValue = displayValue.replaceAll("LABEL_SERVICE",displayNames.getServiceName());
			}else if(displayValue.contains("LABEL_CUSTOMERS")){ 
				displayValue = displayValue.replaceAll("LABEL_CUSTOMERS",displayNames.getCustomersName());
			}else if(displayValue.contains("LABEL_CUSTOMER")){ 
				displayValue = displayValue.replaceAll("LABEL_CUSTOMER",displayNames.getCustomerName());
			}
		}
		return displayValue;		
	}*/
 }
