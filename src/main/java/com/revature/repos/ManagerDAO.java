package com.revature.repos;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ManagerDAO {

	public List<Reimbursement> getAllReimbursements();
	public boolean changeStatus(int statusId, String status);
	public int getManagerId(String username);
	public void updateResolver(int statusId, int managerId);
	public List<Reimbursement> getPendingReimbursements();
	
	
}
