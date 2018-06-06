// fiunctions for default values in text fields
function onBlur(el) {
    if (el.value == '') {
        el.value = el.defaultValue;
    }
}
function onFocus(el) {
    if (el.value == el.defaultValue) {
        el.value = '';
    }
}


  

 // for Upcomint Appointment
function getStyleUpcomintAppointment()
   {
      var temp = document.getElementById("UpcomintAppointment").style.display;
  
      return temp;
   }

 function switchMainUpcomintAppointment()
  {

      var current = getStyleUpcomintAppointment();

      if( current == "none" )
       {
           document.getElementById("UpcomintAppointment").style.display = "block";
           document.getElementById("expnClsp1").src = "static/images/minus.png";
       }
       else
       {
         document.getElementById("UpcomintAppointment").style.display = "none";
        document.getElementById("expnClsp1").src = "static/images/plus.png";
       }
}  

// for CustmerInfo
function getStyleCustomerInfo()
   {
      var temp = document.getElementById("CustomerInfo").style.display;
  
      return temp;
   }

 function switchMainCustomerInfo()
  {

      var current = getStyleCustomerInfo();

      if( current == "none" )
       {
         document.getElementById("CustomerInfo").style.display = "block";
           document.getElementById("expnClsp2").src = "static/images/minus.png";
       }
       else
       {
         document.getElementById("CustomerInfo").style.display = "none";
        document.getElementById("expnClsp2").src = "static/images/plus.png";
       }
} 

//tabs

// for Past Appointment
function getStylePastAppointment()
   {
      var temp = document.getElementById("PastAppointment").style.display;
  
      return temp;
   }

 function switchMainPastAppointment()
  {

      var current = getStylePastAppointment();

      if( current == "none" )
       {
         document.getElementById("PastAppointment").style.display = "block";
           document.getElementById("expnClsp3").src = "static/images/minus.png";
       }
       else
       {
         document.getElementById("PastAppointment").style.display = "none";
        document.getElementById("expnClsp3").src = "static/images/plus.png";
       }
}  

//IPAD number fields Auto tab
function autotab(current,to) {		
	//alert("Auto Tab");
	if (current.getAttribute && current.value.length>=current.getAttribute("maxlength")) {   		
		if(document.contains(to)){
			to.focus();
		}
	}
}
