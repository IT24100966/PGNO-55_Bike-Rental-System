package com.bikerental.pgno55_bikerentalsystem.model;

public class RegularBike extends Bike {
    private String gearType;

    public RegularBike(String bikeId, String location, double pricePerHour, boolean isAvailable, String gearType) {
        super(bikeId, location, pricePerHour, isAvailable);
        this.gearType = gearType;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    @Override
    public String getBikeType() {
        return "Regular";
    }
}
