package com.telappoint.apptadmin.model;

import java.util.List;
import java.util.Map;
public class PrivilegeSettingResponse extends BaseResponse {
	private Map<String,List<JSPPagesPrivileges>> privilegeSetting;

	public Map<String,List<JSPPagesPrivileges>> getPrivilegeSetting() {
		return privilegeSetting;
	}

	public void setPrivilegeSetting(Map<String,List<JSPPagesPrivileges>> privilegeSetting) {
		this.privilegeSetting = privilegeSetting;
	}
}
