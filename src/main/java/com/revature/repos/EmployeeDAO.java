package com.revature.repos;

import java.util.List;

import com.revature.models.Ticket;

public interface EmployeeDAO {

	public boolean insertType(String type);
	public boolean insertStatus();
	public int getUserId(String username);
	public int getTypeId();
	public int getStatusId();
	public boolean request(double amount, String description, int authorId, int statusId, int typeId);
	public List<Ticket> getTickets(int authorId);
	
	
	
}
