package com.eNyaya.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eNyaya.config.DbConfig;
import com.eNyaya.model.ClientModel;
import com.eNyaya.model.LawyerModel;

public class AdminManageUserService{
	
	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public AdminManageUserService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public List<?> searchUsers(String role, String id, String name, String email, String phone) {
        List<Object> resultList = new ArrayList<>();
        StringBuilder query = new StringBuilder();

        if ("client".equalsIgnoreCase(role)) {
            query.append("SELECT * FROM Client WHERE 1=1");
        } else if ("lawyer".equalsIgnoreCase(role)) {
            query.append("SELECT * FROM Lawyer WHERE 1=1");
        } else {
            return resultList; // unsupported role
        }

        if (id != null && !id.isEmpty()) query.append(" AND " + getIdColumn(role) + " LIKE ?");
        if (name != null && !name.isEmpty()) query.append(" AND " + getNameColumn(role) + " LIKE ?");
        if (email != null && !email.isEmpty()) query.append(" AND " + getEmailColumn(role) + " LIKE ?");
        if (phone != null && !phone.isEmpty()) query.append(" AND " + getPhoneColumn(role) + " LIKE ?");

        try (PreparedStatement stmt = dbConn.prepareStatement(query.toString())) {

            int index = 1;
            if (id != null && !id.isEmpty()) stmt.setString(index++, "%" + id + "%");
            if (name != null && !name.isEmpty()) stmt.setString(index++, "%" + name + "%");
            if (email != null && !email.isEmpty()) stmt.setString(index++, "%" + email + "%");
            if (phone != null && !phone.isEmpty()) stmt.setString(index++, "%" + phone + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if ("client".equalsIgnoreCase(role)) {
                    ClientModel client = new ClientModel();
                    client.setClientID(rs.getInt("clientID"));
                    client.setClientName(rs.getString("clientName"));
                    client.setClientEmail(rs.getString("clientEmail"));
                    client.setClientNumber(rs.getString("clientNumber"));
                    resultList.add(client);
                } else if ("lawyer".equalsIgnoreCase(role)) {
                    LawyerModel lawyer = new LawyerModel();
                    lawyer.setLawyerID(rs.getInt("lawyerID"));
                    lawyer.setLawyerName(rs.getString("lawyerName"));
                    lawyer.setLawyerEmail(rs.getString("lawyerEmail"));
                    lawyer.setLawyerNumber(rs.getString("lawyerNumber"));
                    resultList.add(lawyer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private String getIdColumn(String role) {
        return "client".equalsIgnoreCase(role) ? "clientID" : "lawyerID";
    }

    private String getNameColumn(String role) {
        return "client".equalsIgnoreCase(role) ? "clientName" : "lawyerName";
    }

    private String getEmailColumn(String role) {
        return "client".equalsIgnoreCase(role) ? "clientEmail" : "lawyerEmail";
    }

    private String getPhoneColumn(String role) {
        return "client".equalsIgnoreCase(role) ? "clientPhone" : "lawyerNumber";
    }
    
    public boolean deleteUserById(String role, String id) {
        String query = "";
        if ("client".equalsIgnoreCase(role)) {
            query = "DELETE FROM Client WHERE clientID = ?";
        } else if ("lawyer".equalsIgnoreCase(role)) {
            query = "DELETE FROM Lawyer WHERE lawyerID = ?";
        } else {
            return false;
        }

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}