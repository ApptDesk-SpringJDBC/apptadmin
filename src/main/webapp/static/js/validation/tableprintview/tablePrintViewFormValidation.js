$(document).ready(function () {
	new JsDatePick({
		useMode: 2,
		target: "jsCalenderDate",
		dateFormat: "%M/%d/%Y"
	});
	
	viewAppointmentsDetails();
});

function loadTablePrintViewResponseData() {
	$.ajaxSetup({async: false});

	var url = "getTablePrintViewResponse.html?" + ($("#tablePrintViewForm").serialize());
	
	$.get(url, function (data) {		 
		var jsonResponse = $.parseJSON(data); 
		if (jsonResponse.status) {
			var calendarDateDisplay = jsonResponse.calendarDateDisplay;
			var date = jsonResponse.date;
			 
			$("#calendarDateDisplaySpan").html(calendarDateDisplay);
			$("#jsCalenderDate").val(date);
			$("#selectedDate").val(date);
			var tablePrintViewResponse = $.parseJSON(jsonResponse.tablePrintViewResponse);
			var dynamicFieldLabels = tablePrintViewResponse.dynamicFieldLabels;
			var tablePrintViewMapData = tablePrintViewResponse.tablePrintViewData;
			
			if (tablePrintViewResponse.status) {
				if (tablePrintViewMapData != undefined && Object.keys(tablePrintViewMapData).length > 0) {
					$("#tablePrintViewTableDiv").show();
					$("#tablePrintViewTableContent").empty();

					var tableHeaders = "";
					var columnsArr = [];

					tableHeaders += "<th> Appt Time </th>";
					tableHeaders += "<th> " + ($("#serviceLabel").val()) + " </th>";
					tableHeaders += "<th> Notification Status </th>";
					//tableHeaders += "<th> Appointment Details </th>";

					columnsArr.push({
						"data": "time",
						"ordering": false,
						"defaultContent": ""
					});
					columnsArr.push({
						"data": "serviceName",
						"ordering": false,
						"defaultContent": ""
					});
					columnsArr.push({
						"data": "notificationStatus",
						"ordering": false,
						"defaultContent": ""
					});
					try{
						for (var i=0;i<dynamicFieldLabels.length;i++) {
							columnsArr.push({
								"data": dynamicFieldLabels[i].fieldName,
								"ordering": false,
								"defaultContent": ""
							});
							tableHeaders += "<th> "+dynamicFieldLabels[i].title+" </th>";
						}
					}catch(e){
						alert("Exception :::: "+e);
					}
					/*
					columnsArr.push({
						"data": "",
						"ordering": false,
						"defaultContent": "",
						"render":function(data, type, full, meta){
						   return full.firstName +" "+ full.lastName  +"; Status : "+full.state+"; ZipCode : "+full.zipCode;
						}
					});
					*/

					var tablesData = "";
					var i = 0;
					for (var key in tablePrintViewMapData) {
						var resourceDetails = $.parseJSON(key);
						tablesData += '<div class="options"><div class="txt-bold">' + resourceDetails.resourceName + ' - '+resourceDetails.totalBookedAppts+' Total Appointments.</div> <div class="clear-all"></div></div><br/>';
						tablesData += '<table id="tablePrintViewTableId_' + i + '" class="display nowrap" cellspacing="0" width="100%"><thead><tr>' + tableHeaders + '</tr></thead><tfoot><tr>' + tableHeaders + '</tr></tfoot></table><br/>';
						i++;
					}

					$("#tablePrintViewTableContent").append(tablesData);
					
					var dataTableArray = [];
					var i = 0;
					for (var key in tablePrintViewMapData) {
						if (tablePrintViewMapData.hasOwnProperty(key)) {
							try {
								dataTableArray[i] = $('#tablePrintViewTableId_' + i + '').DataTable({
									data: tablePrintViewMapData[key],
									"pagingType": "full_numbers",
									scrollCollapse: true,
									"scrollX": true,
									"lengthMenu": [
										[50, 100, 200],
										[50, 100, 200]
									],
									"columns": columnsArr,
									"dom": 'lBfrtip',
									buttons: [{
										extend: 'colvis'
									}, {
										extend: 'copy'
									}, {
										extend: 'csv',
										title: 'Report'
									}, {
										extend: 'excel',
										title: 'Report'
									}, {
										extend: 'pdf',
										title: 'Report'
									}, {
										extend: 'print'
									}],
									"columnDefs": [{
										"defaultContent": ""
									}]
								});
							} catch (e) {
								alert("Error ::::: " + e);
							}
							i++;
						}
					}
				} else {
					$("#tablePrintViewTableDiv").hide();
					alert("No records found!");
				}
			} else {
				$("#tablePrintViewTableDiv").hide();
				alert("No records found!");
			}
		} else {
			$("#tablePrintViewTableDiv").hide();
			alert(tablePrintViewResponse.message);
		}
	});

	$.ajaxSetup({async: true});
}

function previousDateSelected() {
	if (validateTablePrintViewForm()) {
		$('#noOfDaysToAdd').val(-1);
		loadTablePrintViewResponseData();
	}
}

function nextDateSelected() {
	if (validateTablePrintViewForm()) {
		$('#noOfDaysToAdd').val(1);
		loadTablePrintViewResponseData();
	}
}

function jsCalenderDateSelected() {
	if (validateTablePrintViewForm()) {
		$('#selectedDate').val($("#jsCalenderDate").val());
		$('#noOfDaysToAdd').val(0);
		loadTablePrintViewResponseData();
	}
}

function viewAppointmentsDetails() {
	if (validateTablePrintViewForm()) {
		$('#noOfDaysToAdd').val(0);
		loadTablePrintViewResponseData();
	}
}

function validateTablePrintViewForm() {
	var sucessCount = 0;
	sucessCount = sucessCount + checkMMDDYYYYDateFields("jsCalenderDate", "dataError", "Date");

	var resourceIds = $('input[name="resourceIds"]:checked').map(function () {
		return this.value;
	}).get().join(",");

	if (resourceIds != null && resourceIds != "") {
		sucessCount = sucessCount + 1;
		$('#resourceError').html("");
		$('#selectedResourceIds').val(resourceIds);
	} else {
		$('#resourceError').html("Please select at least one " + ($("#resourceLabel").val()));
	}
	if (sucessCount == 2) {
		return true;
	} else {
		return false;
	}
}

function loadResourcesCheckboxes(locationSelectDropDownId, resourceIdCcheckBoxesDiv) {
	try {
		var locationId = $("#" + locationSelectDropDownId).val();
		var url = "getResourceListByLocationId.html?locationId=" + locationId;
		$.get(url, function (data) {
			if (data != "" && data != null && data != undefined) {
				var htmldata = "";
				$.each($.parseJSON(data), function (index, item) {
					htmldata = htmldata + "<input type='checkbox' name='resourceIds' value='" + item.resourceId + "' class='noWidth' checked>";
					htmldata = htmldata + item.firstName + "  " + item.lastName + " ";
				});
				$("#" + resourceIdCcheckBoxesDiv).html(htmldata);
			}
		});
	} catch (e) {
		alert("Error : " + e);
	}
}