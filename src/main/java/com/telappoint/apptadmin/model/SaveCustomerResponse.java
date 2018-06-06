package com.telappoint.apptadmin.model;

public class SaveCustomerResponse extends BaseResponse {

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }
}
