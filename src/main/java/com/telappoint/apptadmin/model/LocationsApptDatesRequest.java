package com.telappoint.apptadmin.model;

import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class LocationsApptDatesRequest extends BaseRequest{
	private List<Location> locations;

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}
