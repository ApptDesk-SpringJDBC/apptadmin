<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add New Pledge</title>

<script type="text/javascript" src="static/js/validation/applyPledgeFormValidation.js?version=1.8"></script>
<script type="text/javascript">
$(document).ready(function() {
	/*
	 var fundId_data = $('#fundId_data').val();
	 var fundId_data_arr = fundId_data.split("_");
	 $("#fundId").val(fundId_data_arr[0]);
	 */
});
</script>
</head>

<body>
<!-- pop up starts -->
  <h1> &nbsp;&nbsp;Add Pledge Details</h1>
  <a href="#" class="jqmClose close" title="Close"  id="popupClose">Close</a> 

<form:form  id="pledgeHistoryAddPledgeForm" commandName="customerPledgeReq"  modelAttribute="customerPledgeReq"  method="get">

	  <div class="popup-content-big" > 	
	  
			<!-- Customer Details starts -->
				<div class="popup-box">
				   <div class="popup-box-head">
					  <h2>${displayNames.customerName} Details</h2>
					  <img src="static/images/minus.png" onClick="switchMainUpcomintAppointment()" ondrag="return false" id="expnClsp1">
					  <div class="clear-all"></div>
				   </div>
				   <div class="popup-box-content" id="CustomerInfo">
					  <div class="error" id="validationResponse"> </div>
					  <form:hidden path="customer.customerId"/>
					  <table width="100%"  style="border-collapse:separate; border-spacing:1em;">
						 <tr>							
							<td>SSN </td> <td>${customerPledgeReq.customer.accountNumber} </td>
							<td><font color="red">*</font>Household Id</td> 
							<td>
								<form:input path="customer.houseHoldId" id="houseHoldId"/>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>First Name </td> 
							<td>
								<form:input path="customer.firstName" id="firstName"/>
							</td>						 							
							<td><font color="red">*</font>Last Name </td> 
							<td>
								<form:input path="customer.lastName" id="lastName"/>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>Contact Phone</td> 
							<td>
								<form:input path="customer.contactPhone1" id="contactPhone1" class="phone" maxlength="3"/>
								<form:input path="customer.contactPhone2" id="contactPhone2" class="phone" maxlength="3"/>
								<form:input path="customer.contactPhone3" id="contactPhone3" class="phone1" maxlength="4"/>
								<form:hidden path="customer.contactPhone" id="contactPhone"/>
							</td>						 							
							<td>Email </td> 
							<td>
								<form:input path="customer.email" id="email"/>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>Energy Acct#</td> 
							<td>
								<form:input type="text" path="customer.attrib1" id="attrib1" maxlength="20"/>
							</td>													
							<td><font color="red">*</font>Address </td> 
							<td>
								<form:input path="customer.address" id="address"/>
							</td>
						 </tr>
						 <tr>
							<td><font color="red">*</font>City</td> 
							<td>
								<form:input path="customer.city" id="city"/>
							</td>						 						
							<td>State </td> 
							<td>
								<c:if test="${(not empty customerPledgeReq.customer.state) && customerPledgeReq.customer.state!=null  && customerPledgeReq.customer.state!='null'}">
									${customerPledgeReq.customer.state}
								</c:if>
							</td>
						</tr>
						<tr>	
							<td><font color="red">*</font>Zip</td> 
							<td>
								<form:input path="customer.zipCode" id="zipCode"/>
							</td>
						 </tr>
					   </table>				 
				   </div>
				   <div class="clear-all"></div>
				</div>
			<!-- Customer Details ends -->

			<div class="error" id="fundSourcesValidation"> </div>
			<input type="hidden" id="noOfFundSources" value="${fn:length(customerPledgeReq.customerPledgeList)}" />
			<c:forEach items="${customerPledgeReq.customerPledgeList}" var="customerPledge" varStatus="status">
				<!--Funds Detsil starts -->			
				<div class="popup-box">
				   <div class="popup-box-head">
					  <h2> ${customerPledge.fundName}</h2>
					  <img src="static/images/minus.png" onClick="switchMainUpcomintAppointment()" ondrag="return false" id="expnClsp1">
					  <div class="clear-all"></div>
				   </div>
				   <div class="popup-box-content" id="CustomerInfo">
					  <div class="error" id="fundSourcesValidationResponse_${status.index}"> </div>
					  <form:hidden path="customerPledgeList[${status.index}].fundId"/>
					  <form:hidden path="customerPledgeList[${status.index}].pmtUpdateBy"/>
					  <form:hidden path="customerPledgeList[${status.index}].pmtUpdateByName"/>
					  <table width="100%"  style="border-collapse:separate; border-spacing:1em;">
						 <tr>							
							<td>Apply to Appt </td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].scheduleId"  id="scheduleId_${status.index}" class="scheduleIdChanged">
								   <form:option value="-1" label="None"/>
								   <form:options items="${customerPastAppts}" itemLabel="apptDateTime" itemValue="scheduleId"></form:options>
								</form:select>
							</td>
							<%--
							<td>Pledge Priority </td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].pledgePriority"  id="pledgePriority_${status.index}">
								   <form:option value="-1" label="Select"/>
								   <form:option value="1" label="Urgent"/>
								   <form:option value="2" label="Updated"/>
								   <form:option value="3" label="Primary"/>
								   <form:option value="4" label="New Primary"/>
								   <form:option value="5" label="Secondary"/>
								   <form:option value="6" label="New Secondary"/>
								</form:select>
							</td>
							--%>
							<td>&nbsp;</td> 
							<td>&nbsp;</td> 
						</tr>
						<tr id="apptSelectionDetails_${status.index}">
							<td><font color="red">*</font>${displayNames.locationName}</td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].locationId"  id="locationId_${status.index}" class="locChnaged">
								  <form:option value="-1" label="Select ${displayNames.locationName}"/>
								   <form:options items="${customerPledge.locationList}" itemLabel="locationNameOnline" itemValue="locationId"></form:options>
								</form:select>
							</td>						 							
							<td><font color="red">*</font>${displayNames.resourceName} </td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].resourceId"  id="resourceId_${status.index}">
								  <form:option value="-1" label="Select ${displayNames.resourceName}"/>
								   <form:options items="${customerPledge.resourceList}" itemLabel="displayName" itemValue="resourceId"></form:options>
								</form:select>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>Total Amount Pledged</td> 
							<td>
								$<form:input path="customerPledgeList[${status.index}].totalPledgeAmt" id="totalPledgeAmt_${status.index}"/>
							</td>						 							
							<td><font color="red">*</font>Status </td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].pledgeStatusId"  id="pledgeStatusId_${status.index}">
								   <form:option value="-1" label="Select Status"/>
								   <form:options items="${customerPledgeStatus}" itemLabel="status" itemValue="id"></form:options>
								</form:select>
							</td>
						</tr>
						<tr>
							<td>Vendor 1 </td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].vendor1Id"  id="vendor1Id_${status.index}">
								   <form:option value="-1" label="Select Vendor"/>
								   <form:options items="${customerPledge.vendorList}" itemLabel="vendorName" itemValue="vendorId"></form:options>
								</form:select>
							</td>
							<td>Vendor 1 Acc No#</td> 
							<td>
								<form:input path="customerPledgeList[${status.index}].vendor1AccountNumber" id="vendor1AccountNumber_${status.index}"/>
							</td>
						</tr>
						<tr>
							<td>Vendor 1 Payment</td> 
							<td>
								$<form:input path="customerPledgeList[${status.index}].vendor1Payment" id="vendor1Payment_${status.index}"/>
							</td>
							<td>&nbsp;</td> 
							<td>&nbsp;</td> 
						</tr>
						<tr>
							<td>Vendor 2</td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].vendor2Id"  id="vendor2Id_${status.index}">
								  <form:option value="-1" label="Select Vendor"/>
								   <form:options items="${customerPledge.vendorList}" itemLabel="vendorName" itemValue="vendorId"></form:options>
								</form:select>
							</td>
							<td>Vendor 2 Acc No#</td> 
							<td>
								<form:input path="customerPledgeList[${status.index}].vendor2AccountNumber" id="vendor2AccountNumber_${status.index}"/>
							</td>
						</tr>
						<tr>
							<td>Vendor 2 Payment</td> 
							<td>
								$<form:input path="customerPledgeList[${status.index}].vendor2Payment" id="vendor2Payment_${status.index}"/>
							</td>
							<td>&nbsp;</td> 
							<td>&nbsp;</td> 
						</tr>
						<tr>
							<td>Vendor 3</td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].vendor3Id"  id="vendor3Id_${status.index}">
								   <form:option value="-1" label="Select Vendor"/>
								   <form:options items="${customerPledge.vendorList}" itemLabel="vendorName" itemValue="vendorId"></form:options>
								</form:select>
							</td>
							<td>Vendor 3 Acc No#</td> 
							<td>
								<form:input path="customerPledgeList[${status.index}].vendor3AccountNumber" id="vendor3AccountNumber_${status.index}"/>
							</td>
						</tr>
						<tr>
							<td>Vendor 3 Payment</td> 
							<td>
								$<form:input path="customerPledgeList[${status.index}].vendor3Payment" id="vendor3Payment_${status.index}"/>
							</td>
							<td>&nbsp;</td> 
							<td>&nbsp;</td> 
						</tr>

						<tr>
							<td>Pledge Status </td> 
							<td colspan="2">
							<form:checkbox path="customerPledgeList[${status.index}].urgentStatus" id="urgentStatus_${status.index}" value="Y" class="noWidth"/> Urgent
								<form:checkbox path="customerPledgeList[${status.index}].updatedStatus" id="updatedStatus_${status.index}" value="Y" class="noWidth"/> Updated 
								<form:checkbox path="customerPledgeList[${status.index}].calledinStatus" id="calledinStatus_${status.index}" value="Y" class="noWidth"/> Called-In						 
							</td> 
							<td>&nbsp;</td> 
						</tr>
						<tr>
							<td>Pledge Primary/Secondary Status </td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].primaryStatus"  id="primaryStatus_${status.index}">
								   <form:option value="Select" label="Select"/>
								   <form:option value="Primary" label="Primary"/>
								   <form:option value="New Primary" label="New Primary"/>
								   <form:option value="Secondary" label="Secondary"/>
								   <form:option value="New Secondary" label="New Secondary"/>
								   <c:if test = "${status.index != 1}">
								   	<form:option value="Not Listed" label="Not Listed"/>
								   </c:if>
								</form:select>
							</td>
							<td>&nbsp;</td> 
							<td>&nbsp;</td> 
							<form:hidden path="customerPledgeList[${status.index}].secondaryStatus" id="secondaryStatus_${status.index}" value="0"/>
							<%--
							<td>Pledge Secondary Status </td> 
							<td>
								<form:select path="customerPledgeList[${status.index}].secondaryStatus"  id="secondaryStatus_${status.index}">
								   <form:option value="Select" label="Select"/>
								   <form:option value="Secondary" label="Secondary"/>
								   <form:option value="New Secondary" label="New Secondary"/>
								</form:select>
							</td>
							--%>
						</tr>
					   </table>					 				 
				   </div>
				   <div class="clear-all"></div>
				</div>
				<!-- Funds Details ends -->
			</c:forEach>

	  </div>	

	  <div class="popup-button" id="apptSave">		
			<a href="#" class="btn jqmClose"  id="cancelBtn">Cancel</a>
			&nbsp;    
			<input name="btnSave" type="button" class="btn" value="Save" id="pledgeHistoryAddPledge" />
	  </div>
</form:form>	
<!-- pop up ends -->
</body>
</html>
