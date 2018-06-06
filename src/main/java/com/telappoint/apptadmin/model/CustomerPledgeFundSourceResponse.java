package com.telappoint.apptadmin.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerPledgeFundSourceResponse {
	private List<CustomerPledgeFundSource> customerPledgeFundSourceList = new ArrayList<CustomerPledgeFundSource>();

	public List<CustomerPledgeFundSource> getCustomerPledgeFundSourceList() {
		return customerPledgeFundSourceList;
	}

	public void setCustomerPledgeFundSourceList(List<CustomerPledgeFundSource> customerPledgeFundSourceList) {
		this.customerPledgeFundSourceList = customerPledgeFundSourceList;
	}
}
