$(document).ready(function() {

    startDate_calendarObject = new JsDatePick({
        useMode: 2,
        target: "startDate",
        dateFormat: "%M/%d/%Y"
    });
    endDate_calendarObject = new JsDatePick({
        useMode: 2,
        target: "endDate",
        dateFormat: "%M/%d/%Y"
    });

    startDate_calendarObject.setOnSelectedDelegate(function() {
        startDate_calendarObject.closeCalendar();
        populateDate(startDate_calendarObject, "startDate", "startDateError", "* Start Date");
    });

    endDate_calendarObject.setOnSelectedDelegate(function() {
        endDate_calendarObject.closeCalendar();
        populateDate(endDate_calendarObject, "endDate", "endDateError", "* End Date");
    });

    function populateDate(calendarObject, dateField, dateFieldErrorDiv, errorMessage) {
        try {
            var obj = calendarObject.getSelectedDay();
            var day = String(obj.day);
            if (day.length == 1) {
                day = "0" + day;
            }
            var month = String(obj.month);
            if (month.length == 1) {
                month = "0" + month;
            }
            //MM/DD/YYYY
            var date = month + "/" + day + "/" + obj.year;

            $("#" + dateField).val(date);
            var sucess = validateDate(dateField, dateFieldErrorDiv, errorMessage);
            if (sucess > 0) {
                var validationStatus = validateResourceEditHrsForm();
                if (validationStatus) {
                    loadScheduleSettings();
                } else {
                    scrollToTopOfThePage();
                }
            }
        } catch (e) {
            alert("Error : " + e);
        }
    }

    //defaultly making closing
    //showOrHideDayWiseStartEndAndBreakTimeDetsils("sunDay");
    //showOrHideDayWiseStartEndAndBreakTimeDetsils("monDay");
    //showOrHideDayWiseStartEndAndBreakTimeDetsils("tuesDay");
    //showOrHideDayWiseStartEndAndBreakTimeDetsils("wednesDay");
    //showOrHideDayWiseStartEndAndBreakTimeDetsils("thursDay");
    //showOrHideDayWiseStartEndAndBreakTimeDetsils("friDay");
    //showOrHideDayWiseStartEndAndBreakTimeDetsils("saturDay");

    //Sunday Open close operation
    $("#sunDayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils("sunDay");
    });

    $("#sunDayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils("sunDay");
    });

    //Monday Open close operation
    $("#monDayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils("monDay");
    });

    $("#monDayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils("monDay");
    });

    //Tuesday Open close operation
    $("#tuesDayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils("tuesDay");
    });

    $("#tuesDayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils("tuesDay");
    });

    //Wednesday Open close operation
    $("#wednesDayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils("wednesDay");
    });

    $("#wednesDayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils("wednesDay");
    });

    //Thursday Open close operation
    $("#thursDayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils("thursDay");
    });

    $("#thursDayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils("thursDay");
    });

    //Friday Open close operation
    $("#friDayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils("friDay");
    });

    $("#friDayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils("friDay");
    });

    //Saturday Open close operation
    $("#saturDayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils("saturDay");
    });

    $("#saturDayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils("saturDay");
    });

	$('#resetResourceDateRange0WorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
		showBlockUI();
		$.get("clearResourceWorkingHoursDateRangeSessionData.html",function(data) {
			$('#updateResourceDateRange0WorkingHoursForm').trigger("reset");
		});
		showUnBlockUI();
		jQuery.ajaxSetup({async:true});
	});
	
    $('#updateResourceDateRange0WorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
        try {
            var validationStatus = validateResourceEditHrsForm();
            if (validationStatus) {
                var validationDetails = validateScheduleSettings();
                $("#schdSettingsError").html(validationDetails);

                if (validationDetails == "") {
                    var scheduleDetails = getDayScheduleDetails("sunDay", "Sunday");
                    scheduleDetails += " \n " + getDayScheduleDetails("monDay", "Monday");
                    scheduleDetails += " \n " + getDayScheduleDetails("tuesDay", "Tuesday");
                    scheduleDetails += " \n " + getDayScheduleDetails("wednesDay", "Wednesday");
                    scheduleDetails += " \n " + getDayScheduleDetails("thursDay", "Thursday");
                    scheduleDetails += " \n " + getDayScheduleDetails("friDay", "Friday");
                    scheduleDetails += " \n " + getDayScheduleDetails("saturDay", "Saturday");
                    var isConfirmed = confirm(scheduleDetails);
                    if (isConfirmed) {
						showBlockUI();
                        $("#resetResourceDateRange0WorkingHoursBtn").hide();   
						$("#updateResourceDateRange0WorkingHoursBtn").hide(); 
						var resourceSelectedList = getSelectedResourceList();						
						var formData = $("#updateResourceDateRange0WorkingHoursForm").serialize()+"&resourceSelectedList="+resourceSelectedList;
						//alert("Form Data ::: "+formData);
						var url = "updateResourceWorkingHours.html";
						//alert("Server URL ::: "+url);
						$.post(url,formData,function(data) {
							try{
								//alert("Response  From Server ::: "+data);
								var resourceWorkingHrsResponse = $.parseJSON(data);
								if(resourceWorkingHrsResponse.updateSucessfully==true && resourceWorkingHrsResponse.alreadyAppointBooked==false){
									$("#resetResourceDateRange0WorkingHoursBtn").show();   
									$("#updateResourceDateRange0WorkingHoursBtn").show();  
									scrollToTopOfThePage();	
									$("#scheduleSettingsDiv").hide();
									$("#displacedApptsDetailsDiv").hide();
									$('#updateResourceDateRange0WorkingHoursForm').trigger("reset");
									$("#sucessesMsgDivId").html("<b>Edit Hours - Date Range details updated successfully!</b>");
								} else if(resourceWorkingHrsResponse.alreadyAppointBooked==true) {
									populateDisplacedApptsTable(resourceWorkingHrsResponse.displacedCustomers);
								} else {
									$("#resetResourceDateRange0WorkingHoursBtn").show();   
									$("#updateResourceDateRange0WorkingHoursBtn").show();  
									$("#errorMsgDivId").html("<b>Error while updating Edit Hours - Date Range working hours</b>");
									scrollToTopOfThePage();	
								}
							}catch(e){
								alert("Error : "+e);
							}
						});
                    }
                } else {
                    scrollToTopOfThePage();
                }

            } else {
                scrollToTopOfThePage();
            }
        } catch (e) {
            alert("Error : " + e);
        }
		showUnBlockUI();
		jQuery.ajaxSetup({async:true});
    });
	
	$('#displacedApptsCancelResourceDateRangeWorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
		showBlockUI();
		$.get("clearResourceWorkingHoursDateRangeSessionData.html",function(data) {			 
		});
		showUnBlockUI();
		jQuery.ajaxSetup({async:true});
	});
	
	$('#displacedApptsProceedResourceDateRangeWorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
		showBlockUI();
		$.get("proceedResourceDateRangeWorkingHoursUpdate.html",function(data) {
			 try{
				//alert("Response  From Server ::: "+data);
				var resourceWorkingHrsResponse = $.parseJSON(data);
				if(resourceWorkingHrsResponse.updateSucessfully==true && resourceWorkingHrsResponse.alreadyAppointBooked==false){
					$("#displacedApptsDetailsDiv").hide();   
					$("#scheduleSettingsDiv").hide();  
					scrollToTopOfThePage();	 
				} else {
					alert("Error while updating resource working hours");
				}
			}catch(e){
				alert("Error : "+e);
			}
		});
		showUnBlockUI();
		jQuery.ajaxSetup({async:true});
	});
});

function loadScheduleSettingsAfterResourceSelection() {
	var prevSelResourceLen = $("#prevSelResourceLen").val();
	var curSelResourceLen = $('input[name="selResource"]:checked').length;
	$("#prevSelResourceLen").val(curSelResourceLen);
	if (prevSelResourceLen == 0 && curSelResourceLen == 1) {
		var validationStatus = validateResourceEditHrsForm();
		if (validationStatus) {
			loadScheduleSettings();
		} else {
			scrollToTopOfThePage();
		}
	}
}
	
function populateResourcesForSelectedLocation() {
	try {
		var locationId = $("#locationSel").val();
		if(locationId!="-1"){
			$('#locationSelErrorDiv').html("");
			var url = "getResourceListByLocationId.html?locationId=" + locationId;
			//alert("url :::::::: "+url);		
			$.get(url, function(data) {
				if (data != "" && data != null && data != undefined) {
					var htmldata = "";
					$.each($.parseJSON(data), function(index, item) {
						htmldata += "<input id='selResource' name='selResource' class='noWidth' type='checkbox' value='" + item.resourceId + "' onchange='loadScheduleSettingsAfterResourceSelection();'>";
						htmldata += "<label for='selResource'>" + item.firstName + "  " + item.lastName + "</label>";
					});
					$("#selResourceDiv").html(htmldata);
					//alert("htmldata :::::::: "+htmldata);	
				}
			});
		}else {				
			$('#locationSelErrorDiv').html("<b>* Please Select Location.</b>");
		}
	} catch (e) {
	   alert("Error : " + e);
	}
}

function showOrHideDayWiseStartEndAndBreakTimeDetsils(day) {
	var dayOpenClose = $("#" + day + "OpenClose").is(":checked");
	if (dayOpenClose == true) {
		$("#" + day + "StartTimeDiv").show();
		$("#" + day + "EndTimeDiv").show();
		$("#" + day + "BreakTimeOpenCloseDiv").show();
		$("#" + day + "BreakTimeDiv").show();
	} else {
		$("#" + day + "StartTimeDiv").hide();
		$("#" + day + "EndTimeDiv").hide();
		$("#" + day + "BreakTimeOpenCloseDiv").hide();
		$("#" + day + "BreakTimeDiv").hide();
	}
}

function showOrHideDayWiseBreakTimeDetsils(day) {
	var breakTimeOpenClose = $("#" + day + "BreakTimeOpenClose").is(":checked");
	if (breakTimeOpenClose == true) {
		$("#" + day + "BreakTimeDiv").show();
	} else {
		$("#" + day + "BreakTimeDiv").hide();
	}
}

function populateDisplacedApptsTable(displacedApptsList){
	//alert("displacedApptsList :::: "+displacedApptsList);
	$("#displacedApptsDetailsDiv").show();
	$('#displacedApptsDetailsTable').DataTable().destroy();
	try {
		$('#displacedApptsDetailsTable').DataTable({
			data: displacedApptsList,
			"pagingType": "full_numbers",
			scrollY: '100vh',
			//stateSave: true,
			scrollCollapse: true,
			//"lengthMenu": [[10],[10]],
			"lengthChange": false,
			"pageLength": 10,
			"columns": [
				{"data": "account", "ordering": false,"defaultContent": ""},
				{"data": "patientFirstName", "ordering": false,"defaultContent": ""},
				{"data": "patientLastName", "ordering": false,"defaultContent": ""},
				{"data": "patientEmail", "ordering": false,"defaultContent": ""},
				{"data": "patientConP", "ordering": false,"defaultContent": ""}
			],
			"dom": 'lBfrtip',
			buttons: [
				{extend: 'colvis',columns: ':gt(0)'},
				{extend: 'copy'},
				{extend: 'csv',title: 'Displaced Appointments'},
				{extend: 'excel',title: 'Displaced Appointments'},
				{extend: 'pdf',title: 'Displaced Appointments'},
				{extend: 'print'}
			]
		});
	}catch(ex){
		alert("Exception ::: "+ex);
	}
}

function validateResourceEditHrsForm() {
	try {
		var sucessCount = 0;
		var resourceSelectedList = getSelectedResourceList();
		var locationId = $("#locationSel").val();
		
		var rdoEndDate = $('input:radio[name=rdoEndDate]:checked').val();
		var startDate = $('#startDate').val();
		var endDate = $("#endDate").val();

		if (locationId != '-1') {
			sucessCount = sucessCount + 1;
			$('#locationSelErrorDiv').html("");
		} else {
			$('#locationSelErrorDiv').html("<b>* Please Select Location.</b>");
		}
		
		if (resourceSelectedList != '') {
			sucessCount = sucessCount + 1;
			$('#resourceSelectedListError').html("");
		} else {
			$('#resourceSelectedListError').html("<b>* Please Select Resource.</b>");
		}

		sucessCount = sucessCount + validateDate("startDate", "startDateError", "* Start Date");

		if (rdoEndDate == "Forever") {
			sucessCount = sucessCount + 1;
		} else if (rdoEndDate == "EndDate") {
			sucessCount = sucessCount + validateDate("endDate", "endDateError", "* End Date");
		}

		if (sucessCount == 4) {
			if (endDate != "" || rdoEndDate == "Forever") {
				var status = 0;
				if (rdoEndDate == "Forever") {
					status = 1;
				} else {
					status = compareMMDDYYYYDateswithMaxDays(startDate, endDate, 6);
				}
				//dates are proper
				if (status > 0) {
					$('#endDateError').html("");
					var validationDetails = validateScheduleSettings();
					$("#schdSettingsError").html(validationDetails);

					if (validationDetails == "") {
						return true;
					} else {
						return false;
					}
				} else {
					$('#endDateError').html("* End date should be greater than or equal to 7 days from Start date.");
					scrollToTopOfThePage();
					return false;
				}
			} else {
				return false;
			}
		} else {
			scrollToTopOfThePage();
			return false;
		}
	} catch (e) {
		alert("Error : " + e);
		return false;
	}
}

function getSelectedResourceList() {
	var selectedResourceList = [];
	$('input[name="selResource"]:checked').each(function() {
		if ($(this).is(':checked')) {
			selectedResourceList.push($(this).val());
		}
	});
	return selectedResourceList;
}

function validateDate(fieldId, errorDivId, messageField) {
	try {
		if (checkMMDDYYYYDateFields(fieldId, errorDivId, messageField) > 0) {
			if (compareMMDDYYYYDateFields($('#' + fieldId).val()) >= 0) {
				$('#' + errorDivId).html(" ");
				return 1;
			} else {
				$('#' + errorDivId).html(messageField + " should be future date.");
				$("#" + fieldId).focus();
				return 0;
			}
		} else {
			return 0;
		}
	} catch (e) {
		alert("Exception  : " + e);
	}
}

function loadScheduleSettings() {
	showBlockUI();
	try {			
		var locationId = $("#locationSel").val();			
		var resourceSelectedList = getSelectedResourceList();
		var rdoEndDate = $('input:radio[name=rdoEndDate]:checked').val();
		var startDate = $('#startDate').val();
		var endDate = "";

		if (rdoEndDate == "Forever") {
			endDate = startDate;
		} else {
			endDate = $("#endDate").val();
		}
		
		var url = "getSuggestedResourceWorkingHours.html?locationId="+locationId+"&resourceIds="+resourceSelectedList+"&fromDate="+startDate+"&toDate="+endDate
		//alert("url :::::::: "+url);		
		$.get(url, function(data) {
			$("#scheduleSettingsDiv").show();				
			var data = $.parseJSON(data);
			
			//Sunday
			if(data.is_sun_open=="Y"){
				$("#sunDayOpenClose").prop("checked",true);
				$("#sunDayStartTimeHr").val(data.sun_start_hour);
				$("#sunDayStartTimeMin").val(data.sun_start_min);
				$("#sunDayStartTimeAmPm").val(data.sun_start_meridian);
				
				$("#sunDayEndTimeHr").val(data.sun_end_hour);
				$("#sunDayEndTimeMin").val(data.sun_end_min);
				$("#sunDayEndTimeAmPm").val(data.sun_end_meridian);
				
				if(data.is_sun_no_break_time=="Y"){
					$("#sunDayBreakTimeOpenClose").prop("checked",false);
				} else {
					$("#sunDayBreakTimeOpenClose").prop("checked",true);
					$("#sunDayBreakTimeHr").val(data.sun_break_time_hour);
					$("#sunDayBreakTimeMin").val(data.sun_break_time_min);
					$("#sunDayBreakTimeAmPm").val(data.sun_break_time_meridian);
					$("#sunDayBreakDuration").val(data.sun_break_time_1_mins);
				}
			} else {
				$("#sunDayOpenClose").prop("checked",false);
			}
			
			//Monday
			if(data.is_mon_open=="Y"){
				$("#monDayOpenClose").prop("checked",true);
				$("#monDayStartTimeHr").val(data.mon_start_hour);
				$("#monDayStartTimeMin").val(data.mon_start_min);
				$("#monDayStartTimeAmPm").val(data.mon_start_meridian);

				$("#monDayEndTimeHr").val(data.mon_end_hour);
				$("#monDayEndTimeMin").val(data.mon_end_min);
				$("#monDayEndTimeAmPm").val(data.mon_end_meridian);

				if(data.is_mon_no_break_time=="Y"){
					$("#monDayBreakTimeOpenClose").prop("checked",false);
				} else {
					$("#monDayBreakTimeOpenClose").prop("checked",true);
					$("#monDayBreakTimeHr").val(data.mon_break_time_hour);
					$("#monDayBreakTimeMin").val(data.mon_break_time_min);
					$("#monDayBreakTimeAmPm").val(data.mon_break_time_meridian);
					$("#monDayBreakDuration").val(data.mon_break_time_1_mins);
				}
			} else {
				$("#monDayOpenClose").prop("checked",false);
			}
			
			//Tuesday
			if(data.is_tue_open=="Y"){
				$("#tueDayOpenClose").prop("checked",true);
				$("#tuesDayOpenClose").prop("checked",true);
				$("#tuesDayStartTimeHr").val(data.tue_start_hour);
				$("#tuesDayStartTimeMin").val(data.tue_start_min);
				$("#tuesDayStartTimeAmPm").val(data.tue_start_meridian);

				$("#tuesDayEndTimeHr").val(data.tue_end_hour);
				$("#tuesDayEndTimeMin").val(data.tue_end_min);
				$("#tuesDayEndTimeAmPm").val(data.tue_end_meridian);

				if(data.is_tue_no_break_time=="Y"){
					$("#tuesDayBreakTimeOpenClose").prop("checked",false);
				} else {
					$("#tuesDayBreakTimeOpenClose").prop("checked",true);
					$("#tuesDayBreakTimeHr").val(data.tue_break_time_hour);
					$("#tuesDayBreakTimeMin").val(data.tue_break_time_min);
					$("#tuesDayBreakTimeAmPm").val(data.tue_break_time_meridian);
					$("#tuesDayBreakDuration").val(data.tue_break_time_1_mins);
				}				} else {
				$("#tuesDayOpenClose").prop("checked",false);
			}
			
			//Wednesday
			if(data.is_wed_open=="Y"){
				$("#wednesDayOpenClose").prop("checked",true);
				$("#wednesDayStartTimeHr").val(data.wed_start_hour);
				$("#wednesDayStartTimeMin").val(data.wed_start_min);
				$("#wednesDayStartTimeAmPm").val(data.wed_start_meridian);

				$("#wednesDayEndTimeHr").val(data.wed_end_hour);
				$("#wednesDayEndTimeMin").val(data.wed_end_min);
				$("#wednesDayEndTimeAmPm").val(data.wed_end_meridian);

				if(data.is_wed_no_break_time=="Y"){
					$("#wednesDayBreakTimeOpenClose").prop("checked",false);
				} else {
					$("#wednesDayBreakTimeOpenClose").prop("checked",true);
					$("#wednesDayBreakTimeHr").val(data.wed_break_time_hour);
					$("#wednesDayBreakTimeMin").val(data.wed_break_time_min);
					$("#wednesDayBreakTimeAmPm").val(data.wed_break_time_meridian);
					$("#wednesDayBreakDuration").val(data.wed_break_time_1_mins);
				}
			} else {
				$("#wednesDayOpenClose").prop("checked",false);
			}
			
			//Thursday
			if(data.is_thu_open=="Y"){
				$("#thursDayOpenClose").prop("checked",true);
				$("#thursDayStartTimeHr").val(data.thu_start_hour);
				$("#thursDayStartTimeMin").val(data.thu_start_min);
				$("#thursDayStartTimeAmPm").val(data.thu_start_meridian);

				$("#thursDayEndTimeHr").val(data.thu_end_hour);
				$("#thursDayEndTimeMin").val(data.thu_end_min);
				$("#thursDayEndTimeAmPm").val(data.thu_end_meridian);

				if(data.is_thu_no_break_time=="Y"){
					$("#thursDayBreakTimeOpenClose").prop("checked",false);
				} else {
					$("#thursDayBreakTimeOpenClose").prop("checked",true);
					$("#thursDayBreakTimeHr").val(data.thu_break_time_hour);
					$("#thursDayBreakTimeMin").val(data.thu_break_time_min);
					$("#thursDayBreakTimeAmPm").val(data.thu_break_time_meridian);
					$("#thursDayBreakDuration").val(data.thu_break_time_1_mins);
				}
			} else {
				$("#thursDayOpenClose").prop("checked",false);
			}
							
			//Friday
			if(data.is_fri_open=="Y"){
				$("#friDayOpenClose").prop("checked",true);
				$("#friDayStartTimeHr").val(data.fri_start_hour);
				$("#friDayStartTimeMin").val(data.fri_start_min);
				$("#friDayStartTimeAmPm").val(data.fri_start_meridian);

				$("#friDayEndTimeHr").val(data.fri_end_hour);
				$("#friDayEndTimeMin").val(data.fri_end_min);
				$("#friDayEndTimeAmPm").val(data.fri_end_meridian);

				if(data.is_fri_no_break_time=="Y"){
					$("#friDayBreakTimeOpenClose").prop("checked",false);
				} else {
					$("#friDayBreakTimeOpenClose").prop("checked",true);
					$("#friDayBreakTimeHr").val(data.fri_break_time_hour);
					$("#friDayBreakTimeMin").val(data.fri_break_time_min);
					$("#friDayBreakTimeAmPm").val(data.fri_break_time_meridian);
					$("#friDayBreakDuration").val(data.fri_break_time_1_mins);
				}
			} else {
				$("#friDayOpenClose").prop("checked",false);
			}
							
			//Saturday
			if(data.is_sat_open=="Y"){
				$("#saturDayOpenClose").prop("checked",true);
				$("#saturDayStartTimeHr").val(data.sat_start_hour);
				$("#saturDayStartTimeMin").val(data.sat_start_min);
				$("#saturDayStartTimeAmPm").val(data.sat_start_meridian);

				$("#saturDayEndTimeHr").val(data.sat_end_hour);
				$("#saturDayEndTimeMin").val(data.sat_end_min);
				$("#saturDayEndTimeAmPm").val(data.sat_end_meridian);

				if(data.is_sat_no_break_time=="Y"){
					$("#saturDayBreakTimeOpenClose").prop("checked",false);
				} else {
					$("#saturDayBreakTimeOpenClose").prop("checked",true);
					$("#saturDayBreakTimeHr").val(data.sat_break_time_hour);
					$("#saturDayBreakTimeMin").val(data.sat_break_time_min);
					$("#saturDayBreakTimeAmPm").val(data.sat_break_time_meridian);
					$("#saturDayBreakDuration").val(data.sat_break_time_1_mins);
				}
			} else {
				$("#saturDayOpenClose").prop("checked",false);
			}
							 
			var days = ['sunDay','monDay','tuesDay','wednesDay','thursDay','friDay','saturDay'];
			for (index = 0; index < days.length; index++) { 
				var day = days[index];
				//alert("day ::: "+day);
				showOrHideDayWiseStartEndAndBreakTimeDetsils(day);
				showOrHideDayWiseBreakTimeDetsils(day);
			}
			showUnBlockUI();
		});
	} catch (e) {
		alert("Error : " + e);
	}		
}