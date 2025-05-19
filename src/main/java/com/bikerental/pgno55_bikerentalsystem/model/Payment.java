package com.bikerental.pgno55_bikerentalsystem.model;

public abstract class Payment {     //hides implementation details(abstract) that provides(only to admin)
    private String paymentId;           //Encapsulation
    private double amount;
    private String userId;
    private String status; // e.g., "Pending", "Completed", "Failed"

    public Payment(String paymentId, double amount, String userId, String status) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.userId = userId;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract String getPaymentDetails();
}