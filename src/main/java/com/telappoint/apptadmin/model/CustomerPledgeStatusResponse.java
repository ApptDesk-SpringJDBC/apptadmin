package com.telappoint.apptadmin.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerPledgeStatusResponse {
	private List<CustomerPledgeStatus> customerPledgeStatusList = new ArrayList<CustomerPledgeStatus>();

	public List<CustomerPledgeStatus> getCustomerPledgeStatusList() {
		return customerPledgeStatusList;
	}

	public void setCustomerPledgeStatusList(List<CustomerPledgeStatus> customerPledgeStatusList) {
		this.customerPledgeStatusList = customerPledgeStatusList;
	}
}
