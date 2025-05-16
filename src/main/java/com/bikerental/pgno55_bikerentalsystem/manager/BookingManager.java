package com.bikerental.pgno55_bikerentalsystem.manager;

import com.bikerental.pgno55_bikerentalsystem.model.Booking;
import com.bikerental.pgno55_bikerentalsystem.model.RideBooking;
import com.bikerental.pgno55_bikerentalsystem.model.RentalBooking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingManager {
    private static final String FILE_PATH = "E:/New OOP Project/bookings.txt";

    static {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
        if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException e) {
                throw new RuntimeException("Failed to create bookings file: " + e.getMessage(), e);
            }
        }
    }

    public void createBooking(Booking booking) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(booking.getBookingId() + "," + booking.getUserId() + "," + booking.getBikeId() + "," +
                    booking.getType() + "," + booking.getTime() + "," +
                    (booking instanceof RideBooking ? ((RideBooking) booking).getDestination() + "," + ((RideBooking) booking).getDistanceKm() : ((RentalBooking) booking).getDurationHours()) + "," +
                    booking.calculatePrice());
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Failed to create booking due to file access issue: " + e.getMessage(), e);
        }
    }

    public List<Booking> readBookings(String userId) throws IOException {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(userId)) {
                    String bookingId = parts[0];
                    String bikeId = parts[2];
                    String type = parts[3];
                    String time = parts[4];
                    if (type.equals("Ride")) {
                        String destination = parts[5];
                        double distanceKm = Double.parseDouble(parts[6]);
                        bookings.add(new RideBooking(bookingId, userId, bikeId, time, destination, distanceKm));
                    } else {
                        int durationHours = Integer.parseInt(parts[5]);
                        bookings.add(new RentalBooking(bookingId, userId, bikeId, time, durationHours));
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to read bookings due to file access issue: " + e.getMessage(), e);
        }
        return bookings;
    }

    public List<Booking> readAllBookings() throws IOException {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String bookingId = parts[0];
                String userId = parts[1];
                String bikeId = parts[2];
                String type = parts[3];
                String time = parts[4];
                if (type.equals("Ride")) {
                    String destination = parts[5];
                    double distanceKm = Double.parseDouble(parts[6]);
                    bookings.add(new RideBooking(bookingId, userId, bikeId, time, destination, distanceKm));
                } else {
                    int durationHours = Integer.parseInt(parts[5]);
                    bookings.add(new RentalBooking(bookingId, userId, bikeId, time, durationHours));
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to read all bookings due to file access issue: " + e.getMessage(), e);
        }
        return bookings;
    }

    public List<Booking> readBookings() throws IOException {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String bookingId = parts[0];
                String userId = parts[1];
                String bikeId = parts[2];
                String type = parts[3];
                String time = parts[4];
                if (type.equals("Ride")) {
                    String destination = parts[5];
                    double distanceKm = Double.parseDouble(parts[6]);
                    bookings.add(new RideBooking(bookingId, userId, bikeId, time, destination, distanceKm));
                } else {
                    int durationHours = Integer.parseInt(parts[5]);
                    bookings.add(new RentalBooking(bookingId, userId, bikeId, time, durationHours));
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to read bookings due to file access issue: " + e.getMessage(), e);
        }
        return bookings;
    }

    public void updateBooking(Booking updatedBooking) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(updatedBooking.getBookingId())) {
                    String newLine = updatedBooking.getBookingId() + "," + updatedBooking.getUserId() + "," +
                            updatedBooking.getBikeId() + "," + updatedBooking.getType() + "," +
                            updatedBooking.getTime() + "," +
                            (updatedBooking instanceof RideBooking ? ((RideBooking) updatedBooking).getDestination() + "," + ((RideBooking) updatedBooking).getDistanceKm() : ((RentalBooking) updatedBooking).getDurationHours()) + "," +
                            updatedBooking.calculatePrice();
                    lines.add(newLine);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to update booking due to file access issue: " + e.getMessage(), e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Failed to write updated booking due to file access issue: " + e.getMessage(), e);
        }
    }

    public void deleteBooking(String bookingId) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(bookingId + ",")) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to delete booking due to file access issue: " + e.getMessage(), e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Failed to write after deleting booking due to file access issue: " + e.getMessage(), e);
        }
    }
}
