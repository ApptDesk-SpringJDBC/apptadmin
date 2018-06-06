package com.telappoint.apptadmin.model;

import java.util.List;

/**
 * 
 * @author Balaji
 * 
 */
public class PerDateAppts {
	private String isFullyBooked = "N";
	private String isSlotAvailable = "N";
	private String isNotAvailable = "N";
	
	private String isDateDisplay = "Y";
	private String isHoliday="N";
	private String isClosed="N";
	
	private Long numberOfOpenSlots=Long.valueOf(0);
	private Long numberOfBookedAppts=Long.valueOf(0);
	private Long totalTimeSlots=Long.valueOf(0);
	private Long numberOfNotAvailable=Long.valueOf(0);

	private List<ResourceData> resourceDataList;

	public String getIsFullyBooked() {

		return isFullyBooked;
	}

	public String getIsSlotAvailable() {
		return isSlotAvailable;
	}

	public String getIsNotAvailable() {
		return isNotAvailable;
	}

	public void setIsFullyBooked(String isFullyBooked) {
		this.isFullyBooked = isFullyBooked;
	}

	public void setIsSlotAvailable(String isSlotAvailable) {
		this.isSlotAvailable = isSlotAvailable;
	}

	public void setIsNotAvailable(String isNotAvailable) {
		this.isNotAvailable = isNotAvailable;
	}

	public String getIsDateDisplay() {
		return isDateDisplay;
	}

	public void setIsDateDisplay(String isDateDisplay) {
		this.isDateDisplay = isDateDisplay;
	}

	public String getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}

	public Long getNumberOfOpenSlots() {
		return numberOfOpenSlots;
	}

	public void setNumberOfOpenSlots(Long numberOfOpenSlots) {
		this.numberOfOpenSlots = numberOfOpenSlots;
	}

	public Long getNumberOfBookedAppts() {
		return numberOfBookedAppts;
	}

	public void setNumberOfBookedAppts(Long numberOfBookedAppts) {
		this.numberOfBookedAppts = numberOfBookedAppts;
	}

	public Long getTotalTimeSlots() {
		return totalTimeSlots;
	}

	public void setTotalTimeSlots(Long totalTimeSlots) {
		this.totalTimeSlots = totalTimeSlots;
	}

	public Long getNumberOfNotAvailable() {
		return numberOfNotAvailable;
	}

	public void setNumberOfNotAvailable(Long numberOfNotAvailable) {
		this.numberOfNotAvailable = numberOfNotAvailable;
	}

	public List<ResourceData> getResourceDataList() {
		return resourceDataList;
	}

	public void setResourceDataList(final List<ResourceData> resourceDataList) {
		this.resourceDataList = resourceDataList;
	}

}
