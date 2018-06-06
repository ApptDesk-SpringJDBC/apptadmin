package com.telappoint.apptadmin.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.ApptSysConfig;
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.Client;
import com.telappoint.apptadmin.model.HomePageResponse;
import com.telappoint.apptadmin.model.LocationsApptDatesRequest;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.ApptAdminCacheComponent;
import com.telappoint.apptadmin.utils.DateUtils;


/**
 * 
 * @author Murali G
 * 
 */

@Controller
public class ApptAdminHelperController {
	
	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private ApptAdminCacheComponent apptAdminCacheComponent;
	
	public final static String APPCODE = "appt";
	
	public String sucessesMessage = "";
	public String errorMessage = "";
	
	public void populateCommonData(HomeBean homeBean,ModelAndView modelView){
		populateMessages(modelView);
		populateDisplayNames(homeBean, modelView);
		modelView.addObject("privilegedPageNames",apptAdminCacheComponent.getAccessPrivilegedPageNames(homeBean));
	}
	
	public void populateMessages(ModelAndView modelView){
		if(sucessesMessage!=null && !"".equals(sucessesMessage)){
			modelView.addObject("sucessesMessage",sucessesMessage);
			sucessesMessage = "";
		}
		if(errorMessage!=null && !"".equals(errorMessage)){
			modelView.addObject("errorMessage",errorMessage);	
			errorMessage = "";
		}
	}
	
	public void populateDisplayNames(HomeBean homeBean,ModelAndView modelView){
		modelView.addObject("displayNames",apptAdminCacheComponent.getDisplayNames(homeBean));
	}
	
	public void populateHomePageDetails(HttpServletRequest request,ModelAndView modelView,HomeBean homeBean) throws Exception {
		Client client = apptAdminService.getClientDetails(homeBean);
		homeBean.setClientName(client.getClientName());
		modelView.addObject("client",client);
		
		HomePageResponse homePageResponse = apptAdminService.getAdminHomePage(homeBean);
		modelView.addObject("homePageResponse",homePageResponse);
		modelView.addObject("homeBean", homeBean);
				
		ApptSysConfig apptSysConfigs = apptAdminService.getApptSysConfigs(homeBean);
		modelView.addObject("apptSysConfigs",apptSysConfigs);
		populateCommonData(homeBean,modelView);
		
		if("Y".equalsIgnoreCase(apptSysConfigs.getRestrictLocApptWindow())) {
			LocationsApptDatesRequest locationsApptDatesRequest = new LocationsApptDatesRequest();
			locationsApptDatesRequest.setLocations(homePageResponse.getLocationList());
			modelView.addObject("locationsApptDatesRequest",locationsApptDatesRequest);
		}
				
		modelView.setViewName(JspPageNameConstants.APPTADMINDESK_HOME_PAGE);
		
		String defaultDate = DateUtils.getStringFromDate(new Date(),DateUtils.DATE_FORMAT_MMDDYYYY);
		modelView.addObject("defaultDate",defaultDate);
	}
	
	public String getClientCode(HomeBean homeBean){
		String clientCode = "N.A";
		try{
			 clientCode = homeBean.getUserLoginResponse().getClientCode();
			 if(clientCode!=null && clientCode.length()>0){
				 
			 }else{
				 clientCode = "N.A";
			 }
		}catch(Exception e){
			 clientCode = "N.A";
		}
		return clientCode;
	}
	
	public void getUpdatedBaseResponse(BaseResponse baseResponse,String sucessesMsg,String errorMsg){
		if(baseResponse!=null){
			if(baseResponse.isStatus() && (baseResponse.getMessage()==null || baseResponse.getMessage().length()==0)){
				baseResponse.setMessage(sucessesMsg);
			}
		} else {
			baseResponse = new BaseResponse();
			baseResponse.setStatus(false);
			baseResponse.setMessage(errorMsg);
		}
	}
	
	public String getErrorBaseResponseJSON(String errorMsg){
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(false);
		baseResponse.setMessage(errorMsg);
		return AdminUtils.getJSONDataStr(baseResponse);
	}
	
	public List<Integer> populateResourceIds(String resourceSelectedList){
		System.out.println("resourceSelectedList :::: "+resourceSelectedList);
		List<Integer> selectedResourceIds = new ArrayList<Integer>();
		if(resourceSelectedList!=null && resourceSelectedList.trim().length()>0){
			String[] resourceSelectedIdsArray = resourceSelectedList.split(",");
			if(resourceSelectedIdsArray!=null && resourceSelectedIdsArray.length>0){
				for(String resourceSelectedId : resourceSelectedIdsArray){
					if(resourceSelectedId!=null && resourceSelectedId.trim().length()>0){
						selectedResourceIds.add(Integer.parseInt(resourceSelectedId));
					}
				}
			}
		}
		return selectedResourceIds;
	}
}
