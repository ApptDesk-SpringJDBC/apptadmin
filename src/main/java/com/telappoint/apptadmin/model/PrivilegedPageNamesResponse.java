package com.telappoint.apptadmin.model;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author Murali G
 *
 */
public class PrivilegedPageNamesResponse extends BaseResponse {
	private Map<String, List<String>> previlegePageNames;

	public Map<String, List<String>> getPrevilegePageNames() {
		return previlegePageNames;
	}

	public void setPrevilegePageNames(Map<String, List<String>> previlegePageNames) {
		this.previlegePageNames = previlegePageNames;
	}
}
