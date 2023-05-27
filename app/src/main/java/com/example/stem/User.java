package com.example.stem;

public class User {
    private String userId;
    private String username;
    private int points;
    private int numberOfTaskCompleted;

    public User() {
        // Default constructor required for Firebase
        this.userId = "";
        this.username = "";
        this.points = 0;
        this.numberOfTaskCompleted = 0;
    }

    public User(String userId, String username, int points, int  numberOfTaskCompleted) {
        this.userId = userId;
        this.username = username;
        this.points = points;
        this.numberOfTaskCompleted = numberOfTaskCompleted;
    }

    public int getNumberOfTaskCompleted() {
        return numberOfTaskCompleted;
    }

    public void setNumberOfTaskCompleted(int numberOfTaskCompleted) {
        this.numberOfTaskCompleted = numberOfTaskCompleted;
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

