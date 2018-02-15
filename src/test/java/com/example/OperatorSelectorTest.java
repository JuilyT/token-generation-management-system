package com.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.assignment.BankingApplication;
import com.example.assignment.model.Counter;
import com.example.assignment.model.ServiceType;
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
		ServiceType withdrawService = new ServiceType(1, "Withdraw");
		Counter counter = new Counter(1, withdrawService);
		Assert.assertTrue(selector.getOperator(counter) instanceof WithdrawCounterOperator);
		Assert.assertFalse(selector.getOperator(counter) instanceof AccountCreationCounterOperator);
    }
	
	@Test
    public void testIfItsDepositCounterOperator() throws Exception{
		ServiceType depositService = new ServiceType(1, "Deposit");
		Counter counter = new Counter(1, depositService);
		Assert.assertFalse(selector.getOperator(counter) instanceof AccountCreationCounterOperator);
    }
	
	@Test(expected=IllegalArgumentException.class)
    public void throwIfServiceIsNotSupported() throws Exception{
		ServiceType depositService = new ServiceType(1, "Insurance");
		Counter counter = new Counter(1, depositService);
		selector.getOperator(counter);
    }

}
