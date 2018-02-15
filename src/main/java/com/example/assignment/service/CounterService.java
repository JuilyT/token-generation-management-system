package com.example.assignment.service;

import java.util.List;
import java.util.Map;

import com.example.assignment.model.Counter;

public interface CounterService {
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
	public List<Counter> getServiceCounters(int txId);

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
