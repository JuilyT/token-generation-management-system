package com.example.assignment.exception;

public class TokenGenerationException extends Exception {
	private static final long serialVersionUID = 1L;
	String message;
	
	public TokenGenerationException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
