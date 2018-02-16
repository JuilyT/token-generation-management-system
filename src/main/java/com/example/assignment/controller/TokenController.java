package com.example.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.manager.ServiceManager;
import com.example.assignment.model.BankService;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.example.assignment.service.TokenService;

@RestController
@RequestMapping(value="/rest/api/banking/token")
public class TokenController {
	@Autowired
	TokenService tokenService;
	@Autowired
	ServiceManager serviceManager;
	
	 /**
	  * Generated token based on customer's priority and service asked by customer.
	  * @param customer
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value="/users/services/{serviceId}", method=RequestMethod.POST)
	 public Token processService(@PathVariable int serviceId,
			 @RequestBody Customer customer) throws Exception {	
		 if(CollectionUtils.isEmpty(customer.getServiceRequests())) {
			 throw new IllegalArgumentException("Services shouldn't be empty");
		 }
		 return tokenService.process(customer);
	 }
	 
	 @RequestMapping(value="/users/{userId}/services/{serviceId}", method=RequestMethod.POST)
	 public Token processService(
			 @PathVariable int userId, @PathVariable int serviceId
			 ) throws Exception {
		 Customer cust = tokenService.getCustomerById(userId);
		 BankService s = serviceManager.getBankServiceById(serviceId);
		 
		 ServiceRequest req = new ServiceRequest();
		 req.setService(s);
		 req.setCustomer(cust);
		 cust.setActiveRequest(req);
		 
		 return tokenService.process(cust);
	 }
}
