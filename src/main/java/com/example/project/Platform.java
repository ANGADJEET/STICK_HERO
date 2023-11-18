package com.example.project;

public class Platform implements Rectangle, Collider {
    private double xPos;
    private double width;
    private double height;

    public Platform(double xPos, double width, double height) {
        this.xPos = xPos;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean checkCollision(Collider other) {
        // Collision logic with another collider (e.g., hero or stick)
        return false;
    }
}