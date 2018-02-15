package com.example.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.assignment.model.BankService;

public interface ServicesRepository extends CrudRepository<BankService, Integer> {

}
