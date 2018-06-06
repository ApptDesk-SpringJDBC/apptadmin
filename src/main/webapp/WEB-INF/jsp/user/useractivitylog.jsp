<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User Activity Log</title>
		<script type='text/javascript' src="static/js/validation/user/userActivityLogFormValidation.js?version=1.6"></script>
	</head>

	<body>
	   <h1>User Activity Log</h1>
	<div id="apptReportDetails" class="reportsClass">
	
		  <div class="search-bar">
			<div id="startDateError" style="font-weight: bold;" class="error"></div>
			<div id="endDateError" style="font-weight: bold;" class="error"></div>
			
		   <form id="userActivityLogForm">
			<dl>			
				<dt>
					<span class="required">*</span>Start Date:
				</dt>
				<dd>
					<input name="startDate" id="startDate"  value="${startDate}"/>					
				</dd>
			</dl>
			<dl>
				<dt>
					<span class="required">*</span>End Date:
				</dt>
				<dd>
					<input name="endDate" id="endDate" value="${endDate}"/>					
				</dd>
			</dl> 
			<div class="float-right">
				<input id="userActivityLogSearchBtn" type="reset" class="btn" value=" Search ">
			</div>
			<div class="clear-all"></div>
		  </form>
		  </div>
		  
		  <div class="clear-all"></div><br/>
			<!-- Main tables starts -->		
		 <div id="uaserActivityLogTableDiv" style="display:none;">
			<table id="uaserActivityLogTable" class="display nowrap" cellspacing="0" width="100%">
				<thead>
					<tr>
					    <th>User Name</th>
						<th>First Name</th>
						<th>Timestamp</th>
						<th>Session Id</th>
						<th>Page Name</th>
						<th>Click Name</th>
						<th>Log</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>User Name</th>
						<th>First Name</th>
						<th>Timestamp</th>
						<th>Session Id</th>
						<th>Page Name</th>
						<th>Click Name</th>
						<th>Log</th>
					</tr>
				</tfoot>
			</table>
		</div>
		<!-- Main tables ends -->
		  </div>
		<div class="clear-all"></div>		  
		</div>
	</body>
</html>