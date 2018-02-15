package com.example.assignment.operations;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.assignment.enums.Status;
import com.example.assignment.manager.ServiceManager;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;
import com.example.assignment.repository.TokenRepository;

/**
 * Concrete class to perform operations by counter and process token.
 * @author juilykumari
 *
 */
public abstract class AbstractCounterOperatorImpl implements CounterOperator {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TokenGenerator.class);
	@Autowired
	TokenGenerator tokenGenerator;
	@Autowired
	ServiceManager serviceManager;
	@Autowired
	CounterRepository counterRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	TokenRepository tokenRepository;
	@Autowired
	ServiceRequestRepository requestRepository;

	@Override
	@Transactional
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
		final ServiceRequest request = customer.getActiveRequest();
		request.setTransactionIndex(request.getTransactionIndex() + 1);
		
		requestRepository.save(request);
		
		if (request.getTransactionIndex() == request.getService().getTransactions().size()) {
			LOGGER.debug("*************ALL THE TASKS mentioned in the REQUEST have been COMPLETED **************");
			LOGGER.debug("1. Mark Token as completed");
			LOGGER.debug("1. Mark ServiceRequest as completed");
			
			markActiveTokenCompleted(activeToken);
			return;
		}
		activeToken.setCounter(null);
		tokenRepository.save(activeToken);
		try {
			serviceManager.generateTokenForServiceRequest(request);
		} catch (Exception e) {
			LOGGER.debug("Error occured while generating token");
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
