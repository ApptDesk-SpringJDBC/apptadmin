$(document).ready(function() {  
   	new JsDatePick({
		useMode : 2,
		target : "dobSearch",
		dateFormat : "%M/%d/%Y"
	});
	
	$("#firstNameLastNameSearchBtn").click(function() {
		$("#otherSearchErrorDiv").html("");
		$("#searchSelectionType").val("FIRSTNAME_LASTNAME");
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		if((firstName!=null && firstName!="" && firstName!=undefined)
			|| (lastName!=null && lastName!="" && lastName!=undefined)){
			$("#firstNameLastNameSearchErrorDiv").html("");
			loadSearchResponseData();
		}else{
			$("#firstNameLastNameSearchErrorDiv").html("Please provide First Name / Last Name");
		}			
	});
	
	$("#otherSearchBtn").click(function() {	
	    $("#firstNameLastNameSearchErrorDiv").html("");
	    var otherSearch = $("#otherSearch").val();
		$("#searchSelectionType").val(otherSearch);
		if(otherSearch!='DOB'){
			var searchData = $("#searchData").val();
			if((searchData!=null && searchData!="" && searchData!=undefined)){
				$("#otherSearchErrorDiv").html("");
				loadSearchResponseData();
			}else{
				$("#otherSearchErrorDiv").html("Please provide "+($("#otherSearch option:selected").text()));
			}		
		}else{
			var dobSearch = $("#dobSearch").val();
			if((dobSearch!=null && dobSearch!="" && dobSearch!=undefined)){
				var sucessCount = checkMMDDYYYYDateFields("dobSearch","otherSearchErrorDiv"," DOB ");
				if(sucessCount==1){				
					$("#otherSearchErrorDiv").html("");
					loadSearchResponseData();
				}
			}else{
				$("#otherSearchErrorDiv").html("Please provide DOB!");
			}	
		}
	});
	
	var searchTable;
	
	function loadSearchResponseData(){
		$("#searchResponseTableDiv").show();
		$('#searchResponseTable').DataTable().destroy();
		
		var url = prepareGetSearchResponseURL();
		$.get(url, function( data ) {
		  //var inBoundCallData = $.parseJSON(JSON.stringify(data));
		  var searchResponse = $.parseJSON(data);
		  if(searchResponse.status){
			  var searchApptList = searchResponse.searchApptList;
			  searchTable =  $('#searchResponseTable').DataTable({
				data: searchApptList,
				"pagingType": "full_numbers",
				scrollY: '100vh',
				//stateSave: true,
				scrollCollapse: true,
				"lengthMenu": [[50, 100, 200],[50, 100, 200]],
				"columns": [
					{
						"className":      'details-control',
						"orderable":      false,
						"data":           null,
						"defaultContent": ''
					},
					{ "data": "houseHoldId", "ordering": false,"defaultContent": ""},
					{ "data": "ssn", "ordering": false,"defaultContent": ""},
					{ "data": "firstName", "ordering": false,"defaultContent": ""},
					{ "data": "lastName", "ordering": false,"defaultContent": ""},
					{ "data": "contactPhone", "ordering": false,"defaultContent": ""},
					{ "data": "attrib1", "ordering": false,"defaultContent": ""},
					{ "data": "dob", "ordering": false,"defaultContent": ""},
					{ "data": "address", "ordering": false,"defaultContent": ""},
					{ "data": "city", "ordering": false,"defaultContent": ""},
					{ "data": "state", "ordering": false,"defaultContent": ""},
					{ "data": "zipCode", "ordering": false,"defaultContent": ""}
				],
				"dom": 'lBfrtip',
				buttons: [
					{extend: 'colvis',columns: ':gt(0)'},
					{extend: 'copy'},
					{extend: 'csv',title: 'Search Response'},
					{extend: 'excel',title: 'Search Response'},
					{extend: 'pdf',title: 'Search Response'},
					{extend: 'print'}
				]
			  });
			  
			  // Apply the search in normal text input way
			  searchTable.columns().every(function(){
				var that = this;
				$( 'input', this.footer() ).on( 'keyup change', function () {
					if (that.search() !== this.value){
						that.search( this.value ).draw();
					}
				} );
			 });	
		  } else {
			alert((searchResponse.message));
		  }
		});
		
		// Add event listener for opening and closing details
		$('#searchResponseTable tbody').on('click', 'td.details-control', function (){
			var tr = $(this).closest('tr');
			var row = searchTable.row( tr );
			if ( row.child.isShown() ) {
				// This row is already open - close it
				row.child.hide();
				tr.removeClass('shown');
			}
			else {
				//To hide all the TR's that are in SHOW state.
				 /*
				 $('#searchResponseTable tr').each(function () {
					 var tr = $(this).closest('tr');
					 var row = searchTable.row( tr );
					if ( row.child.isShown() ) {
						// This row is already open - close it
						row.child.hide();
						tr.removeClass('shown');
					}
				});
				*/
				// Open this row
				row.child( format(row.data()) ).show();
				tr.addClass('shown');
			}
		});
		
		$("#searchResponseTable tfoot th").each( function ( i ) {
			var title = $(this).text();
			if(title=="Household Id" || title=="SSN" || title=="First Name" || title=="Last Name" || title=="Contact Phone") {
				$(this).html( '<input type="text" placeholder="Search '+title+'" />' );
			}
		});
	}

	/* Formatting function for row details - modify as you need */
	function format ( rowdata ) {
		// `rowdata` is the original data object for the row
		var customerId = rowdata.customerId;
		var viewDetails = $("#viewDetails").val();
		var htmlData;
		$.ajaxSetup({async:false});
		
		if(viewDetails=="APPOINTENT_DETAILS"){
			htmlData = getAppointmentDetailsHtml(customerId);
		} else if(viewDetails=="CUSTOMER_DETAILS"){
			htmlData = getCustomerDetailsHtml(customerId);
		} else if(viewDetails=="CUSTOMER_ACTIVITY_DETAILS"){
			htmlData = getCustomerActivityDetailsHtml(customerId);
		} else if(viewDetails=="HOUSEHOLD_INFO_DETAILS"){
			htmlData = getHouseholdDetailsHtml(customerId);
		} else if(viewDetails=="PLEDGE_DETAILS"){
			htmlData = getPledgeDetailsHtml(customerId);
		} else {
			htmlData = "<div style='color:red;'>This feature not yet implemented</div>";
		}
		
		$.ajaxSetup({async:true});
		return htmlData;
	}
	
	function getAppointmentDetailsHtml(customerId){
		var htmlData;
		try {
			var url = "getAppointmentsByCustomerId.html?customerId="+customerId;
			$.get(url, function( data ) {
				var searchResponse = $.parseJSON(data);
				if(searchResponse.status){
					if(searchResponse.searchApptList.length>0){
						url = "getAppointmentStatusDropDownList.html";
						var appointmentStatusDropDownResponse;
						$.get(url, function(apptStatusData) {
							appointmentStatusDropDownResponse = $.parseJSON(apptStatusData);
						});
						
						htmlData = '<div id="apptMsg" style="color:blue;"></div>'
						htmlData = htmlData + '<table id="apptDetailsTable_'+customerId+'" cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
						htmlData = htmlData + '<thead><tr>'+
							'<td>Confirmation #</td>'+
							'<td>Appt Date & Time</td>'+
							'<td>Appt Status</td>'+
							'<td>Appt Timestamp</td>'+
							'<td>Appt Method</td>'+
							'<td>'+($("#locationLabel").val())+'</td>'+
							'<td>'+($("#resourceLabel").val())+'</td>'+
							'<td>'+($("#serviceLabel").val())+'</td>'+
							'<td>Action</td>'+
							'<td></td>'+
							'<td></td>'+
						'</tr> </thead> <tbody>';
						try{ 				
							for(var i=0;i<searchResponse.searchApptList.length;i++){
								var searchAppt = searchResponse.searchApptList[i];
								htmlData = htmlData +  '<tr id="apptDetailsTr_'+searchAppt.schedulerId+'" >'+
										'<td>'+searchAppt.confirmNumber+'</td>'+
										'<td>'+searchAppt.apptDateTime+'</td>'+
										'<td>'+searchAppt.apptStatus+'</td>'+
										'<td>'+searchAppt.apptTimeStamp+'</td>'+
										'<td>'+searchAppt.apptMethod+'</td>'+
										'<td>'+searchAppt.locationName+'</td>'+
										'<td>'+searchAppt.resourceName+'</td>'+
										'<td>'+searchAppt.serviceName+'</td>'+
										
										"<td>";										
										if(searchAppt.isFutureAppt) {
											htmlData = htmlData + "<a style='cursor:pointer;' onClick='cancelAppointment("+(searchAppt.schedulerId)+","+customerId+");'> Cancel Appt </a>";
										}else {
											htmlData = htmlData + "<select onchange='updateAppointmentStatus(this);' style='width:100px'>"+
												"<option value='-1'>- Select - </option>";
												if(appointmentStatusDropDownResponse.status){
													for(var j=0;j<appointmentStatusDropDownResponse.appointmentStatusList.length;j++){
														var statusDropDown = appointmentStatusDropDownResponse.appointmentStatusList[j];
														htmlData = htmlData + "<option value='"+searchAppt.schedulerId+"_"+statusDropDown.statusVal+"'> "+statusDropDown.status+"</option>";																									
													}
												}
											htmlData = htmlData + "</select>";
										}										
								htmlData = htmlData + "</td>"+
										"<td><a style='cursor:pointer;' onClick='alert(\"View Calendar function not yet implemented\");'> View Calendar </a></td>"+
										"<td><a style='cursor:pointer;' onClick='alert(\"View Pledge function not yet implemented\");'> View Pledge </a></td>"+
									'</tr>';
							}
							htmlData = htmlData + '</tbody></table>';
							
							//htmlData = htmlData + '<a href="javascript:doNothing()" class="ex3trigger" id="1"><img src="static/images/edit.png" width="16" height="16" alt="Edit" title="Edit" id="1" class="edit"></a>';
							/*
							htmlData = htmlData + "<script type='text/javascript'> $(document).ready(function() {" +
								"$('.edit').click(function() {"+ 
									"var id = $(this).attr('id'); "+
									"var url = 'showPopUp.html?id='+id; "+
									"$('#ex3').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex3trigger'}); "+
									"$('#ex3').draggable(); "+
								"});"+
						   "});</script>";
						   */
						   //alert("htmlData :::::::: "+htmlData);
						} catch(e){
							htmlData = "<div style='color:red;'> Error while retriving Appointment details!</div>";
						}
					} else {
						htmlData = "<div style='color:blue;'> There is no Appointments!</div>";
					}
				}else {
					htmlData = "<div style='color:red;'>"+searchResponse.message+"</div>";
				}
			});	
		}catch(e){
			htmlData = "<div style='color:red;'> Error while retriving Appointment details!</div>";
		}
		return htmlData;
	}
	
	function getCustomerDetailsHtml(customerId){
		var htmlData;
		try {
			var url = "getCustomersById.html?customerId="+customerId;
			$.get(url, function( data ) {
				var customersResponse = $.parseJSON(data);
				if(customersResponse.status){
					htmlData = '<div id="customerMsg" style="color:blue;"></div>'
					htmlData = htmlData + '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
					htmlData = htmlData + '<tr>'+
						'<td>Household Id</td>'+
						'<td>SSN</td>'+
						'<td>First Name</td>'+
						'<td>Last Name</td>'+
						'<td>Contact Phone</td>'+
						'<td>Energy Acct #</td>'+
						'<td>DOB</td>'+
						'<td>Address</td>'+
						'<td>City</td>'+
						'<td>State</td>'+
						'<td>Zip</td>'+
					'</tr>';
					try{ 				
						for(var i=0;i<customersResponse.customerList.length;i++){
							var customer = customersResponse.customerList[i];
							htmlData = htmlData +  '<tr>'+
									'<td>'+customer.houseHoldId+'</td>'+
									'<td>'+customer.ssn+'</td>'+
									'<td>'+customer.firstName+'</td>'+									
									'<td>'+customer.lastName+'</td>'+
									'<td>'+customer.contactPhone+'</td>'+
									'<td>'+customer.attrib1+'</td>'+
									'<td>'+customer.dob+'</td>'+
									'<td>'+customer.address+'</td>'+
									'<td>'+customer.city+'</td>'+
									'<td>'+customer.state+'</td>'+									
									'<td>'+customer.zipCode+'</td>'+
								'</tr>';
						}
						htmlData = htmlData + '</table>';
					} catch(e){
						htmlData = "<div style='color:red;'> Error while retriving Customer details!</div>";
					}
				}else {
					htmlData = "<div style='color:red;'>"+searchResponse.message+"</div>";
				}
			});	
		}catch(e){
			htmlData = "<div style='color:red;'> Error while retriving Customer details!</div>";
		}
		return htmlData;
	}
	
	function getCustomerActivityDetailsHtml(customerId){
		var htmlData;
		try {
			var url = "getCustomerActivitiesByCustomerId.html?customerId="+customerId;
			$.get(url, function( data ) {
				var customerActivityResponse = $.parseJSON(data);
				if(customerActivityResponse.status){
					htmlData = '<div id="customerMsg" style="color:blue;"></div>'
					htmlData = htmlData + '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
					htmlData = htmlData + '<tr>'+
						'<td>Account Number</td>'+
						'<td>First Name</td>'+
						'<td>Last Name</td>'+
						'<td>Contact Phone</td>'+
						'<td>'+($("#locationLabel").val())+'</td>'+
						'<td>'+($("#resourceLabel").val())+'</td>'+
						'<td>'+($("#serviceLabel").val())+'</td>'+
						'<td>Conf.No</td>'+
						'<td>Timestamp</td>'+
						'<td>Status</td>'+
						'<td>Appt Date & Time</td>'+
						'<td>Comments</td>'+
						'<td>Screened</td>'+						
						'<td>Updated By</td>'+
						'<td>'+($("#locationLabel").val())+'  Appt Duration</td>'+
						'<td>FrontDesk Appt Duration</td>'+
						'<td>Payment Amount</td>'+						
						'<td>Payment Updated By</td>'+
						'<td>Payment Date</td>'+
						'<td>Notes</td>'+
						'<td>Trans Id</td>'+						
						'<td>Device</td>'+
						'<td>UUID</td>'+
						'<td>Ip Address</td>'+
						'<td>Caller Id</td>'+
						'<td>Username</td>'+
					'</tr>';
					try{ 	
						 
						for(var i=0;i<customerActivityResponse.customerActivityList.length;i++){
							var customerActivity = customerActivityResponse.customerActivityList[i];
							htmlData = htmlData +  '<tr>'+
									'<td>'+customerActivity.ssn+'</td>'+
									'<td>'+customerActivity.firstName+'</td>'+									
									'<td>'+customerActivity.lastName+'</td>'+
									'<td>'+customerActivity.contactPhone+'</td>'+
									'<td>'+customerActivity.locationName+'</td>'+									
									'<td>'+customerActivity.resourceName+'</td>'+
									'<td>'+customerActivity.serviceName+'</td>'+
									'<td>'+customerActivity.confirmNumber+'</td>'+
									'<td>'+customerActivity.timestamp+'</td>'+
									'<td>'+customerActivity.apptStatus+'</td>'+
									'<td>'+customerActivity.apptDateTime+'</td>'+
									'<td>'+customerActivity.comments+'</td>'+									
									'<td>'+customerActivity.screened+'</td>'+									
									'<td>'+customerActivity.updatedBy+'</td>'+
									'<td> -- </td>'+
									'<td> -- </td>'+
									'<td> -- </td>'+
									'<td> -- </td>'+
									'<td> -- </td>'+
									'<td>'+customerActivity.notes+'</td>'+
									'<td> -- </td>'+
									'<td>'+customerActivity.device+'</td>'+									
									'<td>'+customerActivity.uuid+'</td>'+
									'<td>'+customerActivity.ipAddress+'</td>'+	
									'<td>'+customerActivity.callerId+'</td>'+										
									'<td>'+customerActivity.userName+'</td>'+
									
								'</tr>';
						}
						 
						htmlData = htmlData + '</table>';
					} catch(e){
						htmlData = "<div style='color:red;'> Error while retriving Customer Activity details!</div>";
					}
				}else {
					htmlData = "<div style='color:red;'>"+searchResponse.message+"</div>";
				}
			});	
		}catch(e){
			htmlData = "<div style='color:red;'> Error while retriving Customer Activity details!</div>";
		}
		return htmlData;
	}
	
	function getHouseholdDetailsHtml(customerId){
		var htmlData;
		try {
			var url = "getHouseHoldInfoByCustomerId.html?customerId="+customerId;
			$.get(url, function( data ) {
				var customersResponse = $.parseJSON(data);
				if(customersResponse.status){
					htmlData = '<div id="customerMsg" style="color:blue;"></div>'
					htmlData = htmlData + '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
					htmlData = htmlData + '<tr>'+
						'<td>Household Id</td>'+
						'<td>SSN</td>'+
						'<td>First Name</td>'+
						'<td>Last Name</td>'+
						'<td>Contact Phone</td>'+
						'<td>Energy Acct #</td>'+
						'<td>DOB</td>'+
						'<td>Address</td>'+
						'<td>City</td>'+
						'<td>State</td>'+
						'<td>Zip</td>'+
					'</tr>';
					try{ 				
						for(var i=0;i<customersResponse.customerList.length;i++){
							var customer = customersResponse.customerList[i];
							htmlData = htmlData +  '<tr>'+
									'<td>'+customer.houseHoldId+'</td>'+
									'<td>'+customer.ssn+'</td>'+
									'<td>'+customer.firstName+'</td>'+									
									'<td>'+customer.lastName+'</td>'+
									'<td>'+customer.contactPhone+'</td>'+
									'<td>'+customer.attrib1+'</td>'+
									'<td>'+customer.dob+'</td>'+
									'<td>'+customer.address+'</td>'+
									'<td>'+customer.city+'</td>'+
									'<td>'+customer.state+'</td>'+									
									'<td>'+customer.zipCode+'</td>'+
								'</tr>';
						}
						htmlData = htmlData + '</table>';
					} catch(e){
						htmlData = "<div style='color:red;'> Error while retriving Customer details!</div>";
					}
				}else {
					htmlData = "<div style='color:red;'>"+customersResponse.message+"</div>";
				}
			});	
		}catch(e){
			htmlData = "<div style='color:red;'> Error while retriving Customer details!</div>";
		}
		return htmlData;
	}
	
	function getPledgeDetailsHtml(customerId){
		var htmlData;
		try {
			var url = "getPledgeHistoryByCustomerId.html?customerId="+customerId;
			$.get(url, function( data ) {
				//alert(data);
				var pledgeResponse = $.parseJSON(data);
				if(pledgeResponse.status){
					 htmlData = '<div id="customerMsg" style="color:blue;"></div>'
					htmlData = htmlData + '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'
					htmlData = htmlData + '<tr>'+
						'<td>Household Id</td>'+
						'<td>Funding Source</td>'+
						'<td>Total Pledged</td>'+
						'<td>Vendor 1</td>'+
						'<td>Vendor 1 Acc no</td>'+
						'<td>Vendor 1 Pledge Amount</td>'+
						'<td>Vendor 2</td>'+
						'<td>Vendor 2 Acc no</td>'+
						'<td>Vendor 2 Pledge Amount</td>'+
						'<td>Vendor 3</td>'+
						'<td>Vendor 3 Acc no</td>'+
						'<td>Vendor 3 Pledge Amount</td>'+
						'<td>Pledge Date Time</td>'+
						'<td>Urgent Status</td>'+
						'<td>Updated Status</td>'+
						'<td>Called-In Status</td>'+
						'<td>Pledge Primary/Secondary Status</td>'+
						'<td>Eligble for LIHEAP funds</td>'+
						'<td>Eligble for PSE HELP funds</td>'+
						'<td>Add</td>'+
						'<td>Edit</td>'+
						'<td>Delete</td>'+
					'</tr>';
					var js = "";
					if(pledgeResponse.customerPledgeList.length>0){
						try{ 				
							for(var i=0;i<pledgeResponse.customerPledgeList.length;i++){
								var pledgeDetails = pledgeResponse.customerPledgeList[i];
								htmlData = htmlData +  '<tr>'+
									'<td>'+(pledgeDetails.houseHoldId!=undefined ? pledgeDetails.houseHoldId : "" )+'</td>'+
									'<td>'+(pledgeDetails.fundName!=undefined ? pledgeDetails.fundName : "" )+'</td>'+
									'<td>'+(pledgeDetails.totalPledgeAmt!=undefined ? pledgeDetails.totalPledgeAmt : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor1Id!=undefined ? pledgeDetails.vendor1Id : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor1Name!=undefined ? pledgeDetails.vendor1Name : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor1Payment!=undefined ? pledgeDetails.vendor1Payment : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor2Id!=undefined ? pledgeDetails.vendor2Id : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor2Name!=undefined ? pledgeDetails.vendor2Name : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor2Payment!=undefined ? pledgeDetails.vendor2Payment : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor3IdId!=undefined ? pledgeDetails.vendor3Id : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor3Name!=undefined ? pledgeDetails.vendor3Name : "" )+'</td>'+
									'<td>'+(pledgeDetails.vendor3Payment!=undefined ? pledgeDetails.vendor3Payment : "" )+'</td>'+							
									'<td>'+(pledgeDetails.pledgeDateTime!=undefined ? pledgeDetails.pledgeDateTime : "" )+'</td>'+
									'<td>'+(pledgeDetails.urgentStatus!=undefined ? pledgeDetails.urgentStatus : "" )+'</td>'+
									'<td>'+(pledgeDetails.updatedStatus!=undefined ? pledgeDetails.updatedStatus : "" )+'</td>'+
									'<td>'+(pledgeDetails.calledinStatus!=undefined ? pledgeDetails.calledinStatus : "" )+'</td>'+
									'<td>'+(pledgeDetails.primaryStatus!=undefined ? pledgeDetails.primaryStatus : "" )+'</td>'+						
									'<td><a href="javascript:" class="ex2trigger" style="text-decoration: none"><p class="editFundStatus" data="custId='+pledgeDetails.customerId+'&fundName=LIHEAP&currentFundStatus='+(pledgeDetails.liheapFund!=undefined ? pledgeDetails.liheapFund : "No")+'">'+(pledgeDetails.liheapFund!=undefined ? pledgeDetails.liheapFund : "No")+'</p></a></td>'+									
									'<td><a href="javascript:" class="ex2trigger" style="text-decoration: none"><p class="editFundStatus" data="custId='+pledgeDetails.customerId+'&fundName=PSEHELP&currentFundStatus='+(pledgeDetails.liheapFund!=undefined ? pledgeDetails.psehelpFund : "No")+'">'+(pledgeDetails.liheapFund!=undefined ? pledgeDetails.psehelpFund : "No")+'</p></a></td>'+									
									'<td><a href="javascript:" class="ex2trigger" style="text-decoration: none"><p class="add" id="custId='+pledgeDetails.customerId+'&scheduleId='+pledgeDetails.scheduleId+'"> Add </p></a></td>'+
									'<td><a href="javascript:" class="ex3trigger" style="text-decoration: none"><p class="edit" id="custId='+pledgeDetails.customerId+'&custPledgeId='+pledgeDetails.customerPledgeId+'&scheduleId='+pledgeDetails.scheduleId+'" > Edit </p></a></td>'+
									'<td><a href="javascript:doNothing()" class="ex3trigger" id="custId='+pledgeDetails.customerId+'&custPledgeId='+pledgeDetails.customerPledgeId+'&scheduleId='+pledgeDetails.scheduleId+'"><img src="static/images/delete.png" width="16" height="16" alt="Delete" title="Delete" id="custId='+pledgeDetails.customerId+'&custPledgeId='+pledgeDetails.customerPledgeId+'&scheduleId='+pledgeDetails.scheduleId+' class="delete"></a></td>'+
								'</tr>';
							}
							js = '<script type="text/javascript">'+
								 '$(document).ready(function() {'+
								 '	$(".add").click(function() {	'+
								 '	   var custId_scheduleId = $(this).attr("id");'+
								 '	   var url = "add-cust-pledge-details.html?"+custId_scheduleId;'+
								 '	   alert("URL  :::::Add:::::::   "+url);'+
								 '	   $("#ex2").jqm({ajax: url,modal: true,trigger: "a.ex2trigger"});'+
								 '	});'+

								 '   $(".edit").click(function() {	'+
								 '	  var customerId_customerPledgeId_scheduleId = $(this).attr("id");'+
								 '	  var url = "edit-cust-pledge-details.html?"+customerId_customerPledgeId_scheduleId;'+
								 '	  alert("URL  :::::Edit:::::::   "+url);'+
								 '	  $("#ex3").jqm({ajax: url,modal: true,trigger: "a.ex3trigger"});'+
								 '   });'+

								 '  $(".delete").click(function() {'+	
								 '	  var customerId_customerPledgeId_scheduleId = $(this).attr("id");'+
								 '	  var url = "show-delete-cust-pledge-details.html?"+customerId_customerPledgeId_scheduleId;'+
								 '	  alert("URL  :::Delete:::::::::   "+url);'+
								 '	  $("#ex3").jqm({ajax: url,modal: true,trigger: "a.ex3trigger"});'+
								 '   });'+
								 
								 '	$(".editFundStatus").click(function() {'+	
								 '	   var data = $(this).attr("data");'+
								 '	   var url = "editFundStatus.html?"+data;'+
								 '	   alert("URL  :::Delete:::::::::   "+url);'+
								 '	   $("#ex2").jqm({ajax: url,modal: true,trigger: "a.ex2trigger"});'+
								 ' 	});'+
								 
								 '});'+
							 '</script>';
						} catch(e){
							htmlData = "<div style='color:red;'> Error while retriving Pledge details!</div>";
						}
					}
					htmlData = htmlData + '</table>';
					htmlData = htmlData + js;
				}else {
					htmlData = "<div style='color:red;'>"+pledgeResponse.message+"</div>";
				}
			});	
		}catch(e){
			htmlData = "<div style='color:red;'> Error while retriving Pledge details!</div>";
		}
		return htmlData;
	}
	
	function prepareGetSearchResponseURL(){
		var searchSelectionType = $("#searchSelectionType").val();
		var url = "getSearchResponse.html?searchSelectionType="+searchSelectionType;
		
		if(searchSelectionType=="FIRSTNAME_LASTNAME"){
			url = url + "&firstName="+($("#firstName").val())+"&lastName="+($("#lastName").val());
		} else if(searchSelectionType=="CONFIRMATION_NO"){
			url = url + "&confirmationNumber="+($("#searchData").val());
		} else if(searchSelectionType=="SSN" || searchSelectionType=="SSN_LAST_7"){
			url = url + "&accountNumber="+($("#searchData").val());
		} else if(searchSelectionType=="CONTACT_PHONE"){
			url = url + "&contactPhone="+($("#searchData").val());
		} else if(searchSelectionType=="CALLER_ID"){
			url = url + "&callerId="+($("#searchData").val());
		} else if(searchSelectionType=="ENERGY_ACCT_NO"){
			url = url + "&energyAccNO="+($("#searchData").val());
		} else if(searchSelectionType=="DOB"){
			url = url + "&dob="+($("#dobSearch").val());
		} else if(searchSelectionType=="HOUSE_HOLD"){
			url = url + "&houseHoldId="+($("#searchData").val());
		}
		return url;
	}
});	

function updateSearchSelectionType(sel) {
	try {
		var selectedValue = sel.value;
		if(selectedValue!='DOB'){
			$("#searchData").show();
			$("#dobSearch").hide();			
		}else{
			$("#searchData").hide();
			$("#dobSearch").show();	
		}
	} catch (e) {
		alert("Error : "+e);
	}
}

function updateAppointmentStatus(sel) {
	try {
		var selectedValue = sel.value;
		if(selectedValue!='-1'){
			var text = sel.options[sel.selectedIndex].text;
			var isConfirmed = confirm("Are you sure, do you want to change the appointment status to "+text);
			if(isConfirmed){
				$('#apptMsg').html("<b>Please wait, appointment status is changing to "+text+"!</b>");
				$('#apptMsg').css("color","red");
				var selectedValuesArr = selectedValue.split("_");
				var scheduleId = selectedValuesArr[0];
				var status = selectedValuesArr[1];		
				//alert('scheduleId='+scheduleId+'&status='+status);
				 
				$.ajax({
					type : 'GET',
					url : 'updateAppointmentStatus.html',           
					data: 'scheduleId='+scheduleId+'&status='+status,
					dataType: 'json',
					success:function (baseResponse) {
						if(baseResponse.status){	
							$("#apptMsg").html("<b>"+baseResponse.message+"</b>");
							$('#apptMsg').css("color","blue");	
						}else{
							$("#apptMsg").html("<b>"+baseResponse.message+"</b>");
							$('#apptMsg').css("color","red");
						}
					}
				});
				 
			  }
		}else{
			alert("Please select proper Appointment status value!");
		}
	} catch (e) {
		alert("Error : "+e);
	}
}

function cancelAppointment(scheduleId,customerId) {
	try {
		var isConfirmed = confirm("Are you sure, do you want to cancel the appointment");
		if(isConfirmed){
			$('#apptMsg').html("<b>Please wait, appointment is cancelling!</b>");
			$('#apptMsg').css("color","red");
			 
			$.ajax({
				type : 'GET',
				url : 'cancelAppointment.html',           
				data: 'scheduleId='+scheduleId,
				dataType: 'json',
				success:function (cancelAppointResponse) {
					if(cancelAppointResponse.status && cancelAppointResponse.isCancelled){	
						$("#apptMsg").html("<b>"+cancelAppointResponse.message+"</b>");
						$('#apptMsg').css("color","blue");	
						$("#apptDetailsTr_"+scheduleId).remove();
						var rowCount = $('#myTable >tbody >tr').length;
						if(rowCount==0){
							$("#apptDetailsTable_"+customerId).remove();
						}
					}else{
						$("#apptMsg").html("<b>"+cancelAppointResponse.message+"</b>");
						$('#apptMsg').css("color","red");
					}
				}
			});			 
		}
	} catch (e) {
		alert("Error : "+e);
	}
}