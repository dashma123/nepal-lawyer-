package com.eNyaya.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eNyaya.config.DbConfig;
import com.eNyaya.model.LawyerModel;
import com.eNyaya.model.ClientModel;

/**
 * RegisterService handles the registration of new students. It manages database
 * interactions for student registration.
 */
public class RegisterService {

	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public RegisterService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
     * Checks whether the given email already exists in Client or Lawyer table.
	 * @throws SQLException 
     */
	public boolean isEmailExists(String email) throws SQLException {
	    if (dbConn == null) {
	        System.err.println("Database connection is not available.");
	        return false;
	    }

	    String clientQuery = "SELECT clientEmail FROM Client WHERE clientEmail = ?";
	    String lawyerQuery = "SELECT lawyerEmail FROM Lawyer WHERE lawyerEmail = ?";

	    try (PreparedStatement psClient = dbConn.prepareStatement(clientQuery)) {
	        psClient.setString(1, email);
	        try (ResultSet rsClient = psClient.executeQuery()) {
	            if (rsClient.next()) {
	                return true;
	            }
	        }
	    }

	    try (PreparedStatement psLawyer = dbConn.prepareStatement(lawyerQuery)) {
	        psLawyer.setString(1, email);
	        try (ResultSet rsLawyer = psLawyer.executeQuery()) {
	            if (rsLawyer.next()) {
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

    /**
     * Registers a new Client in the database.
     *
     * @param client the Client details to be registered
     * @return Boolean indicating success or null if error
     */
	public Boolean addClient(ClientModel client) throws SQLException {
	    if (dbConn == null) {
	        System.err.println("Database connection is not available.");
	        return false; // Return false instead of null
	    }

	    String insertQuery = "INSERT INTO Client (clientName, clientEmail, clientAddress, clientNumber, dateOfBirth, Gender, clientPassword) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
	        insertStmt.setString(1, client.getClientName());
	        insertStmt.setString(2, client.getClientEmail());
	        insertStmt.setString(3, client.getClientAddress());
	        insertStmt.setString(4, client.getClientNumber());
	        insertStmt.setDate(5, java.sql.Date.valueOf(client.getDateOfBirth()));
	        insertStmt.setString(6, client.getGender());
	        insertStmt.setString(7, client.getClientPassword());

	        return insertStmt.executeUpdate() > 0;

	    } catch (SQLException e) {
	        System.err.println("Error during client registration: " + e.getMessage());
	        e.printStackTrace();
	        return false; // Return false on error
	    }
	}
    /**
     * Registers a new Lawyer in the database.
     *
     * @param lawyer Lawyer object containing details
     * @return true if registration is successful, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean addLawyer(LawyerModel lawyer) throws SQLException {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return false;
        }

        String sql = "INSERT INTO Lawyer (lawyerName, lawyerEmail, lawyerNumber,lawyerPassword, licenseNumber, dateJoined, Gender, district, province) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, lawyer.getLawyerName());
            ps.setString(2, lawyer.getLawyerEmail());
            ps.setString(3, lawyer.getLawyerNumber());
            ps.setString(4, lawyer.getLawyerPassword()); // assume hashed
            ps.setString(5, lawyer.getLicenseNumber());
            ps.setDate(6, java.sql.Date.valueOf(lawyer.getDateJoined()));
            ps.setString(7, lawyer.getGender());
            ps.setString(8, lawyer.getDistrict());
            ps.setString(9, lawyer.getProvince());

            return ps.executeUpdate() > 0;
        }
    }

}