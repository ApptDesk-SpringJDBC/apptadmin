package com.telappoint.apptadmin.form;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali G
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) 
public class CustomerTO implements Serializable
{
	private static final long serialVersionUID = -594742966098048946L;

	private long id;
	
	private String houseHoldId;
	
	private String household_id;// for client search added as per db column

	private String clientCode;

	private String account_number;
	private String  conf_number;

	private String first_name;

	private String middle_name;

	private String last_name;

	private String contact_phone;
	private String apptDate;
	private String contact_phone1;
	private String contact_phone2;
	private String contact_phone3;

	private String home_phone;

	private String work_phone;

	private String cell_phone;
	
	private String home_phone1 = "";
	private String home_phone2 = "";
	private String home_phone3 = "";

	private String work_phone1 = "";
	private String work_phone2 = "";
	private String work_phone3 = "";

	private String cell_phone1 = "";
	private String cell_phone2 = "";
	private String cell_phone3 = "";
    
	private String sex;

	private String pin;

	private String email;

	private String remind_lang;

	private String remind_phone;

	private Integer remind_time;

	private String provider_ids;

	private String location_ids;

	private String address;

	private String address_2;

	private String city;

	private String state;

	private String zip_postal;

	private String country;

	private String notify_by_phone;

	private String notify_by_phone_confirm;

	private String notify_by_sms;

	private String notify_by_sms_confirm;

	private String notify_by_email;

	private String notify_by_email_confirm;

	private String notify_by_push_notification;

	private String property_name;

	private String delete_flag;

	private String blocked_flag;

	private String attrib1;

	private String attrib2;

	private String attrib3;

	private String attrib4;

	private String attrib5;

	private String attrib6;

	private String attrib7;

	private String attrib8;

	private String attrib9;

	private String attrib10;

	private String attrib11;
	
	private String attrib12;

	private String attrib13;

	private String attrib14;

	private String attrib15;

	private String attrib16;

	private String attrib17;

	private String attrib18;

	private String attrib19;

	private String attrib20; //This is we are using to capture a reason for blocking a customer
	
	private String dob;
	private String dob1;// internal onlineappt
	private String dob2;// internal onlineappt
	private String dob3;// internal onlineappt
	
	private String timeStamp;
	private String apptMethod;
	private String confNumber;
	
	private String scheduleIdStr;
	
	@JsonIgnore
	private String[] blockedServiceIdsList;

	private String  blockedServiceIds;	
	private String  blockedServices;
	
	private String  liheap_fund;
	private String  psehelp_fund;
	
	public String getClientCode()
	{
		return clientCode;
	}

	public void setClientCode(String clientCode)
	{
		this.clientCode = clientCode;
	}

	public String getAttrib1()
	{
		if(attrib1!=null && !"null".equals(attrib1)){
			return attrib1;
		}else{
			return "";
		}
	}

	public void setAttrib1(String attrib1)
	{
		this.attrib1 = attrib1;
	}

	public String getAttrib2()
	{
		return attrib2;
	}

	public void setAttrib2(String attrib2)
	{
		this.attrib2 = attrib2;
	}

	public String getAttrib3()
	{
		return attrib3;
	}

	public void setAttrib3(String attrib3)
	{
		this.attrib3 = attrib3;
	}

	public String getAttrib4()
	{
		return attrib4;
	}

	public void setAttrib4(String attrib4)
	{
		this.attrib4 = attrib4;
	}

	public String getAttrib5()
	{
		return attrib5;
	}

	public void setAttrib5(String attrib5)
	{
		this.attrib5 = attrib5;
	}

	public String getAttrib6()
	{
		return attrib6;
	}

	public void setAttrib6(String attrib6)
	{
		this.attrib6 = attrib6;
	}

	public String getAttrib7()
	{
		return attrib7;
	}

	public void setAttrib7(String attrib7)
	{
		this.attrib7 = attrib7;
	}

	public String getAttrib8()
	{
		return attrib8;
	}

	public void setAttrib8(String attrib8)
	{
		this.attrib8 = attrib8;
	}

	public String getAttrib9()
	{
		return attrib9;
	}

	public void setAttrib9(String attrib9)
	{
		this.attrib9 = attrib9;
	}

	public String getAttrib10()
	{
		return attrib10;
	}

	public void setAttrib10(String attrib10)
	{
		this.attrib10 = attrib10;
	}

	public String getAttrib11()
	{
		return attrib11;
	}

	public void setAttrib11(String attrib11)
	{
		this.attrib11 = attrib11;
	}

	public String getAttrib12()
	{
		return attrib12;
	}

	public void setAttrib12(String attrib12)
	{
		this.attrib12 = attrib12;
	}

	public String getAttrib13()
	{
		return attrib13;
	}

	public void setAttrib13(String attrib13)
	{
		this.attrib13 = attrib13;
	}

	public String getAttrib14()
	{
		return attrib14;
	}

	public void setAttrib14(String attrib14)
	{
		this.attrib14 = attrib14;
	}

	public String getAttrib15()
	{
		return attrib15;
	}

	public void setAttrib15(String attrib15)
	{
		this.attrib15 = attrib15;
	}

	public String getAttrib16()
	{
		return attrib16;
	}

	public void setAttrib16(String attrib16)
	{
		this.attrib16 = attrib16;
	}

	public String getAttrib17()
	{
		return attrib17;
	}

	public void setAttrib17(String attrib17)
	{
		this.attrib17 = attrib17;
	}

	public String getAttrib18()
	{
		return attrib18;
	}

	public void setAttrib18(String attrib18)
	{
		this.attrib18 = attrib18;
	}

	public String getAttrib19()
	{
		return attrib19;
	}

	public void setAttrib19(String attrib19)
	{
		this.attrib19 = attrib19;
	}

	public String getAttrib20()
	{
		return attrib20;
	}

	public void setAttrib20(String attrib20)
	{
		this.attrib20 = attrib20;
	}

	

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getAccount_number()
	{
		return account_number;
	}

	public void setAccount_number(String account_number)
	{
		this.account_number = account_number;
	}

	public String getFirst_name()
	{
		return first_name;
	}

	public void setFirst_name(String first_name)
	{
		this.first_name = first_name;
	}

	public String getMiddle_name()
	{
		return middle_name;
	}

	public void setMiddle_name(String middle_name)
	{
		this.middle_name = middle_name;
	}

	public String getLast_name()
	{
		return last_name;
	}

	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}

	public String getContact_phone() {
		if(contact_phone1!=null && contact_phone1!="" && contact_phone1.length()>0){
			this.contact_phone = contact_phone1 +""+contact_phone2+""+contact_phone3;
		}
		return contact_phone;
	}

	public void setContact_phone(String contact_phone)
	{
		this.contact_phone = contact_phone;
	}

	public String getHome_phone()
	{
		return home_phone;
	}

	public void setHome_phone(String home_phone)
	{
		this.home_phone = home_phone;
	}

	public String getWork_phone()
	{
		return work_phone;
	}

	public void setWork_phone(String work_phone)
	{
		this.work_phone = work_phone;
	}

	public String getCell_phone()
	{
		return cell_phone;
	}

	public void setCell_phone(String cell_phone)
	{
		this.cell_phone = cell_phone;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getPin()
	{
		return pin;
	}

	public void setPin(String pin)
	{
		this.pin = pin;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getRemind_lang()
	{
		return remind_lang;
	}

	public void setRemind_lang(String remind_lang)
	{
		this.remind_lang = remind_lang;
	}

	public String getRemind_phone()
	{
		return remind_phone;
	}

	public void setRemind_phone(String remind_phone)
	{
		this.remind_phone = remind_phone;
	}

	public Integer getRemind_time()
	{
		return remind_time;
	}

	public void setRemind_time(Integer remind_time)
	{
		this.remind_time = remind_time;
	}

	public String getProvider_ids()
	{
		return provider_ids;
	}

	public void setProvider_ids(String provider_ids)
	{
		this.provider_ids = provider_ids;
	}

	public String getLocation_ids()
	{
		return location_ids;
	}

	public void setLocation_ids(String location_ids)
	{
		this.location_ids = location_ids;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getAddress_2()
	{
		return address_2;
	}

	public void setAddress_2(String address_2)
	{
		this.address_2 = address_2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getZip_postal()
	{
		return zip_postal;
	}

	public void setZip_postal(String zip_postal)
	{
		this.zip_postal = zip_postal;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getDelete_flag()
	{
		return delete_flag;
	}

	public void setDelete_flag(String delete_flag)
	{
		this.delete_flag = delete_flag;
	}

	public String getBlocked_flag()
	{
		return blocked_flag;
	}

	public void setBlocked_flag(String blocked_flag)
	{
		this.blocked_flag = blocked_flag;
	}

	public String getNotify_by_phone()
	{
		return notify_by_phone;
	}

	public void setNotify_by_phone(String notify_by_phone)
	{
		this.notify_by_phone = notify_by_phone;
	}

	public String getNotify_by_phone_confirm()
	{
		return notify_by_phone_confirm;
	}

	public void setNotify_by_phone_confirm(String notify_by_phone_confirm)
	{
		this.notify_by_phone_confirm = notify_by_phone_confirm;
	}

	public String getNotify_by_sms()
	{
		return notify_by_sms;
	}

	public void setNotify_by_sms(String notify_by_sms)
	{
		this.notify_by_sms = notify_by_sms;
	}

	public String getNotify_by_sms_confirm()
	{
		return notify_by_sms_confirm;
	}

	public void setNotify_by_sms_confirm(String notify_by_sms_confirm)
	{
		this.notify_by_sms_confirm = notify_by_sms_confirm;
	}

	public String getNotify_by_email()
	{
		return notify_by_email;
	}

	public void setNotify_by_email(String notify_by_email)
	{
		this.notify_by_email = notify_by_email;
	}

	public String getNotify_by_email_confirm()
	{
		return notify_by_email_confirm;
	}

	public void setNotify_by_email_confirm(String notify_by_email_confirm)
	{
		this.notify_by_email_confirm = notify_by_email_confirm;
	}

	public String getNotify_by_push_notification()
	{
		return notify_by_push_notification;
	}

	public void setNotify_by_push_notification(String notify_by_push_notification)
	{
		this.notify_by_push_notification = notify_by_push_notification;
	}

	public String getProperty_name()
	{
		return property_name;
	}

	public void setProperty_name(String property_name)
	{
		this.property_name = property_name;
	}

	public String getPropertyValue()
	{
		if (property_name != null)
		{
			switch (CUSTOMER_PROPERTY.valueOf(property_name))
			{
				case id:
					return String.valueOf(id);
				case timeStamp:
					return timeStamp;
				case account_number:
					return account_number;
				case first_name:
					return first_name;
				case middle_name:
					return middle_name;
				case last_name:
					return last_name;
				case contact_phone:
					return contact_phone;
				case home_phone:
					return home_phone;
				case work_phone:
					return work_phone;
				case cell_phone:
					return cell_phone;
				case email:
					return email;
				case notify_by_phone:
					return notify_by_phone;
				case address:
					return address;
				case address_2:
					return address_2;
				case city:
					return city;
				case state:
					return state;
				case zip_postal:
					return zip_postal;
				case notify_by_email:
					return notify_by_email;
				case notify_by_sms:
					return notify_by_sms;
				case notify_by_push_notification:
					return notify_by_push_notification;

				case attrib1:
					return attrib1;
				case attrib2:
					return attrib2;
				case attrib3:
					return attrib3;
				case attrib4:
					return attrib4;
				case attrib5:
					return attrib5;
				case attrib6:
					return attrib6;
				case attrib7:
					return attrib7;
				case attrib8:
					return attrib8;
				case attrib9:
					return attrib9;
				case attrib10:
					return attrib10;
				case attrib11:
					return attrib11;
				case attrib12:
					return attrib12;
				case attrib13:
					return attrib13;
				case attrib14:
					return attrib14;
				case attrib15:
					return attrib15;
				case attrib16:
					return attrib16;
				case attrib17:
					return attrib17;
				case attrib18:
					return attrib18;
				case attrib19:
					return attrib19;
				case attrib20:
					return attrib20;
				
				case contact_phone1:
					return contact_phone1;
				case contact_phone2:
					return contact_phone2;
				case contact_phone3:
					return contact_phone3;
					
				case home_phone1:
					return home_phone1;
				case home_phone2:
					return home_phone2;
				case home_phone3:
					return home_phone3;
					
				case work_phone1:
					return work_phone1;
				case work_phone2:
					return work_phone2;
				case work_phone3:
					return work_phone3;
					
				case cell_phone1:
					return cell_phone1;
				case cell_phone2:
					return cell_phone2;
				case cell_phone3:
					return cell_phone3;
				
				case apptMethod:
					return apptMethod;
				case confNumber:
					return confNumber;
				
				case dob:
					return dob;
				case dob1:
					return dob1;
				case dob2:
					return dob2;
				case dob3:
					return dob3;
				case houseHoldId:
					return houseHoldId;	
				case household_id:
					return household_id;
							
					
				default:
					break;
			}
		}
		return null;
	}

	enum CUSTOMER_PROPERTY
	{
		id,
		account_number,
		first_name,
		middle_name,
		last_name,
		contact_phone,
		contact_phone1,
		contact_phone2,
		contact_phone3,
		home_phone,
		home_phone1,
		home_phone2,
		home_phone3,
		work_phone,
		work_phone1,
		work_phone2,
		work_phone3,
		cell_phone,
		cell_phone1,
		cell_phone2,
		cell_phone3,
		email,
		address,
		address_2,
		city,
		state,
		apptDate,
		zip_postal,
		notify_by_phone,
		notify_by_sms,
		notify_by_email,
		notify_by_push_notification,
		attrib1,
		attrib2,
		attrib3,
		attrib4,
		attrib5,
		attrib6,
		attrib7,
		attrib8,
		attrib9,
		attrib10,
		attrib11,
		attrib12,
		attrib13,
		attrib14,
		attrib15,
		attrib16,
		attrib17,
		attrib18,
		attrib19,
		attrib20,
		timeStamp,
		apptMethod,
		confNumber,
		dob,
		dob1,
		dob2,
		dob3,
		houseHoldId, 
		household_id;

	}

	public String getContact_phone1() {
		if(contact_phone!=null && !"".equals(contact_phone) && contact_phone.length()>0){
			this.contact_phone1 = getPhoneSubPart(contact_phone,0, 3);
		}
		return this.contact_phone1;
	}

	public void setContact_phone1(String contact_phone1) {
		this.contact_phone1 = contact_phone1;
	}

	public String getContact_phone2() {
		if(contact_phone!=null && !"".equals(contact_phone) && contact_phone.length()>0){
			this.contact_phone2 = getPhoneSubPart(contact_phone,3, 6);
		}
		return this.contact_phone2;
	}

	public void setContact_phone2(String contact_phone2) {
		this.contact_phone2 = contact_phone2;
	}

	public String getContact_phone3() {
		if(contact_phone!=null && !"".equals(contact_phone) && contact_phone.length()>0){
			this.contact_phone3 = getPhoneSubPart(contact_phone,6, -1);
		}
		return this.contact_phone3;
	}

	public void setContact_phone3(String contact_phone3) {
		this.contact_phone3 = contact_phone3;
	}

	public String getDob() {
		//dob = (dob!=null && !"".equals(dob)) ? dob : AdminUtils.getSplitedFieldFormData(this,"dob",3,"");
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getApptDate() {
		return apptDate;
	}

	public void setApptDate(String apptDate) {
		this.apptDate = apptDate;
	}

	public String getHome_phone1() {
		return home_phone1;
	}

	public void setHome_phone1(String home_phone1) {
		this.home_phone1 = home_phone1;
	}

	public String getHome_phone2() {
		return home_phone2;
	}

	public void setHome_phone2(String home_phone2) {
		this.home_phone2 = home_phone2;
	}

	public String getHome_phone3() {
		return home_phone3;
	}

	public void setHome_phone3(String home_phone3) {
		this.home_phone3 = home_phone3;
	}

	public String getWork_phone1() {
		return work_phone1;
	}

	public void setWork_phone1(String work_phone1) {
		this.work_phone1 = work_phone1;
	}

	public String getWork_phone2() {
		return work_phone2;
	}

	public void setWork_phone2(String work_phone2) {
		this.work_phone2 = work_phone2;
	}

	public String getWork_phone3() {
		return work_phone3;
	}

	public void setWork_phone3(String work_phone3) {
		this.work_phone3 = work_phone3;
	}

	public String getCell_phone1() {
		return cell_phone1;
	}

	public void setCell_phone1(String cell_phone1) {
		this.cell_phone1 = cell_phone1;
	}

	public String getCell_phone2() {
		return cell_phone2;
	}

	public void setCell_phone2(String cell_phone2) {
		this.cell_phone2 = cell_phone2;
	}

	public String getCell_phone3() {
		return cell_phone3;
	}

	public void setCell_phone3(String cell_phone3) {
		this.cell_phone3 = cell_phone3;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getApptMethod() {
		return apptMethod;
	}

	public void setApptMethod(String apptMethod) {
		this.apptMethod = apptMethod;
	}

	public String getConfNumber() {
		return confNumber;
	}

	public void setConfNumber(String confNumber) {
		this.confNumber = confNumber;
	}

	public String getScheduleIdStr() {
		return scheduleIdStr;
	}

	public void setScheduleIdStr(String scheduleIdStr) {
		this.scheduleIdStr = scheduleIdStr;
	}

	public String[] getBlockedServiceIdsList() {
		return blockedServiceIdsList;
	}

	public void setBlockedServiceIdsList(String[] blockedServiceIdsList) {
		this.blockedServiceIdsList = blockedServiceIdsList;
	}

	public String getBlockedServiceIds() {
		return blockedServiceIds;
	}

	public void setBlockedServiceIds(String blockedServiceIds) {
		this.blockedServiceIds = blockedServiceIds;
	}

	public String getBlockedServices() {
		return blockedServices;
	}

	public void setBlockedServices(String blockedServices) {
		this.blockedServices = blockedServices;
	}

	public String getDob1() {
		return dob1;
	}

	public void setDob1(String dob1) {
		this.dob1 = dob1;
	}

	public String getDob2() {
		return dob2;
	}

	public void setDob2(String dob2) {
		this.dob2 = dob2;
	}

	public String getDob3() {
		return dob3;
	}

	public void setDob3(String dob3) {
		this.dob3 = dob3;
	}

	public String getLiheap_fund() {
		return liheap_fund;
	}

	public void setLiheap_fund(String liheap_fund) {
		this.liheap_fund = liheap_fund;
	}

	public String getPsehelp_fund() {
		return psehelp_fund;
	}

	public void setPsehelp_fund(String psehelp_fund) {
		this.psehelp_fund = psehelp_fund;
	}

	public String getHouseHoldId() {
		return houseHoldId;
	}

	public void setHouseHoldId(String houseHoldId) {
		this.houseHoldId = houseHoldId;
	}

	public String getConf_number() {
		return conf_number;
	}

	public void setConf_number(String conf_number) {
		this.conf_number = conf_number;
	}
	
	private String getPhoneSubPart(String phone, int beginIndex, int endIndex) {
		try{
			if(phone!=null && !"".equals(phone) && phone.length()>0){
				if(phone.contains("-")){
					phone = phone.replaceAll("-", "");
				}
				if(phone.contains("/")){
					phone = phone.replaceAll("/", "");
				}
				if(endIndex==-1){
					endIndex =  phone.length();
				}
				if(phone!=null && !"".equals(phone) && !"--".equals(phone) && phone.length()>=(endIndex)){
					return phone.substring(beginIndex,endIndex);
				}	
			}
		}catch(Exception e){
		}
		return "";
	}

	public String getHousehold_id() {
		return household_id;
	}

	public void setHousehold_id(String household_id) {
		this.household_id = household_id;
	}
	
}