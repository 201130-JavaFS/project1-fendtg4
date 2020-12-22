package com.revature.models;

import java.sql.Timestamp;

public class Reimbursement {

	private int statusId;
	private String author;
	//concatenate first and last name from inner join for this
	private Timestamp submitDate;
	private double amount;
	private String description;
	private String status;
	
	public Reimbursement(int statusId, String author, Timestamp submitDate, double amount, String description,
			String status) {
		super();
		this.statusId = statusId;
		this.author = author;
		this.submitDate = submitDate;
		this.amount = amount;
		this.description = description;
		this.status = status;
	}

	public Reimbursement(String author, Timestamp submitDate, double amount, String description, String status) {
		super();
		this.author = author;
		this.submitDate = submitDate;
		this.amount = amount;
		this.description = description;
		this.status = status;
	}

	public Reimbursement() {
		super();
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + statusId;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitDate == null) ? 0 : submitDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (statusId != other.statusId)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (submitDate == null) {
			if (other.submitDate != null)
				return false;
		} else if (!submitDate.equals(other.submitDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [statusId=" + statusId + ", author=" + author + ", submitDate=" + submitDate + ", amount="
				+ amount + ", description=" + description + ", status=" + status + "]";
	}

	
	
}
