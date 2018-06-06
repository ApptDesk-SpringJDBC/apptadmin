package com.telappoint.apptadmin.model;

import java.util.Map;

public class CalanderAvailabilityResponse extends BaseResponse {
    private String locationId;
    private String isFullyBookedMessage;
    private String resourceIds;
    private String isSlotAvailableMessage;
    private String isNotAvailableMessage;
    private String isHolidayMessage;
    private String isClosedMessage;
    private Map<String, PerDateAppts> perDateAppts;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(final String locationId) {
        this.locationId = locationId;
    }

    public String getIsFullyBookedMessage() {
        return isFullyBookedMessage;
    }

    public void setIsFullyBookedMessage(final String isFullyBookedMessage) {
        this.isFullyBookedMessage = isFullyBookedMessage;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(final String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getIsSlotAvailableMessage() {
        return isSlotAvailableMessage;
    }

    public void setIsSlotAvailableMessage(final String isSlotAvailableMessage) {
        this.isSlotAvailableMessage = isSlotAvailableMessage;
    }

    public String getIsNotAvailableMessage() {
        return isNotAvailableMessage;
    }

    public void setIsNotAvailableMessage(final String isNotAvailableMessage) {
        this.isNotAvailableMessage = isNotAvailableMessage;
    }

    public String getIsHolidayMessage() {
        return isHolidayMessage;
    }

    public void setIsHolidayMessage(final String isHolidayMessage) {
        this.isHolidayMessage = isHolidayMessage;
    }

    public String getIsClosedMessage() {
        return isClosedMessage;
    }

    public void setIsClosedMessage(final String isClosedMessage) {
        this.isClosedMessage = isClosedMessage;
    }

    public Map<String, PerDateAppts> getPerDateAppts() {
        return perDateAppts;
    }

    public void setPerDateAppts(final Map<String, PerDateAppts> perDateAppts) {
        this.perDateAppts = perDateAppts;
    }
}
