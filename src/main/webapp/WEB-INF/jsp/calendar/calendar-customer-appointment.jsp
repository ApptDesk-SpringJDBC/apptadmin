<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
    $(document).ready(function () {
		if($("#apptTypeExistingNew").val() === "new"){
			$("#bookAppointmentLabel").text('Book New Appointment');
			$("#bookAppointment").show();
			$("#saveAppointment").hide();
			$("#rescheduleAppointment").hide();
			$("#cancelAppointment").hide();
		} else {
			$("#bookAppointmentLabel").text('Appointment Details');
			$("#isHoldAppointment").val("no");
			$("#bookAppointment").hide();
			$("#saveAppointment").show();
		}
        $('#hideCalRightSideSection').click(function() {
			hideRightSideSection();
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
					$("#first_name").val(res.firstName);
					$("#last_name").val(res.lastName);
					$("#account_number").val(res.ssn);
					$("#email").val(res.email);
					$("#addr").val(res.address);
					$("#addrCity").val(res.city);
					$("#addrState").val(res.state);
					$("#addrZip").val(res.zipCode);
					$("#attrib1").val(res.attrib1);
					if(res.contactPhone != null){
						var contactPhoneArr = res.contactPhone.split("-");
						$("#contact_phone_1").val(contactPhoneArr[0]);
						$("#contact_phone_2").val(contactPhoneArr[1]);
						$("#contact_phone_3").val(contactPhoneArr[2]);
					}
					var dateOfBirth = res.dob; //1976-11-09
					if(dateOfBirth != null){
						var dateArray = dateOfBirth.split("-");
						$("#dob_1").val(dateArray[0]);
						$("#dob_2").val(dateArray[1]);
						$("#dob_3").val(dateArray[2]);
					}
					$("#audioId").show();
					$("#bookApptCustomerId").val(res.customerId);
					var apptHistoryHtmlContent = getAppointmentsListByCustomerId(res.customerId);
					$("#historyDiv").html("");
					$("#historyDiv").html(apptHistoryHtmlContent);
				});
				return false;
			}
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" )
					.append( "<a>" + item.name /*+ " " + item.val  + "  - " + (item.ssn  !="undefined" ? item.ssn : "")*/+ "</a>" )
					.appendTo( ul );
		};

        $( "#accordion" ).accordion({
            heightStyle: "content",
            beforeActivate: function( event, ui ) {
                if($("#first_name").val() == "" || $("#first_name").val() == undefined){
                    alert("Please select a customer from Customer search");
                    $("#customerName").focus();
                    $("#customerName").css("border-color","blue");
                    return false;
                }
                if($("#apptInfoForm").is(":visible") && ($("#isHoldAppointment").val() == "" || $("#isHoldAppointment").val() == "yes")){
                    alert("Please confirm appointment");
                    return false;
                }
            },
			create: function( event, ui ) {
				var calendarType = $("#calendarType").val();
				if(calendarType === 'daily'){
					$("#intake").html($(".main-table th[style='display: table-cell;']").html());
				} else if (calendarType === 'weekly') {
					$("#intake").html($("input[type='checkbox']:checked").next().html());
				}
			}
        });

        $("#bookAppointment").click(function(){
            $("#isActiveHoldAppointment").val("false");
			var bookApptFormData = $("#apptInfoForm").serializeArray();
			bookApptFormData.push({name: "apptDateTime", value: $("#appointmentTime").text()});
			$.post("confirmAppointment.json", bookApptFormData,
					function(response){
                        $("#isHoldAppointment").val("no");
                        $("#ui-accordion-accordion-header-2").trigger("click");

                        var customerId = $("#bookApptCustomerId").val();
                        var apptHistoryHtmlContent = getAppointmentsListByCustomerId(customerId);
                        $("#historyDiv").html("");
                        $("#historyDiv").html(apptHistoryHtmlContent);
						$("#bookAppointment").hide();
						$("#saveAppointment").show();
					}
            ).fail(function() {
                alert( "Error occurred while confirming appointment." );
            });
		});

		$("#saveAppointment").click(function(){
			$("#isActiveHoldAppointment").val("false");
			var bookApptFormData = $("#apptInfoForm").serializeArray();
			bookApptFormData.push({name: "apptDateTime", value: $("#appointmentTime").text()});
			$.get("updateBookedAppointment.json", bookApptFormData,
					function(response){
						$("#isHoldAppointment").val("no");
						$("#ui-accordion-accordion-header-2").trigger("click");

						var customerId = $("#bookApptCustomerId").val();
						var apptHistoryHtmlContent = getAppointmentsListByCustomerId(customerId);
						$("#historyDiv").html("");
						$("#historyDiv").html(apptHistoryHtmlContent);
					}
			).fail(function() {
				alert( "Error occurred while confirming appointment." );
			});
		});

		$("#rescheduleAppointment").click(function() {
			$("#cal-reschedule-appt-placeholder").html("");
			$("#cal-reschedule-appt-placeholder").attr("rescheduleActive","active");
			var dataElement = $("#apptInfoDiv .bookedApptInfo");
			var htmlContent = "<div><fieldset style='border: 2px solid #17649F;border-radius: 9px; padding-left: 3px; display: inline-block'>" +
					"<legend style='margin-left: 22px;'>Placeholder</legend>";
			htmlContent += "<div style='position: absolute;margin-top: -19px;margin-left: -6px;;cursor:pointer;'>" +
					"<img src='./static/images/close_new.jpg' id='placeHolder' style='width: 20px;cursor:pointer;' onclick='clearPlaceHolder()'></div>";
			htmlContent += "<label><b>Customer Name: </b>"+ dataElement.attr('firstName') + " " + dataElement.attr('lastName') + "</label><br>";
			htmlContent += "<label><b>Appt Date & Time: </b>"+ dataElement.attr('apptdatetime') + "</label><br>";
			htmlContent += "<label><b>Location: </b>"+ dataElement.attr('locationName') + "</label> &nbsp;";
			htmlContent += "<label><b>Intake: </b>"+ dataElement.attr('resourceName')  + "</label> <br>";
			htmlContent += "<label><b>Service: </b>"+ $("#bookApptServices :selected").text();  + "</label>";
			htmlContent += "</fieldset></div>";

			dataElement.attr("serviceId",$("#bookApptServices").val());
			dataElement.attr("comments",$("#bookApptComments").val());
			$("#cal-reschedule-appt-placeholder").append($(htmlContent).html());
			$("#cal-reschedule-appt-placeholder").append(dataElement);
			$("#cal-reschedule-appt-placeholder").css("display", "inline-block");
			searchDailyCalender();
        });
        $("#cancelAppointment").click(function(){
            var holdApptFormData = $("#apptInfoForm").serializeArray();
            $.post("cancelAppointment1.json", holdApptFormData,
                    function(response){
                        console.log(response);
                        var calendarType = $("#calendarType").val();
                        if(calendarType === 'daily'){
                            searchDailyCalender();
                        } else if (calendarType === 'weekly') {
                            searchWeeklyCalender();
                        }
                    }
            );
        });

		$("#cancelCustomerInfo").click(function(){
			var calendarType = $("#calendarType").val();
			if(calendarType === 'daily'){
				searchDailyCalender();
			} else if (calendarType === 'weekly') {
				searchWeeklyCalender();
			}
		});

		$("#resetBookAppointment").click(function(){
			var calendarType = $("#calendarType").val();
			if(calendarType === 'daily'){
				searchDailyCalender();
			} else if (calendarType === 'weekly') {
				searchWeeklyCalender();
			}
		});
	});

	function clearPlaceHolder(){
		$("#cal-reschedule-appt-placeholder").attr("rescheduleActive", "inactive");
		$("#cal-reschedule-appt-placeholder").hide();
		$("#cal-reschedule-appt-placeholder").html("");
		var calendarType = $("#calendarType").val();
		if (calendarType === 'daily') {
			searchDailyCalender();
		} else if (calendarType === 'weekly') {
			searchWeeklyCalender();
		}
		rescheduleData = "";
	};

    function moveToNext(field,nextFieldID){
		if(field.value.length >= field.maxLength){
			$(nextFieldID).focus();
		}
	}

    function closeRightSideSection(){
        var calendarType = $("#calendarType").val();
        if(calendarType === 'daily'){
            searchDailyCalender();
        } else if (calendarType === 'weekly') {
            searchWeeklyCalender();
        }
    }

	function saveCustomerInfo(){
		if(!validateCustomer()){
			return false;
		}
		var customerFormData = $("#customerForm").serializeArray();
		var bookApptCustomerId = $('#bookApptCustomerId').val();
		if(bookApptCustomerId != ""){
			customerFormData.push({name: 'bookApptCustomerId', value : bookApptCustomerId});
		}
		$.post("saveCustomer.json", customerFormData,
				function(response){
					if(response.message == 'Success'){
						$("#ui-accordion-accordion-header-1").trigger("click");
                        $("#bookApptCustomerId").val(response.customerId);
						var apptHistoryHtmlContent = getAppointmentsListByCustomerId(response.customerId);
						$("#historyDiv").html("");
						$("#historyDiv").html(apptHistoryHtmlContent);
					} else {
						alert(response.message);
					}
				}
		);
	}
	function validateCustomer(){
		var valid = true;
		var message = '';
		$('#customerForm input').each(function() {
			var $this = $(this);
			$("input[labelText='"+$this.attr('labelText')+"']").css({"border-color":""})
			$("font[id='"+$this.attr('labelText')+"']").css({"display":"none"})
			if(!$this.val()) {
				console.log($this);
				var inputName = $this.attr('labelText');
				var isRequired = $this.attr('form-required');
				if(isRequired == 'Y'){
					$("input[labelText='"+$this.attr('labelText')+"']").css({"border-color":"red"})
					$("font[id='"+$this.attr('labelText')+"']").css({"display":"inline-block"})
					valid = false;
					return false;
				}
			}
		});
		return valid;
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
                <input type="hidden" id="isHoldAppointment" value="">
				<div class="float-left txt-bold">
					<table>
						<tr>
							<td style="font-weight: bold"><div id="bookAppointmentLabel"></div></td>
						</tr>
					</table>
				</div>
				<div class="float-right" id="hideCalRightSideSection" style="cursor:pointer;"> <img src="./static/images/close_new.jpg" style="width: 20px;"/> </div>
				<div class="clear-all"></div>
				<div class="addEditSection">
					<div id="accordion" class="accordion">
						<h3>Customer Info</h3>
						<div id="customerInfoDiv">
							<div id=""></div>
							<dl id="selectCustomer">
								<dt>Select Customer</dt>
								<dd>
									<input name="customerName search-customer" id="customerName" style="" value=""/><img src="./images\search_icon.png" style="width: 16px;">
									<input id="selectedCustomerId" name="selectedCustomerId" type="hidden">
								</dd>
							</dl>

							<dl id="customerDetails">
								<form id="customerForm">
									<table>
									<c:forEach var="customerRegDetail" items="${regDetailsList.customerRegistrationList}">
										<tr>
											<td style="width: 152px;padding-top: 10px;">
												<label>${customerRegDetail.displayTitle}</label><c:if test="${customerRegDetail.required == 'Y'}"><font color="red">*</font></c:if>
											</td>
											<td>
											<c:if test="${(customerRegDetail.displayType == 'textbox-2-2-4') && (customerRegDetail.paramColumn == 'dob')}">

												<input class="phone noMinWidth" type="${customerRegDetail.displayType=='textbox'?'text':''}"
													   style="border-radius: 5px; margin-right: 10px;"
													   name="${customerRegDetail.paramColumn}_1"
													   id="${customerRegDetail.paramColumn}_1"
                                                       onkeyup="moveToNext(this, ${customerRegDetail.paramColumn}_2)"
													   maxlength="2">
												<input class="phone noMinWidth" type="${customerRegDetail.displayType=='textbox'?'text':''}"
													   style="border-radius: 5px; margin-right: 10px;"
													   name="${customerRegDetail.paramColumn}_2"
													   id="${customerRegDetail.paramColumn}_2"
													   onkeyup="moveToNext(this, ${customerRegDetail.paramColumn}_3)"
													   maxlength="2">
												<input class="phone1 noMinWidth" type="${customerRegDetail.displayType=='textbox'?'text':''}"
													   style="border-radius: 5px; margin-right: 10px;"
													   name="${customerRegDetail.paramColumn}_3" id="${customerRegDetail.paramColumn}_3"
													   maxlength="4">
											</c:if>
											<c:if test="${(customerRegDetail.displayType == 'textbox-3-3-4') && (customerRegDetail.paramColumn == 'contact_phone')}">
												<input class="phone noMinWidth" type="${customerRegDetail.displayType =='textbox'?'text':''}"
													   style="border-radius: 5px; margin-right: 10px;"
													   labelText="phone" form-required="Y"
													   name="${customerRegDetail.paramColumn}_1" id="${customerRegDetail.paramColumn}_1"
                                                       onkeyup="moveToNext(this, ${customerRegDetail.paramColumn}_2)"
													   maxlength="3" onblur="validateCustomer();">
												<input class="phone noMinWidth" type="${customerRegDetail.displayType =='textbox'?'text':''}"
													   style="border-radius: 5px; margin-right: 10px;"
													   name="${customerRegDetail.paramColumn}_2" id="${customerRegDetail.paramColumn}_2"
													   labelText="phone" form-required="Y"
													   onkeyup="moveToNext(this, ${customerRegDetail.paramColumn}_3)"
													   maxlength="3" onblur="validateCustomer();">
												<input class="phone1 noMinWidth" type="${customerRegDetail.displayType =='textbox'?'text':''}"
													   style="border-radius: 5px; margin-right: 10px;"
													   labelText="phone" form-required="Y"
													   name="${customerRegDetail.paramColumn}_3" id="${customerRegDetail.paramColumn}_3"
													   maxlength="4" onblur="validateCustomer();">
												<font id="phone" color="red" style="display: none;">Required Field</font>
											</c:if>
											<c:if test="${(customerRegDetail.displayType != 'textbox-2-2-4') && (customerRegDetail.displayType != 'textbox-3-3-4')}">
												<input type="${customerRegDetail.displayType=='textbox'?'text':''}"
													   form-required="${customerRegDetail.required}"
													   labelText="${customerRegDetail.displayTitle}"
													   style="border-radius: 5px;"
													   name="${customerRegDetail.paramColumn}" id="${customerRegDetail.paramColumn}"
													   maxlength="${customerRegDetail.maxChars}" onblur="validateCustomer();">
												<c:if test="${customerRegDetail.required == 'Y'}">
													<font id="${customerRegDetail.displayTitle}" color="red" style="display: none;">Required Field</font>
												</c:if>
											</c:if>
											</td>
										</tr>
									</c:forEach>
									</table>
								</form>
							</dl>
							<div class="clear-all"></div>
							<div class="float-right pTop20">
								<input type="button" class="btn noMinWidth" value="Cancel" id="cancelCustomerInfo">
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
								<input id="bookApptScheduleId" name="scheduleId" type="hidden">
								<dl>
									<dt>Appointment Date & Time</dt>
									<dd> <label id="appointmentTime" name="apptDateTime">${verifyPageData.apptDateTime}</label></dd>
									<div id="confirmNumberDiv" style="display: none">
										<dt>Confirmation #</dt>
										<dd> <input type="text" id="confirmNumber" name="confirmNumber"></dd>
									</div>
									<dt>${displayNames.locationName}</dt>
									<dd>
										<input type="hidden" id="bookApptLocationId" name="locationId">
										<label id="bookApptLocationName" name="location"></label>
									</dd>
									<%-- Todo: need to fix later --%>
									 <dt>${displayNames.resourceName}</dt>
									<dd> <label id="intake" name="intake"></label></dd>

									<dt>${displayNames.serviceName}</dt>
									<dd>
										<input type="hidden" id="bookedApptServiceName" value="${verifyPageData.serviceName}">
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
									<dt>${displayNames.commentsName}</dt>
									<dd>
										<textarea name="bookApptComments" cols="" rows="" id="bookApptComments">${verifyPageData.comments}</textarea>
									</dd>
								</dl>
								<div class="clear-all"></div>
								<div class="float-right pTop20">
									<input type="button" class="btn noMinWidth" value="Cancel" id="resetBookAppointment" onclick="resetHoldAppt()">
									&nbsp;
									<input type="button" class="btn noMinWidth" value="Save" id="saveAppointment" style="display: none;">
									&nbsp;
									<input type="button" class="btn noMinWidth" value="Book" id="bookAppointment" style="display: none;">
									&nbsp;
									<input type="button" class="btn noMinWidth" value="Reschedule" id="rescheduleAppointment">
									&nbsp;
									<input type="button" class="btn noMinWidth" value="Cancel Appointment" id="cancelAppointment" onclick="cancelAppointment(this)">
								</div>
								<div class="clear-all"></div>
							</form>
						</div>
						<h3>Appointment History</h3>
						<div id="apptHistoryDiv">

                            <div id="historyDiv">                       </div>
                            <input type="button" value="Close" class="btn noMinWidth" style="float: right" onclick="closeRightSideSection()">
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>