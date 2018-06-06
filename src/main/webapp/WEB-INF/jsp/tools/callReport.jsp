<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<style>
	td.details-control {
		background: url('static/images/details_open.png') no-repeat center center;
		cursor: pointer;
	}
	tr.shown td.details-control {
		background: url('static/images/details_close.png') no-repeat center center;
	}
	
	tfoot input {
        width: 100%;
        padding: 3px;
        box-sizing: border-box;
    }
</style>

<meta charset="UTF-8">
<title>Call Report</title>
<script type='text/javascript' src="static/js/validation/tools/callReportFormValidation.js?version=1.0"></script>
</head>

<body>
<input type="hidden"  value="inbound"  id="reportType" />

    <h1>Call Report </h1>

	<div class="error" id="fromDateError"></div>	
	<div class="error" id="toDateError"></div>	

	<div class="new_ultab">
      <ul class="new_tabs">
        <li><a href="javascript:donothing()" id="InBound"  reportType="inbound" >InBound Calls</a></li>
        <li><a href="javascript:donothing()" id="OutBound" reportType="outbound">OutBound Calls</a></li>
      </ul>
    </div>

      <!-- Tab 1 starts -->
      <div id="tab1" class="new_tab_content"> 
        <!--Options starts -->
        <div class="options txt-bold"> 
		  Periods From : &nbsp; <input  name="inBoundPeriodFrom" id="inBoundPeriodFrom"/>
          To : &nbsp; <input  name="inBoundPeriodTo" id="inBoundPeriodTo"/>&nbsp;&nbsp;
          <input  id="inBoundGo" type="button" class="btn" value="Search">		
		  <div align="right" >
				<b>InBound Calls  total no.of minutes : <div id="inBoundCallsMins"></div></b>
		  </div>
		   <div align="left" >
				<b>Click TransID link to view detailed foot print.</b>
		  </div>		  
        </div>
        <!--Options ends -->   
		<div class="clear-all"></div><br/>
       	<!-- Main tables starts -->		
		 <div id="InBoundCallReportDiv" style="display:none;">
			<table id="InBoundCallReport" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Trans Details</th>
						<th>Trans ID</th>
						<th>Confirm no</th>
						<th>Call Timestamp</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Caller ID</th>
						<th>Home Phone</th>
						<th>Appt Type</th>						
						<th>${displayNames.locationName}</th>
						<th>${displayNames.resourceName}</th>
						<th>Call Duration(Mins)</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>Trans Details</th>
						<th>Trans ID</th>
						<th>Confirm no</th>
						<th>Call Timestamp</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Caller ID</th>
						<th>Home Phone</th>
						<th>Appt Type</th>						
						<th>${displayNames.locationName}</th>
						<th>${displayNames.resourceName}</th>
						<th>Call Duration(Mins)</th>
					</tr>
				</tfoot>
			</table>
		</div>
		<!-- Main tables ends -->

      </div>
      <!-- Tab 1 ends --> 

      <!-- Tab 2 starts -->
      <div id="tab2" class="new_tab_content"> 
        <!--Options starts -->
        <div class="options txt-bold"> Periods From : &nbsp;
          <input  path="outBoundPeriodFrom" id="outBoundPeriodFrom"/>
          To : &nbsp;
          <input  path="outBoundPeriodTo" id="outBoundPeriodTo"/>  &nbsp; &nbsp;
          <input  id="outBoundGo" type="button" class="btn" value="Search">		
		  <div align="right">
				<b>OutBound Calls  total no.of  minutes : <div id="OutBoundCallMins"></div></b>
		  </div>
        </div>
        <!--Options ends --> 
        <div class="clear-all"></div><br/>
		<!-- Main tables starts -->	
		 <div id="OutBoundCallReportDiv"  style="display:none;">
			<table id="OutBoundCallReport" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Trans ID</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Appt Date Time</th>
						<th>Call Time</th>
						<th>Attempt</th>
						<th>Dailed Phone</th>
						<th>Status</th>						
						<th>${displayNames.locationName}</th>
						<th>${displayNames.resourceName}</th>
						<th>Minutes</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>Trans ID</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Appt Date Time</th>
						<th>Call Time</th>
						<th>Attempt</th>
						<th>Dailed Phone</th>
						<th>Status</th>						
						<th>${displayNames.locationName}</th>
						<th>${displayNames.resourceName}</th>
						<th>Minutes</th>
					</tr>
				</tfoot>
			</table>
		</div>
		<!-- Main tables ends -->      
      </div>
      <!-- Tab 2 ends --> 

     
    <div class="clear-all"></div>


<!-- Body ends -->

<div class="jqmWindow" id="ex3">Please wait...</div>

</body>
</html>