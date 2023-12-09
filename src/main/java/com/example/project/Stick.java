package com.example.project;

public class Stick implements Rectangle {
    private double length;
    private double width;// this will be final as the width of the stick will not change

    public Stick(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return length; // Assuming the stick is vertical
    }
}
