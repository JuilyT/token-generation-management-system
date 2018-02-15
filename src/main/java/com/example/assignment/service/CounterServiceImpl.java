package com.example.assignment.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.manager.ServiceManager;
import com.example.assignment.model.Counter;
import com.example.assignment.operations.OperatorSelector;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;

@Service
public class CounterServiceImpl implements CounterService{
	public static final int LENGTH = 12;
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CounterServiceImpl.class);
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
	@Autowired
	ServiceManager serviceManager;
	
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
	public List<Counter> getServiceCounters(int serviceTypeId) {
		return counterRepository.findByServiceType(serviceManager.getServiceTypeById(serviceTypeId));
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
