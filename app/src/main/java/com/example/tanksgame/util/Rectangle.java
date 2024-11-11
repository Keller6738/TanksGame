package com.example.tanksgame.util;

public class Rectangle {
    private final double m_width, m_height;
    private final double m_centerX, m_centerY;
    private final int m_angle;

    public Rectangle(double width, double height, double x, double y, int angle) {
        this.m_width = width;
        this.m_height = height;
        this.m_centerX = x;
        this.m_centerY = y;
        this.m_angle = angle;
    }

    public double getWidth() {
        return m_width;
    }

    public double getHeight() {
        return m_height;
    }

    public double getX() {
        return m_centerX;
    }

    public double getY() {
        return m_centerY;
    }

    public int getAngle() {
        return m_angle;
    }

    public boolean contains(double x, double y) {
        // Translate point to rectangle's coordinate system
        double translatedX = x - m_centerX;
        double translatedY = y - m_centerY;

        double angleRadians = Math.toRadians(m_angle);
        // Rotate the point in the opposite direction of the rectangle's rotation
        double rotatedX = translatedX * Math.cos(-angleRadians) - translatedY * Math.sin(-angleRadians);
        double rotatedY = translatedX * Math.sin(-angleRadians) + translatedY * Math.cos(-angleRadians);

        // Check if the rotated point is within the bounds of an axis-aligned rectangle
        return Math.abs(rotatedX) <= m_width / 2 && Math.abs(rotatedY) <= m_height / 2;
    }
}
