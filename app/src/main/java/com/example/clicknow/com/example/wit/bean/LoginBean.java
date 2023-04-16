package com.example.clicknow.com.example.wit.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {


    private long mobileNumber;
    private String passCode;
    private boolean isEmp;

    public boolean isEmp() {
        return isEmp;
    }

    public void setEmp(boolean emp) {
        isEmp = emp;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }
}
