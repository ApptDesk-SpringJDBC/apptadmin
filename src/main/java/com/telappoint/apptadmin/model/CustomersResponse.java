package com.telappoint.apptadmin.model;

import java.util.List;

public class CustomersResponse extends BaseResponse {
	private List<Customer> customerList;

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
}
