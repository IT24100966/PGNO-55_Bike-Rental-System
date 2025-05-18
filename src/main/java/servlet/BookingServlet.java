package servlet;

import manager.BookingManager;
import model.Booking;
import model.RideBooking;
import model.RentalBooking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private BookingManager bookingManager = new BookingManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            String bookingId = request.getParameter("bookingId");
            request.setAttribute("bookingId", bookingId);
            request.getRequestDispatcher("/booking.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/booking.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        String bikeId = request.getParameter("bikeId");
        String time = request.getParameter("time");
        String type = request.getParameter("type");

        if ("create".equals(action)) {
            String bookingId = UUID.randomUUID().toString();
            Booking booking;
            if ("Ride".equals(type)) {
                String destination = request.getParameter("destination");
                String distanceKmStr = request.getParameter("distanceKm");
                if (distanceKmStr == null || distanceKmStr.trim().isEmpty()) {
                    throw new ServletException("Distance (km) is required for Ride booking.");
                }
                double distanceKm = Double.parseDouble(distanceKmStr);
                booking = new RideBooking(bookingId, userId, bikeId, time, destination, distanceKm);
            } else {
                String durationHoursStr = request.getParameter("durationHours");
                if (durationHoursStr == null || durationHoursStr.trim().isEmpty()) {
                    throw new ServletException("Duration (hours) is required for Rental booking.");
                }
                int durationHours = Integer.parseInt(durationHoursStr);
                booking = new RentalBooking(bookingId, userId, bikeId, time, durationHours);
            }
            bookingManager.createBooking(booking);
            request.setAttribute("booking", booking);
            request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
        } else if ("update".equals(action)) {
            String bookingId = request.getParameter("bookingId");
            Booking booking;
            if ("Ride".equals(type)) {
                String destination = request.getParameter("destination");
                String distanceKmStr = request.getParameter("distanceKm");
                if (distanceKmStr == null || distanceKmStr.trim().isEmpty()) {
                    throw new ServletException("Distance (km) is required for Ride booking.");
                }
                double distanceKm = Double.parseDouble(distanceKmStr);
                booking = new RideBooking(bookingId, userId, bikeId, time, destination, distanceKm);
            } else {
                String durationHoursStr = request.getParameter("durationHours");
                if (durationHoursStr == null || durationHoursStr.trim().isEmpty()) {
                    throw new ServletException("Duration (hours) is required for Rental booking.");
                }
                int durationHours = Integer.parseInt(durationHoursStr);
                booking = new RentalBooking(bookingId, userId, bikeId, time, durationHours);
            }
            bookingManager.updateBooking(booking);
            request.setAttribute("booking", booking);
            request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            String bookingId = request.getParameter("bookingId");
            bookingManager.deleteBooking(bookingId);
            response.sendRedirect("bookingHistory");
        }
    }
}