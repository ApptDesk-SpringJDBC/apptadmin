$(document).ready(function () {
    $("#accordion").accordion({
        heightStyle: "content"
    });
    $('.bookAppt').click(function () {
        $("input[name='resourceIds']").attr('disabled',true);
        $("#apptTypeExistingNew").val("new");
        if($("#showRightSideSection").is(":visible")){
            return false;
        }
        var thisObj = $(this);
        var data = $(this).attr('data'); //resourceId=2&time=09:00 AM&rescheduleId=0&isReschedule=false
        var calendarType = $("#calendarType").val();
        var scheduleId;
        if ($("#cal-reschedule-appt-placeholder").attr("rescheduleActive") === "active") {
            var reScheduleOldData = $("#cal-reschedule-appt-placeholder .bookedApptInfo");
            alertify.set({ labels: {
                ok     : "Yes",
                cancel : "No"
            } });
            // confirm dialog
            alertify.confirm($("#confirm_reschedule").attr("message"), function (e) {
                if (e) {
                    rescheduleAppointment(reScheduleOldData, data, calendarType);
                } else {
                    scheduleId = holdAppointment(data);
                    thisObj.addClass("holdAppt");
                    thisObj.text("appt hold");
                    if(scheduleId == null || scheduleId == undefined){
                        return false;
                    }
                    data = openRightsideSelection(calendarType, data, scheduleId);
                    $("#cal-reschedule-appt-placeholder").attr("rescheduleActive", "inactive");
                    $("#cal-reschedule-appt-placeholder").hide();
                    $("#cal-reschedule-appt-placeholder").html("");

                }
            });
            /*if(confirm("An appointment in placeholder.  Do you want to reschedule that appointment to the current time slot?")){

            }*/

        } else {
            scheduleId = holdAppointment(data);
            thisObj.addClass("holdAppt");
            thisObj.text("appt hold");
            if(scheduleId == null || scheduleId == undefined){
                return false;
            }
            data = openRightsideSelection(calendarType, data, scheduleId);
        }
    });
});



function onCancelApptClick(scheduleId){

}


function openRightsideSelection(calendarType, data, scheduleId) {
    try {
        data = data + "&date="+$("#jsCalenderDate").val();
        if (calendarType === 'daily') {
            var date = $("#jsCalenderDate").val();
            var time = data.split("&")[1].split("=")[1];
            var resourceId = data.split("&")[0].split("=")[1];
            var locationName = $("#locationId option:selected").text();
            $(".resources").hide();
            $("#showWeeklyRightSideSection").hide();
            $(".resourceId_" + resourceId).show();
            $("#showRightSideSection").show();
            data = data.replace(/\s/g, '');
            var url = "calendar-right-side-details-section.html?" + data;
            $("#showRightSideSection").load(url, function (response, status, xhr) {
                $("#dailyCal").attr("class", "float-left small-table");
                loadApptInfo(resourceId, date, time, locationName, calendarType);
                $("#bookApptScheduleId").val(scheduleId);
                $("#isHoldAppointment").val("yes");
            });
        } else if (calendarType === 'weekly') {
            var date = data.split("&")[4].split("=")[1];
            var time = data.split("&")[1].split("=")[1];
            var weeklyDate = date.split('/')[1];
            var locationName = $("#locationId option:selected").text();
            var locationId = $("#locationId").val();
            var resourceId = data.split("&")[0].split("=")[1];
            $(".resources").hide();
            $(".weeklyDate_" + weeklyDate).show();
            $("#showRightSideSection").hide();
            $("#showWeeklyRightSideSection").show();
            data = data.replace(/\s/g, '');
            var url = "calendar-right-side-details-section.html?" + data;
            $("#showWeeklyRightSideSection").load(url, function (response, status, xhr) {
                $("#weeklyCal").attr("class", "float-left small-table");
                loadApptInfo(resourceId, date, time, locationName, calendarType);
                $("#bookApptScheduleId").val(scheduleId);
                $("#isHoldAppointment").val("yes");
            });
        }
    } catch (e) {
        alert("Error : " + e);
    }
    return data;
}

var rescheduleAppointment = function (reScheduleOldData, data, calendarType) {
    var newAppointmentData = [];
    var oldScheduleId = reScheduleOldData.attr("scheduleId");
    var customerId = reScheduleOldData.attr("customerId");
    var resourceId = data.split("&")[0].split("=")[1];
    var serviceId = reScheduleOldData.attr("serviceId");
    var comments = reScheduleOldData.attr("comments");
    var locationId = $("#locationId").val();
    var locationName = $("#locationId option:selected").text();
    newAppointmentData.push({name: "oldScheduleId", value: oldScheduleId});
    newAppointmentData.push({name: "customerId", value:customerId});
    newAppointmentData.push({name: "locationId", value:locationId});
    newAppointmentData.push({name: "serviceId", value:serviceId});
    newAppointmentData.push({name: "comments", value:comments});
    newAppointmentData.push({name: "locationName", value: locationName});
    if (calendarType === 'daily') {
        var date = $("#jsCalenderDate").val();
        var time = data.split("&")[1].split("=")[1];
        var dateTime = date + " " + time;
        newAppointmentData.push({name: "apptDateTime", value: dateTime});
        newAppointmentData.push({name: "resourceId", value: resourceId});
        var resourceName = $("th.resourceId_" + resourceId).text();
        newAppointmentData.push({name: "resourceName", value: resourceName});
        $("#showWeeklyRightSideSection").hide();
        $("#showRightSideSection").html("");
        $("#dailyCal").attr("class", "float-left small-table");
        $(".resources").hide();
        $(".resourceId_" + resourceId).show();
        var template = getConfirmRescheduleTemplate(reScheduleOldData);
        var newApptTemplate = getConfirmRescheduleNewTimeslotTemplate(newAppointmentData, reScheduleOldData);
        $("#showRightSideSection").append($(template).html());
        $("#showRightSideSection").append($(newApptTemplate).html());
        $("#showRightSideSection").show();
    } else if (calendarType === 'weekly') {
        var date = data.split("&")[4].split("=")[1];
        var time = data.split("&")[1].split("=")[1];
        var weeklyDate = date.split('/')[1];
        var dateTime = date + " " + time;
        newAppointmentData.push({name: "apptDateTime", value: dateTime});
        newAppointmentData.push({name: "resourceId", value: resourceId});
        var resourceName = $('input[name=resourceIds]:checked', '#resourceServiceList').next().text();
        newAppointmentData.push({name: "resourceName", value: resourceName});
        $("#showRightSideSection").hide();
        $("#showWeeklyRightSideSection").html("");
        $("#weeklyCal").attr("class", "float-left small-table");
        $(".resources").hide();
        $(".weeklyDate_" + weeklyDate).show();
        var template = getConfirmRescheduleTemplate(reScheduleOldData);
        var newApptTemplate = getConfirmRescheduleNewTimeslotTemplate(newAppointmentData, reScheduleOldData);
        $("#showWeeklyRightSideSection").append($(template).html());
        $("#showWeeklyRightSideSection").append($(newApptTemplate).html());
        $("#showWeeklyRightSideSection").show();
    }
}
function getConfirmRescheduleTemplate(apptData){
    var template = "<div>" +
        "<h2 style='border: 2px black solid; display: inline-block;border-radius: 6px; background-color: yellow;padding-right: 8px;padding-left: 8px;padding-top: 5px;'>" +
        "Exiting Appointment Details</h2>" +
        "<div></div><label><b>Customer Name: </b>"+ apptData.attr('firstName') + " " + apptData.attr('lastName') + "</label><br>" +
        "<label><b>Appt Date & Time: </b>"+ apptData.attr('apptdatetime') + "</label><br>" +
        "<label><b>Location: </b>"+ apptData.attr('locationName') + "</label><br>" +
        "<label><b>Intake: </b>"+ apptData.attr('resourceName')  + "</label><br>";
        "<label><b>Service: </b>"+ apptData.attr("serviceName")  + "</label><br>";
        "<label><b>Comments: </b>"+ apptData.attr("comments")  + "</label><br><br><br>";
    return template;
}
var rescheduleData = "";
function getConfirmRescheduleNewTimeslotTemplate(apptData, reScheduleOldData){
    rescheduleData = apptData;
    var template = "<div>" +
        "<h2 style='border: 2px black solid;display: inline-block; border-radius: 6px; background-color: #8edb91; padding-right: 8px; padding-left: 8px; padding-top: 7px;'>New Appointment Details</h2>" +
        "<div></div><label><b>Customer Name: </b>"+ reScheduleOldData.attr('firstName') + " " + reScheduleOldData.attr('lastName') + "</label><br>" +
        "<label><b>New Appt Date & Time: </b>"+ apptData.find(obj => obj.name === 'apptDateTime').value + "</label><br>" +
        "<label><b>Location: </b>"+ apptData.find(obj => obj.name === 'locationName').value + "</label><br>" +
        "<label><b>Intake: </b>"+ apptData.find(obj => obj.name === 'resourceName').value  + "</label><br>" +
        "<label><b>Service Name: </b>"+ reScheduleOldData.attr('serviceName') + "</label><br>" +
        "<label><b>Comments: </b>"+ reScheduleOldData.attr('comments') + "</label><br>" +
    "<br><br>" +"<input type='button' value='cancel' class='btn noMinWidth' onclick='cancelSchedule()'>" + "&nbsp &nbsp"+
        "<input type='button' value='Reschedule' class='btn noMinWidth' onclick='confirmRescheduled()'>";
    return template;
}

function confirmRescheduled(){
    $.post("rescheduleAppointment.json", rescheduleData,
        function (response) {
            //console.log(response);
            if(!response.status){
                alert("Failed to reschedule.  Reason: " + response.errorMessage);
            }
            $("#cal-reschedule-appt-placeholder").attr("rescheduleActive", "inactive");
            $("#cal-reschedule-appt-placeholder").hide();
            $("#cal-reschedule-appt-placeholder").html("");
            var calendarType = $("#calendarType").val();
            if (calendarType === 'daily') {
                searchDailyCalender();
            } else if (calendarType === 'weekly') {
                searchWeeklyCalender();
            }
            rescheduleData = "";
        }
    );
}

function cancelSchedule(){
    var calendarType = $("#calendarType").val();
    if(calendarType === 'daily'){
        searchDailyCalender();
    } else if (calendarType === 'weekly') {
        searchWeeklyCalender();
    }
};

function loadApptInfo(resourceId, date, time, locationName, calendarType) {
    $("#resourceId").val(resourceId);
    $("#bookApptLocationId").val($("#locationId").val());
    $("#bookApptLocationName").text(locationName);

/*    var timeWithAMPM = time + Number(time.split(":")[0]) > 6 ? ' AM' : ' PM';
    $("#appointmentTime").text(date + " " + timeWithAMPM);*/

    //$("#intake").text($("th.resourceId_"+resourceId).text());
    $.ajax({
        type: "GET",
        url: "holdAppointmentResourceList.json?locationId=" + $("#locationId").val() + "&resourceId=" + resourceId + "&serviceId=1",
        dataType: "json",
        success: function (data) {
            var serviceOptions = "";
            $.each(data, function (index, value) {
                var duration = (value.duration != null) ? '-' + value.duration : ''
                serviceOptions += '<option value="' + value.serviceId + '">' + value.serviceNameOnline + duration + '</option>';
            });
            $("#bookApptServices").append(serviceOptions);

        }
    });
}

function holdAppointment(data){
    $("isActiveHoldAppointment").val("true");
    var holdApptFormData = [];
    var locationId = $("#locationId").val();
    var date;
    var time = data.split("&")[1].split("=")[1];
    var resourceId = data.split("&")[0].split("=")[1];
    var calendarType = $("#calendarType").val();
    if (calendarType === 'daily') {
        date = $("#jsCalenderDate").val();
    } else if (calendarType === 'weekly') {
        date = data.split("&")[4].split("=")[1];
    }
    var customerId = 0;
    var serviceId = 1;
    var apptDateTime = date + " " + time;
    holdApptFormData.push({name: "locationId", value: locationId});
    holdApptFormData.push({name: "resourceId", value: resourceId});
    holdApptFormData.push({name: "serviceId", value: serviceId});
    holdApptFormData.push({name: "customerId", value: customerId});
    holdApptFormData.push({name: "apptDateTime", value: apptDateTime});
    var scheduleId;

    $.ajax({
        type: 'POST',
        url: "holdAppointment.json",
        data: holdApptFormData,
        success: function(response){
            scheduleId = response.scheduleId;
            if(scheduleId == null || scheduleId == undefined) {
                alert("Unable to hold the time slot. Reason: " + response.errorMessage);
                return false;
            }
        },
        async:false
    });
    return scheduleId;
}

function openAppointmentDetails(element) {
    try {
        $("input[name='resourceIds']").attr('disabled',true);
        if ($("#showRightSideSection").is(":visible")) {
            return false;
        }
        $("#apptTypeExistingNew").val("existing");
        //console.log($(element).attr("data"));
        var data = $(element).attr("data"); //resourceId=10&time=08:15&customerId=541&scheduleId=0&date=
        var date;
        var time = data.split("&")[1].split("=")[1];
        var scheduleId = data.split("&")[3].split("=")[1];
        var locationName = $("#locationId option:selected").text();
        var locationId = $("#locationId").val();
        var resourceId = data.split("&")[0].split("=")[1];
        $(".resources").hide();
        var calendarType = $("#calendarType").val();
        var rightSideSelector = "";
        if (calendarType === 'daily') {
            date = $("#jsCalenderDate").val();
            $("#showWeeklyRightSideSection").hide();
            $("#showRightSideSection").show();
            $(".resourceId_" + resourceId).show();
            rightSideSelector = "#showRightSideSection";
        } else if (calendarType === 'weekly') {
            date = data.split("&")[4].split("=")[1];
            var weeklyDate = date.split('/')[1];
            $(".weeklyDate_" + weeklyDate).show();
            $("#showRightSideSection").hide();
            $("#showWeeklyRightSideSection").show();
            rightSideSelector = "#showWeeklyRightSideSection";
        }
        data = data.replace(/\s/g, '');
        var url = "calendar-right-side-details-section.html?" + data;

        $(rightSideSelector).load(url, function (response, status, xhr) {
            if (calendarType === 'daily') {
                $("#dailyCal").attr("class", "float-left small-table");
                $("#intake").text($("th.resourceId_" + resourceId).text());
            } else if (calendarType === 'weekly') {
                $("#weeklyCal").attr("class", "float-left small-table");
                var intakeValue = $('input[name=resourceIds]:checked', '#resourceServiceList').next().text();
                $("#intake").text(intakeValue);
            }

            $("#selectCustomer").hide();
            loadCustomer(element);
            $("#resourceId").val(resourceId);
            $("#bookApptLocationId").val($("#locationId").val());
            $("#bookApptLocationName").text(locationName);
            $("#bookApptScheduleId").val(scheduleId);
            var bookedService = $("#bookedApptServiceName").val();
            $.ajax({
                type: "GET",
                url: "holdAppointmentResourceList.json?locationId=" + $("#locationId").val() + "&resourceId=" + resourceId + "&serviceId=1",
                dataType: "json",
                success: function (data) {
                    //console.log(data);
                    var serviceOptions = "";
                    $.each(data, function (index, value) {
                        var duration = (value.duration != null) ? '-' + value.duration : '';
                        if(value.serviceNameOnline == bookedService){
                            serviceOptions += '<option value="' + value.serviceId + '" selected >' + value.serviceNameOnline + duration + '</option>';
                        } else {
                            serviceOptions += '<option value="' + value.serviceId + '" >' + value.serviceNameOnline + duration + '</option>';
                        }
                    });
                    $("#bookApptServices").append(serviceOptions);

                }
            });
        });
    } catch (e) {
        alert(e);
    }
}

function loadCustomer(element) {

    try {
        var dataInput = $(element).next().children();
        $("#apptInfoDiv").append(dataInput);
        var apptDateTime = dataInput.attr("apptDateTime");
        var locationName = dataInput.attr("locationName");
        var resourceName = dataInput.attr("resourceName");
        var serviceName = dataInput.attr("serviceName");
        var apptStatus = dataInput.attr("apptStatus");
        var apptMethod = dataInput.attr("apptMethod");
        var walkIn = dataInput.attr("walkIn");
        var accessed = dataInput.attr("accessed");
        var confirmNumber = dataInput.attr("confirmNumber");
        var customerId = dataInput.attr("customerid");
        var houseHoldId = dataInput.attr("houseHoldId");
        var blockedFlag = dataInput.attr("blockedFlag");

        $("#first_name").val(dataInput.attr("firstName"));
        $("#last_name").val(dataInput.attr("lastName"));
        $("#account_number").val(dataInput.attr("ssn"));
        $("#email").val(dataInput.attr("email"));
        $("#addr").val(dataInput.attr("address"));
        $("#addrCity").val(dataInput.attr("city"));
        $("#addrState").val(dataInput.attr("state"));
        $("#addrZip").val(dataInput.attr("zipCode"));
        $("#attrib1").val(dataInput.attr("attrib1"));
        if (dataInput.attr("contactPhone") != undefined) {
            var contactPhoneArr = dataInput.attr("contactPhone").split("-");
            $("#contact_phone_1").val(contactPhoneArr[0]);
            $("#contact_phone_2").val(contactPhoneArr[1]);
            $("#contact_phone_3").val(contactPhoneArr[2]);
        }
        var dateOfBirth = dataInput.attr("dob");
        if (dateOfBirth != undefined) {
            var dateArray = dateOfBirth.split("/");
            $("#dob_1").val(dateArray[0]);
            $("#dob_2").val(dateArray[1]);
            $("#dob_3").val(dateArray[2]);
        }
        $("#audioId").show();
        $("#bookApptCustomerId").val(dataInput.attr("customerid"));

        //load Appt History
        var apptHistoryHtmlContent = getAppointmentsListByCustomerId(customerId);
        $("#historyDiv").html("");
        $("#historyDiv").html(apptHistoryHtmlContent);
    } catch (e) {
        console.log(e);
    }
}

function getAppointmentsListByCustomerId(customerId){
    var htmlData;
    try {
        var url = "getAppointmentsByCustomerId.html?customerId="+customerId;
        $.ajax({
            type: "GET",
            url: url,
            async:false,
            success: function( data ) {
            console.log("data: " + data);
            var searchResponse = $.parseJSON(data);
            console.log("Parsed to JSON: " + searchResponse);
            if(searchResponse.status){
                if(searchResponse.searchApptList.length > 0) {
                    url = "getAppointmentStatusDropDownList.html";
                    var appointmentStatusDropDownResponse;
                    $.get(url, function (apptStatusData) {
                        appointmentStatusDropDownResponse = $.parseJSON(apptStatusData);
                    });

                    htmlData = '<div id="apptMsg" style="color:blue;"></div>'
                    htmlData = htmlData + '<table id="apptDetailsTable_' + customerId + '" cellpadding="5" cellspacing="0" border="0" style="padding-left:5px;" class="main-table">'
                    htmlData = htmlData + '<thead>' +
                        '<tr>' +
                        '<th width="5%">Conf No.</th>' +
                        '<th width="20%">Appt Date</th>' +
                        '<th width="15%">Location</th>' +
                        '<th width="25%">Intake</th>' +
                        '<th width="20%">Service</th>' +
                        '<th width="15%">Cancel</th>' +
                        '</tr>';
                    for (var i = 0; i < searchResponse.searchApptList.length; i++) {
                        var searchAppt = searchResponse.searchApptList[i];

                        htmlData = htmlData + '<tr>' +
                            '<td>' + searchAppt.confirmNumber + '</td>' +
                            '<td>' + searchAppt.apptDateTime + '</td>' +
                            '<td>' + searchAppt.locationName + '</td>' +
                            '<td>' + searchAppt.resourceName + '</td>' +
                            '<td>' + searchAppt.serviceName + '</td>' +
                            '<td><a href="javascript:void(0)" data="" class="cancleUpcomingAppt" id="confirmNumber_"' + searchAppt.confirmNumber + '>Cancel</a></td>' +
                            '</tr>';
                    }
                    htmlData = htmlData + '</table>'
                }
            } else {
                htmlData = "<div style='color:red;'>"+searchResponse.message+"</div>";
            }
        }
        });
    }catch (e){
        htmlData = "<div style='color:red;'> Error while retriving Appointment details!</div>";
    }
    return htmlData;
}

function hideRightSideSection() {
    if ($("#isActiveHoldAppointment").val()) {
        $.ajax({
            type: "GET",
            url: "releaseHoldAppointment.json?scheduleId=" + $("#bookApptScheduleId").val(),
            dataType: "json",
            success: function (data) {
                var calendarType = $("#calendarType").val();
                try {
                    if (calendarType == "daily") {
                        searchDailyCalender();
                    } else if (calendarType == "weekly") {
                        searchWeeklyCalender();
                    }
                } catch (e) {
                    alert("Error : " + e);
                }
            },
            error: function () {
                console.log("Error");
            }
        });
    }
}