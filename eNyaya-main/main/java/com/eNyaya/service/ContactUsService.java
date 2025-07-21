package com.eNyaya.service;

import java.sql.*;

import com.eNyaya.config.DbConfig;

public class ContactUsService {
	
private Connection dbConn;

/**
 * Constructor initializes the database connection.
 */
public ContactUsService() {
	try {
		this.dbConn = DbConfig.getDbConnection();
	} catch (SQLException | ClassNotFoundException ex) {
		System.err.println("Database connection error: " + ex.getMessage());
		ex.printStackTrace();
	}
}

public boolean saveContactMessage(String firstName, String lastName, String email, String phone, String subject, String message) {
	String sql = "INSERT INTO contact_messages (first_name, last_name, email, phone, subject, message) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, email);
			ps.setString(4, phone);
			ps.setString(5, subject); 
			ps.setString(6, message);
			
			return ps.executeUpdate() > 0;
	
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
