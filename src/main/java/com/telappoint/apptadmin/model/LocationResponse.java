package com.telappoint.apptadmin.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * 
 * @author Murali G
 *
 */
@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LocationResponse extends BaseResponse {
	
	// populated if locations 
	private List<Location> locationList;
	private List<Location> deletedLocationList;
	private Map<String, DynamicFieldDisplay> dynamicFieldDisplay;
	
	// populated if only one location details.
	private Location location;
	private Integer locationId;

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Location> getDeletedLocationList() {
		return deletedLocationList;
	}

	public void setDeletedLocationList(List<Location> deletedLocationList) {
		this.deletedLocationList = deletedLocationList;
	}

	public Map<String, DynamicFieldDisplay> getDynamicFieldDisplay() {
		return dynamicFieldDisplay;
	}

	public void setDynamicFieldDisplay(Map<String, DynamicFieldDisplay> dynamicFieldDisplay) {
		this.dynamicFieldDisplay = dynamicFieldDisplay;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
}
