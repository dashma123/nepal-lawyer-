package com.eNyaya.service;

import com.eNyaya.config.DbConfig;
import com.eNyaya.model.ClientModel;
import java.sql.*;

public class ClientService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor that initializes the database connection.
     * Sets a flag if the connection cannot be established.
     */
    public ClientService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * @param id lawyer ID
     * @return lawyerModel or null if not found or on error
     */
    
    public ClientModel getClientById(int id) {
        if (isConnectionError) return null;

        String sql =
          "SELECT clientID, clientName, clientEmail, clientAddress, clientNumber, clientPassword, dateOfBirth, Gender "
        + "FROM client "
        + "WHERE clientID = ?";

        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("[Service] Row found for ID=" + id);
                    return new ClientModel(
                    	    rs.getInt("clientID"),                       
                    	    rs.getString("clientName"),                  
                    	    rs.getString("clientEmail"),                 
                    	    rs.getString("clientNumber"),                
                    	    rs.getString("clientPassword"),              
                    	    rs.getDate("dateOfBirth").toLocalDate(),     
                    	    rs.getString("Gender"),                     
                    	    rs.getString("clientAddress")                
                    	);
                } else {
                    System.out.println("[Service] No row for ID=" + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Look up a client by email instead of by ID.
     */
    public ClientModel getClientByEmail(String email) {
        if (isConnectionError) return null;

        String sql =
          "SELECT clientID, clientName, clientEmail, clientNumber, " +
          "clientPassword, dateOfBirth, Gender, clientAddress " +
          "FROM client " +
          "WHERE clientEmail = ?";

        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ClientModel(
                        // 1) clientID
                        rs.getInt("clientID"),
                        // 2) clientName
                        rs.getString("clientName"),
                        // 3) clientEmail
                        rs.getString("clientEmail"),
                        // 4) clientNumber
                        rs.getString("clientNumber"),
                        // 5) clientPassword
                        rs.getString("clientPassword"),
                        // 6) dateOfBirth
                        rs.getDate("dateOfBirth").toLocalDate(),
                        // 7) gender
                        rs.getString("Gender"),
                        // 8) clientAddress
                        rs.getString("clientAddress")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateClient(ClientModel client) {
        if (isConnectionError) return false;

        String sql = 
          "UPDATE client SET " +
          "clientEmail = ?, clientAddress = ?, clientNumber = ?, clientPassword = ? " +
          "WHERE clientID = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, client.getClientEmail());
            ps.setString(2, client.getClientAddress());
            ps.setString(3, client.getClientNumber());
            ps.setString(4, client.getClientPassword());
            ps.setInt(5, client.getClientID());
            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}