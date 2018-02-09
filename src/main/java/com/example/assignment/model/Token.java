package com.example.assignment.model;

import static com.example.assignment.utility.Util.generateRandom;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.example.assignment.enums.AccountType;
import com.example.assignment.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private Status status = Status.IN_PROGRESS;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private AccountType priority = AccountType.REGULAR;
	@Column
	private Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counter_id")
	@JsonIgnore
	private Counter counter;
	@OneToOne
	ServiceRequest request;
	
	public Token() {
		// TODO Auto-generated constructor stub
	}

	public Token(AccountType priority) {
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public AccountType getPriority() {
		return priority;
	}

	public void setPriority(AccountType priority) {
		this.priority = priority;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Counter getCounter() {
		return counter;
	}

	public void setCounter(Counter counter) {
		this.counter = counter;
	}

	public ServiceRequest getRequest() {
		return request;
	}
	
	public void setRequest(ServiceRequest request) {
		this.request = request;
	}
	
	public static Token getTokenForCustomer(ServiceRequest request) {
		Token token = new Token();
		token.setPriority(request.getCustomer().getAccountType());
		token.setRequest(request);
		return token;
		
	}
}
