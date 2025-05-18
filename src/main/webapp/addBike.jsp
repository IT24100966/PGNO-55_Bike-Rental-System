<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>BikeRental - Add Bike</title>
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <style>
    :root {
      --primary: #4f46e5;
      --primary-dark: #4338ca;
      --secondary: #06b6d4;
      --accent: #f97316;
      --background: #f8fafc;
      --card-bg: #ffffff;
      --text: #1e293b;
      --text-light: #64748b;
    }

    body {
      font-family: 'Inter', sans-serif;
      background-color: var(--background);
      color: var(--text);
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    .main-content {
      flex: 1;
    }

    .bikerental-header {
      background: rgba(255, 255, 255, 0.8);
      backdrop-filter: blur(10px);
      border-bottom: 1px solid rgba(0, 0, 0, 0.05);
      position: sticky;
      top: 0;
      z-index: 10;
    }

    .bikerental-logo {
      font-weight: 800;
      background: linear-gradient(90deg, var(--primary), var(--secondary));
      -webkit-background-clip: text;
      background-clip: text;
      color: transparent;
      letter-spacing: -1px;
    }

    .bikerental-card {
      background-color: var(--card-bg);
      border-radius: 16px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
      transition: all 0.3s ease;
    }

    .bikerental-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 35px rgba(0, 0, 0, 0.12);
    }

    .form-group {
      margin-bottom: 2rem;
      position: relative;
    }

    .form-label {
      position: absolute;
      top: 0.75rem;
      left: 1rem;
      font-weight: 500;
      color: var(--text-light);
      transition: all 0.3s ease;
      pointer-events: none;
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }

    .form-input {
      width: 100%;
      padding: 1rem 1rem 0.5rem;
      border: none;
      border-bottom: 2px solid #e2e8f0;
      background-color: transparent;
      transition: all 0.3s ease;
      font-size: 1rem;
      color: var(--text);
    }

    .form-input:focus {
      outline: none;
      border-bottom: 2px solid transparent;
      border-image: linear-gradient(135deg, var(--primary), var(--secondary)) 1;
    }

    .form-input:focus + .form-label,
    .form-input:not(:placeholder-shown) + .form-label {
      top: -0.75rem;
      left: 0.5rem;
      font-size: 0.75rem;
      color: var(--primary);
      font-weight: 600;
    }

    .form-label .fas {
      font-size: 0.875rem;
    }

    .bikerental-button {
      background: linear-gradient(135deg, var(--primary), var(--secondary));
      color: white;
      border: none;
      border-radius: 10px;
      padding: 0.75rem 1.5rem;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
    }

    .bikerental-button:hover {
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba(79, 70, 229, 0.4);
    }

    .nav-link {
      color: var(--text);
      font-weight: 600;
      padding: 0.5rem 1rem;
      border-radius: 8px;
      transition: all 0.2s ease;
    }

    .nav-link:hover, .nav-link.active {
      background-color: rgba(79, 70, 229, 0.1);
      color: var(--primary);
    }

    .nav-link .icon {
      margin-right: 0.5rem;
    }

    .bikerental-footer {
      background-color: #1e293b;
      color: #e2e8f0;
    }

    .bg-pattern {
      background-image: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%234f46e5' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
    }
  </style>
</head>
<body class="bg-pattern">
<!-- Header -->
<header class="bikerental-header py-4 px-6 shadow-sm">
  <div class="container mx-auto flex justify-between items-center">
    <div class="flex items-center space-x-2">
      <i class="fas fa-bicycle text-2xl text-indigo-600"></i>
      <h1 class="bikerental-logo text-2xl">BikeRental</h1>
    </div>
    <nav class="flex items-center space-x-2">
      <a href="addBike.jsp" class="nav-link active">
        <i class="fas fa-plus-circle icon"></i>Add Bike
      </a>
      <a href="viewBikes" class="nav-link">
        <i class="fas fa-list icon"></i>View Bikes
      </a>
      <a href="admin/viewUsers" class="nav-link">
        <i class="fas fa-users icon"></i>Manage Users
      </a>
      <a href="admin/viewActivityLog" class="nav-link">
        <i class="fas fa-clipboard-list icon"></i>Activity Log
      </a>
    </nav>
  </div>
</header>

<!-- Main Content -->
<div class="main-content container mx-auto py-12 px-4">
  <div class="max-w-lg mx-auto">
    <div class="bikerental-card p-8">
      <div class="text-center mb-8">
        <h2 class="text-2xl font-bold text-gray-800">Add a New Bike</h2>
        <p class="text-gray-500 mt-2">Enter the details of the bike you want to add to the fleet</p>
      </div>

      <% if (request.getAttribute("error") != null) { %>
      <div class="bg-red-50 border-l-4 border-red-500 p-4 mb-6 rounded">
        <div class="flex items-center">
          <i class="fas fa-exclamation-circle text-red-500 mr-3"></i>
          <p class="text-red-700"><%= request.getAttribute("error") %></p>
        </div>
      </div>
      <% } %>

      <form action="addBike" method="post">
        <div class="form-group">
          <input type="text" id="id" name="id" class="form-input" required placeholder=" " />
          <label class="form-label" for="id">
            <i class="fas fa-fingerprint text-indigo-500"></i>Bike ID
          </label>
        </div>

        <div class="form-group">
          <input type="text" id="model" name="model" class="form-input" required placeholder=" " />
          <label class="form-label" for="model">
            <i class="fas fa-tag text-indigo-500"></i>Model
          </label>
        </div>

        <div class="form-group">
          <input type="text" id="location" name="location" class="form-input" required placeholder=" " />
          <label class="form-label" for="location">
            <i class="fas fa-map-marker-alt text-indigo-500"></i>Location
          </label>
        </div>

        <div class="form-group">
          <select id="status" name="status" class="form-input" required>
            <option value="" disabled selected hidden></option>
            <option value="Available">Available</option>
            <option value="Rented">Rented</option>
            <option value="Maintenance">Maintenance</option>
          </select>
          <label class="form-label" for="status">
            <i class="fas fa-info-circle text-indigo-500"></i>Status
          </label>
        </div>

        <div class="form-group">
          <input type="number" id="price" step="0.01" name="price" class="form-input" required placeholder=" " />
          <label class="form-label" for="price">
            <i class="fas fa-dollar-sign text-indigo-500"></i>Price (per hour)
          </label>
        </div>

        <div class="mt-8">
          <button type="submit" class="bikerental-button w-full flex justify-center items-center">
            <i class="fas fa-bicycle mr-2"></i>
            Add Bike
          </button>
        </div>
      </form>

      <div class="mt-6 text-center">
        <a href="viewBikes" class="text-indigo-600 hover:text-indigo-800 font-medium flex items-center justify-center">
          <i class="fas fa-arrow-left mr-2"></i>
          View All Bikes
        </a>
      </div>
    </div>
  </div>
</div>

<!-- Footer -->
<footer class="bikerental-footer py-8 mt-12">
  <div class="container mx-auto px-4">
    <div class="flex flex-col md:flex-row justify-between items-center">
      <div class="mb-4 md:mb-0">
        <div class="flex items-center justify-center md:justify-start">
          <i class="fas fa-bicycle text-xl text-indigo-400 mr-2"></i>
          <span class="font-bold text-xl">BikeRental</span>
        </div>
        <p class="text-gray-400 mt-2 text-center md:text-left">Making bike rental simple and accessible.</p>
      </div>

      <div class="flex space-x-4">
        <a href="#" class="text-gray-400 hover:text-white transition">
          <i class="fab fa-facebook text-xl"></i>
        </a>
        <a href="#" class="text-gray-400 hover:text-white transition">
          <i class="fab fa-twitter text-xl"></i>
        </a>
        <a href="#" class="text-gray-400 hover:text-white transition">
          <i class="fab fa-instagram text-xl"></i>
        </a>
      </div>
    </div>

    <div class="border-t border-gray-700 mt-8 pt-8 text-center">
      <p>© 2025 BikeRental. All rights reserved.</p>
    </div>
  </div>
</footer>
</body>
</html>