package com.telappoint.apptadmin.constants;

/**
 * 
 * @author Murali G
 * 
 */
public enum PageStatusConstants {
	
	PAGE_STATUS_START("START",0,"Starting Page"), 
	PAGE_STATUS_CUST_INFO_SUBMIT("CUST_INFO_SUBMIT",1,"Customer Info Submitted"),
	PAGE_STATUS_LOC_SELECTION("LOC_SELECTION",2,"Location Selected"),
	PAGE_STATUS_RES_SELECTION("RES_SELECTION",3,"RESOURCE_DESPLAY_NAME Selected"),
	PAGE_STATUS_SER_SELECTION("SER_SELECTION",4,"Service Selected"),
	PAGE_STATUS_DATE_TIME_SELECTION("DATE_TIME_SELECTION",5,"Date & Time Selected"),
	PAGE_STATUS_HOLD_APPT("HOLD_APPT",8,"Holding Appointment"),
	PAGE_STATUS_BOOK_APPT("BOOK_APPT",10,"Successfully Booked Appointment"),
	PAGE_STATUS_NAME_RECORD("NAME_RECORD",9,"Name Recording Completed"),
	PAGE_STATUS_VIEW_EXISTING_APPT("VIEW_EXISTING_APPT",6,"Selected to View Existing Appointment"),
	PAGE_STATUS_CANCEL_APPT("CANCEL_APPT",7,"Cancelled Existing Appointment"),
	
	PAGE_STATUS_ADMIN_LOGIN("ADMIN_LOGIN",11,"Admin login"),
	PAGE_STATUS_UPDATE_CUST("UPDATE_CUST",12,"Update Customer details"),
	PAGE_STATUS_HOLIDAY_DELETE("HOLIDAY_DELETE",13,"Holiday deleted"),
	PAGE_STATUS_CLOSED_DAY_DELETE("CLOSED_DAY_DELETE",14,"Closed day deleted"),
	PAGE_STATUS_LOC_STATUS_CHANGED("LOC_STATUS_CHANGED",15,"Location status changed"),	
	PAGE_STATUS_LOC_CLOSED("LOC_CLOSED",16,"Location closed"),
	PAGE_STATUS_RES_STATUS_CHANGED("RES_STATUS_CHANGED",17,"Resource status changed"),
	PAGE_STATUS_RES_CLOSED("RES_CLOSED",18,"Resource closed"),
	PAGE_STATUS_SER_STATUS_CHANGED("SER_STATUS_CHANGED",19,"Service status changed"),
	PAGE_STATUS_SER_CLOSED("SER_CLOSED",20,"Service closed"),	
	PAGE_STATUS_ONE_DATE("ONE_DATE",21,"One date operation"),
	PAGE_STATUS_DATE_RANGE("DATE_RANGE",22,"Date range operation"),
	PAGE_STATUS_RESERVE_HRS("SER_DELETE",23,"Reserve Hrs operation"),
	PAGE_STATUS_RESCHEDULE_APPT("RESCHEDULE_APPT",24,"Rescheduled Existing Appointment"),
	
	PAGE_STATUS_LOC_DELETE("LOC_DELETE",25,"Location Deleted"),
	PAGE_STATUS_LOC_UN_DELETE("LOC_UN_DELETE",26,"Locations Un Deleted"),
	PAGE_STATUS_LOC_CHANGE_ORDER("LOC_CHANGE_ORDER",27,"Locations change order"),
	
	PAGE_STATUS_RES_DELETE("RES_DELETE",28,"Resource Deleted"),
	PAGE_STATUS_RES_UN_DELETE("RES_UN_DELETE",29,"Resources un Deleted"),
	PAGE_STATUS_RES_CHANGE_ORDER("RES_CHANGE_ORDER",30,"Resources change order"),
	
	PAGE_STATUS_SER_DELETE("SER_DELETE",31,"Service Deleted"),
	PAGE_STATUS_SER_UN_DELETE("SER_UN_DELETE",32,"Services Un Deleted"),
	PAGE_STATUS_SER_CHANGE_ORDER("SER_CHANGE_ORDER",33,"Services change order");
	
    private String page;
	private int status;
	private String description;

	private PageStatusConstants(String page,int status,String description) {
		this.page = page;
		this.status = status;
		this.description = description;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static class PageStatusDescription {
		public static String getDescription(int status,String resourceLabel) {
			PageStatusConstants[] keys = PageStatusConstants.values();
			int _status = 0;
			for (PageStatusConstants key : keys) {
				_status = key.getStatus();
				if (_status == status) {
					return key.getDescription().replace("RESOURCE_DESPLAY_NAME", resourceLabel);
				}
			}
			return "";
		}
	}
	
	public static class PageStatusPage {
		public static String getPage(int status) {
			PageStatusConstants[] keys = PageStatusConstants.values();
			int _status = 0;
			for (PageStatusConstants key : keys) {
				_status = key.getStatus();
				if (_status == status) {
					return key.getPage();
				}
			}
			return "";
		}
	}
	
	public static class PageStatusValues {
		public int getStatus(String page) {
			PageStatusConstants[] keys = PageStatusConstants.values();
			String _page = "";
			for (PageStatusConstants key : keys) {
				_page = key.getPage();
				if (_page == page) {
					return key.getStatus();
				}
			}
			return -1;
		}
	}
	
	public static void main(String[] args) {
		String result = PageStatusDescription.getDescription(1,"");
		System.out.println(result);
	}

}