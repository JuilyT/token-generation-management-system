package com.example.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.manager.ServiceManager;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.example.assignment.operations.OperatorSelector;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;

/**
 * Service for token operations
 * @author juilykumari
 *
 */
@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	TokenGenerator tokenGenerator;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ServiceRequestRepository requestRepository;
	@Autowired
	ServiceManager serviceManager;
	
	/**
	 * Creates token based on customer's priority and service requested
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@Override
	public Token process(Customer customer) throws Exception {
		 if(customer.isNewCustomer()) {
			 customerRepository.save(customer);
		 }
		 requestRepository.save(customer.getActiveRequest());
		 return serviceManager.generateTokenForServiceRequest(customer.getActiveRequest());
	 }
	 
	 @Override
		public Customer getCustomerById(final int id) {
			Customer cust = customerRepository.findOne(id);
			if(null == cust) {
				throw new IllegalArgumentException("No customer found for id :"+ id);
			}
			return cust;
		}
}
