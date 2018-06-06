<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
<title>Welcome</title>
<script type="text/javascript" src="static/js/graphs/highcharts/highcharts.js"></script>
<script type="text/javascript" src="static/js/graphs/highcharts/highcharts-more.js"></script>
<script type="text/javascript" src="static/js/graphs/highcharts/modules/exporting.js"></script>

<script src="static/js/graphs/highcharts/solid-gauge.src.js"></script>

<script type="text/javascript" src="static/js/graphs/graphs.js?version=1.6"></script>
<script type="text/javascript" src="static/js/validation/home/homeHelper.js?version=1.0"></script>
<script type="text/javascript" src="static/js/validation/home/socialMedia.js?version=1.0"></script>

</head>

<body>
	<!-- Header starts -->	
	<!-- Header ends -->
	<!-- Body starts -->
	
			<c:if test="${not empty homeBean.userLoginResponse && not empty client}">			
                <!-- Welcome section starts here -->
                <div class="dailyCal_head" style="text-align:center">		
                  <div class="cal_head-new">Welcome ${homeBean.userLoginResponse.firstName} ${homeBean.userLoginResponse.lastName}</div>
				 
				  <div class="socialmedia">	
						<center>
							<a href="javascript:doNothing()" class="imagedisable" style="text-decoration:none;">
								<img src="static/images/social_media/twitter.ico" width="34" alt="Twitter" class="twitter"/>
							</a> &nbsp;
							<a href="javascript:doNothing()" class="imagedisable" style="text-decoration:none;">
								<img src="static/images/social_media/facebook.ico" width="34" alt="Facebook" class="facebook"/>
							</a> &nbsp;
							<a href="javascript:doNothing()" class="imagedisable" style="text-decoration:none;">
								<img src="static/images/social_media/linkedin.ico" width="34" alt="Linked In" class="linkedin"/>
							</a>
						</center>
                        <p style="color:rgb(0, 0, 161)"><b>Post instant messages to social media</b></p>
				  </div>
				  

                  <div class="float-right">
                    <!-- Success Message new starts -->
                    <div class="home_openstatus" id="home_openstatusDiv" style="display:none">Scheduler is Currently Open</div>
                    <!-- Success Message new ends -->
                    <!-- Close Message new starts -->
                      <div class="home_closestatus"  id="home_closestatusDiv" style="display:none">Scheduler is Currently Closed</div>
                    <!-- Close Message new ends -->
                  
                    <!-- Succesfull message-->
					<div class="home_green" id="schedularValChangeResponce"></div>
                    
					<input type="hidden" id="scheduler_closed" value="${apptSysConfigs.schedulerClosed}"/>
				 </div>
			<div class="clear-all"></div>
		   </div>
                  <!-- Welcome section ends here -->    
				  
            <div class="clear-all"></div>
				<div class="homeMainContainer"> 
                
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody>
                  <tr>
                    <td width="${(client.redirectURL!=null && fn:contains(client.redirectURL,'ivrapptv2')) ? '70%' : '100%'}" style="vertical-align: top;">
					<h2 class="home_h">${client.clientName}</h2>

					<table border="0" cellspacing="0" cellpadding="0" class="main-table-home homeContent" style="width:100% !important">
						<tr>
							<td width="50%">Address:</td>
							<td>
								${client.address}
								${client.address2 !='' ? ',' : '' } ${client.address2}
								${client.city !='' ? ',' : '' } ${client.city}
								${client.state!='' ? ',' : '' } ${client.state}
								${client.zip!='' ? ',' : '' } ${client.zip}
							</td>
						</tr>
						<tr class="altColor">
							<td>Admin Contact Name:</td>
							<td>${client.clientName}</td>
						</tr>
						<tr>
							<td>Admin Contact Email:</td>
							<td>${client.contactEmail}</td>
						</tr>
						<tr class="altColor">
							<td>Admin Contact Phone</td>
							<td>${client.contactPhone}</td>
						</tr>
					</table>


					<%--  Scheduler Info Starts --%>
					<c:set var="tdCount" value="1"/>
					<h2>Scheduler Info</h2>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">
					   <c:if test="${client.forwardUrl!=null && client.forwardUrl!=''}">
							<tr class="${tdCount % 2 == 0 ? 'altColor' : ''}">
								<td width="30%">Admin Site: </td>
								<td>${client.forwardUrl}</td>
							</tr>
							<c:set var="tdCount" value="${tdCount+1}"/>
					   </c:if>
					   <c:if test="${client.apptLink!=null && client.apptLink!=''}">
							<tr class="${tdCount % 2 == 0 ? 'altColor' : ''}">
								<td>Online Scheduler Link:</td>
								<td><a href="${client.apptLink}" target="_blank">${client.apptLink}</a></td>
							</tr>
							<c:set var="tdCount" value="${tdCount+1}"/>
					   </c:if>
					   <c:if test="${(client.clientDnis1!=null && client.clientDnis1!='' && client.clientDnis1!='NA.') 
						|| (client.clientDnis2!=null && client.clientDnis2!='' && client.clientDnis2!='NA.') 
					    || (client.clientDnis3!=null && client.clientDnis3!='' && client.clientDnis3!='NA.')}">
							<tr class="${tdCount % 2 == 0 ? 'altColor' : ''}">
								<td>Phone Scheduler Number:</td>
								<td>
									<c:if test="${(client.clientDnis1!=null && client.clientDnis1!='' && client.clientDnis1!='NA.')}">
										${client.clientDnis1}
									</c:if>
									<c:if test="${(client.clientDnis1!=null && client.clientDnis1!='' && client.clientDnis1!='NA.') 
										&& (client.clientDnis2!=null && client.clientDnis2!='' && client.clientDnis2!='NA.')}">
									 / 
									</c:if>
									<c:if test="${(client.clientDnis2!=null && client.clientDnis2!='' && client.clientDnis2!='NA.')}">
										${client.clientDnis2}
									</c:if>
									<c:if test="${((client.clientDnis1!=null && client.clientDnis1!='' && client.clientDnis1!='NA.') 
										&& (client.clientDnis3!=null && client.clientDnis3!='' && client.clientDnis3!='NA.')) 
										|| ((client.clientDnis2!=null && client.clientDnis2!='' && client.clientDnis2!='NA.') 
										&& (client.clientDnis3!=null && client.clientDnis3!='' && client.clientDnis3!='NA.'))}">
									 / 
									</c:if>
									<c:if test="${(client.clientDnis3!=null && client.clientDnis3!='' && client.clientDnis3!='NA.')}">
										${client.clientDnis3}
									</c:if>
								</td>
							</tr>
							<c:set var="tdCount" value="${tdCount+1}"/>
					   </c:if>
					   <c:if test="${client.directAccessNumber!=null && client.directAccessNumber!='' && client.directAccessNumber!='NA.'}">
						   <c:if test="${client.extension!=null && client.extension!=''}">
								<tr class="${tdCount % 2 == 0 ? 'altColor' : ''}">
									<td>Direct Access Phone # :</td>
									<td>${client.directAccessNumber} Ext ${client.extension}</td>
								</tr>
								<c:set var="tdCount" value="${tdCount+1}"/>
						   </c:if>
					    </c:if>
					</table>
                    </td>
					<c:if test="${(client.redirectURL!=null && fn:contains(client.redirectURL,'ivrapptv2'))}" >
						<td style="text-align:center; vertical-align:top">
							<a title="Virtual Phone Tester" onclick="window.open('${protocalHostPort}/ivrsimulator/login-for-client.html?clientcode=${homeBean.userLoginResponse.clientCode}','Virtual Phone Tester','scrollbars=no,menubar=no,height=600,width=310,screenx=500,screeny=200');" href="javascript:void(0);">
								<img src="static/images/iphone-small.png" width="160" height="340" alt="Virtual Phone"/>
							</a>
							<div style="color:rgb(0, 0, 161)"><center><b>Virtual Phone tester</b></center></div>                    
						</td>
					</c:if>
                    </tr>
                    </table>

					<h2>${displayNames.locationsName}</h2>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">
						<c:forEach items="${homePageResponse.locationList}" var="location" varStatus="loop">
						<tr class="${loop.count%2 == 0 ? 'altColor' : ''}">
							<td>
							<ul>							
								<li>
									  ${location.locationNameOnline} 
									  ${location.address!='' ? ',' : '' } ${location.address}
									  ${location.city !='' ? ',' : '' } ${location.city}
									  ${location.state!='' ? ',' : '' } ${location.state}
								</li>								
							</ul>
							</td>
						</tr>
						</c:forEach>		
					</table>
					<h2>${displayNames.resourcesName}</h2>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">
						<c:forEach items="${homePageResponse.resourceList}" var="resource" varStatus="loop">
						<tr class="${loop.count%2 == 0 ? 'altColor' : ''}">
							<td><ul>
									<li>${resource.firstName} 
										${resource.lastName}
										(${resource.locationName})</li>
								</ul>
							</td>
						</tr>
						</c:forEach>
					</table>
					<h2>${displayNames.servicesName}</h2>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">
						<c:forEach items="${homePageResponse.serviceList}" var="service" varStatus="loop">
						<tr class="${loop.count%2 == 0 ? 'altColor' : ''}">
							<td><ul>
									<li>${service.serviceNameOnline} (${service.duration})</li>
								</ul>
							</td>
						</tr>
						</c:forEach>
					</table>
				</div>


	<div class="homeMainContainerGraph">
           <!-- Scheduler section opens -->
				  <c:set value="${('Administrator' eq homeBean.userLoginResponse.accessLevel) || ('Super User' eq homeBean.userLoginResponse.accessLevel)}"  var="result" />
				  <!-- Administrator   or Super User loggedin-->
					<c:if test="${result}">
						<!-- show this line when the status is open -->
						<div id="opendSchedulerDiv" class="home_mainbuttons">
							<input   class="homebtn"  type="button" value="Close Scheduler Now for Maintenance"  onclick="closeScheduler();">
						</div>
						
						<!-- show this line when the status is open -->
						<div id="closedSchedulerDiv" class="home_mainbuttons">
							<input   class="homebtn"  type="button" value="Open Scheduler" onclick="openScheduler();">
						</div>
                   </c:if>
           <!-- Scheduler section ends --> 
                	
					<input type="hidden" value="${apptSysConfigs.oneApptPerTerm}" id="one_appt_per_term">
					<input type="hidden" value="Y" id="restrict_appt_window">
					<input type="hidden" value="${apptSysConfigs.restrictLocApptWindow}" id="restrict_loc_appt_window">

					<c:if test="${result}">
						<!-- Appointments season details starts -->
						<c:if test="${fn:contains(apptSysConfigs.oneApptPerTerm,'Y')}">
							<div class="error" id="term_start_date_Error"></div>
							<div class="error" id="term_end_date_Error"></div>
							<div class="error" id="no_appt_per_term_Error"></div>
							<div class="home_green" id="appt_per_term_Responce"></div>
							
							<c:set var="allow_Duplicate_Appts_services" value=""/>

							<c:forEach items="${homePageResponse.serviceList}" var="service">
								<c:set var="allow_Duplicate_Appts_services" value="${allow_Duplicate_Appts_services}<br/>${service.serviceNameOnline}"/>	
							</c:forEach>
							<c:if test="${allow_Duplicate_Appts_services!=null && fn:length(allow_Duplicate_Appts_services)>0}">
								<c:set var="allow_Duplicate_Appts_services" value="${fn:substring(allow_Duplicate_Appts_services,5,fn:length(allow_Duplicate_Appts_services))}" />
							</c:if>

                            <div class="home_impsection">								  
								  <div class="search-bar">
									<div><b>Allow number of appointments per season: </b></div>
									<table border="1">
										<c:if test="${allow_Duplicate_Appts_services!=null && fn:length(allow_Duplicate_Appts_services)>0}">
											<tr>
												<td align="center">${displayNames.servicesName}:</td>
												<td>${allow_Duplicate_Appts_services}</td>
											</tr>
										</c:if>
										<tr>
											<td>Season Start Date:</td>
											<td><input id="term_start_date" value="${apptSysConfigs.termStartDate}" style="width:auto" 
											${(apptSysConfigs.termStartDate eq now) ? 'disabled' : ''}/></td>
										</tr>
										<tr>
											<td>Season End Date:</td>
											<td><input id="term_end_date" value="${apptSysConfigs.termEndDate}" style="width:auto"/></td>
										</tr>
										<tr>
											<td>No of appts:</td>
											<td>
												<input id="no_appt_per_term" value="${apptSysConfigs.noApptPerTerm}" style="width:auto"/>
											</td>
										</tr>
									 </table>
									 <br/>
									 <center>
										<input class="btn" type="button" value="Save" onclick="updateApptPerSeasonDetails();"/>
									 </center>
								  </div>								  
							  </div>
						</c:if >
						<!-- Appointments season details ends -->

						<c:if test="${fn:contains(apptSysConfigs.restrictApptWindow,'Y') || fn:contains(apptSysConfigs.restrictApptWindow,'y')}">
							<div class="error" id="appt_start_dateError"></div>
							<div class="error" id="appt_end_dateError"></div>
							<div class="home_green" id="apptRestrictResponce"></div>
							
                            <div class="home_impsection">
								  <!-- Open online/phone scheduler for single location starts -->
								  <div class="search-bar">
									<div><b>Allow online/phone scheduling to book between these dates:</b></div><br/> 						
									Start Date  <input id="appt_start_date"  value="${apptSysConfigs.apptStartDate}" style="width:auto"/> &nbsp; 
									End Date   <input id="appt_end_date"    value="${apptSysConfigs.apptEndDate}" style="width:auto"/> &nbsp; 
									<br/><br/>
									<center><input class="btn" type="button" value="Save" onclick="updateApptRestrictDates();">	</center>					
								  </div>
								  <!-- Open online/phone scheduler for single location ends -->
							  </div>
						</c:if >
											
	
						<c:if test="${fn:contains(apptSysConfigs.restrictLocApptWindow,'Y') || fn:contains(apptSysConfigs.restrictLocApptWindow,'y')}">
							   <!-- Open online/phone scheduler for single location starts -->

							   <div class="error" id="loc_appt_start_dateError"></div>
							   <div class="error" id="loc_appt_end_dateError"></div>
							   <div class="error" id="loc_appt_days_dateError"></div>
							   <div class="home_green" id="loc_apptRestrictResponce"></div>

                              <div class="search-bar">
							  
								<form:form id="updateLocationsApptDates" commandName="locationsApptDatesRequest" action="updateLocationsApptDates.html" method="post" >
									<div><b>Allow online/phone scheduling to book between these dates for the ${displayNames.locationName} :</b></div><br/> 
															
									<input type="hidden" id="noOfLocations" value="${fn:length(homePageResponse.locationList)}"/>
									 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
										  <tr>
											<th width="50%">${displayNames.locationName}</th>
											<th width="25%" class="center-align">Start Date </th>
											<th width="25%" class="center-align">End Date  </th>
										  </tr>
										 <c:forEach items="${homePageResponse.locationList}" var="locationTo" varStatus="status">
											  <tr>
												<form:hidden path="locations[${status.index}].locationId" />
												<td>${locationTo.locationNameOnline} </td>
												<td class="center-align">
													<form:input path="locations[${status.index}].apptStartDate" id="appt_start_date_${status.index}" style="width:auto" />
												</td>
												<td class="center-align">
													<form:input path="locations[${status.index}].apptEndDate" id="appt_end_date_${status.index}" style="width:auto"/>
												</td>
											  </tr>			
										 </c:forEach>
										</table>
										<center> <input   class="btn"  type="button" value="Save" onclick="updateLocationsApptDates();"></center>
								</form:form>
								 
							  </div>
                              <!-- Open online/phone scheduler for single location ends -->						   
						</c:if >	
						
						<c:if test="${fn:contains(apptSysConfigs.restrictLocSerApptWindow,'Y') || fn:contains(apptSysConfigs.restrictLocSerApptWindow,'y')}">
							   <!-- Open online/phone scheduler for Service location window starts -->

							   <div class="error" id="loc_ser_appt_window_appt_start_dateError"></div>
							   <div class="error" id="loc_ser_appt_window_appt_end_dateError"></div>
							   <div class="error" id="loc_ser_appt_window_appt_days_dateError"></div>
							   <div class="home_green" id="loc_ser_appt_window_apptRestrictResponce"></div>

							<div class="search-bar">
									Select ${displayNames.locationName}
									<select id="serviceLocationApptDatesWindowSelectedLocationId"  STYLE="width:160px" onchange="loadServiceLocationApptDatesWindowForSelectedLocation()">
										<c:forEach items="${homePageResponse.locationList}" var="location" varStatus="loop">
											<option value="${location.locationId}"  ${location.locationId==loc_ser_appt_window_sel_loc ? 'selected' : '' }>${location.locationNameOnline}</option>
										</c:forEach>
									</select><br/><br/>
									
									<div  id="updateServiceLocationApptDatesWindowDiv" >	
										<script type="text/javascript">
											loadServiceLocationApptDatesWindowForSelectedLocation();
											<%--  restrict_loc_ser_appt_window.jsp page we are loading dynamically by invoking above JS functionality --%> 
										</script>
									</div>										
							  </div>
							<!-- Open online/phone scheduler for Service location window ends -->
						</c:if >

					</c:if >		

					<input type="hidden" id="funding_based_scheduler" value="${apptSysConfigs.fundingBasedScheduler}"/>
								
						<div class="error" id="locationError"></div>
						<div class="error" id="startDateError"></div>
						<div class="error" id="endDateError"></div>	
						
						<br/> 
						<div >
							Select ${displayNames.locationName}
							<select id="gaugeChartLocationId"  STYLE="width:160px">
								<c:forEach items="${homePageResponse.locationList}" var="location" varStatus="loop">
									<option value="${location.locationId}">${location.locationNameOnline}</option>
								</c:forEach>
							</select>
							&nbsp; <br/> <br/>
							Start Date  <input id="startDate" value="${defaultDate}"/> &nbsp;&nbsp;&nbsp;  End Date  <input id="endDate" value="${defaultDate}"/> 
						</div>	
						<br/>
						<div style="width: 600px; height: 200px; margin: 0 auto">
							<div id="booked_appts_container" style="width: 300px; height: 200px; float: left"></div>
							<div id="opened_appts_container" style="width: 300px; height: 200px; float: left"></div>
						</div>
						<br/>

						<hr/>
						<br/>
						<div >			
							Select ${displayNames.locationName}
							<select id="stackedChartLocationId"  STYLE="width:160px">
								<c:forEach items="${homePageResponse.locationList}" var="location" varStatus="loop">
									<option value="${location.locationId}">${location.locationNameOnline}</option>
								</c:forEach>
							</select>
							&nbsp;
							 
							Select ${displayNames.resourceName}
							<select  id="stackedChartResourceId" STYLE="width:160px">
								<option value="-1">ALL</option>
							</select>							 
						</div>	
						<br/> 
					<div id="stackedcharts" style="min-width: 310px; height: 400px; margin: 0 auto"></div>					
					<br/> 

					<hr/>
					<br/> 
					<div >
					<div class="error" id="dateError"></div>	
						Select ${displayNames.locationName}
						<select id="pieChartLocationId"  STYLE="width:160px">
							<c:forEach items="${homePageResponse.locationList}" var="location" varStatus="loop">
								<option value="${location.locationId}">${location.locationNameOnline}</option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
						Select Date  <input id="date" value="${defaultDate}"/> 
					</div>	
					<br/> 
					<div id="piecharts" style="min-width: 310px; height: 400px; margin: 0 auto"></div>		
					
				</div>

				<div class="clear-all"></div>
			</c:if>
	<!-- Body ends -->

	<!--Pop up section starts -->
		<div class="jqmWindow" id="ex2">Please wait...</div>
	<!--Pop up section ends -->

</body>
</html>
