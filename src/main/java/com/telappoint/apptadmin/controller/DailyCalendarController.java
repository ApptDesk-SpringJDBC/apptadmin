package com.telappoint.apptadmin.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.telappoint.apptadmin.utils.*;
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
import com.telappoint.apptadmin.model.DailyCalendarResponse;
import com.telappoint.apptadmin.model.DisplayNames;
import com.telappoint.apptadmin.model.ResourceCalendarData;
import com.telappoint.apptadmin.model.VerifyPageData;
import com.telappoint.apptadmin.service.ApptAdminService;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class DailyCalendarController extends ApptAdminHelperController {

	private Logger logger = Logger.getLogger(DailyCalendarController.class);
	private static final String CONFIRM_RESCHEDULE = "confirm_reschedule";

	//private static final String DEVICE = "admin";

	@Autowired
	private ApptAdminService apptAdminService;
	@Autowired
	private CalendarCacheComponent calendarCacheComponent;
	@Autowired
	private CalendarTimeSlotsComponent calendarTimeSlotsComponent;

	@Autowired
	private ApptAdminCacheComponent apptAdminCacheComponent;

	@RequestMapping(value="show-daily-calendar", method = RequestMethod.GET)
	public ModelAndView showDailyCalendar(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_DAILY_CALENDAR_PAGE;
		final ModelAndView modelView = new ModelAndView();
		try{
			populateDisplayNames(homeBean, modelView);
			modelView.addObject("locationList",apptAdminService.getLocationListForLoginUser(homeBean));
			modelView.addObject("messagesMap",loadMessages(homeBean));
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			final ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(),
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

	@RequestMapping(value="daily-calendar-view", method = RequestMethod.GET)
	public ModelAndView getDailyCalendarView(@ModelAttribute("homeBean") final HomeBean homeBean,
			@RequestParam final String calendarDate,@RequestParam final String locationId,
			@RequestParam final String resourceIds,@RequestParam final String selectedResourceIds,
			@RequestParam final String selectedField, @RequestParam final String resourceId_ServiceId_Blocks_details, final HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_DAILY_CALENDAR_VIEW;
		final ModelAndView modelView = new ModelAndView();
		try{
			logger.debug(" ------------------------------------------------- "+new Date()+" ----------------------------------------------- ");
			populateDisplayNames(homeBean, modelView);
			final DailyCalendarResponse dailyCalendarResponse = calendarCacheComponent.getDailyCalendarResponse(homeBean, calendarDate, locationId, resourceIds, selectedField);
			modelView.addObject("dailyCalResponse",dailyCalendarResponse);

			final Map<String,Map<String, String>> resourceIdOpenTimeSlotsMap  = new LinkedHashMap<String,Map<String, String>>();
			final Map<String,Map<String, String>> resourceIdGreyTimeSlotsMap  = new LinkedHashMap<String,Map<String, String>>();
			if(dailyCalendarResponse!=null && dailyCalendarResponse.isStatus()
					&& dailyCalendarResponse.getCalendarDataList()!=null && dailyCalendarResponse.getCalendarDataList().size()>0){
				Map<String, String> resourceId_ServiceId_Blocks_details_map = new LinkedHashMap<String, String>();
				if(resourceId_ServiceId_Blocks_details!=null){
					String[] resourceId_ServiceId_Blocks_details_Arr = resourceId_ServiceId_Blocks_details.split(","); //2SEP2_1,11SEP2_1,12SEP2_1,13SEP2_1,14SEP2_1
					if(resourceId_ServiceId_Blocks_details_Arr!=null && resourceId_ServiceId_Blocks_details_Arr.length>0){
						for(String resourceId_ServiceId_Blocks : resourceId_ServiceId_Blocks_details_Arr){
							String[] resourceId_ServiceId_Blocks_arr = resourceId_ServiceId_Blocks.split("SEP"); //2SEP2_1
							resourceId_ServiceId_Blocks_details_map.put(resourceId_ServiceId_Blocks_arr[0],resourceId_ServiceId_Blocks_arr[1]);
						}
					}
				}
				for(final ResourceCalendarData resourceCalendarData : dailyCalendarResponse.getCalendarDataList()){
					final String serviceIdBlocks = resourceId_ServiceId_Blocks_details_map.get(String.valueOf(resourceCalendarData.getResourceId()));
					if(serviceIdBlocks!=null) {
						final String[] serviceId_Blocks_Arr = serviceIdBlocks.split("_");//2_1
						logger.debug("----------------------------------------------------");
						logger.debug("Resource Id ::: "+resourceCalendarData.getResourceId());
						logger.debug("Service Id ::: "+(serviceId_Blocks_Arr[0]));
						logger.debug("Blocks ::: "+(serviceId_Blocks_Arr[1]));
						logger.debug("BlockTimeInMins ::: "+dailyCalendarResponse.getBlockTimeInMins());
						logger.debug(AdminUtils.getDataFromJSON(resourceCalendarData.getCalendarDataList()));
						logger.debug("----------------------------------------------------");
						resourceIdOpenTimeSlotsMap.put(String.valueOf(resourceCalendarData.getResourceId()),calendarTimeSlotsComponent.getOpenTimeSlots(resourceCalendarData.getCalendarDataList(),Integer.parseInt(locationId),resourceCalendarData.getResourceId(),Integer.parseInt(serviceId_Blocks_Arr[0]), serviceId_Blocks_Arr[1]!=null ? Integer.parseInt(serviceId_Blocks_Arr[1]) : 0, dailyCalendarResponse.getBlockTimeInMins(), calendarDate));
						resourceIdGreyTimeSlotsMap.put(String.valueOf(resourceCalendarData.getResourceId()),calendarTimeSlotsComponent.getGreyTimeSlots(resourceCalendarData.getCalendarDataList(),Integer.parseInt(locationId),resourceCalendarData.getResourceId(),Integer.parseInt(serviceId_Blocks_Arr[0]), serviceId_Blocks_Arr[1]!=null ? Integer.parseInt(serviceId_Blocks_Arr[1]) : 0, dailyCalendarResponse.getBlockTimeInMins(), calendarDate));
					}
				}
			}
			logger.debug("resourceIdOpenTimeSlotsMap :::::::::::::::;; "+resourceIdOpenTimeSlotsMap);
			logger.debug("resourceIdGreyTimeSlotsMap :::::::::::::::;; "+resourceIdGreyTimeSlotsMap);
			modelView.addObject("resourceIdOpenTimeSlotsMap",resourceIdOpenTimeSlotsMap);
			modelView.addObject("resourceIdGreyTimeSlotsMap",resourceIdGreyTimeSlotsMap);
			modelView.addObject("selectedResourceIds",(selectedResourceIds!=null ? Arrays.asList(selectedResourceIds.split(",")) : new ArrayList<String>()));
		}catch (Exception e) {
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

	@RequestMapping(value="calendar-right-side-details-section", method = RequestMethod.GET)
	public ModelAndView getCalendarRightSideDetailsSection(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_CALENDAR_CUSTOMER_APPT_VIEW;
		final ModelAndView modelView = new ModelAndView();
		try{
			populateDisplayNames(homeBean, modelView);
			modelView.addObject("regDetailsList",apptAdminService.getCustomerRegistrationDetails(homeBean,"us-en")); //Needs to read
			final DisplayNames displayNames = apptAdminCacheComponent.getDisplayNames(homeBean);
			modelView.addObject("displayNames", displayNames);
			final String customerId = request.getParameter("customerId");
			final String scheduleId = request.getParameter("scheduleId");
			final String date = request.getParameter("date");
			final String time = request.getParameter("time");
			if (customerId != null && scheduleId != null ){
				final VerifyPageData appointmentInfo = apptAdminService.getAppointmentInfo(homeBean, customerId, scheduleId, "1");
                logger.info("verifyPageData: " + appointmentInfo);
                if(appointmentInfo != null && appointmentInfo.getApptDateTime().contains("2013")){
                    final String[] timeSplit = time.split(":");
                    String x = Integer.parseInt(timeSplit[0]) > 6 ? " AM" : " PM";
                    String newDateAndTime = date + " " + time + x;
                    appointmentInfo.setApptDateTime(newDateAndTime);
                }
				modelView.addObject("verifyPageData", appointmentInfo);
			}
		}catch (final Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			final ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(),
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getPage(),
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getErrorCode(),
					ErrorCodesConstants.ERROR_CODE_DAILY_CALENDAR_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getCalendarRightSideDetailsSection", request,e);
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

	private Map<String,String> loadMessages(final HomeBean homeBean){
		final HashMap<String,String> messagesMap = new HashMap<>();
		final String messageKey = StringUtils.isNotEmpty(homeBean.getUserLoginResponse().getClientCode()) ? homeBean.getUserLoginResponse().getClientCode().toLowerCase()
				.concat("_").concat(CONFIRM_RESCHEDULE) : CONFIRM_RESCHEDULE;
		messagesMap.put(CONFIRM_RESCHEDULE,PropertyUtils.getAppMessagesProperties().getProperty(messageKey));
		return messagesMap;
	}

 }
