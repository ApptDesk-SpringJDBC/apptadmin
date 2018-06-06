package com.telappoint.apptadmin.model;

import java.util.List;

public class AccessPrivilegeResponse {
	private List<AccessPrivilege> accessPrivilegeList;
	private List<String> privilegeNames;

	public List<AccessPrivilege> getAccessPrivilegeList() {
		return accessPrivilegeList;
	}

	public void setAccessPrivilegeList(List<AccessPrivilege> accessPrivilegeList) {
		this.accessPrivilegeList = accessPrivilegeList;
	}

	public List<String> getPrivilegeNames() {
		return privilegeNames;
	}

	public void setPrivilegeNames(List<String> privilegeNames) {
		this.privilegeNames = privilegeNames;
	}		
}
