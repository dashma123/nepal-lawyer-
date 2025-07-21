package com.eNyaya.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eNyaya.config.DbConfig;
import com.eNyaya.util.SessionUtil;

import jakarta.servlet.http.HttpServletRequest;
//import com.eNyaya.util.PasswordUtil;

/**
 * Service class for handling login operations.
 */
public class LoginService {
	private static final int ADMIN_ID = 0;
    private static final String ADMIN_EMAIL = "admin@enyaya.com";
    private static final String ADMIN_PASSWORD = "admin";

    private Connection dbConn;
    private boolean isConnectionError = false;

    public LoginService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Returns:
     * - "admin" for successful admin login
     * - "client" for successful client login
     * - "lawyer" for successful lawyer login
     * - "wrong_password" if email found but password does not match
     * - "not_found" if email not registered in any table
     * - "error" for DB connection or other issues
     */
    public String login(String email, String password, HttpServletRequest request) {
        if (isConnectionError) {
            return "error";
        }

        
        // admin login
        if (email.equalsIgnoreCase(ADMIN_EMAIL)) {
            if (password.equals(ADMIN_PASSWORD)) {
            	SessionUtil.setAttribute(request, "AdminID", ADMIN_ID);
                SessionUtil.setAttribute(request, "email", email);
                SessionUtil.setAttribute(request, "role", "admin");
                return "admin";
            } else {
                return "wrong_password";
            }
        }
        
        // Check in lawyer table
        String result = checkUserInTable(email, password, "Lawyer", "lawyerEmail", "lawyerPassword", "lawyerID","lawyerName", request);
		if ("success".equals(result)) return "lawyer";
		if ("wrong_password".equals(result)) return "wrong_password";

        // Check in client table
        result = checkUserInTable(email, password, "Client", "clientEmail", "clientPassword", "clientID", "clientName", request);
        if ("success".equals(result)) return "client";
        if ("wrong_password".equals(result)) return "wrong_password";

        

        return "not_found";
    }

    
    /**
     * Checks user credentials in the given table.
     * Sets session attributes if successful.
     *
     * @param email    User email
     * @param password User password
     * @param table    Table name ("Client" or "Lawyer")
     * @param emailCol Email column name
     * @param passCol  Password column name
     * @param idCol    ID column name
     * @param request  HttpServletRequest for session
     * @return "success" if valid, "wrong_password" if invalid password,
     *         "error" on exception, "not_found" if no record
     */
    private String checkUserInTable(String email, String password, String table, String emailCol, String passCol, String idCol, String nameCol, HttpServletRequest request) {
        String query = "SELECT " + emailCol + ", " + passCol + "," + idCol + "," + nameCol +" FROM " + table + " WHERE " + emailCol + " = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String dbEmail = result.getString(emailCol);
                int userID = result.getInt(idCol);
                String dbName = result.getString(nameCol);
                String dbPassword = result.getString(passCol);
                
                System.out.println(email + password + dbEmail + dbPassword  );
                

                //String decryptedPassword = PasswordUtil.decrypt(dbPassword, dbEmail);

                if (dbPassword == null || !dbPassword.equals(password)) {
                    return "wrong_password";
                }

             // Use SessionUtil to set session attributes
                SessionUtil.setAttribute(request, idCol, userID);
                SessionUtil.setAttribute(request, nameCol, dbName);
                SessionUtil.setAttribute(request, emailCol, email);
                SessionUtil.setAttribute(request, "role", table.toLowerCase());
                
                return "success";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

        return "not_found";
    }
}