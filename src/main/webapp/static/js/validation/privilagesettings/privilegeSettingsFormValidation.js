$(document).ready(function () {	    
	$('#selectedAccess').change(function() {
		loadPrivilegeSettings();
	});
});

function loadPrivilegeSettings(){
	$.ajaxSetup({async:false});
	showBlockUI();
	var accessPrivilegeId = $("#selectedAccess").val();
	if(accessPrivilegeId!=-1) {
		var accessPrivilegeName = $("#selectedAccess").find("option:selected").text();
		accessPrivilegeName = accessPrivilegeName.replace(/ /g, '%20');			
		$("#privilegeSettingsErrorMsg").html("");
		var url = "getPrivilegeSettingsPage.html?accessPrivilegeId="+accessPrivilegeId+"&accessPrivilegeName="+accessPrivilegeName;
		//alert("url :::: "+url);
		$("#privilegeSettingsDiv").load(url, function() {
		  //alert( "Load was performed." );
		});
	} else {
		$("#privilegeSettingsErrorMsg").html("Please select proper Acesses Privilege");
	}
	showUnBlockUI();
	$.ajaxSetup({async:true});
}

function onPrivilegeSettingsSaveClick(){
	try{
		$.ajaxSetup({async:false});
		showBlockUI();
		var accessPrivilegeId = $("#selectedAccess").val();
		if(accessPrivilegeId!=-1) {
			var accessPrivilegeName = $("#selectedAccess").find("option:selected").text();
			$("#selectedAccessPrivilege").val(accessPrivilegeName); 		
			$("#privilegeSettingsErrorMsg").html("");
			var url = "updatePrivilegeSettings.html";
			//alert("url :::: "+url);
			var formData = $("#privilegeSettingsForm").serialize();
			//alert("formData.........."+formData);
		 
			$.post(url,formData, function( data ) {
				//alert( "POST was performed." );
			});
		} else {
			$("#privilegeSettingsErrorMsg").html("Please select proper Acesses Privilege");
		}
		showUnBlockUI();
		$.ajaxSetup({async:true});
	}catch(ex){
		alert(ex);
	}
}

function onPrivilegeSettingsCancelClick(){
	//alert("PrivilegeSettingsCancel..........................................................PrivilegeSettingsCancel");
}