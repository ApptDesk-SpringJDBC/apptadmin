$(document).ready(function() {  
	$(document).ajaxError(function (e, xhr, settings) {
		if (xhr.status == 1000) { //Meaning session expired
		  window.location="session-expired.html";
	    }
	});
});


function scrollToTopOfThePage(){
   $('html, body').animate({ scrollTop: 0 }, 'slow', function () {
		
   });
}
   
function validateNonMandatoryDateField(fieldId,errorDivId,messageField){
	try{
		var inputvalue = $('#'+fieldId).val();
		if(inputvalue!='')  {
			inputvalue = inputvalue.replace("/","-");
			inputvalue = inputvalue.replace("/","-");
			var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
			var sucess= pattern.test(inputvalue);
			if(sucess){
				$('#'+errorDivId).text("");
				return true;
			}else{
				$('#'+errorDivId).html(messageField +"  should be in MM/dd/yyyy format");
				return false;
			}
		} else {
			$('#'+errorDivId).text("");
			return true;
		}
	}catch(e){
		//alert("Exception  : "+e);
		return false;
	}
}

function populateFieldData(fieldId,splitCount){
	var fieldData = "";
	if($('[name="'+fieldId+'1"]').attr('type')=="text"){
		for(var i=1;i<=splitCount;i++){
			var data = $("#"+fieldId+""+i).val();
			if(data!=null && data!="" && data!=undefined){
				fieldData = fieldData + data;
			}
		}
		$("#"+fieldId).val(fieldData);
	}
	return fieldData;
 }
 
  function clearPageFormData() {
	//$("#form").trigger('reset');
	  $(':input','#saveOrUpdateForm')
		 .not(':button, :submit, :reset, :hidden, :radio, :checkbox')
		 .val('')
		 .removeAttr('checked')
		 .removeAttr('selected');
 }
 
 function changeDivSelectedClass(serviceId) {
	$('div').removeClass('dragDivSelected');
	$("#changeDivClass"+serviceId).addClass("dragDiv dragDivSelected");
 }
 
 
 function loadLocationDetailsAsDropDown(selectFieldId,errorDisplayDivId,includeAllOptions){
	$.get("getActiveLocationDropDownData.html",function(data) {
		try{
			var json = $.parseJSON(data);
			if(json.status) {
				if(json.locationList.length>0) {
					var htmldata = "";
					if(includeAllOptions=='Y'){
						htmldata = htmldata + "<option value='-1'> ALL </option>";
					}					
					for(var i=0;i<json.locationList.length;i++){
						var locId = json.locationList[i].locationId;
						var locName = json.locationList[i].locationNameOnline;
						htmldata = htmldata + "<option value='"+locId+"'>"+locName+"</option>";
					}
					$("#"+selectFieldId).html(htmldata);
					$("#"+errorDisplayDivId).html("");
				}
			} else {
				$("#"+errorDisplayDivId).html("Error while fetching data!!!");
			}
		}catch(e){
			//alert("Error : "+e);
		}
	});
 }
 
 function loadServiceDetailsAsCheckbox(detailsToBeLoadDiv,errorDisplayDivId,defauldSelectedIds,checkBoxFieldName){
	$.get("getActiveServiceDropDownData.html",function(data) {
		try{
			var json = $.parseJSON(data);
			if(json.status) {
				if(json.serviceList.length>0) {
					var htmldata = "";
					for(var i=0;i<json.serviceList.length;i++){
						var serId = json.serviceList[i].serviceId;
						var serName = json.serviceList[i].serviceNameOnline;
						var checked = "";
						if(defauldSelectedIds!="" && defauldSelectedIds!=null && defauldSelectedIds!=undefined){
							if(defauldSelectedIds.indexOf(serId)>=0){
								checked = "checked";
							}
						} 
						htmldata = htmldata + "<input name=\""+checkBoxFieldName+"\" "+checked;
						htmldata = htmldata + " class=\"noMinWidth\" type=\"checkbox\" value=\""+serId+"\">"+serName;
					}
					$("#"+detailsToBeLoadDiv).html(htmldata);
				}
			} else {
				$("#"+errorDisplayDivId).html("Error while fetching data!!!");
			}
		}catch(e){
			//alert("Error : "+e);
		}
	});
 }

