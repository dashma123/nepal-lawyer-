package com.eNyaya.model;

import java.time.LocalDate;
import java.time.Period;

public class LawyerModel {
	private int lawyerID;
    private String lawyerName;
    private String lawyerEmail;
    private String lawyerNumber;
    private String lawyerPassword;
    private String licenseNumber;
    private LocalDate dateJoined;
    private String Gender;
    private String district;
    private String province;
    
    public LawyerModel() {
    	
    }

    
    public LawyerModel(String lawyerEmail, String lawyerPassword) {
    	this.lawyerEmail = lawyerEmail;
    	this.lawyerPassword = lawyerPassword;
    	
    }
    
    public LawyerModel(String lawyerName, String lawyerEmail, String lawyerNumber, String Gender, String lawyerPassword,
			String licenseNumber, LocalDate dateJoined, String district, String province) {

		this.lawyerName = lawyerName;
		this.lawyerEmail = lawyerEmail;
		this.lawyerNumber = lawyerNumber;
		this.Gender = Gender;
		this.lawyerPassword = lawyerPassword;
		this.licenseNumber = licenseNumber;
		this.dateJoined = dateJoined;
		this.district = district;
		this.province = province;
	}

    
	public LawyerModel(int lawyerID, String lawyerName, String licenseNumber, LocalDate dateJoined, String lawyerEmail,
			String Gender, String lawyerNumber, String district, String province) {
		this.lawyerID = lawyerID; 
		this.lawyerName = lawyerName;
		this.lawyerEmail = lawyerEmail;
		this.lawyerNumber = lawyerNumber;
		this.licenseNumber = licenseNumber;
		this.dateJoined = dateJoined;
		this.district = district;
		this.Gender = Gender;
		this.province = province;
	}
	
    public LawyerModel(int lawyerID, String lawyerName, String lawyerEmail, String lawyerNumber) {
    	this.lawyerID = lawyerID; 
		this.lawyerName = lawyerName;
		this.lawyerEmail = lawyerEmail;
		this.lawyerNumber = lawyerNumber;
    }
	
	public String getLawyerName() {
		return lawyerName;
	}

	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}

	public String getLawyerEmail() {
		return lawyerEmail;
	}

	public void setLawyerEmail(String lawyerEmail) {
		this.lawyerEmail = lawyerEmail;
	}

	public String getLawyerNumber() {
		return lawyerNumber;
	}

	public void setLawyerNumber(String lawyerNumber) {
		this.lawyerNumber = lawyerNumber;
	}

	public String getLawyerPassword() {
		return lawyerPassword;
	}

	public void setLawyerPassword(String lawyerPassword) {
		this.lawyerPassword = lawyerPassword;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public LocalDate getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(LocalDate dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getLawyerID() {
		return lawyerID;
	}

	public void setLawyerID(int lawyerID) {
		this.lawyerID = lawyerID;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}
	
	public int getExperienceYears(LocalDate dateJoined) {
        if (dateJoined == null) return 0;
        return Period.between(dateJoined, LocalDate.now()).getYears();
    }
	

    
}