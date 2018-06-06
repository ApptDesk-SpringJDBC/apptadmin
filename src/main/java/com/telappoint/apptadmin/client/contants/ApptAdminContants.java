package com.telappoint.apptadmin.client.contants;

/**
 * @author Murali G
 */
public enum ApptAdminContants {

	EMPTY_STRING(""),
	ONLINE("online"),
	
	LANG_CODE("us-en"),
	DEVICE_TYPE("online"),
	ONLINE_EMAIL_APPT_CONF_BODY("ONLINE_EMAIL_APPT_CONF_BODY"),
	ONLINE_EMAIL_APPT_REMIND_BODY("ONLINE_EMAIL_APPT_REMIND_BODY"),
	
	LOCATION_DEFAULT_VALUE("Location"),
	RESOURCE_DEFAULT_VALUE("Resource"),
	SERVICE_DEFAULT_VALUE("Service"),
	DEPARTMENT_DEFAULT_VALUE("Department"),
	
	LOCATION_CLOSED_AUDIO_FILE_APPEND_NAME("loc_closed"),
	LOCATION_CLOSED_AUDIO_FILE_DEFAULT_EXTENSION(".mp3"),
	SERVICE_CLOSED_AUDIO_FILE_APPEND_NAME("loc_closed"),
	
	SUPPORT_ERROR_FILE_APPEND_NAME("support_error"),
	SUPPORT_ERROR_FILE_DEFAULT_EXTENSION(".jpeg");

	private String value;

	private ApptAdminContants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
