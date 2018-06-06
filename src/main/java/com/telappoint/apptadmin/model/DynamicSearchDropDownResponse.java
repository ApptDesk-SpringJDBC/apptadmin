package com.telappoint.apptadmin.model;

import java.util.List;

public class DynamicSearchDropDownResponse extends BaseResponse {
	private List<DynamicSearchByFields> dynamicSearchByFields;

	public List<DynamicSearchByFields> getDynamicSearchByFields() {
		return dynamicSearchByFields;
	}

	public void setDynamicSearchByFields(List<DynamicSearchByFields> dynamicSearchByFields) {
		this.dynamicSearchByFields = dynamicSearchByFields;
	}
}
