package com.example.tanksgame.util;

import static com.example.tanksgame.util.Rectangle.Type.RECTANGLE;

public abstract class Rectangle {
    // Fields representing the rectangle's properties
    protected double m_width; // Width of the rectangle
    protected double m_height; // Length of the rectangle
    protected double m_x; // X-coordinate of the rectangle's center
    protected double m_y; // Y-coordinate of the rectangle's center
    protected int m_angle; // Angle of rotation in degrees

    protected Type m_type = RECTANGLE;

    // Constructor to initialize the rectangle with given dimensions, position, and angle
    public Rectangle(double x, double y, int angle) {
        this.m_x = x;
        this.m_y = y;
        this.m_angle = angle;
    }

    public Rectangle(Type type, double x, double y, int angle) {
        this(x, y, angle);
        m_type = type;
    }

    // Getter methods to retrieve field values
    public double getWidth() {
        return m_width;
    }

    public double getHeight() {
        return m_height;
    }

    public double getX() {
        return m_x;
    }

    public double getY() {
        return m_y;
    }

    public int getAngle() {
        return m_angle;
    }

    public Type getType() {
        return m_type;
    }

    public enum Type {
        RECTANGLE,
        TANK,
        DIED_TANK,
        ROCKET
    }
}
