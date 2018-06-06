package com.telappoint.apptadmin.model;

/**
 * Created by pepala on 20/5/17.
 *
 */
public class VerifyPageData {

    private String lastName;

    private String contactPhone;

    private String timeZone;

    private String locationName;

    private String procedureName;

    private String departmentName;

    private String serviceName;

    private String accountNumber;

    private String email;

    private String apptDateTime;

    private String firstName;

    private String attrib1;

    private String resourceName;

    private String listOfDocsToBring;

    private String comments;

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getContactPhone ()
    {
        return contactPhone;
    }

    public void setContactPhone (String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getTimeZone ()
    {
        return timeZone;
    }

    public void setTimeZone (String timeZone)
    {
        this.timeZone = timeZone;
    }

    public String getLocationName ()
    {
        return locationName;
    }

    public void setLocationName (String locationName)
    {
        this.locationName = locationName;
    }

    public String getProcedureName ()
    {
        return procedureName;
    }

    public void setProcedureName (String procedureName)
    {
        this.procedureName = procedureName;
    }

    public String getDepartmentName ()
    {
        return departmentName;
    }

    public void setDepartmentName (String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getServiceName ()
    {
        return serviceName;
    }

    public void setServiceName (String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getAccountNumber ()
    {
        return accountNumber;
    }

    public void setAccountNumber (String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getApptDateTime ()
    {
        return apptDateTime;
    }

    public void setApptDateTime (String apptDateTime)
    {
        this.apptDateTime = apptDateTime;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getAttrib1 ()
    {
        return attrib1;
    }

    public void setAttrib1 (String attrib1)
    {
        this.attrib1 = attrib1;
    }

    public String getResourceName ()
    {
        return resourceName;
    }

    public void setResourceName (String resourceName)
    {
        this.resourceName = resourceName;
    }

    public String getListOfDocsToBring ()
    {
        return listOfDocsToBring;
    }

    public void setListOfDocsToBring (String listOfDocsToBring)
    {
        this.listOfDocsToBring = listOfDocsToBring;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "VerifyPageData{" +
                "lastName='" + lastName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", locationName='" + locationName + '\'' +
                ", procedureName='" + procedureName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", email='" + email + '\'' +
                ", apptDateTime='" + apptDateTime + '\'' +
                ", firstName='" + firstName + '\'' +
                ", attrib1='" + attrib1 + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", listOfDocsToBring='" + listOfDocsToBring + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}