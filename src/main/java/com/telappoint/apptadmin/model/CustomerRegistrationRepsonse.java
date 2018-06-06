package com.telappoint.apptadmin.model;

import java.util.List;
public class CustomerRegistrationRepsonse extends BaseResponse {
	private List<CustomerRegistration> customerRegistrationList;

	public List<CustomerRegistration> getCustomerRegistrationList() {
		return customerRegistrationList;
	}

	public void setCustomerRegistrationList(List<CustomerRegistration> customerRegistrationList) {
		this.customerRegistrationList = customerRegistrationList;
	}
}
