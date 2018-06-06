<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>${displayNames.locationsName}</title>
<script type='text/javascript' src="static/js/validation/location/locationFormValidation.js?version=3.4"></script>
</head>

<body>
<!-- Body starts -->
<h1>${displayNames.locationsName}</h1>

<!-- New HTML starts here -->
<div> 
  <!-- Left section starts -->
  <div style="width:50%" class="float-left"> 
    
    <!--Scheduled Locations starts -->
    <div class="pageSubHeader">
      <div class="float-left txt-bold">Scheduled ${displayNames.locationsName}</div>
      <div class="float-right">
        <input type="button" class="btn" value="Add ${displayNames.locationName}" id="addLocationReqBtn">
      </div>
      <div class="clear-all"></div>
    </div>
    <div class="dragDivContainer">	
      <c:forEach items="${locationResponse.locationList}" var="location" varStatus="loop">
			<c:set var="changeDivClassId" value="changeDivClass${location.locationId}"/>
			<div id="${changeDivClassId}" class="${loop.index==0 ? 'dragDiv dragDivSelected' : 'dragDiv'}" style="cursor: pointer" 
			onclick="loadScheduledLoactionEditData('${location.locationId}');">				
				${location.locationNameOnline} 
				${(not empty location.address) ? ',' : '' } ${(not empty location.address) ? location.address : '' }
				${(not empty location.city) ? ',' : '' } ${(not empty location.city) ? location.city : '' }
				${(not empty location.state) ? ',' : '' } ${(not empty location.state) ? location.state : '' }
				${(not empty location.zip) ? ',' : '' } ${(not empty location.zip) ? location.zip : '' }
				<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable" class="float-right"/>
				<img src="static/images/open_icon.png" alt="Opened" title="Opened" width="20" height="20"  class="float-right"/>
			</div>
	  </c:forEach>	
	  <c:set var="locationId" value="0"/>
	  <c:if test="${fn:length(locationResponse.locationList)>0}">
			<c:set var="locationId" value="${locationResponse.locationList[0].locationId}"/>			
	  </c:if>
	  <script type="text/javascript">	
			loadScheduledLoactionEditData('${locationId}');
	  </script>
	</div>
    <!--Scheduled Locations ends --> 
    
    <!--Deleted Locations starts -->
    <div class="pageSubHeader">
      <div class="float-left txt-bold">Suspended ${displayNames.locationsName}</div>
      <div class="clear-all"></div>
    </div>
    <div class="dragDivContainer">				
	  <c:forEach items="${locationResponse.deletedLocationList}" var="deletedLocation" varStatus="loop">
			<c:set var="changeDivClassId" value="changeDivClass${deletedLocation.locationId}"/>
			<div id="${changeDivClassId}" class="dragDiv" style="cursor: pointer" onclick="loadSuspendedLoactionEditData('${deletedLocation.locationId}');">				
				${deletedLocation.locationNameOnline} 
				${(not empty deletedLocation.address) ? ',' : '' } ${(not empty deletedLocation.address) ? deletedLocation.address : '' }
				${(not empty deletedLocation.city) ? ',' : '' } ${(not empty deletedLocation.city) ? deletedLocation.city : '' }
				${(not empty deletedLocation.state) ? ',' : '' } ${(not empty deletedLocation.state) ? deletedLocation.state : '' }
				${(not empty deletedLocation.zip) ? ',' : '' } ${(not empty deletedLocation.zip) ? deletedLocation.zip : '' }				  
				<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable" class="float-right"/>
			</div>
	  </c:forEach>	
    </div>
    <!--Deleted Locations ends --> 
    <div class="clear-all"></div>
  </div>
  <!-- Left section ends --> 
  
  <!-- Right section starts -->
  <div class="float-right" style="width:49%">  
    <div class="float-left txt-bold"><span id="addOrEditLocationLabel"> Edit </span> ${displayNames.locationName}</div>
    <div class="float-right"><input type="button" class="btn" value="Delete Location" id="deleteOrUndeleteLocation"></div>
    <div class="clear-all"></div>
	<div style="color:blue" class="txt-bold" id="locationSucessesMsgDivId"></div>
	<div class="required txt-bold"  id="locationErrorMsgDivId"></b></div>	
	
	<input id="deleteOrUndeleteActionURL" type="hidden" value="">
	
	<c:if test="${not empty dynamicFieldDisplayData.dynamicFieldDisplay && fn:length(dynamicFieldDisplayData.dynamicFieldDisplay)>0}">
	  <form id="saveOrUpdateForm" action="" method="post">
		<div class="addEditSection">
		  <div class="required-indicator float-right"> <span class="required">*</span>indicates required field </div>
		  <div class="error" id="errors"></div>	  
		  <div>
			<dl>				
			  <input id="locationId" name="locationId" type="hidden" value="0">
			
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['locationNameOnline'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['locationNameOnline'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>${displayNames.locationName} Name </dt>
					  <dd>
						<input id="locationNameOnline" name="locationNameOnline" type="text" value="" maxlength="100">
						<div class="error" id="locationNameOnline_error"></div>	 
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="locationNameOnline" name="locationNameOnline" type="hidden" value="" maxlength="100">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['address'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['address'].dispay eq 'Y')}">
					  <dt> Street Address </dt>
					  <dd>
						<input id="address" name="address" type="text" value="" maxlength="60">
						<div class="error" id="address_error"></div>	
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="address" name="address" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['city'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['city'].dispay eq 'Y')}">
					  <dt> City </dt>
					  <dd>
						<input id="city" name="city" type="text" value="" maxlength="30">
						<div class="error" id="city_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="city" name="city" type="hidden" value="" maxlength="30">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['state'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['state'].dispay eq 'Y')}">
					  <dt> State </dt>
					  <dd>
						<select id="state" name="state">
						  <option value="AL">Alabama</option>
						  <option value="AK">Alaska</option>
						  <option value="AZ">Arizona</option>
						  <option value="AR">Arkansas</option>
						  <option value="CA">California</option>
						  <option value="CO">Colorado</option>
						  <option value="CT">Connecticut</option>
						  <option value="DE">Delaware</option>
						  <option value="FL">Florida</option>
						  <option value="GA">Georgia</option>
						  <option value="HI">Hawaii</option>
						  <option value="ID">Idaho</option>
						  <option value="IL">Illinois</option>
						  <option value="IN">Indiana</option>
						  <option value="IA">Iowa</option>
						  <option value="KS">Kansas</option>
						  <option value="KY">Kentucky</option>
						  <option value="LA">Louisiana</option>
						  <option value="ME">Maine</option>
						  <option value="MD">Maryland</option>
						  <option value="MA">Massachusetts</option>
						  <option value="MI">Michigan</option>
						  <option value="MN">Minnesota</option>
						  <option value="MS">Mississippi</option>
						  <option value="MO">Missouri</option>
						  <option value="MT">Montana</option>
						  <option value="NE">Nebraska</option>
						  <option value="NV">Nevada</option>
						  <option value="NH">New Hampshire</option>
						  <option value="NJ">New Jersey</option>
						  <option value="NM">New Mexico</option>
						  <option value="NY">New York</option>
						  <option value="NC">North Carolina</option>
						  <option value="ND">North Dakota</option>
						  <option value="OH">Ohio</option>
						  <option value="OK">Oklahoma</option>
						  <option value="OR">Oregon</option>
						  <option value="PA">Pennsylvania</option>
						  <option value="RI">Rhode Island</option>
						  <option value="SC">South Carolina</option>
						  <option value="SD">South Dakota</option>
						  <option value="TN">Tennessee</option>
						  <option value="TX">Texas</option>
						  <option value="UT">Utah</option>
						  <option value="VT">Vermont</option>
						  <option value="VA">Virginia</option>
						  <option value="WA" selected="selected">Washington</option>
						  <option value="WV">West Virginia</option>
						  <option value="WI">Wisconsin</option>
						  <option value="WY">Wyoming</option>
						</select>
						<div class="error" id="state_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="state" name="state" type="hidden" value="" maxlength="30">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['zip'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['zip'].dispay eq 'Y')}">
					  <dt> ZIP </dt>
					  <dd>
						<input id="zip" name="zip" type="text" class="phone1 noMinWidth"  value="" maxlength="5">
						<div class="error" id="zip_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="zip" name="zip" type="hidden" value="" maxlength="5">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['timeZone'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['timeZone'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>Time Zone </dt>
					  <dd>
						<select id="timeZone" name="timeZone">
						  <option value="US/Alaska">US/Alaska</option>
						  <option value="US/Aleutian">US/Aleutian</option>
						  <option value="US/Arizona">US/Arizona</option>
						  <option value="US/Central">US/Central</option>
						  <option value="US/East-Indiana">US/East-Indiana</option>
						  <option value="US/Eastern">US/Eastern</option>
						  <option value="US/Hawaii">US/Hawaii</option>
						  <option value="US/Indiana-Starke">US/Indiana-Starke</option>
						  <option value="US/Michigan">US/Michigan</option>
						  <option value="US/Mountain">US/Mountain</option>
						  <option value="US/Pacific" selected="selected">US/Pacific</option>
						  <option value="US/Pacific-New">US/Pacific-New</option>
						  <option value="US/Samoa">US/Samoa</option>
						  <option value="Canada/Atlantic">Canada/Atlantic</option>
						  <option value="Canada/Central">Canada/Central</option>
						  <option value="Canada/East-Saskatchewan">Canada/East-Saskatchewan</option>
						  <option value="Canada/Eastern">Canada/Eastern</option>
						  <option value="Canada/Mountain">Canada/Mountain</option>
						  <option value="Canada/Newfoundland">Canada/Newfoundland</option>
						  <option value="Canada/Pacific">Canada/Pacific</option>
						  <option value="Canada/Saskatchewan">Canada/Saskatchewan</option>
						  <option value="Canada/Yukon">Canada/Yukon</option>
						</select>
						<div class="error" id="timeZone_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					 <input id="timeZone" name="timeZone" type="text" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['workPhone'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['workPhone'].dispay eq 'Y')}">
					  <dt> Work Phone </dt>
					  <dd>
						<input id="workPhone1" name="workPhone1" class="phone noMinWidth" type="text" value="" maxlength="3">
						<input id="workPhone2" name="workPhone2" class="phone noMinWidth" type="text" value="" maxlength="3">
						<input id="workPhone3" name="workPhone3" class="phone1 noMinWidth" type="text" value="" maxlength="4">
						<input id="workPhone" name="workPhone" type="hidden" value="" maxlength="10">
						<div class="error" id="workPhone_error"></div>
					  </dd> 
				</c:when>
				<c:otherwise>
					   <input id="workPhone" name="workPhone" type="hidden" value="" maxlength="10">
				</c:otherwise>
			  </c:choose>
			  
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['enable'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['enable'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>Enable this Location</dt>
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
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['locationNameIvrTts'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['locationNameIvrTts'].dispay eq 'Y')}">
					  <dt>IVR TTS:</dt>
					  <dd>
						<input id="locationNameIvrTts" name="locationNameIvrTts" type="text" value="" maxlength="60">
						<div class="error" id="locationNameIvrTts_error"></div>
					  </dd>	  
				</c:when>
				<c:otherwise>
					   <input id="locationNameIvrTts" name="locationNameIvrTts" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			 
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['locationNameIvrAudio'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['locationNameIvrAudio'].dispay eq 'Y')}">
					   <dt>IVR Audio:</dt>
					  <dd>
						<audio controls>
						  <source src="horse1.ogg" type="audio/ogg">
						  <source src="horse1.mp3" type="audio/mpeg">
						  Your browser does not support the audio element. 
						</audio>
						<input id="locationNameIvrAudio" name="locationNameIvrAudio" type="hidden" value="">
					  </dd>  
				</c:when>
				<c:otherwise>
					   <input id="locationNameIvrAudio" name="locationNameIvrAudio" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['locationGoogleMap'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['locationGoogleMap'].dispay eq 'Y')}">
					  <dt>Google Embed Map Code:</dt>
					  <dd>
						<textarea rows="5" cols="50" id="locationGoogleMap" name="locationGoogleMap"></textarea>
						<div class="error" id="locationGoogleMap_error"></div>
					  </dd>  
				</c:when>
				<c:otherwise>
					   <input id="locationGoogleMap" name="locationGoogleMap" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			  			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['locationGoogleMapLink'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['locationGoogleMapLink'].dispay eq 'Y')}">
					  <dt>Google Share Link:</dt>
					  <dd>
					    <textarea rows="5" cols="50" id="locationGoogleMapLink" name="locationGoogleMapLink"></textarea>
						<div class="error" id="locationGoogleMapLink_error"></div>
					  </dd>  
				</c:when>
				<c:otherwise>
					   <input id="locationGoogleMapLink" name="locationGoogleMapLink" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			  
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
						<textarea rows="5" cols="50" id="closedTts" name="closedTts"></textarea>
						<div class="error" id="closedTts_error"></div>
					  </dd> 
				</c:when>
				<c:otherwise>
					   <input id="closedTts" name="closedTts" type="hidden" value="" maxlength="60">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['closedAudio'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['closedAudio'].dispay eq 'Y')}">
					  <dt>Closed IVR Audio:</dt>
					  <dd>
						<audio controls>
						  <source src="horse1.ogg" type="audio/ogg">
						  <source src="horse1.mp3" type="audio/mpeg">
						  Your browser does not support the audio element. </audio>
						  <input id="closedAudio" name="closedAudio" type="hidden" value="" maxlength="60">
					  </dd>  
				</c:when>
				<c:otherwise>
					   <input id="closedAudio" name="closedAudio" type="hidden" value="" maxlength="60">
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
		  <div class="clear-all"></div>
		  <div class="float-right pTop20">
			<input type="button" class="btn noMinWidth" value="Reset" id="resetLocationBtn">
			&nbsp;
			<input type="button" class="btn noMinWidth" value="Save" id="saveOrUpdateLocationBtn">
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
