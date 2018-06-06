package com.telappoint.apptadmin.model;

import java.util.Map;

public class DynamicFieldDisplayResponse extends BaseResponse {
	private Map<String, DynamicFieldDisplay> dynamicFieldDisplay;

	public Map<String, DynamicFieldDisplay> getDynamicFieldDisplay() {
		return dynamicFieldDisplay;
	}

	public void setDynamicFieldDisplay(Map<String, DynamicFieldDisplay> dynamicFieldDisplay) {
		this.dynamicFieldDisplay = dynamicFieldDisplay;
	}
	
}
