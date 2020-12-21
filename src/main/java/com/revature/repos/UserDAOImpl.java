package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.utils.ConnectionUtil;

public class UserDAOImpl implements UserDAO {

	private static final Logger log = LogManager.getLogger(UserDAOImpl.class);

	@Override
	public boolean checkIfUserExists(String username) {

		try (Connection connection = ConnectionUtil.getConnection()) {

			String sql = "select ers_username from super_reimbursement.ers_users where ers_username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean checkPassword(String username, String encoded) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "select ers_username from super_reimbursement.ers_users where ers_username = ? and ers_password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, encoded);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	@Override
	public int getRoleId(String username) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "select user_role_id from super_reimbursement.ers_users where ers_username = ?";
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
	public String getUserRole(int roleId) {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "select user_role from super_reimbursement.ers_user_roles where ers_user_role_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, roleId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	

}
