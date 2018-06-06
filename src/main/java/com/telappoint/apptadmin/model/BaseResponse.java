package com.telappoint.apptadmin.model;
import java.net.URI;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BaseResponse {
	
	public boolean status = true;
	public String message;
	private URI resourceUri;
	public Long transId;
	public String errorFlag="N";
	public String errorMessage;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public URI getResourceUri() {
		return resourceUri;
	}
	
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"status=" + status +
				", message='" + message + '\'' +
				", resourceUri=" + resourceUri +
				", transId=" + transId +
				'}';
	}
	public String getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setResourceUri(URI resourceUri) {
		this.resourceUri = resourceUri;
	}
}
