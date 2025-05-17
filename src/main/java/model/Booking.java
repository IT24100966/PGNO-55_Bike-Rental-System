package model;

public interface Booking {
    String getBookingId();
    String getUserId();
    String getBikeId();
    String getType();
    String getTime();
    double calculatePrice();
    String getDetails();
}
