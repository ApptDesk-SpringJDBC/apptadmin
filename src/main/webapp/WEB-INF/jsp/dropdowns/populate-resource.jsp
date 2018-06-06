<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${inputType=='DropDown'}">
	<select id="resourceId" name="resourceId" onchange="populateServicessForSelectedResource();">
		<c:if test="${fn:length(resourceTOList) gt 1}">
			<option value="-1">All ${homeBean.displayNamesTO.resources_name}</option>
		</c:if>
		<c:forEach var="resourceTO"  items="${resourceTOList}">
			<option value="${resourceTO.id}">${resourceTO.dropDownDisplayName}</option>
		</c:forEach>
		<c:if test="${fn:length(resourceTOList) eq 1}">
			<script type="text/javascript">
				populateServicessForSelectedResource();
			</script>
		</c:if>
	</select>
</c:if>

<c:if test="${inputType=='MultiSelec_DropDown'}">
	<select id="${resourceDropDownId}" name="${resourceDropDownId}" onchange="populateServicessForSelectedResource();"
		size="${fn:length(resourceTOList) gt 8 ? (fn:length(resourceTOList)/2) : 5}" style='height:160px;' multiple="true">
		<c:if test="${fn:length(resourceTOList) gt 1}">
			<option value="-1" selected="true">All ${homeBean.displayNamesTO.resources_name}</option>
		</c:if>
		<c:forEach var="resourceTO"  items="${resourceTOList}">
			<option value="${resourceTO.id}">${resourceTO.dropDownDisplayName}</option>
		</c:forEach>
		<c:if test="${fn:length(resourceTOList) eq 1}">
			<script type="text/javascript">
				populateServicessForSelectedResource();
			</script>
		</c:if>
	</select>
</c:if>