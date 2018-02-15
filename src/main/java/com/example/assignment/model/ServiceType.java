package com.example.assignment.model;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * Service info for all those available under bank branch
 * @author juilykumari
 *
 */
@Entity
public class ServiceType {
	@Id
	private int id;
	private String name;

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
		ServiceType other = (ServiceType) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transactions [id=" + id + ", name=" + name + "]";
	}

	public ServiceType() {
		
	}

	public ServiceType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static Map<Integer, ServiceType> getTX() {
		Map<Integer, ServiceType> map = new HashMap<>();
		map.put(1, new ServiceType(1, "Deposit"));
		map.put(2, new ServiceType(2, "Withdraw"));
		map.put(3, new ServiceType(3, "Collect DD"));

		return map;
	}
}
