package com.eNyaya.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentModel {
	private int appointmentID;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String mode;
    private String status;
    private String description;
    private int clientID;
    private int lawyerID;
    private String clientName;
    private String lawyerName;
    private String practice;
    private String time;
    
    public AppointmentModel() {
    	
    }
    
	public AppointmentModel(int appointmentID, LocalDate appointmentDate, LocalTime startTime, LocalTime endTime,
			String mode, String status, String description, int clientID, int lawyerID, String clientName, String lawyerName, String practice, String time) {
		super();
		this.appointmentID = appointmentID;
		this.appointmentDate = appointmentDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mode = mode;
		this.status = status;
		this.description = description;
		this.clientID = clientID;
		this.lawyerID = lawyerID;
		this.clientName = clientName;
		this.lawyerName = lawyerName;
		this.practice = practice;
		this.time = time;
	}

	public int getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public int getLawyerID() {
		return lawyerID;
	}

	public void setLawyerID(int lawyerID) {
		this.lawyerID = lawyerID;
	}
    
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getLawyerName() {
		return lawyerName;
	}

	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}

	public String getPractice() {
		return practice;
	}

	public void setPractice(String practice) {
		this.practice = practice;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
