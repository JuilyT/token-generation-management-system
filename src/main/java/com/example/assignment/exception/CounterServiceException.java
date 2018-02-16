package com.example.assignment.exception;

public class CounterServiceException extends Exception{
	private static final long serialVersionUID = 1L;
	String message;
	
	public CounterServiceException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
