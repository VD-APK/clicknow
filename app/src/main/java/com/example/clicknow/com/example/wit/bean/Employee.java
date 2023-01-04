package com.example.clicknow.com.example.wit.bean;

import java.util.Map;

public class Employee {

    private String empName;
    private Long mobileNumber;
    private String passCode;
    private int pinCode;
    private String jobType;
    private String rating;
    private Map<String,String> userRequested;
    private String aadharNumber;


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Map<String, String> getUserRequested() {
        return userRequested;
    }

    public void setUserRequested(Map<String, String> userRequested) {
        this.userRequested = userRequested;
    }
}
