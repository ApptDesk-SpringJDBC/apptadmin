$(document).ready(function() {  
   new JsDatePick({
		useMode : 2,
		target : "inBoundPeriodFrom",
		dateFormat : "%M/%d/%Y"
	});
	new JsDatePick({
		useMode : 2,
		target : "inBoundPeriodTo",
		dateFormat : "%M/%d/%Y"
	});

	new JsDatePick({
		useMode : 2,
		target : "outBoundPeriodFrom",
		dateFormat : "%M/%d/%Y"
	});
	new JsDatePick({
		useMode : 2,
		target : "outBoundPeriodTo",
		dateFormat : "%M/%d/%Y"
	});
	
	//Default Action
	$(".new_tab_content").hide(); //Hide all content
	
	updateDivActiveState();
	
	//On Click Event
	$("ul.new_tabs li").click(function() {
		$("ul.new_tabs li").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".new_tab_content").hide(); //Hide all tab content
		var reportType = $(this).find("a").attr("reportType");
		$("#reportType").val(reportType);
		updateDivActiveState();
		return false;
	});
	
	$("#inBoundGo").click(function() {		
		if(validateCallReport()){
			$("#InBoundCallReportDiv").show();
			loadInBoundCallData();
		} else {
			$("#InBoundCallReportDiv").hide();
		}			
	});
	
	$("#outBoundGo").click(function() {		
		if(validateCallReport()){
			$("#OutBoundCallReportDiv").show();
			loadOutBoundCallData();
		}else {
			$("#OutBoundCallReportDiv").hide();
		}	
	});
	
	var inBoundTable;
	
	function loadInBoundCallData(){
		$('#InBoundCallReport').DataTable().destroy();
		
		var url = "getInBoundCallReport.html?fromDate="+($("#inBoundPeriodFrom").val())+"&toDate="+($("#inBoundPeriodTo").val());
		$.get(url, function( data ) {
		  //var inBoundCallData = $.parseJSON(JSON.stringify(data));
		  var inBoundCallData = $.parseJSON(data);
		  var status = inBoundCallData.status;	  
		  if(status){
			  $("#inBoundCallsMins").html(inBoundCallData.totalMinutes);
			  var ivrCallLogs = inBoundCallData.ivrCallLogs;
			  inBoundTable =  $('#InBoundCallReport').DataTable({
				data: ivrCallLogs,
				"pagingType": "full_numbers",
				scrollY:        '100vh',
				scrollCollapse: true,
				"lengthMenu": [[50, 100, 200],[50, 100, 200]],
				"columns": [
					{
						"className":      'details-control',
						"orderable":      false,
						"data":           null,
						"defaultContent": ''
					},
					{ "data": "transId", "ordering": false,"defaultContent": ""},
					{ "data": "confNumber", "ordering": false,"defaultContent": ""},
					{ "data": "startTime", "ordering": false,"defaultContent": ""},
					{ "data": "customerFirstName", "ordering": false,"defaultContent": ""},
					{ "data": "customerLastName", "ordering": false,"defaultContent": ""},
					{ "data": "callerId", "ordering": false,"defaultContent": ""},
					{ "data": "homePhone", "ordering": false,"defaultContent": ""},
					{ "data": "apptType", "ordering": false,"defaultContent": ""},
					{ "data": "location", "ordering": false,"defaultContent": ""},
					{ "data": "resource", "ordering": false,"defaultContent": ""},
					{ "data": "seconds", "ordering": false,"defaultContent": ""}
					],
				"dom": 'lBfrtip',
				buttons: [
					{extend: 'colvis',columns: ':gt(0)'},
					{extend: 'copy'},
					{extend: 'csv',title: 'InBound Calls'},
					{extend: 'excel',title: 'InBound Calls'},
					{extend: 'pdf',title: 'InBound Calls'},
					{extend: 'print'}
				]
			  });
			  
			  // Apply the search in normal text input way
			  inBoundTable.columns().every(function(){
				var that = this;
				$( 'input', this.footer() ).on( 'keyup change', function () {
					if (that.search() !== this.value){
						that.search( this.value ).draw();
					}
				} );
			 });	
		  } else {
			alert((inBoundCallData.message));
		  }
		});
		
		// Add event listener for opening and closing details
		$('#InBoundCallReport tbody').on('click', 'td.details-control', function (){
			var tr = $(this).closest('tr');
			var row = inBoundTable.row( tr );
			if ( row.child.isShown() ) {
				// This row is already open - close it
				row.child.hide();
				tr.removeClass('shown');
			}
			else {
				// Open this row
				row.child( format(row.data()) ).show();
				tr.addClass('shown');
			}
		});
		
		$("#InBoundCallReport tfoot th").each( function ( i ) {
			var title = $(this).text();
			if(title=="Trans ID" || title=="Confirm no" || title=="First Name" || title=="Last Name" || title=="Caller ID") {
				$(this).html( '<input type="text" placeholder="Search '+title+'" />' );
			}
		});
	}

	/* Formatting function for row details - modify as you need */
	function format ( rowdata ) {
		// `rowdata` is the original data object for the row
		var url = "getTransStates.html?transId="+(rowdata.transId);
		var htmlData;		
		$.ajaxSetup({async:false});
		$.get(url, function( data ) {
			var transStateResponse = $.parseJSON(data);
			if(transStateResponse.status){
				htmlData = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
				htmlData = htmlData + '<tr>'+
					'<td>S.No</td>'+
					'<td>TimeStamp</td>'+
					'<td>Transaction Description</td>'+
				'</tr>';
								
				for(var i=0;i<transStateResponse.transStateList.length;i++){
					htmlData = htmlData +  '<tr>'+
							'<td>'+(i+1)+'</td>'+
							'<td>'+transStateResponse.transStateList[i].timestamp+'</td>'+
							'<td>'+transStateResponse.transStateList[i].state+'</td>'+
						'</tr>';
				}
				 
				htmlData = htmlData + '</table>';
			}else {
				htmlData = "<div style='color:red;'>"+transStateResponse.message+"</div>";
			}
		});		
		$.ajaxSetup({async:true});
		return htmlData;
	}

	var outBoundTable;
	
	function loadOutBoundCallData(){
		$('#OutBoundCallReport').DataTable().destroy();
		
		var url = "getOutBoundCallReport.html?fromDate="+($("#outBoundPeriodFrom").val())+"&toDate="+($("#outBoundPeriodTo").val());
		$.get(url, function( data ) {
		  //var inBoundCallData = $.parseJSON(JSON.stringify(data));
		  var outBoundCallsResponse = $.parseJSON(data);
		 
		  if(outBoundCallsResponse.status){
			  $("#OutBoundCallMins").html(outBoundCallsResponse.totalMinutes);
			  var outBoundCallLogs = outBoundCallsResponse.outBoundCallLogs;
			  outBoundTable = $('#OutBoundCallReport').DataTable({
				data: outBoundCallLogs,
				"pagingType": "full_numbers",
				scrollY:        '100vh',
				scrollCollapse: true,
				"lengthMenu": [[50, 100, 200],[50, 100, 200]],
				"columns": [
					{ "data": "transId", "ordering": false,"defaultContent": ""},
					{ "data": "customerFirstName", "ordering": false,"defaultContent": ""},
					{ "data": "customerLastName", "ordering": false,"defaultContent": ""},
					{ "data": "apptDateTime", "ordering": false,"defaultContent": ""},
					{ "data": "callTime", "ordering": false,"defaultContent": ""},
					{ "data": "attemptId", "ordering": false,"defaultContent": ""},
					{ "data": "dailedPhone", "ordering": false,"defaultContent": ""},
					{ "data": "status", "ordering": false,"defaultContent": ""},
					{ "data": "location", "ordering": false,"defaultContent": ""},
					{ "data": "resource", "ordering": false,"defaultContent": ""},
					{ "data": "seconds", "ordering": false,"defaultContent": ""}
				],
				"dom": 'lBfrtip',
				buttons: [
					'colvis',
					{extend: 'copy'},
					{extend: 'csv',title: 'OutBound Calls'},
					{extend: 'excel',title: 'OutBound Calls'},
					{extend: 'pdf',title: 'OutBound Calls'},
					{extend: 'print'}
				]
			  });
			  
			  //outBoundTable.columns( [ 0, 1, 2, 3 ] ).visible( false, false );
			    // Apply the search in normal text input way
				outBoundTable.columns().every( function () {
					var that = this;
					$( 'input', this.footer() ).on( 'keyup change', function () {
						if ( that.search() !== this.value ) {
							that.search( this.value ).draw();
						}
					} );
				});	
				
		  } else {
			alert((inBoundCallData.data.message));
		  }
		});
		
		$("#OutBoundCallReport tfoot th").each( function ( i ) {
			var title = $(this).text();
			if(title=="Trans ID" || title=="First Name" || title=="Last Name") {
				$(this).html( '<input type="text" placeholder="Search '+title+'" />' );
			}
		});
	}

});	

function updateDivActiveState(){
	var reportType=$("#reportType").val();
	if(reportType=="inbound"){
		$("ul.new_tabs li:first").addClass("active").show(); //Activate first tab
		$(".new_tab_content:first").show();
	}else{
		$("ul.new_tabs li:last").addClass("active").show(); //Activate first tab
		$(".new_tab_content:last").show();
	}
}

function validateCallReport(){
	try{
		var reportType = $('#reportType').val();		
		var sucessCount = 0;
		var from ="";
		var to = "";
		
		if(reportType=="inbound"){		
			sucessCount = sucessCount + checkMMDDYYYYDateFields("inBoundPeriodFrom","fromDateError","* From Date");
			sucessCount = sucessCount + checkMMDDYYYYDateFields("inBoundPeriodTo","toDateError","* To Date");
			
			from = $('#inBoundPeriodFrom').val();
			to = $('#inBoundPeriodTo').val();
		} else {
			sucessCount = sucessCount + checkMMDDYYYYDateFields("outBoundPeriodFrom","fromDateError","* From Date");
			sucessCount = sucessCount + checkMMDDYYYYDateFields("outBoundPeriodTo","toDateError","* To Date");
			
			from = $('#outBoundPeriodFrom').val();
			to = $('#outBoundPeriodTo').val();
		}
		if(sucessCount==2){
			if(comparedates_new(to,from)<0){
				sucessCount = sucessCount-1;
				$('#toDateError').html("* To date should be greater than or equal to From date.");
			}else{
				$('#toDateError').html(" ");
			}
		}		
		
		if(sucessCount==2){
			return true;
		}else{
			return false;
		}
	}catch(e){
		alert("Exception  : "+e);
		return false;
		
	}
}