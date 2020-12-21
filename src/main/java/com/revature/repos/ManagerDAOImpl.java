package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.Ticket;
import com.revature.utils.ConnectionUtil;

public class ManagerDAOImpl implements ManagerDAO {

	
	public List<Reimbursement> getAllReimbursements() {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "select reimb_author, concat(user_first_name, ' ', user_last_name) as name, reimb_submitted, "
					+ "reimb_amount, reimb_description, reimb_status from super_reimbursement.ers_reimbursement er "
					+ "inner join super_reimbursement.ers_users eu on er.reimb_author = eu.ers_users_id inner join "
					+ "super_reimbursement.ers_reimbursement_status ers on er.reimb_status_id = ers.reimb_status_id ";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Reimbursement r = new Reimbursement (
						resultSet.getInt("reimb_author"),
						resultSet.getString("name"),
						resultSet.getTimestamp("reimb_submitted"),
						resultSet.getDouble("reimb_amount"),
						resultSet.getString("reimb_description"),
						resultSet.getString("reimb_status")
			);
				reimbursementList.add(r);
			}
			return reimbursementList;
		
		} catch (SQLException e) {
		e.printStackTrace();
		}

		return null;
		
	}
}
