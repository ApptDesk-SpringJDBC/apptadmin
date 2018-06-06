$(document).ready(function() {  
	//Appointment Reports Related Functionality
	new JsDatePick({
		useMode : 2,
		target : "apptReportFromDate",
		dateFormat : "%M/%d/%Y"
	});
	new JsDatePick({
		useMode : 2,
		target : "apptReportToDate",
		dateFormat : "%M/%d/%Y"
	});
	
	//Appointments Summary Reports Related Functionality
	new JsDatePick({
		useMode : 2,
		target : "apptSummaryFromDate",
		dateFormat : "%M/%d/%Y"
	});
	new JsDatePick({
		useMode : 2,
		target : "apptSummaryToDate",
		dateFormat : "%M/%d/%Y"
	});
	
	//Appointment Status Total Reports Related Functionality
	new JsDatePick({
		useMode : 2,
		target : "apptStatusTotalReportFromDate",
		dateFormat : "%M/%d/%Y"
	});
	new JsDatePick({
		useMode : 2,
		target : "apptStatusTotalReportToDate",
		dateFormat : "%M/%d/%Y"
	});
	
	//Pledge Reports Related Functionality
	new JsDatePick({
		useMode : 2,
		target : "pledgeReportFromDate",
		dateFormat : "%M/%d/%Y"
	});
	new JsDatePick({
		useMode : 2,
		target : "pledgeReportToDate",
		dateFormat : "%M/%d/%Y"
	});
	
	//Enabling selected report type
	hideDisplayReportTypes();
	
	//Appointment Reports Related Functionality
	$("#apptReportRunReportBtn").click(function() {	
		var isValid = validateAppointmentReportForm();
		if(isValid){
			$("#apptReportTableDiv").show();
			loadApptReportResponseData();
		} else {
			$("#apptReportTableDiv").hide();
		}
	});
	
	var apptReportTableVar;
	
	function loadApptReportResponseData(){
		$("#searchResponseTableDiv").show();
		
		$.ajaxSetup({async:false});
		
		var url = "getAppointmentReport.html?"+($("#apptReportForm").serialize());	
		$.get(url, function( data ) {
		  var appointmentReportResponse = $.parseJSON(data);
		  if(appointmentReportResponse.status){			  
			  var dynamicIncludeReportResponse = "";
			  $.get("getDynamicIncludeReportsData.html", function( data ) {
				  dynamicIncludeReportResponse = $.parseJSON(data);
			  });
			  
			  if(dynamicIncludeReportResponse.status && dynamicIncludeReportResponse.dynamicIncludeReportList.length>0){	
				  var tableHeaders="";
				  var columnsArr = [];
				  var toBeHideColumns = [];
				  var dynamicIncludeReportList = dynamicIncludeReportResponse.dynamicIncludeReportList;
				  for(var i=0;i<dynamicIncludeReportList.length;i++){							  
					 var tableColumn = dynamicIncludeReportList[i].tableColumn;
					 var checkBoxValue = dynamicIncludeReportList[i].checkBoxValue;
					 var titleVal = dynamicIncludeReportList[i].title;
					 
					 if(titleVal=='CUSTOMER_NAME'){
						 titleVal = $("#customerLabel").val();
					 } else if(titleVal=='LOCATION_NAME'){
						  titleVal = $("#locationLabel").val();
					 } else if(titleVal=='RESOURCE_NAME'){
						  titleVal = $("#resourceLabel").val();
					 } else if(titleVal=='SERVICE_NAME'){
						  titleVal = $("#serviceLabel").val();
					 }
					 
					 tableHeaders += "<th>"+titleVal+"</th>";
					 columnsArr.push({ "data": tableColumn, "ordering": false,"defaultContent": ""});
					 if(checkBoxValue==0){
						toBeHideColumns.push(titleVal);
					 }					 								 
				  }
				  var tablesData = '<table id="apptReportTable" class="display nowrap" cellspacing="0" width="100%"><thead><tr>' + tableHeaders + '</tr></thead><tfoot><tr>' + tableHeaders + '</tr></tfoot></table><br/>';
				  
				  $("#apptReportTableDiv").empty();
				  $("#apptReportTableDiv").append(tablesData);
				  
				  var appointmentReportDataList = appointmentReportResponse.appointmentReportDataList;
				  apptReportTableVar =  $('#apptReportTable').DataTable({
						data: appointmentReportDataList,
						"pagingType": "full_numbers",
						scrollCollapse: true,
						//"scrollX": true,
						"lengthMenu": [[50, 100, 200],[50, 100, 200]],
						"columns": columnsArr,
						"dom": 'lBfrtip',
						buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
							{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
						],
						"columnDefs": [ {
							"defaultContent": ""
						}]
				   });
				   
				   $('#apptReportTable thead tr th' ).each(function( index ) {					
					  if(toBeHideColumns.indexOf($( this ).text()) > -1){
						apptReportTableVar.column(index).visible( false );
					  }
				   });
				  
				  // Apply the search in normal text input way
				  apptReportTableVar.columns().every(function(){
					var that = this;
					$( 'input', this.footer() ).on( 'keyup change', function () {
						if (that.search() !== this.value){
							that.search( this.value ).draw();
						}
					} );
				 });
			} else {
				alert(dynamicIncludeReportResponse.message);
			}
		  } else {
			alert(appointmentReportResponse.message);
		  }
		});
				
		$("#apptReportTable tfoot th").each( function ( i ) {
			var title = $(this).text();
			if(title=="Location" || title=="Intake" || title=="Resource" || title=="Status" || title=="Method" || title=="LIHEAP REVD" || title=="PSEHELP REVD") {
				var select = $('<select><option value=""></option></select>')
					.appendTo( $(this).empty() )
					.on( 'change', function () {
						apptReportTableVar.column( i ).search( $(this).val() ).draw();
					});		 
				apptReportTableVar.column( i ).data().unique().sort().each( function ( d, j ) {
					select.append( '<option value="'+d+'">'+d+'</option>' )
				} );
			} else if(title=="SSN" || title=="First Name" || title=="Last Name" || title=="Contact Phone" || title=="Energy Acct#" || title=="Confirmation#") {
				$(this).html( '<input type="text" placeholder="Search '+title+'" />' );
			}
		});
		
		$.ajaxSetup({async:true});
	}
	
	//Automatic Email Report Related Functionality
	$("#automaticEmailReportConfigBtn").click(function() {	
		var isValid = validateAutomaticEmailReportForm();
		if(isValid){
			var url = "addAppointmentReportConfig.html";
			var formData = $("#automaticEmailReportForm").serialize();
			$.post(url,formData,function(data) {
			  var appointmentReportResponse = $.parseJSON(data);
			  if(appointmentReportResponse.status){
					loadAutomaticEmailReportResponseData();
			  } else {
				alert(appointmentReportResponse.message);
			  }
			});
			
		} else {
			$("#automaticEmailTableDiv").hide();
		}
	});
		
	var automaticEmailTableVar;
	
	function loadAutomaticEmailReportResponseData(){
		$("#automaticEmailTableDiv").show();
		$('#automaticEmailTable').DataTable().destroy();
		
		$.ajaxSetup({async:false});
		
		var url = "getAppointmentReportConfig.html";
	
		$.get(url, function( data ) {
		  var appointmentReportConfigResponse = $.parseJSON(data);
		  if(appointmentReportConfigResponse.status){
			  var apptReportConfigList = appointmentReportConfigResponse.apptReportConfigList;
			  automaticEmailTableVar =  $('#automaticEmailTable').DataTable({
				data: apptReportConfigList,
				"pagingType": "full_numbers",
				//scrollY: '100vh',
				//stateSave: true,
				//scrollCollapse: true,
				"scrollX": true,
				"lengthMenu": [[50, 100, 200],[50, 100, 200]],
				"columns": [
					{ "data": "userName", "ordering": false,"defaultContent": ""},
					{ "data": "timeStamp", "ordering": false,"defaultContent": ""},
					{ "data": "reportName", "ordering": false,"defaultContent": ""},
					{ "data": "email1", "ordering": false,"defaultContent": ""},
					{ "data": "email2", "ordering": false,"defaultContent": ""},
					{ "data": "email3", "ordering": false,"defaultContent": ""},
					{ "data": "id",
					  "render":function(data,type,full,meta) {
						return '<a href="#" class="delete" id="'+data+'"><img src="static/images/delete.png" width="16" height="16" alt="Delete"></a>';
					  }
					}
				],
				"dom": 'lBfrtip',
				buttons: [
					{extend: 'colvis'},
					{extend: 'copy'},
					{extend: 'csv',title: 'Automatic Email Report'},
					{extend: 'excel',title: 'Automatic Email Report'},
					{extend: 'pdf',title: 'Automatic Email Report'},
					{extend: 'print'}
				]
			  });
			 
			  $('.delete').click(function() {
				  $.ajaxSetup({async:false});
				  var url = "deleteApptReportConfigById.html?configId="+($(this).attr("id"));
				  var currentThis = $(this);
				  var deleted = false;
				  $.get(url, function( data ) {
						var response = $.parseJSON(data);
						if(response.status){
							deleted = true;
						}else {
							alert(response.message);
						}						
				  });
				  if(deleted){
					 currentThis.closest("tr").remove();
				  }
				  $.ajaxSetup({async:true});
				  return false;
			  });
		  } else {
			alert(appointmentReportConfigResponse.message);
		  }
		});
		$.ajaxSetup({async:true});
	}
	
	//Appointment Summary Reports Related Functionality
	$("#apptSummaryRunReportBtn").click(function() {	
		var isValidDates = validateDateFormatsReport("apptSummaryFromDate","apptSummaryFromDateError","From Date","apptSummaryToDate","apptSummaryToDateError","To Date");
		if(isValidDates){
			$("#apptSummaryTableDiv").show();
			loadApptSummaryReportResponseData();
		} else {
			$("#apptSummaryTableDiv").hide();
		}
	});
	
	function loadApptSummaryReportResponseData(){
		$("#apptSummaryTableDiv").show();
		
		$.ajaxSetup({async:false});
		
		var url = "getSummaryReport.html?"+($("#apptSummaryReportForm").serialize());	
		$.get(url, function( data ) {
		  var summaryReportResponse = $.parseJSON(data);
		  
		  if(summaryReportResponse.status){
			  //Procedure Related Summary Report
			  var procedureStatReports = summaryReportResponse.procedureStatReports;
			  if(procedureStatReports!=undefined && procedureStatReports.statisticsReportList!=undefined && procedureStatReports.statisticsReportList.length>0){
				  $("#apptSummaryProcedureTableDiv").show();
				  $('#apptSummaryProcedureTable').DataTable().destroy();
				  $("#procedureTotalNoOfAppts").html(procedureStatReports.totalNoOfAppts);
				  
				  $('#apptSummaryProcedureTable').DataTable({
					data: procedureStatReports.statisticsReportList,
					"pagingType": "full_numbers",
					scrollCollapse: true,
					"lengthMenu": [[50, 100, 200],[50, 100, 200]],
					"columns": [
						{ "data": "name", "ordering": false,"defaultContent": ""},
						{ "data": "noOfBookedAppts", "ordering": false,"defaultContent": ""},
						{ "data": "noOfOtherAppts", "ordering": false,"defaultContent": ""},
						{ "data": "totalNoOfAppts", "ordering": false,"defaultContent": ""}
					],
					"dom": 'lBfrtip',
					buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
						{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
					]
				  });
			  } else {
				  $("#apptSummaryProcedureTableDiv").hide();
			  }
			  
			  //Location Related Summary Report
			  var locationStatReports = summaryReportResponse.locationStatReports;
			  if(locationStatReports!=undefined && locationStatReports.statisticsReportList!=undefined && locationStatReports.statisticsReportList.length>0){
				  $("#apptSummaryLocationTableDiv").show();
				  $('#apptSummaryLocationTable').DataTable().destroy();
				  $("#locationTotalNoOfAppts").html(locationStatReports.totalNoOfAppts);
				  
				  $('#apptSummaryLocationTable').DataTable({
					data: locationStatReports.statisticsReportList,
					"pagingType": "full_numbers",
					scrollCollapse: true,
					"scrollX": true,
					"lengthMenu": [[50, 100, 200],[50, 100, 200]],
					"columns": [
						{ "data": "name", "ordering": false,"defaultContent": ""},
						{ "data": "noOfBookedAppts", "ordering": false,"defaultContent": ""},
						{ "data": "noOfOtherAppts", "ordering": false,"defaultContent": ""},
						{ "data": "totalNoOfAppts", "ordering": false,"defaultContent": ""}
					],
					"dom": 'lBfrtip',
					buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
						{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
					]
				  });
			  } else {
				  $("#apptSummaryLocationTableDiv").hide();
			  }
			  
			  //Resource Related Summary Report
			  var resourceStatReports = summaryReportResponse.resourceStatReports;
			  if(resourceStatReports!=undefined && resourceStatReports.statisticsReportList!=undefined && resourceStatReports.statisticsReportList.length>0){
				  $("#apptSummaryResourceTableDiv").show();
				  $('#apptSummaryResourceTable').DataTable().destroy();
				  $("#resourceTotalNoOfAppts").html(resourceStatReports.totalNoOfAppts);
				  
				  $('#apptSummaryResourceTable').DataTable({
					data: resourceStatReports.statisticsReportList,
					"pagingType": "full_numbers",
					scrollCollapse: true,
					"lengthMenu": [[50, 100, 200],[50, 100, 200]],
					"columns": [
						{ "data": "name", "ordering": false,"defaultContent": ""},
						{ "data": "noOfBookedAppts", "ordering": false,"defaultContent": ""},
						{ "data": "noOfOtherAppts", "ordering": false,"defaultContent": ""},
						{ "data": "totalNoOfAppts", "ordering": false,"defaultContent": ""}
					],
					"dom": 'lBfrtip',
					buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
						{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
					]
				  });
			  } else {
				  $("#apptSummaryResourceTableDiv").hide();
			  }
			  
			  //Service Related Summary Report
			  var serviceStatReports = summaryReportResponse.serviceStatReports;
			  if(serviceStatReports!=undefined && serviceStatReports.statisticsReportList!=undefined && serviceStatReports.statisticsReportList.length>0){
				  $("#apptSummaryServiceTableDiv").show();
				  $('#apptSummaryServiceTable').DataTable().destroy();
				  $("#serviceTotalNoOfAppts").html(serviceStatReports.totalNoOfAppts);
				  
				  $('#apptSummaryServiceTable').DataTable({
					data: serviceStatReports.statisticsReportList,
					"pagingType": "full_numbers",
					scrollCollapse: true,
					"scrollX": true,
					"lengthMenu": [[50, 100, 200],[50, 100, 200]],
					"columns": [
						{ "data": "name", "ordering": false,"defaultContent": ""},
						{ "data": "noOfBookedAppts", "ordering": false,"defaultContent": ""},
						{ "data": "noOfOtherAppts", "ordering": false,"defaultContent": ""},
						{ "data": "totalNoOfAppts", "ordering": false,"defaultContent": ""}
					],
					"dom": 'lBfrtip',
					buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
						{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
					]
				  });
			  } else {
				  $("#apptSummaryServiceTableDiv").hide();
			  }
			  
			  //Location  and Service Related Summary Report
			  var locationServiceStatReports = summaryReportResponse.locationServiceStatReports;
			  if(locationServiceStatReports!=undefined && locationServiceStatReports.statisticsReportList!=undefined && locationServiceStatReports.statisticsReportList.length>0){
				  $("#apptSummaryLocationAndServiceTableDiv").show();
				  $('#apptSummaryLocationAndServiceTable').DataTable().destroy();
				  $("#locationAndServiceTotalNoOfAppts").html(locationServiceStatReports.totalNoOfAppts);
				  
				  $('#apptSummaryLocationAndServiceTable').DataTable({
					data: locationServiceStatReports.statisticsReportList,
					"pagingType": "full_numbers",
					scrollCollapse: true,
					"lengthMenu": [[50, 100, 200],[50, 100, 200]],
					"columns": [
						{ "data": "locationName", "ordering": false,"defaultContent": ""},
						{ "data": "name", "ordering": false,"defaultContent": ""},
						{ "data": "noOfBookedAppts", "ordering": false,"defaultContent": ""},
						{ "data": "noOfOtherAppts", "ordering": false,"defaultContent": ""},
						{ "data": "totalNoOfAppts", "ordering": false,"defaultContent": ""}
					],
					"dom": 'lBfrtip',
					buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
						{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
					]
				  });
			  } else {
				  $("#apptSummaryLocationAndServiceTableDiv").hide();
			  }
			  
		  } else {
			alert(summaryReportResponse.message);
		  }
		});
			
		$.ajaxSetup({async:true});
	}
	
	//Appointment Status Total Reports Related Functionality
	$("#apptStatusTotalReportRunBtn").click(function() {	
		var isValid = validateAppointmentStatusTotalReportForm();
		if(isValid){
			$("#apptStatusTotalTableDiv").show();
			loadApptStatusTotalReportResponseData();
		} else {
			$("#apptStatusTotalTableDiv").hide();
		}
	});
	
	$("#apptStatusTotalReportViewPDFReportBtn").click(function() {	
		var isValid = validateAppointmentStatusTotalReportForm();
		if(isValid){
			var url = "getItemizedReportTemplate.html?"+($("#apptStatusTotalReportForm").serialize());	
			window.open(url, '_blank');
		}
	});
	
	
	function loadApptStatusTotalReportResponseData(){
		$.ajaxSetup({async:false});
		
		var url = "getSummaryStatisticsReport.html?"+($("#apptStatusTotalReportForm").serialize());	
		$.get(url, function( data ) {
		  var apptStatusTotalResponse = $.parseJSON(data);
		  
		  if(apptStatusTotalResponse.status){
			  var summaryStatisticsResults = apptStatusTotalResponse.summaryStatisticsResults;
			  if(summaryStatisticsResults!=undefined && summaryStatisticsResults.length>0){
				  $("#apptStatusTotalTableDiv").show();
				  $('#apptStatusTotalTable').DataTable().destroy();
				  //$("#apptStatusTotalTotalNoOfAppts").html(apptStatusTotalResponse.summaryTotalNoOfAppts);
				  //alert("Columns loading Dynamically");
				  var statusData = "";
				  $.get("getAppointmentStatusDropDownList.html", function( data ) {
					  statusData = data;					  
				  });
				  //alert("statusData  ::::::  "+statusData);
				  var statusDataResponse = $.parseJSON(statusData);
				  
				  //var apptStatusTotalReportCategory = $('input[name=apptStatusTotalReportCategory]:checked').val();
				  var apptStatusTotalReportCategory = $("#apptStatusTotalReportCategory").val();
				  
				  var dataArray = [];				  
				  var subArray = [];				  
				  var deniedExists = false;
				  var deniedClosCount = false;
				  var noOfTotalCountableColumns = 0;
				  
				  if(statusDataResponse.status && statusDataResponse.appointmentStatusList!=undefined){
					  if(statusDataResponse.appointmentStatusList.length>1){							  
						  for(var i=0;i<statusDataResponse.appointmentStatusList.length;i++){
							var denied = statusDataResponse.appointmentStatusList[i].denied;
							if(denied=='Y'){								
								deniedExists = true;
								deniedClosCount += 1;
							}
						  }						  
					  }
				  }
					  
				  for(var j=0;j<summaryStatisticsResults.length;j++){
					  subArray = [];
					  if(apptStatusTotalReportCategory=='D'){
						  //subArray.push(summaryStatisticsResults[j].monthName);
						  subArray.push(summaryStatisticsResults[j].day);
					  } else if (apptStatusTotalReportCategory=='W'){
						  //subArray.push(summaryStatisticsResults[j].monthName);
						  subArray.push(summaryStatisticsResults[j].week);
					  } else if (apptStatusTotalReportCategory=='M'){
						  subArray.push(summaryStatisticsResults[j].month);
					  }else if (apptStatusTotalReportCategory=='Q'){
						  subArray.push(summaryStatisticsResults[j].quater);
					  }else if (apptStatusTotalReportCategory=='Y'){
						  subArray.push(summaryStatisticsResults[j].year);
					  }
					  
					  subArray.push(summaryStatisticsResults[j].totalAppointments);
					  
					  var apptStatusWithApptCount = summaryStatisticsResults[j].apptStatusWithApptCount;
					  
					  if(statusDataResponse.status && statusDataResponse.appointmentStatusList!=undefined){
						  if(statusDataResponse.appointmentStatusList.length>1){							  
							  for(var i=0;i<statusDataResponse.appointmentStatusList.length;i++){
								var statusVal = statusDataResponse.appointmentStatusList[i].statusVal;
								var denied = statusDataResponse.appointmentStatusList[i].denied;
							    if(denied=='N'){ 
									var reportDisplay = statusDataResponse.appointmentStatusList[i].reportDisplay;
									if(reportDisplay=='Y'){
										var apptCount = apptStatusWithApptCount[statusVal];
										if(apptCount!=undefined){
											subArray.push(apptCount);
										}else{
											subArray.push("");
										}
									}
								}
							  }
							  
							  if(deniedExists==true){
								  for(var i=0;i<statusDataResponse.appointmentStatusList.length;i++){
									  var statusVal = statusDataResponse.appointmentStatusList[i].statusVal;
									  var denied = statusDataResponse.appointmentStatusList[i].denied;
									  if(denied=='Y'){
										 var apptCount = apptStatusWithApptCount[statusVal];
										 if(apptCount!=undefined){
										 	subArray.push(apptCount);
										 }else{
										 	subArray.push("");
										 }
									 } 
								  }
							  }						  
						  }
					  }					  
					  dataArray.push(subArray);
				  }
				  
				  var rowspan = 1;
				  if(deniedExists==true){
					  rowspan = 2;
				  }					  
				  var tableHeaders="";
				  if(apptStatusTotalReportCategory=='D'){
					 //tableHeaders += "<th> Month </th>";
					 tableHeaders += "<th rowspan='"+rowspan+"'> Day </th>";
				  } else if (apptStatusTotalReportCategory=='W'){
					  //tableHeaders += "<th> Month </th>";
					  tableHeaders += "<th rowspan='"+rowspan+"'> Week </th>";
				  } else if (apptStatusTotalReportCategory=='M'){
					  tableHeaders += "<th> Month </th>";
				  }else if (apptStatusTotalReportCategory=='Q'){
					  tableHeaders += "<th rowspan='"+rowspan+"'> Quater </th>";
				  }else if (apptStatusTotalReportCategory=='Y'){
					  tableHeaders += "<th rowspan='"+rowspan+"'> Year </th>";
				  }
				  
				  tableHeaders += "<th rowspan='"+rowspan+"'> Total Scheduled Appts </th>";
				  noOfTotalCountableColumns  += 1;
				  
				  if(statusDataResponse.status && statusDataResponse.appointmentStatusList!=undefined){
					  if(statusDataResponse.appointmentStatusList.length>1){
						  for(var i=0;i<statusDataResponse.appointmentStatusList.length;i++){
							  var statusVal = statusDataResponse.appointmentStatusList[i].statusVal;
							  var denied = statusDataResponse.appointmentStatusList[i].denied;
							  if(denied=='N'){
								  var reportDisplay = statusDataResponse.appointmentStatusList[i].reportDisplay;
								  if(reportDisplay=='Y'){
										tableHeaders += "<th rowspan='"+rowspan+"'> "+statusDataResponse.appointmentStatusList[i].status+" </th>";
										noOfTotalCountableColumns  += 1;
								  }	
							  }								  
						  }
						  
						  if(deniedExists==true){
							  tableHeaders += "<th colspan='"+deniedClosCount+"' style='text-align: left;'> Denied </th> </tr><tr>";
							  for(var i=0;i<statusDataResponse.appointmentStatusList.length;i++){
								  var denied = statusDataResponse.appointmentStatusList[i].denied;
								  if(denied=='Y'){
									 tableHeaders += "<th> "+statusDataResponse.appointmentStatusList[i].status+" </th>";
									 noOfTotalCountableColumns  += 1;
								 } 
							  }
						  }	
					  }
				  }
				                       
                  $("#apptStatusTotalTableContent").empty();
                   
				  var tableHtml = "";
				  tableHtml += '<table id="apptStatusTotalTable" class="display nowrap" cellspacing="0" width="100%"><thead><tr>' + tableHeaders + '</tr></thead>';
				  var tableFooterHtml = '<tfoot><tr><th colspan="1" style="background:#e5e5e5;">Total:</th>';
				  tableFooterHtml += '<th style="background:#e5e5e5;text-align: left;"></th>'; //for total Appts
				  if(statusDataResponse.status && statusDataResponse.appointmentStatusList!=undefined){
					  if(statusDataResponse.appointmentStatusList.length>1){
						  for(var i=0;i<statusDataResponse.appointmentStatusList.length;i++){
							  var statusVal = statusDataResponse.appointmentStatusList[i].statusVal;
							  var denied = statusDataResponse.appointmentStatusList[i].denied;
							  if(denied=='N'){
								  var reportDisplay = statusDataResponse.appointmentStatusList[i].reportDisplay;
								  if(reportDisplay=='Y'){
										tableFooterHtml += '<th style="background:#e5e5e5;text-align: left;"></th>';
								  }	
							  }								  
						  }
						  if(deniedExists==true){
							  for(var i=0;i<statusDataResponse.appointmentStatusList.length;i++){
								  var denied = statusDataResponse.appointmentStatusList[i].denied;
								  if(denied=='Y'){
									tableFooterHtml += '<th style="background:#e5e5e5;text-align: left;"></th>';
								 } 
							  }
						  }	
					  }
				  }
				  tableFooterHtml += '</tr></tfoot></table>';
				  tableHtml = tableHtml + tableFooterHtml;
				  //console.log("tableHtml :::: "+tableHtml);
				  $("#apptStatusTotalTableContent").append(tableHtml);
				  
				  $('#apptStatusTotalTable').DataTable({
					data: dataArray,
					"pagingType": "full_numbers",
					scrollCollapse: true,
					"scrollX": true,
					"lengthMenu": [[50, 100, 200],[50, 100, 200]],
					//"columns": columnsArr,
					"dom": 'lBfrtip',
					buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
						{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
					],
					"columnDefs": [ {
						"defaultContent": ""
					} ],
					"footerCallback": function ( row, data, start, end, display ) {
						var api = this.api(), data;
			 
						// Remove the formatting to get integer data for summation
						var intVal = function ( i ) {
							return typeof i === 'string' ?
								i.replace(/[\$,]/g, '')*1 :
								typeof i === 'number' ?
									i : 0;
						};
						for (var columnIndex=1;columnIndex<=noOfTotalCountableColumns;columnIndex++){							
							// Total over this page
							var pageTotal = api
								.column( columnIndex, { page: 'current'} )
								.data()
								.reduce( function (a, b) {
									return intVal(a) + intVal(b);
								}, 0 );
							// Update footer
							$( api.column( columnIndex ).footer() ).html(
								pageTotal
							);
						}
					}
				  });
				  
			  } else {				 
				  $("#apptStatusTotalTableDiv").hide();
				  alert("No records found!");
			  }
			  
		  } else {
			$("#apptStatusTotalTableDiv").hide();
			alert(apptStatusTotalResponse.message);
		  }
		});
			
		$.ajaxSetup({async:true});
	}

	//Pledge Reports Related Functionality
	$("#pledgeReportRunBtn").click(function() {	
		var isValid = validatePledgeReportForm();
		if(isValid){
			$("#pledgeTableDiv").show();
			loadPledgeReportResponseData();
		} else {
			$("#pledgeTableDiv").hide();
		}
	});
		
	function loadPledgeReportResponseData(){
		$.ajaxSetup({async:false});
		
		var url = "getPledgeReport.html?"+($("#pledgeReportForm").serialize());	
		$.get(url, function( data ) {
		  var pledgeReportResponse = $.parseJSON(data);
		  
		  if(pledgeReportResponse.status){
			  var pledgeReportData =  pledgeReportResponse.pledgeReportData;
			  
			  if(pledgeReportData!=undefined && Object.keys(pledgeReportData).length>0){
				  $("#pledgeTableDiv").show();
				  
				  $("#pledgeTableContent").empty();
				  
				  var dynamicPledgeResultResponse = "";
				  $.get("getDynamicPledgeResults.html", function( data ) {
					  dynamicPledgeResultResponse = $.parseJSON(data);
				  });
				  
				  if(dynamicPledgeResultResponse.status){
				     var dynamicPledgeResultList = dynamicPledgeResultResponse.dynamicPledgeResultList;
				     if(dynamicPledgeResultList!=undefined && dynamicPledgeResultList.length>0){
						  
						  var pledgeReportRequestType = $('input[name=pledgeReportRequestType]:checked').val();
						  
						  var tableHeaders="";
						  var columnsArr = [];
						  var toBeHideColumns = [];
						  
  						  for(var i=0;i<dynamicPledgeResultList.length;i++){							  
							 var titleVal = dynamicPledgeResultList[i].title;
							 var column = dynamicPledgeResultList[i].column;
							 var displayFor = dynamicPledgeResultList[i].displayFor;
							 var enable = dynamicPledgeResultList[i].enable;
							 //alert("titleVal :: "+titleVal+" ,column :::: "+column+" ,displayFor :::: "+displayFor+" ,enable :::: "+enable);
							 
							 if(titleVal=='CUSTOMER_ID'){
								 titleVal = $("#customerLabel").val()+" Id";
							 } else if(titleVal=='LOCATION_NAME'){
								  titleVal = $("#locationLabel").val();
							 } else if(titleVal=='RESOURCE_NAME'){
								  titleVal = $("#resourceLabel").val();
							 }
							 if(displayFor!=undefined){
								 displayFor = displayFor.trim();
								 if(displayFor=='NOT_Group_By_Vendor'){
									 if(pledgeReportRequestType != 'Group_By_Vendor'){
										 tableHeaders += "<th>"+titleVal+"</th>";
										 columnsArr.push({ "data": column, "ordering": false,"defaultContent": ""});
										 if(enable!='Y'){
											toBeHideColumns.push(titleVal);
										 }
									 }
								 } else if(displayFor=='Group_By_Vendor'){
									 if(pledgeReportRequestType == 'Group_By_Vendor'){
										 tableHeaders += "<th>"+titleVal+"</th>";
										 columnsArr.push({ "data": column, "ordering": false,"defaultContent": ""});
										 if(enable!='Y'){
											toBeHideColumns.push(titleVal);
										 }
									}
								 } else if(displayFor=='All'){
									  tableHeaders += "<th>"+titleVal+"</th>";
									  columnsArr.push({ "data": column, "ordering": false,"defaultContent": ""});
									  if(enable!='Y'){
										toBeHideColumns.push(titleVal);
									  }
								 }	
							 }								 
						  }
						  var tablesData = "";	
						  var i=0;
						  for (var key in pledgeReportData) {
							  tablesData += '<div class="options"><div class="txt-bold">'+key+' Pledge Report </div> <div class="clear-all"></div></div>';
							  tablesData += '<table id="pledgeTableId_'+i+'" class="display nowrap" cellspacing="0" width="100%"><thead><tr>' + tableHeaders + '</tr></thead><tfoot><tr>' + tableHeaders + '</tr></tfoot></table><br/>';
							  i++;
						  }
						 
						  $("#pledgeTableContent").append(tablesData);
						
						   var dataTableArray = [];	
							var i=0;
							for (var key in pledgeReportData) {
								if (pledgeReportData.hasOwnProperty(key)) {
								   try {
									 dataTableArray[i] =  $('#pledgeTableId_'+i+'').DataTable({
											data: pledgeReportData[key],
											"pagingType": "full_numbers",
											scrollCollapse: true,
											"scrollX": true,
											"lengthMenu": [[50, 100, 200],[50, 100, 200]],
											"columns": columnsArr,
											"dom": 'lBfrtip',
											buttons: [ {extend: 'colvis'},{extend: 'copy'},{extend: 'csv',title: 'Report'},
												{extend: 'excel',title: 'Report'},{extend: 'pdf',title: 'Report'},{extend: 'print'}
											],
											"columnDefs": [ {
												"defaultContent": ""
											} ]
										});
								   } catch(e){
									   alert("Error ::::: "+e);
								   }
								  i++;
							  }
							}
						  
						  for(var i=0;i<Object.keys(pledgeReportData).length;i++){
							try {
								  $('#pledgeTableId_'+i+' thead tr th' ).each(function( index ) {					
									 if(toBeHideColumns.indexOf($( this ).text()) > -1){
										dataTableArray[i].column(index).visible( false );
									 }
								  });
							   } catch(e){
								   alert("Error ::::: "+e);
							   }
						  }
					 }else {
						  $("#pledgeTableDiv").hide();
						  alert("No records found!");
					  }
				  }else {
					  $("#pledgeTableDiv").hide();
					  alert("No records found!");
				  }
			  } else {				 
				  $("#pledgeTableDiv").hide();
				  alert("No records found!");
			  }
			  
		  } else {
			$("#pledgeTableDiv").hide();
			alert(pledgeReportResponse.message);
		  }
		});
			
		$.ajaxSetup({async:true});
	}
	
	$("#reportType").on('change', function (e) {
		try {			
			hideDisplayReportTypes();
		} catch (e) {
			alert("Error : "+e);
		}
	});
	
	function hideDisplayReportTypes(){
		var reportType = $("#reportType").val();
		$(".reportsClass").hide();			
		if(reportType=="APPOINTMENTS_REPORT"){
			$("#apptReportDetails").show();
		} else if (reportType=="AUTOMATIC_EMAIL_REPORT"){
			$("#automaticEmailReportDetails").show();
			loadAutomaticEmailReportResponseData();
		} else if (reportType=="APPOINTMENTS_SUMMARY"){
			$("#apptSummaryReportDetails").show();
		} else if (reportType=="APPOINTMENT_STATUS_TOTAL"){
			$("#apptStatusTotalReportDetails").show();
		} else if (reportType=="PLEDGE_REPORT"){
			$("#pledgeReportDetails").show();
		}else {
			$("#apptReportDetails").show();
		}
	}
});	

//Appointment Report related functionality
function validateAppointmentReportForm(){
	var sucessCount = 0;
	sucessCount = sucessCount + validDropDownValue("apptReportLocationId","apptReportLocationError",($("#locationLabel").val()));
	sucessCount = sucessCount + validDropDownValue("apptReportResourceIds","apptReportResourceError",($("#resourceLabel").val()));
	sucessCount = sucessCount + validDropDownValue("apptReportServiceIds","apptReportServiceError",($("#serviceLabel").val()));
	
	var isValidDates = validateDateFormatsReport("apptReportFromDate","apptReportFromDateError","From Date","apptReportToDate","apptReportToDateError","To Date");
	
	var apptReportApptStatus = $('input[name="apptReportApptStatus"]:checked').map(function () {  
        return this.value;
    }).get().join(",");
	
	var apptReportOtherApptStatus = $("#apptReportOtherApptStatus").val();
	
	if((apptReportApptStatus!=null && apptReportApptStatus!="") || (apptReportOtherApptStatus!=null && apptReportOtherApptStatus!="")){
		sucessCount = sucessCount + 1;
		$('#apptReportApptStatusError').html("");	
		var apptStatus = "";
		if(apptReportApptStatus!=null && apptReportApptStatus!=""){
			apptStatus = apptReportApptStatus;
		}
		if(apptReportOtherApptStatus!=null && apptReportOtherApptStatus!=""){
			if(apptStatus!=null && apptStatus!=""){
				apptStatus = apptStatus + ",";
			}
			apptStatus = apptStatus + apptReportOtherApptStatus;
		}
		$("#apptStatus").val(apptStatus);		
	}else{
		$('#apptReportApptStatusError').html("Please select at least one Appointment Status");
	}
	
	if(sucessCount==4 && isValidDates){		
		return true;
	}else{
		return false;
	}
}

//Automatic Email Report related functionality
function validateAutomaticEmailReportForm(){
	var sucessCount = 0;
	
	var isValidReportName = validateNotNullFieldWithInputVal($("#automaticEmailReportForm input[name=reportName]").val(),"automaticEmailReportNameError","Report Name");
	
	sucessCount = sucessCount + checkEmailIDFieldsWithInputValue($("#automaticEmailReportForm input[name=email1]").val(),"automaticEmailReportEmail1Error","Email 1") ;
	var email2 = $("#automaticEmailReportForm input[name=email2]").val();
	if(email2!=""){
		sucessCount = sucessCount + checkEmailIDFieldsWithInputValue(email2,"automaticEmailReportEmail2Error","Email 2") ;
	}else{
		sucessCount = sucessCount + 1;
	}
	var email3 = $("#automaticEmailReportForm input[name=email3]").val();
	if(email3!=""){
		sucessCount = sucessCount + checkEmailIDFieldsWithInputValue(email3,"automaticEmailReportEmail3Error","Email 3") ;
	}else{
		sucessCount = sucessCount + 1;
	}
	sucessCount = sucessCount + validDropDownValue("automaticEmailReportForm select[name=locationIds]","automaticEmailReportLocationError",($("#locationLabel").val()));
	sucessCount = sucessCount + validDropDownValue("automaticEmailReportForm select[name=resourceIds]","automaticEmailReportResourceError",($("#resourceLabel").val()));
	sucessCount = sucessCount + validDropDownValue("automaticEmailReportForm select[name=serviceIds]","automaticEmailReportServiceError",($("#serviceLabel").val()));
	
	var reportApptStatus = $('input[name="automaticEmailReportApptStatus"]:checked').map(function () {  
        return this.value;
    }).get().join(",");
	
	var reportOtherApptStatus = $("#automaticEmailReportOtherApptStatus").val();
	
	if((reportApptStatus!=null && reportApptStatus!="") || (reportOtherApptStatus!=null && reportOtherApptStatus!="")){
		sucessCount = sucessCount + 1;
		$('#automaticEmailReportApptStatusError').html("");	
		var apptStatus = "";
		if(reportApptStatus!=null && reportApptStatus!=""){
			apptStatus = reportApptStatus;
		}
		if(reportOtherApptStatus!=null && reportOtherApptStatus!=""){
			if(apptStatus!=null && apptStatus!=""){
				apptStatus = apptStatus + ",";
			}
			apptStatus = apptStatus + reportOtherApptStatus;
		}
		$("#apptStatusFetch").val(apptStatus);		
	}else{
		$('#automaticEmailReportApptStatusError').html("Please select at least one Appointment Status");
	}
	
	if(sucessCount==7 && isValidReportName){		
		return true;
	}else{
		return false;
	}
}

//Appointment Status Total Report related functionality
function validateAppointmentStatusTotalReportForm(){
	var sucessCount = 0;
	sucessCount = sucessCount + validDropDownValue("apptStatusTotalReportLocationId","apptStatusTotalReportLocationError",($("#locationLabel").val()));
	sucessCount = sucessCount + validDropDownValue("apptStatusTotalReportServiceId","apptStatusTotalReportServiceError",($("#serviceLabel").val()));
	
	var isValidDates = validateDateFormatsReport("apptStatusTotalReportFromDate","apptStatusTotalReportFromDateError","From Date","apptStatusTotalReportToDate","apptStatusTotalReportToDateError","To Date");
	 	
	if(sucessCount==2 && isValidDates){ 
		return true;
	}else{ 
		return false;
	}
}

//Pledge Report related functionality
function validatePledgeReportForm(){
	var sucessCount = 0;
	sucessCount = sucessCount+validDropDownValue("apptStatusTotalReportLocationId","pledgeReportLocationError",($("#locationLabel").val()));
	sucessCount = sucessCount+validDropDownValue("apptStatusTotalReportServiceId","pledgeReportResourceError",($("#serviceLabel").val()));
	sucessCount = sucessCount+validDropDownValue("pledgeReportFundSourceId","pledgeReportFundSourceError","Fund Source");
	
	var isValidDates = validateDateFormatsReport("pledgeReportFromDate","pledgeReportFromDateError","From Date","pledgeReportToDate","pledgeReportToDateError","To Date");
	 	
	if(sucessCount==3 && isValidDates){	
		return true;
	}else{
		return false;
	}
}

function validDropDownValue(dropDownId,dropDownErrorDivId,dropDownMsg){	
	var dropDownVal = $("#"+dropDownId).val();	
	if(dropDownVal!=null){
		$('#'+dropDownErrorDivId).html(" ");	
		return 1;
	}else{
		$('#'+dropDownErrorDivId).html("Please select "+dropDownMsg);
		return 0;
	}
}

function validateDateFormatsReport(fromDateFiled,fromDateDivId,fromDateMsg,toDateFiled,toDateDivId,toDateMsg){
	try{
		var sucessCount = checkMMDDYYYYDateFields(fromDateFiled,fromDateDivId,fromDateMsg);
		sucessCount = sucessCount + checkMMDDYYYYDateFields(toDateFiled,toDateDivId,toDateMsg);
		
		if(sucessCount==2){
			if(comparedates_new($('#'+toDateFiled).val(),$('#'+fromDateFiled).val())<0){
				sucessCount = sucessCount-1;
				$('#'+toDateDivId).html("To date should be greater than or equal to From date.");
			}else{
				$('#'+toDateDivId).html(" ");
			}
		}		
		if(sucessCount==2){
			return true;
		}else{
			return false;
		}
	}catch(e){
		return false;
		//alert("Exception  : "+e);
	}
}

function populateResourcesForSelectedLocation(locationSelectDropDownId,resourceSelectDropDownId){
	try{
		var locationId = $("#"+locationSelectDropDownId).val();
		var url = "getResourceListByLocationId.html?locationId="+locationId;
		//alert("url :::::::: "+url);		
		$.get( url, function( data ) {		
			if(data!="" && data!=null && data!=undefined){
				var htmldata = "<option value='-1'> ALL "+($("#resourcesLabel").val())+" </option>";
				$.each($.parseJSON(data), function(index, item) {
					htmldata = htmldata + "<option value='"+item.resourceId+"'>"+item.firstName + "  " + item.lastName +"</option>";
				});
				$("#"+resourceSelectDropDownId).html(htmldata);
			}
		});	
   }catch(e){
	   alert("Error : "+e);
   }
}

function populateServicessForSelectedResource(resourceSelectDropDownId,serviceSelectDropDownId){
	try{
		var resourceIds = $("#"+resourceSelectDropDownId).val();
		resourceIds = String(resourceIds).replace(/,/g, "%2C");
		var url = "getServiceListByResourceIds.html?resourceIds="+resourceIds;
		//alert("url :::::::: "+url);		
		$.get( url, function( data ) {	
			//alert("data :::::::: "+data);	
			if(data!="" && data!=null && data!=undefined){
				var htmldata = "<option value='-1'> ALL "+($("#servicesLabel").val())+" </option>";
				$.each($.parseJSON(data), function(index, item) {
					htmldata = htmldata + "<option value='"+item.serviceId+"'>"+item.serviceNameOnline +"</option>";
				});
				$("#"+serviceSelectDropDownId).html(htmldata);
			}
		});	
   }catch(e){
	   alert("Error : "+e);
   }
}

function populateServicesForSelectedLocation(locationSelectDropDownId,serviceSelectDropDownId){
	try{
		var locationId = $("#"+locationSelectDropDownId).val();
		
		var url = "getServiceListByLocationId.html?locationId="+locationId;
		//alert("url :::::::: "+url);		
		$.get( url, function( data ) {
			//alert("data :::::::: "+data);	
			if(data!="" && data!=null && data!=undefined){
				var htmldata = "<option value='-1'> ALL "+($("#servicesLabel").val())+" </option>";
				$.each($.parseJSON(data), function(index, item) {
					htmldata = htmldata + "<option value='"+item.serviceId+"'>"+item.serviceNameOnline +"</option>";
				});
				$("#"+serviceSelectDropDownId).html(htmldata);
			}
		});	
   }catch(e){
	   alert("Error : "+e);
   }
}