package com.telappoint.apptadmin.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 
 * @author Balaji 
 *
 */
@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Customer {
	private Long customerId;
	private Long houseHoldId;
	public String ssn;              //SSN and accountNumber both same.  But in some context we receive SSN from backend rest services
	private String accountNumber;   //in some context we receive accountNumber from backend rest services.  So keeping both
	public String firstName;
	public String lastName;
	public String contactPhone;
	public String email;
	public String attrib1;
	public String dob;
	public String address;
	public String city;
	public String state;
	public String zipCode;
	private String blockedFlag;
	private String name; // for autosuggest dropdown
	
	@JsonIgnore
	public String contactPhone1;
	@JsonIgnore
	public String contactPhone2;
	@JsonIgnore
	public String contactPhone3;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}
	
	public String toString1() {
		ObjectMapper ow = new ObjectMapper();
		String json = null;
		try {
			json = ow.writeValueAsString(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"blockedFlag='" + blockedFlag + '\'' +
				", zipCode='" + zipCode + '\'' +
				", state='" + state + '\'' +
				", city='" + city + '\'' +
				", address='" + address + '\'' +
				", dob='" + dob + '\'' +
				", attrib1='" + attrib1 + '\'' +
				", email='" + email + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				", lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				", accountNumber='" + accountNumber + '\'' +
				", ssn='" + ssn + '\'' +
				", houseHoldId=" + houseHoldId +
				", customerId=" + customerId +
				'}';
	}

	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getAttrib1() {
		return attrib1;
	}
	public void setAttrib1(String attrib1) {
		this.attrib1 = attrib1;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Long getHouseHoldId() {
		return houseHoldId;
	}
	public void setHouseHoldId(Long houseHoldId) {
		this.houseHoldId = houseHoldId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getBlockedFlag() {
		return blockedFlag;
	}
	public void setBlockedFlag(String blockedFlag) {
		this.blockedFlag = blockedFlag;
	}

	public String getContactPhone1() {
		return contactPhone1;
	}

	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}

	public String getContactPhone2() {
		return contactPhone2;
	}

	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}

	public String getContactPhone3() {
		return contactPhone3;
	}

	public void setContactPhone3(String contactPhone3) {
		this.contactPhone3 = contactPhone3;
	}
}
