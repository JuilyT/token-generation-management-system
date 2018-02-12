package com.example.assignment.operations;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;

@Service
public class BankingServiceImpl implements BankingService{
	public static final int LENGTH = 12;
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BankingServiceImpl.class);
	@Autowired
	TokenGenerator tokenGenerator;
	@Autowired
	OperatorSelector operatorSelector;
	@Autowired
	CounterRepository counterRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ServiceRequestRepository requestRepository;
	
	@Override
	public List<Counter> getCounters() {
		return counterRepository.findAllByOrderByIdAsc();
	}
	
	/**
	 * Gets counter info based on Id.
	 * @param counterId
	 * @return
	 */
	@Override
	public Counter getCounterById(int counterId) {
		Counter counter = counterRepository.findOne(counterId);
		 if(counter == null) {
			 LOGGER.error("No counter found for id:{}", counterId);
			 throw new IllegalArgumentException("No counter found for id:"+ counterId);
		 }
		 return counter;
	}
	
	@Override
	public Map<String, Counter> addCounters() {
		return null;
	}

	/**
	 * Returns the list of counters based on type of service those are offering
	 * @param type
	 * @return
	 */
	@Override
	public List<Counter> getServiceCounters(ServiceType type) {
		return counterRepository.findByServiceType(type);
	}
	
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
		 saveServices(customer);
		 ServiceRequest nextCustomerRequest = requestRepository.getActiveServiceRequestForCustomer(customer.getId());
		 customer.setActiveRequest(nextCustomerRequest);
		 customerRepository.save(customer);
		 
		 return tokenGenerator.generateToken(nextCustomerRequest);
	 }

	 private void saveServices(Customer customer) {
		 List<ServiceRequest> requests = customer.getServiceRequests();
			for(ServiceRequest request : requests) {
				request.setCustomer(customer);
			}
			requestRepository.save(requests);
	 }

	 /**
	 * Used by counter operator to operate on each service token present in queue.
	 * @param counterId
	 * @throws Exception
	 */
	@Override
	public void operate(final int counterId) throws Exception {
		 Counter counter = getCounterById(counterId);
		 operatorSelector.getOperator(counter).operate(counter);
	}
}
