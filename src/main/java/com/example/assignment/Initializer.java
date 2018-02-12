package com.example.assignment;

import static com.example.assignment.utility.Util.generateRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.assignment.enums.AccountType;
import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;

/**
 * This is to initialize customers, counters info in postgreSQL database which are assumed scenarios
 * @author juilykumari
 *
 */
@Component
public class Initializer {
	public static final int LENGTH = 12;
	public static HashMap<String, Customer> customerMap;	
	public static List<Counter> counters;
	
	@Value("${app.deposit.size}")
	private int depositSize;
	
	@Value("${app.withdraw.size}")
	private int withdrawSize;
	
	@Value("${app.create.size}")
	private int createSize;	
	
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CounterRepository counterRepository;
	
	@PostConstruct
	private void initData() {
		initCustomers();
		initCounters();
	}
	
	private void initCustomers() {
		customerMap=new HashMap<>();
		List<ServiceRequest> services  =new ArrayList<>();
		ServiceRequest request = new ServiceRequest();
		request.setServiceType(ServiceType.DEPOSIT);
		services.add(request);
		String accnt1 = generateRandom(LENGTH);
		Customer cust1 = new Customer(accnt1, "John Doe", AccountType.REGULAR);
		customerMap.put(accnt1, cust1);
		String accnt2 = generateRandom(LENGTH);
		Customer cust2 = new Customer(accnt2, "Arya Stark", 
				AccountType.PREMIUM);
		cust2.setServiceRequests(services);
		customerMap.put(accnt2, cust2);
		String accnt3 = generateRandom(LENGTH);
		Customer cust3 = new Customer(accnt3, "ram", 
				AccountType.REGULAR);
		customerMap.put(accnt3, cust3);
		String accnt4= generateRandom(LENGTH);
		Customer cust4 = new Customer(accnt4, "shyam", 
				AccountType.REGULAR);
		customerMap.put(accnt4, cust4);
		String accnt5 = generateRandom(LENGTH);
		Customer cust5 = new Customer(accnt5, "ram2", 
				AccountType.PREMIUM);
		customerMap.put(accnt5, cust5);
	}
	
	private void initCounters() {
		counters = new ArrayList<>();
		Counter counterDepost1 = new Counter(ServiceType.DEPOSIT, AccountType.REGULAR);
		Counter counterWithDrawl1 = new Counter(ServiceType.WITHDRAWL, AccountType.REGULAR);
		Counter counterDepost2 = new Counter(ServiceType.DEPOSIT, AccountType.REGULAR);
		Counter counterDepost3 = new Counter(ServiceType.DEPOSIT, AccountType.PREMIUM);
		Counter counterAccountCreation1 = new Counter(ServiceType.ACCOUNT_CREATION, AccountType.PREMIUM);		
		counters.add(counterDepost1);
		counters.add(counterWithDrawl1);
		counters.add(counterDepost2);
		counters.add(counterDepost3);
		counters.add(counterAccountCreation1);
	}
	
	@Bean
	InitializingBean sendDatabase() {
	    return () -> {
	    	for (Customer cust : customerMap.values()) {
	    		customerRepository.save(cust);
			}
	    	for (Counter countr : counters) {
	    		counterRepository.save(countr);
			}
	    };
	}
}
