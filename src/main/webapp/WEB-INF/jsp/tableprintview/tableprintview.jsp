<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Appointments &gt; Table View</title>
		<script type='text/javascript' src="static/js/validation/tableprintview/tablePrintViewFormValidation.js?version=1.0"></script>
	</head>

	<body>
	  
	<div class="dailyCal_head">
		 <div class="cal_head-new">Appointments &gt; Table View</div>
		  <div class="float-left" style="margin-left:10px">
			<input name="btnSearch" type="submit" class="btn printbtn" value="Print" onclick="window.print(); return false;" style="height: 28px; padding: 0 20px;">
		  </div> <div class="clear-all"></div>
		</div>
		  
		<form id="tablePrintViewForm">
		  <div class="search-bar">
			
			<div id="resourceError" style="font-weight: bold;" class="error"></div>
			
			<div class="float-left" style="z-index:9999;">
				${displayNames.locationName}:
				<select id="locationId" name="locationId" style="width:200px" 
					onchange="loadResourcesCheckboxes('locationId','resourceIdCcheckBoxesDiv');">
					<!--
					<c:if test="${fn:length(locations.locationList) gt 1}">
						<option value="-1" label="All ${displayNames.locationsName}"/> 
					</c:if>
					-->
					<c:forEach var="location" items="${locations.locationList}">
						<option value='${location.locationId}'> ${location.locationNameOnline}</option>
					 </c:forEach>
				</select>	
			</div>
			<div class="float-left">
				<span class="required">*</span>${displayNames.resourceName}:
				<span id="resourceIdCcheckBoxesDiv">
					<c:forEach var="resource" items="${resourceList}">
						<input type="checkbox" name="resourceIds" value="${resource.resourceId}" class="noWidth" checked>
						${resource.firstName} ${resource.lastName}&nbsp;
					</c:forEach>
				</span>
			</div>
			<div class="float-left">
				&nbsp;&nbsp;<input  type="button" class="btn" value="View Appointments" onclick="viewAppointmentsDetails();">
			</div>
			<div class="clear-all"></div>		  
		  </div>
		
		  <!--Options starts -->
		  <div class="options" >
			<div id="dataError" style="font-weight: bold;" class="error"></div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="txt-bold" width="200" valign="top">Appointment Details</td>
					<td style="text-align: center;" class="font16 txt-bold"> 
						<a href="javascript:" onClick="previousDateSelected();" style="text-decoration: none;">
							<img src="static/images/previous.png" alt="Previous" title="Previous" style="vertical-align: bottom">
						</a>  
						<span id="calendarDateDisplaySpan">
							${calendarDateDisplay}	
						</span>
						<a href="javascript:" onClick="nextDateSelected();"  style="text-decoration: none;">
							<img src="static/images/next.png" alt="Next" title="Next" style="vertical-align: bottom">
						</a> 
					</td>
					<td align="right" width="300" style="text-align: right;z-index:9999;">
						<span class="float-righ1t"> 
							<input type="text" id="jsCalenderDate" value="${date}"/> 
							<input name="btnSearch" type="button" class="btn" value="Go"  onclick="jsCalenderDateSelected();">
						</span>
					</td>
				</tr>
			</table>
			<div class="clear-all"></div>
		</div>
		<!--Options ends -->  
		<input type="hidden" name="noOfDaysToAdd" id="noOfDaysToAdd" value=""/> 
		<input type="hidden" name="selectedDate" id="selectedDate" value="${date}"/> 
		<input type="hidden" name="selectedResourceIds" id="selectedResourceIds"/>
	</form>  
		  
		  <div class="clear-all"></div><br/>
			<!-- Main tables starts -->		
			 <div id="tablePrintViewTableDiv" style="display:none;" >
				<div id="tablePrintViewTableContent">

				</div>
			</div>
			<!-- Main tables ends -->
		  </div>
		<div class="clear-all"></div>		  
		</div>		
	</body>
</html>