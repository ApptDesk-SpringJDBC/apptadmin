<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Reports</title>
		<script type='text/javascript' src="static/js/validation/reports/reportsFormValidation.js?version=3."></script>
	</head>

	<body>
	   <h1>Reports</h1>

	   <div class="options">
		  Report Type
		  <select id="reportType" style="width:200px">				
			<option value="APPOINTMENTS_REPORT" ${reportType=='APPOINTMENTS_REPORT' ? 'selected=selected' : ''}> Appointments Report </option>
			<option value="AUTOMATIC_EMAIL_REPORT" ${reportType=='AUTOMATIC_EMAIL_REPORT' ? 'selected=selected' : ''}> Automatic Email Report </option>
			<option value="APPOINTMENTS_SUMMARY" ${reportType=='APPOINTMENTS_SUMMARY' ? 'selected=selected' : ''}> Appointments Summary </option>
			<option value="APPOINTMENT_STATUS_TOTAL" ${reportType=='APPOINTMENT_STATUS_TOTAL' ? 'selected=selected' : ''}> Appointment Status Total </option>
			<option value="PLEDGE_REPORT" ${reportType=='PLEDGE_REPORT' ? 'selected=selected' : ''}> Pledge Report </option> 
		  </select>
	   </div>	   
	   <br/>
	   
<!----------------------  Appointment Report Related HTML - Starts --------------------->

	<div id="apptReportDetails" class="reportsClass">
	
		  <div class="search-bar">
			<div id="apptReportFromDateError" style="font-weight: bold;" class="error"></div>
			<div id="apptReportToDateError" style="font-weight: bold;" class="error"></div>
			<div id="apptReportLocationError" style="font-weight: bold;" class="error"></div>
			<div id="apptReportResourceError" style="font-weight: bold;" class="error"></div>
			<div id="apptReportServiceError" style="font-weight: bold;" class="error"></div>
			<div id="apptReportApptStatusError" style="font-weight: bold;" class="error"></div>
			
		   <form id="apptReportForm">
			<dl>			
				<dt>
					<span class="required">*</span>Appointment From Date:
				</dt>
				<dd>
					<input name="apptReportFromDate" id="apptReportFromDate"  value="${fromDate}"/>					
				</dd>
			</dl>
			<dl>
				<dt>
					<span class="required">*</span>To Date:
				</dt>
				<dd>
					<input name="apptReportToDate" id="apptReportToDate" value="${toDate}"/>
					
				</dd>
			</dl>
			<dl>
				<dt>${displayNames.locationName}:</dt>
				<dd id="location_DDId">
					<select id="apptReportLocationId" name="apptReportLocationId" style="width:200px" 
						onchange="populateResourcesForSelectedLocation('apptReportLocationId','apptReportResourceIds');">
						<c:if test="${fn:length(locations.locationList) gt 1}">
							<option value="-1" label="All ${displayNames.locationsName}"/> 
						</c:if>
						<c:forEach var="location" items="${locations.locationList}">
							<option value='${location.locationId}'> ${location.locationNameOnline}</option>
						 </c:forEach>
					</select>					
				</dd>
			</dl>
			<dl class="clear-all">
				<dt>${displayNames.resourceName}:</dt>
				<dd class="noHeight">	
					<div id="resource_DivId" style="width:900px;">
						<select id="apptReportResourceIds" name="apptReportResourceIds" 
							onchange="populateServicessForSelectedResource('apptReportResourceIds','apptReportServiceIds');"
							size="${fn:length(resources.resourceList) gt 8 ? (fn:length(resources.resourceList)/2) : 5}" 
							style='height:160px;' multiple>
							<c:if test="${fn:length(resources.resourceList) gt 1}">
								<option value="-1" selected="selected" label="All ${displayNames.resourcesName}"/> 
							</c:if>
							<c:forEach var="resource" items="${resources.resourceList}">
								<option value='${resource.resourceId}'> ${resource.resourceName}</option>
							 </c:forEach>
						</select>						
					</div>
				</dd>
			</dl>
			<dl>
				<dt>${displayNames.serviceName}:</dt>
				<dd class="noHeight">	
					<div id="service_DivId" style="width:900px;">
						<select id="apptReportServiceIds" name="apptReportServiceIds"
							size="${fn:length(services.serviceList) gt 8 ? (fn:length(services.serviceList)/2) : 5}" 
							style='height:160px;' multiple>
							<c:if test="${fn:length(services.serviceList) gt 1}">
								<option value="-1" selected="selected" label="All ${displayNames.servicesName}"/> 
							</c:if>
							<c:forEach var="service" items="${services.serviceList}">
								<option value='${service.serviceId}'> ${service.serviceNameOnline}</option>
							 </c:forEach>
						</select>
					</div>
				</dd>
			</dl>
			<dl>
				<dt>
					<span class="required">*</span>Appointment Status:
				</dt>
				<dd class="noHeight">					
					<!-- Please donot change check box values, If we want to change we need to updated in REST WS Query..!
						 If we want we can change labels 
						 Appointment Status values in Backed
						 -----------------------------------
						 No Show = 31  -- please dont chage this value we are using this value in backend as hard coded
						 CONFIRM = 11
						 CANCEL = 21
						 DISPLACED = 32
					-->
					<div style="width:900px;">
						<select id="apptReportOtherApptStatus" name="apptReportOtherApptStatus"
							size="${fn:length(apptStatusDropDowns.appointmentStatusList) gt 8 ? (fn:length(apptStatusDropDowns.appointmentStatusList)/2) : 5}" 
							style='height:160px;' multiple>
							<c:forEach var="appointmentStatus" items="${apptStatusDropDowns.appointmentStatusList}">
								<option value='${appointmentStatus.statusVal}'> ${appointmentStatus.status}</option>
							 </c:forEach>
						</select>					
					</div>				
					<input type="checkbox" name="apptReportApptStatus" value="11" class="noWidth" checked="checked">Confirmed<br>
					<input type="checkbox" name="apptReportApptStatus" value="21" class="noWidth">Canceled<br>
					<input type="checkbox" name="apptReportApptStatus" value="24" class="noWidth">Displaced 
					
					<input type="hidden" id="apptStatus" name="apptStatus" value="">					
				</dd>
			</dl>
			<div class="clear-all"></div><br/>
			<div class="float-right">
				<input id="apptReportResetBtn" type="reset" class="btn" value=" Reset ">
				<input id="apptReportRunReportBtn" type="button" class="btn" value="Run Report">
			</div>
			<div class="clear-all"></div>
			<span ><b style="color:red;">Note : </b><i>Multi Select Combo Box: Press & Hold CTRL key while clicking items</i></span>
		  </form>
		  </div>
		  
		  <div class="clear-all"></div><br/>
			<!-- Main tables starts -->		
			 <div id="apptReportTableDiv" style="display:none;" >
				<div id="apptReportTableContent">

				</div>
			</div>
			<!-- Main tables ends -->
		  </div>
		<div class="clear-all"></div>		  
		</div>
<!----------------------  Appointment Report Related HTML - Ends --------------------->

<!----------------------  Automatic Email Report Related HTML - Starts --------------------->
	<div id="automaticEmailReportDetails" class="reportsClass" style="display:none">
			
		  <div class="search-bar">
		    <div id="automaticEmailReportNameError" style="font-weight: bold;" class="error"></div>
			<div id="automaticEmailReportEmail1Error" style="font-weight: bold;" class="error"></div>
			<div id="automaticEmailReportEmail2Error" style="font-weight: bold;" class="error"></div>
			<div id="automaticEmailReportEmail3Error" style="font-weight: bold;" class="error"></div>
			<div id="automaticEmailReportLocationError" style="font-weight: bold;" class="error"></div>
			<div id="automaticEmailReportResourceError" style="font-weight: bold;" class="error"></div>
			<div id="automaticEmailReportServiceError" style="font-weight: bold;" class="error"></div>
			<div id="automaticEmailReportApptStatusError" style="font-weight: bold;" class="error"></div>
			
		   <form id="automaticEmailReportForm">
			<dl> 		
				<dt><span class="required">*</span>Report Name:	</dt>
				<dd>
					<input name="reportName" id="reportName" />					
				</dd>
			</dl>
			<dl>
				<dt><span class="required">*</span>Email 1:</dt>
				<dd>
					<input name="email1" id="email1" />					
				</dd>
			</dl>			
			<dl> 		
				<dt> Email 2:	</dt>
				<dd>
					<input name="email2" id="email2" />					
				</dd>
			</dl>
			<dl class="clear-all">
				<dt> Email 3:</dt>
				<dd>
					<input name="email3" id="email3" />
					
				</dd>
			</dl>
			<dl>
				<dt> How many days report: </dt>
				<dd>
					<select name="reportNoDays" id="reportNoDays">
						<option value="-10">Previous Day +9</option>
						<option value="-9">Previous Day +8</option>
						<option value="-8">Previous Day +7</option>
						<option value="-7">Previous Day +6</option>
						<option value="-6">Previous Day +5</option>
						<option value="-5">Previous Day +4</option>
						<option value="-4">Previous Day +3</option>
						<option value="-3">Previous Day +2</option>
						<option value="-2">Previous Day +1</option>
						<option value="-1">Previous Day</option>
						
						<option value="0">Today</option>

						<option value="1">Tomorrow</option>
						<option value="2">Tomorrow +1</option>
						<option value="3">Tomorrow +2</option>
						<option value="4">Tomorrow +3</option>
						<option value="5">Tomorrow +4</option>
						<option value="6">Tomorrow +5</option>
						<option value="7">Tomorrow +6</option>
						<option value="8">Tomorrow +7</option>
						<option value="9">Tomorrow +8</option>
						<option value="10">Tomorrow +9</option>
					 </select>
				</dd>
			</dl>			
			<dl>
				<dt> Time Of Report: </dt>
				<dd>
					<select name="timeOfReport" id="timeOfReport">
						<option value="23:00:00">11:00 PM</option>
						<option value="23:30:00">11:30 PM</option>
						<option value="00:00:00">00:00 AM</option>
						<option value="00:30:00">00:30 AM</option>
						<option value="01:00:00">01:00 AM</option>
						<option value="01:30:00">01:30 AM</option>
						<option value="02:00:00">02:00 AM</option>
						<option value="02:30:00">02:30 AM</option>
						<option value="03:00:00">03:00 AM</option>
						<option value="03:30:00">03:30 AM</option>
						<option value="04:00:00">04:00 AM</option>
						<option value="04:30:00">04:30 AM</option>
						<option value="05:00:00">05:00 AM</option>
						<option value="05:30:00">05:30 AM</option>
						<option value="06:00:00">06:00 AM</option>
						<option value="06:30:00">06:30 AM</option>
						<option value="07:00:00">07:00 AM</option>
						<option value="07:30:00">07:30 AM</option>
						<option value="08:00:00">08:00 AM</option>
						<option value="08:30:00">08:30 AM</option>
						<option value="09:00:00">09:00 AM</option>
						<option value="09:30:00">09:30 AM</option>
						<option value="10:00:00">10:00 AM</option>
						<option value="10:30:00">10:30 AM</option>
						<option value="11:00:00">11:00 AM</option>
						<!--
						<option value="11:30:00">11:30 AM</option>
						<option value="12:00:00">12:00 AM</option>
						<option value="12:30:00">12:30 PM</option>
						<option value="13:00:00">01:00 PM</option>
						<option value="14:00:00">02:00 PM</option>
						<option value="14:30:00">02:30 PM</option>
						<option value="15:00:00">03:00 PM</option>
						<option value="15:30:00">03:30 pM</option>
						<option value="16:00:00">04:00 PM</option>
						<option value="16:30:00">04:30 PM</option>
						<option value="17:00:00">05:00 PM</option>
						-->
					 </select>
				</dd>
			</dl>
			<dl>
				<dt> Select Report File Format: </dt>
				<dd><select name="fileFormat" id="fileFormat">
				 	<option value="PDF">PDF</option>
				 	<option value="CSV">CSV</option>
				 </select>
			</dl>
			<dl>
				<dt>${displayNames.locationName}:</dt>
				<dd id="location_DDId">
					<select id="locationIds" name="locationIds" style="width:200px" 
						onchange="populateResourcesForSelectedLocation('automaticEmailReportForm select[name=locationIds]','resourceIds');">
						<c:if test="${fn:length(locations.locationList) gt 1}">
							<option value="-1" label="All ${displayNames.locationsName}" selected="selected"/> 
						</c:if>
						<c:forEach var="location" items="${locations.locationList}">
							<option value='${location.locationId}'> ${location.locationNameOnline}</option>
						 </c:forEach>
					</select>					
				</dd>
			</dl>
			<dl class="clear-all">
				<dt>${displayNames.resourceName}:</dt>
				<dd class="noHeight">	
					<div id="automaticEmailReport_resource_DivId" style="width:900px;">
						<select id="resourceIds" name="resourceIds" 
							onchange="populateServicessForSelectedResource('automaticEmailReportForm select[name=resourceIds]','serviceIds');"
							size="${fn:length(resources.resourceList) gt 8 ? (fn:length(resources.resourceList)/2) : 5}" 
							style='height:160px;' multiple>
							<c:if test="${fn:length(resources.resourceList) gt 1}">
								<option value="-1" label="All ${displayNames.resourcesName}" selected="selected"/> 
							</c:if>
							<c:forEach var="resource" items="${resources.resourceList}">
								<option value='${resource.resourceId}'> ${resource.resourceName}</option>
							 </c:forEach>
						</select>
					</div>
				</dd>
			</dl>
			<dl>
				<dt>${displayNames.serviceName}:</dt>
				<dd class="noHeight">	
					<div id="automaticEmailReport_service_DivId" style="width:900px;">
						<select id="serviceIds" name="serviceIds"
							size="${fn:length(services.serviceList) gt 8 ? (fn:length(services.serviceList)/2) : 5}" 
							style='height:160px;' multiple>
							<c:if test="${fn:length(services.serviceList) gt 1}">
								<option value="-1" label="All ${displayNames.servicesName}"  selected="selected"/> 
							</c:if>
							<c:forEach var="service" items="${services.serviceList}">
								<option value='${service.serviceId}'> ${service.serviceNameOnline}</option>
							 </c:forEach>
						</select>
					</div>
				</dd>
			</dl>
			<dl>
				<dt>
					<span class="required">*</span>Appointment Status:
				</dt>
				<dd class="noHeight">					
					<!-- Please donot change check box values, If we want to change we need to updated in REST WS Query..!
						 If we want we can change labels 
						 Appointment Status values in Backed
						 -----------------------------------
						 No Show = 31  -- please dont chage this value we are using this value in backend as hard coded
						 CONFIRM = 11
						 CANCEL = 21
						 DISPLACED = 32
					-->
					<div style="width:900px;">
						<select id="automaticEmailReportOtherApptStatus" name="automaticEmailReportOtherApptStatus"
							size="${fn:length(apptStatusDropDowns.appointmentStatusList) gt 8 ? (fn:length(apptStatusDropDowns.appointmentStatusList)/2) : 5}" 
							style='height:160px;' multiple>
							<c:forEach var="appointmentStatus" items="${apptStatusDropDowns.appointmentStatusList}">
								<option value='${appointmentStatus.statusVal}'> ${appointmentStatus.status}</option>
							 </c:forEach>
						</select>					
					</div>				
					<input type="checkbox" name="automaticEmailReportApptStatus" value="11" class="noWidth" checked >Confirmed<br>
					<input type="checkbox" name="automaticEmailReportApptStatus" value="21" class="noWidth">Canceled<br>
					<input type="checkbox" name="automaticEmailReportApptStatus" value="24" class="noWidth">Displaced 
				</dd>
				
				<input type="hidden" id="apptStatusFetch" name="apptStatusFetch" value="">	
				
			</dl>
			<div class="clear-all"></div><br/>
			<div class="float-right">
				<input id="automaticEmailReportResetBtn" type="reset" class="btn" value=" Reset ">
				<input id="automaticEmailReportConfigBtn" type="button" class="btn" value="Configure Automatic Report">
			</div>
			<div class="clear-all"></div>
			<span ><b style="color:red;">Note : </b><i>Multi Select Combo Box: Press & Hold CTRL key while clicking items</i></span>
		  </form>
		  </div>
		  
		  <div class="clear-all"></div><br/>
			<!-- Main tables starts -->		
			 <div id="automaticEmailTableDiv" style="display:none;">
				<table id="automaticEmailTable" class="display nowrap" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>User Name</th>
							<th>Timestamp</th>
							<th>Report Name</th>
							<th>Email 1</th>
							<th>Email 2</th>
							<th>Email 3</th>
							<th>Delete</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th>User Name</th>
							<th>Timestamp</th>
							<th>Report Name</th>
							<th>Email 1</th>
							<th>Email 2</th>
							<th>Email 3</th>
							<th>Delete</th>
						</tr>
					</tfoot>
				</table>
			</div>
			<!-- Main tables ends -->
		  </div>
		<div class="clear-all"></div>		  
		</div>
<!----------------------  Automatic Email Report Related HTML - Ends --------------------->

<!----------------------  Appointments Summary Report Related HTML - Starts --------------------->
	<div id="apptSummaryReportDetails" class="reportsClass" style="display:none">
	
		  <div class="search-bar">
			<div id="apptSummaryFromDateError" style="font-weight: bold;"  class="error"></div>			
			<div id="apptSummaryToDateError" style="font-weight: bold;"  class="error"></div>
		   
		   <form id="apptSummaryReportForm">
			<dl>			
				<dt>
					<span class="required">*</span>Appointment From Date:
				</dt>
				<dd>
					<input name="apptSummaryFromDate" id="apptSummaryFromDate" value="${fromDate}"/>
				</dd>
			</dl>
			<dl>
				<dt>
					<span class="required">*</span>To Date:
				</dt>
				<dd>
					<input name="apptSummaryToDate" id="apptSummaryToDate" value="${toDate}"/>
				</dd>
			</dl>			
			<dl>
				<dt>
					<span class="required">*</span>Report Summary:
				</dt>
				<dd>
					<input type="radio" name="apptSummaryReportSummary" value="D" class="noWidth" checked="checked">Daily
					<input type="radio" name="apptSummaryReportSummary" value="W" class="noWidth">Weekly 
					<input type="radio" name="apptSummaryReportSummary" value="M" class="noWidth">Monthly 					  
				</dd>
			</dl>
			<div class="clear-all"></div><br/>
			<div class="float-right">
				<input id="apptSummaryRunReportBtn" type="button" class="btn" value="Run Report">
			</div>
			<div class="clear-all"></div>
		  </form>
		  </div>
		  
		  <div class="clear-all"></div><br/>
			<!-- Main tables starts -->		
			 <div id="apptSummaryTableDiv" style="display:none;">
			 
				<!-- Procedure Related Summary Report -->
				<div id="apptSummaryProcedureTableDiv" style="display:none;">
					<!--Options starts -->
					<div class="options">
						<span class="txt-bold">Appointment Report Itemized on  ${displayNames.proceduresName} </span>
						<span class="float-right" >
							<b>Total  Appointments : <span id="procedureTotalNoOfAppts"></span> </b>
						</span>
					</div>
					<div class="clear-all"></div><br/>
					<!--Options ends -->
					<br/>
					<table id="apptSummaryProcedureTable" class="display nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>${displayNames.procedureName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>${displayNames.procedureName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</tfoot>
					</table>
					<br/>
				</div>
				
				<!-- Location Related Summary Report -->
				<div id="apptSummaryLocationTableDiv" style="display:none;">
					<!--Options starts -->
					<div class="options">
						<span class="txt-bold">Appointment Report Itemized on  ${displayNames.locationsName} </span>
						 <span class="float-right" >
							<b>Total  Appointments : <span id="locationTotalNoOfAppts"></span> </b>
						</span>
					</div>
					<div class="clear-all"></div><br/>
					<!--Options ends -->
					<table id="apptSummaryLocationTable" class="display nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>${displayNames.locationName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>${displayNames.locationName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</tfoot>
					</table>
					<br/>
				</div>
				
				<!-- Resource Related Summary Report -->
				<div id="apptSummaryResourceTableDiv" style="display:none;">
					<!--Options starts -->
					<div class="options">
						<span class="txt-bold">Appointment Report Itemized on  ${displayNames.resourcesName} </span>
						 <span class="float-right" >
							<b>Total  Appointments : <span id="resourceTotalNoOfAppts"></span> </b>
						</span>
					</div>
					<div class="clear-all"></div><br/>
					
					<!--Options ends -->
					<table id="apptSummaryResourceTable" class="display nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>${displayNames.resourceName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>${displayNames.resourceName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</tfoot>
					</table>
					<br/>
				</div>
								
				<!-- Service Related Summary Report -->
				<div id="apptSummaryServiceTableDiv" style="display:none;">
					<!--Options starts -->
					<div class="options">
						<span class="txt-bold">Appointment Report Itemized on  ${displayNames.servicesName} </span>
						 <span class="float-right" >
							<b>Total  Appointments : <span id="serviceTotalNoOfAppts"></span> </b>
						</span>						
					</div>
					<div class="clear-all"></div><br/>
					<!--Options ends -->
					<table id="apptSummaryServiceTable" class="display nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>${displayNames.serviceName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>${displayNames.serviceName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</tfoot>
					</table>
					<br/>
				</div>
				
				<!-- Location  and Service Related Summary Report -->
				<div id="apptSummaryLocationAndServiceTableDiv" style="display:none;">
					<!--Options starts -->
					<div class="options">
						<span class="txt-bold">Appointment Report Itemized on  ${displayNames.locationsName} and ${displayNames.servicesName} </span>
						 <span class="float-right" >
							<b>Total  Appointments : <span id="locationAndServiceTotalNoOfAppts"></span> </b>
						</span>						
					</div>
					<div class="clear-all"></div><br/>
					<!--Options ends -->
					<table id="apptSummaryLocationAndServiceTable" class="display nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>${displayNames.locationName}</th>
								<th>${displayNames.serviceName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>${displayNames.locationName}</th>
								<th>${displayNames.serviceName}</th>
								<th>Confirmed  Appointments</th>
								<th>Other  Appointments</th>
								<th>Total  Appointments</th>
							</tr>
						</tfoot>
					</table>
				</div>
				
			</div>
			<!-- Main tables ends -->
		  </div>
		<div class="clear-all"></div>		  
		</div>
<!----------------------  Appointments Summary  Report Related HTML - Ends --------------------->

<!----------------------  Appointment Status Total Repor Related HTML - Starts --------------------->
	<div id="apptStatusTotalReportDetails" class="reportsClass" style="display:none">
	
		  <div class="search-bar">
			<div id="apptStatusTotalReportFromDateError" style="font-weight: bold;" class="error"></div>
			<div id="apptStatusTotalReportToDateError" style="font-weight: bold;" class="error"></div>
			<div id="apptStatusTotalReportLocationError" style="font-weight: bold;" class="error"></div>
			<div id="apptStatusTotalReportServiceError" style="font-weight: bold;" class="error"></div>
		   
		   <form id="apptStatusTotalReportForm">
		   <dl>
				<dt>
					<span class="required">*</span>Report Type:
				</dt>
				<dd>
					<select id="apptStatusTotalReportCategory" name="apptStatusTotalReportCategory" style="width:200px">				
						<option value="D"> Daily </option>
						<option value="W"> Weekly </option>
						<option value="M"> Monthly </option>
						<option value="Q"> Quaterly </option>
						<option value="Y"> Yearly </option> 
					</select>
				</dd> 
			</dl>		    
			<dl>			
				<dt>
					<span class="required">*</span>Appointment From Date:
				</dt>
				<dd>
					<input name="apptStatusTotalReportFromDate" id="apptStatusTotalReportFromDate" value="${fromDate}"/>
				</dd>
			</dl>
			<dl>
				<dt>
					<span class="required">*</span>To Date:
				</dt>
				<dd>
					<input name="apptStatusTotalReportToDate" id="apptStatusTotalReportToDate" value="${toDate}"/>
				</dd>
			</dl>
			<dl class="clear-all">
				<dt>${displayNames.locationName}:</dt>
				<dd >
					<select id="apptStatusTotalReportLocationId" name="apptStatusTotalReportLocationId" style="width:200px" 
						onchange="populateServicesForSelectedLocation('apptStatusTotalReportLocationId','apptStatusTotalReportServiceId');">
						<c:if test="${fn:length(locations.locationList) gt 1}">
							<option value="-1" label="All ${displayNames.locationsName}" selected="selected"/> 
						</c:if>
						<c:forEach var="location" items="${locations.locationList}">
							<option value='${location.locationId}'> ${location.locationNameOnline}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>${displayNames.serviceName}:</dt>
				<dd class="noHeight">	
					<select id="apptStatusTotalReportServiceId" name="apptStatusTotalReportServiceId" style="width:200px" >
						<c:if test="${fn:length(services.serviceList) gt 1}">
							<option value="-1" label="All ${displayNames.servicesName}" selected="selected"/> 
						</c:if>
						<c:forEach var="service" items="${services.serviceList}">
							<option value='${service.serviceId}'> ${service.serviceNameOnline}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<div class="clear-all"></div>
			<div class="float-right">
				<input id="apptStatusTotalReportRunBtn" type="button" class="btn" value="Run Report">
			</div>
			<div class="clear-all"></div>
		  </form>
		  </div>
		  
		  <div class="clear-all"></div><br/>
			<!-- Main tables starts -->		
			 <div id="apptStatusTotalTableDiv" style="display:none;">
				<!--Options starts -->
				<div class="options">
					<span class="txt-bold">Itemized Report Summary</span>
					 <span class="float-right" > 					 
						<input id="apptStatusTotalReportViewPDFReportBtn" type="button" class="btn" value="View PDF Report">
					</span>						
				</div>
				<div class="clear-all"></div><br/>
				<!--Options ends -->
				<div id="apptStatusTotalTableContent">
					
				</div>
			</div>
			<!-- Main tables ends -->
		  </div>
		<div class="clear-all"></div>		  
		</div>
<!----------------------  Appointments Summary Report Related HTML - Ends --------------------->

<!----------------------  Pledge Report Related HTML - Starts --------------------->
	<div id="pledgeReportDetails" class="reportsClass" style="display:none">
		
		  <div class="search-bar">			
			<div id="pledgeReportFromDateError" style="font-weight: bold;" class="error"></div>
			<div id="pledgeReportToDateError" style="font-weight: bold;" class="error"></div>
			<div id="pledgeReportLocationError" style="font-weight: bold;" class="error"></div>
			<div id="pledgeReportResourceError" style="font-weight: bold;" class="error"></div>
			<div id="pledgeReportFundSourceError" style="font-weight: bold;" class="error"></div>
			
		   <form id="pledgeReportForm">
			<dl>			
				<dt><span class="required">*</span>Pledge Date From:</dt>
				<dd>
					<input name="pledgeReportFromDate" id="pledgeReportFromDate" value="${fromDate}"/>
				</dd>
			</dl>
			<dl>
				<dt><span class="required">*</span>To:</dt>
				<dd>
					<input name="pledgeReportToDate" id="pledgeReportToDate" value="${toDate}"/>
				</dd>
			</dl>
			<dl  class="clear-all">
				<dt>${displayNames.locationName}:</dt>
				<dd id="location_DDId">
					<select id="pledgeReportLocationId" name="pledgeReportLocationId" style="width:200px" 
						onchange="populateResourcesForSelectedLocation('pledgeReportLocationId','pledgeReportResourceId');">
						<c:if test="${fn:length(locations.locationList) gt 1}">
							<option value="0" label="All ${displayNames.locationsName}" selected="selected"/> 
						</c:if>
						<c:forEach var="location" items="${locations.locationList}">
							<option value='${location.locationId}'> ${location.locationNameOnline}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>${displayNames.resourceName}:</dt>
				<dd class="noHeight">	
					<div id="pledgeReport_resource_DivId" style="width:900px;">
						<select id="pledgeReportResourceId" name="pledgeReportResourceId" style="width:200px" >
							<c:if test="${fn:length(resources.resourceList) gt 1}">
								<option value="0" label="All ${displayNames.resourcesName}"/> 
							</c:if>
							<c:forEach var="resource" items="${resources.resourceList}">
								<option value='${resource.resourceId}'> ${resource.resourceName}</option>
							 </c:forEach>
						</select>
					</div>
				</dd>
			</dl>
			<dl>
				<dt>Fund Source :</dt>
				<dd>
					<select id="pledgeReportFundSourceId" name="pledgeReportFundSourceId" style="width:200px" >
						<c:if test="${fn:length(resources.resourceList) gt 1}">
							<option value="0" label="All Fund Sources" selected="selected"/> 
						</c:if>
						<c:forEach var="resource" items="${resources.resourceList}">
							<option value='${resource.resourceId}'> ${resource.resourceName}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<div class="clear-all"></div>
			<dl style="width: auto">
				<dt>
					&nbsp;
				</dt>				
				<dd class="noHeightWidth">	
					<input type="radio" name="pledgeReportRequestType" value="Complete" class="noWidth" checked="checked">Complete
					<input type="radio" name="pledgeReportRequestType" value="Group_By_Resource" class="noWidth">Group by ${displayNames.resourceName}
					<input type="radio" name="pledgeReportRequestType" value="Group_By_Fund_Source" class="noWidth">Group by Fund Source 	
					<input type="radio" name="pledgeReportRequestType" value="Group_By_Vendor" class="noWidth">Group by Vendor
				</dd>
			</dl>
			<div class="clear-all"></div>
			<div class="float-right">
				<input id="pledgeReportRunBtn" type="button" class="btn" value="Run Report">
			</div>
			<div class="clear-all"></div>
		  </form>
		  </div>
		  
		  <div class="clear-all"></div><br/>
			<!-- Main tables starts -->		
			 <div id="pledgeTableDiv" style="display:none;">
				<div id="pledgeTableContent">
				</div>
			</div>
			<!-- Main tables ends -->
		  </div>
		<div class="clear-all"></div>		  
		</div>
<!----------------------  Pledge Report Related HTML - Ends --------------------->

	</body>
</html>