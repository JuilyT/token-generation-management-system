package com.example.assignment.model;

import java.beans.Transient;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.util.CollectionUtils;

import com.example.assignment.enums.AccountType;
import com.example.assignment.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Info regarding the counter which serves customers for their services on token basis.
 * @author juilykumari
 *
 */
@Entity
public class Counter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ServiceType serviceType;
	private AccountType priority = AccountType.REGULAR;
	 @OneToMany(
		        mappedBy = "counter", 
		        cascade = CascadeType.ALL, 
		        orphanRemoval = true
		    )
	private List<Token> tokens;

	public Counter() {
		super();
	};

	public Counter(int id, ServiceType type) {
		this.id = id;
		this.serviceType = type;
	}

	public Counter(ServiceType type, AccountType priority) {
		this.serviceType = type;
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public String toString() {
		return "Counter [id=" + id + ", serviceType=" + serviceType + "]";
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	// It can be put in Utility class and make generic method
	public static List<Token> safe(List<Token> other) {
		return other == null ? Collections.emptyList() : other;
	}

	@Transient
	public Token getActiveToken() {
		return getTokens().get(0);
	}

	public AccountType getPriority() {
		return priority;
	}

	public void setPriority(AccountType priority) {
		this.priority = priority;
	}
	
	@JsonIgnore
	public int getRank() {
		if (CollectionUtils.isEmpty(tokens)) {
			return 0;
		}
		return tokens.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Counter other = (Counter) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
