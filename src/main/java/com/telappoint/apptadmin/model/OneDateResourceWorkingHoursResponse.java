package com.telappoint.apptadmin.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class OneDateResourceWorkingHoursResponse extends BaseResponse {
	private List<OneDateResourceWorkingHoursDetails> oneDateResourceWorkingHoursList;

	public List<OneDateResourceWorkingHoursDetails> getOneDateResourceWorkingHoursList() {
		return oneDateResourceWorkingHoursList;
	}

	public void setOneDateResourceWorkingHoursList(List<OneDateResourceWorkingHoursDetails> oneDateResourceWorkingHoursList) {
		this.oneDateResourceWorkingHoursList = oneDateResourceWorkingHoursList;
	}
}
