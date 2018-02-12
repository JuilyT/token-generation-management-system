package com.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.assignment.BankingApplication;
import com.example.assignment.enums.ServiceType;
import com.example.assignment.model.Counter;
import com.example.assignment.operations.AccountCreationCounterOperator;
import com.example.assignment.operations.OperatorSelector;
import com.example.assignment.operations.WithdrawCounterOperator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BankingApplication.class)
public class OperatorSelectorTest {
	@Autowired
	OperatorSelector selector;
	
	@Test
    public void testIfItsWithdrawlCounterOperator() throws Exception{
		Counter counter = new Counter(1, ServiceType.WITHDRAWL);
		Assert.assertTrue(selector.getOperator(counter) instanceof WithdrawCounterOperator);
		Assert.assertFalse(selector.getOperator(counter) instanceof AccountCreationCounterOperator);
    }
	
	@Test
    public void testIfItsAccountCreationCounterOperator() throws Exception{
		Counter counter = new Counter(1, ServiceType.ACCOUNT_CREATION);
		Assert.assertTrue(selector.getOperator(counter) instanceof AccountCreationCounterOperator);
    }

}
