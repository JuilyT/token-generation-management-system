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
import com.example.assignment.model.BankService;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;
import com.example.assignment.service.TokenService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BankingApplication.class)
public class TokenServiceImplTest {
	@Autowired
	TokenService tokenService;	
    @MockBean
	ServiceRequestRepository requestRepository;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    TokenGenerator tokenGenerator;
    
	@Test
    public void testProcessWhenExistingCustomer(){
        List<ServiceRequest> services  =new ArrayList<>();
		ServiceRequest request = new ServiceRequest();
		BankService service = BankService.getServices().get(1);
		request.setService(service);
		services.add(request);
		Customer cust = new Customer("123456789102", "John Doe", AccountType.PREMIUM);
		cust.setAddress("Panjagutta");
		cust.setId(66436);
		cust.setServiceRequests(services);
		cust.setActiveRequest(request);
		Mockito.when(requestRepository.save(services)).thenReturn(services);
		Mockito.when(requestRepository.getActiveServiceRequestForCustomer(1)).thenReturn(request);
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		Token token1 = new Token(AccountType.PREMIUM);
		try {
			Mockito.when(tokenGenerator.generateToken(Mockito.any(ServiceRequest.class))).thenReturn(token1);
			Token token = tokenService.process(cust);
			Assert.assertTrue(token.getPriority()==AccountType.PREMIUM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
}
