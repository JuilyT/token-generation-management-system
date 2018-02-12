package com.example.assignment.operations;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.assignment.model.Token;
import com.example.assignment.repository.CustomerRepository;

@Component
public class AccountCreationCounterOperator extends AbstractCounterOperatorImpl {

	@Autowired
	CustomerRepository customerRepository;

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TokenGenerator.class);

	@Override
	protected void performSpecificTask(Token activeToken) {}
}
