<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Appointment Calendar</title>

<script type="text/javascript" src="static/js/validation/calendar/dailyCalendarFormValidation.js?version=1.0"></script>
<script type="text/javascript" src="static/js/alertify/alertify.min.js?version=1.0"></script>

<!-- inline demo CSS (combined + minified for performance; see comments for raw source URLs) -->
<link rel="stylesheet" type="text/css" href="demo/index-rollup.css"/>
<link rel="stylesheet" type="text/css" href="static/js/alertify/alertify.core.css"/>
<link rel="stylesheet" type="text/css" href="static/js/alertify/alertify.default.css"/>

<!-- the SoundManager 2 API -->
<script type="text/javascript" src="script/soundmanager2.js"></script>
 
<style type="text/css">
    .ui-widget-content, .JsDatePickBox {
        z-index: 9999 !important;
    }
    .ui-state-available .ui-state-default{
        background: #a4ed5c;
    }
</style>
</head>

<body>
<div style="display: none">
    <c:forEach var="message" items="${messagesMap}">
        <span id="${message.key}" message="${message.value}" style="display: none"/>
    </c:forEach>
</div>
<form id="calendarForm" name="calendarForm" action="search-daily-calendar.html" method="post">
  <div class="dailyCal_head">
    <div class="cal_head-new">Appointment > Calendar</div>
    <div class="cal_button float-left">
        <a href="javascript:donothing();" id="dailyCalender" onclick="searchDailyCalender(true);">Daily</a>
        <a href="javascript:donothing();" id="weeklyCalender" onclick="searchWeeklyCalender(true);">Weekly</a>
        <a href="javascript:donothing();" id="monthlyCalender" onclick="searchMonthlyCalender(true);">Monthly</a>
    </div>
    <div class="cal-location">
      <span class="txt-bold">Location: </span>
      <select id="locationId" name="locationId">
		 <c:forEach var="location" items="${locationList}">
			<option value='${location.locationId}'> ${location.locationNameOnline}</option>
		 </c:forEach>
      </select>
      </div>
      <div class="cal-location ui-widget-header ui-corner-all">
          <a title="Prev" style="float: left;" class="prev-day ui-datepicker-prev ui-corner-all">
              <span class="ui-icon ui-icon-circle-triangle-w">Prev</span>
          </a> &nbsp;&nbsp;
          <span id="dayNavigation" style="vertical-align: super;">new Date()</span>&nbsp;&nbsp;
          <a title="Next" style="float: right;" class="next-day ui-datepicker-next ui-corner-all">
              <span class="ui-icon ui-icon-circle-triangle-e">Next</span>
          </a>
      </div>
      <div id="cal-reschedule-appt-placeholder" style="display: none;padding-left: 3px;margin-top: -5px;">
          
      </div>
    
    <div class="float-right pTop10">
      <a href="javascript:" onclick="refreshCalendar()">Refresh</a>
    </div>
    <div style="clear:both"></div>
  </div>
  
  <!-- Main section starts -->
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="300px" style="vertical-align:top">
      
		<div id="calendarFull">
			<div id="inlineCalendar"></div>
			<input type="hidden" id="jsCalenderDate" value="">
		</div>
		<div class="clear-all"></div><br/>

		<div id="resourceServiceList"> </div>
		
      </td>

        <td width="100%" id="calendarDataGrid">
            <input type="hidden" id="isActiveHoldAppointment" value="false">

        <!-- Main tables starts -->
          <div class="dailyCal">
			<div class="float-left big-table"  id="dailyCal"></div>
			<div class="showSection right-table" id="showRightSideSection" style="display:none;"></div>
        </div> 
        <!-- Main tables ends -->

          <div class="weeklyCal">
              <div class="float-left big-table"  id="weeklyCal"></div>
              <div class="showSection right-table" id="showWeeklyRightSideSection" style="display:none;"></div>
          </div>

            <div class="monthlyCal">
              <div class="float-left big-table"  id="monthlyCal"></div>
              <div class="showSection right-table" id="showMonthlyRightSideSection" style="display:none;"></div>
          </div>
      </td>
    </tr>
  </table>
  <input type="hidden" id="calendarType" value="daily">
  <input type="hidden" id="apptTypeExistingNew">
  <!-- Main section ends -->
  
</form>
<!-- Body ends -->

<div class="jqmWindow" id="ex2">Please wait...</div>
<div class="jqmWindow" id="ex3"> Please wait... </div>

<script>
    function searchDailyCalender(isReloadRightResourceSelection){
        $("input[name='resourceIds']").removeAttr('disabled');
        $("#calendarType").val('daily');
        $("#weeklyCalender").removeClass('active');
        $("#monthlyCalender").removeClass('active');
        $("#dailyCalender").addClass('active');
        $("#weeklyCal").hide();
        $("#weeklyCal").html("");
        $("#dailyCal").show();
        resetCalenderStyle();
        loadResourceServiceListData(isReloadRightResourceSelection);
        populateDateString();
    }

    function searchWeeklyCalender(isReloadRightResourceSelection){
        $("input[name='resourceIds']").removeAttr('disabled');
        $("#calendarType").val('weekly');
        $("#dailyCalender").removeClass('active');
        $("#monthlyCalender").removeClass('active');
        $("#weeklyCalender").addClass('active');
        $("#dailyCal").hide();
        $("#dailyCal").html("");
        $("#weeklyCal").show();
        resetCalenderStyle();
        loadResourceServiceListData(isReloadRightResourceSelection);
        populateDateString();
    }

    function loadWeeklyCalendarPage(clearMsgs){
        clearMessages(clearMsgs);
        loadPageData("show-daily-calendar.html","Calendar Page");
    }

    function searchMonthlyCalender(isReloadRightResourceSelection){
        $("input[name='resourceIds']").removeAttr('disabled');
        $("#calendarType").val('monthly');
        $("#dailyCalender").removeClass('active');
        $("#weeklyCalender").removeClass('active');
        $("#monthlyCalender").addClass('active');
        $("#dailyCal").hide();
        $("#dailyCal").html("");
        $("#weeklyCal").hide();
        $("#weeklyCal").html('');
        $("#monthlyCal").show();
        resetCalenderStyle();
        loadResourceServiceListData(isReloadRightResourceSelection);
        populateDateString();
    }

    function weeklyResourceChange(selectedOption){
        $('input.selectedResourceIdsList').not(selectedOption).prop('checked', false);
        loadResourceCalendarData($(selectedOption).val());
    }

    function monthlyResourceChange(selectedOption){
        $('input.selectedResourceIdsList').not(selectedOption).prop('checked', false);
        loadResourceCalendarData($(selectedOption).val());
    }

    function donothing(){

    }

    function resetCalenderStyle(){
        $("#showRightSideSection").hide();
        $("#showRightSideSection").html("");
        $("#showWeeklyRightSideSection").hide();
        $("#showWeeklyRightSideSection").html("");
        $("#dailyCal, #weeklyCal #monthlyCal").attr("class","float-left big-table");
    }
</script>
</body>
</html>
