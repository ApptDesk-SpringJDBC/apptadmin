package com.telappoint.apptadmin.model;

/**
 * Created by pepala on 20/5/17.
 *
 */
public class AppointmentInfoResponse extends BaseResponse {

    private VerifyPageData verifyPageData;

    public VerifyPageData getVerifyPageData() {
        return verifyPageData;
    }

    public void setVerifyPageData(final VerifyPageData verifyPageData) {
        this.verifyPageData = verifyPageData;
    }
}
