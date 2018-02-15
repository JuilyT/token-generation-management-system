package com.example.assignment.service;

import com.example.assignment.model.Customer;
import com.example.assignment.model.Token;

public interface TokenService {
	/**
	 * Creates token based on customer's priority and service requested
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public Token process(Customer customer) throws Exception;
	
	/**
	 * Gets customer info based on Id.
	 * @param counterId
	 * @return
	 */
	Customer getCustomerById(int id);
}
