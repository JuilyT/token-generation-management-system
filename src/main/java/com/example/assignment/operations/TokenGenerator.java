package com.example.assignment.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.assignment.Initializer;
import com.example.assignment.exception.TokenGenerationException;
import com.example.assignment.manager.ServiceManager;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.ServiceType;
import com.example.assignment.model.Token;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.TokenRepository;

/**
 * To generate token based on service requested by Customer
 * @author juilykumari
 *
 */
@Component
public class TokenGenerator {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TokenGenerator.class);
	@Autowired
	CounterRepository counterRepository;
	@Autowired
	TokenRepository tokenRepository;
	@Autowired
	ServiceManager serviceManager;	
	@Autowired
	OperatorSelector operatorSelector;
	@Autowired
	Initializer initializer;

	/**
	 * Generates token as per customer request.
	 * @param serviceRequest
	 * @return
	 * @throws Exception
	 */
	public Token generateToken(ServiceRequest serviceRequest) throws TokenGenerationException {
		if (null == serviceRequest) {
			LOGGER.info("No service to serve for this customer");
			throw new TokenGenerationException("No service to serve for this customer");
		}
		
		Token token = getTokenForRequest(serviceRequest); 
		token.setCounter(assignTokenToCounter(token, serviceRequest.getCustomer()));
		tokenRepository.save(token);
		LOGGER.info("Token :{} generated for request: {}", new Object[] { token.toString(), serviceRequest });
		return token;
	}

	/*
	 * Fetching customer's service request for token generation from db.
	 */
	private Token getTokenForRequest(ServiceRequest serviceRequest) {
		Token existing = tokenRepository.findByRequest(serviceRequest);
		if (null == existing) {
			existing = Token.getTokenForCustomer(serviceRequest);
		}
		existing.setCounter(null);
		return existing;
	}

	/*
	 * Assigns generated token to service counter as per availability.
	 */
	private Counter assignTokenToCounter(Token token, Customer customer) throws TokenGenerationException {
		Counter minCounter = getCounterWithMinRank(customer);

		int minIndex = minCounter.getRank();
		if (minIndex == 0 && minCounter.getTokens() == null) {
			List<Token> counterTokens = new ArrayList<>();
			counterTokens.add(minIndex, token);
			minCounter.setTokens(counterTokens);
		} else {
			minCounter.getTokens().add(minIndex, token);
		}
		LOGGER.info("Assigning token :{} to counter: {}", new Object[] { token.toString(), minCounter.toString() });
		return minCounter;
	}

	/*
	 * Evaluates min rank for each counter as customer's service token and returns the most available counter.
	 */
	private Counter getCounterWithMinRank(Customer customer) throws TokenGenerationException {
		
		ServiceType type = serviceManager.getActiveServiceTypeForRequest(customer.getActiveRequest().getId());
		List<Counter> serviceCounters = counterRepository.findByServiceTypeAndPriority(type.getId(), customer.getAccountType());
		if (CollectionUtils.isEmpty(serviceCounters)) {
			LOGGER.info("No counter available for this service for now. Please try again.");
			throw new TokenGenerationException("No counter available for this service for now. Please try again.");
		}

		if (serviceCounters.size() == 1) {
			return serviceCounters.get(0);
		}

		Map<Counter, Integer> counterRankMap = new HashMap<>();
		for (Counter counter : serviceCounters) {
			counterRankMap.put(counter, counter.getRank());
		}
		Entry<Counter, Integer> min = null;
		for (Entry<Counter, Integer> entry : counterRankMap.entrySet()) {
			if (min == null || min.getValue() > entry.getValue()) {
				min = entry;
			}
		}

		return min.getKey();
	}
}
