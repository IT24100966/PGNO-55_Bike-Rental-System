package com.bikerental.pgno55_bikerentalsystem.servlet;

import com.bikerental.dao.UserDAO;
import com.bikerental.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LOGGER.info("Login attempt for username: " + username);

        try {
            UserDAO userDAO = new UserDAO(getServletContext());
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                LOGGER.info("Login successful for user: " + username + ", Role: " + user.getRole());
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                if ("admin".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin/viewUsers");
                } else {
                    response.sendRedirect(request.getContextPath() + "/viewBikes");
                }
            } else {
                LOGGER.warning("Login failed for username: " + username + " - Invalid credentials");
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during login for username: " + username, e);
            request.setAttribute("error", "An error occurred while logging in: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}