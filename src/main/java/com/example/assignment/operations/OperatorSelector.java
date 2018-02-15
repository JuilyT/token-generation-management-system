package com.example.assignment.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.assignment.model.Counter;

/**
 * Based on serviceType, customer has asked for, returns particular service counter operator
 * @author juilykumari
 *
 */
@Component
public class OperatorSelector {

	@Autowired
	AccountCreationCounterOperator accountCreationCounterOperator;
	
	@Autowired
	DepositCounterOperator depositCounterOperator;
	
	@Autowired
	WithdrawCounterOperator withdrawCounterOperator;
	
	@Autowired
	DDCounterOperator ddCounterOperator;
	
	public CounterOperator getOperator(Counter counter) {
		switch (counter.getServiceType().getName()) {
		case "Deposit":
			return depositCounterOperator;
		case "Withdraw":
			return withdrawCounterOperator;
		case "Collect DD" :	
			return ddCounterOperator;
		default:
			throw new IllegalArgumentException("Currently counter support only CollectDD/WITHDRAW/DEPOSIT");
		}
	}
}
