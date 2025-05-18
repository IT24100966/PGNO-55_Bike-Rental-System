package com.bikerental.pgno55_bikerentalsystem;

public class RentalRequest {
    private String userId;
    private String bikeId;

    public RentalRequest(String userId, String bikeId) {
        this.userId = userId;
        this.bikeId = bikeId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBikeId() {
        return bikeId;
    }

    @Override
    public String toString() {
        return "RentalRequest{" +
                "userId='" + userId + '\'' +
                ", bikeId='" + bikeId + '\'' +
                '}';
    }
}