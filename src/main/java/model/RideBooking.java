package model;

public class RideBooking implements Booking {
    private String bookingId;
    private String userId;
    private String bikeId;
    private String time;
    private String destination;
    private double distanceKm;

    public RideBooking(String bookingId, String userId, String bikeId, String time, String destination, double distanceKm) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.bikeId = bikeId;
        this.time = time;
        this.destination = destination;
        this.distanceKm = distanceKm;
    }

    @Override
    public String getBookingId() { return bookingId; }
    @Override
    public String getUserId() { return userId; }
    @Override
    public String getBikeId() { return bikeId; }
    @Override
    public String getType() { return "Ride"; }
    @Override
    public String getTime() { return time; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    @Override
    public double calculatePrice() {
        return distanceKm * 500.00; // Rs.500 per km
    }

    @Override
    public String getDetails() {
        return "Booking ID: " + bookingId + "<br>" +
                "Type: Ride" + "<br>" +
                "User: " + userId + "<br>" +
                "Bike: " + bikeId + "<br>" +
                "Time: " + time + "<br>" +
                "Destination: " + destination + "<br>" +
                "Price: Rs." + String.format("%.2f", calculatePrice());
    }
}