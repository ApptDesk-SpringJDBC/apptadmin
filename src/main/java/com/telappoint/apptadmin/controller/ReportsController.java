package com.telappoint.apptadmin.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.constants.ErrorCodesConstants;
import com.telappoint.apptadmin.constants.JspPageNameConstants;
import com.telappoint.apptadmin.form.ErrorBean;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.AppointmentReportConfig;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.DateUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReportsController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(ReportsController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	
	@RequestMapping(value="reports", method = RequestMethod.GET)
	public ModelAndView showReportsPage(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam(required=false) String reportType,
			HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_REPORTS_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			populateDisplayNames(homeBean, modelView);
			modelView.addObject("apptStatusDropDowns",apptAdminService.getAppointmentStatusDropDownList(homeBean));
			modelView.addObject("locations",apptAdminService.getAllLocationsBasicData(homeBean));
			modelView.addObject("resources",apptAdminService.getAllResourcesBasicData(homeBean));
			modelView.addObject("services",apptAdminService.getAllServicesBasicData(homeBean));
			modelView.addObject("fromDate",DateUtils.getMonthStartDate(DateUtils.DATE_FORMAT_MMDDYYYY));
			modelView.addObject("toDate",DateUtils.getMonthEndDate(DateUtils.DATE_FORMAT_MMDDYYYY));
			if(reportType==null || "".equals(reportType)){
				reportType = "APPOINTMENTS_REPORT";
			}
			modelView.addObject("reportType",reportType);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showReportsPage", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	} 
	
	//Reports - Appointment Reports related Functionality
	@RequestMapping(value="getDynamicIncludeReportsData", method =RequestMethod.GET)
	public @ResponseBody String getDynamicIncludeReportsData(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		try{
			return apptAdminService.getDynamicIncludeReportsData(homeBean);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getDynamicIncludeReportsData", request,e);
			return getErrorBaseResponseJSON("Error while retriving Dynamic Include Report Response data!");  
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "getAppointmentReport")
	public @ResponseBody String getAppointmentReport(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam String apptReportFromDate,@RequestParam String apptReportToDate,@RequestParam String apptReportLocationId,
			@RequestParam String apptReportResourceIds,@RequestParam String apptReportServiceIds,@RequestParam String apptStatus,HttpServletRequest request) {
		try{
			return apptAdminService.getAppointmentReport(homeBean, apptReportFromDate, apptReportToDate, apptReportLocationId, apptReportResourceIds, apptReportServiceIds, apptStatus);
			//return AdminUtils.getJSONDataStr(getDummy()); 
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getAppointmentReport", request,e);
			return getErrorBaseResponseJSON("Error while retriving Appointment Report data!");
		}		 
	}
	
	/*private AppointmentReportResponse getDummy(){
		AppointmentReportResponse appointmentReportResponse = new AppointmentReportResponse();
		List<AppointmentReportData> appointmentReportDataList = new ArrayList<AppointmentReportData>();
		for(Integer i=1;i<11;i++) {
			AppointmentReportData appointmentReportData = new AppointmentReportData();
			appointmentReportData.setApptDateTime("ApptDateTime - "+i);
			appointmentReportData.setWalkIn("WalkIn - "+i);
			appointmentReportData.setResourceName("ResourceName - "+i);
			appointmentReportData.setLocationName("LocationName - "+i);
			appointmentReportData.setServiceName("ServiceName - "+i);
			appointmentReportData.setSsn("SSN - "+i);
			appointmentReportData.setFirstName("FirstName - "+i);
			appointmentReportData.setLastName("LastName - "+i);
			appointmentReportData.setContactPhone("ContactPhone - "+i);
			appointmentReportData.setEmail("Email - "+i);
			appointmentReportData.setZipCode("ZipCode - "+i);
			appointmentReportData.setApptStatus((i%2==0 ? "ApptStatus - 1" : "ApptStatus - 2"));
			appointmentReportData.setApptMethod("ApptMethod - "+i);
			appointmentReportData.setAccessed("Accessed - "+i);
			appointmentReportData.setConfirmNumber(i.longValue());
			appointmentReportData.setAttrib1("Attrib1 - "+i);
			appointmentReportDataList.add(appointmentReportData);
		}
		appointmentReportResponse.setStatus(true);
		appointmentReportResponse.setAppointmentReportDataList(appointmentReportDataList);
		return appointmentReportResponse;
	}*/
	
	//Reports -  Summary Reports related Functionality
	@RequestMapping(method = RequestMethod.GET, value = "getSummaryReport")
	public @ResponseBody String getSummaryReport(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam String apptSummaryFromDate,@RequestParam String apptSummaryToDate,@RequestParam String apptSummaryReportSummary,HttpServletRequest request) {
		try{
			return apptAdminService.getSummaryReport(homeBean, apptSummaryFromDate, apptSummaryToDate, apptSummaryReportSummary);
			//return AdminUtils.getJSONDataStr(getSummaryReportResponse());
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getSummaryReport", request,e);
			return getErrorBaseResponseJSON("Error while retriving Summary Report data!"); 
		}
	}
	
	/*private SummaryReportResponse  getSummaryReportResponse(){
		SummaryReportResponse summaryReportResponse = new SummaryReportResponse();
		StatisticsReportResult locationStatReports =  new StatisticsReportResult();
		List<StatisticReport> statisticsReportList = new ArrayList<StatisticReport>();
		StatisticReport statisticReport =  null;
		for(Integer i=0;i<5;i++){
			statisticReport = new StatisticReport();
			statisticReport.setName("Location - "+i);
			statisticReport.setNoOfBookedAppts(i*2);
			statisticReport.setNoOfOtherAppts(i*3);
			statisticReport.setTotalNoOfAppts(i.longValue()*4);
			statisticsReportList.add(statisticReport);
		}
		locationStatReports.setTotalNoOfAppts(20);
		locationStatReports.setStatisticsReportList(statisticsReportList);
		
		StatisticsReportResult resourceStatReports =  new StatisticsReportResult();
		List<StatisticReport> statisticsReportList1 = new ArrayList<StatisticReport>();
		StatisticReport statisticReport1 =  null;
		for(Integer i=0;i<5;i++){
			statisticReport1 = new StatisticReport();
			statisticReport1.setName("Resource - "+i);
			statisticReport1.setNoOfBookedAppts(i*2);
			statisticReport1.setNoOfOtherAppts(i*3);
			statisticReport1.setTotalNoOfAppts(i.longValue()*4);
			statisticsReportList1.add(statisticReport1);
		}
		resourceStatReports.setTotalNoOfAppts(20);
		resourceStatReports.setStatisticsReportList(statisticsReportList1);
		
		StatisticsReportResult serviceStatReports =  new StatisticsReportResult();	
		List<StatisticReport> statisticsReportList2 = new ArrayList<StatisticReport>();
		StatisticReport statisticReport2 =  null;
		for(Integer i=0;i<5;i++){
			statisticReport2 = new StatisticReport();
			statisticReport2.setName("Service - "+i);
			statisticReport2.setNoOfBookedAppts(i*2);
			statisticReport2.setNoOfOtherAppts(i*3);
			statisticReport2.setTotalNoOfAppts(i.longValue()*4);
			statisticsReportList2.add(statisticReport2);
		}
		serviceStatReports.setTotalNoOfAppts(20);
		serviceStatReports.setStatisticsReportList(statisticsReportList2);
		
		StatisticsReportResult locationServiceStatReports = new StatisticsReportResult();
		List<StatisticReport> statisticsReportList3 = new ArrayList<StatisticReport>();
		StatisticReport statisticReport3 =  null;
		for(Integer i=0;i<5;i++){
			statisticReport3 = new StatisticReport();
			statisticReport3.setLocationName("Location - "+i);
			statisticReport3.setName("Service - "+i);
			statisticReport3.setNoOfBookedAppts(i*2);
			statisticReport3.setNoOfOtherAppts(i*3);
			statisticReport3.setTotalNoOfAppts(i.longValue()*4);
			statisticsReportList3.add(statisticReport3);
		}
		locationServiceStatReports.setTotalNoOfAppts(20);
		locationServiceStatReports.setStatisticsReportList(statisticsReportList3);
		
		summaryReportResponse.setLocationStatReports(locationStatReports);
		summaryReportResponse.setResourceStatReports(resourceStatReports);
		summaryReportResponse.setServiceStatReports(serviceStatReports);
		summaryReportResponse.setLocationServiceStatReports(locationServiceStatReports);
		
		return summaryReportResponse;
	}*/
	
	//Reports - Itemized Reports related Functionality
	@RequestMapping(method = RequestMethod.GET, value = "getSummaryStatisticsReport")
	public @ResponseBody String getSummaryStatisticsReport(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam String apptStatusTotalReportFromDate,@RequestParam String apptStatusTotalReportToDate,
			@RequestParam String apptStatusTotalReportLocationId,@RequestParam String apptStatusTotalReportServiceId,@RequestParam String apptStatusTotalReportCategory,HttpServletRequest request) {
		try{
			return apptAdminService.getSummaryStatisticsReport(homeBean, apptStatusTotalReportFromDate, apptStatusTotalReportToDate, apptStatusTotalReportLocationId, apptStatusTotalReportServiceId, apptStatusTotalReportCategory);
			//return AdminUtils.getJSONDataStr(getSummaryStatisticsReportResponse());
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getSummaryStatisticsReport", request,e);
			return getErrorBaseResponseJSON("Error while retriving Summary Statistics Report data!"); 
		}
	}
	
	@RequestMapping(value = "/getItemizedReportTemplate", method = RequestMethod.GET)
	public void getItemizedReportTemplate(@ModelAttribute("homeBean") HomeBean homeBean, 
			@RequestParam String apptStatusTotalReportFromDate,
			@RequestParam String apptStatusTotalReportToDate,
			@RequestParam String apptStatusTotalReportLocationId,
			@RequestParam String apptStatusTotalReportServiceId,
			@RequestParam String apptStatusTotalReportCategory,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String errorHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"> <body> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tr><td width=\"60%\" align=\"center\">Error while retiring PDF Report!</td> </tr> </table></body></html>";
		try {
			String reportTemplate = "";
			Map<String,Object> itemizedReportTemplateResponse =  null;
			try {
				itemizedReportTemplateResponse =  apptAdminService.getItemizedReportTemplate(homeBean, apptStatusTotalReportFromDate, apptStatusTotalReportToDate, apptStatusTotalReportLocationId, apptStatusTotalReportServiceId, apptStatusTotalReportCategory);
			}catch (Exception e) {			
				logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
				apptAdminService.sendErrorEmail(getClientCode(homeBean),"getItemizedReportTemplate", request,e);
			}
			if(itemizedReportTemplateResponse!=null && itemizedReportTemplateResponse.size()>0) {
				if(AdminUtils.getValue(itemizedReportTemplateResponse,"status", Boolean.class)){
					reportTemplate = AdminUtils.getValue(itemizedReportTemplateResponse,"reportTemplate", String.class);
					if(reportTemplate!=null && reportTemplate.length()>0) {
						//nothing to do
					} else {
						reportTemplate = errorHtml;
					}
				} else {
					reportTemplate = errorHtml;
				}
			} else {
				reportTemplate = errorHtml;
			}
			System.out.println("reportTemplate ::: "+reportTemplate);
			InputStream htmlInputStream = new ByteArrayInputStream(reportTemplate.getBytes());
			ByteArrayOutputStream output = new ByteArrayOutputStream();
		    // step 1
	        Document document = new Document();
	        // step 2	       
	        PdfWriter writer = PdfWriter.getInstance(document,output);
	        // step 3
	        document.open();
	        // step 4
	        XMLWorkerHelper.getInstance().parseXHtml(writer, document,htmlInputStream); 
	        //step 5
	        writer.flush();
	        writer.close();
	        
	        document.close();
	       
			byte[] data = output.toByteArray();
			response.setContentType("application/pdf");
	        //response.setHeader("Content-disposition", "attachment; filename=AwardLetter.pdf");
			response.setHeader("Content-Disposition", "inline; filename=AwardLetter.pdf");
	        response.setContentLength(data.length);
	        
	        response.getOutputStream().write(data);
	        response.getOutputStream().flush();
		} catch (Exception e) {			
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getItemizedReportTemplate", request,e);
		}
	}
	
	/*private SummaryReportResponse  getSummaryStatisticsReportResponse(){
		SummaryReportResponse summaryReportResponse = new SummaryReportResponse();
		List<SummaryStatisticsResult> summaryStatisticsResults = new ArrayList<SummaryStatisticsResult>();
		SummaryStatisticsResult summaryStatisticsResult =  null;
		for(Integer i=0;i<5;i++){
			summaryStatisticsResult = new SummaryStatisticsResult();
			summaryStatisticsResult.setDay("Day - "+i);
			summaryStatisticsResult.setMonth("Month - "+i);
			summaryStatisticsResult.setMonthName("Month - "+i);
			summaryStatisticsResult.setWeek("Week - "+i);
			summaryStatisticsResult.setTotalAppointments(i);
			Map<Integer,Integer> apptStatusWithApptCount = new HashMap<Integer,Integer>();
			apptStatusWithApptCount.put(51,i);
			apptStatusWithApptCount.put(52,i);
			apptStatusWithApptCount.put(11,i);
			apptStatusWithApptCount.put(41,i);
			summaryStatisticsResult.setApptStatusWithApptCount(apptStatusWithApptCount);
			summaryStatisticsResults.add(summaryStatisticsResult);			
		}		
		summaryReportResponse.setSummaryTotalNoOfAppts(25);
		summaryReportResponse.setSummaryStatisticsResults(summaryStatisticsResults);
		
		return summaryReportResponse;
	}*/
	
	//Reports - Automatic Email related Functionality
	@RequestMapping(method = RequestMethod.GET, value = "getAppointmentReportConfig")
	public @ResponseBody String getAppointmentReportConfig(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) {
		try{
			return apptAdminService.getAppointmentReportConfig(homeBean);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getDynamicIncludeReportsData", request,e);
			return getErrorBaseResponseJSON("Error while retriving Appointment Report Config data!");  
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "addAppointmentReportConfig")
	public @ResponseBody String addAppointmentReportConfig(@ModelAttribute("homeBean") HomeBean homeBean,@ModelAttribute("apptReportConfig") AppointmentReportConfig apptReportConfig,HttpServletRequest request) {
		try{
			return apptAdminService.addAppointmentReportConfig(homeBean, apptReportConfig);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"addAppointmentReportConfig", request,e);
			return getErrorBaseResponseJSON("Error while Adding Appointment Report Config data!");  
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "deleteApptReportConfigById")
	public @ResponseBody String deleteApptReportConfigById(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam String configId,HttpServletRequest request) {
		try{
			return apptAdminService.deleteApptReportConfigById(homeBean, configId);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"deleteApptReportConfigById", request,e);
			return getErrorBaseResponseJSON("Error while deleting Appointment Report Config data!");  
		}
	}
	
	//Reports - Pledge Report related Functionality
	@RequestMapping(method = RequestMethod.GET, value = "getPledgeReport")
	public @ResponseBody String getPledgeReport(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request, 
			 @RequestParam String pledgeReportFromDate,@RequestParam String pledgeReportToDate,@RequestParam String pledgeReportLocationId,  @RequestParam String pledgeReportResourceId, @RequestParam String pledgeReportFundSourceId,@RequestParam String pledgeReportRequestType) {
		try{
			return apptAdminService.getPledgeReport(homeBean, pledgeReportFromDate, pledgeReportToDate, pledgeReportLocationId, pledgeReportResourceId, pledgeReportFundSourceId, pledgeReportRequestType);
			//return AdminUtils.getJSONDataStr(getPledgeDummyData());
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getSummaryStatisticsReport", request,e);
			return getErrorBaseResponseJSON("Error while retriving Pledge Report data!"); 
		}
	}
	
	@RequestMapping(value="getDynamicPledgeResults", method =RequestMethod.GET)
	public @ResponseBody String getDynamicPledgeResult(@ModelAttribute("homeBean") HomeBean homeBean,HttpServletRequest request) throws Exception {
		try{
			return apptAdminService.getDynamicPledgeResults(homeBean);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_REPORTS_PAGE_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getDynamicPledgeResults", request,e);
			return getErrorBaseResponseJSON("Error while retriving Dynamic Pledge Report Result Response data!");  
		}
	}
	
	/*private PledgeReportResponse getPledgeDummyData(){
		Map<String, List<PledgeDetails>> pledgeReportListMap = new LinkedHashMap<String, List<PledgeDetails>>();
		PledgeDetails pledgeDetails = null;
		List<PledgeDetails> pledgeDetailsList = null;
		for(int i=1;i<11;i++){
			pledgeDetailsList = new ArrayList<PledgeDetails>();
			for(int j=0;j<(10*i);j++){
				pledgeDetails = new PledgeDetails();
				pledgeDetails.setAddress("");
				pledgeDetails.setApptDateTime("");
				pledgeDetails.setAttrib1("");
				pledgeDetails.setContactPhone("");
				pledgeDetails.setFirstName("");
				pledgeDetails.setVendorName1("V.Name 1 - "+j+" - "+i);
				pledgeDetails.setVendor1AccountNumber("V.Acc 1 - "+j+" - "+i);
				pledgeDetails.setVendor1PledgeAmount("V.Pay 1 - "+j+" - "+i);
				pledgeDetails.setVendorName2("V.Name 2 - "+j+" - "+i);
				pledgeDetails.setVendor2AccountNumber("V.Acc 2 - "+j+" - "+i);
				pledgeDetails.setVendor2PledgeAmount("V.Pay 2 - "+j+" - "+i);
				pledgeDetails.setVendorName3("V.Name 3 - "+j+" - "+i);
				pledgeDetails.setVendor3AccountNumber("V.Acc 3 - "+j+" - "+i);
				pledgeDetails.setVendor3PledgeAmount("V.Pay 3 - "+j+" - "+i);
				pledgeDetails.setLocationNameOnline("Loc - "+j+" - "+i);
				pledgeDetails.setResourceNameOnline("Res - "+j+" - "+i);
				pledgeDetails.setPledgeDatetime("P.Date - "+j+" - "+i);
				pledgeDetails.setPrimaryStatus("P.Status - "+j+" - "+i);
				pledgeDetails.setPsehelpFund("Fund - "+j+" - "+i);
				
				pledgeDetailsList.add(pledgeDetails);
			}
			pledgeReportListMap.put("Vendor - "+i,pledgeDetailsList);
		}
		PledgeReportResponse pledgeReportResponse = new PledgeReportResponse();
		pledgeReportResponse.setStatus(true);
		pledgeReportResponse.setPledgeReportData(pledgeReportListMap);
		
		return pledgeReportResponse;	       
	}*/
 }
