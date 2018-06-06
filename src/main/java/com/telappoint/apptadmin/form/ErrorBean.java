/*
 * 
 */
package com.telappoint.apptadmin.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.apptadmin.constants.ErrorCodesConstants;


/**
 * @author Murali G
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ErrorBean {

    private String appCode = "Appt";
    private int errorCode = 0;
    private String displayText = "";
    private long errorID = 0;
    
    private String clientCode = "";
    private String errorDescription = "";
    private String transId = "";
    private String page = "";
   
    public ErrorBean() {    	
    }
    
    public ErrorBean(String appCode, int errorCode, String displayText) {
        this.appCode = appCode;
        this.errorCode = errorCode;
        this.displayText = displayText;
    }
    
    public ErrorBean(String clientCode,String appCode,String transId,String page, int errorCode, String errorDescription) {
        this.clientCode = clientCode;
        this.appCode = appCode;
        this.transId = transId;
        this.page = page;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;       
    }
    
    public ErrorBean(String clientCode,String transId,ErrorCodesConstants errorCodesConstant) {
        this.clientCode = clientCode;
        this.transId = transId;
        this.page = errorCodesConstant.getPage();
        this.errorCode = errorCodesConstant.getErrorCode();
        this.errorDescription = errorCodesConstant.getDescription();    
    }

    public String getAppCode() {
        return this.appCode;
    }
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }    
    public int getErrorCode() {
        return this.errorCode;
    }
    public String getErrorCodeStr() {
        return String.valueOf(this.errorCode);
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }    
    public String getDisplayText() {
        return this.displayText;
    }
    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }    
    public long getErrorID() {
        return this.errorID;
    }
    public String getErrorIDStr() {
        return String.valueOf(this.errorID);
    }    
    public void setErrorID(long errorID) {
        this.errorID = errorID;
    }
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
}
