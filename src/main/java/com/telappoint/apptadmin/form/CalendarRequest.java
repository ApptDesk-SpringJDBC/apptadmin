package com.telappoint.apptadmin.form;


public class CalendarRequest {

    private String calendarDate;
    private String locationId;
    private String resourceIds;
    private String selectedResourceIds;
    private String selectedField;
    private String resourceIdServiceIdBlocksdetails;
    private String colorDoctorMap;
    private boolean showAllResourceCount;

    public boolean isShowAllResourceCount() {
        return showAllResourceCount;
    }

    public void setShowAllResourceCount(final boolean showAllResourceCount) {
        this.showAllResourceCount = showAllResourceCount;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(final String calendarDate) {
        this.calendarDate = calendarDate;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(final String locationId) {
        this.locationId = locationId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(final String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getSelectedResourceIds() {
        return selectedResourceIds;
    }

    public void setSelectedResourceIds(final String selectedResourceIds) {
        this.selectedResourceIds = selectedResourceIds;
    }

    public String getSelectedField() {
        return selectedField;
    }

    public void setSelectedField(final String selectedField) {
        this.selectedField = selectedField;
    }

    public String getResourceIdServiceIdBlocksdetails() {
        return resourceIdServiceIdBlocksdetails;
    }

    public void setResourceIdServiceIdBlocksdetails(final String resourceIdServiceIdBlocksdetails) {
        this.resourceIdServiceIdBlocksdetails = resourceIdServiceIdBlocksdetails;
    }

    public String getColorDoctorMap() {
        return colorDoctorMap;
    }

    public void setColorDoctorMap(final String colorDoctorMap) {
        this.colorDoctorMap = colorDoctorMap;
    }


}
