package com.telappoint.apptadmin.model;

import java.util.List;

public class ResourcePrefixResponse extends BaseResponse {
	private List<ResourcePrefix> resourcePrefixList;

	public List<ResourcePrefix> getResourcePrefixList() {
		return resourcePrefixList;
	}

	public void setResourcePrefixList(List<ResourcePrefix> resourcePrefixList) {
		this.resourcePrefixList = resourcePrefixList;
	}
}
