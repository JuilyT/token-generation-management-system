package com.example.assignment.manager;

import com.example.assignment.model.BankService;
import com.example.assignment.model.ServiceRequest;
import com.example.assignment.model.ServiceType;
import com.example.assignment.model.Token;

public interface ServiceManager {
	BankService getBankServiceById(int id);

	ServiceRequest getRequestById(int id);

	ServiceType getActiveServiceTypeForRequest(int id);

	ServiceType getServiceTypeById(int id);
	
	Token generateTokenForServiceRequest(ServiceRequest request) throws Exception;
}
