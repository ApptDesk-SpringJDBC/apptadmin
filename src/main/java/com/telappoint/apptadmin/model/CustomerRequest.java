package com.telappoint.apptadmin.model;


public class CustomerRequest extends BaseRequest {
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

    @Override
    public String toString() {
        return "CustomerRequest{" +
                "customer=" + customer.toString() +
                '}';
    }
}
