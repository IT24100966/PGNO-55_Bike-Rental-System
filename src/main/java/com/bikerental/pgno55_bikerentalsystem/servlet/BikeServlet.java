package com.bikerental.pgno55_bikerentalsystem.servlet;



import com.bikerental.pgno55_bikerentalsystem.dao.BikeDAO;
import com.bikerental.pgno55_bikerentalsystem.model.Bike;
import com.bikerental.pgno55_bikerentalsystem.model.ElectricBike;
import com.bikerental.pgno55_bikerentalsystem.model.RegularBike;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bikes")
public class BikeServlet extends HttpServlet {
    private BikeDAO bikeDAO;

    @Override
    public void init() {
        bikeDAO = new BikeDAO(getServletContext());
        System.out.println("BikeServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println("Handling GET request with action: " + action);
        if ("view".equals(action)) {
            List<Bike> bikes = bikeDAO.sortBikesByAvailability();
            System.out.println("Fetched " + bikes.size() + " bikes for view");
            req.setAttribute("bikes", bikes);
            req.getRequestDispatcher("/viewBikes.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            String bikeId = req.getParameter("bikeId");
            List<Bike> bikes = bikeDAO.getAllBikes();
            Bike bike = bikes.stream()
                    .filter(b -> b.getBikeId().equals(bikeId))
                    .findFirst()
                    .orElse(null);
            req.setAttribute("bike", bike);
            req.getRequestDispatcher("/viewBikes.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/addBike.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println("Handling POST request with action: " + action);
        if ("add".equals(action)) {
            String bikeId = req.getParameter("bikeId");
            String bikeType = req.getParameter("bikeType");
            String location = req.getParameter("location");
            String pricePerHourStr = req.getParameter("pricePerHour");
            boolean isAvailable = "true".equals(req.getParameter("isAvailable"));

            System.out.println("Adding bike: ID=" + bikeId + ", Type=" + bikeType + ", Location=" + location);

            if (bikeId == null || bikeId.trim().isEmpty() || location == null || location.trim().isEmpty() ||
                    pricePerHourStr == null || pricePerHourStr.trim().isEmpty() || bikeType == null) {
                System.out.println("Validation failed: Missing required fields");
                req.setAttribute("error", "All fields are required.");
                req.getRequestDispatcher("/addBike.jsp").forward(req, resp);
                return;
            }

            double pricePerHour;
            try {
                pricePerHour = Double.parseDouble(pricePerHourStr);
            } catch (NumberFormatException e) {
                System.out.println("Validation failed: Invalid price format: " + pricePerHourStr);
                req.setAttribute("error", "Invalid price format.");
                req.getRequestDispatcher("/addBike.jsp").forward(req, resp);
                return;
            }

            Bike bike;
            if ("Electric".equals(bikeType)) {
                String batteryRangeStr = req.getParameter("batteryRange");
                try {
                    int batteryRange = Integer.parseInt(batteryRangeStr);
                    if (batteryRange <= 0) {
                        throw new NumberFormatException("Battery range must be positive.");
                    }
                    bike = new ElectricBike(bikeId, location, pricePerHour, isAvailable, batteryRange);
                } catch (NumberFormatException e) {
                    System.out.println("Validation failed: Invalid battery range: " + batteryRangeStr);
                    req.setAttribute("error", "Invalid battery range: " + e.getMessage());
                    req.getRequestDispatcher("/addBike.jsp").forward(req, resp);
                    return;
                }
            } else {
                String gearType = req.getParameter("gearType");
                if (gearType == null || gearType.trim().isEmpty()) {
                    System.out.println("Validation failed: Gear type required");
                    req.setAttribute("error", "Gear type is required for Regular bikes.");
                    req.getRequestDispatcher("/addBike.jsp").forward(req, resp);
                    return;
                }
                bike = new RegularBike(bikeId, location, pricePerHour, isAvailable, gearType);
            }

            try {
                bikeDAO.addBike(bike);

                System.out.println("Bike added successfully: " + bikeId);
                List<Bike> bikes = bikeDAO.sortBikesByAvailability();
                System.out.println("Fetched " + bikes.size() + " bikes after adding");
                req.setAttribute("message", "Bike added successfully!");
                req.setAttribute("bikes", bikes);
                req.getRequestDispatcher("/viewBikes.jsp").forward(req, resp);
            } catch (IOException e) {
                System.err.println("Failed to add bike: " + e.getMessage());
                req.setAttribute("error", "Failed to add bike: " + e.getMessage());
                req.getRequestDispatcher("/addBike.jsp").forward(req, resp);
            }
        } else if ("update".equals(action)) {
            String bikeId = req.getParameter("bikeId");
            String bikeType = req.getParameter("bikeType");
            String location = req.getParameter("location");
            String pricePerHourStr = req.getParameter("pricePerHour");
            boolean isAvailable = "true".equals(req.getParameter("isAvailable"));

            double pricePerHour = Double.parseDouble(pricePerHourStr);

            Bike bike;
            if ("Electric".equals(bikeType)) {
                int batteryRange = Integer.parseInt(req.getParameter("batteryRange"));
                bike = new ElectricBike(bikeId, location, pricePerHour, isAvailable, batteryRange);
            } else {
                String gearType = req.getParameter("gearType");
                bike = new RegularBike(bikeId, location, pricePerHour, isAvailable, gearType);
            }
            bikeDAO.updateBike(bike);
            List<Bike> bikes = bikeDAO.sortBikesByAvailability();
            req.setAttribute("message", "Bike updated successfully!");
            req.setAttribute("bikes", bikes);
            resp.sendRedirect(req.getContextPath() + "/bikes?action=view");
        } else if ("delete".equals(action)) {
            String bikeId = req.getParameter("bikeId");
            bikeDAO.deleteBike(bikeId);
            List<Bike> bikes = bikeDAO.sortBikesByAvailability();
            req.setAttribute("message", "Bike deleted successfully!");
            req.setAttribute("bikes", bikes);
            resp.sendRedirect(req.getContextPath() + "/bikes?action=view");
        }
    }
}