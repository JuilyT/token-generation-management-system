package com.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.assignment.BankingApplication;
import com.example.assignment.enums.AccountType;
import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.ServiceRequestRepository;
import com.example.assignment.repository.TokenRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BankingApplication.class)
public class TokenGeneratorTest {
	@Autowired
	TokenGenerator generator;
    @MockBean
	CounterRepository counterRepository; 
    @MockBean
	ServiceRequestRepository requestRepository;
    @MockBean
    TokenRepository tokenRepository;
	    
    @Test(expected = IllegalArgumentException.class)
    public void testGenerateTokenIfNoServiceRequested() throws Exception{
    	generator.generateToken(null);
    }
    
    @Test
    public void testGenerateToken() throws Exception{
    	ServiceRequest request = new ServiceRequest();
		request.setServiceType(ServiceType.DEPOSIT);
		Customer cust = new Customer("123456789102", "John Doe", AccountType.PREMIUM);
		request.setCustomer(cust);
		cust.setActiveRequest(request);
		List<Counter> counters = new ArrayList();
		Counter counter1 = new Counter(1, ServiceType.DEPOSIT);
		counter1.setPriority(AccountType.REGULAR);
		Counter counter2 = new Counter(2, ServiceType.DEPOSIT);
		counter2.setPriority(AccountType.PREMIUM);
		counters.add(counter1);
		counters.add(counter2);
		Mockito.when(counterRepository.
				findByServiceTypeAndPriority(ServiceType.DEPOSIT, AccountType.PREMIUM)).thenReturn(counters);
		Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(new Token(AccountType.PREMIUM));
    	Token token = generator.generateToken(request);
    	Assert.assertNotNull(token);
    }
    
    @Test(expected=Exception.class)
    public void throwIFServiceCounterNotAvailabe() throws Exception{
    	ServiceRequest request = new ServiceRequest();
		request.setServiceType(ServiceType.DEPOSIT);
		request.setCustomer(new Customer("123456789102", "John Doe", AccountType.PREMIUM));
		List<Counter> counters = new ArrayList();
		Counter counter1 = new Counter(1, ServiceType.DEPOSIT);
		counter1.setPriority(AccountType.REGULAR);
		Counter counter2 = new Counter(2, ServiceType.DEPOSIT);
		counter2.setPriority(AccountType.PREMIUM);
		counters.add(counter1);
		counters.add(counter2);
		Mockito.when(counterRepository.
				findByServiceTypeAndPriority(ServiceType.DEPOSIT, AccountType.PREMIUM)).thenReturn(counters);
		Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(new Token(AccountType.PREMIUM));
    	generator.generateToken(request);
    }
}
