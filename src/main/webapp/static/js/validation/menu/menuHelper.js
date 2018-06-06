 /*
	$(document).ajaxStart($.blockUI({ 
		message: '<div style="align:center"><h1><img src="static/images/loading-circle.gif" /> Just a moment...</h1></div>', 
		css: { 
			width: '350px', 
			height: '100px', 
			top: '250px', 
			left: '40%', 
			right: '10px', 
			border: '1', 
			padding: '10px', 
			backgroundColor: 'powderblue', 
			'-webkit-border-radius': '10px', 
			'-moz-border-radius': '10px', 
			opacity: .6, 
			color: 'red' 
		} 
		})).ajaxStop($.unblockUI);
 */
 function showBlockUI(){
	 $.blockUI({ 
		message: '<div style="align:center"><img src="static/images/spin.gif"/></div>'
	}); 
 }
 
  function showUnBlockUI(){
	  $.unblockUI();
  }
//Page Loading
function loadPageData(url,page){
    //alert("Hello Load Data : "+page); 
    showBlockUI();
	//setTimeout($.unblockUI, 10000); 		
    $( "#main" ).load(url, function( response, status, xhr ) {
	  //alert("response :::: "+response);
	  if ( status == "error" ) {
		var msg = "Sorry but there was an error: ";
		$( "#errorMsgDivId" ).html( msg + xhr.status + " " + xhr.statusText );
	  }
	 showUnBlockUI();
   });
}

function clearMessages(clearMsgs){
   if(clearMsgs){
	  $("#sucessesMsgDivId" ).html("");
	  $("#errorMsgDivId").html("");
   }
} 

//Location Page Loading
function loadLocationPage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("locations.html","Locations");
} 

//Service Page Loading
function loadServicePage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("services.html","Services");
} 

//Resource Page Loading
function loadResourcePage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("resources.html","Resources");
} 

//Users Page Loading
function loadUsersPage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("users.html","Users");
} 

//Call Page Loading
function loadCallReportPage(clearMsgs,callReportType){
   clearMessages(clearMsgs);
   loadPageData("call-report.html?callReportType="+callReportType,"Call Report");
} 

//Search Page Loading
function loadSearchPage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("search.html","Search");
} 

function loadSearchPageWithAutoSelect(clearMsgs,searchType){
   clearMessages(clearMsgs);
   loadPageData("search.html?searchType="+searchType,"Search");
} 

//Reports Page Loading
function loadReportsPage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("reports.html","Reports");
} 

function loadReportsPageWithAutoSelect(clearMsgs,reportType){
   clearMessages(clearMsgs);
   loadPageData("reports.html?reportType="+reportType,"Reports");
} 

//Table Print View Page Loading
function loadTablePrintView(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("table-print-view.html","Table Print View");
} 

//Calendar Page Loading
function loadCalendarPage(clearMsgs,obj){
   clearMessages(clearMsgs);
    $(".menuCal").attr('active','false');
    $(obj).attr('active',true);
   loadPageData("show-daily-calendar.html","Calendar Page");
}

 function loadMonthlyCalendar(clearMsgs,obj){
     clearMessages(clearMsgs);
     $(".menuCal").attr('active','false');
     $(obj).attr('active',true);
     loadPageData("monthly-calendar-view.html","Calendar Page");
 }



 //Resource Edit Hours Date Range Page Loading
function loadResourceEditHoursDataRangePage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("showResourceEditHoursDateRange.html","Resource Edit Hours Date Range Page");
} 

//Resource Edit Hours One Date Page Loading
function loadResourceEditHoursOneDataPage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("showResourceEditHoursOneDate.html","Resource Edit Hours One Date Page");
}

//User Activity Log Page Loading
function loadUserActivityLogPage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("userActivityLog.html","User Activity Log");
} 

//Privilege Settings Page Loading
function loadPrivilegeSettingsPage(clearMsgs){
   clearMessages(clearMsgs);
   loadPageData("showAcessesPrivilegePage.html","Privilege Settings");
} 