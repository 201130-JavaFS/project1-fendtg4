package com.revature.repos;

public interface UserDAO {

	public boolean checkIfUserExists(String username);
	public boolean checkPassword(String username, String encoded);
	public int getRoleId(String username);
	public String getUserRole(int roleId);
	
}
