package com.example.assignment.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

/**
 * Wrapper containing all the tasks involved for each service 
 * select s.id, s.name, st.transactions_order, t.name FROM service s INNER JOIN service_transactions st ON s.id=st.service_id INNER JOIN transaction t ON st.transactions_id=t.id ORDER BY s.id, st.transactions_order
 * @author juilykumari
 *
 */

@Entity
public class BankService {
	@Id
	private int id;
	private String name;
	@ManyToMany
	@OrderColumn
	private List<ServiceType> transactions;

	public BankService() {
	}

	public BankService(int id, String name, List<ServiceType> transactions) {
		super();
		this.id = id;
		this.name = name;
		this.transactions = transactions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceType> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<ServiceType> transactions) {
		this.transactions = transactions;
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
		BankService other = (BankService) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", transactions=" + transactions + "]";
	}

	public static List<BankService> getServices() {
		List<BankService> services = new ArrayList<>();
		Map<Integer, ServiceType> map = ServiceType.getTX();
		
		// services spanning single TXs
		services.add(new BankService(1, "WITHDRAW", Arrays.asList(map.get(2))));
		services.add(new BankService(2, "DEPOSIT", Arrays.asList(map.get(1))));
		
		// multi-counter services
		services.add(new BankService(3, "DEMAND DRAFT", Arrays.asList(map.get(1), map.get(3))));
		
		return services;

	}
}
