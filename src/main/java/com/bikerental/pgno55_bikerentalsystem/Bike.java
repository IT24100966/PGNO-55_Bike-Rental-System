package com.bikerental.pgno55_bikerentalsystem;

public class Bike {
    private String bikeId;
    private String model;
    private int availability;

    public Bike(String bikeId, String model, int availability) {
        this.bikeId = bikeId;
        this.model = model;
        this.availability = availability;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getModel() {
        return model;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "bikeId='" + bikeId + '\'' +
                ", model='" + model + '\'' +
                ", availability=" + availability +
                '}';
    }
}