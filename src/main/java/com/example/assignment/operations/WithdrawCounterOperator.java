package com.example.assignment.operations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.example.assignment.enums.AccountType;
import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Customer;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WithdrawCounterOperator  extends AbstractCounterOperatorImpl {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TokenGenerator.class);

	@Override
	protected void performSpecificTask(Token activeToken) {
		Customer customer = activeToken.getRequest().getCustomer();
		LOGGER.info("10000 withdrawn by customer :{}", customer);
	}
	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<ServiceRequest> services  =new ArrayList<>();
		ServiceRequest request = new ServiceRequest();
		request.setServiceType(ServiceType.DEPOSIT);
		services.add(request);
		Customer cust = new Customer("123456789102", "John Doe", AccountType.PREMIUM);
		cust.setAddress("Panjagutta");
		cust.setId(66436);
		cust.setServiceRequests(services);
		System.out.println(mapper.writeValueAsString(cust));
	}

}
