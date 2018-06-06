package com.telappoint.apptadmin.model;


public class ConfirmAppointmentRequest extends BaseRequest {
    private Long scheduleId;
    private String langCode;
    private Long customerId;
    private Long serviceId;
    private String comments;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ConfirmAppointmentRequest{" +
                "scheduleId=" + scheduleId +
                ", langCode='" + langCode + '\'' +
                ", customerId=" + customerId +
                ", serviceId=" + serviceId +
                ", comments='" + comments + '\'' +
                ", clientCode='" + getClientCode() + '\'' +
                '}';
    }
}
