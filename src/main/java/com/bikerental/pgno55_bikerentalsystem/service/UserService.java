package com.bikerental.pgno55_bikerentalsystem.service;

import com.bikerental.pgno55_bikerentalsystem.dao.UserDAO;
import com.bikerental.pgno55_bikerentalsystem.model.User;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(ServletContext context) {
        this.userDAO = new UserDAO(context);
    }

    public void register(User user) throws Exception {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new Exception("Invalid user data");
        }
        if (userDAO.findByUsername(user.getUsername()) != null) {
            throw new Exception("Username already exists");
        }
        userDAO.saveUser(user);
    }

    public void updateUser(User user) throws IOException {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IOException("Invalid user data");
        }
        userDAO.updateUser(user);
    }

    public void deleteUser(String username) throws IOException {
        if (username == null || username.trim().isEmpty()) {
            throw new IOException("Invalid username");
        }
        userDAO.deleteUser(username);
    }

    public User authenticate(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        User user = userDAO.findByUsername(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}