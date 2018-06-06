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
import com.telappoint.apptadmin.model.UserActivityLogsResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.DateUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class UserActivityLogController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(UserActivityLogController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	
	@RequestMapping(value="userActivityLog", method = RequestMethod.GET)
	public ModelAndView showUserActivityLogPage(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_USER_ACTIVITY_LOG_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("startDate",DateUtils.getMonthStartDate(DateUtils.DATE_FORMAT_MMDDYYYY));
			modelView.addObject("endDate",DateUtils.getMonthEndDate(DateUtils.DATE_FORMAT_MMDDYYYY));
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_USER_ACTIVITY_LOG_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_USER_ACTIVITY_LOG_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_USER_ACTIVITY_LOG_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_USER_ACTIVITY_LOG_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showUserActivityLogPage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "getUserActivityLog")
	public @ResponseBody String getUserActivityLog(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam String startDate,@RequestParam String endDate,HttpServletRequest request) {
		UserActivityLogsResponse userActivityLogsResponse = null;
		try{
			userActivityLogsResponse =  apptAdminService.getUserActivityLogs(homeBean, startDate, endDate);
		}catch (Exception e) {
			userActivityLogsResponse = new UserActivityLogsResponse();
			userActivityLogsResponse.setStatus(false);
			userActivityLogsResponse.setMessage("Error while retriving User Activity Log!");
			logger.error(ErrorCodesConstants.ERROR_CODE_USER_ACTIVITY_LOG_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getUserActivityLog", request,e);
		}
		System.out.println(AdminUtils.getJSONDataStr(userActivityLogsResponse));
		return AdminUtils.getJSONDataStr(userActivityLogsResponse);
	}
 }
