$(document).ready(function() {  
   	new JsDatePick({
		useMode : 2,
		target : "startDate",
		dateFormat : "%M/%d/%Y"
	});
	new JsDatePick({
		useMode : 2,
		target : "endDate",
		dateFormat : "%M/%d/%Y"
	});
		
	$("#userActivityLogSearchBtn").click(function() {	
		$("#uaserActivityLogTableDiv").show();
		$('#uaserActivityLogTable').DataTable().destroy();
		var isValidDates = validateDateFormats("startDate","startDateError","Start Date","endDate","endDateError","End Date");
		//alert("isValidDates  ::::::::::::::: "+isValidDates);
		if(isValidDates){
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var url = "getUserActivityLog.html?startDate="+startDate+"&endDate="+endDate;
			$.get(url, function( data ) {
			  //alert("data  ::::::::::::::: "+data);
			  var userActivityLogsResponse = $.parseJSON(data);
			  if(userActivityLogsResponse.status){
				  var userActivityLogs = userActivityLogsResponse.userActivityLogs;
				  $('#uaserActivityLogTable').DataTable({
					data: userActivityLogs,
					"pagingType": "full_numbers",
					scrollY: '100vh',
					//stateSave: true,
					scrollCollapse: true,
					"lengthMenu": [[50, 100, 200],[50, 100, 200]],
					"columns": [
						{ "data": "userName", "ordering": false,"defaultContent": ""},
						{ "data": "userFirstName", "ordering": false,"defaultContent": ""},
						{ "data": "timestampStr", "ordering": false,"defaultContent": ""},
						{ "data": "sessionId", "ordering": false,"defaultContent": ""},
						{ "data": "pageName", "ordering": false,"defaultContent": ""},
						{ "data": "clickName", "ordering": false,"defaultContent": ""},
						{ "data": "summaryLog", "ordering": false,"defaultContent": ""}
					],
					"dom": 'lBfrtip',
					buttons: [
						{extend: 'colvis',columns: ':gt(0)'},
						{extend: 'copy'},
						{extend: 'csv',title: 'User Activity Log'},
						{extend: 'excel',title: 'User Activity Log'},
						{extend: 'pdf',title: 'User Activity Log'},
						{extend: 'print'}
					]
				  });	
			  } else {
				alert((userActivityLogsResponse.message));
			  }
			});
		}
	});
	
	function validateDateFormats(fromDateFiled,fromDateDivId,fromDateMsg,toDateFiled,toDateDivId,toDateMsg){
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
	
});	