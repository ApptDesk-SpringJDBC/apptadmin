<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document).ready(function () {
		$('#hideCalRightSideSection').click(function() {
			var calendarType = $("#calendarType").val();
			alert(calendarType);
			try{
				if(calendarType == "daily"){
					searchDailyCalender();
				} else if(calendarType == "weekly") {
					searchWeeklyCalender();
				}
			}catch(e){
				alert("Error : "+e);
			}
		});
		$( "#accordion" ).accordion({
			heightStyle: "content"
		});

		$( "#customerName" ).autocomplete({
			minLength: 0,
			source: function( request, response ) {
				$.ajax({
					type: "GET",
					url: "getCustomerNames.json?customerName="+$("#customerName").val(),
					dataType: "json",

					success: function( data ) {
						response(data);
						//return data;
					}
				});
			},
			focus: function( event, ui ) {
				$( "#customerName" ).val( ui.item.name );
				return false;
			},
			select: function( event, ui ) {
				$("#selectedCustomerId").val(ui.item.customerId);
				var url = "getCustomerById.json?customerId="+ui.item.customerId;
				$.get(url,function(res,stat){
					console.log(res);
					$("#firstName").val(res.firstName);
					$("#lastName").val(res.lastName);
					$("#account_number").val(res.ssn);
					$("#email").val(res.email);
					$("#addr").val(res.address);
					$("#addrCity").val(res.city);
					$("#addrState").val(res.state);
					$("#addrZip").val(res.zipCode);
					if(res.contactPhone != null){
						var contactPhoneArr = res.contactPhone.split("-");
						$("#contact_phone1").val(contactPhoneArr[0]);
						$("#contact_phone2").val(contactPhoneArr[1]);
						$("#contact_phone3").val(contactPhoneArr[2]);
					}
					var dateOfBirth = res.dob;
					if(dateOfBirth != null){
						var dateArray = dateOfBirth.split("/");
						$("#dob1").val(dateArray[0]);
						$("#dob2").val(dateArray[1]);
						$("#dob3").val(dateArray[2]);
					}
					$("#audioId").show();
					$("#bookApptCustomerId").val(res.customerId);
				});
				return false;
			}
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" )
					.append( "<a>" + item.name /*+ " " + item.val  + "  - " + (item.ssn  !="undefined" ? item.ssn : "")*/+ "</a>" )
					.appendTo( ul );
		};
		$("#holdAppointment").click(function(){
			var holdApptFormData = $("#apptInfoForm").serializeArray();
			$.post("holdAppointment.json", holdApptFormData,
					function(response){
						console.log(response);
						searchWeeklyCalender();
					}
			);
		});
	});

	function saveCustomerInfo(){
		var customerFormData = $("#customerForm").serializeArray();
		$.post("saveCustomer.html", customerFormData,
				function(response){
					alert(response);
				}
		);
		$("#ui-accordion-accordion-header-1").trigger("click");
	}

</script>
<style type="text/css">
	.ui-widget-content, .JsDatePickBox {
		z-index: 9999 !important;
	}
</style>

<table border="0" cellspacing="0" cellpadding="0" class="" width="100%">
	<tr>
		<td>
			<div>
				<div class="float-left txt-bold"></div>
				<div class="float-right" id="hideCalRightSideSection" style="cursor:pointer;">X</div>
				<div class="clear-all"></div>
				<div class="addEditSection">
					<div id="accordion">
						<h3>Customer Info</h3>
						<div id="customerInfoDiv">
							<dl>
								<dt>Select Customer</dt>
								<dd>
									<input name="customerName" id="customerName" style="margin-right:0; padding-right:25px" value=""/>
									<input id="selectedCustomerId" name="selectedCustomerId" type="hidden">
								</dd>
							</dl>

							<dl id="customerDetails">
								<form id="customerForm">
									<dt><font color="red">*</font>First Name</dt>
									<dd>
										<input id="firstName" name="firstName" type="textbox" value="" maxlength="50">
									</dd>
									<dt><font color="red">*</font>Last Name</dt>
									<dd>
										<input id="lastName" name="lastName" type="textbox" value="" maxlength="50">
									</dd>
									<div id="audioId" style="display: none">
										<dt>Audio</dt>
										<dd>
											<audio controls>
												<source src="horse1.ogg" type="audio/ogg">
												<source src="horse1.mp3" type="audio/mpeg">
												Your browser does not support the audio element.
											</audio>
										</dd>
									</div>
									<div id="transcibedName" style="display: none">
										<dt><font color="red">*</font>Transcibed Name</dt>
										<dd>
											<input id="last_name" name="last_name" type="textbox" value="" maxlength="50">
										</dd>
									</div>
									<dt><font color="red">*</font>SSN</dt>
									<dd>
										<input id="account_number" name="ssn" type="text" value="" maxlength="9">
									</dd>
									<dt><font color="red">*</font>Contact Phone</dt>
									<dd>
										<input id="contact_phone1" name="contact_phone1" type="textbox" class="phone noMinWidth" value="" maxlength="3">
										<input id="contact_phone2" name="contact_phone2" type="textbox" class="phone noMinWidth" value="" maxlength="3">
										<input id="contact_phone3" name="contact_phone3" type="textbox" class="phone1 noMinWidth" value="" maxlength="4">
									</dd>
									<dt>Email</dt>
									<dd>
										<input id="email" name="email" type="textbox" value="" maxlength="50">
									</dd>
									<dt><font color="red">*</font>Appointment Type</dt>
									<dd>
										<input name="apptType" type="radio" value="inPerson" class="nowidth">
										In Person
										<input name="apptType" type="radio" value="" class="nowidth">
										Phone
									</dd>
									<dt><font color="red">*</font>Address</dt>
									<dd>
										<input id="addr" name="address" type="textbox" value="" maxlength="20">
									</dd>
									<dt><font color="red">*</font>City</dt>
									<dd>
										<input id="addrCity" name="city" type="textbox" value="" maxlength="20">
									</dd>
									<dt><font color="red">*</font>State</dt>
									<dd>
										<input id="addrState" name="state" type="textbox" value="" maxlength="20">
									</dd>
									<dt><font color="red">*</font>Zip</dt>
									<dd>
										<input id="addrZip" name="zipCode" type="textbox" value="" maxlength="20">
									</dd>
									<dt>DOB</dt>
									<dd>
										<input id="dob1" name="dob1" type="textbox" class="phone noMinWidth" value="" maxlength="2">
										<input id="dob2" name="dob2" type="textbox" class="phone noMinWidth" value="" maxlength="2">
										<input id="dob3" name="dob3" type="textbox" class="phone1 noMinWidth" value="" maxlength="4">
									</dd>
									<dt><font color="red">*</font>Energy Acct</dt>
									<dd>
										<input id="energyAcct" name="attrib1" type="textbox" value="" maxlength="20">
									</dd>
								</form>
							</dl>
							<div class="clear-all"></div>
							<div class="float-right pTop20">
								<input type="button" class="btn noMinWidth" value="Reset" id="resetCustomerInfo">
								&nbsp;
								<input type="button" class="btn noMinWidth" value="Save" onclick="saveCustomerInfo()">
							</div>
							<div class="clear-all"></div>
						</div>
						<h3>Appointment Info</h3>
						<div id="apptInfoDiv">
							<form id="apptInfoForm">
								<input type="hidden" id="resourceId" name="resourceId">
								<input id="bookApptCustomerId" name="customerId" type="hidden">
								<dl>
									<dt>Appointment Date & Time</dt>
									<dd> <input type="text" id="appointmentTime" name="apptDateTime"></dd>
									<div id="confirmNumberDiv" style="display: none">
										<dt>Confirmation #</dt>
										<dd> <input type="text" id="confirmNumber" name="appointmentTime"></dd>
									</div>
									<dt>Location</dt>
									<dd>
										<input type="hidden" id="bookApptLocationId" name="locationId">
										<input type="text" id="bookApptLocationName" name="location">
									</dd>
									<dt>Intake</dt>
									<dd> <input type="text" id="intake" name="intake"></dd>

									<dt>Service</dt>
									<dd>
										<select id="bookApptServices" name="serviceId">

										</select>
									</dd>
									<dt></dt>
									<div id="modifyAppointment" style="display: none">
										<dd><a href="#">Cancel this Appointment</a> <a href="#">Rescedule this Appointment</a></dd>
									</div>
									<div id="modifyAppointmentStatus" style="display: none">
										<dt>Change Appointment Status</dt>
										<dd>
											<select id="changeApptStatus" name="appointmentStatus">
												<option></option>
											</select>
										</dd>
									</div>
									<dt>Add Comments</dt>
									<dd>
										<textarea name="bookApptComments" cols="" rows="" id="bookApptComments"></textarea>
									</dd>
								</dl>
								<div class="clear-all"></div>
								<div class="float-right pTop20">
									<input type="button" class="btn noMinWidth" value="Reset" id="resetBookAppointment" onclick="resetHoldAppt()">
									&nbsp;
									<input type="button" class="btn noMinWidth" value="Book Appointment" id="holdAppointment" >
								</div>
								<div class="clear-all"></div>
							</form>
						</div>
						<h3>Appointment History</h3>
						<div id="apptHistoryDiv">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
								<tbody>
								<tr>
									<th width="5%">Conf No.</th>
									<th width="20%">Appt Date</th>
									<th width="15%">Location</th>
									<th width="25%">Intake</th>
									<th width="20%">Service</th>
									<th width="15%">Cancel</th>
								</tr>
								<tr>
									<td>4338</td>
									<td>02/06/2017 08:15 AM</td>
									<td>MSC-Federal Way</td>
									<td>Uchral Lkhamj</td>
									<td>PSE Electric</td>
									<td>
										<a href="javascript:void(0)" data="" class="cancleUpcomingAppt" id="5309">Cancel</a>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>