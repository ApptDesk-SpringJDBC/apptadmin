package com.telappoint.apptadmin.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.ErrorCodesConstants;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.ErrorBean;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.ResourceCalendarData;
import com.telappoint.apptadmin.model.WeeklyCalendarResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.CalendarCacheComponent;
import com.telappoint.apptadmin.utils.CalendarTimeSlotsComponent;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class WeeklyCalendarController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(WeeklyCalendarController.class);

	//private static final String DEVICE = "admin";
	
	@Autowired
	private ApptAdminService apptAdminService;

	@Autowired
	private CalendarCacheComponent calendarCacheComponent;

	@Autowired
	private CalendarTimeSlotsComponent calendarTimeSlotsComponent;


	@RequestMapping(value="showWeeklyCalendar", method = RequestMethod.GET)
	public ModelAndView showWeeklyCalendar(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_WEEKLY_CALENDAR_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			modelView.addObject("searchDropDownList",apptAdminService.getSearchDropDownList(homeBean));
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showWeeklyCalendar", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}

	@RequestMapping(value="weekly-calendar-view", method = RequestMethod.GET)
	public ModelAndView getWeeklyCalendarView(@ModelAttribute("homeBean") final HomeBean homeBean,
											 @RequestParam final String calendarDate, @RequestParam final String locationId,
											 @RequestParam final String resourceIds, @RequestParam final String selectedField, @RequestParam final String resourceId_ServiceId_Blocks_details, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_WEEKLY_CALENDAR_VIEW;
		ModelAndView modelView = new ModelAndView();
		try{
			System.out.println("\n ------------------------------------------------- "+new Date()+" ----------------------------------------------- \n");
			populateDisplayNames(homeBean, modelView);
			final WeeklyCalendarResponse weeklyCalendarResponse = calendarCacheComponent.getWeeklyCalendarResponse(homeBean, calendarDate, locationId, resourceIds, selectedField);
			modelView.addObject("weeklyCalendarResponse",weeklyCalendarResponse);

			final Map<String,Map<String, String>> resourceIdOpenTimeSlotsMap  = new LinkedHashMap<String,Map<String, String>>();
			final Map<String,Map<String, String>> resourceIdGreyTimeSlotsMap  = new LinkedHashMap<String,Map<String, String>>();
			if(weeklyCalendarResponse!=null && weeklyCalendarResponse.isStatus() && weeklyCalendarResponse.getCalendarDataList()!=null && weeklyCalendarResponse.getCalendarDataList().size()>0){
				final Map<String, String> resourceidServiceidBlocksDetailsMap = new LinkedHashMap<String, String>();
				if(resourceId_ServiceId_Blocks_details!=null){
					final String[] resourceidServiceidBlocksDetailsArr = resourceId_ServiceId_Blocks_details.split(","); //2SEP2_1,11SEP2_1,12SEP2_1,13SEP2_1,14SEP2_1
					if(resourceidServiceidBlocksDetailsArr.length>0){
						logger.info("");
						for(final String resourceidServiceidBlocks : resourceidServiceidBlocksDetailsArr){
							final String[] resourceidServiceidBlocksArr = resourceidServiceidBlocks.split("SEP"); //2SEP2_1
							resourceidServiceidBlocksDetailsMap.put(resourceidServiceidBlocksArr[0],resourceidServiceidBlocksArr[1]);
						}
					}
				}
				for(ResourceCalendarData resourceCalendarData : weeklyCalendarResponse.getCalendarDataList()){
					String serviceId_Blocks = resourceidServiceidBlocksDetailsMap.get(resourceIds);
					if(serviceId_Blocks!=null) {
						String[] serviceId_Blocks_Arr = serviceId_Blocks.split("_");//2_1

						resourceIdOpenTimeSlotsMap.put(String.valueOf(resourceCalendarData.getDate()),
								calendarTimeSlotsComponent.getOpenTimeSlots(resourceCalendarData.getCalendarDataList(),
										Integer.parseInt(locationId),
										resourceCalendarData.getResourceId(),
										Integer.parseInt(serviceId_Blocks_Arr[0]),
										serviceId_Blocks_Arr[1]!=null ? Integer.parseInt(serviceId_Blocks_Arr[1]) : 0,
										weeklyCalendarResponse.getBlockTimeInMins(), resourceCalendarData.getDate()));
						resourceIdGreyTimeSlotsMap.put(String.valueOf(resourceCalendarData.getDate()),calendarTimeSlotsComponent.getGreyTimeSlots(resourceCalendarData.getCalendarDataList(),Integer.parseInt(locationId),resourceCalendarData.getResourceId(),Integer.parseInt(serviceId_Blocks_Arr[0]), serviceId_Blocks_Arr[1]!=null ? Integer.parseInt(serviceId_Blocks_Arr[1]) : 0, weeklyCalendarResponse.getBlockTimeInMins(), resourceCalendarData.getDate()));
					}
				}
			}
			System.out.println("resourceIdOpenTimeSlotsMap :::::::::::::::;; "+resourceIdOpenTimeSlotsMap);
			System.out.println("resourceIdGreyTimeSlotsMap :::::::::::::::;; "+resourceIdGreyTimeSlotsMap);
			modelView.addObject("resourceIdOpenTimeSlotsMap",resourceIdOpenTimeSlotsMap);
			modelView.addObject("resourceIdGreyTimeSlotsMap",resourceIdGreyTimeSlotsMap);
			modelView.addObject("selectedResourceIds", resourceIds);
		} catch (final Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(),
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getPage(),
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getErrorCode(),
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showDailyCalendar", request,e);
		}
		modelView.setViewName(targetPage);
		return modelView;
	}

	/*private String getQueryStringForAvailableDates(final String locationId, final HomeBean homeBean, final String resourceIds){
		final StringBuffer sb = new StringBuffer("").append("clientCode=").append(homeBean.getUserLoginResponse().getClientCode())
				.append("&device=").append(DEVICE)
				.append("&locationId=").append(locationId != null ? locationId : 1)
				.append("&resourceIds=").append('1')
				.append("&departmentId=").append('1')
				.append("&serviceIds=").append('1')
				.append("&langCode=").append(CommonConstants.US_LANG_CODE)
				.append("&transId=").append('1');

		return sb.toString();
	}*/
}
