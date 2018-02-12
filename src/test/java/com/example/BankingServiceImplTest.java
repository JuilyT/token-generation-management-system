package com.example;

import java.sql.Timestamp;
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
import com.example.assignment.operations.BankingService;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BankingApplication.class)
public class BankingServiceImplTest {
    @Autowired
	BankingService bankingService;	
    @MockBean
	CounterRepository counterRepository; 
    @MockBean
	ServiceRequestRepository requestRepository;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    TokenGenerator tokenGenerator;
    
	@Test(expected = IllegalArgumentException.class)
    public void throwIfNoCounterExists(){
        Mockito.when(counterRepository.findOne(1)).thenReturn(null);
        bankingService.getCounterById(11);
    }
	
	@Test
    public void testGetCounterById(){
        Mockito.when(counterRepository.findOne(1)).thenReturn(new Counter(1, ServiceType.DEPOSIT));
        Counter counter = bankingService.getCounterById(1);
        Assert.assertTrue(counter.getId()==1);
        Assert.assertTrue(counter.getServiceType()==ServiceType.DEPOSIT);
    }
	
	@Test
    public void testProcessWhenExistingCustomer(){
        List<ServiceRequest> services  =new ArrayList<>();
		ServiceRequest request = new ServiceRequest();
		request.setServiceType(ServiceType.DEPOSIT);
		services.add(request);
		Customer cust = new Customer("123456789102", "John Doe", AccountType.PREMIUM);
		cust.setAddress("Panjagutta");
		cust.setId(66436);
		cust.setServiceRequests(services);
		Mockito.when(requestRepository.save(services)).thenReturn(services);
		Mockito.when(requestRepository.getActiveServiceRequestForCustomer(1)).thenReturn(request);
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		Token token = null;
		try {
			Mockito.when(tokenGenerator.generateToken(request)).thenReturn(new Token(AccountType.PREMIUM));
			token = bankingService.process(cust);
			Assert.assertTrue(token.getPriority()==AccountType.PREMIUM);
			Assert.assertTrue(token.getLastUpdated()==new Timestamp(System.currentTimeMillis()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
}
