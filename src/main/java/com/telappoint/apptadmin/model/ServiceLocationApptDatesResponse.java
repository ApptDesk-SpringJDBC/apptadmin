package com.telappoint.apptadmin.model;

import java.util.List;
/**
 * 
 * @author Murali G
 *
 */
public class ServiceLocationApptDatesResponse extends BaseResponse {
	private List<ServiceLocation> serviceLocationList;

	public List<ServiceLocation> getServiceLocationList() {
		return serviceLocationList;
	}

	public void setServiceLocationList(List<ServiceLocation> serviceLocationList) {
		this.serviceLocationList = serviceLocationList;
	}
}
