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

@WebServlet("/admin/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = new UserDAO(getServletContext());
        User user = userDAO.getUserById(id);
        if (user != null) {
            userDAO.deleteUser(id);

            // Log the action
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            ActivityLogDAO logDAO = new ActivityLogDAO(getServletContext());
            ActivityLog log = new ActivityLog();
            log.setAction("User Deleted");
            log.setDetails("User " + user.getUsername() + " deleted");
            log.setPerformedBy(currentUser != null ? currentUser.getUsername() : "unknown");
            logDAO.addLog(log);

            response.sendRedirect("viewUsers");
        } else {
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("/admin/viewUsers").forward(request, response);
        }
    }
}