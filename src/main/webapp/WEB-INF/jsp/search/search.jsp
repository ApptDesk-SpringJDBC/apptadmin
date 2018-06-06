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
<title>Search</title>
<script type='text/javascript' src="static/js/validation/search/searchFormValidation.js?version=5.2"></script>
</head>

<body>
   <h1>Search</h1>

	<div class="options">
		View Details
		<select id="viewDetails" name="viewDetails" style="width:200px">
				<!--
				<option value="APPOINTENT_DETAILS"> Appointent Details </option>
				<option value="CUSTOMER_DETAILS"> Customer Details </option>
				<option value="CUSTOMER_ACTIVITY_DETAILS"> Customer Activity Details </option>
				<option value="HOUSEHOLD_INFO_DETAILS"> Household Info Details </option>
				<option value="PLEDGE_DETAILS"> Pledge Details </option> 
				-->
				<c:forEach var="dynamicSearchByField" items="${searchDropDownList.dynamicSearchByFields}">
					<c:set var="title" value="${dynamicSearchByField.title}"/>
					<c:set var="title_with_undersore" value="${fn:replace(title,' ', '_')}" />
					<c:set var="title_with_undersore_upper" value="${fn:toUpperCase(title_with_undersore)}" />
					<option value='${title_with_undersore_upper}' ${searchType==title_with_undersore_upper ? 'selected=selected' : ''}> ${title}</option>
				 </c:forEach>
										 
		</select>
    </div>
	 
	  <input id="searchSelectionType" type="hidden" value="FIRSTNAME_LASTNAME"/>
      <!-- Tab 1 starts -->
      <div id="tab1" class="new_tab_content"> 
        <!--Options starts -->
        <div class="options"> 
		  First Name : &nbsp; <input  name="firstName" id="firstName"/>
          Last Name : &nbsp; <input  name="lastName" id="lastName"/>&nbsp;&nbsp;
          <input  id="firstNameLastNameSearchBtn" type="button" class="btn" value="Search">	
		  <span id="firstNameLastNameSearchErrorDiv" class="error"></span>
		  <div class="clear-all"></div><br/>
		  <div align="left" >
			 &nbsp; &nbsp; &nbsp; &nbsp; <b>Or Search By</b>
		  </div>
		  <div class="clear-all"></div><br/>
		  <select id="otherSearch" onchange="updateSearchSelectionType(this);"  style="width:200px">
				<option value="CONFIRMATION_NO"> Confirmation #</option>
				<option value="SSN"> SSN </option>
				<option value="SSN_LAST_7"> SSN Last 7 </option>
				<option value="CONTACT_PHONE"> Contact Phone </option>
				<option value="CALLER_ID"> Caller ID </option>
				<option value="ENERGY_ACCT_NO"> Energy Acct # </option>
				<option value="DOB"> DOB (MM/DD/YYYY) </option>
				<option value="HOUSE_HOLD"> Household </option>
		   </select>
		   <input  name="searchData" id="searchData" placeholder="Exact Match"/>
		   <input  name="dobSearch" id="dobSearch" style="display:none;" placeholder="MM/DD/YYYY"/>
		   <input  id="otherSearchBtn" type="button" class="btn" value="Search">
		   <span id="otherSearchErrorDiv" class="error"></span>		   
        </div>
        <!--Options ends -->  

		<div class="clear-all"></div><br/>
       	<!-- Main tables starts -->		
		 <div id="searchResponseTableDiv" style="display:none;">
			<table id="searchResponseTable" class="display nowrap" cellspacing="0" width="100%">
				<thead>
					<tr>
					    <th>View Details</th>
						<th>Household Id</th>
						<th>SSN</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Contact Phone</th>
						<th>Energy Acct #</th>
						<th>DOB</th>						
						<th>Address</th>
						<th>City</th>
						<th>State</th>
						<th>Zip</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>View Details</th>
						<th>Household Id</th>
						<th>SSN</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Contact Phone</th>
						<th>Energy Acct #</th>
						<th>DOB</th>						
						<th>Address</th>
						<th>City</th>
						<th>State</th>
						<th>Zip</th>
					</tr>
				</tfoot>
			</table>
		</div>
		<!-- Main tables ends -->
      </div>
      <!-- Tab 1 ends --> 
	  
    <div class="clear-all"></div>


<!-- Body ends -->

<div class="jqmWindow" id="ex2">Please wait...</div>
<div class="jqmWindow" id="ex3">Please wait...</div>

</body>
</html>