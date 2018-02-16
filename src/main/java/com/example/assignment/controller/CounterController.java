package com.example.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.exception.CounterServiceException;
import com.example.assignment.model.Counter;
import com.example.assignment.service.CounterService;

@RestController
@RequestMapping(value="/rest/api/banking/counters")
public class CounterController {
	@Autowired
	CounterService counterService;
		
	/**
	 * Get status of all counters in DB
	 * @return
	 */
	 @RequestMapping(method=RequestMethod.GET)
	 public List<Counter> getCounters() {
		 return counterService.getCounters();
	 }

	 /**
	  * fetches the list of counters available for particular service type 
	  * @param type
	  * @return
	  */
	 @RequestMapping(value="/counters/type", method=RequestMethod.GET)
	 public List<Counter> getServiceCounters(@RequestParam int typeId) {
		 return counterService.getServiceCounters(typeId);
	 }

	 /**
	  * Gets the counter info based on ID passed as parameter.
	  * @param counterId
	  * @return
	 * @throws CounterServiceException 
	  */
	 @RequestMapping(value="/{id}", method=RequestMethod.GET)
	 public Counter getSingleCounter(@PathVariable("id") int counterId) throws CounterServiceException {
		 if (counterId < 1) {
			 throw new IllegalArgumentException("Invalid counter");
		 }
		 return counterService.getCounterById(counterId);
	 }
	 
	 /**
	  * Used by counter operator to operate on service token present in the queue.
	  * @param counterId
	  * @throws Exception
	  */
	 @RequestMapping(value="/{id}/operate", method=RequestMethod.PUT)
	 public void operateCounter(@PathVariable("id") int counterId) throws Exception {	
		 counterService.operate(counterId);
	 }
}
