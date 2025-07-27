package com.election.simulator.model;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String nationalId;
    private boolean isAdmin;
    private boolean hasVoted;

    public User(String username, String password, String fullName, String nationalId, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.isAdmin = isAdmin;
        this.hasVoted = false;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", fullName='" + fullName + '\'' +
               ", nationalId='" + nationalId + '\'' +
               ", isAdmin=" + isAdmin +
               ", hasVoted=" + hasVoted +
               '}';
    }
}

