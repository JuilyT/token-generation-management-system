package com.example;

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
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceType;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;
import com.example.assignment.service.CounterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BankingApplication.class)
public class CounterServiceImplTest {
    @Autowired
	CounterService counterService;	
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
        counterService.getCounterById(11);
    }
	
	@Test
    public void testGetCounterById() throws JsonProcessingException{
		ServiceType depositService = new ServiceType(2, "DEPOSIT");
        Mockito.when(counterRepository.findOne(1)).thenReturn(new Counter(1, depositService));
        Counter counter = counterService.getCounterById(1);
        Assert.assertTrue(counter.getId()==1);
        Assert.assertTrue(counter.getServiceType()==depositService);
        Customer cust = new Customer("123456789102", "John Doe", AccountType.PREMIUM);
		cust.setAddress("Panjagutta");
		cust.setId(66436);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(cust));
    }
}
