package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Ticket;
import com.revature.utils.ConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public boolean insertType(String type) {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			int insertSuccess = 0;
			String sql = "insert into super_reimbursement.ers_reimbursement_type (reimb_type) values (?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, type);
			insertSuccess = preparedStatement.executeUpdate();
			if (insertSuccess==1) {
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
	public boolean insertStatus() {
		
		try (Connection connection = ConnectionUtil.getConnection()) {

			
			int insertSuccess = 0;
			String sql = "insert into super_reimbursement.ers_reimbursement_status (reimb_status) values ('PENDING')";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			insertSuccess = preparedStatement.executeUpdate();
			if (insertSuccess==1) {
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
	public boolean request(double amount, String description, int authorId, int statusId, int typeId) {
		try (Connection connection = ConnectionUtil.getConnection()) {

			String sql = "insert into super_reimbursement.ers_reimbursement (reimb_amount, reimb_submitted, reimb_description,"
					+ " reimb_author, reimb_status_id, reimb_type_id) values (?, ?, ?, ?, ?, ?)";
			
			int insertSuccess = 0;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, amount);
			preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setString(3, description);
			preparedStatement.setInt(4, authorId);
			preparedStatement.setInt(5, statusId);
			preparedStatement.setInt(6, typeId);
			insertSuccess = preparedStatement.executeUpdate();
			if (insertSuccess==1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	
	}

	@Override
	public int getUserId(String username) {

		try (Connection connection = ConnectionUtil.getConnection()) {

		
			String sql = "select ers_users_id from super_reimbursement.ers_users where ers_username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int userId = resultSet.getInt("ers_users_id");
				return userId;
			}
			else {
				return 0;
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
		
	

	
	@Override
	public int getTypeId() {

		try (Connection connection = ConnectionUtil.getConnection()) {

			
			
			String sql = "select reimb_type_id from super_reimbursement.ers_reimbursement_type order by reimb_type_id desc limit 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int typeId = resultSet.getInt("reimb_type_id");
				return typeId;
			}
			else {
				return 0;
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	

	@Override
	public int getStatusId() {

		try (Connection connection = ConnectionUtil.getConnection()) {

			String sql = "select reimb_status_id from super_reimbursement.ers_reimbursement_status order by reimb_status_id desc limit 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int statusId = resultSet.getInt("reimb_status_id");
				return statusId;
			}
			else {
				return 0;
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	@Override
	public List<Ticket> getTickets(int authorId) {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
	
			String sql = "select reimb_submitted, reimb_amount, reimb_description, reimb_status from super_reimbursement.ers_reimbursement er "
					+ "inner join super_reimbursement.ers_reimbursement_status ers on er.reimb_status_id = ers.reimb_status_id where reimb_author = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authorId);
			List<Ticket> ticketList = new ArrayList<Ticket>();
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Ticket t = new Ticket (
						resultSet.getTimestamp("reimb_submitted"),
						resultSet.getDouble("reimb_amount"),
						resultSet.getString("reimb_description"),
						resultSet.getString("reimb_status")
			);
				ticketList.add(t);
			}
			return ticketList;
		
		} catch (SQLException e) {
		e.printStackTrace();
		}

		return null;

	}
}
	
