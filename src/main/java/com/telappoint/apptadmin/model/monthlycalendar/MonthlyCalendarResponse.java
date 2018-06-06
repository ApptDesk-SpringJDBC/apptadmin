package com.telappoint.apptadmin.model.monthlycalendar;

import com.telappoint.apptadmin.model.BaseResponse;
import com.telappoint.apptadmin.model.PerDateAppts;

import java.util.Map;

/**
 * 
 * @author Murali G
 *
 */
public class MonthlyCalendarResponse extends BaseResponse {

	private Integer locationId;
	private Integer blockTimeInMins;
	private String calendarLastDate;
	private Map<String,PerDateAppts> perDateAppts;

	public Map<String,PerDateAppts> getPerDateAppts() {
		return perDateAppts;
	}

	public void setPerDateAppts(Map<String,PerDateAppts> perDateAppts) {
		this.perDateAppts = perDateAppts;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getBlockTimeInMins() {
		return blockTimeInMins;
	}

	public void setBlockTimeInMins(Integer blockTimeInMins) {
		this.blockTimeInMins = blockTimeInMins;
	}

	public String getCalendarLastDate() {
		return calendarLastDate;
	}

	public void setCalendarLastDate(String calendarLastDate) {
		this.calendarLastDate = calendarLastDate;
	}
}
