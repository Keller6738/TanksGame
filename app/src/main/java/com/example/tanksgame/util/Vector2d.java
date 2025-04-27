package com.example.tanksgame.util;

public class Vector2d {
    private final double m_x, m_y;

    /**
     * A constructor that takes two doubles representing the x and y components:
     *
     * @param x The x component of the Vector
     * @param y The y component of the Vector
     */
    public Vector2d(double x, double y) {
        this.m_x = x;
        this.m_y = y;
    }

    /**
     * A constructor that takes a double representing the distance,
     * and a Rotation2D representing the angle:
     *
     * @param magnitude The distance from the origin
     * @param direction The angle of the Vector in radians
     */
    public static Vector2d fromPollar(double magnitude, double direction) {
        return new Vector2d(magnitude * Math.cos(direction), magnitude * Math.sin(direction));
    }

    public double getX() {
        return m_x;
    }

    public double getY() {
        return m_y;
    }

    public double getDistance() {
        return Math.sqrt(m_x * m_x + m_y * m_y);
    }

    public double getDirection() {
        return Math.atan2(m_y, m_x);
    }

    @Override
    public String toString() {
        return getDistance() + ", " + getDirection();
    }
}
