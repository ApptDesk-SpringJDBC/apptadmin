function getDayScheduleDetails(day,displayDay){
	var details =displayDay+" :";
	try{		
	
		if($("#"+day+"OpenClose").is(':checked')){
			details =  details +" Open ";

			var from_hr = $("#"+day+"StartTimeHr").val();
			var from_min = $("#"+day+"StartTimeMin").val();
			var from_AmPm = $("#"+day+"StartTimeAmPm").val();

			details = details + " Office Hours :  From  "+from_hr+":"+from_min+" "+from_AmPm;

			var to_hr = $("#"+day+"EndTimeHr").val();
			var to_min = $("#"+day+"EndTimeMin").val();
			var to_AmPm = $("#"+day+"EndTimeAmPm").val();
			
			details = details + "   To  "+to_hr+":"+to_min+" "+to_AmPm;

			if($("#"+day+"BreakTimeOpenClose").is(':checked')){

				var break_time_hr = $("#"+day+"BreakTimeHr").val();
				var break_time_min = $("#"+day+"BreakTimeMin").val();
				var break_time_AmPm = $("#"+day+"BreakTimeAmPm").val();

				var break_duration = $("#"+day+"BreakDuration").val();
				
				details = details + "  Break Time :  "+break_time_hr+" : "+break_time_min+" "+break_time_AmPm;
				
				//details = details + " <b>  Duration : </b> "+break_duration + "Mins";
				
				var hrs = parseInt(break_duration)/60;
				hrs = String(hrs).split(".")[0];
				var duration ="";
				if(parseInt(hrs)>0){
					duration = hrs + " hour";
				}
				if(parseInt(hrs)>1){
					duration = duration+"s";
				}			
				var mins = parseInt(break_duration)%60;
				if(mins > 0){
					duration = duration +" " + mins+ " mins";
				}else{
					if(duration ==""){
						duration = duration +" " + mins+ " mins";
					}
				}
				details = details + "   Duration :  "+duration;
				
			}else{
				details = details + "  No Break time  ";
			}

		}else{
			details = details + " Closed";
		}
	}catch(e){
		  alert("Error : "+e);
    }
	return details;
}

function validateScheduleSettings(){

		var  validationDetails = "";
		var valid = validateTimes("sunDay");
		if(valid != ""){
			validationDetails = " <b> Sunday : </b>"+valid+"<br/>";
		}
		var valid = validateTimes("monDay");
		if(valid != ""){
			validationDetails = validationDetails+" <b> Monday : </b>"+valid+"<br/>";
		}
		var valid = validateTimes("tuesDay");
		if(valid != ""){
			validationDetails = validationDetails+" <b> Tuesday : </b>"+valid+"<br/>";
		}
		var valid = validateTimes("wednesDay");
		if(valid != ""){
			validationDetails = validationDetails+" <b> Wednesday : </b>"+valid+"<br/>";
		}
		var valid = validateTimes("thursDay");
		if(valid != ""){
			validationDetails = validationDetails+" <b> Thursday : </b>"+valid+"<br/>";
		}
		var valid = validateTimes("friDay");
		if(valid != ""){
			validationDetails = validationDetails+" <b> Friday : </b>"+valid+"<br/>";
		}
		var valid = validateTimes("saturDay");
		if(valid != ""){
			validationDetails = validationDetails+" <b> Saturday : </b>"+valid+"<br/>";
		}
	return validationDetails;
}

  function validateTimes(day) {			
		if($("#"+day+"OpenClose").is(':checked')){	
			var d= new Date();

			var fromTime = $("#"+day+"StartTimeHr").val()+":"+$("#"+day+"StartTimeMin").val()+" "+$("#"+day+"StartTimeAmPm").val();
			var toTime = $("#"+day+"EndTimeHr").val()+":"+$("#"+day+"EndTimeMin").val()+" "+$("#"+day+"EndTimeAmPm").val();
			
			var fromTimeMills = new Date(d.getFullYear()+","+d.getMonth()+","+d.getDate()+" "+ fromTime).getTime();
			var toTimeMills = new Date(d.getFullYear()+","+d.getMonth()+","+d.getDate()+" "+ toTime).getTime();
			
			if (toTimeMills <= fromTimeMills) {
					//alert("Invalid To Time");				
					return "Invalid To Time ";
			}else{

				if($("#"+day+"BreakTimeOpenClose").is(':checked')){

					var breakTime = $("#"+day+"BreakTimeHr").val()+":"+$("#"+day+"BreakTimeMin").val()+" "+$("#"+day+"BreakTimeAmPm").val();
					var breakDuration = parseInt($("#"+day+"BreakDuration").val());
					
					var breakTimeDate = new Date(d.getFullYear()+","+d.getMonth()+","+d.getDate()+" "+ breakTime);
					var breakFromMills =breakTimeDate.getTime();
					
					if (breakFromMills <= fromTimeMills || breakFromMills >= toTimeMills) {
						//alert("Invalid Break Time");
						return " Invalid Break Time ";
					}else{
							breakTimeDate.setMinutes(breakTimeDate.getMinutes() +parseInt(breakDuration));
							var breakToMills =breakTimeDate.getTime();

						if (breakToMills <= fromTimeMills || breakToMills >= toTimeMills) {
							//alert("Invalid Break Duration");
							return " Invalid Break Duration ";
						}else{
							//alert(" Correct Break Time & Duration");
							return "";
						}
					}
				}else{
					//alert(" No Break Time!");
					return "";
				}
			}
		}else{
			//alert(" Day is Closed!");
			return "";
		}
	}



	/*
	function getDayScheduleDetails(day,displayDay){
	var details ="<b>" + displayDay+" </b> :";
	try{		
	
		if($("#"+day+"Open_true").is(':checked')){
			details =  details +" Open ";

			var from_hr = $("#"+day+"StartTimeHr").val();
			var from_min = $("#"+day+"StartTimeMin").val();
			var from_AmPm = $("#"+day+"StartTimeAmPm").val();

			details = details + " <b> Office Hours : </b> From  "+from_hr+":"+from_min+" "+from_AmPm;

			var to_hr = $("#"+day+"EndTimeHr").val();
			var to_min = $("#"+day+"EndTimeMin").val();
			var to_AmPm = $("#"+day+"EndTimeAmPm").val();
			
			details = details + "   To  "+to_hr+":"+to_min+" "+to_AmPm;

			if($("#"+day+"breaktime").is(':checked')){

				var break_time_hr = $("#"+day+"BreakTimeHr").val();
				var break_time_min = $("#"+day+"BreakTimeMin").val();
				var break_time_AmPm = $("#"+day+"BreakTimeAmPm").val();

				var break_duration = $("#"+day+"BreakDuration").val();
				
				details = details + " <b> Break Time : </b> "+break_time_hr+" : "+break_time_min+" "+break_time_AmPm;
				
				//details = details + " <b>  Duration : </b> "+break_duration + "Mins";
				
				var hrs = parseInt(break_duration)/60;
				hrs = String(hrs).split(".")[0];
				var duration ="";
				if(parseInt(hrs)>0){
					duration = hrs + " hour";
				}
				if(parseInt(hrs)>1){
					duration = duration+"s";
				}			
				var mins = parseInt(break_duration)%60;
				if(mins > 0){
					duration = duration +" " + mins+ " mins";
				}else{
					if(duration ==""){
						duration = duration +" " + mins+ " mins";
					}
				}
				details = details + " <b>  Duration : </b> "+duration;
				
			}else{
				details = details + " <b> No Break time  </b>";
			}

		}else{
			details = details + " Closed";
		}
	}catch(e){
		  alert("Error : "+e);
    }
	return details;
}
*/
