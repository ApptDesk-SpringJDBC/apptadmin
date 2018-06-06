$(document).ready(function() {   
   $(".phone").bind('keyup', function() {
		var limit = parseInt($(this).attr('maxlength'));  
		var chars = $(this).val().length; 
		if(chars >=limit){  	
			$("#"+$(this).next().attr("id")).focus();
		}	
	});	
		
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
	
   $('#addLocationReqBtn').click(function() {
	  $("#addOrEditLocationLabel").html("Add ");
	  clearLocationResponseMsgs();
	  loadAddLocationForm();
   });

   $('#deleteOrUndeleteLocation').click(function() {
	  jQuery.ajaxSetup({async:false});
	  clearLocationResponseMsgs();
	  var locationId = $("#locationId").val();
	  if(locationId>0){
		  var deleteOrUndeleteActionURL = $('#deleteOrUndeleteActionURL').val();
		  deleteOrUndeleteActionURL = deleteOrUndeleteActionURL+"?id="+locationId
		  $.get(deleteOrUndeleteActionURL,function(data) {
				try{
					//$("#locPageSelLocId").val($("#locationId").val());
					//alert("Response  From Server ::: "+data);
					//alert("locationId :::::: "+($("#locationId").val()));
					//alert("locationId :::::: "+($("#locPageSelLocId").val()));
					var json = $.parseJSON(data);
					var msg = json.message;
					if(json.status){
						loadLocationPage(false);
						$("#locationSucessesMsgDivId" ).html("<b>"+msg+"</b>");		
						//scrollToTopOfThePage();
					    //changeDivSelectedClass($("#locPageSelLocId").val());						
					}else{	
						$("#locationErrorMsgDivId").html("<b>"+msg+"</b>");
					}					
				}catch(e){
					//alert("Error : "+e);
				}
			});
	   }
	   jQuery.ajaxSetup({async:true});
   });
   
   $('#saveOrUpdateLocationBtn').click(function() {	
	  jQuery.ajaxSetup({async:false});
      clearLocationResponseMsgs();
	  var isValid = validateLocationForm();
	  if(isValid){
		var formData = $("#saveOrUpdateForm").serialize();
		//alert("formData :::: "+formData);		
		var url = $("#saveOrUpdateForm").attr('action');
		$.post(url,formData,function(data) {
			try{
				//alert("Response  From Server ::: "+data);
				var json = $.parseJSON(data);
				var msg = json.message;
				if(json.status){
					var locationId = $("#locationId").val();
					//alert("locationId ::11111111111111111111111:: "+locationId);
					if(locationId!="" && locationId!=undefined && locationId>0){
						//nothning to do
					}else {
						locationId = json.locationId;
					}
					//alert("locationId :::: "+locationId);
					$("#locPageSelLocId").val(locationId);
					loadLocationPage(false);
					$("#locationSucessesMsgDivId").html("<b>"+msg+"</b>");
					changeDivSelectedClass(locationId);
					scrollToTopOfThePage();
				}else{	
					$("#locationErrorMsgDivId").html("<b>"+msg+"</b>");
				}				
			}catch(e){
				//alert("Error : "+e);
			}
		});	  
	  }
	  jQuery.ajaxSetup({async:true});
   });
   
   
   $('#resetLocationBtn').click(function() {	
	  var locationId = $("#locationId").val();
	  clearLocationResponseMsgs();
	  if(locationId>0){
		  loadLoactionFormData(locationId);
	  }else{
		  loadAddLocationForm();
	  }
   });
});
 
 function clearLocationResponseMsgs(){
	 $("#locationSucessesMsgDivId" ).html("");
	 $("#locationErrorMsgDivId").html("");
 }
 
 function loadAddLocationForm(){
	$("#saveOrUpdateLocationBtn").val("Save");
	$('input:radio[name=enable][value=Y]').attr('checked', true);
	$('input:radio[name=closed][value=N]').attr('checked', true);
	$("#locationId").val("0");
	$("#saveOrUpdateForm").attr('action','saveLocation.html');
	clearPageFormData();	
	clearLocationResponseMsgs();	
	$(".error").html("");
 }
 
 function loadScheduledLoactionEditData(locationId) {
	$('#deleteOrUndeleteActionURL').val('deleteLocation.html');
	$("#deleteOrUndeleteLocation").val("Delete");
	$("#addOrEditLocationLabel").html("Edit ");
	clearLocationResponseMsgs();
	changeDivSelectedClass(locationId);
	var locPageSelLocId = $("#locPageSelLocId").val();
	if(locPageSelLocId!="" && locPageSelLocId!=undefined && locPageSelLocId>0){
		locationId = locPageSelLocId;
		$("#locPageSelLocId").val(0);
	}
	loadLoactionFormData(locationId);	 
 }
 
 function loadSuspendedLoactionEditData(locationId) {
	$('#deleteOrUndeleteActionURL').val('unDeleteLocation.html');
	$("#deleteOrUndeleteLocation").val("Un Delete");
	changeDivSelectedClass(locationId);
	loadLoactionFormData(locationId);	
	clearLocationResponseMsgs();
 }
 
 function loadLoactionFormData(locationId) {
	$(".error").html("");	
	if(locationId>0){
		$("#saveOrUpdateForm").attr('action','updateLocations.html');	
		$("#saveOrUpdateLocationBtn").val("Update");
		$.getJSON("getLocationById.html?id="+locationId,function(data){
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
			
			if($('[name="workPhone1"]').attr('type')=="text"){
				var workPhone = $("#workPhone").val();	
				 
				if(workPhone.includes("-")){
					 var workPhoneArr = workPhone.split("-");
					 $("#workPhone1").val(workPhoneArr[0]);
					 $("#workPhone2").val(workPhoneArr[1]);
					 $("#workPhone3").val(workPhoneArr[2]);
				} else {
					if(workPhone!=null && workPhone!="" && workPhone!=undefined){
						$("#workPhone1").val(workPhone.substring(0,3));
						$("#workPhone2").val(workPhone.substring(3,6));
						$("#workPhone3").val(workPhone.substring(6));
					}
				}				
			}
		});
	}else {
		loadAddLocationForm();
	}
 }
 
 function validateLocationForm() {
	 
	var sucessCount = 0;
	var errorCount = 0;
	if($('[name="locationNameOnline"]').attr('type')=="text"){
		sucessCount = checkAlphaNumericFieldsWithSpace('locationNameOnline','locationNameOnline_error','Name ');   
		if(sucessCount==0){
			errorCount = 1;
			$("body").scrollTop($("#locationNameOnline").offset().top);
		}
	} 
	
	if(errorCount==0){				
		if($('[name="workPhone1"]').attr('type')=="text"){
			var workPhone = populateFieldData("workPhone",3);
			if(workPhone!="" && workPhone!=null && workPhone!=undefined){
				sucessCount = checkNumericFieldsWithLenght('workPhone','workPhone_error','Work Phone ',10);   
				if(sucessCount==0){
					errorCount = errorCount + 1;
				}
			}
		}
		if($('[name="zip"]').attr('type')=="text"){
			var zip = $("#zip").val();	
			if(zip!="" && zip!=null && zip!=undefined){			
				sucessCount = checkNumericFieldsWithLenght('zip','zip_error','Zip ',5); 
                if(sucessCount==0){
					errorCount = errorCount + 1;
				}				
			}
		}
		if($('[name="locationNameIvrTts"]').attr('type')=="text"){
			var locationNameIvrTts = $("#locationNameIvrTts").val();	
			if(locationNameIvrTts!="" && locationNameIvrTts!=null && locationNameIvrTts!=undefined){			
				sucessCount = checkAlphaNumericSpaceFieldsWithInputValue(locationNameIvrTts,'locationNameIvrTts_error','IVR TTS '); 
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