<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bikerental.model.User" %>
<html>
<head>
    <title>BikeRental - View Users</title>
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

        .bikerental-button {
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            color: white;
            border: none;
            border-radius: 10px;
            padding: 0.5rem 1rem;
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

        .table-header {
            background: linear-gradient(135deg, var(--primary), var(--secondary));
            color: white;
        }
    </style>
</head>
<body class="bg-pattern">
<div class="container mx-auto px-4 py-8">
    <div class="bikerental-card p-8">
        <div class="flex items-center justify-between mb-6">
            <div class="flex items-center space-x-2">
                <i class="fas fa-users text-3xl text-indigo-600"></i>
                <h1 class="text-3xl font-bold bg-gradient-to-r from-indigo-600 to-cyan-500 bg-clip-text text-transparent">View Users</h1>
            </div>
            <a href="<%= request.getContextPath() %>/logout" class="bikerental-button flex items-center">
                <i class="fas fa-sign-out-alt mr-2"></i> Logout
            </a>
        </div>

        <% if (request.getAttribute("error") != null) { %>
        <div class="bg-red-50 border-l-4 border-red-500 p-4 mb-6 rounded">
            <div class="flex items-center">
                <i class="fas fa-exclamation-circle text-red-500 mr-3"></i>
                <p class="text-red-700"><%= request.getAttribute("error") %></p>
            </div>
        </div>
        <% } %>

        <div class="overflow-x-auto">
            <table class="min-w-full border-collapse">
                <thead>
                <tr class="table-header">
                    <th class="py-3 px-6 text-left">ID</th>
                    <th class="py-3 px-6 text-left">Username</th>
                    <th class="py-3 px-6 text-left">Email</th>
                    <th class="py-3 px-6 text-left">Role</th>
                    <th class="py-3 px-6 text-left">Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<User> users = (List<User>) request.getAttribute("users");
                    if (users != null && !users.isEmpty()) {
                        for (User user : users) {
                %>
                <tr class="border-b border-gray-200 hover:bg-gray-50">
                    <td class="py-4 px-6"><%= user.getId() %></td>
                    <td class="py-4 px-6"><%= user.getUsername() %></td>
                    <td class="py-4 px-6"><%= user.getEmail() %></td>
                    <td class="py-4 px-6"><%= user.getRole() %></td>
                    <td class="py-4 px-6 flex space-x-2">
                        <a href="<%= request.getContextPath() %>/admin/editUser?id=<%= user.getId() %>"
                           class="text-indigo-600 hover:text-indigo-800">
                            <i class="fas fa-edit"></i> Edit
                        </a>
                        <a href="<%= request.getContextPath() %>/admin/deleteUser?id=<%= user.getId() %>"
                           class="text-red-600 hover:text-red-800"
                           onclick="return confirm('Are you sure you want to delete this user?')">
                            <i class="fas fa-trash-alt"></i> Delete
                        </a>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5" class="py-4 px-6 text-center text-gray-500">No users found.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>

        <div class="mt-6">
            <a href="<%= request.getContextPath() %>/admin/addUser" class="bikerental-button flex items-center">
                <i class="fas fa-plus mr-2"></i> Add New User
            </a>
        </div>
    </div>
</div>
</body>
</html>