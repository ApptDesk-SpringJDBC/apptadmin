package com.telappoint.apptadmin.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali G
 * 
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class InBoundCallsResponse extends BaseResponse  {	
	private String totalMinutes;	
	private List<IvrCalls> ivrCallLogs;
	
	public String getTotalMinutes() {
		return totalMinutes;
	}
	public void setTotalMinutes(String totalMinutes) {
		this.totalMinutes = totalMinutes;
	}
	public List<IvrCalls> getIvrCallLogs() {
		return ivrCallLogs;
	}
	public void setIvrCallLogs(List<IvrCalls> ivrCallLogs) {
		this.ivrCallLogs = ivrCallLogs;
	}	
}
