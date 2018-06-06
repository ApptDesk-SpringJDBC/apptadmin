package com.telappoint.apptadmin.controller;

import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.CalendarRequest;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.MonthlyCalendar;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarData;
import com.telappoint.apptadmin.model.monthlycalendar.MonthlyCalendarResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.service.MonthlyCalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes(value = "homeBean", types = HomeBean.class)
public class MonthlyCalendarController extends ApptAdminHelperController {

    //private Logger logger = Logger.getLogger(MonthlyCalendarController.class);

    @Autowired
    private ApptAdminService apptAdminService;

    @Autowired
    private MonthlyCalenderService monthlyCalenderService;

    @RequestMapping(value = "monthly-calendar-view", method = RequestMethod.GET)
    public ModelAndView showMonthlyCalendar(@ModelAttribute("homeBean") final HomeBean homeBean, final CalendarRequest calendarRequest) throws Exception {
        final String targetPage = JspPageNameConstants.APPTADMINDESK_MONTHLY_CALENDAR_VIEW;
        final ModelAndView modelView = new ModelAndView();
        final MonthlyCalendarResponse response = apptAdminService.getMonthlyCalendar(homeBean, calendarRequest);
        final MonthlyCalendar monthlyCalendar = monthlyCalenderService.getMonthlyCalendar(response, calendarRequest);

		/*
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
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showMonthlyCalendar", request,e);
		}
*/
        modelView.addObject("monthlyCalendar", monthlyCalendar);
        modelView.setViewName(targetPage);
        return modelView;
    }

	/*@RequestMapping(value="getSearchResponse", method = RequestMethod.GET)
	public @ResponseBody String getInBoundCallReport(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam(value="searchSelectionType",required=false) String searchSelectionType, //Search By
			@RequestParam(value="firstName",required=false) String firstName,@RequestParam(value="lastName",required=false) String lastName, //Search By - FIRSTNAME_LASTNAME
			@RequestParam(value="confirmationNumber",required=false) String confirmationNumber, //Search By - CONFIRMATION_NO
			@RequestParam(value="accountNumber",required=false) String accountNumber, //Search By - SSN / SSN_LAST_7
			@RequestParam(value="contactPhone",required=false) String contactPhone, //Search By - CONTACT_PHONE
			@RequestParam(value="callerId",required=false) String callerId, //Search By - CALLER_ID
			@RequestParam(value="energyAccNO",required=false) String attrib1, //Search By - ENERGY_ACCT_NO - attrib1
			@RequestParam(value="dob",required=false) String dob, //Search By - DOB
			@RequestParam(value="houseHoldId",required=false) String houseHoldId, //Search By - HOUSE_HOLD
			HttpServletRequest request) throws Exception {
		SearchResponse searchResponse = null;
		try{
			searchResponse = apptAdminService.getSearchResponse(homeBean, searchSelectionType, firstName, lastName, confirmationNumber, accountNumber, contactPhone, callerId, attrib1, dob, houseHoldId);
			searchResponse.setStatus(true);
		}catch (Exception e) {
			searchResponse =  new SearchResponse();
			searchResponse.setStatus(false);
			searchResponse.setMessage("Error while retriving Search data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_SEARCH_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getSearchResponse", request,e);
		}
		System.out.println("getSearchResponse ::: "+AdminUtils.getJSONDataStr(searchResponse));
		return AdminUtils.getJSONDataStr(searchResponse);
	}*/
}
