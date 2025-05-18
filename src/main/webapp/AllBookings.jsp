<%@ page import="model.Booking" %>
<%@ page import="java.util.List" %>
<%@ page import="manager.BookingManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Bookings | Admin Panel</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome 6 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary: #6366f1;
            --primary-hover: #4f46e5;
            --success: #10b981;
            --info: #00C000;
            --warning: #f59e0b;
            --danger: #ef4444;
            --light: #f1f5f9;
            --dark: #0f172a;
            --border-radius: 0.5rem;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8fafc;
        }

        .card {
            border: none;
            border-radius: var(--border-radius);
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }

        .card-header {
            background-color: white;
            border-bottom: 1px solid #e2e8f0;
            font-weight: 600;
        }

        .table th {
            background-color: var(--light);
            color: var(--dark);
            font-weight: 600;
        }

        .badge {
            font-weight: 500;
            padding: 0.35em 0.65em;
        }

        .status-badge {
            display: inline-block;
            padding: 0.35rem 0.75rem;
            border-radius: 50px;
            font-size: 0.75rem;
            font-weight: 600;
        }

        .status-active {
            background-color: rgba(0, 128, 128, 0.1);
            color: var(--success);
        }

        .status-completed {
            background-color: rgba(59, 130, 246, 0.1);
            color: var(--info);
        }

        .status-cancelled {
            background-color: rgba(239, 68, 68, 0.1);
            color: var(--danger);
        }

        .action-buttons .btn {
            margin-right: 0.5rem;
        }

        .error-message {
            color: var(--danger);
            padding: 1rem;
            background-color: rgba(239, 68, 68, 0.1);
            border-radius: var(--border-radius);
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="container-fluid py-4">
    <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="mb-0"><i class="fas fa-calendar-alt me-2"></i>All Bookings (FIFO Order)</h5>
            <div>
                <form action="bookingHistory" method="GET" class="d-inline">
                    <div class="input-group">
                        <input type="text" name="userId" class="form-control" placeholder="Search by User ID...">
                        <button class="btn btn-primary" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </form>
                <a href="booking?action=create" class="btn btn-success ms-2">
                    <i class="fas fa-plus me-1"></i>New Booking
                </a>
            </div>
        </div>
        <div class="card-body">
            <%-- Error handling section --%>
            <%
                List<Booking> bookings = null;
                try {
                    BookingManager bookingManager = new BookingManager();
                    String userIdParam = request.getParameter("userId");
                    if (userIdParam != null && !userIdParam.isEmpty()) {
                        bookings = bookingManager.readBookings(userIdParam);
                    } else {
                        bookings = bookingManager.readBookings();
                    }
                } catch (Exception e) {
            %>
            <div class="error-message">
                <i class="fas fa-exclamation-circle me-2"></i>
                Error loading bookings: <%= e.getMessage() %>
            </div>
            <%
                }
            %>

            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Type</th>
                        <th>User ID</th>
                        <th>Bike ID</th>
                        <th>Time</th>
                        <th>Details</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if (bookings != null && !bookings.isEmpty()) {
                            for (Booking b : bookings) {
                    %>
                    <tr>
                        <td><%= b.getBookingId() %></td>
                        <td><span class="badge bg-primary"><%= b.getType() %></span></td>
                        <td><%= b.getUserId() %></td>
                        <td><%= b.getBikeId() %></td>
                        <td><%= b.getTime() %></td>
                        <td>
                            <% if (b.getType().equals("Ride")) { %>
                            Destination: <%= ((model.RideBooking)b).getDestination() %><br>
                            Distance: <%= ((model.RideBooking)b).getDistanceKm() %> km
                            <% } else { %>
                            Duration: <%= ((model.RentalBooking)b).getDurationHours() %> hours
                            <% } %>
                        </td>
                        <td class="fw-bold">Rs.<%= String.format("%.2f", b.calculatePrice()) %></td>
                        <td>
                            <span class="status-badge status-active">Active</span>
                        </td>
                        <td class="action-buttons">
                            <a href="booking?action=edit&bookingId=<%= b.getBookingId() %>" class="btn btn-sm btn-primary" title="Edit">
                                <i class="fas fa-edit"></i>
                            </a>
                            <form action="booking" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="bookingId" value="<%= b.getBookingId() %>">
                                <button type="submit" class="btn btn-sm btn-danger" title="Delete" onclick="return confirm('Are you sure you want to delete this booking?')">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                    <% }
                    } else { %>
                    <tr>
                        <td colspan="9" class="text-center py-4 text-muted">
                            <i class="fas fa-calendar-times fa-2x mb-3"></i>
                            <h5>No bookings found</h5>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Initialize tooltips
    document.addEventListener('DOMContentLoaded', function() {
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[title]'));
        tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });
    });
</script>
<div class="card-header d-flex justify-content-between align-items-center">
    <div>
        <a href="index.jsp" class="btn btn-outline-secondary me-2">
            <i class="fas fa-home me-1"></i>Back to Home
        </a>
    </div>
</div>
</body>
</html>