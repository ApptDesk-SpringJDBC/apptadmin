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
import com.telappoint.apptadmin.model.InBoundCallsResponse;
import com.telappoint.apptadmin.model.OutBoundCallsResponse;
import com.telappoint.apptadmin.model.TransStateResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class CallReportController extends ApptAdminHelperController { 
	
	/*//TODO : Dummy needs to remove
	int inouBoundEndCount = 10;
	int outBountEndCount = 10;
	*/
	private Logger logger = Logger.getLogger(CallReportController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	
	@RequestMapping(value="call-report", method = RequestMethod.GET)
	public ModelAndView showCallReport(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_CALL_REPORT_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			populateDisplayNames(homeBean, modelView);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_CALL_REPORT_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_CALL_REPORT_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_CALL_REPORT_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_CALL_REPORT_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showCallReport", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="getInBoundCallReport", method = RequestMethod.GET)
	public @ResponseBody String getInBoundCallReport(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate, HttpServletRequest request) throws Exception {
		InBoundCallsResponse inBoundCallsResponse = null;
		try{
			inBoundCallsResponse = apptAdminService.getInBoundCallLogs(homeBean, fromDate, toDate);	
			inBoundCallsResponse.setStatus(true);
			//addDummyIVRCalls(inBoundCallsResponse);
		}catch (Exception e) {
			inBoundCallsResponse =  new InBoundCallsResponse();
			inBoundCallsResponse.setStatus(false);
			inBoundCallsResponse.setMessage("Error while retriving InBound Call Report data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_CALL_REPORT_IN_BOUND.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getInBoundCallReport", request,e);
		}
		System.out.println("inBoundCallsResponse ::: "+AdminUtils.getJSONDataStr(inBoundCallsResponse));
		return AdminUtils.getJSONDataStr(inBoundCallsResponse);  
	}
	
	@RequestMapping(value="getOutBoundCallReport", method =RequestMethod.GET)
	public @ResponseBody String getOutBoundCallReport(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate, HttpServletRequest request) throws Exception {
		OutBoundCallsResponse outBoundCallsResponse = null;
		try{
			outBoundCallsResponse = apptAdminService.getOutBoundCallLogs(homeBean, fromDate, toDate);
			outBoundCallsResponse.setStatus(true);
			//addDummyOutCalls(outBoundCallsResponse);
		}catch (Exception e) {
			outBoundCallsResponse =  new OutBoundCallsResponse();
			outBoundCallsResponse.setStatus(false);
			outBoundCallsResponse.setMessage("Error while retriving OutBound Call Report data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_CALL_REPORT_OUT_BOUND.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getOutBoundCallReport", request,e);
		}
		System.out.println("outBoundCallsResponse ::: "+AdminUtils.getJSONDataStr(outBoundCallsResponse));
		return AdminUtils.getJSONDataStr(outBoundCallsResponse);  
	}
	
	@RequestMapping(value="getTransStates", method =RequestMethod.GET)
	public @ResponseBody String getTransStates(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("transId") String transId, HttpServletRequest request) throws Exception {
		TransStateResponse transStateResponse = null;
		try{
			transStateResponse = apptAdminService.getTransStates(homeBean,transId);
			transStateResponse.setStatus(true);
			//addTransStateResponse(transStateResponse);
		}catch (Exception e) {
			transStateResponse =  new TransStateResponse();
			transStateResponse.setStatus(false);
			transStateResponse.setMessage("Error while retriving Trans States data!");
			logger.error(ErrorCodesConstants.ERROR_CODE_CALL_REPORT_OUT_BOUND.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getOutBoundCallReport", request,e);
		}
		System.out.println("transStateResponse ::: "+AdminUtils.getJSONDataStr(transStateResponse));
		return AdminUtils.getJSONDataStr(transStateResponse);  
	}
	
	/*//TODO: Dummy method needs to remove
	private void addDummyIVRCalls(InBoundCallsResponse inBoundCallsResponse){
		List<IvrCalls> listOfIvrCalls = new ArrayList<IvrCalls>();
		IvrCalls ivrCalls = null;
		for (Integer i = 1; i <= inouBoundEndCount; i++) {
			ivrCalls = new IvrCalls();
			ivrCalls.setTransId(Long.valueOf(i.longValue()));
			ivrCalls.setApptType("IN - Appt Type : "+i);
			ivrCalls.setCustomerFirstName("IN - F.Name : "+i);
			ivrCalls.setCustomerLastName("IN - L.Name : "+i);
			listOfIvrCalls.add(ivrCalls);
		}
		List<IvrCalls> inBoundCallLlistOfIvrCalls = inBoundCallsResponse.getIvrCallLogs();
		if(inBoundCallLlistOfIvrCalls==null){
			inBoundCallLlistOfIvrCalls = new ArrayList<IvrCalls>();
		}
		inBoundCallLlistOfIvrCalls.addAll(listOfIvrCalls);
		inBoundCallsResponse.setIvrCallLogs(inBoundCallLlistOfIvrCalls);
		inouBoundEndCount = inouBoundEndCount+1;
	}
	
	//TODO: Dummy method needs to remove
	private void addDummyOutCalls(OutBoundCallsResponse outBoundCallsResponse ){
		List<OutBoundCalls> listOfOutBoundCalls = new ArrayList<OutBoundCalls>();
		OutBoundCalls outBoundCalls = null;
		for (Integer i = 1; i <= outBountEndCount; i++) {			
			outBoundCalls = new OutBoundCalls();
			outBoundCalls.setTransId(Long.valueOf(i.longValue()));
			outBoundCalls.setDailedPhone("OUT - 12345");
			outBoundCalls.setCustomerFirstName("OUT - F.Name : "+i);
			outBoundCalls.setCustomerLastName("OUT - L.Name : "+i);
			listOfOutBoundCalls.add(outBoundCalls);
		}
		List<OutBoundCalls> OutBoundCallsList = outBoundCallsResponse.getOutBoundCallLogs();
		if(OutBoundCallsList==null){
			OutBoundCallsList = new ArrayList<OutBoundCalls>();
		}
		OutBoundCallsList.addAll(listOfOutBoundCalls);
		outBoundCallsResponse.setOutBoundCallLogs(OutBoundCallsList);
		outBountEndCount = outBountEndCount+1;
	}
	
	//TODO: Dummy method needs to remove
	private void addTransStateResponse(TransStateResponse transStateResponse){
		List<TransState> transStateList = new ArrayList<TransState>();
		TransState transState = null;
		for (int i = 1; i <= 5; i++) {
			transState = new TransState();
			transState.setState(i);
			transState.setTimestamp("Time Stamp - "+i);
			transStateList.add(transState);
		}
		List<TransState> transStates = transStateResponse.getTransStateList();
		if(transStates==null){
			transStates = new ArrayList<TransState>();
		}
		transStates.addAll(transStateList);
		transStateResponse.setTransStateList(transStates);
	}*/
 }
