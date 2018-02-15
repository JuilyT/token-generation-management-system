package com.example.assignment.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;

/**
 * Repository for token operations in database
 * @author juilykumari
 *
 */
public interface TokenRepository extends CrudRepository<Token, Integer>{
	public Token findByRequest(ServiceRequest request);
}
