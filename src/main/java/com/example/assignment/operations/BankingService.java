package com.example.assignment.operations;

import java.util.List;
import java.util.Map;

import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.Token;

public interface BankingService {
	/**
	 * Returns list of all counters available for multiple services along with list of token assigned.
	 * 
	 * @return List<Counter>
	 */
	public List<Counter> getCounters();

	/**
	 * To add counter info for services in branch.
	 * @return
	 */
	public Map<String, Counter> addCounters();

	/**
	 * Returns the list of counters based on type of service those are offering
	 * @param type
	 * @return
	 */
	public List<Counter> getServiceCounters(ServiceType type);

	/**
	 * Creates token based on customer's priority and service requested
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public Token process(Customer customer) throws Exception;

	/**
	 * Used by counter operator to operate on each service token present in queue.
	 * @param counterId
	 * @throws Exception
	 */
	public void operate(int counterId) throws Exception;

	/**
	 * Gets counter info based on Id.
	 * @param counterId
	 * @return
	 */
	Counter getCounterById(int counterId);
}
