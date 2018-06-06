package com.telappoint.apptadmin.model;

import java.util.List;

public class UserResponse extends BaseResponse {
	private List<AdminLogin> userList;
	private List<AdminLogin> suspendedUserList;
	
	//user exist response
	private AdminLogin adminLogin;

	public AdminLogin getAdminLogin() {
		return adminLogin;
	}

	public void setAdminLogin(AdminLogin adminLogin) {
		this.adminLogin = adminLogin;
	}
	
	public List<AdminLogin> getUserList() {
		return userList;
	}
	public void setUserList(List<AdminLogin> userList) {
		this.userList = userList;
	}
	public List<AdminLogin> getSuspendedUserList() {
		return suspendedUserList;
	}
	public void setSuspendedUserList(List<AdminLogin> suspendedUserList) {
		this.suspendedUserList = suspendedUserList;
	}
}
