package com.example.assignment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.assignment.model.ServiceRequest;

public interface ServiceRequestRepository extends CrudRepository<ServiceRequest, Integer>{
	
	@Query(value = "SELECT * from service_request s WHERE s.customer_id = :customerId AND s.status='IN_PROGRESS' ORDER by s.id LIMIT 1",
			nativeQuery = true)
	public ServiceRequest getActiveServiceRequestForCustomer(@Param("customerId") int customerId);
}
