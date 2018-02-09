package com.example.assignment.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.assignment.enums.AccountType;
import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;

public interface CounterRepository extends CrudRepository<Counter, Integer>{
	public List<Counter> findByServiceType(ServiceType serviceType);
	public List<Counter> findByServiceTypeAndPriority(ServiceType serviceType, AccountType priority);
	public List<Counter> findAllByOrderByIdAsc();
}
