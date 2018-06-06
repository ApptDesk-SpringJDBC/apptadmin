package com.telappoint.apptadmin.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LocationDepartmentResourceTO {

	private int id;

	private int locationId;
	private String location_name_online;

	private int departmentId;
	private String department_name_online;

	private int resourceId;
	private boolean enable = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocation_name_online() {
		return location_name_online;
	}

	public void setLocation_name_online(String location_name_online) {
		this.location_name_online = location_name_online;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartment_name_online() {
		return department_name_online;
	}

	public void setDepartment_name_online(String department_name_online) {
		this.department_name_online = department_name_online;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}