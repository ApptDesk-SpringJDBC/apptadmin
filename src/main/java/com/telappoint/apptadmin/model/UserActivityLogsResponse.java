package com.telappoint.apptadmin.model;

import java.util.List;

public class UserActivityLogsResponse extends BaseResponse {
	private List<UserActivityLog> userActivityLogs;

	public List<UserActivityLog> getUserActivityLogs() {
		return userActivityLogs;
	}

	public void setUserActivityLogs(List<UserActivityLog> userActivityLogs) {
		this.userActivityLogs = userActivityLogs;
	}
}
