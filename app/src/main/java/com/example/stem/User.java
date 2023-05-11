package com.example.stem;

public class User {
    private String userId;
    private String username;
    private int points;

    public User() {
        // Default constructor required for Firebase
        this.userId = "";
        this.username = "";
        this.points = 0;
    }

    public User(String userId, String username, int points) {
        this.userId = userId;
        this.username = username;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

