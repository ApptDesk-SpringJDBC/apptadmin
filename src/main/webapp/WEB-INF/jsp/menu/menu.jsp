<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<div id="navbar">
	<ul id="nav">

		<%--  Home Menu  ----%>		
		<c:set value="${fn:contains(privilegedPageNames,'home')}" var="result" />
		<li class="level1">
			<c:if test="${result}">
				<a href="home.html">Home</a>
			</c:if> 
			<c:if test="${!result}">
				<a href="javascript:" class="disabled">Home</a>
			</c:if>
		</li>
	
		<%-- Appointments Menu  ----%>
		<li class="level1">
			<a href="javascript:">Appointments 
				<img src="static/images/down-arrow.png" width="8" height="6">
			</a>
			<ul>
				<div class="wrapper">
				<c:set value="${fn:contains(privilegedPageNames,'daily-calendar')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" class="menuCal" onClick = "loadCalendarPage(true,this);" title="Daily Calendar" type="daily" active="false">Daily Calendar</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Daily Calendar</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" class="menuCal" onClick = "loadCalendarPage(true,this);" title="Weekly Calendar" type="weekly" active="false">Weekly Calendar</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Weekly Calendar</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" class="menuCal" onClick = "loadCalendarPage(true,this);" title="Monthly Calendar" type="monthly" active="false">Monthly Calendar</a>
					</c:if>
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Monthly Calendar</a>
					</c:if>		
				</li>
				<c:set value="${fn:contains(privilegedPageNames,'appt-table-view')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadTablePrintView(true);">Table/Print View</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Table/Print View</a>
					</c:if>
				</li>				
			   </div>
			</ul>
		</li>

		<%-- Reports   ----%>
		<li class="level1">
			<a href="javascript:">Reports 
				<img src="static/images/down-arrow.png" width="8" height="6">
			</a>
			<ul>
				<div class="wrapper">
				<c:set value="${fn:contains(privilegedPageNames,'appointment-reports')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadReportsPageWithAutoSelect(true,'APPOINTMENTS_REPORT');">Appointments Report</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Appointments Report</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadReportsPageWithAutoSelect(true,'AUTOMATIC_EMAIL_REPORT');">Automatic Email Report</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Automatic Email Report</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadReportsPageWithAutoSelect(true,'APPOINTMENTS_SUMMARY');">Appointments Summary</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Appointments Summary</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadReportsPageWithAutoSelect(true,'APPOINTMENT_STATUS_TOTAL');">Appointment Status Total</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Appointment Status Total</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadReportsPageWithAutoSelect(true,'PLEDGE_REPORT');">Pledge Report</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Pledge Report</a>
					</c:if>		
				</li>				
			   </div>
			</ul>
		</li>
		
		<%-- Search   ----%>
		<li class="level1">
			<a href="javascript:">Search 
				<img src="static/images/down-arrow.png" width="8" height="6">
			</a>
			<ul>
				<div class="wrapper">
				<c:set value="${fn:contains(privilegedPageNames,'search-appointment')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadSearchPageWithAutoSelect(true,'APPOINTENT_DETAILS');">Appointment Search</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Appointment Search</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadSearchPageWithAutoSelect(true,'CUSTOMER_DETAILS');">Client Search</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Client Search</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadSearchPageWithAutoSelect(true,'HOUSEHOLD_INFO_DETAILS');">Household Search</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Household Search</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadSearchPageWithAutoSelect(true,'CUSTOMER_ACTIVITY_DETAILS');">Client Activity</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Client Activity</a>
					</c:if>		
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadSearchPageWithAutoSelect(true,'PLEDGE_DETAILS');">Pledge Search</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Pledge Search</a>
					</c:if>		
				</li>				
			   </div>
			</ul>
		</li>
		
		<!-- Locations Menu  ---->
		<li class="level1">
			<a href="javascript:">Locations 
				<img src="static/images/down-arrow.png" width="8" height="6">
			</a>
			<ul>
				<div class="wrapper">
				<c:set value="${fn:contains(privilegedPageNames,'locations')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadLocationPage(true);">${displayNames.locationsName}</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">${displayNames.locationsName}</a>
					</c:if>
				</li>
				<c:set value="${fn:contains(privilegedPageNames,'closed-day')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" class="disabled">Setup Holidays / Closed Days</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Setup Holidays / Closed Days</a>
					</c:if>
				</li>				
			   </div>
			</ul>
		</li>

		<!-- Physicians Menu  ---->
		<li class="level1">
			<a href="javascript:">${displayNames.resourcesName}
				<img src="static/images/down-arrow.png" width="8" height="6">
			</a>
			<ul>
				<div class="wrapper">
				<c:set value="${fn:contains(privilegedPageNames,'physicians')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadResourcePage(true);">View/Edit/Add</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">View/Edit/Add</a>
					</c:if>
				</li>

				<c:set value="${fn:contains(privilegedPageNames,'edit-weekly-hours')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadResourceEditHoursDataRangePage(true);">Edit Hours - Date Range</a>
					</c:if> <c:if test="${!result}">
						<a href="javascript:" class="disabled">Edit Hours - Date Range</a>
					</c:if>
				</li>
				
				<c:set value="${fn:contains(privilegedPageNames,'edit-specific-date')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadResourceEditHoursOneDataPage(true);">Edit Hours - One Date</a>
					</c:if> <c:if test="${!result}">
						<a href="javascript:" class="disabled">Edit Hours - One Date</a>
					</c:if>
				</li>

				<c:set value="${fn:contains(privilegedPageNames,'edit-calendar')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" class="disabled">Edit Hours - Using Calendar</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Edit Hours - Using Calendar</a>
					</c:if>
				</li>
				</div>
			</ul>
		</li>

		<!-- Service Menu  ---->
		<c:set value="${fn:contains(privilegedPageNames,'services')}" var="result" />
		<li class="level1">
			<c:if test="${result}">
				<a href="javascript:" onClick = "loadServicePage(true);">${displayNames.servicesName}</a>
			</c:if> 
			<c:if test="${!result}">
				<a href="javascript:" class="disabled">${displayNames.servicesName}</a>
			</c:if>
		</li>


		<!-- Users Menu  ---->
		<li class="level1">
			<a href="javascript:">Users 
				<img src="static/images/down-arrow.png" width="8" height="6">
			</a>
			<ul>
				<div class="wrapper">
				<c:set value="${fn:contains(privilegedPageNames,'users')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadUsersPage(true);">User Details</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">User Details</a>
					</c:if>
				</li>
				<li class="level2">
					<a href="changepassword.html">Change Password</a>
				</li>
				 
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadUserActivityLogPage(true);">User Activity Log</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">User Activity Log</a>
					</c:if>
				</li>
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadPrivilegeSettingsPage(true);">Privilege Settings</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Privilege Settings</a>
					</c:if>
				</li>
				</div>
			</ul>
		</li>


		<!-- IVR Menu  ---->
		<li class="level1">
			<a href="javascript:">IVR 
				<img src="static/images/down-arrow.png" width="8" height="6">
			</a>
			<ul>
				<div class="wrapper">
				<c:set 	value="${fn:contains(privilegedPageNames,'call-report')}" var="result" />
				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" onClick = "loadCallReportPage(true,'inbound');">Call Report</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Call Report</a>
					</c:if>
				</li>

				<li class="level2">
					<c:if test="${result}">
						<a href="javascript:" class="disabled">Reminders</a>
					</c:if> 
					<c:if test="${!result}">
						<a href="javascript:" class="disabled">Reminders</a>
					</c:if>
				</li>
				</div>
			</ul>
		</li>

		<li class="level1">
			<a href="help.html">Help</a>
		</li>
	</ul>


	<div class="version">Version ${homeBean.userLoginResponse.versionNumber}</div>
</div>
