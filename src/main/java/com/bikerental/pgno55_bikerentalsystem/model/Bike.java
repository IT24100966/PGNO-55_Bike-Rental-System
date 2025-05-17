package com.bikerental.pgno55_bikerentalsystem.model;

public abstract class Bike  {
    private String bikeId;
    private String location;
    private double pricePerHour;
    private boolean isAvailable;

    public Bike(String bikeId, String location, double pricePerHour, boolean isAvailable) {
        this.bikeId = bikeId;
        this.location = location;
        this.pricePerHour = pricePerHour;
        this.isAvailable = isAvailable;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract String getBikeType();
}