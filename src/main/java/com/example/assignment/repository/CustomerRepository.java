package com.example.assignment.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.assignment.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer	, Integer>{
}
