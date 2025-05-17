package com.bikerental.pgno55_bikerentalsystem.controller;

import com.bikerental.pgno55_bikerentalsystem.model.AdminUser;
import com.bikerental.pgno55_bikerentalsystem.model.RegularUser;
import com.bikerental.pgno55_bikerentalsystem.model.User;
import com.bikerental.pgno55_bikerentalsystem.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user-management")
public class UserManagementServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            userService = new UserService(getServletContext());
            if (userService == null) {
                throw new ServletException("Failed to initialize UserService");
            }
        } catch (Exception e) {
            throw new ServletException("Initialization error: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "profile";

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        Boolean isAdmin = (session != null && user instanceof AdminUser);

        if (user == null && !action.equals("logout")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        switch (action) {
            case "profile":
                request.setAttribute("user", user);
                request.setAttribute("isAdmin", isAdmin);
                request.getRequestDispatcher("/user-management/profile.jsp").forward(request, response);
                break;
            case "list":
                if (isAdmin) {
                    try {
                        List<User> users = userService.getAllUsers();
                        Map<String, Boolean> userTypeMap = new HashMap<>();
                        for (User u : users) {
                            userTypeMap.put(u.getUsername(), u instanceof AdminUser);
                        }
                        request.setAttribute("users", users);
                        request.setAttribute("userTypeMap", userTypeMap);
                        request.getRequestDispatcher("/user-management/list.jsp").forward(request, response);
                    } catch (Exception e) {
                        request.setAttribute("error", "Failed to load user list: " + e.getMessage());
                        request.getRequestDispatcher("/user-management/list.jsp").forward(request, response);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
                break;
            case "create":
                if (isAdmin) {
                    request.getRequestDispatcher("/user-management/create-user.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
                break;
            case "logout":
                if (session != null) session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        Boolean isAdmin = (session != null && user instanceof AdminUser);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        switch (action) {
            case "update":
                String newEmail = request.getParameter("email");
                String newPassword = request.getParameter("password");
                String newDetail = request.getParameter("detail");

                if (newEmail == null || newPassword == null || newEmail.trim().isEmpty() || newPassword.trim().isEmpty()) {
                    request.setAttribute("error", "Email and password are required");
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/user-management/profile.jsp").forward(request, response);
                    return;
                }

                User updatedUser = user instanceof AdminUser ? new AdminUser() : new RegularUser();
                updatedUser.setUsername(user.getUsername());
                updatedUser.setEmail(newEmail);
                updatedUser.setPassword(newPassword);
                if (user instanceof AdminUser) {
                    ((AdminUser) updatedUser).setAdminRole(newDetail != null && !newDetail.trim().isEmpty() ? newDetail : ((AdminUser) user).getAdminRole());
                } else {
                    ((RegularUser) updatedUser).setDrivingLicenseNumber(newDetail != null && !newDetail.trim().isEmpty() ? newDetail : ((RegularUser) user).getDrivingLicenseNumber());
                }

                try {
                    userService.updateUser(updatedUser);
                    session.setAttribute("user", updatedUser);
                    request.setAttribute("message", "Profile updated successfully");
                    request.setAttribute("user", updatedUser);
                    request.setAttribute("isAdmin", isAdmin);
                    request.getRequestDispatcher("/user-management/profile.jsp").forward(request, response);
                } catch (IOException e) {
                    request.setAttribute("error", "Failed to update profile: " + e.getMessage());
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/user-management/profile.jsp").forward(request, response);
                }
                break;
            case "delete":
                try {
                    userService.deleteUser(user.getUsername());
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/login");
                } catch (IOException e) {
                    request.setAttribute("error", "Failed to delete account: " + e.getMessage());
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/user-management/profile.jsp").forward(request, response);
                }
                break;
            case "create":
                if (isAdmin) {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String email = request.getParameter("email");
                    String userType = request.getParameter("userType");
                    String detail = request.getParameter("detail");

                    // Enhanced validation
                    if (username == null || password == null || email == null || userType == null ||
                            username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()) {
                        request.setAttribute("error", "Username, password, and email are required");
                        request.getRequestDispatcher("/user-management/create-user.jsp").forward(request, response);
                        return;
                    }

                    // Validate email format
                    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        request.setAttribute("error", "Invalid email format");
                        request.getRequestDispatcher("/user-management/create-user.jsp").forward(request, response);
                        return;
                    }

                    // Validate password strength (e.g., minimum 6 characters)
                    if (password.length() < 6) {
                        request.setAttribute("error", "Password must be at least 6 characters long");
                        request.getRequestDispatcher("/user-management/create-user.jsp").forward(request, response);
                        return;
                    }

                    User newUser = userType.equals("ADMIN") ? new AdminUser() : new RegularUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);
                    if (newUser instanceof AdminUser) {
                        ((AdminUser) newUser).setAdminRole(detail != null && !detail.trim().isEmpty() ? detail : "staff");
                    } else {
                        ((RegularUser) newUser).setDrivingLicenseNumber(detail != null && !detail.trim().isEmpty() ? detail : "N/A");
                    }

                    try {
                        userService.register(newUser);
                        request.setAttribute("message", "User '" + username + "' created successfully");
                        response.sendRedirect(request.getContextPath() + "/user-management?action=list");
                    } catch (Exception e) {
                        request.setAttribute("error", "Failed to create user: " + e.getMessage());
                        request.getRequestDispatcher("/user-management/create-user.jsp").forward(request, response);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
                break;
            case "deleteUser":
                if (isAdmin) {
                    String username = request.getParameter("username");
                    if (username == null || username.trim().isEmpty()) {
                        request.setAttribute("error", "Username is required");
                        request.setAttribute("users", userService.getAllUsers());
                        request.getRequestDispatcher("/user-management/list.jsp").forward(request, response);
                        return;
                    }
                    if (username.equals(user.getUsername())) {
                        request.setAttribute("error", "Cannot delete your own account from this page");
                        request.setAttribute("users", userService.getAllUsers());
                        request.getRequestDispatcher("/user-management/list.jsp").forward(request, response);
                        return;
                    }
                    try {
                        userService.deleteUser(username);
                        request.setAttribute("message", "User deleted successfully");
                        request.setAttribute("users", userService.getAllUsers());
                        Map<String, Boolean> userTypeMap = new HashMap<>();
                        for (User u : userService.getAllUsers()) {
                            userTypeMap.put(u.getUsername(), u instanceof AdminUser);
                        }
                        request.setAttribute("userTypeMap", userTypeMap);
                        request.getRequestDispatcher("/user-management/list.jsp").forward(request, response);
                    } catch (IOException e) {
                        request.setAttribute("error", "Failed to delete user: " + e.getMessage());
                        request.setAttribute("users", userService.getAllUsers());
                        Map<String, Boolean> userTypeMap = new HashMap<>();
                        for (User u : userService.getAllUsers()) {
                            userTypeMap.put(u.getUsername(), u instanceof AdminUser);
                        }
                        request.setAttribute("userTypeMap", userTypeMap);
                        request.getRequestDispatcher("/user-management/list.jsp").forward(request, response);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}