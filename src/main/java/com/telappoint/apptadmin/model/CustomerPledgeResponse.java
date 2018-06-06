package com.telappoint.apptadmin.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
 * @author Murali G
 *
 */

@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerPledgeResponse extends BaseResponse {
	private List<CustomerPledge> customerPledgeList;
	
	public List<CustomerPledge> getCustomerPledgeList() {
		return customerPledgeList;
	}
	public void setCustomerPledgeList(List<CustomerPledge> customerPledgeList) {
		this.customerPledgeList = customerPledgeList;
	}
}
