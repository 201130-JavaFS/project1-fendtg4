package com.revature.services;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;

public class LoginService {

	private static final Logger log = LogManager.getLogger(LoginService.class);
	
	private UserDAO userDAO = new UserDAOImpl();
	public boolean login(String username, String password) {

		// encode data using BASE64
		String encoded = DatatypeConverter.printBase64Binary(password.getBytes());

		//log.debug("password: " +  password);
		//log.debug("encoded: " + encoded);
		if (userDAO.checkIfUserExists(username)) {
			if (userDAO.checkPassword(username, encoded)) {
				//log.debug("success1");
				return true;
			} else {
				//log.debug("fail1");
				return false;
			}
		} else {
			//log.debug("fail2");
			return false;
		}
	}
	
	public int getRoleId(String username) {
		
		return userDAO.getRoleId(username);
	}
	public String getUserRole(int roleId) {
	
		return userDAO.getUserRole(roleId);
	}

}
