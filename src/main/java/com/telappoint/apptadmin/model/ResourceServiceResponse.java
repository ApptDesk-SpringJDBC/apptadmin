package com.telappoint.apptadmin.model;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author Murali G
 *
 */
public class ResourceServiceResponse extends BaseResponse {
	private Map<Resource, List<ServiceVO>> resourceServices;

	public Map<Resource, List<ServiceVO>> getResourceServices() {
		return resourceServices;
	}

	public void setResourceServices(Map<Resource, List<ServiceVO>> resourceServices) {
		this.resourceServices = resourceServices;
	}
}
