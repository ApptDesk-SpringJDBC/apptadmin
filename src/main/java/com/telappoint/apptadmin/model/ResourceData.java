package com.telappoint.apptadmin.model;

public class ResourceData {

    private Integer resourceId;
    private Long numberOfOpenSlots = Long.valueOf(0);
    private Long numberOfBookedAppts = Long.valueOf(0);
    private Integer colorId;

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(final Integer colorId) {
        this.colorId = colorId;
    }

    public Long getNumberOfOpenSlots() {
        return numberOfOpenSlots;
    }

    public void setNumberOfOpenSlots(final Long numberOfOpenSlots) {
        this.numberOfOpenSlots = numberOfOpenSlots;
    }

    public Long getNumberOfBookedAppts() {
        return numberOfBookedAppts;
    }

    public void setNumberOfBookedAppts(final Long numberOfBookedAppts) {
        this.numberOfBookedAppts = numberOfBookedAppts;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(final Integer resourceId) {
        this.resourceId = resourceId;
    }
}
