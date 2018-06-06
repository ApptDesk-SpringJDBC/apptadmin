package com.telappoint.apptadmin.form;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Murali G
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserLoginTO {
	private String username;
	private String password;
	private String ipAddress;
	@JsonIgnore
	private boolean keepMeLoggedIn;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public boolean isKeepMeLoggedIn() {
		return keepMeLoggedIn;
	}
	public void setKeepMeLoggedIn(boolean keepMeLoggedIn) {
		this.keepMeLoggedIn = keepMeLoggedIn;
	}
}
