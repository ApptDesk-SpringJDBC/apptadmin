<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form name="privilegeSettingsForm" id="privilegeSettingsForm" method="post">
	<input type="hidden" id="selectedAccessPrivilege" name="selectedAccessPrivilege">
	<div class="options">
		<dl>	
			<dt>
				<span class="float-left txt-bold">Previlege Details</span>
			</dt>
			<dd></dd>
		</dl>
		<div class="clear-all"></div>
	</div>
	
	<c:set var = "index" value="0"/>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="main-table">
		<c:forEach var="jspPagesPrivileges" items="${privilegeSettingResponse.privilegeSetting}">
		  <tr>
			<c:set var="key" value="${jspPagesPrivileges.key}" />				
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_LOCATIONS',displayNames.locationsName)}" />
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_LOCATION',displayNames.locationName)}" />
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_RESOURCES',displayNames.resourcesName)}" />
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_RESOURCE',displayNames.resourceName)}" />
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_SERVICES',displayNames.servicesName)}" />
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_SERVICE',displayNames.serviceName)}" />
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_CUSTOMERS',displayNames.customersName)}" />
			<c:set var = "key" value = "${fn:replace(key, 'LABEL_CUSTOMER',displayNames.customerName)}" />
			<th colspan="2">${key}</th>
		  </tr>
			
		 <c:forEach items="${jspPagesPrivileges.value}" var="jspPageNames" varStatus="status">
		  <tr class="${status.index % 2 == 0 ? '' : 'altColor'}">				 
			<td width="40%">
				<c:set var="jspPages" value="${jspPageNames.jspPages}" />
				<c:set var="updatedPagesTitle" value="${jspPageNames.pagesTitle}" />				
				<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_LOCATIONS',displayNames.locationsName)}" />
     			<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_LOCATION',displayNames.locationName)}" />
				<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_RESOURCES',displayNames.resourcesName)}" />
				<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_RESOURCE',displayNames.resourceName)}" />
				<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_SERVICES',displayNames.servicesName)}" />
     			<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_SERVICE',displayNames.serviceName)}" />
				<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_CUSTOMERS',displayNames.customersName)}" />
				<c:set var = "updatedPagesTitle" value = "${fn:replace(updatedPagesTitle, 'LABEL_CUSTOMER',displayNames.customerName)}" />
				${updatedPagesTitle}
				<input type="hidden" name="privilegePageMappingList[${index}].pageName" value="${jspPages}"/>
			</td>
			<td>
				<c:set var="checked" value="" />
				<c:set var = "jspPagesArr" value = "${fn:split(jspPages, ',')}" />
				<c:forEach var="jspPage" items="${jspPagesArr}">
				  <c:if test="${fn:contains(privilegePageMappingResponse.privilegeNames,jspPage)}">
						<c:set var="checked" value="checked" />
					</c:if>
				</c:forEach>
				<input type="checkbox" name="privilegePageMappingList[${index}].selected" ${checked} class="enable"/>
			</td>
		  </tr>
		  <c:set var = "index" value="${index+1}"/>
		</c:forEach>

		</c:forEach>
	</table>
	<div class="center-align">
		<input type="button" id="privilegeSettingsAddSubmit" class="btn" value="Save" onclick="onPrivilegeSettingsSaveClick()"> 
		<input type="button" id="" class="btn" value="Cancel" onclick="onPrivilegeSettingsCancelClick()"> 
	</div>
</form>