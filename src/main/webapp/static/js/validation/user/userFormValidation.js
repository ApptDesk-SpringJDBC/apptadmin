$(document).ready(function() {   
   $('#addUserReqBtn').click(function(){
	   $("#accessLevel").val("Super User");
	   loadAddUserForm();
	   clearMessages(true);
   });
   
  $('#deleteOrUndeleteUser').click(function() {
	  clearMessages(true);
	  var userLoginId = $("#userLoginId").val();
	  if(userLoginId>0){
		  var deleteOrUndeleteActionURL = $('#deleteOrUndeleteActionURL').val();
		  deleteOrUndeleteActionURL = deleteOrUndeleteActionURL+"?id="+userLoginId
		  $.get(deleteOrUndeleteActionURL,function(data) {
				try{
					//alert("Response  From Server ::: "+data);
					var json = $.parseJSON(data);
					var msg = json.message;
					if(json.status){
						$("#sucessesMsgDivId" ).html("<b>"+msg+"</b>");
						loadUsersPage(false);
					}else{	
						$("#errorMsgDivId").html("<b>"+msg+"</b>");
					}
				}catch(e){
					//alert("Error : "+e);
				}
			});
	   }
   });
   
   $('#saveOrUpdateUserBtn').click(function() {
	   try{
		  clearMessages(true);
		  var isValid = validateUserForm();
		  var isValidAccessLevel = populateAndValidateAccessLevelSelectedData();
		  
		  if(isValid && isValidAccessLevel){			  
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
						$("#sucessesMsgDivId" ).html("<b>"+msg+"</b>");
						loadUsersPage(false);
					}else{	
						$("#errorMsgDivId").html("<b>"+msg+"</b>");
					}
				}catch(e){
					//alert("Error : "+e);
				}
			});	
		  }
	   }catch(e){
			//alert("Error : "+e);
	   }
   });
   
   $('#saveOrUpdateUserBtn1234').click(function() {	      
      clearMessages(true);
	  var isValid = validateUserForm();
	  if(isValid){
		  var valuesArray = $('input[name="userServicesSelectedServices"]:checked').map(function () {  
				return this.value;
		  }).get().join(",");
		  if(valuesArray!="" && valuesArray!=undefined && valuesArray!=null && valuesArray.length>0){
			$("#userServices_error").html("");
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
						$("#sucessesMsgDivId" ).html("<b>"+msg+"</b>");
						loadUsersPage(false);
					}else{	
						$("#errorMsgDivId").html("<b>"+msg+"</b>");
					}
				}catch(e){
					//alert("Error : "+e);
				}
			});	
		  }else {
			  $("#userServices_error").html("<b>Please select Services!");
		  }
	  }
   });
   
   $('#resetUserBtn').click(function() {	
	  var userLoginId = $("#userLoginId").val();
	  if(userLoginId>0){
		  loadUserFormData(userLoginId);
	  }else{
		  loadAddUserForm();
	  }
   });
	
});
 
 function populateAndValidateAccessLevelSelectedData(){	 
	var isValid = false;
	var accessLevel = $("input[name='accessLevel']:checked").val();//$("#accessLevel").val();
	$("#locationIds").val("");
	$("#resourceIds").val("");
	$("#accessLevel_error").html("");
	if(accessLevel=="Location"){
		var locationIdsStr = $("#locationIdsStr").val();
		if(locationIdsStr!=null && locationIdsStr!="" && locationIdsStr!=undefined){			
			$("#locationIds").val(locationIdsStr);
			isValid = true;
		}else {			
			$("#accessLevel_error").html("Please select proper "+($("#locationLabel").val()));
		}		
	}else if (accessLevel=="Intake"){
		var resourceIdsStr = $("#resourceIdsStr").val();
		if(resourceIdsStr!=null && resourceIdsStr!="" && resourceIdsStr!=undefined){			
			$("#resourceIds").val(resourceIdsStr);
			isValid = true;
		}else {			
			$("#accessLevel_error").html("Please select proper "+($("#resourceLabel").val()));
		}
	} else {
		isValid = true;
	}
	return isValid;
 }
  
 function changePasswordChanged(){
	if($('#changePasswordCheckbox').is(":checked")) {
	  $("#changePasswordDetails").show();
	  $("#passwordUpdate").val("Y");
	} else {
	  $("#changePasswordDetails").hide();
	  $("#passwordUpdate").val("N");
	}
 }
 
 function loadAddUserForm(){
	$("#deleteOrUndeleteUser").hide();
	$("#saveOrUpdateUserBtn").val("Save");
	$("#userLoginId").val("0");
	$("#saveOrUpdateForm").attr('action','saveUser.html');
	clearPageFormData();
	$(".error").html("");
	$("#addOrEditLabel").html("Add ");
	if($("#changePasswordEnabled").val()=='Y'){
		$("#changePasswordDiv").hide();
	}
	$("#passwordAddDetails").show();
 }
 
 function loadScheduledUserEditData(userLoginId) {
	$("#deleteOrUndeleteUser").show();
	$('#deleteOrUndeleteActionURL').val('deleteUser.html');
	$("#deleteOrUndeleteUser").val("Delete");
	changeDivSelectedClass(userLoginId);
	$("#addOrEditLabel").html("Edit ");
	loadUserFormData(userLoginId);	
	clearMessages(true);
	if($("#changePasswordEnabled").val()=='Y'){
		$("#changePasswordDiv").show();
	}
	$("#passwordAddDetails").hide();
	$("#userNameValid").val("Y")
 }
 
 function loadUserFormData(userLoginId) {	
	$(".error").html("");	
	if(userLoginId>0){
		$("#saveOrUpdateForm").attr('action','updateUser.html');	
		$("#saveOrUpdateUserBtn").val("Update");
		$.getJSON("getUserById.html?id="+userLoginId,function(data){
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
			
			if($('[name="contactPhone1"]').attr('type')=="text"){
				var contactPhone = $("#contactPhone").val();
				if(contactPhone!=null && contactPhone!="" && contactPhone!=undefined){
					$("#contactPhone1").val(contactPhone.substring(0,3));
					$("#contactPhone2").val(contactPhone.substring(3,6));
					$("#contactPhone3").val(contactPhone.substring(6));
				}
			}
		});
	}else {
		loadAddUserForm();
	}
 }
   
 function validateUserForm() {
	try {
		var sucessCount = 0;
		var errorCount = 0;
		if($('[name="firstName"]').attr('type')=="text"){
			sucessCount = checkAlphaNumericFieldsWithSpace('firstName','firstName_error','First Name ');   
			if(sucessCount==0){
				errorCount = errorCount + 1;
				$("body").scrollTop($("#firstName").offset().top);
			}
		} 
		if($('[name="lastName"]').attr('type')=="text"){
			sucessCount = checkAlphaNumericFieldsWithSpace('lastName','lastName_error','Last Name '); 
			if(sucessCount==0){
				errorCount = errorCount + 1;
				$("body").scrollTop($("#lastName").offset().top);
			}
		} 
		
		if($('[name="email"]').attr('type')=="text"){
			sucessCount = checkEmailIDFieldsWithInputValue(email,"email_error","Email"); 
			if(sucessCount==0){
				errorCount = errorCount + 1;
				$("body").scrollTop($("#email").offset().top);
			}	
		} 
		
		if($('[name="contactPhone1"]').attr('type')=="text"){
			sucessCount = checkNumericFieldsWithLenght('contactPhone','contactPhone_error','Contact Phone ',10);   
			if(sucessCount==0){
				errorCount = errorCount + 1;
			}
		}
		
		validatePassword();
		
		if($('[name="username"]').attr('type')=="text"){
			var username = $("#username").val();
	 		if(username!="" && username!=null && username!=undefined){
				var userNameValid = $("#userNameValid").val();
				if(userNameValid=='N'){
					errorCount = errorCount + 1;
					$("#username_error").html("<b>Please select proper User name.</b>");
					$("#username_error").css("color", "red");
					$("#userNameValid").val("N");
					$("body").scrollTop($("#username").offset().top);
				}else {
					//Usser Name is valid....
				}
			} else {
				errorCount = errorCount + 1;
				$("#username_error").html("<b>User name cannot be empty.</b>");
				$("#username_error").css("color", "red");
				$("#userNameValid").val("N");
				$("body").scrollTop($("#username").offset().top);
			}
		}
		if(errorCount==0){
			return true;
		}else {
			return false;
		}
	}catch(e){
		//alert("Error ::: "+e);
		return false;
	}
 }
 
function enableLocationDropDown(){
	$("#resourceIdsStr").hide();
	$("#locationIdsStr").show();
}

function enableResourceDropDown(){
	$("#resourceIdsStr").show();
	$("#locationIdsStr").hide();
}

function hideLocationAndResourceDropDowns(){
	$("#resourceIdsStr").hide();
	$("#locationIdsStr").hide();
}

function validateUserName(){
	 var userLoginId = $('#userLoginId').val();
	 var username = $('#username').val();
	 if(username != ''){
		var url = "validateUserName.html?userName="+username+"&userId="+userLoginId;
		 $.get(url,function(data) {
			var json = $.parseJSON(data);
			var msg = json.message;
			if(json.status){
				$("#username_error" ).html("<b>"+msg+"</b>");
				$("#username_error").css("color", "green");
				$("#userNameValid").val("Y");
			}else{	
				$("#username_error").html("<b>"+msg+"</b>");
				$("#username_error").css("color", "red");
				$("#userNameValid").val("N");
			}
		 });
	 }else{
		$("#username_error").html("<b>User name cannot be empty.</b>");
		$("#username_error").css("color", "red");
		$("#userNameValid").val("N");
		$("#username").focus();
	 }
}

function validatePassword(){
	 var isValid = false;
	 var url = $("#saveOrUpdateForm").attr('action');
	 var userName = $('#userName').val();
	 if(userName != ''){
		$('#username_error').html(""); 
		
		//Password data population starts...		
		 var password = "";
		 if(url=="saveUser.html"){
			var passwordData = $("#passwordData").val();
			if (passwordData != "") {
				$('#passwordData_error').html(""); 
				password = passwordData;
			} else {
				$('#passwordData_error').html("<b>Password cannot be empty.</b>");
				$("#passwordData").focus();
				password = "";
			}
		 }else{
			var newPassword = $("#newPassword").val();
			var confirmPassword = $("#confirmPassword").val();
			
			if(newPassword != "" && confirmPassword!="") {
				if (newPassword == confirmPassword) {
					$('#newPassword_error').html(""); 
					password = newPassword;
				}else {
					$('#newPassword_error').html("<b>* New and Confirm Passwords should be same.</b>");
					$("#newPassword").focus();
				}
			} else {
				password = "";
				if (newPassword != "") {
					$('#newPassword_error').html(""); 
				} else {
					$('#newPassword_error').html("<b>New Password cannot be empty.</b>");
					$("#newPassword").focus();
				}
				if (confirmPassword != "") {
					$('#confirmPassword_error').html(""); 
				} else {
					$('#confirmPassword_error').html("<b>Confirm Password cannot be empty.</b>");
					$("#confirmPassword").focus();
				}
			}
		 }
		 
		//Password validation starts...	
		if (password != "") {
			
			 password = password.replace("%","%25"); 
			 password = password.replace("#","%23"); 
			 password = password.replace("-","%2D");
			 var url = "validatePassword.html?username="+userName+"&password="+password;
			 //alert("url ------------> "+url);		
			 $.get(url,function(data) {
				//alert( "Data ---------------> "+data);
				$("#passwordValidationResponse").html(data);
				if(data!=""){
					$('#newpassworderror').html("<b>* "+data+".</b>"); 
				}else{
					$('#newpassworderror').html(""); 
					$("#passwordValidationResponse").html("");
					isValid = true;
				}
			 });
		}else {
			if(url=="saveUser.html"){
				var passwordData = $("#passwordData").val();
				if (passwordData != "") {
					$('#passwordData_error').html(""); 
					password = passwordData;
				} else {
					$('#passwordData_error').html("<b>Password cannot be empty.</b>");
					$("#passwordData").focus();
					password = "";
				}
			 }else{
				var newPassword = $("#newPassword").val();
				var confirmPassword = $("#confirmPassword").val();
				
				if(newPassword != "" && confirmPassword!="") {
					if (newPassword == confirmPassword) {
						$('#newPassword_error').html(""); 
						password = newPassword;
					}else {
						$('#newPassword_error').html("<b>* New and Confirm Passwords should be same.</b>");
						$("#newPassword").focus();
					}
				} else {
					password = "";
					if (newPassword != "") {
						$('#newPassword_error').html(""); 
					} else {
						$('#newPassword_error').html("<b>New Password cannot be empty.</b>");
						$("#newPassword").focus();
					}
					if (confirmPassword != "") {
						$('#confirmPassword_error').html(""); 
					} else {
						$('#confirmPassword_error').html("<b>Confirm Password cannot be empty.</b>");
						$("#confirmPassword").focus();
					}
				}
			 }
			 $('#newPassword_error').html("<b>Password cannot be empty.</b>");
			 $("#newPassword").focus();
		}
	 }else{
		$('#username_error').html('<b>User name cannot be empty.</b>');  
		$("#username_error").css("color", "red");
		$("#username").focus();
	 }
	 return isValid;
}