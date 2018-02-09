package com.example.assignment.config;

import java.util.List;
import java.util.Map;

import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.Token;

public interface BankingService {
	public List<Counter> getCounters();

	public Map<String, Counter> addCounters();

	public List<Counter> getServiceCounters(ServiceType type);

	public Token process(Customer customer) throws Exception;

	public void operate(int counterId) throws Exception;

	Counter getCounterById(int counterId);
}
