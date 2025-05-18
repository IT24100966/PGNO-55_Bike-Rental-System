package manager;

import model.Booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private final CustomQueue<Booking> bookingQueue;
    private final BookingFileUtil fileUtil;

    public BookingManager() {
        this.bookingQueue = new CustomQueue<>();
        this.fileUtil = new BookingFileUtil();
        // Load existing bookings from file to queue on initialization
        try {
            List<Booking> existingBookings = fileUtil.readAllBookings();
            for (Booking booking : existingBookings) {
                bookingQueue.enqueue(booking);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize bookings from file: " + e.getMessage(), e);
        }
    }

    public void createBooking(Booking booking) throws IOException {
        bookingQueue.enqueue(booking);
        fileUtil.writeBooking(booking);
    }

    public List<Booking> readBookings(String userId) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookingQueue) {
            if (booking.getUserId().equals(userId)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    public List<Booking> readAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : bookingQueue) {
            bookings.add(booking);
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
            Booking current = bookingQueue.dequeue();
            if (current.getBookingId().equals(updatedBooking.getBookingId())) {
                tempQueue.enqueue(updatedBooking);
                updated = true;
            } else {
                tempQueue.enqueue(current);
            }
        }
        // Restore queue
        while (!tempQueue.isEmpty()) {
            bookingQueue.enqueue(tempQueue.dequeue());
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
            Booking current = bookingQueue.dequeue();
            if (!current.getBookingId().equals(bookingId)) {
                tempQueue.enqueue(current);
            } else {
                deleted = true;
            }
        }
        // Restore queue
        while (!tempQueue.isEmpty()) {
            bookingQueue.enqueue(tempQueue.dequeue());
        }
        if (deleted) {
            fileUtil.deleteBooking(bookingId);
        } else {
            throw new IOException("Booking with ID " + bookingId + " not found");
        }
    }
}