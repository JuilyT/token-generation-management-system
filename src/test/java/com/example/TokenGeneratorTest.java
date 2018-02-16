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
import com.example.assignment.exception.TokenGenerationException;
import com.example.assignment.manager.ServiceManager;
import com.example.assignment.model.BankService;
import com.example.assignment.model.Counter;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.ServiceType;
import com.example.assignment.model.Token;
import com.example.assignment.operations.TokenGenerator;
import com.example.assignment.repository.CounterRepository;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.repository.ServiceRequestRepository;
import com.example.assignment.repository.TokenRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BankingApplication.class)
public class TokenGeneratorTest {
	@Autowired
	TokenGenerator tokenGenerator;	
    @MockBean
	ServiceRequestRepository requestRepository;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    TokenRepository tokenRepository;
    @MockBean
    ServiceManager serviceManager;
    @MockBean
    CounterRepository counterRepository;
    
	@Test(expected=TokenGenerationException.class)
    public void throwIfNoServiceToGenerateTokenFor() throws Exception{
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
		tokenGenerator.generateToken(null);
    }
	
	@Test
    public void testIfTokenGeneratedAndAssignedCounter() throws Exception{
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
		request.setCustomer(cust);
		request.setId(2);
		List<Counter> counters = new ArrayList<>();
		List<Token> tokens = new ArrayList<>();
		tokens.add(new Token(AccountType.PREMIUM));
		tokens.add(new Token(AccountType.PREMIUM));
		tokens.add(new Token(AccountType.PREMIUM));
		List<Token> tokensForCounter2 = new ArrayList<>();
		tokensForCounter2.add(new Token(AccountType.PREMIUM));
		tokensForCounter2.add(new Token(AccountType.PREMIUM));
		Counter counter1 = new Counter(new ServiceType(2, "DEPOSIT"), AccountType.PREMIUM);
		counter1.setId(1);
		counter1.setTokens(tokens);
		Counter counter2 = new Counter(new ServiceType(2, "DEPOSIT"), AccountType.PREMIUM);
		counter2.setTokens(tokensForCounter2);
		counter2.setId(5);
		counters.add(counter1);
		counters.add(counter2);
		Mockito.when(requestRepository.save(services)).thenReturn(services);
		Mockito.when(requestRepository.getActiveServiceRequestForCustomer(1)).thenReturn(request);
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		Mockito.when(tokenRepository.findByRequest(Mockito.any(ServiceRequest.class))).thenReturn(null);
		Mockito.when(serviceManager.getActiveServiceTypeForRequest(2)).thenReturn(new ServiceType(2, "DEPOSIT"));
		Mockito.when(counterRepository.findByServiceTypeAndPriority(2, AccountType.PREMIUM)).thenReturn(counters);
		Token token = new Token(AccountType.PREMIUM);
		token.setId(65536);
		Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(token);
		Token generatedToken = tokenGenerator.generateToken(request);
		Assert.assertNotNull(generatedToken);
		Assert.assertTrue(generatedToken.getCounter().getId()==5);
    }
	
	@Test(expected=TokenGenerationException.class)
    public void throwIfNullServiceCounterAvailable() throws Exception{
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
		request.setCustomer(cust);
		request.setId(2);
		Mockito.when(requestRepository.save(services)).thenReturn(services);
		Mockito.when(requestRepository.getActiveServiceRequestForCustomer(1)).thenReturn(request);
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		Mockito.when(tokenRepository.findByRequest(Mockito.any(ServiceRequest.class))).thenReturn(null);
		Mockito.when(serviceManager.getActiveServiceTypeForRequest(2)).thenReturn(new ServiceType(2, "DEPOSIT"));
		Mockito.when(counterRepository.findByServiceTypeAndPriority(2, AccountType.PREMIUM)).thenReturn(null);
		Token token = new Token(AccountType.PREMIUM);
		token.setId(65536);
		Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(token);
		tokenGenerator.generateToken(request);
    }
	
	@Test(expected=TokenGenerationException.class)
    public void throwIfEmptyServiceCounterAvailable() throws Exception{
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
		request.setCustomer(cust);
		request.setId(2);
		Mockito.when(requestRepository.save(services)).thenReturn(services);
		Mockito.when(requestRepository.getActiveServiceRequestForCustomer(1)).thenReturn(request);
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		Mockito.when(tokenRepository.findByRequest(Mockito.any(ServiceRequest.class))).thenReturn(null);
		Mockito.when(serviceManager.getActiveServiceTypeForRequest(2)).thenReturn(new ServiceType(2, "DEPOSIT"));
		Mockito.when(counterRepository.findByServiceTypeAndPriority(2, AccountType.PREMIUM)).thenReturn(new ArrayList<>());
		Token token = new Token(AccountType.PREMIUM);
		token.setId(65536);
		Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(token);
		tokenGenerator.generateToken(request);
    }
	
	@Test
    public void testIfOneCounterIsEmpty() throws Exception{
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
		request.setCustomer(cust);
		request.setId(2);
		List<Counter> counters = new ArrayList<>();
		List<Token> tokensForCounter2 = new ArrayList<>();
		tokensForCounter2.add(new Token(AccountType.PREMIUM));
		tokensForCounter2.add(new Token(AccountType.PREMIUM));
		Counter counter1 = new Counter(new ServiceType(2, "DEPOSIT"), AccountType.PREMIUM);
		counter1.setId(1);
		Counter counter2 = new Counter(new ServiceType(2, "DEPOSIT"), AccountType.PREMIUM);
		counter2.setTokens(tokensForCounter2);
		counter2.setId(5);
		counters.add(counter1);
		counters.add(counter2);
		Mockito.when(requestRepository.save(services)).thenReturn(services);
		Mockito.when(requestRepository.getActiveServiceRequestForCustomer(1)).thenReturn(request);
		Mockito.when(customerRepository.save(cust)).thenReturn(cust);
		Mockito.when(tokenRepository.findByRequest(Mockito.any(ServiceRequest.class))).thenReturn(null);
		Mockito.when(serviceManager.getActiveServiceTypeForRequest(2)).thenReturn(new ServiceType(2, "DEPOSIT"));
		Mockito.when(counterRepository.findByServiceTypeAndPriority(2, AccountType.PREMIUM)).thenReturn(counters);
		Token token = new Token(AccountType.PREMIUM);
		token.setId(65536);
		Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(token);
		Token generatedToken = tokenGenerator.generateToken(request);
		Assert.assertNotNull(generatedToken);
		Assert.assertTrue(generatedToken.getCounter().getId()==1);
    }
}
