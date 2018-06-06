package com.telappoint.apptadmin.model;

import java.util.List;

public class CustomerActivityResponse extends BaseResponse{
	private List<CustomerActivity> customerActivityList;

	public List<CustomerActivity> getCustomerActivityList() {
		return customerActivityList;
	}

	public void setCustomerActivityList(List<CustomerActivity> customerActivityList) {
		this.customerActivityList = customerActivityList;
	}
}
