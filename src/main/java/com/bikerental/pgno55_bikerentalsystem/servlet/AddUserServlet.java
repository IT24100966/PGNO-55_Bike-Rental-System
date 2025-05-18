package com.bikerental.pgno55_bikerentalsystem.servlet;

import com.bikerental.dao.ActivityLogDAO;
import com.bikerental.dao.UserDAO;
import com.bikerental.model.ActivityLog;
import com.bikerental.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/addUser")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        try {
            UserDAO userDAO = new UserDAO(getServletContext());
            userDAO.addUser(user);

            // Log the action
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            ActivityLogDAO logDAO = new ActivityLogDAO(getServletContext());
            ActivityLog log = new ActivityLog();
            log.setAction("User Created");
            log.setDetails("User " + username + " created");
            log.setPerformedBy(currentUser != null ? currentUser.getUsername() : "unknown");
            logDAO.addLog(log);

            response.sendRedirect("viewUsers");
        } catch (IOException e) {
            request.setAttribute("error", "Failed to add user: " + e.getMessage());
            request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response);
        }
    }
}