package com.telappoint.apptadmin.filters;

/**
 * 
 * @author Murali G
 * 
 */

import com.telappoint.apptadmin.constants.CommonConstants;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.service.ApptAdminService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HomeBeanInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(HomeBeanInterceptor.class);

    @Autowired
    private ApptAdminService apptAdminService;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws IOException, ServletException {

        //Release hold when user navigates to other page
        if(!isExemptedServletPath(request.getServletPath()) && request.getSession().getAttribute(CommonConstants.HOLD_APPT_SCHDID_SESSION_CONST) != null){
            final HomeBean homeBean = (HomeBean) request.getSession().getAttribute("homeBean");
            final StringBuffer sb = new StringBuffer("")
                    .append("?clientCode=").append(homeBean.getUserLoginResponse().getClientCode())
                    .append("&device=").append(CommonConstants.ADMIN_DEVICE)
                    .append("&scheduleId=").append(request.getSession().getAttribute(CommonConstants.HOLD_APPT_SCHDID_SESSION_CONST));
            try {
                apptAdminService.releaseHoldAppointment(homeBean, request, sb.toString());
            } catch (Exception e) {
                logger.error("Error while releasing hold",e);
            }
        }
        if (request.getSession().getAttribute("homeBean") == null) {
            logger.error("HomeBean in Session : "+request.getSession().getAttribute("homeBean") );
            String requestURI = request.getRequestURI();
            if(shouldRedirect(requestURI)) {
                String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
                if ("XMLHttpRequest".equals(ajaxHeader)) {
                    //logger.info("Ajax call detected, send {} error code", this.customSessionExpiredErrorCode);
                    //HttpServletResponse resp = (HttpServletResponse) response;
                    response.sendError(1000);
                } else {
                    // redirect to login page
                    response.sendRedirect(JspPageNameConstants.SESSION_EXPIRED_URL);
                    logger.error("Redirecting to "+JspPageNameConstants.SESSION_EXPIRED_URL+" - Because HomeBean in Session : "+request.getSession().getAttribute("homeBean") );
                    //response.setStatus(1000);
                }
                return false; // request handled, no need to bother controller
            }
        } else {
            //Clear Calendar Cache data
            clearCalendarCachedata(request);
        }
        return true;
    }

	 private boolean shouldRedirect(String requestURI) {
		boolean doNotRedirect = requestURI.startsWith("/apptadmin/log") || requestURI.startsWith("/apptadmin/sendResetPasswordRequest")
			|| requestURI.startsWith("/apptadmin/resetPasswordRequest")  || requestURI.startsWith("/apptadmin/updatePassword")
			|| requestURI.startsWith("/apptadmin/resetpassword") || requestURI.startsWith("/apptadmin/validateResetPassword")
			|| requestURI.startsWith("/apptadmin/session-expired")
			|| requestURI.contains(".css") || requestURI.contains(".js")
			|| requestURI.contains(".png") || requestURI.contains(".jp")
			|| requestURI.endsWith("/apptadmin/") || requestURI.endsWith("/apptadmin");
		return !doNotRedirect;
	 }

	 private void clearCalendarCachedata(HttpServletRequest request) {
		 System.out.println("Clear Calendar Cache data ::::::::::::::::: Clear Calendar Cache data");
		 try{
			 HomeBean homeBean = (HomeBean) request.getSession().getAttribute("homeBean");
			 if(homeBean!=null){
				 System.out.println("Clear Calendar Cache data exists!!!!");
				 String requestURI = request.getRequestURI();
				 boolean shouldKeepDailyCalendarData = requestURI.contains(JspPageNameConstants.APPTADMINDESK_RESOURCE_SERVICE_LIST_PAGE)
						 				//|| requestURI.contains(JspPageNameConstants.APPTADMINDESK_DAILY_CALENDAR_PAGE)
						 				|| requestURI.startsWith(JspPageNameConstants.APPTADMINDESK_DAILY_CALENDAR_VIEW);
				 boolean shouldKeepWeeklyCalendarData = requestURI.contains(JspPageNameConstants.APPTADMINDESK_RESOURCE_SERVICE_LIST_PAGE)
						 				//|| requestURI.contains(JspPageNameConstants.APPTADMINDESK_WEEKLY_CALENDAR_PAGE)
						 				|| requestURI.startsWith(JspPageNameConstants.APPTADMINDESK_WEEKLY_CALENDAR_VIEW);
				 boolean shouldKeepMonthlyCalendarData = requestURI.contains(JspPageNameConstants.APPTADMINDESK_RESOURCE_SERVICE_LIST_PAGE)
						 				//|| requestURI.contains(JspPageNameConstants.APPTADMINDESK_MONTHLY_CALENDAR_PAGE)
						 				|| requestURI.startsWith(JspPageNameConstants.APPTADMINDESK_MONTHLY_CALENDAR_VIEW);
				if(shouldKeepDailyCalendarData || shouldKeepWeeklyCalendarData || shouldKeepMonthlyCalendarData){
					//homeBean.setCalendarDataExists(true);
					if(shouldKeepDailyCalendarData){
						homeBean.getCalendarSessionData().setWeeklyCalendarResponse(null);
						homeBean.getCalendarSessionData().setMonthlyCalendarResponse(null);
					}else if(shouldKeepWeeklyCalendarData) {
						homeBean.getCalendarSessionData().setDailyCalendarResponse(null);
						homeBean.getCalendarSessionData().setMonthlyCalendarResponse(null);
					}else if(shouldKeepMonthlyCalendarData) {
						homeBean.getCalendarSessionData().setDailyCalendarResponse(null);
						homeBean.getCalendarSessionData().setWeeklyCalendarResponse(null);
					} else {
						//nothing to do
					}
				} else {
					homeBean.setCalendarSessionData(null);
					//homeBean.setCalendarDataExists(false);
				}
			 } else{
				 System.out.println("There is no Calendar Cache data to Clear");
			 }
		 }catch(Exception e){

		 }
	 }

    private boolean isExemptedServletPath(final String servletPath ){
        final String[] servletPathArray = {
                "/getCustomerNames.json",
                "/confirmAppointment.json",
                "/calendar-right-side-details-section.html",
                "/holdAppointmentResourceList.json",
                "/getCustomerById.json",
                "/saveCustomer.json"};

        final List<String> pathList = Arrays.asList(servletPathArray);
        for(final String pathString : pathList){
            if(pathString.equals(servletPath)){
                return true;
            }

        }
        return false;
    }
}
