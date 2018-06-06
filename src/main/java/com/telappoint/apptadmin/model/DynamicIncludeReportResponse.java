package com.telappoint.apptadmin.model;

import java.util.List;

public class DynamicIncludeReportResponse extends BaseResponse {
	private List<DynamicIncludeReport> dynamicIncludeReportList;

	public List<DynamicIncludeReport> getDynamicIncludeReportList() {
		return dynamicIncludeReportList;
	}

	public void setDynamicIncludeReportList(List<DynamicIncludeReport> dynamicIncludeReportList) {
		this.dynamicIncludeReportList = dynamicIncludeReportList;
	}
}
