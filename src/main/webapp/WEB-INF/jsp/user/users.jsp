<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>Users</title>
<script type='text/javascript' src="static/js/validation/user/userFormValidation.js?version=1.6"></script>
</head>

<body>
<!-- Body starts -->
<h1>Users</h1>

<!-- New HTML starts here -->
<div> 
      <!-- Left section starts -->
    <div style="width:50%" class="float-left"> 
      <div class="pageSubHeader">
        <div class="float-left txt-bold">User Details (Full Name/User Name/Access Level)</div>
        <div class="float-right">
          <input type="submit" class="btn" value="Add User" id="addUserReqBtn">
        </div>
        <div class="clear-all"></div>
      </div>
      <div class="dragDivContainer">
        <c:forEach items="${users.userList}" var="user" varStatus="loop">
			<c:set var="changeDivClassId" value="changeDivClass${user.userLoginId}"/>
			<div id="${changeDivClassId}" class="${loop.index==0 ? 'dragDiv dragDivSelected' : 'dragDiv'}" style="cursor: pointer" 
				onclick="loadScheduledUserEditData('${user.userLoginId}');">				
				${user.firstName} ${(not empty user.lastName) ? user.lastName : '' }
				${(not empty user.username) ? '/' : '' } ${(not empty user.username) ? user.username : '' }
				${(not empty user.accessLevel) ? '/' : '' } ${(not empty user.accessLevel) ? user.accessLevel : '' }
			</div>
		  </c:forEach>	
		  <c:set var="userLoginId" value="0"/>
		  <c:if test="${fn:length(users.userList)>0}">
				<c:set var="userLoginId" value="${users.userList[0].userLoginId}"/>			
		  </c:if>
		  <script type="text/javascript">	
				loadScheduledUserEditData('${userLoginId}');
		  </script>
      </div>
	  <div class="clear-all"></div>      
    </div>
    <!-- Left section ends --> 
    
    <!-- Right section starts -->
    <div class="float-right" style="width:49%">
      <div class="float-left txt-bold">Edit User</div>
      <div class="required-indicator float-right"> <span class="required">*</span>indicates required field </div>
      <div class="clear-all"></div>
      <div class="addEditSection">
        <div class="float-right"><input type="submit" class="btn noMinWidth" value="Delete User" id="deleteOrUndeleteUser"></div>
        <div>
	<c:if test="${not empty dynamicFieldDisplayData.dynamicFieldDisplay && fn:length(dynamicFieldDisplayData.dynamicFieldDisplay)>0}">
	  <form id="saveOrUpdateForm" action="" method="post">
			<input id="userLoginId" name="userLoginId" type="hidden" value="">
          <dl>
			<c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['firstName'])
						&& (dynamicFieldDisplayData.dynamicFieldDisplay['firstName'].dispay eq 'Y')}">
				   <dt> <span class="required">*</span>First Name </dt>
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
				   <dt> <span class="required">*</span>Last Name </dt>
				  <dd>
					<input id="lastName" name="lastName" type="text" value="">
					<div class="error" id="lastName_error"></div>	 
				  </dd>
				</c:when>
				<c:otherwise>
				   <input id="lastName" name="lastName" type="hidden" value="">
				</c:otherwise>
			</c:choose>  
			
			<c:set var="userNameValid" value="Y"/>
			<c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['username'])
						&& (dynamicFieldDisplayData.dynamicFieldDisplay['username'].dispay eq 'Y')}">
				  <c:set var="userNameValid" value="N"/>
				  <dt> <span class="required">*</span>User Name </dt>
				  <dd>
					<input id="username" name="username" type="text" value="" onchange="validateUserName();">
					<div class="error" id="username_error"></div>	 
				  </dd>
				  <div id="passwordAddDetails" style="display:none;">
					  <dt><span class="required">*</span>Password:</dt>
					  <dd>
						<input id="passwordData" type="password" value="">
						<div class="error" id="passwordData_error"></div>	
					  </dd>
					  <div class="clear-all error">Password should contain :
							Minimum 6 characters, atleast 1 uppercase letter, atleast 1 lowercase letter, atleast 1 digit, atleast 1 splchar(@#$%-_ ) and no username</div>
					  <br />
					  <div>
							Password can contain <b class="error"> @#$%-_ </b> special
							characters only!
						</div>
					</div>				
				</c:when>
				<c:otherwise>
				   <input id="username" name="username" type="hidden" value="">
				</c:otherwise>
			</c:choose> 
				<input id="userNameValid" type="hidden" value="${userNameValid}">
				
			<c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['contactEmail'])
						&& (dynamicFieldDisplayData.dynamicFieldDisplay['contactEmail'].dispay eq 'Y')}">
				   <dt> <span class="required">*</span>Contact Email</dt>
				  <dd> 
					<input id="contactEmail" name="contactEmail" type="text" value="">
					<div class="error" id="contactEmail_error"></div>	 
				  </dd>
				</c:when>
				<c:otherwise>
				   <input id="contactEmail" name="contactEmail" type="hidden" value="">
				</c:otherwise>
			</c:choose> 
          
			<c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['contactPhone'])
						&& (dynamicFieldDisplayData.dynamicFieldDisplay['contactPhone'].dispay eq 'Y')}">
				<dt> <span class="required">*</span>Contact Phone</dt>
				<dd> 
					<input id="contactPhone1" name="contactPhone1" class="phone noMinWidth" type="text" value="" maxlength="3">
					<input id="contactPhone2" name="contactPhone2" class="phone noMinWidth" type="text" value="" maxlength="3">
					<input id="contactPhone3" name="contactPhone3" class="phone1 noMinWidth" type="text" value="" maxlength="4">
					<div class="error" id="contactPhone_error"></div>	
				</dd>
			</c:if>
            <input id="contactPhone" name="contactPhone" type="hidden" value="">
			
			<c:choose>
				<c:when test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['accessLevel'])
						&& (dynamicFieldDisplayData.dynamicFieldDisplay['accessLevel'].dispay eq 'Y')}">         
					<dt class="clear-all"> <span class="required">*</span>Administrative Privilege </dt>
					<dd class="noWidth">
					  <input name="accessLevel" class="noWidth" onchange="hideLocationAndResourceDropDowns()" type="radio" value="Super User">
					  Super User<br>
					  <input name="accessLevel" class="noWidth" onchange="hideLocationAndResourceDropDowns()" type="radio" value="Administrator">
					  Administrator<br>
					  <input name="accessLevel" class="noWidth" onchange="hideLocationAndResourceDropDowns()" type="radio" value="Manager">
					  Manager<br>
					  <input name="accessLevel" class="noWidth" onchange="enableLocationDropDown()" type="radio" value="Location">
					  ${displayNames.locationName} <br>
					  <select id="locationIdsStr" style="height:80px;display:none;" class="noWidth" multiple="multiple" 
						size="${fn:length(locationResponse.locationList) gt 8 ? (fn:length(locationResponse.locationList)/2) : 5}">
						<c:if test="${fn:length(locationResponse.locationList) gt 1}">
							<option value="-1">Select ${displayNames.locationName}</option>
						</c:if>
						<c:forEach items="${locationResponse.locationList}" var="location">
							<option value="${location.locationId}">${location.locationNameOnline}</option>
						</c:forEach>
					  </select>
					  <input name="accessLevel" class="noWidth" onchange="enableResourceDropDown()" type="radio" value="Intake">
					  ${displayNames.resourceName} <br>
					  <select id="resourceIdsStr" style="height: 100%;display:none;" class="noWidth" multiple="multiple" 
						size="${fn:length(resourceResponse.resourceList) gt 8 ? (fn:length(resourceResponse.resourceList)/2) : 5}">
						<c:if test="${fn:length(resourceResponse.resourceList) gt 1}">
							<option value="-1">Select ${displayNames.resourceName}</option>
						</c:if>
						<c:forEach items="${resourceResponse.resourceList}" var="resource">
							<option value="${resource.resourceId}">${resource.resourceName} - ${resource.locationName}</option>
						</c:forEach>
					  </select>
					  <input name="accessLevel" class="noWidth" onchange="hideLocationAndResourceDropDowns()" type="radio" value="Scheduler">
					  Scheduler<br>
					  <input name="accessLevel" class="noWidth" onchange="hideLocationAndResourceDropDowns()" type="radio" value="Read Only">
					  Read Only<br>
					  <div class="error" id="accessLevel_error"></div>
					</dd>
				</c:when>
				<c:otherwise>
				   <input id="accessLevel" name="accessLevel" type="hidden" value="">				   
				</c:otherwise>
			</c:choose> 
			<input id="locationIds" name="locationIds" type="hidden" value="">
			<input id="resourceIds" name="resourceIds" type="hidden" value="">
				
			<c:set var="changePasswordEnabled" value="N"/>
			<c:if test="${(not empty dynamicFieldDisplayData.dynamicFieldDisplay['changePassword'])
						&& (dynamicFieldDisplayData.dynamicFieldDisplay['changePassword'].dispay eq 'Y')}">      
				<c:set var="changePasswordEnabled" value="Y"/>
				<div id="changePasswordDiv">
					<dt>Change Password</dt>
					<dd><input id="changePasswordCheckbox" type="checkbox" value=""  class="noWidth" onchange="changePasswordChanged()"></dd>
					<div id="changePasswordDetails" style="display:none;">
					  <dt>New Password:</dt>
					  <dd>
						<input id="newPassword" type="password" value="">
						<div class="error" id="newPassword_error"></div>	
					  </dd>
					  <dt>Confirm New Password:</dt>
					  <dd>
						<input id="confirmPassword" type="password" value="">
						<div class="error" id="confirmPassword_error"></div>	
					  </dd>
					  <div class="clear-all"></div>
					  <div class="clear-all error">Password should contain :
							Minimum 6 characters, atleast 1 uppercase letter, atleast 1 lowercase letter, atleast 1 digit, atleast 1 splchar(@#$%-_ ) and no username</div>
					  <br />
					  <div>
							Password can contain <b class="error"> @#$%-_ </b> special
							characters only!
					  </div>
					</div>
				</div>
			</c:if>		
			<input id="changePasswordEnabled" type="hidden" value="${changePasswordEnabled}">
			<input id="passwordUpdate" name="passwordUpdate" type="hidden" value="N">
			<input id="password" name="password" type="hidden" value="">
			<div class="clear-all"></div>
          </dl>
        </div>
		
		<div class="clear-all"></div>
		  <div class="float-right pTop20" id="actionButtonsDiv">
			<input type="button" class="btn noMinWidth" value="Reset" id="resetServiceBtn">
			&nbsp;
			<input type="button" class="btn noMinWidth" value="Save" id="saveOrUpdateUserBtn">
		  </div>
		  <div class="clear-all"></div>
		</div>
		</form>
	</c:if>
	<div class="clear-all"></div>
    </div>
    <!-- Right section ends -->   
	<div class="clear-all"></div>
</div>
<!-- New HTML ends here --> 

<!-- Body ends --> 
</body>
</html>
