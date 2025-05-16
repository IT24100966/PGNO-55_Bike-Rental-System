package com.bikerental.pgno55_bikerentalsystem.controller;

import com.bikerental.pgno55_bikerentalsystem.model.RegularUser;
import com.bikerental.pgno55_bikerentalsystem.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String drivingLicenseNumber = request.getParameter("drivingLicenseNumber");

        if (username == null || password == null || email == null || drivingLicenseNumber == null ||
                username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty() || drivingLicenseNumber.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        RegularUser user = new RegularUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setDrivingLicenseNumber(drivingLicenseNumber);

        try {
            userService.register(user);
            response.sendRedirect(request.getContextPath() + "/login?registered=true");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}