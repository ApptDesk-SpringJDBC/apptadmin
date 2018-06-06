$(document).ready(function () {		
	var one_appt_per_term = $("#one_appt_per_term").val();
	var restrict_appt_window = $("#restrict_appt_window").val();
	var restrict_loc_appt_window = $("#restrict_loc_appt_window").val();

	try{
		if(one_appt_per_term=="Y" || one_appt_per_term=="y"){
			new JsDatePick({
				useMode : 2,
				target : "term_start_date",
				dateFormat : "%M/%d/%Y"
			});			
			new JsDatePick({
				useMode : 2,
				target : "term_end_date",
				dateFormat : "%M/%d/%Y"
			});
		}
	}catch(e){
		//alert("Exception ::: "+e);
	}

	try{
		if(restrict_appt_window=="Y" || restrict_appt_window=="y"){
			new JsDatePick({
				useMode : 2,
				target : "appt_start_date",
				dateFormat : "%M/%d/%Y"
			});			
			new JsDatePick({
				useMode : 2,
				target : "appt_end_date",
				dateFormat : "%M/%d/%Y"
			});
		}
	}catch(e){
		//alert("Exception ::: "+e);
	}

	try{
		var scheduler_closed = $("#scheduler_closed").val();				
		if((scheduler_closed=='N' || scheduler_closed=='n')){
			$("#opendSchedulerDiv").show();
			$("#closedSchedulerDiv").hide();
			$("#home_openstatusDiv").show();
			$("#home_closestatusDiv").hide();
		}else{
			$("#opendSchedulerDiv").hide();
			$("#closedSchedulerDiv").show();
			$("#home_openstatusDiv").hide();
			$("#home_closestatusDiv").show();
		}	
	}catch(e){
		//alert("Exception ::: "+e);
	}

	try{
		if(restrict_loc_appt_window=="Y" || restrict_loc_appt_window=="y"){
			var noOfLocations = $("#noOfLocations").val();	
			for(var i=0;i<noOfLocations;i++){
				new JsDatePick({
					useMode : 2,
					target : "appt_start_date_"+i,
					dateFormat : "%M/%d/%Y"
				});
				new JsDatePick({
					useMode : 2,
					target : "appt_end_date_"+i,
					dateFormat : "%M/%d/%Y"
				});
			}	
		}
	}catch(e){
		//alert("Exception ::: "+e);
	}
});

function openScheduler() { 
	var url = "updateScheduleClosedStatus.html?closedStatus=N&message=Scheduler_opened";
	$.get(url, function(data) {
		var json = $.parseJSON(data);
		if(json.status){	
			  $("#closedSchedulerDiv").hide();
			  $("#opendSchedulerDiv").show();
			  $("#home_openstatusDiv").show();
			  $("#home_closestatusDiv").hide();
			  $("#schedularValChangeResponce").html("Scheduler opened successfully!");
			  $("#schedularValChangeResponce").css('color', 'green');
	   }else{					     
			 $("#schedularValChangeResponce").html("Error while opening Scheduler!");
			 $("#schedularValChangeResponce").css('color', 'red');
	   }
  });
}

function closeScheduler() { 
	try{
		var isConfirmed = confirm("Do you want to close the Online / Phone Scheduler for Maintenance");
		if(isConfirmed){
			var columnValue="Y";
			var url = "updateScheduleClosedStatus.html?closedStatus=Y&message=Scheduler_closed";
			$.get(url, function(data) {
				var json = $.parseJSON(data);
				if(json.status){	
				  $("#opendSchedulerDiv").hide();
				  $("#closedSchedulerDiv").show();
				  $("#home_openstatusDiv").hide();
				  $("#home_closestatusDiv").show();
				  $("#schedularValChangeResponce").html("Scheduler closed successfully!");
				  $("#schedularValChangeResponce").css('color', 'green');
				}else{				
				  $("#schedularValChangeResponce").html("Error while closing Scheduler!");
				  $("#schedularValChangeResponce").css('color', 'red');
				}
			});
		}			 
	}catch(e){
		//alert("Error : "+e);
	}
}


function  updateApptRestrictDates() { 
	try{
		$("#apptRestrictResponce").html("");
		var sucessCount = validateDateField("appt_start_date","appt_start_dateError","* Start Date");
		sucessCount = sucessCount + validateDateField("appt_end_date","appt_end_dateError","* End Date");
		
		if(sucessCount==2){
			if(comparedates($('#appt_end_date').val(),$('#appt_start_date').val())>=0){
				var url = "updateApptRestrictDates.html?appt_start_date="+$('#appt_start_date').val()+"&appt_end_date="+$('#appt_end_date').val();	
				$.get(url, function(data) {
					var json = $.parseJSON(data);
					if(json.status){
						  $("#apptRestrictResponce").html("<b>Date update is saved successfully!</b>");
						  $("#apptRestrictResponce").css('color', 'green');
					}else{				
						  $("#apptRestrictResponce").html("<b>Error while updating dates!</b>");
						  $("#apptRestrictResponce").css('color', 'red');
					}
				});
			}else{
				$('#appt_end_dateError').html("* End date should be greater than or equal to Start date.");
			}
		}
	}catch(e){
		//alert("Error : "+e);
	}
}

function  updateApptPerSeasonDetails() { 
	try{
		$("#appt_per_term_Responce").html("");
		var sucessCount = validateDateField("term_start_date","term_start_date_Error","* Start Date");
		sucessCount = sucessCount + validateDateField("term_end_date","term_end_date_Error","* End Date");
		if(!isNaN($("#no_appt_per_term").val())){
			$('#no_appt_per_term_Error').html("");
			sucessCount = sucessCount + 1;
		}else{
			sucessCount = sucessCount - 1;
			$('#no_appt_per_term_Error').html("* No of allowed appts per season shoulbe be a number!");
		}
		//alert("sucessCount :::: "+sucessCount);

		if(sucessCount==3){
			if(comparedates($('#term_end_date').val(),$('#term_start_date').val())>=0){
				$('#term_end_date_Error').html("");
				var url = "updateApptPerSeasonDetails.html?term_start_date="+$('#term_start_date').val()+"&term_end_date="+$('#term_end_date').val()
					+"&no_appt_per_term="+$('#no_appt_per_term').val();	
				//alert("URL :::: "+url);
				$.get(url, function(data) {
					var json = $.parseJSON(data);
					if(json.status){
						  $("#appt_per_term_Responce").html("<b>Appointments season details saved successfully!</b>");
						  $("#appt_per_term_Responce").css('color', 'green');
					}else{				
						  $("#appt_per_term_Responce").html("<b>Error while updating Appointments season details!</b>");
						  $("#appt_per_term_Responce").css('color', 'red');
					}
				});
			}else{
				$('#term_end_date_Error').html("<b>* End date should be greater than or equal to Start date.<b/>");
			}
		}
	}catch(e){
		//alert("Error : "+e);
	}
}

function  updateLocationsApptDates() { 
	try{
		  	$("#loc_apptRestrictResponce").html("");
			$("#loc_appt_start_dateError").html("");
			$("#loc_appt_end_dateError").html("");
			$("#loc_appt_days_dateError").html("");

			var startDateEmptyWrongRows ="";
			var endDateEmptyWrongRows ="";
			var startDateFormatWrongRows ="";
			var endDateFormatWrongRows ="";
			
			var validationWrongRows ="";

			var noOfLocations = $("#noOfLocations").val();	
			
			for(var i=0;i<noOfLocations;i++){
				var appt_start_date_Valid = validateLocApptDate("appt_start_date_"+i);
				var appt_end_date_Valid = validateLocApptDate("appt_end_date_"+i);
				
				if(appt_start_date_Valid>0 && appt_end_date_Valid>0){
					if(comparedates($('#appt_end_date_'+i).val(),$('#appt_start_date_'+i).val())<0){
							validationWrongRows = validationWrongRows + (i+1)+",";
					}
				}else{
					if(appt_start_date_Valid==0 ){	//"Date  should be in MM/dd/yyyy format"
								startDateFormatWrongRows = startDateFormatWrongRows + (i+1)+",";
					}else if(appt_start_date_Valid<0 ){//"Date is required."
								startDateEmptyWrongRows = startDateEmptyWrongRows + (i+1)+",";
					}

					if(appt_end_date_Valid==0 ){		//"Date  should be in MM/dd/yyyy format"
								endDateFormatWrongRows = endDateFormatWrongRows + (i+1)+",";
					}else if(appt_end_date_Valid<0 ){	//"Date is required."
								endDateEmptyWrongRows = endDateEmptyWrongRows + (i+1)+",";
					}
				}
			}

			if((startDateFormatWrongRows=="" && startDateEmptyWrongRows=="")
				&& (endDateFormatWrongRows=="" && endDateEmptyWrongRows=="")
				&& (validationWrongRows=="")){
					//alert("Submitting Form Data ::: ");
					var url = "updateLocationsApptDates.html";
					$.post(url, $("#updateLocationsApptDates").serialize(),function(data) {
						try{
							//alert("Response  From Server ::: "+data);
							var json = $.parseJSON(data);
							if(json.status){
								  $("#loc_apptRestrictResponce").html("<b>Dates are updated successfully!</b>");
								  $("#loc_apptRestrictResponce").css('color', 'green');
							}else{				
								  $("#loc_apptRestrictResponce").html("<b>Error while updating dates!</b>");
								  $("#loc_apptRestrictResponce").css('color', 'red');
							}
						}catch(e){
							//alert("Error : "+e);
						}
					});

			}else{
				var errorRows = ""; 
				//alert("startDateEmptyWrongRows ::: "+startDateEmptyWrongRows);
				if(startDateEmptyWrongRows!=""){
						errorRows = "Start Date is required for the rows ["+startDateEmptyWrongRows.substring(0, startDateEmptyWrongRows.length-1)+" ] ;"
				}
				//alert("startDateFormatWrongRows ::: "+startDateFormatWrongRows);
				if(startDateFormatWrongRows!=""){
						errorRows = errorRows + "Start Date should be in MM/dd/yyyy format for the rows ["+startDateFormatWrongRows.substring(0, startDateFormatWrongRows.length-1)+" ] ;"
				}
				$("#loc_appt_start_dateError").html(errorRows);

				errorRows = ""; 
				//alert("endDateEmptyWrongRows ::: "+endDateEmptyWrongRows);
				if(endDateEmptyWrongRows!=""){
						errorRows = "End Date is required for the rows ["+endDateEmptyWrongRows.substring(0, endDateEmptyWrongRows.length-1)+" ] ;"
				}
				//alert("endDateFormatWrongRows ::: "+endDateFormatWrongRows);
				if(endDateFormatWrongRows!=""){
						errorRows = errorRows + "End Date should be in MM/dd/yyyy format for the rows ["+endDateFormatWrongRows.substring(0, endDateFormatWrongRows.length-1)+" ] ;"
				}
				$("#loc_appt_end_dateError").html(errorRows);
				
				errorRows = ""; 
				//alert("validationWrongRows ::: "+validationWrongRows);
				if(validationWrongRows!=""){
						errorRows = errorRows + "End date should be greater than or equal to Start date for the rows ["+validationWrongRows.substring(0, validationWrongRows.length-1)+" ] ;"
				}
				$("#loc_appt_days_dateError").html(errorRows);
			}
	}catch(e){
		//alert("Error : "+e);
	}
}

function validateLocApptDate(fieldId){
		try{
			var inputvalue = $('#'+fieldId).val();
			if(inputvalue=='')  {
					//$('#'+errorDivId).text(messageField +" is required.");
					return "-1";
			}else{
					inputvalue = inputvalue.replace("/","-");
					inputvalue = inputvalue.replace("/","-");
					var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
					var sucess= pattern.test(inputvalue);
					if(sucess){
						//$('#'+errorDivId).text("");
						return 1;
					}else{
						//$('#'+errorDivId).html(messageField +"  should be in MM/dd/yyyy format");
						return 0;
					}
			}
		}catch(e){
			//alert("Exception  : "+e);
		}
	}



	function  updateServiceLocationApptDatesWindow() { 
	try{
		  	$("#loc_ser_appt_window_apptRestrictResponce").html("");
			$("#loc_ser_appt_window_appt_start_dateError").html("");
			$("#loc_ser_appt_window_appt_end_dateError").html("");
			$("#loc_ser_appt_window_appt_days_dateError").html("");

			var startDateEmptyWrongRows ="";
			var endDateEmptyWrongRows ="";
			var startDateFormatWrongRows ="";
			var endDateFormatWrongRows ="";
			
			var validationWrongRows ="";

			var noOfServiceWindows = $("#noOfServiceWindows").val();	
			
			for(var i=0;i<noOfServiceWindows;i++){
				var appt_start_date_Valid = validateLocApptDate("loc_ser_appt_window_start_date_"+i);
				var appt_end_date_Valid = validateLocApptDate("loc_ser_appt_window_end_date_"+i);
				
				//alert("Start_date_Valid Row  ::: "+i+" Date :::: "+($("#appt_start_date_"+i).val())+" Response :::: "+appt_start_date_Valid);
				//alert("End_date_Valid Row  ::: "+i+" Date :::: "+($("#appt_end_date_"+i).val())+" Response :::: "+appt_end_date_Valid);

				if(appt_start_date_Valid>0 && appt_end_date_Valid>0){
					if(comparedates($('#loc_ser_appt_window_end_date_'+i).val(),$('#loc_ser_appt_window_start_date_'+i).val())<0){
							validationWrongRows = validationWrongRows + (i+1)+",";
					}
				}else{
					if(appt_start_date_Valid==0 ){	//"Date  should be in MM/dd/yyyy format"
								startDateFormatWrongRows = startDateFormatWrongRows + (i+1)+",";
					}else if(appt_start_date_Valid<0 ){//"Date is required."
								startDateEmptyWrongRows = startDateEmptyWrongRows + (i+1)+",";
					}

					if(appt_end_date_Valid==0 ){		//"Date  should be in MM/dd/yyyy format"
								endDateFormatWrongRows = endDateFormatWrongRows + (i+1)+",";
					}else if(appt_end_date_Valid<0 ){	//"Date is required."
								endDateEmptyWrongRows = endDateEmptyWrongRows + (i+1)+",";
					}
				}
			}

			if((startDateFormatWrongRows=="" && startDateEmptyWrongRows=="")
				&& (endDateFormatWrongRows=="" && endDateEmptyWrongRows=="")
				&& (validationWrongRows=="")){
					//alert("Submitting Form Data :::---------------->>>>>>>   "+($("#updateServiceLocationApptDatesWindow").serialize()));
					var url = "updateServiceLocationApptDatesWindow.html";
					$.post(url, $("#updateServiceLocationApptDatesWindow").serialize(),function(data) {
						try{
							//alert("Response  From Server ::: "+data);
							var json = $.parseJSON(data);
							if(json.status){
								  $("#loc_ser_appt_window_apptRestrictResponce").html("<b>Dates are updated successfully!</b>");
								  $("#loc_ser_appt_window_apptRestrictResponce").css('color', 'green');
							}else{				
								  $("#loc_ser_appt_window_apptRestrictResponce").html("<b>Error while updating dates!</b>");
								  $("#loc_ser_appt_window_apptRestrictResponce").css('color', 'red');
							}
						}catch(e){
							//alert("Error : "+e);
						}
					});

			}else{
				var errorRows = ""; 
				//alert("startDateEmptyWrongRows ::: "+startDateEmptyWrongRows);
				if(startDateEmptyWrongRows!=""){
						errorRows = "Start Date is required for the rows ["+startDateEmptyWrongRows.substring(0, startDateEmptyWrongRows.length-1)+" ] ;"
				}
				//alert("startDateFormatWrongRows ::: "+startDateFormatWrongRows);
				if(startDateFormatWrongRows!=""){
						errorRows = errorRows + "Start Date should be in MM/dd/yyyy format for the rows ["+startDateFormatWrongRows.substring(0, startDateFormatWrongRows.length-1)+" ] ;"
				}
				$("#loc_ser_appt_window_appt_start_dateError").html(errorRows);

				errorRows = ""; 
				//alert("endDateEmptyWrongRows ::: "+endDateEmptyWrongRows);
				if(endDateEmptyWrongRows!=""){
						errorRows = "End Date is required for the rows ["+endDateEmptyWrongRows.substring(0, endDateEmptyWrongRows.length-1)+" ] ;"
				}
				//alert("endDateFormatWrongRows ::: "+endDateFormatWrongRows);
				if(endDateFormatWrongRows!=""){
						errorRows = errorRows + "End Date should be in MM/dd/yyyy format for the rows ["+endDateFormatWrongRows.substring(0, endDateFormatWrongRows.length-1)+" ] ;"
				}
				$("#loc_ser_appt_window_appt_end_dateError").html(errorRows);
				
				errorRows = ""; 
				//alert("validationWrongRows ::: "+validationWrongRows);
				if(validationWrongRows!=""){
						errorRows = errorRows + "End date should be greater than or equal to Start date for the rows ["+validationWrongRows.substring(0, validationWrongRows.length-1)+" ] ;"
				}
				$("#loc_ser_appt_window_appt_days_dateError").html(errorRows);
			}
	}catch(e){
		//alert("Error : "+e);
	}
}



function  loadServiceLocationApptDatesWindowForSelectedLocation() { 
	try{
		$("#loc_ser_appt_window_apptRestrictResponce").html("");
		$("#loc_ser_appt_window_appt_start_dateError").html("");
		$("#loc_ser_appt_window_appt_end_dateError").html("");
		$("#loc_ser_appt_window_appt_days_dateError").html("");

		var requestData = "locationId="+$("#serviceLocationApptDatesWindowSelectedLocationId").val();
		alert("requestData :::::::: "+requestData);
		$("#updateServiceLocationApptDatesWindowDiv").load("loadServiceLocationApptDatesWindow.html",requestData,function(data){	
			alert("data :::::::: "+data);
		});
	}catch(e){
		alert("Error : "+e);
	}
}