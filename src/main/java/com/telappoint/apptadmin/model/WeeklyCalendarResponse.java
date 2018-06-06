package com.telappoint.apptadmin.model;

import java.util.List;
/**
 * 
 * @author Murali G
 *
 */
public class WeeklyCalendarResponse extends BaseResponse {
	private Integer locationId;
	private Integer blockTimeInMins;
	private List<DynamicFieldLabelData> dynamicToolTipData;
	private List<ResourceCalendarData> calendarDataList;
	
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public List<ResourceCalendarData> getCalendarDataList() {
		return calendarDataList;
	}

	public void setCalendarDataList(List<ResourceCalendarData> calendarDataList) {
		this.calendarDataList = calendarDataList;
	}

	public Integer getBlockTimeInMins() {
		return blockTimeInMins;
	}

	public void setBlockTimeInMins(Integer blockTimeInMins) {
		this.blockTimeInMins = blockTimeInMins;
	}

	public List<DynamicFieldLabelData> getDynamicToolTipData() {
		return dynamicToolTipData;
	}

	public void setDynamicToolTipData(List<DynamicFieldLabelData> dynamicToolTipData) {
		this.dynamicToolTipData = dynamicToolTipData;
	}
}
