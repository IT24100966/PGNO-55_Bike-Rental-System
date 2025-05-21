package manager;

import model.Booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    // Custom queue to store bookings
    private final CustomQueue<Booking> bookingQueue;
    //handle file operations
    private final BookingFileUtil fileUtil;

    public BookingManager() {
        //Constructor
        this.bookingQueue = new CustomQueue<>();
        this.fileUtil = new BookingFileUtil();

        // Load existing bookings from the file into the queue
        try {
            List<Booking> existingBookings = fileUtil.readAllBookings();
            for (Booking booking : existingBookings) {
                bookingQueue.insert(booking);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize bookings from file: " + e.getMessage(), e);
        }
    }

    public void createBooking(Booking booking) throws IOException {
        bookingQueue.insert(booking);
        fileUtil.writeBooking(booking);
    }
    // Method to read all bookings for a specific user based on userId
    public List<Booking> readBookings(String userId) {
        List<Booking> userBookings = new ArrayList<>();
        for (int i = 0; i < bookingQueue.getSize(); i++) {
            Booking booking = bookingQueue.get(i);
            if (booking.getUserId().equals(userId)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    public List<Booking> readAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        for (int i = 0; i < bookingQueue.getSize(); i++) {
            bookings.add(bookingQueue.get(i));
        }
        return bookings;
    }

    public List<Booking> readBookings() {
        return readAllBookings();
    }

    public void updateBooking(Booking updatedBooking) throws IOException {
        CustomQueue<Booking> tempQueue = new CustomQueue<>();
        boolean updated = false;
        while (!bookingQueue.isEmpty()) {
            Booking current = bookingQueue.remove();
            if (current.getBookingId().equals(updatedBooking.getBookingId())) {
                tempQueue.insert(updatedBooking);
                updated = true;
            } else {
                tempQueue.insert(current);
            }
        }
        // Restore queue
        while (!tempQueue.isEmpty()) {
            bookingQueue.insert(tempQueue.remove());
        }
        if (updated) {
            fileUtil.updateBooking(updatedBooking);
        } else {
            throw new IOException("Booking with ID " + updatedBooking.getBookingId() + " not found");
        }
    }

    public void deleteBooking(String bookingId) throws IOException {
        CustomQueue<Booking> tempQueue = new CustomQueue<>();
        boolean deleted = false;
        while (!bookingQueue.isEmpty()) {
            Booking current = bookingQueue.remove();
            if (!current.getBookingId().equals(bookingId)) {
                tempQueue.insert(current);
            } else {
                deleted = true;
            }
        }
        // Restore queue
        while (!tempQueue.isEmpty()) {
            bookingQueue.insert(tempQueue.remove());
        }
        if (deleted) {
            fileUtil.deleteBooking(bookingId);
        } else {
            throw new IOException("Booking with ID " + bookingId + " not found");
        }
    }
}