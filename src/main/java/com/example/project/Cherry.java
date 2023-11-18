package com.example.project;

public class Cherry implements Collider {
    private double xPos;
    private double yPos;

    public Cherry(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public boolean checkCollision(Collider other) {
        // Collision logic with another collider (e.g., hero or stick)
        return false;
    }

    // Additional methods for cherry behavior
}