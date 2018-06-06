<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>${displayNames.resourcesName}</title>
<script type='text/javascript' src="static/js/validation/resource/resourceFormValidation.js?version=20.5"></script>
</head>

<body>
<!-- Body starts -->
<h1>${displayNames.resourcesName}</h1>

<!-- New HTML starts here -->
<div> 
  <!-- Left section starts -->
  <div style="width:50%" class="float-left"> 
    
    <!--Scheduled Locations starts -->
	  <c:set var="resourceId" value="0"/>
      <c:forEach items="${resourceResponse.resourceListMap}" var="entry">
			${displayNames.locationName}: ${entry.key}
			<c:set var="resourceList" value="${entry.value}"/>
			<div class="pageSubHeader">
			  <div class="float-left txt-bold">Active ${displayNames.resourcesName}</div>
			  <div class="float-right">
				<input type="button" class="btn" value="Add ${displayNames.resourceName}" id="addResourceReqBtn">
			  </div>
			  <div class="clear-all"></div>
			</div>
			<div class="dragDivContainer">	
				<c:forEach items="${resourceList}" var="resource" varStatus="loop">
					<c:set var="changeDivClassId" value="changeDivClass${resource.resourceId}"/>
					<c:if test="${resourceId==0}">
						<c:set var="resourceId" value="${resource.resourceId}"/>
					</c:if>
					<div id="${changeDivClassId}" class="${loop.index==0 ? 'dragDiv dragDivSelected' : 'dragDiv'}" style="cursor: pointer" 
						onclick="loadScheduledResourceEditData('${resource.resourceId}');">				
						${resource.resourceName} 
						<c:if test="${fn:length(resource.serviceNames)>0}">
							,
							<c:forEach items="${resource.serviceNames}" var="serviceName" varStatus="status">
									${serviceName}
									 <c:if test="${!status.last}">/</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${resource.enable=='Y'}">
							<img src="static/images/enable-icon.png" width="16" height="16" alt="Disable" class="float-right"/>
						</c:if>
						<c:if test="${resource.enable=='N'}">
							<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable" class="float-right"/>
						</c:if>
						<img src="static/images/open_icon.png" alt="Opened" title="Opened" width="20" height="20"  class="float-right"/>
					</div>
				</c:forEach>	
			</div>
	  </c:forEach>
	  <script type="text/javascript">	
			loadScheduledResourceEditData('${resourceId}');
	  </script>
    <!--Scheduled Resources ends --> 
    
    <!--Deleted Resources starts -->
    <div class="pageSubHeader">
      <div class="float-left txt-bold">Suspended ${displayNames.resourcesName}</div>
      <div class="clear-all"></div>
    </div>
    <div class="dragDivContainer">				
	  <c:forEach items="${resourceResponse.deletedResourceList}" var="deletedResource" varStatus="loop">
			<c:set var="changeDivClassId" value="changeDivClass${deletedResource.resourceId}"/>
			<div id="${changeDivClassId}" class="dragDiv" style="cursor: pointer" 
				onclick="loadSuspendedResourceEditData('${deletedResource.resourceId}');">				
				${deletedResource.resourceName} 
				<c:if test="${fn:length(deletedResource.serviceNames)>0}">
					,
					<c:forEach items="${deletedResource.serviceNames}" var="serviceName" varStatus="status">
							${serviceName}
							 <c:if test="${!status.last}">/</c:if>
					</c:forEach>
				</c:if>	
			</div>
	  </c:forEach>	
    </div>
    <!--Deleted Resources ends --> 
    <div class="clear-all"></div>
  </div>
  <!-- Left section ends --> 
  <%--
  <input id="blockTimeInMin" name="blockTimeInMin" type="hidden" value="${resourceResponse.blocksTimeInMins}">
  --%>
  
  <!-- Right section starts -->
  <div class="float-right" style="width:49%">  
    <div class="float-left txt-bold" id="addOrEditResourceLabel">
		<span id="addOrEditLabel"> Edit </span>${displayNames.resourceName}
	</div>
    <div class="required-indicator float-right"> <span class="required">*</span>indicates required field </div>
	<div class="clear-all"></div>
	<div style="color:blue" class="txt-bold" id="resourceSucessesMsgDivId"></div>
	<div class="required txt-bold"  id="resourceErrorMsgDivId"></b></div>	
	
	<input id="deleteOrUndeleteActionURL" type="hidden" value="">
	
	<c:if test="${not empty dynamicFieldDisplayData.dynamicFieldDisplay && fn:length(dynamicFieldDisplayData.dynamicFieldDisplay)>0}">
	  <form id="saveOrUpdateForm" action="" method="post">
		<div class="addEditSection">
		  <div class="float-right"><input type="button" class="btn noMinWidth" value="Delete ${displayNames.resourceName}" id="deleteOrUndeleteResource"></div>
		  <div class="error" id="errors"></div>	  
		  <div>
			<dl>				
			  <input id="resourceId" name="resourceId" type="hidden" value="0">
			
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['prefix'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['prefix'].dispay eq 'Y')}">
					  <dt>${displayNames.resourceName} Prefix </dt>
					  <dd>
						<select id="prefix" name="prefix">
						  <c:if test="${fn:length(resourcePrefix.resourcePrefixList)>0}">
							  <c:forEach items="${resourcePrefix.resourcePrefixList}" var="resPrefix" varStatus="status">
									<option value="${resPrefix.optionValue}">${resPrefix.optionName}</option>
							  </c:forEach>
						  </c:if>
						  <c:if test="${fn:length(resourcePrefix.resourcePrefixList)<=0}">
								<option value="-1">Select Prefix</option>
						  </c:if>
						</select>
						<div class="error" id="prefix_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="prefix" name="prefix" type="hidden" value="" maxlength="100">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['firstName'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['firstName'].dispay eq 'Y')}">
				  <dt><span class="required">*</span> First Name: </dt>
				  <dd>
					 <input id="firstName" name="firstName" type="text" value="">
					 <div class="error" id="firstName_error"></div>
				  </dd>
				</c:when>
				<c:otherwise>
					   <input id="firstName" name="firstName" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  			  			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['lastName'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['lastName'].dispay eq 'Y')}">
				 <dt> Last Name: </dt>
				  <dd>
					 <input id="lastName" name="lastName" type="text" value="">
					 <div class="error" id="lastName_error"></div>
				  </dd>
				</c:when>
				<c:otherwise>
					   <input id="lastName" name="lastName" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['title'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['title'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>${displayNames.resourceName} Title </dt>
					  <dd>
						<select id="title" name="title">
						  <c:if test="${fn:length(resourceTitle.resourceTitleList)>0}">
							  <c:forEach items="${resourceTitle.resourceTitleList}" var="resTitle" varStatus="status">
									<option value="${resTitle.optionValue}">${resTitle.optionName}</option>
							  </c:forEach>
						  </c:if>
						  <c:if test="${fn:length(resourceTitle.resourceTitleList)<=0}">
								<option value="-1">Select Title</option>
						  </c:if>
						 </select>
						<div class="error" id="title_error"></div>
					  </dd>
				</c:when>
				<c:otherwise>
					   <input id="title" name="title" type="hidden" value="" maxlength="100">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['email'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['email'].dispay eq 'Y')}">
				 <c:set var="durationEnabled" value="Y"/>
				 <dt> Email : </dt>
				  <dd>
					 <input id="email" name="email" type="text" value="">
					 <div class="error" id="email_error"></div>
				  </dd>
				</c:when>
				<c:otherwise>
					   <input id="email" name="email" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:set var="locationNameEnabled" value="N"/>
			  <c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['locationName'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['locationName'].dispay eq 'Y')}">
				 <c:set var="locationNameEnabled" value="Y"/>
				 <dt> ${displayNames.locationName} </dt>
				  <dd>
					<div id="locationNameSelect" style="disply:none">
						<select id="locationIdStr" name="locationIdStr">
						</select>
						<div class="error" id="locationIdStr_error"></div>
					</div>
					<div id="locationNameText">
					</div>
				  </dd>
			  </c:if>
			  <input id="locationNameEnabled" type="hidden" value="${locationNameEnabled}">
			  <input id="locationId" name="locationId" type="hidden" value="">
			  
			  <c:set var="resourceServicesEnabled" value="N"/>
			  <c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['resourceServices'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['resourceServices'].dispay eq 'Y')}">
				 <c:set var="resourceServicesEnabled" value="Y"/>
				 <dt>Assign ${displayNames.serviceName} </dt>
				  <dd id="resourceServices">
				  </dd>
				  <div class="error" id="resourceServices_error"></div>
			  </c:if>
			  <input id="resourceServicesEnabled" type="hidden" value="${resourceServicesEnabled}">
			  <input id="selectedServiceIds" name="selectedServiceIds" type="hidden" value="">
			 
			   <c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['resourceAudio'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['resourceAudio'].dispay eq 'Y')}">
				  <dt>Audio </dt>
				  <dd>
					<audio controls>
					  <source src="horse1.ogg" type="audio/ogg">
					  <source src="horse1.mp3" type="audio/mpeg">
					  Your browser does not support the audio element. 
					</audio>			  
					<div class="error" id="resourceAudio_error"></div>
				  </dd> 
				</c:if>
				<input id="resourceAudio" name="resourceAudio" type="hidden" value="">
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['allowSelfService'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['allowSelfService'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>Allow self-serve</dt>
					  <dd>
						<input name="allowSelfService" type="radio" value="Y" class="noMinWidth"> Yes
						<input name="allowSelfService" type="radio" value="N" class="noMinWidth"> No 
					  </dd>			  
				</c:when>
				<c:otherwise>
					   <input id="allowSelfService" name="enable" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			  
			  <c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['enable'])
							&& (dynamicFieldDisplayData.dynamicFieldDisplay['enable'].dispay eq 'Y')}">
					  <dt><span class="required">*</span>Status</dt>
					  <dd>
						<input name="enable" type="radio" value="Y" class="noMinWidth"> Yes
						<input name="enable" type="radio" value="N" class="noMinWidth"> No
					  </dd>		  
				</c:when>
				<c:otherwise>
					   <input id="enable" name="enable" type="hidden" value="">
				</c:otherwise>
			  </c:choose>
			 
			</dl>
		  </div>
		  
		  <div class="clear-all"></div>
		  <div class="float-right pTop20" id="actionButtonsDiv">
			<input type="button" class="btn noMinWidth" value="Reset" id="resetResourceBtn">
			&nbsp;
			<input type="button" class="btn noMinWidth" value="Save" id="saveOrUpdateResourceBtn">
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
