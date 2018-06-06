package com.telappoint.apptadmin.utils;

import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.CalendarSessionData;
import com.telappoint.apptadmin.model.DailyCalendarResponse;
import com.telappoint.apptadmin.model.WeeklyCalendarResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarCacheComponent {

	@Autowired
	private ApptAdminService apptAdminService;
	
	private Logger logger = Logger.getLogger(CalendarCacheComponent.class);
	
	public DailyCalendarResponse getDailyCalendarResponse(HomeBean homeBean,String calendarDate,String locationId,String resourceIds,String selectedField) {
		DailyCalendarResponse dailyCalendarResponse = null;
		try{
			System.out.println("selectedField ::::::::::::: "+selectedField);
			if("Service_Field".equals(selectedField)){
				if(homeBean.getCalendarSessionData()!=null) {
					dailyCalendarResponse = homeBean.getCalendarSessionData().getDailyCalendarResponse();
					if(dailyCalendarResponse==null){				 
						dailyCalendarResponse = apptAdminService.getDailyCalendar(homeBean, calendarDate, locationId, resourceIds);
						System.out.println("###################  Daily Calendar Response read from  --  BACKEND --  ###################");
					}else{
						System.out.println("###################  Daily Calendar Response read from  --  CACHE --  ###################");
					}			
				} else {
					dailyCalendarResponse = apptAdminService.getDailyCalendar(homeBean, calendarDate, locationId, resourceIds);
					CalendarSessionData calendarSessionData = new CalendarSessionData();
					homeBean.setCalendarSessionData(calendarSessionData);
					System.out.println("###################  Daily Calendar Response read from  --  BACKEND --  ###################");					
				}
			} else {
				CalendarSessionData calendarSessionData = new CalendarSessionData();
				homeBean.setCalendarSessionData(calendarSessionData);
				dailyCalendarResponse = apptAdminService.getDailyCalendar(homeBean, calendarDate, locationId, resourceIds);
				System.out.println("###################  Daily Calendar Response read from  --  BACKEND --  ###################");		
			}
			homeBean.getCalendarSessionData().setDailyCalendarResponse(dailyCalendarResponse);
		} catch(Exception e){
			dailyCalendarResponse = new DailyCalendarResponse();
			logger.error("Error :: "+e.getMessage(),e);
		}
		return dailyCalendarResponse;		
	}

	public WeeklyCalendarResponse getWeeklyCalendarResponse(final HomeBean homeBean, final String calendarDate,
															final String locationId, final String resourceIds,
															final String selectedField) {
		WeeklyCalendarResponse weeklyCalendarResponse = null;
		try{
			if("Service_Field".equals(selectedField)){
				if(homeBean.getCalendarSessionData()!=null && homeBean.getCalendarSessionData().getWeeklyCalendarResponse() !=null) {
						weeklyCalendarResponse = homeBean.getCalendarSessionData().getWeeklyCalendarResponse();
				} else {
					weeklyCalendarResponse = apptAdminService.getWeeklyCalendar(homeBean, calendarDate, locationId, resourceIds);
					final CalendarSessionData calendarSessionData = new CalendarSessionData();
					calendarSessionData.setWeeklyCalendarResponse(weeklyCalendarResponse);
					homeBean.setCalendarSessionData(calendarSessionData);
				}
			} else {
				final CalendarSessionData calendarSessionData = new CalendarSessionData();
				homeBean.setCalendarSessionData(calendarSessionData);
				weeklyCalendarResponse = apptAdminService.getWeeklyCalendar(homeBean, calendarDate, locationId, resourceIds);
			}
			homeBean.getCalendarSessionData().setWeeklyCalendarResponse(weeklyCalendarResponse);
		} catch(final Exception e){
			weeklyCalendarResponse = new WeeklyCalendarResponse();
			logger.error("Error :: "+e.getMessage(),e);
		}
		return weeklyCalendarResponse;
	}

}
