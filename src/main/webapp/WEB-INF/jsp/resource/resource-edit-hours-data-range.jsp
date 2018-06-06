<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>${displayNames.resourcesName} &gt; Edit Hours - Date Range</title>
<script type='text/javascript' src="static/js/validation/resource/resourceEditHoursDateRangeFormValidation.js?version=5.2"></script>
<script type='text/javascript' src="static/js/validation/resource/scheduleSettingsValidation.js?version=1.6"></script>
</head>

<body>

<form id="updateResourceDateRange0WorkingHoursForm" action="" method="post">

  <h1>${displayNames.resourcesName} &gt; Edit Hours - Date Range</h1>
  <div class="search-bar">
    <dl>
      <dt><span class="required">*</span>Select ${displayNames.locationName}: </dt>
      <dd>
        <select id="locationSel" name="locationSel" class="leftFloat marginLTen paddingAll selectChangeClass"
		onchange="populateResourcesForSelectedLocation();">
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
      <dt class="clear-all"><span class="required">*</span> Start Date:</dt>
      <dd> <span style="position: relative;">
        <input id="startDate" name="startDate" type="text" value="">
        </span>
        <div class="error" id="startDateError"> </div>
      </dd>
    </dl>
     
    <dl class="noWidth">
      <dt><span class="required">*</span>End Date:</dt>
      <dd class="noHeight noWidth" style="height:auto; width:auto">
        <input id="rdoEndDate1" name="rdoEndDate" class="noWidth noHeight" type="radio" value="Forever">
        Forever
        <input id="rdoEndDate2" name="rdoEndDate" class="noWidth noHeight" type="radio" value="EndDate" checked="checked">
        End Date <span style="position: relative;">
        <input id="endDate" name="endDate" type="text" value="">
        </span> <br>
        <div class="error" id="endDateError">End date should be greater than or equal to 7 days from Start date.</div>
      </dd>
    </dl>
	
    <dl class="clear-all noWidth">
      <dt><span class="required">*</span>Change Working hours for ${displayNames.resourcesName}:</dt>
      <dd class="noHeightWidth float-left" style="height:auto" id="selResourceDiv">
		<!--
         <c:forEach var="resource" items="${resources.resourceList}">
		    <input id="selResource" name="selResource" class="noWidth" type="checkbox" value="${resource.resourceId}">
			<label for="selResource">${resource.resourceName}</label>
		 </c:forEach>
		 -->
      </dd>
	    <div class="clear-all"></div>
		<input type="hidden" name="resourceSelectedList" value="">
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
			 <th>Days</th>
			 <th width="24%">Start Time</th>
			 <th width="24%">End Time</th>
			 <th width="40%">Break Time</th>
		  </tr>
		  <!---------------------------- Sunday Hour details ------------------------------>
		  <tr class="altColor">
			 <td>
				<i>Sunday</i><br>
				<div class="openClose slide2" id="sunDayOpenCloseDiv">
				   <input id="sunDayOpenClose" type="checkbox" name="sunDayOpenClose"/>
				   <label for="sunDayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="sunDayStartTimeDiv">
				   <select id="sunDayStartTimeHr" name="sunDayStartTimeHr" class="box">
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
				   <select id="sunDayStartTimeMin" name="sunDayStartTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="sunDayStartTimeAmPm" name="sunDayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div id="sunDayEndTimeDiv">
				   <select id="sunDayEndTimeHr" name="sunDayEndTimeHr" class="box">
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
				   <select id="sunDayEndTimeMin" name="sunDayEndTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="sunDayEndTimeAmPm" name="sunDayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="sunDayBreakTimeOpenCloseDiv">
				   <input id="sunDayBreakTimeOpenClose" type="checkbox"  name="sunDayBreakTimeOpenClose" />
				   <label for="sunDayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="sunDayBreakTimeDiv">
				   <select id="sunDayBreakTimeHr" name="sunDayBreakTimeHr" class="box">
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
				   <select id="sunDayBreakTimeMin" name="sunDayBreakTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="sunDayBreakTimeAmPm" name="sunDayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   &nbsp;
				   Duration
				   <select id="sunDayBreakDuration" name="sunDayBreakDuration" class="box">
					  <c:forEach var="breakTimeDuration" items="${breakTimeDurationMap}">
						<option value="${breakTimeDuration.key}">${breakTimeDuration.value}</option>
					  </c:forEach>
				   </select>
				</div>
			 </td>
		  </tr>
		  <!---------------------------- Monday Hour details ------------------------------>
		  <tr>
			 <td>
				<i>Monday</i><br>
				<div class="openClose slide2" id="monDayOpenCloseDiv">
				   <input id="monDayOpenClose" type="checkbox" name="monDayOpenClose" />
				   <label for="monDayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="monDayStartTimeDiv" >
				   <select id="monDayStartTimeHr" name="monDayStartTimeHr" class="box">
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
				   <select id="monDayStartTimeMin" name="monDayStartTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="monDayStartTimeAmPm" name="monDayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div id="monDayEndTimeDiv">
				   <select id="monDayEndTimeHr" name="monDayEndTimeHr" class="box">
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
				   <select id="monDayEndTimeMin" name="monDayEndTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="monDayEndTimeAmPm" name="monDayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="monDayBreakTimeOpenCloseDiv">
				   <input id="monDayBreakTimeOpenClose" type="checkbox"  name="monDayBreakTimeOpenClose" />
				   <label for="monDayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="monDayBreakTimeDiv">
				   <select id="monDayBreakTimeHr" name="monDayBreakTimeHr" class="box">
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
				   <select id="monDayBreakTimeMin" name="monDayBreakTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="monDayBreakTimeAmPm" name="monDayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   Duration
				   <select id="monDayBreakDuration" name="monDayBreakDuration" class="box">
					  <c:forEach var="breakTimeDuration" items="${breakTimeDurationMap}">
						<option value="${breakTimeDuration.key}">${breakTimeDuration.value}</option>
					  </c:forEach>
				   </select>
				</div>
			 </td>
		  </tr>
		  <!---------------------------- Tuesday Hour details ------------------------------>
		  <tr class="altColor">
			 <td>
				<i>Tuesday</i><br>
				<div class="openClose slide2" id="tuesDayOpenCloseDiv">
				   <input id="tuesDayOpenClose" type="checkbox" name="tuesDayOpenClose"/>
				   <label for="tuesDayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="tuesDayStartTimeDiv">
				   <select id="tuesDayStartTimeHr" name="tuesDayStartTimeHr" class="box">
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
				   <select id="tuesDayStartTimeMin" name="tuesDayStartTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="tuesDayStartTimeAmPm" name="tuesDayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div id="tuesDayEndTimeDiv">
				   <select id="tuesDayEndTimeHr" name="tuesDayEndTimeHr" class="box">
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
				   <select id="tuesDayEndTimeMin" name="tuesDayEndTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="tuesDayEndTimeAmPm" name="tuesDayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="tuesDayBreakTimeOpenCloseDiv">
				   <input id="tuesDayBreakTimeOpenClose" type="checkbox"  name="tuesDayBreakTimeOpenClose" />
				   <label for="tuesDayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="tuesDayBreakTimeDiv">
				   <select id="tuesDayBreakTimeHr" name="tuesDayBreakTimeHr" class="box">
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
				   <select id="tuesDayBreakTimeMin" name="tuesDayBreakTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="tuesDayBreakTimeAmPm" name="tuesDayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   Duration
				   <select id="tuesDayBreakDuration" name="tuesDayBreakDuration" class="box">
					 <c:forEach var="breakTimeDuration" items="${breakTimeDurationMap}">
						<option value="${breakTimeDuration.key}">${breakTimeDuration.value}</option>
					  </c:forEach>
				   </select>
				</div>
			 </td>
		  </tr>
		  <!---------------------------- Wednesday Hour details ------------------------------>
		  <tr>
			 <td>
				<i>Wednesday</i><br>
				<div class="openClose slide2" id="wednesDayOpenCloseDiv">
				   <input id="wednesDayOpenClose" type="checkbox" name="wednesDayOpenClose"/>
				   <label for="wednesDayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="wednesDayStartTimeDiv">
				   <select id="wednesDayStartTimeHr" name="wednesDayStartTimeHr" class="box">
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
				   <select id="wednesDayStartTimeMin" name="wednesDayStartTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="wednesDayStartTimeAmPm" name="wednesDayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div id="wednesDayEndTimeDiv">
				   <select id="wednesDayEndTimeHr" name="wednesDayEndTimeHr" class="box">
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
				   <select id="wednesDayEndTimeMin" name="wednesDayEndTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="wednesDayEndTimeAmPm" name="wednesDayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="wednesDayBreakTimeOpenCloseDiv">
				   <input id="wednesDayBreakTimeOpenClose" type="checkbox"  name="wednesDayBreakTimeOpenClose" />
				   <label for="wednesDayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="wednesDayBreakTimeDiv">
				   <select id="wednesDayBreakTimeHr" name="wednesDayBreakTimeHr" class="box">
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
				   <select id="wednesDayBreakTimeMin" name="wednesDayBreakTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="wednesDayBreakTimeAmPm" name="wednesDayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   Duration
				   <select id="wednesDayBreakDuration" name="wednesDayBreakDuration" class="box">
					  <c:forEach var="breakTimeDuration" items="${breakTimeDurationMap}">
						<option value="${breakTimeDuration.key}">${breakTimeDuration.value}</option>
					  </c:forEach>
				   </select>
				</div>
			 </td>
		  </tr>
		  
		  <!---------------------------- Thursday Hour details ------------------------------>
		  <tr class="altColor">
			 <td>
				<i>Thursday</i><br>
				<div class="openClose slide2" id="thursDayOpenCloseDiv">
				   <input id="thursDayOpenClose" type="checkbox"  name="thursDayOpenClose"/>
				   <label for="thursDayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="thursDayStartTimeDiv">
				   <select id="thursDayStartTimeHr" name="thursDayStartTimeHr" class="box">
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
				   <select id="thursDayStartTimeMin" name="thursDayStartTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="thursDayStartTimeAmPm" name="thursDayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div>
				<div id="thursDayEndTimeDiv">
				   <select id="thursDayEndTimeHr" name="thursDayEndTimeHr" class="box">
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
				   <select id="thursDayEndTimeMin" name="thursDayEndTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="thursDayEndTimeAmPm" name="thursDayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="thursDayBreakTimeOpenCloseDiv">
				   <input id="thursDayBreakTimeOpenClose" type="checkbox"  name="thursDayBreakTimeOpenClose" />
				   <label for="thursDayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="thursDayBreakTimeDiv">
				   <select id="thursDayBreakTimeHr" name="thursDayBreakTimeHr" class="box">
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
				   <select id="thursDayBreakTimeMin" name="thursDayBreakTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="thursDayBreakTimeAmPm" name="thursDayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   Duration
				   <select id="thursDayBreakDuration" name="thursDayBreakDuration" class="box">
					  <c:forEach var="breakTimeDuration" items="${breakTimeDurationMap}">
						<option value="${breakTimeDuration.key}">${breakTimeDuration.value}</option>
					  </c:forEach>
				   </select>
				</div>
			 </td>
		  </tr>
		  
		  <!---------------------------- Friday Hour details ------------------------------>
		  <tr>
			 <td>
				<i>Friday</i><br>
				<div class="openClose slide2" id="friDayOpenCloseDiv">
				   <input id="friDayOpenClose" type="checkbox" name="friDayOpenClose"/>
				   <label for="friDayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="friDayStartTimeDiv">
				   <select id="friDayStartTimeHr" name="friDayStartTimeHr" class="box">
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
				   <select id="friDayStartTimeMin" name="friDayStartTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="friDayStartTimeAmPm" name="friDayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div id="friDayEndTimeDiv">
				   <select id="friDayEndTimeHr" name="friDayEndTimeHr" class="box">
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
				   <select id="friDayEndTimeMin" name="friDayEndTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="friDayEndTimeAmPm" name="friDayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="friDayBreakTimeOpenCloseDiv">
				   <input id="friDayBreakTimeOpenClose" type="checkbox"  name="friDayBreakTimeOpenClose" />
				   <label for="friDayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="friDayBreakTimeDiv">
				   <select id="friDayBreakTimeHr" name="friDayBreakTimeHr" class="box">
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
				   <select id="friDayBreakTimeMin" name="friDayBreakTimeMin" class="box">
					  <c:forEach var="min" items="${minsList}">
						<option value="${min}">${min}</option>
					  </c:forEach>
				   </select>
				   &nbsp;
				   <select id="friDayBreakTimeAmPm" name="friDayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   Duration
				   <select id="friDayBreakDuration" name="friDayBreakDuration" class="box">
					  <c:forEach var="breakTimeDuration" items="${breakTimeDurationMap}">
						<option value="${breakTimeDuration.key}">${breakTimeDuration.value}</option>
					  </c:forEach>
				   </select>
				</div>
			 </td>
		  </tr>
		  
		  <!---------------------------- Saturday Hour details ------------------------------>
		  <tr class="altColor">
			 <td>
				<i>Saturday</i><br>
				<div class="openClose slide2" id="saturDayOpenCloseDiv">
				   <input id="saturDayOpenClose" type="checkbox" name="saturDayOpenClose"/>
				   <label for="saturDayOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
			 </td>
			 <td>
				<div id="saturDayStartTimeDiv">
				   <select id="saturDayStartTimeHr" name="saturDayStartTimeHr" class="box">
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
				   <select id="saturDayStartTimeMin" name="saturDayStartTimeMin" class="box">
					  <option value="00">00</option>
					  <option value="15">15</option>
					  <option value="30">30</option>
					  <option value="45">45</option>
				   </select>
				   &nbsp;
				   <select id="saturDayStartTimeAmPm" name="saturDayStartTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div id="saturDayEndTimeDiv">
				   <select id="saturDayEndTimeHr" name="saturDayEndTimeHr" class="box">
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
				   <select id="saturDayEndTimeMin" name="saturDayEndTimeMin" class="box">
					  <option value="00">00</option>
					  <option value="15">15</option>
					  <option value="30">30</option>
					  <option value="45">45</option>
				   </select>
				   &nbsp;
				   <select id="saturDayEndTimeAmPm" name="saturDayEndTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				</div>
			 </td>
			 <td>
				<div class="yesNo slide2"  id="saturDayBreakTimeOpenCloseDiv">
				   <input id="saturDayBreakTimeOpenClose" type="checkbox"  name="saturDayBreakTimeOpenClose" />
				   <label for="saturDayBreakTimeOpenClose">
					  <div class="card"></div>
				   </label>
				</div>
				<div id="saturDayBreakTimeDiv">
				   <select id="saturDayBreakTimeHr" name="saturDayBreakTimeHr" class="box">
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
				   <select id="saturDayBreakTimeMin" name="saturDayBreakTimeMin" class="box">
					  <option value="00">00</option>
					  <option value="15">15</option>
					  <option value="30">30</option>
					  <option value="45">45</option>
				   </select>
				   &nbsp;
				   <select id="saturDayBreakTimeAmPm" name="saturDayBreakTimeAmPm" class="box">
					  <option value="AM">AM</option>
					  <option value="PM">PM</option>
				   </select>
				   Duration
				   <select id="saturDayBreakDuration" name="saturDayBreakDuration" class="box">
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
		<input type="button" class="btn noMinWidth" value="Reset" id="resetResourceDateRange0WorkingHoursBtn">
		&nbsp;
		<input type="button" class="btn" value="Update Working Hours" id="updateResourceDateRange0WorkingHoursBtn">
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
	   <input type="button" class="btn" value="Cancel" id="displacedApptsCancelResourceDateRangeWorkingHoursBtn"> 
		&nbsp;
		<input type="button" class="btn noMinWidth" value="Proceed" id="displacedApptsProceedResourceDateRangeWorkingHoursBtn">
	  </div>
	  <div class="clear-all"></div>	 
 </div> 
 
<!-- Body ends --> 
</body>
</html>