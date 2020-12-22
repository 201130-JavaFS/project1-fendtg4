package com.revature.services.test.testing;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import com.revature.models.Ticket;
import com.revature.services.test.EmployeeServiceTest;

public class EmployeeServiceTesting {

	
	private EmployeeServiceTest est = new EmployeeServiceTest();
	@Test
	public void testRequestWrongTiming() {
		
		String username = "werwre";
		String description = "sdffsdfds";
		double amount = 10000;
		String type = "Employee";
		
		boolean success = est.request(username, description, amount, type);
		assertTrue(success);
	}

	
}
