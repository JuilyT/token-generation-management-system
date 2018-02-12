package com.example.assignment.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.assignment.Initializer;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
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
	OperatorSelector operatorSelector;
	@Autowired
	Initializer initializer;

	public Token generateToken(ServiceRequest serviceRequest) throws Exception {
		if (serviceRequest == null) {
			LOGGER.info("No service to serve for this customer");
			throw new IllegalArgumentException("No service to serve for this customer");
		}
		
		Token token = Token.getTokenForCustomer(serviceRequest);
		token.setCounter(assignTokenToCounter(token, serviceRequest.getCustomer()));
		tokenRepository.save(token);
		LOGGER.info("Token :{} generated for request: {}", new Object[] { token.toString(), serviceRequest });
		return token;
	}

	private Counter assignTokenToCounter(Token token, Customer customer) throws Exception {

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

	private Counter getCounterWithMinRank(Customer customer) throws Exception {

		List<Counter> serviceCounters = counterRepository.findByServiceTypeAndPriority(customer.getActiveService(),
				customer.getAccountType());
		if (serviceCounters.isEmpty()) {
			LOGGER.info("No counter available for this service for now. Please try again.");
			throw new Exception("No counter available for this service for now. Please try again.");
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
