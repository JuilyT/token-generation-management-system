package com.example.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.assignment.enums.AccountType;
import com.example.assignment.model.Counter;
import com.example.assignment.model.ServiceType;

public interface CounterRepository extends CrudRepository<Counter, Integer>{
	public List<Counter> findByServiceType(ServiceType serviceType);
	@Query(value = "SELECT * from counter WHERE service_type_id = :serviceTypeId AND priority= :priority",
			nativeQuery = true)
	public List<Counter> findByServiceTypeAndPriority(@Param("serviceTypeId") int serviceTypeId, 
			@Param("priority") AccountType priority);
	public List<Counter> findAllByOrderByIdAsc();
}
