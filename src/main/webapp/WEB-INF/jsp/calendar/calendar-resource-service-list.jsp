<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${resourceServiceResponse.status=='true'}">
	<c:forEach var="resourceService" items="${resourceServiceResponse.resourceServices}" varStatus="resourceIndex">
		<c:set var="resource" value="${resourceService.key}"/>
		<c:set var="serviceList" value="${resourceService.value}"/>

        <c:choose>
			<c:when	test="${calendarType == 'daily' && empty param.selectedResouceIds}">
			<c:set var="resChecked" value="${resourceIndex.count<=3 ? 'checked' : ''}"/>
			</c:when>
			<c:otherwise>
				<c:set var="contains" value="false" />
				<c:set var="ids" value="${fn:split(param.selectedResouceIds, ',')}" />
				<c:forEach var="id" items="${ids}">
					<c:if test="${id eq resource.resourceId}">
						<c:set var="contains" value="true" />
					</c:if>
				</c:forEach>
				<c:set var="resChecked" value="${contains? 'checked' : ''}"/>
			</c:otherwise>
		</c:choose>
		<c:if test="${calendarType == 'weekly'}"><c:set var="resChecked" value="${resourceIndex.count<=1 ? 'checked' : ''}"/></c:if>

		<div class="calendarDoctor">
			<c:if test="${calendarType == 'daily'}">
				<input id="selectedResourceIdsList" name="resourceIds" type="checkbox" value="${resource.resourceId}"  ${resChecked}>
			</c:if>
			<c:if test="${calendarType == 'weekly'}">
				<input class="selectedResourceIdsList" name="resourceIds" onclick="weeklyResourceChange(this)" type="checkbox" value="${resource.resourceId}"  ${resChecked}>
			</c:if>
			<c:if test="${calendarType == 'monthly'}">
				<input id="selectedResourceIdsList" name="resourceIds" type="checkbox" onclick="monthlyResourceChange(this)" value="${resource.resourceId}"  ${resChecked}>
			</c:if>
		  <label  for="selectedResourceIdsList${resourceIndex.count}" resource-index="${resourceIndex.count}" id="${resource.resourceId}" class="DocColor${resourceIndex.count}">${resource.resourceName} </label>
		  <select id="resource_${resource.resourceId}_serviceId" name="resource_${resource.resourceId}_service" class="DocServices" onchange="onResourceServiceChange();">
		    <c:forEach var="service" items="${serviceList}" varStatus="serviceIndex">
				<option value="${service.serviceId}_${service.blocks}">${service.serviceNameOnline} - ${service.duration}</option>
			</c:forEach>
		  </select>
		  <div class="clear-all"></div>
		</div>
	</c:forEach>
</c:if>
<c:if test="${resourceServiceResponse.status=='false'}">
	Error!
</c:if>