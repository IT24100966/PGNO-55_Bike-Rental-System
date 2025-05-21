package model;

public class RentalBooking implements Booking {
    private String bookingId;
    private String userId;
    private String bikeId;
    private String time;
    private int durationHours;

    // Constructor
    public RentalBooking(String bookingId, String userId, String bikeId, String time, int durationHours) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.bikeId = bikeId;
        this.time = time;
        this.durationHours = durationHours;
    }


    //Getters And Setters
    @Override
    public String getBookingId() { return bookingId; }
    @Override
    public String getUserId() { return userId; }
    @Override
    public String getBikeId() { return bikeId; }
    @Override
    public String getType() { return "Rental"; }
    @Override
    public String getTime() { return time; }

    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }

    @Override
    public double calculatePrice() {
        return durationHours * 2000.00; // 2000 per hour
    }

    //Display Details
    @Override
    public String getDetails() {
        return "Booking ID: " + bookingId + "<br>" +
                "Type: Rental<br>" +
                "User: " + userId + "<br>" +
                "Bike ID: " + bikeId + "<br>" +
                "Time: " + time + "<br>" +
                "Duration: " + durationHours + " hours<br>" +
                "Price: Rs." + String.format("%.2f", calculatePrice());
    }
}