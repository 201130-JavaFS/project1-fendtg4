package com.revature.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.repos.ManagerDAO;
import com.revature.repos.ManagerDAOImpl;


public class ManagerService {
	
	private static final Logger log = LogManager.getLogger(ManagerService.class);
	private ManagerDAO mDAO = new ManagerDAOImpl();
	
	public List<Reimbursement> getAllReimbursements() {
		
		List<Reimbursement> reimbursementList = mDAO.getAllReimbursements();
		return reimbursementList;
	}
}
