package com.example.assignment.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.example.assignment.enums.AccountType;
import com.example.assignment.enums.ServiceType;

/**
 * Info for each customer associated with bank along with list of services 
 * @author juilykumari
 *
 */
@Entity
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String accountNo;
	@Column
	private String name;
	@Column
	private String address;
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private AccountType accountType;
	@OneToOne
	private ServiceRequest activeRequest;
	@Transient
	private List<ServiceRequest> serviceRequests;
	
	public Customer() {
	}

	public Customer(String accountNo, String name, AccountType accountType) {
		this.accountNo = accountNo;
		this.name = name;
		this.accountType = accountType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public boolean isNewCustomer() {
		return this.getId() == null ? true : false;
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", accountNo=" + accountNo + ", name=" + name + ", accountType=" + accountType
				+ "]";
	}

	public ServiceRequest getActiveRequest() {
		return activeRequest;
	}

	public void setActiveRequest(ServiceRequest activeRequest) {
		this.activeRequest = activeRequest;
	}
	
	public List<ServiceRequest> getServiceRequests() {
		return serviceRequests;
	}
	
	public void setServiceRequests(List<ServiceRequest> serviceRequests) {
		this.serviceRequests = serviceRequests;
	}
	
	public ServiceType getActiveService() {
		if (activeRequest == null) {
			return null;
		}
		return activeRequest.getServiceType();
	}

}
