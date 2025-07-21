package com.eNyaya.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eNyaya.config.DbConfig;

/**
 * Service‑layer class that gathers statistics and recent activity
 * for the Admin Dashboard.  No DAO layer is used – this class
 * talks to the database directly via JDBC, mirroring the pattern
 * in DashboardService of the college example.
 */
public class AdminDashboardService {

	private Connection dbConn;
    private boolean isConnectionError = false;

    public AdminDashboardService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /** Static inner class to hold stats data */
    public static class Stats {
        private int client;
        private int lawyer;
        private int appointment;
        private int completed;
        private int ongoing;
        private int cancelled;

        // Getters and setters
        public int getClient() { 
        	return client; 
        }
        public void setClient(int client) {
        	this.client = client; 
        }

        public int getLawyer() {
        	return lawyer; 
        	}
        public void setLawyer(int lawyer) { 
        	this.lawyer = lawyer; 
        }

        public int getAppointment() { 
        	return appointment; 
        }
        public void setAppointment(int appointment) { 
        	this.appointment = appointment; 
        }

        public int getCompleted() { 
        	return completed; 
        }
        public void setCompleted(int completed) { 
        	this.completed = completed; 
        }

        public int getOngoing() { 
        	return ongoing; 
        }
        public void setOngoing(int ongoing) { 
        	this.ongoing = ongoing; 
        }

        public int getCancelled() { 
        	return cancelled; 
        }
        public void setCancelled(int cancelled) { 
        	this.cancelled = cancelled; 
        }
    }

    /** Static inner class to hold recent appointment details */
    public static class RecentAppointment {
        private int appointmentId;
        private String clientName;
        private String lawyerName;
        private String status;
        private LocalDateTime dateTime;

        public RecentAppointment(int appointmentId, String clientName, String lawyerName, String status, LocalDateTime dateTime) {
            this.appointmentId = appointmentId;
            this.clientName = clientName;
            this.lawyerName = lawyerName;
            this.status = status;
            this.dateTime = dateTime;
        }

        public int getAppointmentId() { return appointmentId; }
        public String getClientName() { return clientName; }
        public String getLawyerName() { return lawyerName; }
        public String getStatus() { return status; }
        public LocalDateTime getDateTime() { return dateTime; }
    }

    /** Returns dashboard statistic */
    public Stats getStats() {
        if (isConnectionError) 
        	return null;

        Stats s = new Stats();
        s.setClient(singleInt("SELECT COUNT(*) FROM client"));
        s.setLawyer(singleInt("SELECT COUNT(*) FROM lawyer"));

        String apptCountSql =
            "SELECT " +
            "   COUNT(*) AS total, " +
            "   SUM(status = 'COMPLETED') AS completed, " +
            "   SUM(status = 'ONGOING') AS ongoing, " +
            "   SUM(status = 'CANCELLED') AS cancelled " +
            "FROM appointment";

        try (PreparedStatement ps = dbConn.prepareStatement(apptCountSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                s.setAppointment(rs.getInt("total"));
                s.setCompleted(rs.getInt("completed"));
                s.setOngoing(rs.getInt("ongoing"));
                s.setCancelled(rs.getInt("cancelled"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

        return s;
    }

    /** Returns a list of recent appointments */
    public List<RecentAppointment> getRecentAppointments(int limit) {
        if (isConnectionError) 
        	return null;

        String sql =
            "SELECT a.AppointmentID, a.Status, a.DateTime, " +
            "       c.C_Full_Name AS clientName, " +
            "       l.L_Full_Name AS lawyerName " +
            "FROM appointment a " +
            "JOIN client c ON a.ClientID = c.ClientID " +
            "JOIN lawyer l ON a.LawyerID = l.LawyerID " +
            "ORDER BY a.DateTime DESC " +
            "LIMIT ?";

        List<RecentAppointment> list = new ArrayList<>();

        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecentAppointment appt = new RecentAppointment(
                        rs.getInt("AppointmentID"),
                        rs.getString("clientName"),
                        rs.getString("lawyerName"),
                        rs.getString("Status"),
                        rs.getTimestamp("DateTime").toLocalDateTime()
                    );
                    list.add(appt);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

        return list;
    }

    /** Utility method for count queries */
    private int singleInt(String sql) {
        try (PreparedStatement ps = dbConn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}