package com.example.assignment.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.assignment.model.Token;

public interface TokenRepository extends CrudRepository<Token, Integer>{
	
}
