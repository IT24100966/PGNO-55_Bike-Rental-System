package com.bikerental.pgno55_bikerentalsystem.dao;
import com.bikerental.model.User;

import jakarta.servlet.ServletContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private final String filePath;
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    public UserDAO(ServletContext context) {
        this.filePath = context.getRealPath("/WEB-INF/data/users.txt");
        LOGGER.info("UserDAO initialized with file path: " + this.filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                LOGGER.info("Created new users.txt file at: " + filePath);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("1,admin,admin@bikerental.com,admin123,admin");
                    writer.newLine();
                    LOGGER.info("Initialized users.txt with default admin user");
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to create users.txt: " + e.getMessage(), e);
            }
        }
    }

    public void addUser(User user) throws IOException {
        List<User> users = getAllUsers();
        user.setId(users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1);
        users.add(user);
        saveUsers(users);
    }

    public User getUserById(int id) throws IOException {
        return getAllUsers().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public User getUserByUsername(String username) throws IOException {
        LOGGER.info("Looking for user with username: " + username);
        User user = getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (user == null) {
            LOGGER.warning("User not found for username: " + username);
        } else {
            LOGGER.info("Found user: " + user.getUsername() + ", Role: " + user.getRole());
        }
        return user;
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(filePath);
        LOGGER.info("Attempting to read users from file: " + filePath);
        if (!file.exists()) {
            LOGGER.warning("users.txt file does not exist at path: " + filePath);
            return users;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    LOGGER.warning("Skipping empty line at line number: " + lineNumber);
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length != 5) {
                    LOGGER.severe("Invalid user data at line " + lineNumber + ": " + line);
                    continue;
                }
                try {
                    users.add(new User(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[4].trim()
                    ));
                } catch (NumberFormatException e) {
                    LOGGER.severe("Error parsing user ID at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading users.txt: " + e.getMessage(), e);
            throw e;
        }
        LOGGER.info("Successfully loaded " + users.size() + " users");
        return users;
    }

    public void updateUser(User user) throws IOException {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
        saveUsers(users);
    }

    public void deleteUser(int id) throws IOException {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId() == id);
        saveUsers(users);
    }

    private void saveUsers(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                writer.write(String.format("%d,%s,%s,%s,%s",
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole()));
                writer.newLine();
            }
            LOGGER.info("Successfully saved " + users.size() + " users to " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving users.txt: " + e.getMessage(), e);
            throw e;
        }
    }
}