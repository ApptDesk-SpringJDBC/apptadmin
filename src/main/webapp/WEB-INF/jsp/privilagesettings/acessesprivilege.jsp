<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title>Privilege Settings</title>
<script type="text/javascript" src="static/js/validation/privilagesettings/privilegeSettingsFormValidation.js?version=7.0"></script>
 
<h1>Privilege Settings</h1>
<br/>

<div id="privilegeSettingsErrorMsg" class="error"></div>

<div class="search-bar">
	<select id="selectedAccess" name="selectedAccess" style="width:200px" class="leftFloat marginLTen paddingAll selectChangeClass">
		<option value="-1"> Select Access Privilege </option>
		<c:forEach var="accessPrivilege" items="${accessPrivilegeResponse.accessPrivilegeList}">
			<option value='${accessPrivilege.id}'> ${accessPrivilege.privilege}</option>
		 </c:forEach>
	</select>
</div>

<div id="privilegeSettingsDiv">

</div>