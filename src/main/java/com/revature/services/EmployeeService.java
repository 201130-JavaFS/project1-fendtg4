package com.revature.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Ticket;
import com.revature.repos.EmployeeDAO;
import com.revature.repos.EmployeeDAOImpl;

public class EmployeeService {
	private static final Logger log = LogManager.getLogger(EmployeeService.class);
	private EmployeeDAO eDAO = new EmployeeDAOImpl();
	
	public boolean request(String username, String description, double amount, String type) {
		
		if (eDAO.insertStatus() && eDAO.insertType(type)) {
			
			int authorId = eDAO.getUserId(username);
			int statusId = eDAO.getStatusId();
			int typeId = eDAO.getTypeId();
			if (eDAO.request(amount, description, authorId, statusId, typeId)) {
				return true;
			}
			else {
				return false;
			}
		
		
	}
		else {
			return false;
		}
	}

	public List<Ticket> getTickets(String username) {
		
		
		int authorId = eDAO.getUserId(username);
		List<Ticket> ticketList = eDAO.getTickets(authorId);
		return ticketList;
	}
}
