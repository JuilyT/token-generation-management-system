package com.example.assignment.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.Token;

@RestController
@RequestMapping(value="/rest/api/banking")
public class Controller {
	@Autowired
	BankingService service;
		
	/**
	 * Get status of all counters in DB
	 * @return
	 */
	 @RequestMapping(value="/counters", method=RequestMethod.GET)
	 public List<Counter> getCounters() {
		 return service.getCounters();
	 }

	 /**
	  * fetches the list of counters available for particular service type 
	  * @param type
	  * @return
	  */
	 @RequestMapping(value="/counters/type", method=RequestMethod.GET)
	 public List<Counter> getServiceCounters(@RequestParam ServiceType type) {
		 return service.getServiceCounters(type);
	 }

	 /**
	  * Generated token based on customer's priority and service asked by customer.
	  * @param customer
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value="/token", method=RequestMethod.POST)
	 public Token processService(@RequestBody Customer customer) throws Exception {	
		 if(CollectionUtils.isEmpty(customer.getServiceRequests())) {
			 throw new IllegalArgumentException("Services shouldn't be empty");
		 }
		 return service.process(customer);
	 }
	 
	 /**
	  * Gets the counter info based on ID passed as parameter.
	  * @param counterId
	  * @return
	  */
	 @RequestMapping(value="/counters/{id}", method=RequestMethod.GET)
	 public Counter getSingleCounter(@PathVariable("id") int counterId) {
		 if (counterId < 1) {
			 throw new IllegalArgumentException("Invalid counter");
		 }
		 return service.getCounterById(counterId);
	 }
	 
	 /**
	  * Used by counter operator to operate on service token present in the queue.
	  * @param counterId
	  * @throws Exception
	  */
	 @RequestMapping(value="/counters/{id}/operate", method=RequestMethod.PUT)
	 public void operateCounter(@PathVariable("id") int counterId) throws Exception {	
		 service.operate(counterId);
	 }
}
