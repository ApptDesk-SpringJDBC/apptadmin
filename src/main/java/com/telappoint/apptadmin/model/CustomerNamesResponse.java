package com.telappoint.apptadmin.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class CustomerNamesResponse extends BaseResponse {

	@JsonProperty("customerNames")
	private List<CustomerDTO> customerNames;

	public List<CustomerDTO> getCustomerNames() {
		return customerNames;
	}

	public void setCustomerNames(final List<CustomerDTO> customerNames) {
		this.customerNames = customerNames;
	}

}
