<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test ="${weeklyCalendarResponse.status && (fn:length(weeklyCalendarResponse.calendarDataList) gt 0)
	 && (fn:length(weeklyCalendarResponse.calendarDataList[0].calendarDataList) gt 0)}">
	 
	<script type="text/javascript" src="static/js/validation/calendar/calendarHelper.js?version=1.0"></script> 
	
	<script>
		$(document).ready(function () {
			$("input[name=resourceIds]").change(function(){
				resetCalendarWithAvailableDates();
			   var resourceId = $(this).prop('value');
			   if($(this).prop('checked')){
					$(".resourceId_"+resourceId).show();
			   } else {
					var selectedResourceIds = $('input:checkbox[name=resourceIds]:checked').map( function () {
						return $(this).val();
					}).get().join();
					if(selectedResourceIds!=""){
						$(".resourceId_"+resourceId).hide();
					} else {
						$(this).prop('checked',true)
					}
			   }
			});
		});
	</script>
	<c:set var="availableDates" value="${availableDates}"></c:set>
 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
	   <tbody>
		  <tr>
			 <th width="8%" class="center-align time">Time</th>
			 <c:forEach items="${weeklyCalendarResponse.calendarDataList}" var="resourceCalendarData" varStatus="resourceStatus">
				 <c:set var="weeklyDate" value="${resourceCalendarData.date.split('/')[1]}"></c:set>
				
				<c:set var="style" value=""/>
				<c:if test="${selectedResourceIds == resourceCalendarData.resourceId}">
					<c:set var="style" value="display"/>
				</c:if>

				<c:if test="${style ne 'display'}">
					<c:set var="style" value="style='display:none;'"/>
				</c:if>
					
				<th class="width7Col resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}" ${style}> ${resourceCalendarData.date} </th>
			 </c:forEach>
		  </tr>
		  <c:forEach var="i" begin="0" end="${fn:length(weeklyCalendarResponse.calendarDataList[0].calendarDataList)-1}">
			  <tr>
				 <td class="${i%2==0 ? 'dotdiv' : 'solid'} rline">${weeklyCalendarResponse.calendarDataList[0].calendarDataList[i].time}</td>
				 
				 <c:forEach items="${weeklyCalendarResponse.calendarDataList}" var="resourceCalendarData" varStatus="resourceStatus">
					 <c:set var="weeklyDate" value="${resourceCalendarData.date.split('/')[1]}"></c:set>
					<c:set var="rowspan" value="${resourceCalendarData.calendarDataList[i].rowSpan}"/>
					
					<c:set var="style" value=""/>				
					<c:forEach var="selectedResourceId" items="${selectedResourceIds}">
					  <c:if test="${selectedResourceId eq resourceCalendarData.resourceId}">
							<c:set var="style" value="display"/>
					  </c:if>
					</c:forEach>
					
					<c:if test="${style ne 'display'}">
						<c:set var="style" value="style='display:none;'"/>
					</c:if>
					
					<c:if test="${rowspan gt 0}">
						<c:choose>
							<c:when test="${resourceCalendarData.calendarDataList[i].apptStatus eq 'open'}">
								<c:set var="mapKey" value="${resourceCalendarData.resourceId}|${resourceCalendarData.date}|${resourceCalendarData.calendarDataList[i].timestr}"/>
								
								<c:set var="resourceOpenTimeSlotsMap" value="${resourceIdOpenTimeSlotsMap[resourceCalendarData.date]}"/>
								<c:set var="resourceGreyTimeSlotsMap" value="${resourceIdGreyTimeSlotsMap[resourceCalendarData.date]}"/>
									<!--
										OpenTimeSlots --  Values = "open","","closed"
										GreyTimeSlots --  Values = "skip","yellow","Open"
									-->

									<c:set var="resourceTimeSlot" value="${resourceOpenTimeSlotsMap[mapKey]}"/>
									<c:choose>
										<c:when test="${not empty resourceOpenTimeSlotsMap[mapKey]}">
										   <c:set var="resourceTimeSlot" value="${resourceOpenTimeSlotsMap[mapKey]}"/>
										   <c:choose>

												<c:when test="${resourceTimeSlot eq 'open' || resourceTimeSlot eq 'Open'}">
													<td rowspan="${rowspan}"  ${style}
														class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
															 <a href="javascript:void(0)">
																<div data="resourceId=${resourceCalendarData.resourceId}&time=${resourceCalendarData.calendarDataList[i].timestr}&customerId=0&scheduleId=0&date=${resourceCalendarData.date}" class="bookAppt" style="font-weight:bold">
																	Open
																</div>
															</a>
													</td>
												</c:when>
												<c:when test="${resourceTimeSlot eq ''}">
													<td rowspan="${rowspan}"  ${style}
														class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
															<a href="javascript:void(0)">${resourceTimeSlot}</a>
													</td>
												</c:when>
												<c:when test="${resourceTimeSlot eq 'closed'}">
													<td rowspan="${rowspan}"  ${style}
														class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
															<a href="javascript:void(0)">${resourceTimeSlot}</a>
													</td>
												</c:when>
												<c:otherwise>
													<td rowspan="${rowspan}"  ${style}
														class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
															<a href="javascript:void(0)">${resourceTimeSlot}</a>
													</td>
												</c:otherwise>
										   </c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${not empty resourceGreyTimeSlotsMap[mapKey]}">
													<c:set var="resourceTimeSlot" value="${resourceGreyTimeSlotsMap[mapKey]}"/>
													 <c:choose>
														<c:when test="${resourceTimeSlot eq 'skip'}">
															<td rowspan="${rowspan}"  ${style}
																class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
																	<a href="javascript:void(0)">${resourceTimeSlot}</a>
															</td>
														</c:when>
														<c:when test="${resourceTimeSlot eq 'yellow'}">
															<td rowspan="${rowspan}" class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">

															</td>
														</c:when>
														<c:when test="${resourceTimeSlot eq 'Open'}">
															<td rowspan="${rowspan}"  ${style}
																class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
																	<a href="javascript:void(0)">${resourceTimeSlot}</a>
															</td>
														</c:when>
														<c:otherwise>
															<td rowspan="${rowspan}"  ${style}
																class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
																	<a href="javascript:void(0)">${resourceTimeSlot}</a>
															</td>
														</c:otherwise>
												   </c:choose>
												</c:when>
												<c:otherwise>
													<td rowspan="${rowspan}"  ${style}
														class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail appt resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
															<a href="javascript:void(0)">N.A</a>
													</td>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>								
							</c:when>
							<c:when test="${resourceCalendarData.calendarDataList[i].apptStatus eq 'booked'}">
								<c:set var="appointmentData" value="${resourceCalendarData.calendarDataList[i].appointmentData}"></c:set>
								<td rowspan="${rowspan}"  ${style} cust_id="${appointmentData.customerId}"
									class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 reservedDocColor1 resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate} bookedResource"
									style="${appointmentData.customerId == 0 ? 'background-color: #f9f395' : ''}" >
									<a class="tt" style="color:white;float:left" href="javascript:"
									   data="resourceId=${resourceCalendarData.resourceId}&time=${resourceCalendarData.calendarDataList[i].timestr}&customerId=${appointmentData.customerId}&scheduleId=${appointmentData.scheduleId}&date=${resourceCalendarData.date}"
									   onclick="openAppointmentDetails(this)">
									${appointmentData.firstName} ${appointmentData.lastName}
										<span class="tooltip">
											<span class="top"></span>
											<span class="middle">
												<b>Appointment Details:</b> <br><br>
												<b>SSN : </b>${appointmentData.accountNumber}<br>
												<b>First Name : </b>${appointmentData.firstName}<br>
												<b>Last Name : </b>${appointmentData.lastName}<br>
												<b>Contact Phone : </b>${appointmentData.contactPhone}<br>
												<b>Appt Date &amp; Time : </b>${appointmentData.apptDateTime}<br>
												<b>Service : </b>${appointmentData.serviceName}<br>
												<b>Energy Acct# : </b>${appointmentData.attrib1}<br>
											</span>
											<span class="bottom"></span>
										</span>
										&nbsp;
									</a>
									<div style="display: none">
										<input type="hidden" class="bookedApptInfo" value=""
											   apptDateTime="${appointmentData.apptDateTime}"
											   locationName="${appointmentData.locationName}"
											   resourceName="${appointmentData.resourceName}"
											   serviceName="${appointmentData.serviceName}"
											   apptStatus="${appointmentData.apptStatus}"
											   apptMethod="${appointmentData.apptMethod}"
											   walkIn="${appointmentData.walkIn}"
											   accessed="${appointmentData.accessed}"
											   confirmNumber="${appointmentData.confirmNumber}"
											   customerId="${appointmentData.customerId}"
											   houseHoldId="${appointmentData.houseHoldId}"
											   ssn="${appointmentData.accountNumber}"
											   firstName="${appointmentData.firstName}"
											   lastName="${appointmentData.lastName}"
											   contactPhone="${appointmentData.contactPhone}"
											   email="${appointmentData.email}"
											   attrib1="${appointmentData.attrib1}"
											   dob="${appointmentData.dob}"
											   address="${appointmentData.address}"
											   city="${appointmentData.city}"
											   state="${appointmentData.state}"
											   zipCode="${appointmentData.zipCode}"
											   blockedFlag="${appointmentData.blockedFlag}"
											   scheduleId="${appointmentData.scheduleId}"
										>
									</div>
								</td>
							</c:when>
							<c:when test="${resourceCalendarData.calendarDataList[i].apptStatus eq 'holiday'}">
								<td rowspan="${rowspan}"  ${style}
									class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 holiday resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
									&nbsp;
								</td>
							</c:when>
							<c:when test="${resourceCalendarData.calendarDataList[i].apptStatus eq 'closed'}">
								<td rowspan="${rowspan}"  ${style}
									class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 cldday resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
									&nbsp;
								</td>
							</c:when>
							<c:when test="${resourceCalendarData.calendarDataList[i].apptStatus eq 'NA'}">
								<td rowspan="${rowspan}"  ${style}
									class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 nowrk resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
									&nbsp;
								</td>
							</c:when>
							<c:when test="${resourceCalendarData.calendarDataList[i].apptStatus eq 'reserved'}">
								<td rowspan="${rowspan}" ${style}
									class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 reserved resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}"> 	Reserved
								</td>
							</c:when>
							<c:otherwise>
								<td rowspan="${rowspan}"  ${style}
									class="${i%2==0 ? 'dotdiv' : 'solid'} rline2 avail resourceId_${resourceCalendarData.resourceId} resources weeklyDate_${weeklyDate}">
									&nbsp; 
								</td>
							</c:otherwise>
						</c:choose>
					 </c:if>
				 </c:forEach>
			  </tr>
		  </c:forEach>
	   </tbody>
	</table>
</c:if>
<c:if test ="${!weeklyCalendarResponse.status}">
	${weeklyCalendarResponse.message!="" ? weeklyCalendarResponse.message : "There is no data!!"}
</c:if>

<script>

	$(document).ready(function(){
		try {
			resetCalendarWithAvailableDates();

			$(".bookedResource").each(function () {
				if ($(this).attr("cust_id") != 0) {
					$(this).css('background', $("#" + $("input:radio:checked").val()).css('background'));
				}
			});
		} catch (e){
			console.log("----------------- Error ------------------", e)
		}
	});
</script>