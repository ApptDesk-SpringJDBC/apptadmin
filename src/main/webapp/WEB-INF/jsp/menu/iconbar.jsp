<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<style>
.opaque { filter: alpha(opacity=25); -moz-opacity:0.23; opacity=0.25 } 
</style>
	
	<!--  Home Icon ---->
	
	<c:set value="${fn:contains(privilegedPageNames,'home')}" var="result" />
	<c:if test="${result}">	
		<a href="home.html" title="Home">
			<img src="static/images/home.png" width="20" height="20" alt="Home">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Home">
			<img src="static/images/home.png" width="20" height="20" alt="Home">
		</a> 
	</c:if>
	
	<!--  Daily Calander Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'daily-calendar')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" class="menuCal" onClick = "loadCalendarPage(true,this);" title="Daily Calendar" type="daily" active="false">
			<img src="static/images/daily_cal.png" alt="Daily Calander">
		</a>
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Daily Calendar">
			<img src="static/images/daily_cal.png" alt="Daily Calander">
		</a>
	</c:if>
	
	<!--  Weekly Calendar Icon ---->	
	<c:set value="${fn:contains(privilegedPageNames,'weekly-calendar')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:"  class="menuCal" onClick = "loadCalendarPage(true,this);"  title="Weekly Calendar" type="weekly" active="false">
			<img src="static/images/weekly_cal.png" alt="Weekly Calendar">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Weekly Calender">
			<img src="static/images/weekly_cal.png" alt="Weekly Calender">
		</a> 
	</c:if>

	<!--  Monthly Calendar Icon ---->	
	<c:set value="${fn:contains(privilegedPageNames,'appt-monthly-calendar')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:"  class="menuCal" onClick = "loadCalendarPage(true,this);" title="Monthly Calendar" type="monthly" active="false">
			<img src="static/images/monthly_cal.png" alt="Monthly Calendar">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Monthly Calendar">
			<img src="static/images/monthly_cal.png" alt="Monthly Calender">
		</a> 
	</c:if>

	<!--  Table/Print View Icon ---->	
	<c:set value="${fn:contains(privilegedPageNames,'appt-table-view')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadTablePrintView(true);" title="Table/Print">
			<img src="static/images/print_cal.png" alt="Table/Print">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Table/Print">
			<img src="static/images/print_cal.png" alt="Table/Print">
		</a> 
	</c:if>

	<!--  Search Appointment Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'search-appointment')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadSearchPage(true);" title="Search Appointment">
			<img src="static/images/search-appt.png" width="18" height="20" alt="Search Appointment">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Search Appointment">
			<img src="static/images/search-appt.png" width="18" height="20" alt="Search Appointment">
		</a> 
	</c:if>
	

	<!--  Appointment Reports Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'appointment-reports')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadReportsPage(true);" title="Appointment Reports">
			<img src="static/images/appt-reports.png" width="22" height="20" alt="Appointment Reports">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Appointment Reports">
			<img src="static/images/appt-reports.png" width="22" height="20" alt="Appointment Reports">
		</a> 
	</c:if>

	<!--  Closed Day Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'closed-day')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" class="disabled" title="Holiday/Closed Day">
			<img src="static/images/closed-day.png" width="22" height="22" alt="Closed Day">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Holiday/Closed Day">
			<img src="static/images/closed-day.png" width="22" height="22" alt="Closed Day">
		</a> 
	</c:if>


	<!--  Locations Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'locations')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadLocationPage(true);" title="${displayNames.locationName}">
			<img src="static/images/locations.png" width="14" height="20" alt="${displayNames.locationName}">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="${displayNames.locationName}">
			<img src="static/images/locations.png" width="14" height="20" alt="${displayNames.locationName}">
		</a> 
	</c:if>	
		
	<!--  Physicians Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'physicians')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadResourcePage(true);" title="View/Edit/Add ${displayNames.resourceName}">
			<img src="static/images/physician.png" width="20" height="20" alt="View/Edit/Add ${displayNames.resourceName}">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="View/Edit/Add ${displayNames.resourceName}">
			<img src="static/images/physician.png" width="20" height="20" alt="View/Edit/Add ${displayNames.resourceName}">
		</a> 
	</c:if>		
	
	<!--  Edit Weekly Hours Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'edit-weekly-hours')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" class="disabled" title="Edit Hours - Date Range">
			<img src="static/images/week-edit.png" width="22" height="22" alt="Edit Hours - Date Range">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Edit Hours - Date Range">
			<img src="static/images/week-edit.png" width="22" height="22" alt="Edit Hours - Date Range">
		</a> 
	</c:if>	

	<!--  Edit Specific Date Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'edit-specific-date')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" class="disabled" title="Edit Hours - One Date">
			<img src="static/images/specific-date.png" width="22" height="21" alt="Edit Hours - One Date">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Edit Hours - One Date">
			<img src="static/images/specific-date.png" width="22" height="21" alt="Edit Hours - One Date">
		</a> 
	</c:if>	
	
	<!--  Edit Calendar Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'edit-calendar')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" class="disabled" title="Edit Calendar Hours">
			<img src="static/images/cal-edit.png" width="24" height="24" alt="Edit Calendar Hours">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Edit Calendar Hours">
			<img src="static/images/cal-edit.png" width="24" height="24" alt="Edit Calendar Hours">
		</a> 
	</c:if>	
	
	<!-- Services Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'services')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadServicePage(true);" title="${displayNames.serviceName}">
			<img src="static/images/services.png" width="22" height="22" alt="${displayNames.serviceName}">
		</a>
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="${displayNames.serviceName}">
			<img src="static/images/services.png" width="22" height="22" alt="${displayNames.serviceName}">
		</a>
	</c:if>
	
	<!-- Users Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'users')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadUsersPage(true);" title="Users">
			<img src="static/images/users.png" width="22" height="22" alt="Users">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Users">
			<img src="static/images/users.png" width="22" height="22" alt="Users">
		</a> 
	</c:if>
	
	<!-- Call Report Icon ---->
	<c:set value="${fn:contains(privilegedPageNames,'call-report')}" var="result" />
	<c:if test="${result}">
		<a href="javascript:" onClick = "loadCallReportPage(true,'inbound');" title="Call Report">
			<img src="static/images/call-report.png" width="22" height="22"	alt="Call Report">
		</a> 
	</c:if>
	<c:if test="${!result}">
		<a href="javascript:" class="disabled" title="Call Report">
			<img src="static/images/call-report.png" width="22" height="22"	alt="Call Report">
		</a> 
	</c:if>

	<a href="help.html" title="Help">
		<img src="static/images/help.png" width="22" height="22" alt="Help">
	</a>	