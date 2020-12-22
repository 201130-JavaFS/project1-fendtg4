package com.revature.services.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.repos.ManagerDAO;
import com.revature.repos.ManagerDAOImpl;


public class ManagerServiceTest {
	
	private ManagerDAO mDAO = new ManagerDAOImpl();
	
	public List<Reimbursement> getAllReimbursements() {
		
		List<Reimbursement> reimbursementList = mDAO.getAllReimbursements();
		return reimbursementList;
	}

	public boolean changeStatus(int statusId, String status, String username) {
		
		if (mDAO.changeStatus(statusId, status)) {
			int managerId = mDAO.getManagerId(username);
			mDAO.updateResolver(statusId, managerId);
			return true;
		}
		else {
			return false;
		}
		
	}

	public List<Reimbursement> getPendingReimbursements() {
		
		List<Reimbursement> pendingList = mDAO.getPendingReimbursements();
		return pendingList;
	}
}



