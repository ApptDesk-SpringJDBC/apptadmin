package com.telappoint.apptadmin.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Murali G
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class BaseRequest {
	private String clientCode;

	public String getClientCode() {
		return clientCode;
	}
	
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
}
