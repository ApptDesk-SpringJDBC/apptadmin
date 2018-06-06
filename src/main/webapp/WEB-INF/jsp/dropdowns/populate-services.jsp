<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<select id="${serviceDropDownId}" name="${serviceDropDownId}">
		<c:if test="${fn:length(serviceTOList) gt 1}">
				<option value="-1">All  ${homeBean.displayNamesTO.services_name}</option>
		</c:if>
		<c:forEach var="serviceTO"  items="${serviceTOList}">
			<option value="${serviceTO.id}">${serviceTO.service_name_online}</option>
		</c:forEach>
	</select>