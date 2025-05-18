<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Booking" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Booking Confirmation</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <% Booking booking = (Booking) request.getAttribute("booking"); %>
  <% if (booking != null) { %>
  <h2>Booking Confirmed</h2>
  <p><%= booking.getDetails() %></p>
  <a href="bookingHistory?userId=<%= booking.getUserId() %>" class="btn btn-secondary">View Booking History</a>
  <% } else if (request.getAttribute("bookings") != null) { %>
  <h2>Booking History</h2>
  <% List<Booking> bookings = (List<Booking>) request.getAttribute("bookings"); %>
  <% if (bookings != null && !bookings.isEmpty()) { %>
  <table class="table table-striped">
    <thead>
    <tr>
      <th>Booking ID</th>
      <th>Type</th>
      <th>Bike ID</th>
      <th>Time</th>
      <th>Details</th>
      <th>Price</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <% for (Booking b : bookings) { %>
    <tr>
      <td><%= b.getBookingId() %></td>
      <td><%= b.getType() %></td>
      <td><%= b.getBikeId() %></td>
      <td><%= b.getTime() %></td>
      <td><%= b.getDetails() %></td>
      <td>Rs.<%= String.format("%.2f", b.calculatePrice()) %></td>
      <td>
        <a href="booking?action=edit&bookingId=<%= b.getBookingId() %>" class="btn btn-sm btn-warning">Edit</a>
        <form action="booking" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="bookingId" value="<%= b.getBookingId() %>">
          <button type="submit" class="btn btn-sm btn-danger">Cancel</button>
        </form>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <% } else { %>
  <p>No bookings found.</p>
  <% } %>
  <% } else { %>
  <div class="alert alert-danger" role="alert">
    Failed to process booking. Please try again.
  </div>
  <a href="booking.jsp" class="btn btn-primary">Try Again</a>
  <% } %>
</div>
</body>
</html>