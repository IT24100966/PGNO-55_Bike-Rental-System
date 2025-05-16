package com.bikerental.pgno55_bikerentalsystem.model;

public class ElectricBike extends Bike {
    private int batteryRange;

    public ElectricBike(String bikeId, String location, double pricePerHour, boolean isAvailable, int batteryRange) {
        super(bikeId, location, pricePerHour, isAvailable);
        this.batteryRange = batteryRange;
    }

    public int getBatteryRange() {
        return batteryRange;
    }

    public void setBatteryRange(int batteryRange) {
        this.batteryRange = batteryRange;
    }

    @Override
    public String getBikeType() {
        return "Electric";
    }
}
