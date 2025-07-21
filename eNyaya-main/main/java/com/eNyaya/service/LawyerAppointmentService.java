package com.eNyaya.service;

import java.sql.*;
import java.util.*;
import com.eNyaya.config.DbConfig;
import com.eNyaya.model.AppointmentModel;

public class LawyerAppointmentService {

	public List<AppointmentModel> getAppointmentsByLawyerId(int lawyerId) {
	    List<AppointmentModel> appointmentList = new ArrayList<>();
	    String sql = "SELECT ap.AppointmentID, ap.Appointment_Date, ap.Start_Time, ap.End_Time, ap.Mode, ap.Status, ap.Appointment_Description,\n"
	    		+ "	                 c.clientID, c.clientName\n"
	    		+ "	                 FROM Appointment ap\n"
	    		+ "	                 JOIN Client_Lawyer_Appointment acla ON ap.AppointmentID = acla.AppointmentID\n"
	    		+ "	                 JOIN Client c ON acla.clientID = c.clientID \n"
	    		+ "	                 WHERE acla.lawyerID = ? \n"
	    		+ "	                 ORDER BY ap.Appointment_Date DESC;";
	
	    try (Connection conn = DbConfig.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	
	        stmt.setInt(1, lawyerId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	            	AppointmentModel app = new AppointmentModel();
	                app.setAppointmentID(rs.getInt("AppointmentID"));
	                app.setAppointmentDate(rs.getObject("Appointment_Date", java.time.LocalDate.class));
	                app.setStartTime(rs.getObject("Start_Time", java.time.LocalTime.class));
	                app.setEndTime(rs.getObject("End_Time", java.time.LocalTime.class));
	                app.setMode(rs.getString("Mode"));
	                app.setStatus(rs.getString("Status"));
	                app.setDescription(rs.getString("Appointment_Description"));
	                app.setClientID(rs.getInt("clientID"));
	                app.setClientName(rs.getString("clientName"));
	                appointmentList.add(app);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    return appointmentList;
	}
}