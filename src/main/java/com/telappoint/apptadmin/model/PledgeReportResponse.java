package com.telappoint.apptadmin.model;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Murali G
 *
 */
public class PledgeReportResponse extends BaseResponse {
	private  Map<String, List<PledgeDetails>> pledgeReportData;

	public Map<String, List<PledgeDetails>> getPledgeReportData() {
		return pledgeReportData;
	}

	public void setPledgeReportData(Map<String, List<PledgeDetails>> pledgeReportData) {
		this.pledgeReportData = pledgeReportData;
	}
}
