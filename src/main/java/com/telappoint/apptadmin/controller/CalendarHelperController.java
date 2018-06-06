package com.telappoint.apptadmin.controller;

import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.CommonConstants;
import com.telappoint.apptadmin.constants.ErrorCodesConstants;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.ErrorBean;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.CalendarAvailability;
import com.telappoint.apptadmin.model.CustomerDTO;
import com.telappoint.apptadmin.model.CustomerNamesResponse;
import com.telappoint.apptadmin.model.ResourceServiceResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class CalendarHelperController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(CalendarHelperController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;

	private static final String DEVICE = "admin";
	
	@RequestMapping(value="calendar-resource-service-list", method = RequestMethod.GET)
	public ModelAndView getCalendarResourceServiceList(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam String locationId, final HttpServletRequest request, @RequestParam final String calendarType) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_RESOURCE_SERVICE_LIST_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			logger.info("Fetching resource list for calendar");
			request.setAttribute("calendarType", calendarType);
			final ResourceServiceResponse resourceServiceList = apptAdminService.getResourceServiceList(homeBean, locationId);
			modelView.addObject("resourceServiceResponse", resourceServiceList);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getCalendarResourceServiceList", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;
	}

	@RequestMapping(value="calendar-available-dates", method = RequestMethod.GET)
    @ResponseBody
	public Map<String, CalendarAvailability> getCalendarResourceServiceList(@ModelAttribute("homeBean") HomeBean homeBean,
																			@RequestParam final String locationId,
																			@RequestParam final String resourceIdStr,
																			@RequestParam final String serviceIdStr,
																			@RequestParam final String calendarDate) throws Exception {

		final StringBuffer sb = new StringBuffer("").append("clientCode=").append(homeBean.getUserLoginResponse().getClientCode())
				.append("&device=").append(DEVICE)
				.append("&locationId=").append(locationId != null ? locationId : 1)
				.append("&resourceIdStr=").append(resourceIdStr)
				.append("&departmentId=").append('1')
				.append("&serviceIdStr=").append(serviceIdStr)
				.append("&calendarDate=").append(calendarDate)
				.append("&langCode=").append(CommonConstants.US_LANG_CODE)
				.append("&transId=").append('1');

		return apptAdminService.getAvailableDates(homeBean, sb.toString());
	}

	@RequestMapping(value="getCustomerNames", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomerDTO> getCustomerNames(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam final String customerName, final HttpServletRequest request) throws Exception {
		try{
			final CustomerNamesResponse customerNames = apptAdminService.getCustomerNames(homeBean, customerName);
			return customerNames.getCustomerNames();
		} catch (final Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getCalendarResourceServiceList", request,e);
		}
	    return new ArrayList<>();
	}
}
