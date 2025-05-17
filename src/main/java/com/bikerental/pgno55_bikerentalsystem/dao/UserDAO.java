package com.bikerental.pgno55_bikerentalsystem.dao;

import com.bikerental.pgno55_bikerentalsystem.model.*;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.util.*;

public class UserDAO {
    private final String FILE_PATH;

    public UserDAO(ServletContext context) {
        this.FILE_PATH = context.getRealPath("/WEB-INF/users.txt");
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists() || file.length() == 0) {
                file.getParentFile().mkdirs();
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write("ADMIN,admin,admin123,admin@bikerental.com,superadmin\n");
                    bw.write("REGULAR,user,user123,user@bikerental.com,DL12345\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String userType = parts[0];
                    String username = parts[1];
                    String password = parts[2];
                    String email = parts[3];
                    String detail = parts[4];

                    if (userType.equals("ADMIN")) {
                        AdminUser user = new AdminUser();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAdminRole(detail);
                        users.add(user);
                    } else if (userType.equals("REGULAR")) {
                        RegularUser user = new RegularUser();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setDrivingLicenseNumber(detail);
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUser(User user) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String type = (user instanceof AdminUser) ? "ADMIN" : "REGULAR";
            String detail = (user instanceof AdminUser) ? ((AdminUser) user).getAdminRole() : ((RegularUser) user).getDrivingLicenseNumber();
            bw.write(String.format("%s,%s,%s,%s,%s\n", type, user.getUsername(), user.getPassword(), user.getEmail(), detail));
        }
    }

    public void updateUser(User updatedUser) throws IOException {
        List<User> users = getAllUsers();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (user.getUsername().equals(updatedUser.getUsername())) {
                    String type = (updatedUser instanceof AdminUser) ? "ADMIN" : "REGULAR";
                    String detail = (updatedUser instanceof AdminUser) ? ((AdminUser) updatedUser).getAdminRole() : ((RegularUser) updatedUser).getDrivingLicenseNumber();
                    bw.write(String.format("%s,%s,%s,%s,%s\n", type, updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getEmail(), detail));
                } else {
                    String type = (user instanceof AdminUser) ? "ADMIN" : "REGULAR";
                    String detail = (user instanceof AdminUser) ? ((AdminUser) user).getAdminRole() : ((RegularUser) user).getDrivingLicenseNumber();
                    bw.write(String.format("%s,%s,%s,%s,%s\n", type, user.getUsername(), user.getPassword(), user.getEmail(), detail));
                }
            }
        }
    }

    public void deleteUser(String username) throws IOException {
        List<User> users = getAllUsers();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (!user.getUsername().equals(username)) {
                    String type = (user instanceof AdminUser) ? "ADMIN" : "REGULAR";
                    String detail = (user instanceof AdminUser) ? ((AdminUser) user).getAdminRole() : ((RegularUser) user).getDrivingLicenseNumber();
                    bw.write(String.format("%s,%s,%s,%s,%s\n", type, user.getUsername(), user.getPassword(), user.getEmail(), detail));
                }
            }
        }
    }

    public User findByUsername(String username) {
        for (User user : getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}