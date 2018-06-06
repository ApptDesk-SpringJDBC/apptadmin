package com.telappoint.apptadmin.model;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Murali G
 *
 */
public class SearchResponse extends BaseResponse {
	private List<SearchData> searchApptList;
	private Map<Customer, List<AppointmentData>> searchAppointmentList;

	public List<SearchData> getSearchApptList() {
		return searchApptList;
	}

	public void setSearchApptList(List<SearchData> searchApptList) {
		this.searchApptList = searchApptList;
	}

	public Map<Customer, List<AppointmentData>> getSearchAppointmentList() {
		return searchAppointmentList;
	}

	public void setSearchAppointmentList(Map<Customer, List<AppointmentData>> searchAppointmentList) {
		this.searchAppointmentList = searchAppointmentList;
	}
}
