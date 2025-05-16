package com.bikerental.pgno55_bikerentalsystem.model;

public class CardPayment extends Payment {
    private String cardNumber;
    private String cardType;

    public CardPayment(String paymentId, double amount, String userId, String status, String cardNumber, String cardType) {
        super(paymentId, amount, userId, status);
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    @Override
    public String getPaymentDetails() {
        return "Card Payment [ID: " + getPaymentId() + ", Amount: " + getAmount() + ", Card Type: " + cardType + "]";
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
