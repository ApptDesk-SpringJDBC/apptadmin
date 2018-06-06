package com.telappoint.apptadmin.model;

public class CustomerDTO {
    private Long customerId;
    private String name;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
