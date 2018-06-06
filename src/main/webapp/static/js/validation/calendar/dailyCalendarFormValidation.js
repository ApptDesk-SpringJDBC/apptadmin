$(document).ready(function () {

    $("#inlineCalendar").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'mm/dd/yy',
        altField: "#jsCalenderDate",
        altFormat: "mm/dd/yy",
        onSelect: jsCalenderDateSelected,
        //beforeShowDay: function(date){	},
        //beforeShow: beforeShowCal
        //minDate:setStartMonth
        //beforeShow:setStartMonth
        onChangeMonthYear: function (year, month, inst) {
            resetCalendarWithAvailableDates(month + '/' + '01' + '/' + year);
        }
    });

    // get the active element from menu and set calander type and load content
    if ($(".menuCal[active='true']").attr('type') != undefined) {
        $("#calendarType").val($(".menuCal[active='true']").attr('type'));
        var map = {"daily": "dailyCalender", "weekly": "weeklyCalender", "monthly": "monthlyCalender"};
        // remove active for all types of calender tab
        $.map(map, function (value, index) {
            $("#" + value).removeClass("active");
        })
        $("#" + map[$("#calendarType").val()]).addClass('active');
    } else {
        $("#calendarType").val('daily');
        $("#dailyCalender").addClass('active');
    }
    //$("#calendarType").val('daily');
    //$("#jsCalenderDate").datepicker( "option", "minDate", getMin_Date());
    //$("#jsCalenderDate").datepicker( "option", "maxDate", getMax_Date());
    //$("#jsCalenderDate").datepicker( "option", "defaultDate", getDefault_Date());

    loadResourceServiceListData(true);

    $("#locationId").change(function () {
        //loadResourceCalendarData("location");
        resetCalenderStyle();
        loadResourceServiceListData(true);
    });

    // populate date when page loaded
    populateDateString();

    $('.next-day').on("click", function () {
        if ($("#calendarType").val() == 'daily') {
            var date = $('#inlineCalendar').datepicker('getDate');
            date.setTime(date.getTime() + (1000 * 60 * 60 * 24))
            $('#inlineCalendar').datepicker("setDate", date);
            jsCalenderDateSelected();
        }else if($("#calendarType").val() == 'monthly'){
            ChangeMonth(1);
            jsCalenderDateSelected();
        } else {
            var date = new Date($("#dayNavigation").attr("last-day"));
            date.setTime(date.getTime() + ((1000 * 60 * 60 * 24)));
            $('#inlineCalendar').datepicker("setDate", date);
            jsCalenderDateSelected();
        }

    });

    function ChangeMonth(month)
    {
        var initialVal = $('#inlineCalendar').datepicker('getDate');
        var curMonth = initialVal.getMonth();

        if(month > 0 && curMonth < 12){
            initialVal.setMonth(curMonth + 1)
        }
        if(month < 0 && curMonth > 1) {
            initialVal.setMonth(curMonth - 1)
        }
        $('#inlineCalendar').datepicker('setDate', initialVal);
    }

    $('.prev-day').on("click", function () {
        if ($("#calendarType").val() == 'daily') {
            var date = $('#inlineCalendar').datepicker('getDate');
            date.setTime(date.getTime() - (1000 * 60 * 60 * 24))
            $('#inlineCalendar').datepicker("setDate", date);
            jsCalenderDateSelected();
        }else if($("#calendarType").val() == 'monthly'){
            ChangeMonth(-1);
            jsCalenderDateSelected();
        }else {
            var date = new Date($("#dayNavigation").attr("first-day"));
            date.setTime(date.getTime() - ((1000 * 60 * 60 * 24) - 6));
            $('#inlineCalendar').datepicker("setDate", date);
            jsCalenderDateSelected();
        }

    });

});

function populateDateString() {
    var monthName = $.datepicker.formatDate('MM', $("#inlineCalendar").datepicker('getDate'));
    var year = $.datepicker.formatDate('yy', $("#inlineCalendar").datepicker('getDate'));
    if ($("#calendarType").val() == 'daily') {
        var weekName = $.datepicker.formatDate('DD', $("#inlineCalendar").datepicker('getDate'));
        var date = $.datepicker.formatDate('dd', $("#inlineCalendar").datepicker('getDate'));
        $("#dayNavigation").html(weekName + ", " + monthName + " " + date + ", " + year);
    }else if ($("#calendarType").val() == 'monthly') {
        var weekName = $.datepicker.formatDate('DD', $("#inlineCalendar").datepicker('getDate'));
        var date = $.datepicker.formatDate('dd', $("#inlineCalendar").datepicker('getDate'));
        $("#dayNavigation").html(monthName +", " + year);
    }else {
        var curr = $("#inlineCalendar").datepicker('getDate');	// get current date
        var first = curr.getDate() - curr.getDay(); // First day is the day of the month - the day of the week
        var last = first + 6; // last day is the first day + 6
        firstday = new Date(curr.setDate(first));
        lastday = new Date(curr.setDate(curr.getDate() + 6));
        $("#dayNavigation").attr('first-day', firstday);
        $("#dayNavigation").attr('last-day', lastday);
        if (firstday.getMonth() == lastday.getMonth()) {
            $("#dayNavigation").html(monthName + " " + firstday.getDate() + " - " + lastday.getDate() + ", " + lastday.getFullYear());
        } else {
            var startMonthName = $.datepicker.formatDate('MM', firstday);
            var lastMonthName = $.datepicker.formatDate('MM', lastday);
            $("#dayNavigation").html(startMonthName + " " + firstday.getDate() + " - " + lastMonthName + " " + lastday.getDate() + ", " + lastday.getFullYear());
        }
    }

}

function onResourceServiceChange() {
    loadResourceCalendarData("Service_Field");
}

function reloadOnHoldAppointment() {
    loadResourceCalendarData("hold_appt");
}

function loadResourceServiceListData(isReloadRightResourceSelection) {
    var calendarType = $("#calendarType").val();
    var selectedResourceIds = $('input:checkbox[name=resourceIds]:checked').map(function () {
        return $(this).val();
    }).get().join();
    var url = "calendar-resource-service-list.html?locationId=" + ($("#locationId").val()) + "&calendarType=" + calendarType;
    if (isReloadRightResourceSelection != undefined && isReloadRightResourceSelection == true) {
        $("#resourceServiceList").load(url, function (response, status, xhr) {
            resetCalendarWithAvailableDates();
            loadResourceCalendarData("Resource_Field");
        });
    } else {
        loadResourceCalendarData("Resource_Field");
    }
}

function loadCalendarData(resourceIds, selectedResourceIds, selectedField, resourceId_ServiceId_Blocks_details) {
    var calendarType = $("#calendarType").val();
    if (calendarType == 'daily') {
        var url = "daily-calendar-view.html?calendarDate=" + ($("#jsCalenderDate").val()) + "&locationId=" + ($("#locationId").val()) + "&resourceIds=" + resourceIds + "&selectedResourceIds=" + selectedResourceIds + "&selectedField=" + selectedField + "&resourceId_ServiceId_Blocks_details=" + resourceId_ServiceId_Blocks_details;
        $("#dailyCal").load(url, '', function () {
            $('#calendarDataGrid').unblock();
        });
    } else if (calendarType == 'weekly') {
        var url = "weekly-calendar-view.html?calendarDate=" + ($("#jsCalenderDate").val()) + "&locationId=" + ($("#locationId").val()) + "&resourceIds=" + resourceIds + "&selectedResourceIds=" + selectedResourceIds + "&selectedField=" + selectedField + "&resourceId_ServiceId_Blocks_details=" + resourceId_ServiceId_Blocks_details;
        $("#weeklyCal").load(url, '', function () {
            $('#calendarDataGrid').unblock();
        });
    } else if (calendarType == 'monthly') {
        var url = "monthly-calendar-view.html?calendarDate=" + ($("#jsCalenderDate").val()) + "&locationId=" + ($("#locationId").val()) + "&resourceIds=" + resourceIds + "&selectedResourceIds=" + selectedResourceIds + "&selectedField=" + selectedField + "&resourceIdServiceIdBlocksdetails=" + resourceId_ServiceId_Blocks_details;
        var colorDoctorMap = $('input:checkbox[name=resourceIds]:checked').map(function () {
            return $(this).next().attr('id') + '_' + $(this).next().attr('resource-index');
        }).get().join();
        if($('input:checkbox[name=resourceIds]:checked').length == $('input:checkbox[name=resourceIds]').length){
            url = url + "&showAllResourceCount="+true;
        }
        url = url + "&colorDoctorMap="+colorDoctorMap;

        $("#monthlyCal").load(url, '', function () {
            $('#calendarDataGrid').unblock();
        });
    }
}
function loadResourceCalendarData(selectedField) {
    $('#calendarDataGrid').block({
        message: '<div style="align:center"><img src="static/images/spin.gif"/></div>'
    });
    var calendarType = $("#calendarType").val();
    var resourceIds;
    if (calendarType == 'daily') {
        var selectedResourceIds = $('input:checkbox[name=resourceIds]:checked').map(function () {
            return $(this).val();
        }).get().join();
        resourceIds = $('input:checkbox[name=resourceIds]').map(function () {
            return $(this).val();
        }).get().join();
    } else if (calendarType == 'weekly') {
        resourceIds = $('input:checkbox[name=resourceIds]:checked').val();
        selectedResourceIds = resourceIds;
    }else if (calendarType == 'monthly') {
        selectedResourceIds = $('input:checkbox[name=resourceIds]:checked').map(function () {
            return $(this).val();
        }).get().join();
        if(selectedResourceIds == '' || selectedResourceIds == 'undefined'){
            $('input:checkbox[name=resourceIds]').prop('checked', true);
            selectedResourceIds = $('input:checkbox[name=resourceIds]:checked').map(function () {
                return $(this).val();
            }).get().join();
        }
    }
    var resourceId_ServiceId_Blocks_details = "";
    if (resourceIds != "" && typeof resourceIds != 'undefined') {
        var resourceIdsArr = resourceIds.split(",");
        for (var i = 0; i < resourceIdsArr.length; i++) {
            var resourceId = resourceIdsArr[i];
            var serviceId_Blocks = $("#resource_" + resourceId + "_serviceId").val();
            if (resourceId_ServiceId_Blocks_details != "") {
                resourceId_ServiceId_Blocks_details = resourceId_ServiceId_Blocks_details + ",";
            }
            resourceId_ServiceId_Blocks_details = resourceId_ServiceId_Blocks_details + resourceId + "SEP" + serviceId_Blocks;
        }
    }
    loadCalendarData(resourceIds, selectedResourceIds, selectedField, resourceId_ServiceId_Blocks_details);
}

function jsCalenderDateSelected() {
    resetCalenderStyle();
    loadResourceCalendarData("Date_Field");
    populateDateString();
}

function resetCalendarWithAvailableDates(date) {
    try {
        var selectedDate = date == undefined ? $("#jsCalenderDate").val() : date;
        var selectedResourceIds = $('input:checkbox[name=resourceIds]:checked').map(function () {
            return $(this).val();
        }).get().join();
        var serviceIdsArr = [];
        $("input[name=resourceIds]").each(function (index, value) {
            if ($(value).is(':checked')) {
                serviceIdsArr.push($("#resource_" + $(value).val() + "_serviceId").val().split("_")[0]);
            }
        });
        $.ajax({
            url: "./calendar-available-dates.json?calendarDate=" + selectedDate + "&locationId=" + $("#locationId").val()
            + "&resourceIdStr=" + selectedResourceIds + "&serviceIdStr=" + serviceIdsArr.toString(),
            success: function (response, data) {
                $("table.ui-datepicker-calendar td").each(function (index, element) {
                    var childElement = $(element).children();
                    if (childElement != undefined) {
                        var m = Number($(element).attr("data-month")) + 1;
                        m = m < 10 ? "0" + m : m;
                        var d = $(element).children().html();
                        d = d < 10 ? "0" + d : d;
                        var currDate = $(element).attr("data-year") + "-" + m + "-" + d;
                        var currDateStyle = response[currDate];
                        if (currDateStyle != undefined) {
                            $(childElement).removeClass(function (index, className) {
                                return (className.match(/(^|\s)cal-\S+/g) || []).join(' ');
                            });
                            $(childElement).addClass("cal-" + currDateStyle.availabilityStr);
                            $(childElement).removeClass("ui-state-default");
                            $(childElement).attr("title", currDateStyle.toolTipMessage);
                        }
                    }
                });
            }
        });
    } catch (e) {
        alert(e);
    }
}


function refreshCalendar() {
    var calendarType = $("#calendarType").val();
    if (calendarType === 'daily') {
        searchDailyCalender();
    } else if (calendarType === 'weekly') {
        searchWeeklyCalender();
    }else if(calendarType === 'monthly'){
        searchMonthlyCalender();
    }
}