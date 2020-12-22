package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.Ticket;
import com.revature.utils.ConnectionUtil;

public class ManagerDAOImpl implements ManagerDAO {

	
	public List<Reimbursement> getAllReimbursements() {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "select er.reimb_status_id, concat(user_first_name, ' ', user_last_name) as name, reimb_submitted, "
					+ "reimb_amount, reimb_description, reimb_status from super_reimbursement.ers_reimbursement er "
					+ "inner join super_reimbursement.ers_users eu on er.reimb_author = eu.ers_users_id inner join "
					+ "super_reimbursement.ers_reimbursement_status ers on er.reimb_status_id = ers.reimb_status_id"
					+ " order by er.reimb_status_id";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Reimbursement r = new Reimbursement (
						resultSet.getInt("reimb_status_id"),
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

	@Override
	public boolean changeStatus(int statusId, String status) {
		

		try (Connection connection = ConnectionUtil.getConnection()) {

			String upperStatus = status.toUpperCase();
			
			int updateSuccess = 0;
			String sql = "update super_reimbursement.ers_reimbursement_status set reimb_status = ?"
					+ " where reimb_status_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, upperStatus);
			preparedStatement.setInt(2, statusId);
			updateSuccess = preparedStatement.executeUpdate();
			if (updateSuccess==1) {
				return true;
			}
			else {
				return false;
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getManagerId(String username) {

		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "select ers_users_id from  super_reimbursement.ers_users where ers_username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	@Override
	public void updateResolver(int statusId, int managerId) {
		
	
		try (Connection connection = ConnectionUtil.getConnection()) {

			String sql = "update super_reimbursement.ers_reimbursement set reimb_resolver = ?, reimb_resolved = ? "
					+ " where reimb_status_id = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, managerId);
			preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setInt(3, statusId);
			preparedStatement.executeUpdate();
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public List<Reimbursement> getPendingReimbursements() {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "select er.reimb_status_id, concat(user_first_name, ' ', user_last_name) as name, reimb_submitted, "
					+ "reimb_amount, reimb_description, reimb_status from super_reimbursement.ers_reimbursement er "
					+ "inner join super_reimbursement.ers_users eu on er.reimb_author = eu.ers_users_id inner join "
					+ "super_reimbursement.ers_reimbursement_status ers on er.reimb_status_id = ers.reimb_status_id "
					+ "where ers.reimb_status = 'PENDING' order by er.reimb_status_id";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Reimbursement r = new Reimbursement (
						resultSet.getInt("reimb_status_id"),
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
