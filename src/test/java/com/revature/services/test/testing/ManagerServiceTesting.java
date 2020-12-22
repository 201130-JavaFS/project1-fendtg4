package com.revature.services.test.testing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.services.test.ManagerServiceTest;

public class ManagerServiceTesting {

	
	private ManagerServiceTest mst = new ManagerServiceTest();
	
	@Test
	public void testChangeStatusWrongId() {
		
		int statusId = 100;
		String status = "APPROVED";
		String username = "Manage456";
		
		assertTrue(mst.changeStatus(statusId, status, username));
	}
	

}
