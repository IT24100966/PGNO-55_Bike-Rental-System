<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>BikeRental - Login</title>
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
      justify-content: center;
      align-items: center;
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

    .bg-pattern {
      background-image: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%234f46e5' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
    }
  </style>
</head>
<body class="bg-pattern">
<div class="container mx-auto px-4">
  <div class="max-w-md mx-auto">
    <div class="bikerental-card p-8">
      <div class="text-center mb-8">
        <div class="flex items-center justify-center space-x-2 mb-4">
          <i class="fas fa-bicycle text-3xl text-indigo-600"></i>
          <h1 class="text-3xl font-bold bg-gradient-to-r from-indigo-600 to-cyan-500 bg-clip-text text-transparent">BikeRental</h1>
        </div>
        <h2 class="text-2xl font-bold text-gray-800">Login</h2>
        <p class="text-gray-500 mt-2">Sign in to manage your bike rental platform</p>
      </div>

      <% if (request.getAttribute("error") != null) { %>
      <div class="bg-red-50 border-l-4 border-red-500 p-4 mb-6 rounded">
        <div class="flex items-center">
          <i class="fas fa-exclamation-circle text-red-500 mr-3"></i>
          <p class="text-red-700"><%= request.getAttribute("error") %></p>
        </div>
      </div>
      <% } %>

      <form action="<%= request.getContextPath() %>/login" method="post">
        <div class="form-group">
          <input type="text" id="username" name="username" class="form-input" required placeholder=" " />
          <label class="form-label" for="username">
            <i class="fas fa-user text-indigo-500"></i>Username
          </label>
        </div>

        <div class="form-group">
          <input type="password" id="password" name="password" class="form-input" required placeholder=" " />
          <label class="form-label" for="password">
            <i class="fas fa-lock text-indigo-500"></i>Password
          </label>
        </div>

        <div class="mt-8">
          <button type="submit" class="bikerental-button w-full flex justify-center items-center">
            <i class="fas fa-sign-in-alt mr-2"></i>
            Sign In
          </button>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>