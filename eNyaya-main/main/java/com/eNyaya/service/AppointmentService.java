package com.eNyaya.service;


import java.sql.*;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eNyaya.config.DbConfig;
import com.eNyaya.model.AppointmentModel;

public class AppointmentService {

		private Connection dbConn;
	    private boolean connError = false;

	    public AppointmentService() {
	        try {
	            dbConn = DbConfig.getDbConnection();
	        } catch (Exception e) {
	            e.printStackTrace();
	            connError = true;
	        }
	    }
	
	public List<AppointmentModel> getBookedSlots(int lawyerId, LocalDate date) {
        if (connError) return List.of();
        String sql = """
        	    SELECT a.Appointment_Date,
        	           a.Start_Time,
        	           a.End_Time
        	      FROM Appointment a
        	      JOIN client_lawyer_appointment cla
        	        ON a.AppointmentID = cla.AppointmentID
        	     WHERE cla.lawyerID = ?
        	       AND a.Appointment_Date = ?
        	     ORDER BY a.Start_Time
        	    """;
        List<AppointmentModel> list = new ArrayList<>();
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setInt(1, lawyerId);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentModel a = new AppointmentModel();
                    a.setAppointmentDate(rs.getDate("Appointment_Date").toLocalDate());
                    a.setStartTime  (rs.getTime("Start_Time").toLocalTime());
                    a.setEndTime    (rs.getTime("End_Time").toLocalTime());
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    

    public boolean bookAppointment(AppointmentModel appt) {
        if (connError) return false;
        try {
            dbConn.setAutoCommit(false);

            // 1) insert into appointment
            String sqlAppt = """
                INSERT INTO Appointment
                  (Appointment_Date, Start_Time, End_Time, Mode, Status, Appointment_Description)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
            try (PreparedStatement ps1 = dbConn.prepareStatement(
                   sqlAppt, Statement.RETURN_GENERATED_KEYS)) {

                ps1.setDate(1, Date.valueOf(appt.getAppointmentDate()));
                ps1.setTime(2, Time.valueOf(appt.getStartTime()));
                ps1.setTime(3, Time.valueOf(appt.getEndTime()));
                ps1.setString(4, appt.getMode());
                ps1.setString(5, appt.getStatus());
                ps1.setString(6, appt.getDescription());

                if (ps1.executeUpdate() != 1) {
                    dbConn.rollback();
                    return false;
                }
                try (ResultSet rs = ps1.getGeneratedKeys()) {
                    if (!rs.next()) {
                        dbConn.rollback();
                        return false;
                    }
                    appt.setAppointmentID(rs.getInt(1));
                }
            }

            // 2) insert into join table
            String sqlLink = """
            	    INSERT INTO client_lawyer_appointment
            	      (clientID, lawyerID, AppointmentID)
            	    VALUES (?, ?, ?)
            	    """;
            try (PreparedStatement ps2 = dbConn.prepareStatement(sqlLink)) {
                ps2.setInt(1, appt.getClientID());
                ps2.setInt(2, appt.getLawyerID());
                ps2.setInt(3, appt.getAppointmentID());
                if (ps2.executeUpdate() != 1) {
                    dbConn.rollback();
                    return false;
                }
            }

            dbConn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try { dbConn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;

        } finally {
            try { dbConn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }
    
    public List<AppointmentModel> getAppointmentsByClient(int clientId) {
        if (connError) return List.of();

        String sql = """
          SELECT a.AppointmentID,
                 a.Appointment_Date,
                 a.Start_Time,
                 a.End_Time,
                 a.Mode,
                 a.Status,
                 a.Appointment_Description,
                 l.lawyerName
            FROM Appointment a
            JOIN client_lawyer_appointment cla
              ON a.AppointmentID = cla.AppointmentID
            JOIN Lawyer l
              ON cla.lawyerID = l.lawyerID
           WHERE cla.clientID = ?
           ORDER BY a.Appointment_Date, a.Start_Time
          """;

        List<AppointmentModel> list = new ArrayList<>();
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            System.out.println("[AppointmentService] fetch for clientID=" + clientId + " → " + ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentModel appt = new AppointmentModel();
                    appt.setAppointmentID(      rs.getInt("AppointmentID"));
                    appt.setAppointmentDate(    rs.getDate("Appointment_Date").toLocalDate());
                    appt.setStartTime(          rs.getTime("Start_Time").toLocalTime());
                    appt.setEndTime(            rs.getTime("End_Time").toLocalTime());
                    appt.setMode(               rs.getString("Mode"));
                    appt.setStatus(             rs.getString("Status"));
                    appt.setDescription(        rs.getString("Appointment_Description"));
                    appt.setLawyerName(         rs.getString("lawyerName"));
                    list.add(appt);
                }
            }
        } catch (SQLException e) {
            System.err.println("[AppointmentService] error fetching appointments:");
            e.printStackTrace();
        }
        return list;
    }
	
	public List<AppointmentModel> getAllAppointments() {
	    List<AppointmentModel> appointmentList = new ArrayList<>();

	    String sql = "SELECT a.AppointmentID, c.ClientName, l.LawyerName, " +
	                 "a.Appointment_Date, a.Start_Time, a.End_Time, a.Status " +
	                 "FROM Appointment a " +
	                 "JOIN Client_Lawyer_Appointment acl ON a.AppointmentID = acl.AppointmentID " +
	                 "JOIN Client c ON acl.ClientID = c.ClientID " +
	                 "JOIN Lawyer l ON acl.LawyerID = l.LawyerID";

	    try (PreparedStatement pstmt = dbConn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            AppointmentModel appt = new AppointmentModel();
	            appt.setAppointmentID(rs.getInt("AppointmentID"));
	            appt.setClientName(rs.getString("ClientName"));
	            appt.setLawyerName(rs.getString("LawyerName"));
	            appt.setAppointmentDate(rs.getDate("Appointment_Date").toLocalDate()); 

	            String start = rs.getString("Start_Time");
	            String end = rs.getString("End_Time");
	            
	            appt.setTime(start + " - " + end);

	            appt.setStatus(rs.getString("Status"));

	            appointmentList.add(appt);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return appointmentList;
	}

    /**
     * Update the appointment date and time by appointment ID.
     */
    public boolean rescheduleAppointment(int appointmentId, String newDate, String newTime) {
        String sql = "UPDATE Appointment SET Appointment_Date = ?, Start_Time = ?, End_Time = ? WHERE AppointmentID = ?";
        // Assuming time format is "HH:mm - HH:mm", split start and end time:
        String[] times = newTime.split(" - ");
        if (times.length != 2) {
            return false;
        }
        String startTime = times[0];
        String endTime = times[1];

        try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
            pstmt.setString(1, newDate);   // Assuming date is stored as String in 'yyyy-MM-dd' format
            pstmt.setString(2, startTime);
            pstmt.setString(3, endTime);
            pstmt.setInt(4, appointmentId);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}