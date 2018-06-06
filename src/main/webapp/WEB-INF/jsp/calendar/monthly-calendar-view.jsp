<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Appointments &gt; Monthly Calander</title>
<script type="text/javascript" src="static/js/validation/calenderFormHelper.js"></script>
<script type="text/javascript" src="static/js/validation/ApptMonthlyCalenderFormValidation.js"></script>
<script type="text/javascript" src="static/js/validation/calendar/calendarHelper.js?version=1.0"></script>

    <script>
        $(document).ready(function () {
            $("input[name=resourceIds]").change(function(){
                resetCalendarWithAvailableDates();
                var resourceId = $(this).prop('value');
                if($(this).prop('checked')){
                    $(".resourceId_"+resourceId).show();
                } else {
                    var selectedResourceIds = $('input:checkbox[name=resourceIds]:checked').map( function () {
                        return $(this).val();
                    }).get().join();
                    if(selectedResourceIds!=""){
                        $(".resourceId_"+resourceId).hide();
                    } else {
                        $(this).prop('checked',true)
                    }
                }
            });
        });
</script>

</head>
<input type="hidden" id="calendarType" value="monthly">
<body>
<form:form action="search-monthly-calendar.html" method="POST" name="calendarForm" id="calendarForm"  modelAttribute="monthlyCalendarFieldsTO">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%"><!-- Main tables starts -->

        <div class="dailyCal monthlyCal">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
            <tbody>
              <tr>
                <th width="14%" class="center-align">Sun</th>
                <th width="14%" class="center-align">Mon</th>
                <th width="14%" class="center-align">Tue</th>
                <th width="14%" class="center-align">Wed</th>
                <th width="14%" class="center-align">Thu</th>
                <th width="14%" class="center-align">Fri</th>
                <th width="14%" class="center-align">Sat</th>
              </tr>
<%--
              <c:set var="cellCssMap" property="currentDate" value="current-date"/>
              <c:set target="${cellCssMap}" property="calDate" value="cal-date"/>
              <c:set target="${cellCssMap}" property="calDateDisabled" value="cal-date-dsbl"/>
              <c:set target="${cellCssMap}" property="calDateClosed" value=""/>
              <c:set target="${cellCssMap}" property="calDateClosed" value=""/>
--%>

              <c:forEach  items="${monthlyCalendar.monthlyCalendarDataRowsColumns}" var="monthlyCalendarRowData">
                 <tr>
                  <c:forEach items="${monthlyCalendarRowData}" var="monthlyCalendarCell">
                      <c:choose>
                      <c:when test="${monthlyCalendarCell.notAvailable}">
                      <c:set var="isCurrentDate" value="${monthlyCalendarCell.calendarDate == monthlyCalendar.currentDate}"></c:set>
                      <c:set var="anchorTagStyleClass" value="${monthlyCalendarCell.disabledField ? 'cal-date-dsbl' : 'cal-date'}"/>
                      <td bgcolor="#f2f2f2" height="100"><a class="${anchorTagStyleClass}" href="javascript:donothing();" onclick="getDailyCalendar('${monthlyCalendarCell.calendarDate}');"> ${monthlyCalendarCell.day } </a>
                          <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
                          <div class="mon-cal-info-no-dots">&nbsp; </div>
                      </td>
                      </c:when>
                          <c:otherwise>
                              <td bgcolor="#fff" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('${monthlyCalendarCell.calendarDate}');"> ${monthlyCalendarCell.day } </a>
                                <c:forEach var="resourceData" items="${monthlyCalendarCell.resourceDataList}">
                                    <c:choose>
                                        <c:when test="${monthlyCalendar.showAllResourceCount}">
                                            <div class="reservedDocColor${resourceData.colorId} mon-calcontainer">
                                                <div class="mon-cal-info" style="margin-left:27%;width: 90%">Total Booked: <span class="txt-bold">${resourceData.numberOfBookedAppts}</span></div>
                                            </div>
                                            <div class="reservedDocColor${resourceData.colorId} mon-calcontainer">
                                                <div class="mon-cal-info" style="margin-left:27%;width: 90%">Total Open: <span class="txt-bold">${resourceData.numberOfOpenSlots}</span></div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="reservedDocColor${resourceData.colorId} mon-calcontainer">
                                                <div class="mon-cal-info">Booked: <span class="txt-bold">${resourceData.numberOfBookedAppts}</span></div>
                                                <div class="mon-cal-info">Open: <span class="txt-bold">${resourceData.numberOfOpenSlots}</span></div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                              </td>
                          </c:otherwise>
                      </c:choose>
                  </c:forEach>
                 </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>

        <!-- Main tables ends --></td>
    </tr>
  </table>
  <div class="tips-box appt"></div>
  <div class="tips-box-text">Open</div>
  <div class="tips-box new-patient"></div>
  <div class="tips-box-text">In Progress</div>
  <div class="tips-box avail"></div>
  <div class="tips-box-text">Booked</div>
  <div class="tips-box nowrk"></div>
  <div class="tips-box-text">No Time Slots Available</div>
  <div class="tips-box other"></div>
  <div class="tips-box-text">Others</div>

  <!-- Main tables ends      -->

  <div class="clear-all"></div>
</form:form>
<!-- Body ends -->

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2">Please wait...</div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<!--Pop up section ends -->
<script>
    $(document).ready(function(){
        try {
            resetCalendarWithAvailableDates();
            $(".bookedResources").each(function (index, value) {
                if ($(this).attr("cust_id") != 0) {
                    $(this).addClass("reservedDocColor" + $("#" + $(this).attr('resource_id')).attr("resource-index"));
                }
            });
        } catch (excep) {
            alert(excep);
        }
    });
</script>
</body>
</html>
