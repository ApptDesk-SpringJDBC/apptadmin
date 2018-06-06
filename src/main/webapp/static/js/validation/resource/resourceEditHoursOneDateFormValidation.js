$(document).ready(function() {
	
	populateSpecificDatesResponseTable();
	
    g_calendarObject = new JsDatePick({
		useMode:2,
		target:"startDate0",
		dateFormat : "%M/%d/%Y"
	});

	g_calendarObject.setOnSelectedDelegate(function(){
		g_calendarObject.closeCalendar ();
		$("#addMoreDatesDiv").show();
		processesSelectedDate();
	});
	
	//Day Open close operation
    $("#dayOpenClose").click(function() {
        showOrHideDayWiseStartEndAndBreakTimeDetsils();
    });

    $("#dayBreakTimeOpenClose").click(function() {
        showOrHideDayWiseBreakTimeDetsils();
    });
	
	$('#resetResourceOneDateWorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
		showBlockUI();
		$.get("clearResourceWorkingHoursDateRangeSessionData.html",function(data) {
			$('#updateResourceDateRange0WorkingHoursForm').trigger("reset");
		});
		showUnBlockUI();
		jQuery.ajaxSetup({async:true});
	});
	
    $('#updateResourceOneDateWorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
		showBlockUI();
        try {
            var validationStatus = validateResourceEditHrsForm();
            if (validationStatus) {
                var validationDetails = validateScheduleSettings();
                $("#schdSettingsError").html(validationDetails);

                if (validationDetails == "") {
                    var scheduleDetails = getDayScheduleDetails("day", "Day"); 
                    var isConfirmed = confirm(scheduleDetails);
                    if (isConfirmed) {						
                        $("#resetResourceDateRange0WorkingHoursBtn").hide();   
						$("#updateResourceDateRange0WorkingHoursBtn").hide(); 
						var resourceSelectedList = getSelectedResourceList();
						$("#resourceSelectedList").val(resourceSelectedList);
						var formData = $("#updateResourceOneDateWorkingHoursForm").serialize();
						//alert("Form Data ::: "+formData);
						var url = "updateOneDateResourceWorkingHrs.html";
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
									$('#updateResourceOneDateWorkingHoursForm').trigger("reset");
									$("#sucessesMsgDivId").html("<b>Edit Hours - One Date details updated successfully!</b>");
									populateSpecificDatesResponseTable();
								} else if(resourceWorkingHrsResponse.alreadyAppointBooked==true) {
									populateDisplacedApptsTable(resourceWorkingHrsResponse.displacedCustomers);
								} else {
									$("#resetResourceDateRange0WorkingHoursBtn").show();   
									$("#updateResourceDateRange0WorkingHoursBtn").show();  
									$("#errorMsgDivId").html("<b>Error while updating Edit Hours - One Date working hours</b>");
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
	
	$('#displacedApptsCancelResourceOneDateWorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
		showBlockUI();
		$.get("clearResourceWorkingHoursOneDateSessionData.html",function(data) {			 
		});
		showUnBlockUI();
		jQuery.ajaxSetup({async:true});
	});
	
	$('#displacedApptsProceedResourceOneDateWorkingHoursBtn').click(function() {
		jQuery.ajaxSetup({async:false});
		showBlockUI();
		$.get("proceedResourceOneDateWorkingHoursUpdate.html",function(data) {
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

function populateSpecificDatesResponseTable(){
	$('#specificDatesTable').DataTable().destroy();
	try {
		$.get("getOneDateResourceWorkingHrsDetails.html",function(data) {
			 try{
				//alert("Response  From Server ::: "+data);
				var oneDateResourceWorkingHoursResponse = $.parseJSON(data);
				var oneDateResourceWorkingHoursList = oneDateResourceWorkingHoursResponse.oneDateResourceWorkingHoursList;
				$('#specificDatesTable').DataTable({
					data: oneDateResourceWorkingHoursList,
					"pagingType": "full_numbers",
					scrollY: '100vh',
					//stateSave: true,
					scrollCollapse: true,
					"lengthMenu": [[10,50,100],[10,50,100]],
					//"lengthChange": false,
					//"pageLength": 10,
					"columns": [
						{"data": "userName", "ordering": false,"defaultContent": ""},
						{"data": "timestamp", "ordering": false,"defaultContent": ""},
						{"data": "locationName", "ordering": false,"defaultContent": ""},
						{"data": function (data, type, dataToSet) {
									return data.prefix + " " + data.firstName+ " " + data.lastName;
								}, "ordering": false,"defaultContent": ""},
						{"data": "dateStr", "ordering": false,"defaultContent": ""},
						{"data": function (data, type, dataToSet) {
									return data.startTime1 + " - " + data.endTime1;
								}, "ordering": false,"defaultContent": ""},
						{"data": function (data, type, dataToSet) {
									return data.startTime2 + " - " + data.endTime2;
								}, "ordering": false,"defaultContent": ""}
					],
					"dom": 'lBfrtip',
					buttons: [
						{extend: 'colvis',columns: ':gt(0)'},
						{extend: 'copy'},
						{extend: 'csv',title: 'Specific Dates'},
						{extend: 'excel',title: 'Specific Dates'},
						{extend: 'pdf',title: 'Specific Dates'},
						{extend: 'print'}
					]
				});
			}catch(e){
				alert("Error : "+e);
			}
		});		
	}catch(ex){
		alert("Exception ::: "+ex);
	}
}


function processesSelectedDate(){
	try {		
		var obj = g_calendarObject.getSelectedDay();				
		
		var day = String(obj.day);
		if(day.length==1){
			day = "0"+day;
		}		
		var month = String(obj.month);
		if(month.length==1){
			month = "0"+month;
		}			
		//MM/DD/YYYY 
		var date =  month + "/" +day + "/" +  obj.year;
		$("#startDate0").val(date);	
		loadScheduleSettings();
	} catch (e) {
		//alert("Error : "+e);
	}
}
	
function validate(){
	var validationStatus = validateResourceEditHrsForm();
	if (validationStatus) {
		validationStatus = validateOneDateDates();
		if (validationStatus) {
			return true;
		} else {
			scrollToTopOfThePage();
			return false;
		}			
	} else {
		scrollToTopOfThePage();
		return false;
	}
}
	
function loadScheduleSettings() {
	try {	
		jQuery.ajaxSetup({async:false});
		showBlockUI();
		try {	
			var validationStatus = validate();	
			if (validationStatus) {				
				var locationId = $("#locationSel").val();			
				var resourceSelectedList = getSelectedResourceList();
				var rdoEndDate = $('input:radio[name=rdoEndDate]:checked').val();
				var date = $('#startDate0').val();
				
				var url = "getSuggestedOneDateResourceWorkingHrs.html?locationId="+locationId+"&resourceId="+resourceSelectedList+"&date="+date;
				//alert("url :::::::: "+url);		
				$.get(url, function(data) {
					//alert("data :::::::: "+data);	
					$("#scheduleSettingsDiv").show();				
					var data = $.parseJSON(data);
					if(data.status==true){
						var oneDateWorkingHours = data.oneDateWorkingHours;
						//alert("oneDateWorkingHours :::::::: "+oneDateWorkingHours);	
						//Day
						if(oneDateWorkingHours.dayOpen==true){
							$("#dayOpenClose").prop("checked",true);
							var selectedStartTime = oneDateWorkingHours.selectedStartTime; //08:15 AM
							var selectedStartTimeHRMINAndMeridiemArr = selectedStartTime.split(" ");
							var selectedStartTimeHRMINArr = selectedStartTimeHRMINAndMeridiemArr[0].split(":");
							var selectedStartTimeMeridiem = selectedStartTimeHRMINAndMeridiemArr[1];
							
							$("#dayStartTimeHr").val(selectedStartTimeHRMINArr[0]);
							$("#dayStartTimeMin").val(selectedStartTimeHRMINArr[1]);
							$("#dayStartTimeAmPm").val(selectedStartTimeMeridiem);
							
							var selectedEndTime = oneDateWorkingHours.selectedEndTime; //08:15 AM
							var selectedEndTimeHRMINAndMeridiemArr = selectedEndTime.split(" ");
							var selectedEndTimeHRMINArr = selectedEndTimeHRMINAndMeridiemArr[0].split(":");
							var selectedEndTimeMeridiem = selectedEndTimeHRMINAndMeridiemArr[1];
							
							$("#dayEndTimeHr").val(selectedEndTimeHRMINArr[0]);
							$("#dayEndTimeMin").val(selectedEndTimeHRMINArr[1]);
							$("#dayEndTimeAmPm").val(selectedEndTimeMeridiem);
							
							if(oneDateWorkingHours.breakTimeOpen==false){
								$("#dayBreakTimeOpenClose").prop("checked",false);
							} else {
								$("#dayBreakTimeOpenClose").prop("checked",true);
								
								var selectedBreakTime = oneDateWorkingHours.selectedBreakTime; //08:15 AM
								var selectedBreakTimeHRMINAndMeridiemArr = selectedBreakTime.split(" ");
								var selectedBreakTimeHRMINArr = selectedBreakTimeHRMINAndMeridiemArr[0].split(":");
								var selectedBreakTimeMeridiem = selectedBreakTimeHRMINAndMeridiemArr[1];
							
								$("#dayBreakTimeHr").val(selectedBreakTimeHRMINArr[0]);
								$("#dayBreakTimeMin").val(selectedBreakTimeHRMINArr[1]);
								$("#dayBreakTimeAmPm").val(selectedBreakTimeMeridiem);
								$("#dayBreakDuration").val(oneDateWorkingHours.selectedDuration);
							}
						} else {
							$("#dayOpenClose").prop("checked",false);
						}														 
						showOrHideDayWiseStartEndAndBreakTimeDetsils();
						showOrHideDayWiseBreakTimeDetsils();	
					}
				});
			} else {
				scrollToTopOfThePage();
			}
		} catch (e) {
			alert("Error : " + e);
		}
		showUnBlockUI();
		jQuery.ajaxSetup({async:true});
	} catch (e) {
		alert("Error : " + e);
	}
}
	

function showOrHideDayWiseStartEndAndBreakTimeDetsils() {
	var dayOpenClose = $("#dayOpenClose").is(":checked");
	if (dayOpenClose == true) {
		$("#dayStartTimeDiv").show();
		$("#dayEndTimeDiv").show();
		$("#dayBreakTimeOpenCloseDiv").show();
		$("#dayBreakTimeDiv").show();
	} else {
		$("#dayStartTimeDiv").hide();
		$("#dayEndTimeDiv").hide();
		$("#dayBreakTimeOpenCloseDiv").hide();
		$("#dayBreakTimeDiv").hide();
	}
}

function showOrHideDayWiseBreakTimeDetsils() {
	var breakTimeOpenClose = $("#dayBreakTimeOpenClose").is(":checked");
	if (breakTimeOpenClose == true) {
		$("#dayBreakTimeDiv").show();
	} else {
		$("#dayBreakTimeDiv").hide();
	}
}

function validateResourceEditHrsForm() {
	jQuery.ajaxSetup({async:false});
	var isValid = false;
	try {
		var sucessCount = 0;
		var resourceSelectedList = getSelectedResourceList();
		var locationId = $('#selLocationValue').val(); 
		var startDate = $('#startDate').val();
		
		if (locationId != '') {
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
		
		sucessCount = sucessCount + validateDate("startDate0", "startDateError", "* Date"); 
		if (sucessCount == 3) { 
			 
			var url = "validateDateAgainestClosedDaysAndHolidays.html?locationId="+locationId+"&date="+$("#startDate0").val();					
			$.get(url, function(data) {	
				data = $.parseJSON(data);				
				if(data.status){
					 $("#startDateError").html("");
					 isValid = true;
				 }else{					 
					$("#startDateError").html(data);
				 }
			});               
		}
	} catch (e) {
		alert("Error : " + e);
	}	
	scrollToTopOfThePage();	
	jQuery.ajaxSetup({async:true});
	return isValid;
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
	
function validateOneDateDates(){
	try{
		//var successCount = 0;
		var isValid = true;
		var atleastOneSelected = false;
		var errorMessage = "";
		var error = "";
		var count = $("#moreDatesCount").val();
		for(var i=0;i<=count;i++){
			//alert("I :::: "+i);
			if($("#startDate"+i).val()!=""){
				atleastOneSelected = true;
				if(validateDate("startDate"+i,"startDateError","Date")==0){
					isValid = false;
					//alert("isValid = true; :::: "+isValid);
					error = $('#startDateError').html();
					if(error!=""){
						error = " Date : "+(i+1)+" - "+error;
						if(errorMessage!=""){
							errorMessage = errorMessage  +"<br/>" ;
						}
						errorMessage = errorMessage  +error ;
					}
					//alert("errorMessage; :::: "+errorMessage);
				}
			}
		}
		$('#startDateError').html(errorMessage); 
		if(isValid && atleastOneSelected){
			return true;
		}else{
			return false;
		}
	}catch(e){
		//alert("Exception : "+e);
	}
}
	
function loadScheduleSettingsAfterResourceSelection() {
	try {
		var prevSelResourceLen = $('#prevSelResourceLen').val();
		var curSelResourceLen = $('input[name="selResource"]:checked').length;
		$("#prevSelResourceLen").val(curSelResourceLen);
		if (prevSelResourceLen == 0 && curSelResourceLen == 1) {
			loadScheduleSettings();            
		}
	}catch(e){
		//alert("Exception : "+e);
	}
}
	
function populateResourcesForOneDateSelectedLocation() {
	jQuery.ajaxSetup({async:false});
	showBlockUI();
	try {
		$('#scheduleSettingsDiv').hide();
		var locationId = $("#locationSel").val();
		if(locationId!="-1"){
			$('#locationSelErrorDiv').html("");
			var url = "getResourceListByLocationId.html?locationId=" + locationId;
			//alert("url :::::::: "+url);		
			$.get(url, function(data) {
				if (data != "" && data != null && data != undefined) {
					var htmldata = "";
					$.each($.parseJSON(data), function(index, item) {
						htmldata += "<input id='selResource' name='selResource' class='noWidth' type='checkbox' value='" + item.resourceId + "'  onchange='loadScheduleSettingsAfterResourceSelection();'>";
						htmldata += "<label for='selResource'>" + item.firstName + "  " + item.lastName + "</label>";
					});
					//console.log("htmldata + script:::::::: "+(htmldata));
					$("#selResourceDiv").html(htmldata);
					//console.log("htmldata + script:::::::: "+(htmldata));
					$("#prevSelResourceLen").val("0");
				}
			});
		}else {
			$('#locationSelErrorDiv').html("<b>* Please Select Location.</b>");
		}
	} catch (e) {
		alert("Error : " + e);
	}
	showUnBlockUI();
	jQuery.ajaxSetup({async:true});
}

function addMoreDates(){
	try {
		var count = $("#moreDatesCount").val();
		if(validateDate("startDate"+count,"startDateError","Date ")!=0){
			count = parseInt(count)+1;
			var html = "<tr><td><input type=\"text\" size='12' name=\"oneDateDates["+(count)+"]\" id=\"startDate"+(count)+"\"></input></td></tr>"+
			"<script type=\"text/javascript\">new JsDatePick({useMode:2,target:\"startDate"+count+"\",dateFormat:\"%M/%d/%Y\"});"+
				"$('#startDate"+(count)+"').val($('#startDate"+(count-1)+"').val())"+	
			"<\/script>";
			//alert("Html :::: "+html);
			$('#moreDates tr').last().after(html);
			$("#moreDatesCount").val(count);
		}
	}catch(e){
		//alert("Error :::: "+e);
	}
}