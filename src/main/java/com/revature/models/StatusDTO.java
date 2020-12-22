package com.revature.models;

public class StatusDTO {

	public String username;
	public int statusId;
	public String status;
	
	

	public StatusDTO(String username, int statusId, String status) {
		super();
		this.username = username;
		this.statusId = statusId;
		this.status = status;
	}


	public StatusDTO() {
		super();
	}
	
	
}
