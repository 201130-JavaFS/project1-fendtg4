package com.revature.models;

public class RequestDTO {

	public String username;
	public String description;
	public double amount;
	public String type;
	
	public RequestDTO(String username, String description, double amount, String type) {
		super();
		this.username = username;
		this.description = description;
		this.amount = amount;
		this.type = type;
	}

	public RequestDTO() {
		super();
	}
	
	
	
}
