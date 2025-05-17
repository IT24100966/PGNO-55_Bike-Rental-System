
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Bike</title>
    <!-- Bootstrap 5 CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<!-- Bootstrap Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/bikes">Bike Rental System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/bikes?action=view">View Bikes</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container mt-5 pt-4">
    <div class="card mx-auto" style="max-width: 28rem;">
        <h2>Add New Bike</h2>
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error" role="alert">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>
        <form action="${pageContext.request.contextPath}/bikes" method="post">
            <input type="hidden" name="action" value="add">
            <div class="form-group">
                <label for="bikeId">Bike ID</label>
                <input type="text" id="bikeId" name="bikeId" required class="form-control">
            </div>
            <div class="form-group">
                <label for="bikeType">Bike Type</label>
                <select id="bikeType" name="bikeType" onchange="toggleFields()" class="form-control">
                    <option value="Regular">Regular</option>
                    <option value="Electric">Electric</option>
                </select>
            </div>
            <div class="form-group">
                <label for="location">Location</label>
                <input type="text" id="location" name="location" required class="form-control">
            </div>
            <div class="form-group">
                <label for="pricePerHour">Price per Hour ($)</label>
                <input type="number" step="0.01" id="pricePerHour" name="pricePerHour" required class="form-control">
            </div>
            <div class="form-group">
                <label for="isAvailable" class="checkbox-group">
                    <input type="checkbox" id="isAvailable" name="isAvailable" value="true" class="checkbox">
                    <span class="checkbox-label">Available</span>
                </label>
            </div>
            <div id="electricFields" class="form-group hidden">
                <label for="batteryRange">Battery Range (km)</label>
                <input type="number" id="batteryRange" name="batteryRange" required class="form-control">
            </div>
            <div id="regularFields" class="form-group hidden">
                <label for="gearType">Gear Type</label>
                <input type="text" id="gearType" name="gearType" required class="form-control">
            </div>
            <div class="form-group">
                <div class="button-group">
                    <button type="submit" class="btn btn-submit">Add Bike</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Bootstrap 5 JavaScript CDN -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>
    function toggleFields() {
        const bikeType = document.getElementById('bikeType').value;
        document.getElementById('electricFields').classList.toggle('hidden', bikeType !== 'Electric');
        document.getElementById('regularFields').classList.toggle('hidden', bikeType !== 'Regular');
        document.getElementById('batteryRange').required = bikeType === 'Electric';
        document.getElementById('gearType').required = bikeType === 'Regular';
    }
    toggleFields();
</script>
</body>
</html>
