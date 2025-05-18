<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book a Ride or Rental</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Flatpickr CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Book a Ride or Rental</h2>
    <form action="booking" method="post" class="mt-4" onsubmit="return validateForm()">
        <input type="hidden" name="action" value="<%= request.getAttribute("bookingId") != null ? "update" : "create" %>">
        <% if (request.getAttribute("bookingId") != null) { %>
        <input type="hidden" name="bookingId" value="<%= request.getAttribute("bookingId") %>">
        <% } %>
        <div class="mb-3">
            <label for="userId" class="form-label">User ID</label>
            <input type="text" class="form-control" id="userId" name="userId" required>
        </div>
        <div class="mb-3">
            <label for="bikeId" class="form-label">Bike ID</label>
            <input type="text" class="form-control" id="bikeId" name="bikeId" required>
        </div>
        <div class="mb-3">
            <label for="time" class="form-label">Date (YYYY-MM-DD)</label>
            <input type="text" class="form-control" id="time" name="time" placeholder="Click to select date" required>
        </div>
        <div class="mb-3">
            <label for="type" class="form-label">Booking Type</label>
            <select class="form-select" id="type" name="type" onchange="toggleFields()">
                <option value="Ride">Ride</option>
                <option value="Rental">Rental</option>
            </select>
        </div>
        <div id="rideFields" class="mb-3">
            <label for="destination" class="form-label">Destination</label>
            <input type="text" class="form-control" id="destination" name="destination" placeholder="City Center" required>
            <label for="distanceKm" class="form-label">Distance (km)</label>
            <input type="number" step="0.1" class="form-control" id="distanceKm" name="distanceKm" placeholder="5.0" required>
        </div>
        <div id="rentalFields" class="mb-3" style="display: none;">
            <label for="durationHours" class="form-label">Duration (hours)</label>
            <input type="number" class="form-control" id="durationHours" name="durationHours" placeholder="2" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit Booking</button>
        <div class="mt-3">
            <a href="index.jsp" class="btn btn-outline-secondary">
                <i class="fas fa-home me-1"></i>Back to Home
            </a>
        </div>
    </form>
</div>
<!-- Flatpickr JS -->
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script>
    // Initialize Flatpickr
    flatpickr("#time", {
        enableTime: false,
        dateFormat: "Y-m-d",
        minDate: "2025-05-17",
        defaultDate: "2025-05-17"
    });

    function toggleFields() {
        var type = document.getElementById("type").value;
        document.getElementById("rideFields").style.display = type === "Ride" ? "block" : "none";
        document.getElementById("rentalFields").style.display = type === "Rental" ? "block" : "none";
        // Reset required attributes based on visibility
        document.getElementById("distanceKm").required = type === "Ride";
        document.getElementById("durationHours").required = type === "Rental";
        document.getElementById("destination").required = type === "Ride";
    }

    function validateForm() {
        var type = document.getElementById("type").value;
        if (type === "Ride") {
            var distanceKm = document.getElementById("distanceKm").value;
            var destination = document.getElementById("destination").value;
            if (!distanceKm || distanceKm <= 0) {
                alert("Please enter a valid distance (km) greater than 0 for Ride booking.");
                return false;
            }
            if (!destination || destination.trim() === "") {
                alert("Please enter a destination for Ride booking.");
                return false;
            }
        } else if (type === "Rental") {
            var durationHours = document.getElementById("durationHours").value;
            if (!durationHours || durationHours <= 0) {
                alert("Please enter a valid duration (hours) greater than 0 for Rental booking.");
                return false;
            }
        }

        // Validate date
        var timeInput = document.getElementById("time").value;
        if (!timeInput) {
            alert("Please select a date.");
            return false;
        }

        // Ensure the selected date is in the future
        var selectedDate = new Date(timeInput);
        var currentDate = new Date("2025-05-17T17:15:00+05:30"); // Updated to current time
        if (selectedDate <= currentDate) {
            alert("Please select a date in the future.");
            return false;
        }

        return true;
    }
</script>
</body>
</html>