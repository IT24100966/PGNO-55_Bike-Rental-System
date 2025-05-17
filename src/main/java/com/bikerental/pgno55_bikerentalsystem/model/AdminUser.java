package com.bikerental.pgno55_bikerentalsystem.model;

public class AdminUser extends User {
    private String adminRole;
    public String getAdminRole() { return adminRole; }
    public void setAdminRole(String adminRole) { this.adminRole = adminRole; }
}