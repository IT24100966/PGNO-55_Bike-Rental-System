package com.bikerental.pgno55_bikerentalsystem.servlet;

import com.bikerental.dao.ActivityLogDAO;
import com.bikerental.model.ActivityLog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/viewActivityLog")
public class ViewActivityLogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActivityLogDAO logDAO = new ActivityLogDAO(getServletContext());
        List<ActivityLog> logs = logDAO.getAllLogs();
        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/admin/viewActivityLog.jsp").forward(request, response);
    }
}