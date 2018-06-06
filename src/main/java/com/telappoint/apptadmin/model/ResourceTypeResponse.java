package com.telappoint.apptadmin.model;

import java.util.List;

public class ResourceTypeResponse extends BaseResponse {
	private List<ResourceType> resourceTypeList;

	public List<ResourceType> getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(List<ResourceType> resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}	
}