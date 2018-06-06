package com.telappoint.apptadmin.form.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.telappoint.apptadmin.constants.AdminApptValidationConstants;
import com.telappoint.apptadmin.form.CustomerTO;
import com.telappoint.apptadmin.model.RegistrationDetails;
import com.telappoint.apptadmin.utils.AdminUtils;

/** 
 * @author Murali
 */

@Service
public class CustomerFormValidatorImpl  {
	private Logger logger = Logger.getLogger(CustomerFormValidatorImpl.class);

	public boolean supports(Class<?> clazz) {
		return CustomerTO.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		// LoginForm loginForm = (LoginForm) target;
	}

	/*Method to validate Registration Form*/
	public String validate(Object target,List<RegistrationDetails> registrationDetailsList) {
		StringBuilder validationResponse = new StringBuilder();
		CustomerTO customerTO = (CustomerTO) target;
		String fieldValueStr = null;
		String maxChars = null;
		String minChars = null;
		if (null==registrationDetailsList) return "Error while validating Appointment Form";
		try {
			/*Iterating through RegistrationDetails list*/
			for (RegistrationDetails registrationDetails : registrationDetailsList) {
				maxChars = registrationDetails.getValidateMaxChars();
				minChars = registrationDetails.getValidateMinValue();
				String displayType = registrationDetails.getDisplayType();
				String emptyErrorMsg = registrationDetails.getEmptyErrorMessage();
				String invalidErrorMsg = registrationDetails.getInvalidErrorMessage();
				String fieldName = registrationDetails.getParamColumn();
				
				/*If display type not a button then only it will processes*/
				if (displayType != null && !displayType.contains("button")) {
					Object fieldValue = AdminUtils.getPropertyValue(customerTO, fieldName);
					
					if(registrationDetails.getValidationRules().contains("phone") ||
							registrationDetails.getValidationRules().contains("dob")) {
						String fieldVal = "";
						Object fieldVal1 = AdminUtils.getPropertyValue(customerTO, fieldName+"1");
						Object fieldVal2 = AdminUtils.getPropertyValue(customerTO, fieldName+"2");
						Object fieldVal3 = AdminUtils.getPropertyValue(customerTO, fieldName+"3");
						if(null!=fieldVal1){
							fieldVal = fieldVal + (String)fieldVal1;
						}
						if(null!=fieldVal2){
							fieldVal = fieldVal + (String)fieldVal2;
						}
						if(null!=fieldVal3){
							fieldVal = fieldVal + (String)fieldVal3;
						}
						if(fieldVal!=null && !"".equals(fieldVal) && fieldVal.length()>0){
							fieldValue = fieldVal;	
						}else {
							fieldValue = null;
						}
					}
										
					if(fieldValue!=null) {
						String validateFieldValue = fieldValue.toString().replaceAll(AdminApptValidationConstants.HYPHEN.getValidateExp(), "");
						boolean isValidReq = ((registrationDetails.getLoginType().equals("login")) 
								|| (registrationDetails.getLoginType().equals("registration")) 
								|| (registrationDetails.getLoginType().equals("update")) 
								|| (registrationDetails.getLoginType().equals("nologin"))
								|| ("Y".equalsIgnoreCase(registrationDetails.getRequired())))?true:false;
						
						if(registrationDetails.getLoginType().equals("update") && validateFieldValue.equals("") && !"Y".equalsIgnoreCase(registrationDetails.getRequired())){
							continue;
						}
					
						/*Checking that validation is required or not for this field */
						if(isValidReq) {
							if(displayType.contains("select")){
								if (fieldValue instanceof String) {
									fieldValueStr = (String) fieldValue;
								}
								if (fieldValueStr == null || "".equals(fieldValueStr.trim())) {
									validationResponse.append(emptyErrorMsg);
									validationResponse.append("<br/>");
								}else{
									if ("-1".equals(fieldValueStr.trim())) {
										validationResponse.append(invalidErrorMsg);
										validationResponse.append("<br/>");
									}
								}
							}else if(displayType.contains("radio")){
								
								if (fieldValue instanceof String) {
									fieldValueStr = (String) fieldValue;
								}
								if (fieldValueStr == null || "".equals(fieldValueStr.trim())) {
									validationResponse.append(emptyErrorMsg);
									validationResponse.append("<br/>");
								}
							}else {
								if ("Y".equalsIgnoreCase(registrationDetails.getValidationRequired())) {
									
									if (displayType != null && displayType.contains("textbox-")) {
										if (fieldValue instanceof String) {
											fieldValueStr = (String) fieldValue;
										}
									
										if(registrationDetails.getValidationRules().contains("phone")) {
											if ("".equals(fieldValueStr.trim()) || fieldValueStr == null) {
												validationResponse.append(emptyErrorMsg);
												validationResponse.append("<br/>");
											}else{
												check(fieldValue.toString(), AdminApptValidationConstants.PHONE.getValidateExp(), fieldName, invalidErrorMsg, validationResponse);
											}
										}
										else if ( minChars != null && fieldValueStr.length() < Integer.parseInt(minChars) ) {
											validationResponse.append(invalidErrorMsg);
											validationResponse.append("<br/>");
										}
										else if(registrationDetails.getValidationRules().contains("numeric")) {
											check(fieldValue.toString(), AdminApptValidationConstants.NUMERIC.getValidateExp(), fieldName, invalidErrorMsg, validationResponse);
										}else if(registrationDetails.getValidationRules().contains("dob")) {
											check(fieldValue.toString(), AdminApptValidationConstants.NUMERIC.getValidateExp(), fieldName, invalidErrorMsg, validationResponse);
											validateDOBDate(fieldValue.toString(), fieldName, invalidErrorMsg, validationResponse);
										}
									} else if(!(fieldName.equals("date") || fieldName.equals("Date&Time"))) {
										if (fieldValue instanceof String) {
											fieldValueStr = (String) fieldValue;
										}
										if ("".equals(fieldValueStr.trim()) || fieldValueStr == null) {
											validationResponse.append(emptyErrorMsg);
											validationResponse.append("<br/>");
										} 
										else if ( minChars != null && fieldValueStr.length() < Integer.parseInt(minChars) ) {
											validationResponse.append(invalidErrorMsg);
											validationResponse.append("<br/>");
										}
										else if(maxChars != null && fieldValueStr.length() > Integer.parseInt(maxChars)) {
											validationResponse.append(invalidErrorMsg);
											validationResponse.append("<br/>");
										} else {
											boolean isValid = FormValidationUtils.validateFieldValue(registrationDetails.getValidationRules(),fieldValue.toString());
											if(!isValid) {
												validationResponse.append(invalidErrorMsg);
												validationResponse.append("<br/>");
											}
										}
									}
								}
							}
						}
					}else{
						//if(displayType.contains("radio")){	
							if("Y".equalsIgnoreCase(registrationDetails.getRequired())) {
								if (fieldValue == null) {
									validationResponse.append(emptyErrorMsg);
									validationResponse.append("<br/>");
								}
							}
						//}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error:" + e, e);
		}
		return validationResponse.toString();
	}
	
	
	private void check(String fieldValue, String reg, String fieldName, String message, StringBuilder validationResult) {
		fieldValue = fieldValue.replaceAll(AdminApptValidationConstants.HYPHEN.getValidateExp(), "");
		
		if(!fieldValue.matches(reg)) {
			validationResult.append(message);
		}
	}
	
	private void validateDOBDate(String fieldValue, String fieldName, String message,StringBuilder validationResponse) {
		if(fieldValue!=null && fieldValue.length()==8) { //considering 8 characters
			String datePattern = "[0-9]{8}+";
		    boolean isValid = fieldValue.matches(datePattern);
		    if(isValid){	
				String MMDDYYYY_DATE_FORMAT = "MMddyyyy";
				Date dob = null;
				try {
					DateFormat dateFormat = new SimpleDateFormat(MMDDYYYY_DATE_FORMAT);
					dob = dateFormat.parse(fieldValue);
					
					Date today = new Date();
					if(today.after(dob)){
						//nothing to do
					}else{
						validationResponse.append(message);
						validationResponse.append("<br/>");
					}				
				} catch (Exception e) {
					validationResponse.append(message);
					validationResponse.append("<br/>");
				}
		    }else{
				validationResponse.append(message);
				validationResponse.append("<br/>");
			}
		}else{
			validationResponse.append(message);
			validationResponse.append("<br/>");
		}
	}

}
