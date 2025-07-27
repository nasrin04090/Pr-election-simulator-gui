package com.election.simulator.auth;

import com.election.simulator.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthService {
    private List<User> users;
    private User currentUser;
    private FaceRecognitionService faceRecognitionService;

    public AuthService() {
        this.users = new ArrayList<>();
        this.faceRecognitionService = new FaceRecognitionService();
        // Add a default admin user for testing
        this.users.add(new User("admin", "adminpass", "Administrator", "00000000000", true));
    }

    public boolean registerUser(String username, String password, String fullName, String nationalId) {
        // Check if username or national ID already exists
        if (users.stream().anyMatch(u -> u.getUsername().equals(username) || u.getNationalId().equals(nationalId))) {
            System.out.println("Registration failed: Username or National ID already exists.");
            return false;
        }
        
        // Capture face for biometric registration
        System.out.println("\nFace capture required for registration...");
        if (!faceRecognitionService.captureAndStoreFace(nationalId)) {
            System.out.println("Registration failed: Face capture unsuccessful.");
            return false;
        }
        
        User newUser = new User(username, password, fullName, nationalId, false);
        users.add(newUser);
        System.out.println("User registered successfully: " + username);
        return true;
    }

    public User authenticateUser(String username, String password) {
        Optional<User> userOptional = users.stream()
                                          .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                                          .findFirst();
        return userOptional.orElse(null);
    }

    public User login(String username, String password) {
        Optional<User> userOptional = users.stream()
                                          .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                                          .findFirst();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Perform face verification for non-admin users
            if (!user.isAdmin()) {
                System.out.println("\nFace verification required for login...");
                if (!faceRecognitionService.verifyFace(user.getNationalId())) {
                    System.out.println("Login failed: Face verification unsuccessful.");
                    return null;
                }
            }
            currentUser = user;
            System.out.println("Login successful for user: " + username);
            return currentUser;
        } else {
            System.out.println("Login failed: Invalid username or password.");
            return null;
        }
    }

    public void logout() {
        currentUser = null;
        System.out.println("User logged out.");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean deleteUser(String username) {
        Optional<User> userToDelete = users.stream()
                                          .filter(u -> u.getUsername().equals(username))
                                          .findFirst();
        if (userToDelete.isPresent()) {
            users.remove(userToDelete.get());
            System.out.println("User " + username + " deleted successfully.");
            return true;
        } else {
            System.out.println("User " + username + " not found.");
            return false;
        }
    }
}

