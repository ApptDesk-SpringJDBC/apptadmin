<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test ="${fn:length(serviceLocationResponse.serviceLocationList) gt 0}">
	<script type="text/javascript">
		$(document).ready(function () {		
			try{
				var noOfServiceWindows = $("#noOfServiceWindows").val();	
				if(noOfServiceWindows > 0){
					for(var i=0;i<noOfServiceWindows;i++){
						new JsDatePick({
							useMode : 2,
							target : "loc_ser_appt_window_start_date_"+i,
							dateFormat : "%M/%d/%Y"
						});
						new JsDatePick({
							useMode : 2,
							target : "loc_ser_appt_window_end_date_"+i,
							dateFormat : "%M/%d/%Y"
						});
					}	
				}
			}catch(e){
				alert("Error :: "+e);
			}
		});
	</script>

	<form id="updateServiceLocationApptDatesWindow" method="post" >
		<b>Allow online/phone scheduling to book between these dates for the ${displayNames.servicesName} :</b><br/> 
		<input type="hidden" id="noOfServiceWindows" value="${fn:length(serviceLocationResponse.serviceLocationList)}"/>
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
			  <tr>
				<th width="50%">${displayNames.serviceName}</th>
				<th width="25%" class="center-align">Start Date </th>
				<th width="25%" class="center-align">End Date  </th>
			  </tr>
			 <c:forEach items="${serviceLocationResponse.serviceLocationList}" var="serviceLocation" varStatus="status">
				  <tr>
					<input type="hidden" name="serviceLocationList[${status.index}].serviceLocationId" value="${serviceLocation.serviceLocationId}" />
					<td>${serviceLocation.serviceName} </td>
					<td class="center-align">
						<input type="text" name="serviceLocationList[${status.index}].startDate" 
						id="loc_ser_appt_window_start_date_${status.index}" style="width:auto"  value="${serviceLocation.startDate}"/>
					</td>
					<td class="center-align">
						<input type="text" name="serviceLocationList[${status.index}].endDate"
						id="loc_ser_appt_window_end_date_${status.index}" style="width:auto" value="${serviceLocation.endDate}"/>
					</td>
				  </tr>			
			 </c:forEach>
		 </table>
		 <center> <input   class="btn"  type="button" value="Save" onclick="updateServiceLocationApptDatesWindow();"></center>
	</form>
</c:if>

<c:if test ="${fn:length(serviceLocationResponse.serviceLocationList) le 0}">
	 <div class="error"><b>There is no ${displayNames.servicesName} configured for this ${displayNames.locationName}</b></div>
</c:if>