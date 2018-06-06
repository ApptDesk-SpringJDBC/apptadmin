package com.telappoint.apptadmin.model;

public class ResourceService {

	private int id;
	private String service_name_online;
	private int resourceId;
	private int serviceId;
	private boolean allow_admin = false;
	private boolean allow_selfservice = false;
	private boolean enable = false;
	private String serviveEnable;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getService_name_online() {
		return service_name_online;
	}

	public void setService_name_online(String service_name_online) {
		this.service_name_online = service_name_online;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public boolean isAllow_admin() {
		return allow_admin;
	}

	public void setAllow_admin(boolean allow_admin) {
		this.allow_admin = allow_admin;
	}

	public boolean isAllow_selfservice() {
		return allow_selfservice;
	}

	public void setAllow_selfservice(boolean allow_selfservice) {
		this.allow_selfservice = allow_selfservice;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getServiveEnable() {
		return serviveEnable;
	}

	public void setServiveEnable(String serviveEnable) {
		this.serviveEnable = serviveEnable;
	}
}