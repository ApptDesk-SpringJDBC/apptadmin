<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>${displayNames.resourcesName} &gt; Edit Hours - One Date</title>
<script type='text/javascript' src="static/js/validation/resource/resourceEditHoursOneDateFormValidation.js?version=12.4"></script>
<script type='text/javascript' src="static/js/validation/resource/scheduleSettingsValidation.js?version=1.6"></script>
</head>

<body>

<form id="updateResourceOneDateWorkingHoursForm" action="" method="post">

  <h1>${displayNames.resourcesName} &gt; Edit Hours - One Date</h1>
  <div class="search-bar">
    <dl>
      <dt><span class="required">*</span>Select ${displayNames.locationName}: </dt>
      <dd>
        <select id="locationSel" name="locationSel" class="leftFloat marginLTen paddingAll selectChangeClass"
		onchange="populateResourcesForOneDateSelectedLocation();">
           <c:if test="${fn:length(locations.locationList) gt 1}">
				<option selected="selected" value="-1" label="Select ${displayNames.locationsName}"/> 
		   </c:if>
		   <c:forEach var="location" items="${locations.locationList}">
			  <option value='${location.locationId}'> ${location.locationNameOnline}</option>
		   </c:forEach>
        </select>
        <div class="error" id="locationSelErrorDiv"></div>
      </dd>
    </dl>
    <dl>
      <dt class="clear-all"><span class="required">*</span> Date:</dt>
      <dd style="height:auto"> 
		<input type="hidden" id="moreDatesCount" name="moreDatesCount" value="0"/>
		
		<table id="moreDates">
			<tr>
				<td > 
					<input id="startDate0" name="startDate0" type="text" value="">
					<div id="addMoreDatesDiv" onclick="addMoreDates();" style="display:none;"><a href="javascript:">Add More Dates</a></div>
				</td>
			 </tr>
		</table>
        <div class="error" id="startDateError"> </div>
      </dd>
    </dl>
	
    <dl class="clear-all noWidth">
      <dt><span class="required">*</span>Change Working hours for ${displayNames.resourcesName}:</dt>
      <dd class="noHeightWidth float-left" style="height:auto" id="selResourceDiv">
		 <!--
         <c:forEach var="resource" items="${resources.resourceList}">
		    <input id="selResource" name="selResource" class="noWidth" type="checkbox" value="${resource.resourceId}" onchange="loadScheduleSettingsAfterResourceSelection();">
			<label for="selResource">${resource.resourceName}</label>
		 </c:forEach>
		 -->
      </dd>
	    <div class="clear-all"></div>
		<input type="hidden" id="resourceSelectedList" name="resourceSelectedList" value="">
		 <input type="hidden" id="prevSelResourceLen">
        <div class="error" id="resourceSelectedListError"></div>
    </dl>
    <div class="clear-all"></div>
  </div>
 
 <div id="scheduleSettingsDiv" style="display:none;">
  <div class="error" id="schdSettingsError"></div>
  
  <div class="pageSubHeader">
    <div class=" txt-bold">Schedule settings</div>
  </div>
  <div>
  
	<table id="scheduleTable" width="100%" class="main-table" style="display: table;">
	   <tbody>
		  <tr>
			 <th>Day</th>
			 <th width="24%">Start Time</th>
			 <th width="24%">End Time</th>
			 <th width="40%">Break Time</th>
		  </tr>
		  <!---------------------------- Sunday Hour details ------------------------------>
		  <tr class="altColor">
			 <td> 
				<div class="openClose slide2" id="dayOpenCloseDiv">
				   <input id="dayOpenClose" type="checkbox" name="dayOpenClose"/>
				   <label for="dayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="dayStartTimeDiv">
				   <select id="dayStartTimeHr" name="dayStartTimeHr" class="box">
					  <option value="7">7</option>
					  <option value="8">8</option>
					  <option value="9">9</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="1">1</option>
					  <option value="2">2</option>
					  <option value="3">3</option>
					  <option value="4">4</option>
					  <option value="5">5</option>
					  <option value="6">6</option>
				   </select>
				   &nbsp;
				   <select id="dayStartTimeMin" name="dayStartTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="dayStartTimeAmPm" name="dayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div id="dayEndTimeDiv">
				   <select id="dayEndTimeHr" name="dayEndTimeHr" class="box">
					  <option value="7">7</option>
					  <option value="8">8</option>
					  <option value="9">9</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="1">1</option>
					  <option value="2">2</option>
					  <option value="3">3</option>
					  <option value="4">4</option>
					  <option value="5">5</option>
					  <option value="6">6</option>
				   </select>
				   &nbsp;
				   <select id="dayEndTimeMin" name="dayEndTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="dayEndTimeAmPm" name="dayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="dayBreakTimeOpenCloseDiv">
				   <input id="dayBreakTimeOpenClose" type="checkbox"  name="dayBreakTimeOpenClose" />
				   <label for="dayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="dayBreakTimeDiv">
				   <select id="dayBreakTimeHr" name="dayBreakTimeHr" class="box">
					  <option value="7">7</option>
					  <option value="8">8</option>
					  <option value="9">9</option>
					  <option value="10">10</option>
					  <option value="11">11</option>
					  <option value="12">12</option>
					  <option value="1">1</option>
					  <option value="2">2</option>
					  <option value="3">3</option>
					  <option value="4">4</option>
					  <option value="5">5</option>
					  <option value="6">6</option>
				   </select>
				   &nbsp;
				   <select id="dayBreakTimeMin" name="dayBreakTimeMin" class="box">
					  <option value="00">00</option>
					  <option value="15">15</option>
					  <option value="30">30</option>
					  <option value="45">45</option>
				   </select>
				   &nbsp;
				   <select id="dayBreakTimeAmPm" name="dayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   &nbsp;
				   Duration
				   <select id="dayBreakDuration" name="dayBreakDuration" class="box">
					  <c:forEach var="breakTimeDuration" items="${breakTimeDurationMap}">
						<option value="${breakTimeDuration.key}">${breakTimeDuration.value}</option>
					  </c:forEach>
				   </select>
				</div>
			 </td>
		  </tr>
		  
	   </tbody>
	</table>
  </div>
   
	  <div class="clear-all"></div>
	  <div style="text-align: center;">
		<input type="button" class="btn noMinWidth" value="Reset" id="resetResourceOneDateWorkingHoursBtn">
		&nbsp;
		<input type="button" class="btn" value="Update Working Hours" id="updateResourceOneDateWorkingHoursBtn">
	  </div>
	  <div class="clear-all"></div>	 
 </div> 
	</form>

<div id="displacedApptsDetailsDiv" style="display:none;">	
	 <div class="pageSubHeader">
		<div class=" txt-bold">Displaced Appointments</div>
	 </div>
	   
	 <!-- Main tables starts -->		
	 <table id="displacedApptsDetailsTable" class="display nowrap" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>SSN</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Contact Phone</th>
					<th>Email</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>SSN</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Contact Phone</th>
					<th>Email</th>
				</tr>
			</tfoot>
			</table>
	 <!-- Main tables ends -->
	  
	  <div class="clear-all"></div>
	  <div style="text-align: center;">
	   <input type="button" class="btn" value="Cancel" id="displacedApptsCancelResourceOneDateWorkingHoursBtn"> 
		&nbsp;
		<input type="button" class="btn noMinWidth" value="Proceed" id="displacedApptsProceedResourceOneDateWorkingHoursBtn">
	  </div>
	  <div class="clear-all"></div>	 
 </div> 
 
 <br/>
	<!--Options starts -->
    <div class="options">
      <div class="txt-bold">Specific Date Edits</div>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
    
    <!-- Main tables starts -->
    <table id="specificDatesTable" class="display nowrap" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th width="12%">UserName</th>
				<th width="12%">Timestamp</th>
				<th width="12%">${displayNames.locationName}</th>
				<th width="15%">${displayNames.resourceName}</th>
				<th width="12%">Date</th> 
				<th width="15%">Working Hours</th>
				<th width="15%">Break Time</th> 
			 </tr>
		</thead>
	    <tfoot>
			<tr>
				<th width="12%">UserName</th>
				<th width="12%">Timestamp</th>
				<th width="12%">${displayNames.locationName}</th>
				<th width="15%">${displayNames.resourceName}</th>
				<th width="12%">Date</th> 
				<th width="15%">Working Hours</th>
				<th width="15%">Break Time</th> 
			 </tr>
		</tfoot>
    </table>
    <!-- Main tables ends -->
 
<!-- Body ends --> 
</body>
</html>