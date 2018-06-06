package com.telappoint.apptadmin.model;

import java.util.List;

public class ResourceTitleResponse extends BaseResponse {
	private List<ResourceTitle> resourceTitleList;

	public List<ResourceTitle> getResourceTitleList() {
		return resourceTitleList;
	}

	public void setResourceTitleList(List<ResourceTitle> resourceTitleList) {
		this.resourceTitleList = resourceTitleList;
	}
}
