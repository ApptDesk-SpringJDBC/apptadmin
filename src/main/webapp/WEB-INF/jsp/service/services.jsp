<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>${displayNames.servicesName}</title>
<script type='text/javascript' src="static/js/validation/service/serviceFormValidation.js?version=1.4"></script>
</head>

<body>
<!-- Body starts -->
<h1>${displayNames.servicesName}</h1>

<!-- New HTML starts here -->
<div> 
  <!-- Left section starts -->
  <div style="width:50%" class="float-left"> 
    
    <!--Scheduled Locations starts -->
    <div class="pageSubHeader">
      <div class="float-left txt-bold">Scheduled ${displayNames.servicesName}</div>
      <div class="float-right">
        <input type="button" class="btn" value="Add ${displayNames.serviceName}" id="addServiceReqBtn">
      </div>
      <div class="clear-all"></div>
    </div>
    <div class="dragDivContainer">	
      <c:forEach items="${serviceResponse.serviceList}" var="service" varStatus="loop">
			<c:set var="changeDivClassId" value="changeDivClass${service.serviceId}"/>
			<div id="${changeDivClassId}" class="${loop.index==0 ? 'dragDiv dragDivSelected' : 'dragDiv'}" style="cursor: pointer" 
				onclick="loadScheduledServiceEditData('${service.serviceId}');">				
				${service.serviceNameOnline} 
				${(not empty service.duration) ? ',' : '' } ${(not empty service.duration) ? service.duration : '' }${(not empty service.duration) ? ' mins' : '' }  
				<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable" class="float-right"/>
				<img src="static/images/open_icon.png" alt="Opened" title="Opened" width="20" height="20"  class="float-right"/>
			</div>
	  </c:forEach>	
	  <c:set var="serviceId" value="0"/>
	  <c:if test="${fn:length(serviceResponse.serviceList)>0}">
			<c:set var="serviceId" value="${serviceResponse.serviceList[0].serviceId}"/>			
	  </c:if>
	  <script type="text/javascript">	
			loadScheduledServiceEditData('${serviceId}');
	  </script>
	</div>
    <!--Scheduled Services ends --> 
    
    <!--Deleted Services starts -->
    <div class="pageSubHeader">
      <div class="float-left txt-bold">Suspended ${displayNames.servicesName}</div>
      <div class="clear-all"></div>
    </div>
    <div class="dragDivContainer">				
	  <c:forEach items="${serviceResponse.deletedServiceList}" var="deletedService" varStatus="loop">
			<c:set var="changeDivClassId" value="changeDivClass${deletedService.serviceId}"/>
			<div id="${changeDivClassId}" class="dragDiv" style="cursor: pointer" 
				onclick="loadSuspendedServiceEditData('${deletedService.serviceId}');">				
				${deletedService.serviceNameOnline} 
				${(not empty deletedService.duration) ? ',' : '' } ${(not empty deletedService.duration) ? deletedService.duration : '' } ${(not empty deletedService.duration) ? ' mins' : '' }	  
				<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable" class="float-right"/>
			</div>
	  </c:forEach>	
    </div>
    <!--Deleted Services ends --> 
    <div class="clear-all"></div>
  </div>
  <!-- Left section ends --> 
  <input id="blockTimeInMin" name="blockTimeInMin" type="hidden" value="${serviceResponse.blocksTimeInMins}">
  
  <!-- Right section starts -->
  <div class="float-right" style="width:49%">  
    <div class="float-left txt-bold" id="addOrEditServiceLabel">Edit ${displayNames.serviceName}</div>
    <div class="float-right"><input type="button" class="btn" value="Delete Service" id="deleteOrUndeleteService"></div>
    <div class="clear-all"></div>
	<div style="color:blue" class="txt-bold" id="serviceSucessesMsgDivId"></div>
	<div class="required txt-bold"  id="serviceErrorMsgDivId"></b></div>	
	
	<input id="deleteOrUndeleteActionURL" type="hidden" value="">
	
	<c:if test="${not empty dynamicFieldDisplayData.dynamicFieldDisplay && fn:length(dynamicFieldDisplayData.dynamicFieldDisplay)>0}">
	  <form id="saveOrUpdateForm" action="" method="post">
		<div class="addEditSection">
		  <div class="required-indicator float-right"> <span class="required">*</span>indicates required field </div>
		  <div class="error" id="errors"></div>	  
		  <div>
			<dl>				
			  <input id="serviceId" name="serviceId" type="hidden" value="0">
			
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['serviceNameOnline'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['serviceNameOnline'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>${displayNames.serviceName} Name </dt>
					  <dd>
						<input id="serviceNameOnline" name="serviceNameOnline" type="text" value="" maxlength="100">
						<div class="error" id="serviceNameOnline_error"></div>	 
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="serviceNameOnline" name="serviceNameOnline" type="hidden" value="" maxlength="100">
				</c:otherwise>
			  </c:choose>
			  
			  <c:set var="durationEnabled" value="N"/>
			  <c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['duration'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['duration'].dispay eq 'Y')}">
				 <c:set var="durationEnabled" value="Y"/>
				 <dt> Duration </dt>
				  <dd>
					<div id="serviceDurationSelect" style="disply:none">
						<select id="serviceDuration" name="serviceDuration">
						</select>
					</div>
					<div id="serviceDurationText">
					</div>
				  </dd>
			  </c:if>
			  <input id="durationEnabled" type="hidden" value="${durationEnabled}">
			  <input id="duration" name="duration" type="hidden" value="">
			  			  
			  <c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['allowSchedulingFor'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['allowSchedulingFor'].dispay eq 'Y')}">
					  <dt> Allow Scheduling for: </dt>
					  <dd>
						<input id="isSunOpen_str" class="noMinWidth" type="checkbox" value="Y">
						Sunday <br/>
						<input id="isMonOpen_str" class="noMinWidth" type="checkbox" value="Y">
						Monday <br/>
						<input id="isTueOpen_str" class="noMinWidth" type="checkbox" value="Y">
						Tuesday <br/>
						<input id="isWedOpen_str" class="noMinWidth" type="checkbox" value="Y">
						Wednesday <br/>
						<input id="isThuOpen_str" class="noMinWidth" type="checkbox" value="Y">
						Thursday <br/>
						<input id="isFriOpen_str" class="noMinWidth" type="checkbox" value="Y">
						Friday <br/>
						<input id="isSatOpen_str" class="noMinWidth" type="checkbox" value="Y">
						Saturday <br/>
						<div class="error" id="allowSchedulingFor_error"></div>
					  </dd>
			   </c:if>
			   
			   <input id="isSunOpen" name="isSunOpen" class="noMinWidth" type="hidden">
			   <input id="isMonOpen" name="isMonOpen" class="noMinWidth" type="hidden">
			   <input id="isTueOpen" name="isTueOpen" class="noMinWidth" type="hidden">
			   <input id="isWedOpen" name="isWedOpen" class="noMinWidth" type="hidden">
			   <input id="isThuOpen" name="isThuOpen" class="noMinWidth" type="hidden">
			   <input id="isFriOpen" name="isFriOpen" class="noMinWidth" type="hidden">
			   <input id="isSatOpen" name="isSatOpen" class="noMinWidth" type="hidden">
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['serviceNameIvrTts'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['serviceNameIvrTts'].dispay eq 'Y')}">
					  <dt> TTS: </dt>
					  <dd>
						<input id="serviceNameIvrTts" name="serviceNameIvrTts" type="text">
						<div class="error" id="serviceNameIvrTts_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="serviceNameIvrTts" name="serviceNameIvrTts" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['serviceNameIvrAudio'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['serviceNameIvrAudio'].dispay eq 'Y')}">
					  <dt>Audio </dt>
					  <dd>
						<audio controls>
						  <source src="horse1.ogg" type="audio/ogg">
						  <source src="horse1.mp3" type="audio/mpeg">
						  Your browser does not support the audio element. 
						</audio>			  
						<input id="serviceNameIvrAudio" name="serviceNameIvrAudio" type="hidden" value="">
						<div class="error" id="serviceNameIvrAudio_error"></div>
					  </dd> 
				</c:when>
				<c:otherwise>
					   <input id="serviceNameIvrAudio" name="serviceNameIvrAudio" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['allowDuplicateAppt'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['allowDuplicateAppt'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>Allow Duplicate Appt:</dt>
					  <dd>
						<input name="allowDuplicateAppt" type="radio" value="Y" class="noMinWidth"> Yes
						<input name="allowDuplicateAppt" type="radio" value="N" class="noMinWidth"> No 
					  </dd>			  
				</c:when>
				<c:otherwise>
					   <input id="allowDuplicateAppt" name="allowDuplicateAppt" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['enable'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['enable'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>Enable this ${displayNames.serviceName}</dt>
					  <dd>
						<input name="enable" type="radio" value="Y" class="noMinWidth"> Yes
						<input name="enable" type="radio" value="N" class="noMinWidth"> No 
					  </dd>			  
				</c:when>
				<c:otherwise>
					   <input id="enable" name="enable" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['closed'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['closed'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>Status</dt>
					  <dd>
						<input name="closed" type="radio" value="N" class="noMinWidth"> Open
						<input name="closed" type="radio" value="Y" class="noMinWidth"> Close
					  </dd>		  
				</c:when>
				<c:otherwise>
					   <input id="closed" name="closed" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			 <c:set var="closedLocationsEnabled" value="N"/>
			 <div id="closedLocationDetails" style="display:none">
				<c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['closedLocations'])
								&& (dynamicFieldDisplayData.dynamicFieldDisplay['closedLocations'].dispay eq 'Y')}">
					<c:set var="closedLocationsEnabled" value="Y"/>
					<dt> <span class="required">*</span>${displayNames.locationsName}: </dt>
					<dd id="locationDetails">						
					</dd>
					<div class="error" id="locations_error"></div>
				</c:if>
			 </div>
			 <input id="closedLocationsEnabled" type="hidden" value="${closedLocationsEnabled}">
			 <input id="closedLocationIds" name="closedLocationIds" type="hidden" value="">
				
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['closedMessage'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['closedMessage'].dispay eq 'Y')}">
					  <dt>Closed Online Message:</dt>
					  <dd>
					    <textarea rows="5" cols="50" id="closedMessage" name="closedMessage"></textarea>
						<div class="error" id="closedMessage_error"></div>
					  </dd> 
				</c:when>
				<c:otherwise>
					   <input id="closedMessage" name="closedMessage" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['closedTts'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['closedTts'].dispay eq 'Y')}">
					  <dt>Closed IVR TTS:</dt>
					  <dd>
						<input id="closedTts" name="closedTts" type="text" value="" maxlength="60">
						<div class="error" id="closedTts_error"></div>
					  </dd> 
				</c:when>
				<c:otherwise>
					   <input id="closedTts" name="closedTts" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			  
			  <c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['closedAudio'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['closedAudio'].dispay eq 'Y')}">
					<dt>Closed IVR Audio:</dt>
					<dd>
						<audio controls>
						  <source src="horse1.ogg" type="audio/ogg">
						  <source src="horse1.mp3" type="audio/mpeg">
						  Your browser does not support the audio element. 
						</audio>						  
					</dd>  
			   </c:if>
				<input id="closedAudio" name="closedAudio" type="hidden" value="">
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['serviceCSSColor'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['serviceCSSColor'].dispay eq 'Y')}">
					  <dt> Service Color: </dt>
					  <dd>
						<select id="serviceCSSColor" name="serviceCSSColor">
						  <option value="Green">Green</option>
						  <option value="Blue">Blue</option>
						  <option value="Red">Red</option>
						  <option value="Yellow">Yellow</option>
						</select>
						<div class="error" id="serviceCSSColor_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="serviceCSSColor" name="serviceCSSColor" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['apptStartDate'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['apptStartDate'].dispay eq 'Y')}">
					  <dt>App Start Date:</dt>
					  <dd>
						<input id="apptStartDate" name="apptStartDate" type="text" class="hasDatepicker">
						<div class="error" id="apptStartDate_error"></div>
					  </dd> 
				</c:when>
				<c:otherwise>
					   <input id="apptStartDate" name="apptStartDate" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			  			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['apptEndDate'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['apptEndDate'].dispay eq 'Y')}">
					  <dt>App End Date:</dt>
					  <dd>
						<input id="apptEndDate" name="apptEndDate" type="text" class="hasDatepicker">
						<div class="error" id="apptEndDate_error"></div>
					  </dd> 
				</c:when>
				<c:otherwise>
					   <input id="apptEndDate" name="apptEndDate" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			</dl>
		  </div>
		  
		  <input id="buffer" name="buffer" type="hidden" value="">
		  
		  <div class="clear-all"></div>
		  <div class="float-right pTop20" id="actionButtonsDiv">
			<input type="button" class="btn noMinWidth" value="Reset" id="resetServiceBtn">
			&nbsp;
			<input type="button" class="btn noMinWidth" value="Save" id="saveOrUpdateServiceBtn">
		  </div>
		  <div class="clear-all"></div>
		</div>
		</form>
	</c:if>
  </div>
  <!-- Right section ends --> 
  <div class="clear-all"></div>
</div>
<!-- New HTML ends here --> 

<!-- Body ends --> 
</body>
</html>
