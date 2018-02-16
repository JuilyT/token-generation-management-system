package com.example.assignment.manager;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.exception.TokenGenerationException;
import com.example.assignment.exception.TokenServiceException;
import com.example.assignment.model.BankService;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.ServiceType;
import com.example.assignment.model.Token;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.ServiceRequestRepository;
import com.example.assignment.repository.ServiceTypeRepository;
import com.example.assignment.repository.ServicesRepository;

@Service
public class ServiceManagerImpl implements ServiceManager {
	@Autowired
	ServiceRequestRepository requestRepository;
	@Autowired
	ServicesRepository servicesRepository;
	@Autowired
	ServiceTypeRepository serviceTypeRepository;
	@Autowired
	TokenGenerator tokenGenerator;
	
	/**
	 * Gets the services provided by bank by id.
	 */
	@Override
	public BankService getBankServiceById(final int id) {
		BankService s = servicesRepository.findOne(id);
		if(null == s) {
			throw new IllegalArgumentException("No service found for id :"+ id);
		}
		return s;
	}
	
	/**
	 * Gets the service requests by customer by id.
	 */
	@Override
	public ServiceRequest getRequestById(final int id) {
		ServiceRequest req = requestRepository.findOne(id);
		if(null == req) {
			throw new IllegalArgumentException("No serviceRequest found for id :"+ id);
		}
		return req;
	}
	
	/**
	 * Fetches the current service task out of request by id. 
	 */
	@Override
	public ServiceType getActiveServiceTypeForRequest(final int id) {
		ServiceRequest req = getRequestById(id);
		final int currentTXIndex = req.getTransactionIndex();
		
		return req.getService().getTransactions().get(currentTXIndex);
	}
	
	@Override
	public ServiceType getServiceTypeById(final int id) {
		ServiceType s = serviceTypeRepository.findOne(id);
		if(null == s) {
			throw new IllegalArgumentException("No ServiceType found for id :"+ id);
		}
		return s;
	}

	/**
	 * Generates token based on service requested by customer.
	 */
	@Override
	@Transactional
	public Token generateTokenForServiceRequest(ServiceRequest request) throws TokenServiceException {
		Token token = null;
		try {
			token = tokenGenerator.generateToken(request);
		} catch (TokenGenerationException e) {
			throw new TokenServiceException(e.getMessage());
		}
		return token;		
	}
}
