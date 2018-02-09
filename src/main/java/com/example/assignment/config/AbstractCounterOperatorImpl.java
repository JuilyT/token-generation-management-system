package com.example.assignment.config;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.assignment.TokenGenerator;
import com.example.assignment.enums.Status;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;
import com.example.assignment.repository.TokenRepository;

public abstract class AbstractCounterOperatorImpl implements CounterOperator {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TokenGenerator.class);
	@Autowired
	TokenGenerator tokenGenerator;
	
	@Autowired
	CounterRepository counterRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	TokenRepository tokenRepository;
	@Autowired
	ServiceRequestRepository requestRepository;

	@Override
	public void operate(Counter counter) throws Exception {
		Token activeToken = counter.getActiveToken();
		if(activeToken == null) {
			LOGGER.info("Queue is empty now");
			return;
		}
		
		performSpecificTask(activeToken);
		completeToken(counter);
	}
	
	private void completeToken(Counter counter) throws Exception{
		final Token activeToken = counter.getActiveToken();
		final Customer customer = activeToken.getRequest().getCustomer();
		
		markActiveTokenCompleted(activeToken);
		
		
		ServiceRequest nextCustomerRequest = requestRepository.getActiveServiceRequestForCustomer(customer.getId());
		customer.setActiveRequest(nextCustomerRequest);
		counterRepository.save(counter);
	

		if (nextCustomerRequest==null) {
			 LOGGER.info("No service to serve for this customer");
			 return;
		} else {
			tokenGenerator.generateToken(nextCustomerRequest);
		}
	}
	
	private void markActiveTokenCompleted(Token activeToken) {
		activeToken.setStatus(Status.COMPLETED);
		activeToken.setLastUpdated(new Timestamp(System.currentTimeMillis()));
		activeToken.setCounter(null);
		tokenRepository.save(activeToken);
		
		
		ServiceRequest request = activeToken.getRequest();
		request.setCompletionTime(new Timestamp(System.currentTimeMillis()));
		request.setStatus(Status.COMPLETED);
		requestRepository.save(request);
	}

	protected abstract void performSpecificTask(Token activeToken);

}
