package com.telappoint.apptadmin.controller;

import java.util.Date;

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
import com.telappoint.apptadmin.model.LocationResponse;
import com.telappoint.apptadmin.model.TablePrintViewJSONResponse;
import com.telappoint.apptadmin.service.ApptAdminService;
import com.telappoint.apptadmin.utils.AdminUtils;
import com.telappoint.apptadmin.utils.DateUtils;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class TablePrintViewController extends ApptAdminHelperController { 
	
	private Logger logger = Logger.getLogger(TablePrintViewController.class);
	
	@Autowired
	private ApptAdminService apptAdminService;
	
	@RequestMapping(value="table-print-view", method = RequestMethod.GET)
	public ModelAndView showTablePrintView(@ModelAttribute("homeBean") HomeBean homeBean, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_SHOW_TABLE_PRINT_VIEW_PAGE;		
		ModelAndView modelView = new ModelAndView();	
		try{
			populateDisplayNames(homeBean, modelView);
			LocationResponse locationResponse = apptAdminService.getAllLocationsBasicData(homeBean);
			modelView.addObject("locations",locationResponse);
			int locationId = -1;
			if(locationResponse!=null && locationResponse.isStatus() && locationResponse.getLocationList()!=null && locationResponse.getLocationList().size()>0){
				locationId = locationResponse.getLocationList().get(0).getLocationId();
			}
			modelView.addObject("resourceList",apptAdminService.getResourceListByLocationIdForLoginUser(homeBean,String.valueOf(locationId)));			
			modelView.addObject("date",DateUtils.getStringFromDate(new Date(),DateUtils.DATE_FORMAT_MMDDYYYY));
			modelView.addObject("calendarDateDisplay",DateUtils.getStringFromDate(new Date(),DateUtils.DATE_FORMAT_EEEEMMMDDYYYY));			
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_TABLE_PRINT_VIEW_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(homeBean.getUserLoginResponse().getClientCode(),APPCODE,homeBean.getTransId(), 
					ErrorCodesConstants.ERROR_CODE_TABLE_PRINT_VIEW_SHOW.getPage(), 
					ErrorCodesConstants.ERROR_CODE_TABLE_PRINT_VIEW_SHOW.getErrorCode(), 
					ErrorCodesConstants.ERROR_CODE_TABLE_PRINT_VIEW_SHOW.getDescription());
			modelView.addObject("errorBean", errorBean);
			targetPage = JspPageNameConstants.APPTADMINDESK_ERROR_PAGE;
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"showTablePrintView", request,e);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value="getTablePrintViewResponse", method = RequestMethod.GET)
	public @ResponseBody String getTablePrintViewResponse(@ModelAttribute("homeBean") HomeBean homeBean,
			@RequestParam String locationId,@RequestParam String selectedResourceIds,@RequestParam String selectedDate,@RequestParam String noOfDaysToAdd,HttpServletRequest request) throws Exception {
		TablePrintViewJSONResponse jsonResponse = new TablePrintViewJSONResponse();
		try{
			String date = DateUtils.getStringFromDate( 
					   DateUtils.addDays(DateUtils.getDateFromString(selectedDate,DateUtils.DATE_FORMAT_MMDDYYYY),Integer.parseInt(noOfDaysToAdd)),
					   DateUtils.DATE_FORMAT_MMDDYYYY);
			String calendarDateDisplay = DateUtils.getStringFromDate(DateUtils.getDateFromString(date,DateUtils.DATE_FORMAT_MMDDYYYY),DateUtils.DATE_FORMAT_EEEEMMMDDYYYY);	
			String tablePrintViewResponse = apptAdminService.getTablePrintView(homeBean,locationId,selectedResourceIds,date);
			//String tablePrintViewResponse = AdminUtils.getJSONDataStr(getTablePrintViewResponse());
			jsonResponse.setStatus(true);
			jsonResponse.setCalendarDateDisplay(calendarDateDisplay);
			jsonResponse.setDate(date);
			jsonResponse.setTablePrintViewResponse(tablePrintViewResponse);
		}catch (Exception e) {
			logger.error(ErrorCodesConstants.ERROR_CODE_CALL_REPORT_IN_BOUND.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			apptAdminService.sendErrorEmail(getClientCode(homeBean),"getTablePrintViewResponse", request,e);
			jsonResponse.setStatus(false);
			jsonResponse.setMessage("Error while retriving Table Print View Response data!");
		}
		return AdminUtils.getJSONDataStr(jsonResponse); 
	}
	
	/*private TablePrintViewResponse getTablePrintViewResponse(){
		System.out.println(new Date());
		TablePrintViewResponse tablePrintViewResponse = new TablePrintViewResponse();
		tablePrintViewResponse.setClientName("Client Name");
		tablePrintViewResponse.setClosedDay("N");
		tablePrintViewResponse.setHoliday("N");
		tablePrintViewResponse.setLocationId(1);
		tablePrintViewResponse.setMessage("Message");
		tablePrintViewResponse.setStatus(true);
		
		List<DynamicFieldLabelData> dynamicFieldLabels = new ArrayList<DynamicFieldLabelData>();
		
		DynamicFieldLabelData dynamicFieldLabelData = new DynamicFieldLabelData();
		dynamicFieldLabelData.setDisplay("Y");
		dynamicFieldLabelData.setFieldName("firstName");
		dynamicFieldLabelData.setTitle("First Name");
		dynamicFieldLabels.add(dynamicFieldLabelData);
		
		DynamicFieldLabelData dynamicFieldLabelData1 = new DynamicFieldLabelData();
		dynamicFieldLabelData1.setDisplay("Y");
		dynamicFieldLabelData1.setFieldName("lastName");
		dynamicFieldLabelData1.setTitle("Last Name");
		dynamicFieldLabels.add(dynamicFieldLabelData1);
		
		DynamicFieldLabelData dynamicFieldLabelData2 = new DynamicFieldLabelData();
		dynamicFieldLabelData2.setDisplay("Y");
		dynamicFieldLabelData2.setFieldName("state");
		dynamicFieldLabelData2.setTitle("State");
		dynamicFieldLabels.add(dynamicFieldLabelData2);
		
		DynamicFieldLabelData dynamicFieldLabelData3 = new DynamicFieldLabelData();
		dynamicFieldLabelData3.setDisplay("Y");
		dynamicFieldLabelData3.setFieldName("zipCode");
		dynamicFieldLabelData3.setTitle("ZipCode");
		dynamicFieldLabels.add(dynamicFieldLabelData3);
		
		DynamicFieldLabelData dynamicFieldLabelData4 = new DynamicFieldLabelData();
		dynamicFieldLabelData4.setDisplay("Y");
		dynamicFieldLabelData4.setFieldName("email");
		dynamicFieldLabelData4.setTitle("Email");
		dynamicFieldLabels.add(dynamicFieldLabelData4);
		
		DynamicFieldLabelData dynamicFieldLabelData5 = new DynamicFieldLabelData();
		dynamicFieldLabelData5.setDisplay("Y");
		dynamicFieldLabelData5.setFieldName("attrib1");
		dynamicFieldLabelData5.setTitle("Energy Acc#");
		dynamicFieldLabels.add(dynamicFieldLabelData5);
		
		tablePrintViewResponse.setDynamicFieldLabels(dynamicFieldLabels);
		
		Map<BasicTablePrintData, List<TablePrintAppointmentData>> tablePrintViewMap = new LinkedHashMap<BasicTablePrintData, List<TablePrintAppointmentData>>();
    	
    	BasicTablePrintData basicTablePrintData;
        TablePrintAppointmentData tablePrintAppointmentData;
        List<TablePrintAppointmentData> tablePrintViewDataList = null;
        for (Integer j=0;j<5;j++) {        	
        	basicTablePrintData = new BasicTablePrintData();
        	basicTablePrintData.setResourceId(j.longValue());
        	basicTablePrintData.setResourceName("Resource Name : "+j);
        	basicTablePrintData.setTotalBookedAppts(j.longValue());
        	tablePrintViewDataList = new ArrayList<TablePrintAppointmentData>();
        	
        	for (int i=0;i<10;i++) {  
	        	tablePrintAppointmentData = new TablePrintAppointmentData();
	        	tablePrintAppointmentData.setTime("Time - "+i);
	        	tablePrintAppointmentData.setServiceName("Service - "+i);
	        	tablePrintAppointmentData.setNotificationStatus("Notify Status - "+i);
	        	tablePrintAppointmentData.setFirstName("F.Name - "+i);
	        	tablePrintAppointmentData.setLastName("L.Name - "+i);
	        	tablePrintAppointmentData.setState("Status - "+i);
	        	tablePrintAppointmentData.setZipCode("ZipCode - "+i);
	        	tablePrintAppointmentData.setEmail("Email - "+i);
	        	tablePrintAppointmentData.setAttrib1("Energy Acc - "+i);
	        	tablePrintViewDataList.add(tablePrintAppointmentData);
        	}
        	tablePrintViewMap.put(basicTablePrintData, tablePrintViewDataList);
        }
        tablePrintViewResponse.setTablePrintViewData(tablePrintViewMap);
        
		return tablePrintViewResponse;
	}*/
 }
