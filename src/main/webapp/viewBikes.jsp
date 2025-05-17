
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>View Bikes</title>
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/bikes">Add New Bike</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/bikes?action=view">View Bikes</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container mt-5 pt-4">
    <h2>Bike Inventory</h2>
    <c:if test="${not empty message}">
        <div class="alert alert-success" role="alert">
                ${message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error" role="alert">
                ${error}
        </div>
    </c:if>
    <%
        java.util.List<com.bikerental.pgno55_bikerentalsystem.model.Bike> bikes = (java.util.List<com.bikerental.pgno55_bikerentalsystem.model.Bike>) request.getAttribute("bikes");
        if (bikes != null) {
            System.out.println("Rendering " + bikes.size() + " bikes in viewBikes.jsp");
            for (com.bikerental.pgno55_bikerentalsystem.model.Bike bike : bikes) {
                System.out.println("Rendering bike: " + bike.getBikeId());
            }
        } else {
            System.out.println("No bikes attribute found in request");
        }
    %>
    <div class="table-container">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Bike ID</th>
                <th>Type</th>
                <th>Location</th>
                <th>Price/Hour</th>
                <th>Available</th>
                <th>Details</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="bike" items="${bikes}">
                <tr>
                    <td>${bike.bikeId}</td>
                    <td>${bike.bikeType}</td>
                    <td>${bike.location}</td>
                    <td>$${bike.pricePerHour}</td>
                    <td>${bike.available ? 'Yes' : 'No'}</td>
                    <td>
                        <c:if test="${bike.bikeType == 'Electric'}">
                            Battery Range: ${bike.batteryRange} km
                        </c:if>
                        <c:if test="${bike.bikeType == 'Regular'}">
                            Gear Type: ${bike.gearType}
                        </c:if>
                    </td>
                    <td class="d-flex gap-2">
                        <button onclick="openEditModal('${bike.bikeId}', '${bike.bikeType}', '${bike.location}', '${bike.pricePerHour}', '${bike.available}', '${bike.bikeType == 'Electric' ? bike.batteryRange : bike.gearType}')"
                                class="btn btn-link">Edit</button>
                        <form action="${pageContext.request.contextPath}/bikes" method="post" class="m-0">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="bikeId" value="${bike.bikeId}">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty bikes}">
                <tr><td colspan="7" class="text-center">No bikes available.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<!-- Edit Modal -->
<div id="editModal" class="modal">
    <div class="modal-content">
        <h3>Edit Bike</h3>
        <form action="${pageContext.request.contextPath}/bikes" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" id="editBikeId" name="bikeId">
            <div class="form-group">
                <label for="editBikeType">Bike Type</label>
                <select id="editBikeType" name="bikeType" onchange="toggleEditFields()" class="form-control">
                    <option value="Regular">Regular</option>
                    <option value="Electric">Electric</option>
                </select>
            </div>
            <div class="form-group">
                <label for="editLocation">Location</label>
                <input type="text" id="editLocation" name="location" required class="form-control">
            </div>
            <div class="form-group">
                <label for="editPricePerHour">Price per Hour ($)</label>
                <input type="number" step="0.01" id="editPricePerHour" name="pricePerHour" required class="form-control">
            </div>
            <div class="form-group">
                <label for="editIsAvailable" class="checkbox-group">
                    <input type="checkbox" id="editIsAvailable" name="isAvailable" value="true" class="checkbox">
                    <span class="checkbox-label">Available</span>
                </label>
            </div>
            <div id="editElectricFields" class="form-group hidden">
                <label for="editBatteryRange">Battery Range (km)</label>
                <input type="number" id="editBatteryRange" name="batteryRange" class="form-control">
            </div>
            <div id="editRegularFields" class="form-group hidden">
                <label for="editGearType">Gear Type</label>
                <input type="text" id="editGearType" name="gearType" class="form-control">
            </div>
            <div class="d-flex justify-content-end gap-2">
                <button type="button" onclick="closeEditModal()" class="btn btn-secondary">Cancel</button>
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
    </div>
</div>

<!-- Bootstrap 5 JavaScript CDN -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script>
    function openEditModal(bikeId, bikeType, location, pricePerHour, isAvailable, specificDetail) {
        document.getElementById('editBikeId').value = bikeId;
        document.getElementById('editBikeType').value = bikeType;
        document.getElementById('editLocation').value = location;
        document.getElementById('editPricePerHour').value = pricePerHour;
        document.getElementById('editIsAvailable').checked = isAvailable === 'true';
        if (bikeType === 'Electric') {
            document.getElementById('editBatteryRange').value = specificDetail;
        } else {
            document.getElementById('editGearType').value = specificDetail;
        }
        toggleEditFields();
        document.getElementById('editModal').classList.add('show');
    }

    function closeEditModal() {
        document.getElementById('editModal').classList.remove('show');
    }

    function toggleEditFields() {
        const bikeType = document.getElementById('editBikeType').value;
        document.getElementById('editElectricFields').classList.toggle('hidden', bikeType !== 'Electric');
        document.getElementById('editRegularFields').classList.toggle('hidden', bikeType !== 'Regular');
    }
</script>
</body>
</html>
