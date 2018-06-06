package com.telappoint.apptadmin.model;

import java.util.List;

/**
 * 
 * @author Murali G
 *
 */
public class ServiceLocationApptDatesRequest extends BaseRequest {
	private List<ServiceLocation> serviceLocationList;

	public List<ServiceLocation> getServiceLocationList() {
		return serviceLocationList;
	}

	public void setServiceLocationList(List<ServiceLocation> serviceLocationList) {
		this.serviceLocationList = serviceLocationList;
	}
}
