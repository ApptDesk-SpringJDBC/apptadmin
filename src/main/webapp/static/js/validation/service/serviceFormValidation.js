$(document).ready(function() {   
	
   if($("input:radio[name='closed'][value='Y']").is(":checked")) { 
      $("#closedLocationDetails").show();
	  loadServiceClosedLocationDetails();
   } 
    
   try{
		if($('[name="apptStartDate"]').attr('type')=="text"){
			new JsDatePick({
				useMode : 2,
				target : "apptStartDate",
				dateFormat : "%M/%d/%Y"
			});	
		}
	}catch(e){
		//alert("Exception ::: "+e);
	}
	
	try{
		if($('[name="apptEndDate"]').attr('type')=="text"){
			new JsDatePick({
				useMode : 2,
				target : "apptEndDate",
				dateFormat : "%M/%d/%Y"
			});	
		}
	}catch(e){
		//alert("Exception ::: "+e);
	}
	
   $('#addServiceReqBtn').click(function(){
	   clearServiceResponseMsgs();
	   $("#closedLocationDetails").hide();
	   $("#locationDetails").html("");
	   loadAddServiceForm();
   });
   
   $('input[type=radio][name=closed]').change(function() {
        if (this.value == 'N') {
            $("#closedLocationDetails").hide();
        }
        if (this.value == 'Y') {
			$("#closedLocationDetails").show();	
			loadServiceClosedLocationDetails();			
        }
    });

   $('#deleteOrUndeleteService').click(function() {
	  jQuery.ajaxSetup({async:false});
	  clearServiceResponseMsgs();
	  var serviceId = $("#serviceId").val();
	  if(serviceId>0){
		  var deleteOrUndeleteActionURL = $('#deleteOrUndeleteActionURL').val();
		  deleteOrUndeleteActionURL = deleteOrUndeleteActionURL+"?id="+serviceId;
		  //alert("deleteOrUndeleteActionURL  ::: "+deleteOrUndeleteActionURL);
		  $.get(deleteOrUndeleteActionURL,function(data) {
				try{
					//$("#locPageSelLocId").val($("#locationId").val());
					//alert("Response  From Server ::: "+data);
					var json = $.parseJSON(data);
					var msg = json.message;
					if(json.status){
						loadServicePage(false);
						$("#serviceSucessesMsgDivId" ).html("<b>"+msg+"</b>");						
					}else{	
						$("#serviceErrorMsgDivId").html("<b>"+msg+"</b>");
					}
				}catch(e){
					//alert("Error : "+e);
				}
			});
	   }
	   jQuery.ajaxSetup({async:true});
   });
   
   $('#saveOrUpdateServiceBtn').click(function() {	
      jQuery.ajaxSetup({async:false});
      var durationEnabled = $("#durationEnabled").val();
	  if(durationEnabled=='Y'){
		 var serviceDuration = $( "#serviceDuration" ).val();	
		  if(serviceDuration!=null && serviceDuration!="" && serviceDuration!=undefined){
			 $("#duration").val(serviceDuration);
		  }
	  }
	  readAllowSchedulingForData();
	  
      clearServiceResponseMsgs();
	  var isValid = validateServiceForm();
	  if(isValid){
		var formData = $("#saveOrUpdateForm").serialize();
		var url = $("#saveOrUpdateForm").attr('action');
		$.post(url,formData,function(data) {
			try{
				//alert("Response  From Server ::: "+data);
				var json = $.parseJSON(data);
				var msg = json.message;
				if(json.status){
					var serviceId = $("#serviceId").val();
					//alert("serviceId :::: "+serviceId);
					if(serviceId!="" && serviceId!=undefined && serviceId>0){
						//nothning to do
					}else {
						serviceId = json.serviceId;
					}
					$("#serPageSelSerId").val(serviceId);					
					loadServicePage(false);
					$("#serviceSucessesMsgDivId").html("<b>"+msg+"</b>");
					changeDivSelectedClass(serviceId);
					scrollToTopOfThePage();
				}else{	
					$("#serviceErrorMsgDivId").html("<b>"+msg+"</b>");
				}
			}catch(e){
				//alert("Error : "+e);
			}
		});	  
	  }
	  jQuery.ajaxSetup({async:true});
   });
   
   $('#resetServiceBtn').click(function() {	
      clearServiceResponseMsgs();
	  var serviceId = $("#serviceId").val();
	  if(serviceId>0){
		  loadServiceFormData(serviceId);
	  }else{
		  loadAddServiceForm();
	  }
   });
});
 
 function loadServiceClosedLocationDetails(){
	if($("#closedLocationsEnabled").val() == 'Y'){
		$.get("getLocationsByServiceIdToCloseServiceStatus.html?id="+($("#serviceId").val()),function(data) {
			try{
				var closedLocationIds = $("#closedLocationIds").val();
				var json = $.parseJSON(data);
				if(json.status) {
					if(json.locationList.length>0) {
						var locationsHTML = "";
						for(var i=0;i<json.locationList.length;i++){
							var locId = json.locationList[i].locationId;
							var locName = json.locationList[i].locationNameOnline;
							var checked = "";
							if(closedLocationIds!="" && closedLocationIds!=null && closedLocationIds!=undefined){
								if(closedLocationIds.indexOf(locId)>=0){
									checked = "checked";
								}
							} else {
								checked = "checked";
							}
							locationsHTML = locationsHTML + "<input name=\"selectedlocationIds\" "+checked;
							locationsHTML = locationsHTML + " class=\"noWidth\" type=\"checkbox\" value=\""+locId+"\">"+locName;
						}
						$("#locationDetails").html(locationsHTML);
					}
				} else {
					$("#locationDetails").html("Error while fetching locations!!!");
				}
			}catch(e){
				//alert("Error : "+e);
			}
		});
	}
 }
 
 function clearServiceResponseMsgs(){
	 $("#serviceSucessesMsgDivId" ).html("");
	 $("#serviceErrorMsgDivId").html("");
 }
 
 function loadAddServiceForm(){
	clearServiceResponseMsgs();
	$("#saveOrUpdateServiceBtn").val("Save");
	$('input:radio[name=enable][value=Y]').attr('checked', true);
	$('input:radio[name=closed][value=N]').attr('checked', true);
	$("#serviceId").val("0");
	$("#saveOrUpdateForm").attr('action','saveService.html');
	clearPageFormData();
	var durationEnabled = $("#durationEnabled").val();
	if(durationEnabled=='Y'){
		$("#serviceDurationSelect").show();
		$("#serviceDurationText").hide();
		loadDurationDetails();
	}
	$("#actionButtonsDiv").show();
	$(".error").html("");
	
 }
 
 function loadScheduledServiceEditData(serviceId) {
	clearServiceResponseMsgs();
	$('#deleteOrUndeleteActionURL').val('deleteService.html');
	$("#deleteOrUndeleteService").val("Delete");
	changeDivSelectedClass(serviceId);
	$("#actionButtonsDiv").show();
	var serPageSelSerId = $("#serPageSelSerId").val();
	if(serPageSelSerId!="" && serPageSelSerId!=undefined && serPageSelSerId>0){
		serviceId = serPageSelSerId;
		$("#serPageSelSerId").val(0);
	}
	loadServiceFormData(serviceId);	
 }
 
 function loadSuspendedServiceEditData(serviceId) {
	clearServiceResponseMsgs();
	$('#deleteOrUndeleteActionURL').val('unDeleteService.html');
	$("#deleteOrUndeleteService").val("Un Delete");
	changeDivSelectedClass(serviceId);	
	$("#actionButtonsDiv").hide();
	loadServiceFormData(serviceId);	
 }
 
 function populateDurationData(){
	var durationEnabled = $("#durationEnabled").val();
	if(durationEnabled=='Y'){
		$("#serviceDurationText").show();
		var duration = $("#duration").val();
		duration = duration + " mins";
		$("#serviceDurationText").html(duration);
		$("#serviceDurationSelect").hide();		
	}
 }
 
 function loadServiceFormData(serviceId) {
	$(".error").html("");	
	if(serviceId>0){
		$("#saveOrUpdateForm").attr('action','updateService.html');	
		$("#saveOrUpdateServiceBtn").val("Update");
		$.getJSON("getServiceById.html?id="+serviceId,function(data){
			$.each(data, function(name, val){
				var $el = $('[name="'+name+'"]');
				var type = $el.attr('type');
				
				switch(type){
					case 'checkbox':
						$el.attr('checked', 'checked');
						break;
					case 'radio':
						$el.filter('[value="'+val+'"]').attr('checked', 'checked');
						break;
					default:
						$el.val(val);
				}
			});
			
			setAllowSchedulingForData();
			populateDurationData();
		});
	}else {
		loadAddServiceForm();
	}
 }
 
 function loadDurationDetails(){
	if($("#durationEnabled").val() == 'Y'){
		var blockTimeInMin = $("#blockTimeInMin").val();
		var url = "getBreakTimeDuration.html?blockTimeInMin="+blockTimeInMin;			 
		$.get( url, function( data ) {	
            //alert("data :::::::::::  "+data);		
			if(data!="" && data!=null && data!=undefined){
				var htmldata = "";
				$.each( $.parseJSON(data), function( key, value ) {
				  htmldata = htmldata + "<option value='"+key+"'>"+value+"</option>";
				});
				$("#serviceDuration").html(htmldata);
			}
		});
	}
 }
 
 var AllowSchedulingForFields = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
 
 function setAllowSchedulingForData() {
	try {
		for(var i=0;i<AllowSchedulingForFields.length;i++){
			var isOpen = $("#is"+(AllowSchedulingForFields[i])+"Open").val();
			if(isOpen=='Y'){
				$("#is"+(AllowSchedulingForFields[i])+"Open_str").prop( "checked", true );
			}else {
				$("#is"+(AllowSchedulingForFields[i])+"Open_str").prop( "checked", false );
			}
		}
	}catch(e){
		//alert("Error : "+e);
	}
 }
 
 function readAllowSchedulingForData() {
	try {
		for(var i=0;i<AllowSchedulingForFields.length;i++){
			var isChecked = $("#is"+(AllowSchedulingForFields[i])+"Open_str").prop('checked');
			if(isChecked){
				$("#is"+(AllowSchedulingForFields[i])+"Open").val("Y");
			}else {
				$("#is"+(AllowSchedulingForFields[i])+"Open").val("N");
			}
		}
	}catch(e){
		//alert("Error : "+e);
	}
 }
 
  function readClosedLocationsDataData() {
	try {
		 if($("#closedLocationsEnabled").val()=='Y'){
			 var selectedlocationIdsArr = [];
			 $.each($("input[name='selectedlocationIds']:checked"), function(){            
				selectedlocationIdsArr.push($(this).val());
			 });
			 $("#closedLocationIds").val(selectedlocationIdsArr.join(", "));
		 }
	}catch(e){
		//alert("Error : "+e);
	}
 }
 
 function validateAllowSchedulingForData() {
	try {
		var validCount = 0;
		for(var i=0;i<AllowSchedulingForFields.length;i++){
			var isChecked = $("#is"+(AllowSchedulingForFields[i])+"Open_str").prop('checked');
			if(isChecked){
				validCount = validCount + 1;
			}
		}
		if(validCount>0){
			return true;
		}
	}catch(e){
		//alert("Error : "+e);
	}
	return false;
 }
 
 function validateServiceForm() {
	 
	var sucessCount = 0;
	var errorCount = 0;
	if($('[name="serviceNameOnline"]').attr('type')=="text"){
		sucessCount = checkAlphaNumericFieldsWithSpace('serviceNameOnline','serviceNameOnline_error','Name ');   
		if(sucessCount==0){
			errorCount = 1;
			$("body").scrollTop($("#serviceNameOnline").offset().top);
		}
	} 
	
	if($('[name="isSunOpen_str"]').attr('type')=="checkbox"){
		var isValid = validateAllowSchedulingForData();
		if(isValid){		
			$("#allowSchedulingFor_error").html("");
		}else {
			errorCount = 1;
			$("#allowSchedulingFor_error").html("Please select Allow Scheduling for");
		}
	}
	
	if(errorCount==0){
		if($('[name="serviceNameIvrTts"]').attr('type')=="text"){
			var serviceNameIvrTts = $("#serviceNameIvrTts").val();	
			if(serviceNameIvrTts!="" && serviceNameIvrTts!=null && serviceNameIvrTts!=undefined){			
				sucessCount = checkAlphaNumericSpaceFieldsWithInputValue(serviceNameIvrTts,'serviceNameIvrTts_error','IVR TTS '); 
				if(sucessCount==0){
					errorCount = errorCount + 1;
				}
			}
		}
		
		if(($('[name="apptStartDate"]').attr('type')=="text") || ($('[name="apptEndDate"]').attr('type')=="text")){
			var apptStartDate = $("#apptStartDate").val();
			var apptEndDate = $("#apptEndDate").val();
			if((apptStartDate!="" && apptStartDate!=null && apptStartDate!=undefined) ||
				(apptEndDate!="" && apptEndDate!=null && apptEndDate!=undefined)){	
				var startDateValid = false;
				if(apptStartDate!="" && apptStartDate!=null && apptStartDate!=undefined){
					startDateValid = validateNonMandatoryDateField("apptStartDate","apptStartDate_error","* Start Date");
				}
				var endDateValid = false;
				if(apptEndDate!="" && apptEndDate!=null && apptEndDate!=undefined){
					endDateValid = validateNonMandatoryDateField("apptEndDate","apptEndDate_error","* End Date");
				}
				
				if(startDateValid && endDateValid){
					sucessCount = compareMMDDYYYYDateswithMaxDays(apptStartDate,apptEndDate,0);
					if(sucessCount>=0){
						$('#apptEndDate_error').html("");
					} else {
						errorCount = errorCount + 1;
						$('#apptEndDate_error').html("* Start date should be less than orr equal to End date");
					}
				}else {
					errorCount = errorCount + 1;
					if(startDateValid==false){
						$('#apptStartDate_error').html("* Start Date should be in MM/dd/yyyy format");
					}
					if(endDateValid==false){
						$('#apptEndDate_error').html("* End Date should be in MM/dd/yyyy format");
					}
				}					
			}
		}
		
		var closed = $("input[name='closed']:checked").val();
		if(closed=='Y'){
			if($('[name="closedMessage"]').attr('type')=="text"){
				var valid = validateNotNullField("closedMessage","closedMessage_error","Closed Online Message");
				if(valid==false){
					errorCount = errorCount + 1;
				}
			}
			if($('[name="closedTts"]').attr('type')=="text"){
				var valid = validateFieldDataWithPattern("closedTts","closedTts_error","Closed IVR TTS",/^[a-zA-Z0-9\s,./-]+$/,"Closed IVR TTS should contain letters, digits, space, hyphen, comma, dot only",true);
				if(valid==false){
					errorCount = errorCount + 1;
				}
			}
		}
		if(errorCount==0){
			return true;
		}else{
			return false;
		}
	}else {
		return false;
	}
 }