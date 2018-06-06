package com.telappoint.apptadmin.controller;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.telappoint.apptadmin.client.contants.ApptAdminRestConstants;
import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.CommonConstants;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.CancelAppointResponse;
import com.telappoint.apptadmin.model.ConfirmAppointmentRequest;
import com.telappoint.apptadmin.model.ConfirmAppointmentResponse;
import com.telappoint.apptadmin.model.HoldAppt;
import com.telappoint.apptadmin.model.ServiceVO;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.PropertyUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class AppointmentController extends ApptAdminHelperController {
	
	private final Logger logger = Logger.getLogger(AppointmentController.class);

	@Autowired
	private ApptAdminService apptAdminService;

	private static final String DEVICE = "admin";
	
	@RequestMapping(value="holdAppointment", method = RequestMethod.POST)
	@ResponseBody
	public HoldAppt holdAppointment(@ModelAttribute("homeBean") final HomeBean homeBean,
									final HttpServletRequest request,
									@RequestParam final String locationId,
									@RequestParam final String resourceId,
									@RequestParam final String serviceId,
									@RequestParam final String customerId,
									@RequestParam final String apptDateTime) throws Exception {

		final String procedureId = "1";
		final String departmentId = "1";
		final String langCode = CommonConstants.US_LANG_CODE;
		final String transId = "1";
		final String dateTimeStr = getFormattedApptDateTime(apptDateTime);

		logger.info("holding appointment with locationId=" + locationId + ", resourceId="+resourceId+", procedureId="+procedureId+", departmentId="+departmentId+", serviceId="+serviceId+", customerId="+customerId+", dateTimeStr="+dateTimeStr+", DEVICE, langCode, transId");
		final HoldAppt holdAppt = apptAdminService.holdAppointment(homeBean, locationId, resourceId, procedureId, departmentId,
																	serviceId, customerId, dateTimeStr, DEVICE, langCode, transId);
		request.getSession().setAttribute(CommonConstants.HOLD_APPT_SCHDID_SESSION_CONST, holdAppt.getScheduleId());
        logger.info("Hold appt response: " + holdAppt.toString());
		return holdAppt;
	}

	@RequestMapping(value="holdAppointmentResourceList", method = RequestMethod.GET)
	@ResponseBody
	public List<ServiceVO> holdAppointmentResourceList(@ModelAttribute("homeBean") final HomeBean homeBean,
													   @RequestParam final String locationId, @RequestParam final String resourceId,
													   @RequestParam final String serviceId) throws Exception {

		return apptAdminService.holdAppointmentServiceList(homeBean, locationId, resourceId, serviceId);
	}

	@RequestMapping(value="updateBookedAppointment", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse updateBookedAppointment(@ModelAttribute("homeBean") final HomeBean homeBean,
                                                @RequestParam("bookApptComments") final String comments,
                                                @RequestParam final String scheduleId,
												@RequestParam final String serviceId) throws Exception {

        final String url = PropertyUtils.getApptAdminRestServiceEndPointURL().concat(ApptAdminRestConstants.REST_SERVICE_SAVE_BOOKED_APPT.getRequestURI());
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("comments", comments)
                .queryParam("scheduleId", scheduleId)
                .queryParam("clientCode", homeBean.getUserLoginResponse().getClientCode())
                .queryParam("serviceId", serviceId);
		return apptAdminService.updateConfirmAppointment(homeBean, builder.build().toString());
	}

	@RequestMapping(value="releaseHoldAppointment", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse releaseHoldAppointment(@ModelAttribute("homeBean") final HomeBean homeBean,final HttpServletRequest request,
													   @RequestParam final String scheduleId) throws Exception {

		StringBuffer sb = new StringBuffer("")
                .append("?clientCode=").append(homeBean.getUserLoginResponse().getClientCode())
                .append("&device=").append(DEVICE)
                .append("&scheduleId=").append(scheduleId);
		return apptAdminService.releaseHoldAppointment(homeBean, request, sb.toString());
	}

    @RequestMapping(value="confirmAppointment", method = RequestMethod.POST)
    @ResponseBody
    public ConfirmAppointmentResponse confirmAppointment(@ModelAttribute("homeBean") final HomeBean homeBean, final HttpServletRequest servletRequest,
                                    @RequestParam final String customerId,
                                    @RequestParam final String bookApptComments,
                                    @RequestParam final String serviceId,
                                    @RequestParam final Long scheduleId) throws Exception {

        final String langCode = CommonConstants.US_LANG_CODE;

        logger.info("confirming appointment with  customerId="+customerId+", scheduleId="+scheduleId+", DEVICE, langCode, transId");
        final ConfirmAppointmentRequest request = new ConfirmAppointmentRequest();
        request.setLangCode(langCode);
        request.setScheduleId(scheduleId);
        request.setCustomerId(Long.valueOf(customerId));
        request.setComments(bookApptComments);
        request.setServiceId(Long.valueOf(serviceId));

        final ConfirmAppointmentResponse response = apptAdminService.confirmAppointment(homeBean, request);
		if(response.isStatus()){
			servletRequest.getSession().removeAttribute(CommonConstants.HOLD_APPT_SCHDID_SESSION_CONST);
		}
        return response;
    }

	@RequestMapping(value="cancelAppointment1", method = RequestMethod.POST)
	@ResponseBody
	public CancelAppointResponse cancelAppointment(@ModelAttribute("homeBean") final HomeBean homeBean,
												   @RequestParam final String customerId, @RequestParam final String scheduleId) throws Exception {

		return apptAdminService.cancelAppointment(homeBean, "us-en", scheduleId, customerId);
	}

	@RequestMapping(value="rescheduleAppointment", method = RequestMethod.POST)
	@ResponseBody
	public ConfirmAppointmentResponse rescheduleAppointment(@ModelAttribute("homeBean") final HomeBean homeBean,
															@RequestParam final String customerId,
															@RequestParam final String locationId,
															@RequestParam final String resourceId,
															@RequestParam final String oldScheduleId,
															@RequestParam final String serviceId,
															@RequestParam final String apptDateTime) throws Exception {

        final String procedureId = "1";
        final String departmentId = "1";
        final String langCode = CommonConstants.US_LANG_CODE;
        final String transId = "1";
        final String dateTimeStr = getFormattedApptDateTime(apptDateTime);

        return apptAdminService.rescheduleAppointment(homeBean,locationId,resourceId,procedureId,departmentId,serviceId,customerId,dateTimeStr,DEVICE,langCode,transId,oldScheduleId);
	}

	private String getFormattedApptDateTime(final String apptDateTime) {
		//03/27/2017 08:15
		final String[] dateTimeArr = apptDateTime.split(" ");
		final String[] dateArr = dateTimeArr[0].split("/");
		final String dateStr = new StringBuilder("").append(dateArr[2]).append('-').append(dateArr[0]).append('-').append(dateArr[1]).toString();
		return new StringBuilder(dateStr).append(' ').append(dateTimeArr[1]).append(":00").toString();
	}
}
