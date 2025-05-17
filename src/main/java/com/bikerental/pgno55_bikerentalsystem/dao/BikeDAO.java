package com.bikerental.pgno55_bikerentalsystem.dao;

import com.bikerental.pgno55_bikerentalsystem.model.Bike;
import com.bikerental.pgno55_bikerentalsystem.model.ElectricBike;
import com.bikerental.pgno55_bikerentalsystem.model.RegularBike;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BikeDAO {
    private final String filePath;
    private Queue<String> rentalRequests = new LinkedList<>();
    private final ServletContext servletContext;

    public BikeDAO(ServletContext servletContext) {
        this.servletContext = servletContext;
        // Hardcode the path to the project source directory
        this.filePath = "C:\\Users\\ACER\\IdeaProjects\\PGNO-55_Bike-Rental-System\\src\\main\\webapp\\WEB-INF\\";
        initializeFile();
    }

    private void initializeFile() {
        try {
            File file = new File(filePath);
            System.out.println("Initializing bikes.txt at: " + filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Ensure directory exists
                file.createNewFile();
                System.out.println("Created new bikes.txt");
            }
            if (!file.canWrite()) {
                System.err.println("Warning: bikes.txt is not writable at " + filePath + "!");
            }
            if (!file.canRead()) {
                System.err.println("Warning: bikes.txt is not readable at " + filePath + "!");
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize bikes.txt: " + e.getMessage());
            throw new RuntimeException("Failed to initialize bikes.txt", e);
        }
    }

    public void addBike(Bike bike) throws IOException {
        System.out.println("Adding bike to: " + filePath + ", Bike: " + bike.getBikeId());
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String line = String.format("%s,%s,%s,%.2f,%b",
                    bike.getBikeId(), bike.getBikeType(), bike.getLocation(),
                    bike.getPricePerHour(), bike.isAvailable());
            if (bike instanceof ElectricBike) {
                line += "," + ((ElectricBike) bike).getBatteryRange();
            } else if (bike instanceof RegularBike) {
                line += "," + ((RegularBike) bike).getGearType();
            }
            writer.write(line);
            writer.newLine();
            writer.flush();
            System.out.println("Successfully wrote bike: " + line);
            file.setLastModified(System.currentTimeMillis());
        } catch (IOException e) {
            System.err.println("Failed to add bike: " + e.getMessage());
            throw e;
        }
    }

    public List<Bike> getAllBikes() throws IOException {
        List<Bike> bikes = new ArrayList<>();
        System.out.println("Reading bikes from: " + filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("bikes.txt does not exist at: " + filePath);
            return bikes;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                System.out.println("Raw line " + lineNumber + ": [" + line + "]"); // Debug raw input
                if (line.trim().isEmpty()) {
                    System.out.println("Skipping empty line " + lineNumber);
                    continue;
                }
                String[] parts = line.split(",");
                System.out.println("Split parts for line " + lineNumber + ": " + Arrays.toString(parts));
                if (parts.length < 6) {
                    System.err.println("Invalid data at line " + lineNumber + ": " + line + " (less than 6 fields)");
                    continue;
                }
                try {
                    String bikeId = parts[0].trim();
                    String bikeType = parts[1].trim();
                    String location = parts[2].trim();
                    double pricePerHour = Double.parseDouble(parts[3].trim());
                    boolean isAvailable = Boolean.parseBoolean(parts[4].trim());

                    Bike bike;
                    if (bikeType.equals("Electric")) {
                        int batteryRange = Integer.parseInt(parts[5].trim());
                        bike = new ElectricBike(bikeId, location, pricePerHour, isAvailable, batteryRange);
                        System.out.println("Parsed Electric bike: " + bikeId + ", Range: " + batteryRange);
                    } else if (bikeType.equals("Regular")) {
                        String gearType = parts[5].trim();
                        bike = new RegularBike(bikeId, location, pricePerHour, isAvailable, gearType);
                        System.out.println("Parsed Regular bike: " + bikeId + ", Gear: " + gearType);
                    } else {
                        System.err.println("Unknown bike type at line " + lineNumber + ": " + bikeType);
                        continue;
                    }
                    bikes.add(bike);
                } catch (NumberFormatException e) {
                    System.err.println("Number format error at line " + lineNumber + ": " + line + " (" + e.getMessage() + ")");
                    continue;
                } catch (Exception e) {
                    System.err.println("Error parsing line " + lineNumber + ": " + line + " (" + e.getMessage() + ")");
                    continue;
                }
            }
            System.out.println("Total bikes parsed: " + bikes.size());
        } catch (IOException e) {
            System.err.println("Failed to read bikes.txt: " + e.getMessage());
            throw e;
        }
        return bikes;
    }

    public void updateBike(Bike updatedBike) throws IOException {
        List<Bike> bikes = getAllBikes();
        bikes = bikes.stream()
                .map(bike -> bike.getBikeId().equals(updatedBike.getBikeId()) ? updatedBike : bike)
                .collect(Collectors.toList());
        saveAllBikes(bikes);
    }

    public void deleteBike(String bikeId) throws IOException {
        List<Bike> bikes = getAllBikes();
        bikes = bikes.stream()
                .filter(bike -> !bike.getBikeId().equals(bikeId))
                .collect(Collectors.toList());
        saveAllBikes(bikes);
    }

    private void saveAllBikes(List<Bike> bikes) throws IOException {
        System.out.println("Saving all bikes to: " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Bike bike : bikes) {
                String line = String.format("%s,%s,%s,%.2f,%b",
                        bike.getBikeId(), bike.getBikeType(), bike.getLocation(),
                        bike.getPricePerHour(), bike.isAvailable());
                if (bike instanceof ElectricBike) {
                    line += "," + ((ElectricBike) bike).getBatteryRange();
                } else if (bike instanceof RegularBike) {
                    line += "," + ((RegularBike) bike).getGearType();
                }
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            System.out.println("Successfully saved " + bikes.size() + " bikes");
        } catch (IOException e) {
            System.err.println("Failed to save bikes: " + e.getMessage());
            throw e;
        }
    }

    public void addRentalRequest(String bikeId) {
        rentalRequests.offer(bikeId);
        System.out.println("Added rental request for bike: " + bikeId);
    }

    public String processRentalRequest() {
        String bikeId = rentalRequests.poll();
        System.out.println("Processed rental request for bike: " + bikeId);
        return bikeId;
    }

    public List<Bike> sortBikesByAvailability() throws IOException {
        List<Bike> bikes = getAllBikes();
        System.out.println("Sorting " + bikes.size() + " bikes by availability");
        quickSort(bikes, 0, bikes.size() - 1);
        return bikes;
    }

    private void quickSort(List<Bike> bikes, int low, int high) {
        if (low < high) {
            int pi = partition(bikes, low, high);
            quickSort(bikes, low, pi - 1);
            quickSort(bikes, pi + 1, high);
        }
    }

    private int partition(List<Bike> bikes, int low, int high) {
        boolean pivot = bikes.get(high).isAvailable();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (bikes.get(j).isAvailable() && !pivot || (!bikes.get(j).isAvailable() == !pivot)) {
                i++;
                Collections.swap(bikes, i, j);
            }
        }
        Collections.swap(bikes, i + 1, high);
        return i + 1;
    }
}
