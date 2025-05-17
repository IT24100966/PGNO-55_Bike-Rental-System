package com.bikerental.pgno55_bikerentalsystem.model;

public class RegularUser extends User {
    private String drivingLicenseNumber;
    public String getDrivingLicenseNumber() { return drivingLicenseNumber; }
    public void setDrivingLicenseNumber(String drivingLicenseNumber) { this.drivingLicenseNumber = drivingLicenseNumber; }
}
