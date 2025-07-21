package com.eNyaya.service;

import com.eNyaya.config.DbConfig;
import com.eNyaya.model.LawyerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LawyerService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor that initializes the database connection.
     * Sets a flag if the connection cannot be established.
     */
    public LawyerService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Fetches a single lawyer by ID.
     * @param id lawyer ID
     * @return lawyerModel or null if not found or on error
     */

    public LawyerModel getLawyerById(int id) {
        if (isConnectionError) return null;

        String sql =
                "SELECT lawyerID, lawyerName, licenseNumber, dateJoined, lawyerEmail, Gender, lawyerNumber, district, province "
                        + "FROM Lawyer "
                        + "WHERE lawyerID = ?";

        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("[Service] Row found for ID=" + id);
                    return new LawyerModel(
                            rs.getInt("lawyerID"),
                            rs.getString("lawyerName"),
                            rs.getString("licenseNumber"),
                            rs.getDate("dateJoined").toLocalDate(),
                            rs.getString("lawyerEmail"),
                            rs.getString("Gender"),
                            rs.getString("lawyerNumber"),
                            rs.getString("district"),
                            rs.getString("province")
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

    public boolean updateLawyer(LawyerModel lawyer) {
        if (isConnectionError) return false;

        String sql =
                "UPDATE lawyer SET " +
                        "lawyerName = ?, licenseNumber = ?, dateJoined = ?, lawyerEmail = ?, " +
                        "Gender = ?, lawyerNumber = ?, district = ?, province = ? " +
                        "WHERE lawyerID = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, lawyer.getLawyerName());
            ps.setString(2, lawyer.getLicenseNumber());
            ps.setDate(3, Date.valueOf(lawyer.getDateJoined()));
            ps.setString(4, lawyer.getLawyerEmail());
            ps.setString(5, lawyer.getGender());
            ps.setString(6, lawyer.getLawyerNumber());
            ps.setString(7, lawyer.getDistrict());
            ps.setString(8, lawyer.getProvince());
            ps.setInt(9, lawyer.getLawyerID());
            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LawyerModel> getAllLawyers() {
        if (isConnectionError) return Collections.emptyList();

        String sql = "SELECT lawyerID, lawyerName, licenseNumber, dateJoined, "
                + "lawyerEmail, Gender, lawyerNumber, district, province "
                + "FROM Lawyer";

        List<LawyerModel> lawyers = new ArrayList<>();
        try (PreparedStatement ps = dbConn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lawyers.add(new LawyerModel(
                		rs.getInt("lawyerID"),
                        rs.getString("lawyerName"),
                        rs.getString("licenseNumber"),
                        rs.getDate("dateJoined").toLocalDate(),
                        rs.getString("lawyerEmail"),
                        rs.getString("Gender"),
                        rs.getString("lawyerNumber"),
                        rs.getString("district"),
                        rs.getString("province")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lawyers;
    }
    
    public List<LawyerModel> searchByKeyword(String keyword) {
        if (isConnectionError) return Collections.emptyList();

        String sql = """
            SELECT lawyerID, lawyer_name, licenseNumber, dateJoined,
                   lawyerEmail, Gender, lawyerNumber, district, province
              FROM Lawyer
             WHERE lawyerName LIKE ?
                OR district    LIKE ?
                OR province    LIKE ?
             ORDER BY dateJoined ASC
            """;

        List<LawyerModel> list = new ArrayList<>();
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            String kw = "%" + keyword.trim() + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new LawyerModel(
                        rs.getInt("lawyerID"),
                        rs.getString("lawyerName"),
                        rs.getString("licenseNumber"),
                        rs.getDate("dateJoined").toLocalDate(),
                        rs.getString("lawyerEmail"),
                        rs.getString("Gender"),
                        rs.getString("lawyerNumber"),
                        rs.getString("district"),
                        rs.getString("province")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}