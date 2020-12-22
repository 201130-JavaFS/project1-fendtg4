package com.revature.services.test.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.services.test.LoginServiceTest;

public class LoginServiceTesting {

	private LoginServiceTest lst = new LoginServiceTest();
	
	@Test
	public void testLoginWrongPassword() {
		
		String username = "JohnSmith123";
		String password = "abc123";
		
		boolean success = lst.login(username, password);
		assertTrue(success);
	}
	
	@Test
	public void testLoginRightPassword() {
		
		String username = "JohnSmith123";
		String password = "password";
		
		boolean success = lst.login(username, password);
		assertTrue(success);
	}
	
	@Test
	public void testGetRoleId() {
		
		String username = "JohnSmith123";
		int roleId = 1;
		
		assertEquals(roleId, lst.getRoleId(username));
	}
	
	@Test
	public void testGetUserRoleCorrect() {
		
		int roleId = 1;
		String userRole = "Employee";
		
		assertEquals(userRole, lst.getUserRole(roleId));
	}
	
	@Test
	public void testGetUserRoleIncorrect() {
		
		int roleId = 1;
		String userRole = "King";
		
		assertEquals(userRole, lst.getUserRole(roleId));
	}
}
