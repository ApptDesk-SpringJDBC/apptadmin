package com.telappoint.apptadmin.controller;

import com.telappoint.apptadmin.common.controller.ApptAdminHelperController;
import com.telappoint.apptadmin.form.HomeBean;
import com.telappoint.apptadmin.model.*;
import com.telappoint.apptadmin.service.ApptAdminService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class CustomerController extends ApptAdminHelperController {

	private final Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private ApptAdminService apptAdminService;

	@RequestMapping(value="saveCustomer", method = RequestMethod.POST)
	@ResponseBody
	public SaveCustomerResponse saveCustomer(@ModelAttribute("homeBean") final HomeBean homeBean,
											 final HttpServletRequest request,
											 @RequestParam final String account_number, //SSN
											 @RequestParam(required = false) final String bookApptCustomerId, //CustomerId
											 @RequestParam final String first_name,
											 @RequestParam final String last_name,
											 @RequestParam final String contact_phone_1,
											 @RequestParam final String contact_phone_2,
											 @RequestParam final String contact_phone_3,
											 @RequestParam final String email,
											 @RequestParam final String attrib1, //Energy Acct#
											 @RequestParam final String dob_1,
											 @RequestParam final String dob_2,
											 @RequestParam final String dob_3
	) throws Exception {

		SaveCustomerResponse response = null;

		final String contactPhone = new StringBuilder("").append(contact_phone_1).append(contact_phone_2).append(contact_phone_3).toString();
		final String dob = new StringBuilder("").append(dob_1).append('/').append(dob_2).append('/').append(dob_3).toString();
		final Customer customer = new Customer();
		if(StringUtils.isNotBlank(bookApptCustomerId)){
			customer.setCustomerId(Long.parseLong(bookApptCustomerId));
		}
		customer.setSsn(account_number);
		customer.setAccountNumber(account_number);
		customer.setFirstName(first_name);
		customer.setLastName(last_name);
		customer.setEmail(email);
		customer.setAttrib1(attrib1);
		customer.setContactPhone(contactPhone);
		customer.setDob(dob);
		final CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setCustomer(customer);
		response = apptAdminService.createOrUpdateCustomer(homeBean, customerRequest);
		if(!Objects.isNull(response)){
			logger.debug(response.toString());

			if(response.isStatus() && response.getMessage() == null){
				response.setMessage("Success");
			}
		}
		return response;
	}

	@RequestMapping(value="getCustomerById", method = RequestMethod.GET)
	@ResponseBody
	public Customer getCustomerById(@ModelAttribute("homeBean") final HomeBean homeBean, @RequestParam final String customerId) throws Exception {
		final CustomersResponse customersResponse = apptAdminService.getCustomersById(homeBean, customerId);
		final Customer customer = customersResponse.getCustomerList().stream().findFirst().get();
		return customer;
	}
}
