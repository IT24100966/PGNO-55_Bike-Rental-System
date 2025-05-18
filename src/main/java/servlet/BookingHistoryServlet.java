package servlet;

import manager.BookingManager;
import model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bookingHistory")
public class BookingHistoryServlet extends HttpServlet {
    private BookingManager bookingManager = new BookingManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get search parameter if exists
        String userId = request.getParameter("userId");

        List<Booking> bookings;
        if (userId != null && !userId.isEmpty()) {
            // Search for bookings by userId
            bookings = bookingManager.readBookings().stream()
                    .filter(b -> b.getUserId() != null && b.getUserId().equals(userId))
                    .toList();
            System.out.println("Searching for userId: " + userId + ", found " + bookings.size() + " bookings");
        } else {
            // Get all bookings
            bookings = bookingManager.readBookings();
            System.out.println("No search parameter, total bookings: " + bookings.size());
        }

        if (bookings == null) {
            System.out.println("Bookings list is null from BookingManager.readBookings()");
        }
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/AllBookings.jsp").forward(request, response);
    }
}