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

    public Vector2d plus(Vector2d other) {
        return new Vector2d(m_x + other.m_x, m_y + other.m_y);
    }

    /**
     * A function that multiplies the Vector by a scalar
     *
     * @return a new Vector2D that represents the product of the Vectors
     */
    public Vector2d mul(double scalar) {
        return new Vector2d(m_x * scalar, m_y * scalar);
    }

    public Vector2d rotateBy(double deltaDirection) {
        return Vector2d.fromPollar(getDistance(), getDirection() + deltaDirection);
    }

    public Vector2d withDistance(double newDistance) {
        return Vector2d.fromPollar(newDistance, getDirection());
    }

    @Override
    public String toString() {
        return getDistance() + ", " + getDirection();
    }
}
