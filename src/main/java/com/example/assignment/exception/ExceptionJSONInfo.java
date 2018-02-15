package com.example.assignment.exception;

/**
 * Wrapper containing exception/error info usable for client when handled in the application. 
 *  
 * @author juilykumari
 *
 */
public class ExceptionJSONInfo {
	private int statusCode;
	private String message;
	private String url;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
