package com.example.clicknow.com.example.wit.bean;

import java.util.List;

public class UserBean {

    private String userName;
    private Long mobileNumber;
    private String passCode;
    private int pinCode;
    private List<Integer> empRequested;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<Integer> getEmpRequested() {
        return empRequested;
    }

    public void setEmpRequested(List<Integer> empRequested) {
        this.empRequested = empRequested;
    }
}
