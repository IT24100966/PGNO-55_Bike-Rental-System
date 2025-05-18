package com.bikerental.pgno55_bikerentalsystem.servlet;

import com.bikerental.dao.UserDAO;
import com.bikerental.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/viewUsers")
public class ViewUsersServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ViewUsersServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserDAO userDAO = new UserDAO(getServletContext());
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);
            LOGGER.info("Fetched " + users.size() + " users for display");
            request.getRequestDispatcher("/admin/viewUsers.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching users: " + e.getMessage(), e);
            request.setAttribute("error", "An error occurred while fetching users: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}