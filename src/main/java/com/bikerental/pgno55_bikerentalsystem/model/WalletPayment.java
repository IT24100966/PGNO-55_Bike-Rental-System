package com.bikerental.pgno55_bikerentalsystem.model;

public class WalletPayment extends Payment {
    private String walletId;

    public WalletPayment(String paymentId, double amount, String userId, String status, String walletId) {
        super(paymentId, amount, userId, status);
        this.walletId = walletId;
    }

    @Override
    public String getPaymentDetails() {
        return "Wallet Payment [ID: " + getPaymentId() + ", Amount: " + getAmount() + ", Wallet ID: " + walletId + "]";
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }
}