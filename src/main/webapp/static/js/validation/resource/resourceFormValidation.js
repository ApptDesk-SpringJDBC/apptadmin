$(document).ready(function() {   
   
   $('#addResourceReqBtn').click(function(){
	   clearResourceResponseMsgs();
	   $("#locationDetails").html("");
	   loadAddResourceForm();
	   clearMessages(true);
   });
   
  $('#deleteOrUndeleteResource').click(function() {
	  jQuery.ajaxSetup({async:false});
	  clearMessages(true);
	  clearResourceResponseMsgs();
	  var resourceId = $("#resourceId").val();
	  if(resourceId>0){
		  var deleteOrUndeleteActionURL = $('#deleteOrUndeleteActionURL').val();
		  deleteOrUndeleteActionURL = deleteOrUndeleteActionURL+"?id="+resourceId
		  $.get(deleteOrUndeleteActionURL,function(data) {
				try{
					//alert("Response  From Server ::: "+data);
					var json = $.parseJSON(data);
					var msg = json.message;
					if(json.status){						
						loadResourcePage(false);
						$("#resourceSucessesMsgDivId").html("<b>"+msg+"</b>");
					}else{	
						$("#resourceErrorMsgDivId").html("<b>"+msg+"</b>");
					}
				}catch(e){
					//alert("Error : "+e);
				}
			});
	   }
	   jQuery.ajaxSetup({async:true});
   });
   
   $('#saveOrUpdateResourceBtn').click(function() {	
	  jQuery.ajaxSetup({async:false});
      clearMessages(true);
	  clearResourceResponseMsgs();
	  var isValid = validateResourceForm();
	  if(isValid){
		  var valuesArray = $('input[name="resourceServicesSelectedServices"]:checked').map(function () {  
				return this.value;
		  }).get().join(",");
		  if(valuesArray!="" && valuesArray!=undefined && valuesArray!=null && valuesArray.length>0){
			$("#resourceServices_error").html("");
			$("#selectedServiceIds").val(valuesArray);
			
			var locationIdStr = $("#locationIdStr").val();
			if(locationIdStr!="" && locationIdStr!=undefined && locationIdStr!=null){
				$("#locationId").val(locationIdStr);
			}
			
			var formData = $("#saveOrUpdateForm").serialize();
			//alert("Form Data ::: "+formData);
			var url = $("#saveOrUpdateForm").attr('action');
			//alert("Server URL ::: "+url);
			$.post(url,formData,function(data) {
				try{
					//alert("Response  From Server ::: "+data);
					var json = $.parseJSON(data);
					var msg = json.message;
					if(json.status){
						var resourceId = $("#resourceId").val();
						//alert("resourceId :::: "+resourceId);
						if(resourceId!="" && resourceId!=undefined && resourceId>0){
							//nothning to do
						}else {
							resourceId = json.resourceId;
						}
						$("#resPageSelResId").val(resourceId);						
						loadResourcePage(false);
						$("#resourceSucessesMsgDivId" ).html("<b>"+msg+"</b>");
						changeDivSelectedClass(resourceId);
						scrollToTopOfThePage();
					}else{	
						$("#resourceErrorMsgDivId").html("<b>"+msg+"</b>");
					}
				}catch(e){
					//alert("Error : "+e);
				}
			});	
		  }else {
			  $("#resourceServices_error").html("<b>Please select Services!");
		  }
	  }
	  jQuery.ajaxSetup({async:true});
   });
   
   $('#resetResourceBtn').click(function() {
	  clearResourceResponseMsgs();
	  var resourceId = $("#resourceId").val();
	  if(resourceId>0){
		  loadResourceFormData(resourceId);
	  }else{
		  loadAddResourceForm();
	  }
   });
});

 function clearResourceResponseMsgs(){
	 $("#resourceSucessesMsgDivId").html("");
	 $("#resourceErrorMsgDivId").html("");
 }
  
 function loadAddResourceForm(){
	clearResourceResponseMsgs();
	$("#saveOrUpdateResourceBtn").val("Save");
	$('input:radio[name=enable][value=Y]').attr('checked', true);
	$('input:radio[name=allowSelfService][value=N]').attr('checked', true);
	$("#resourceId").val("0");
	$("#saveOrUpdateForm").attr('action','saveResource.html');
	clearPageFormData();
	$("#actionButtonsDiv").show();
	$(".error").html("");
	loadLocationDetails();
	if($("#resourceServicesEnabled").val()=='Y'){
	   var selectedServiceIds = $("#selectedServiceIds").val();
	   loadServiceDetailsAsCheckbox('resourceServices','resourceServices_error',selectedServiceIds,"resourceServicesSelectedServices");
	}
	$("#addOrEditLabel").html("Add ");
 }
 
 function loadLocationDetails(){
	 clearResourceResponseMsgs();
	 if($("#locationNameEnabled").val()=='Y'){
		 $("#locationNameSelect").show();
		 $("#locationNameText").hide();
		 loadLocationDetailsAsDropDown('locationIdStr','locationIdStr_error','N');
	 }	
 }
 
 function loadScheduledResourceEditData(resourceId) {
	clearResourceResponseMsgs();
	//alert("loadScheduledResourceEditData  ::::::::::::::::::: resourceId  :::::::::: "+resourceId);
	$('#deleteOrUndeleteActionURL').val('deleteResource.html');
	$("#deleteOrUndeleteResource").val("Delete");
	changeDivSelectedClass(resourceId);
	$("#actionButtonsDiv").show();
	$("#addOrEditLabel").html("Edit ");
	var resPageSelResId = $("#resPageSelResId").val();
	if(resPageSelResId!="" && resPageSelResId!=undefined && resPageSelResId>0){
		resourceId = resPageSelResId;
		$("#resPageSelResId").val(0);
	}
	loadResourceFormData(resourceId);	
	clearMessages(true);
 }
 
 function loadSuspendedResourceEditData(resourceId) {
	clearResourceResponseMsgs();
	$('#deleteOrUndeleteActionURL').val('unDeleteResource.html');
	$("#deleteOrUndeleteResource").val("Un Delete");
	changeDivSelectedClass(resourceId);	
	$("#actionButtonsDiv").hide();
	$("#addOrEditLabel").html("Edit ");
	loadResourceFormData(resourceId);	
	clearMessages(true);
 }
  
 function loadResourceFormData(resourceId) {
	showBlockUI(); 
	clearResourceResponseMsgs();
	$(".error").html("");	
	if(resourceId>0){
		$("#saveOrUpdateForm").attr('action','updateResource.html');	
		$("#saveOrUpdateResourceBtn").val("Update");
		try{
			$.getJSON("getResourceById.html?id="+resourceId,function(data){
				if(data!=null) {
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
					$("#locationNameText").html(data.locationName);
					if($("#resourceServicesEnabled").val()=='Y'){
					   var selectedServiceIds = $("#selectedServiceIds").val();
					   loadServiceDetailsAsCheckbox('resourceServices','resourceServices_error',selectedServiceIds,"resourceServicesSelectedServices");
					}
					if($("#locationNameEnabled").val()=='Y'){
						$("#locationNameSelect").hide();
						$("#locationNameText").show();
					}
				} else {
					alert("Some thing went wrong!!!");
				}
			});
		}catch(ex){
			alert("Error :::: "+ex);
		}
	}else {
		loadAddResourceForm();
	}
	showUnBlockUI();
 }
   
 function validateResourceForm() {
	 
	var sucessCount = 0;
	var errorCount = 0;
	if($('[name="firstName"]').attr('type')=="text"){
		sucessCount = checkAlphabeticNumWithSpaceFields('firstName','firstName_error','First Name ');   
		if(sucessCount==0){
			errorCount = 1;
			$("body").scrollTop($("#firstName").offset().top);
		}
	} 
	if($('[name="lastName"]').attr('type')=="text"){
		var lastName = $("#lastName").val();
		if(lastName!="" && lastName!=null && lastName!=undefined){
			sucessCount = checkAlphabeticNumWithSpaceFields('lastName','lastName_error','Last Name ');   
			if(sucessCount==0){
				errorCount = 1;
				$("body").scrollTop($("#lastName").offset().top);
			}
		}
	} 
	
	if($('[name="email"]').attr('type')=="text"){
		var email = $("#email").val();
		if(email!="" && email!=null && email!=undefined){
			sucessCount = checkEmailIDFieldsWithInputValue(email,"email_error","Email"); 
			if(sucessCount==0){
				errorCount = 1;
				$("body").scrollTop($("#email").offset().top);
			}
		}	
	} 		
	if(errorCount==0){
		if(errorCount==0){
			return true;
		}else{
			return false;
		}
	}else {
		return false;
	}
 }