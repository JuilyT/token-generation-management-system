package com.example.assignment.operations;

import com.example.assignment.model.Counter;

/**
 * To list the operations to be performed by counter operator
 * @author juilykumari
 *
 */
public interface CounterOperator {
	public void operate(Counter counter) throws Exception;
}
