package com.example.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.assignment.model.Counter;

@Component
public class OperatorSelector {

	@Autowired
	AccountCreationCounterOperator accountCreationCounterOperator;
	
	@Autowired
	DepositCounterOperator depositCounterOperator;
	
	@Autowired
	WithdrawCounterOperator withdrawCounterOperator;
	
	
	public CounterOperator getOperator(Counter counter) {
		switch (counter.getServiceType()) {
		case DEPOSIT:
			return depositCounterOperator;
		case ACCOUNT_CREATION:
			return accountCreationCounterOperator;
		case WITHDRAWL:
			return withdrawCounterOperator;

		default:
			throw new IllegalArgumentException("Currently counter support only CREATE/WITHDRAW/DEPOSIT");
		}
	}
}
