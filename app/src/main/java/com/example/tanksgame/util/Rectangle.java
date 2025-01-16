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

        // Setter methods to modify field values with validation where necessary
        public void setWidth(double width) {
            if (width > 0) {
                this.m_width = width;
            } else {
                throw new IllegalArgumentException("Width must be positive.");
            }
        }

        public void setLength(double length) {
            if (length > 0) {
                this.m_height = length;
            } else {
                throw new IllegalArgumentException("Length must be positive.");
            }
        }

        public void setX(double x) {
            this.m_x = x;
        }

        public void setY(double y) {
            this.m_y = y;
        }

        public void setAngle(int angle) {
            this.m_angle = angle % 360;
        }

        // Method to move the rectangle by a specified offset in the x and y directions
        public void move(double deltaX, double deltaY) {
            this.m_x += deltaX; // Update x-coordinate
            this.m_y += deltaY; // Update y-coordinate
        }

        // Method to rotate the rectangle by a specified angle
        public void rotate(int deltaAngle) {
            this.m_angle = (this.m_angle + deltaAngle) % 360;
        }

        // Method to calculate the area of the rectangle
        public double getArea() {
            return m_width * m_height;
        }

        // Method to calculate the perimeter of the rectangle
        public double getPerimeter() {
            return 2 * (m_width + m_height);
        }

    public boolean checkCollision(Rectangle other) {
        // Get the boundaries of this rectangle
        double thisLeft = this.m_x - this.m_width / 2;
        double thisRight = this.m_x + this.m_width / 2;
        double thisTop = this.m_y - this.m_height / 2;
        double thisBottom = this.m_y + this.m_height / 2;

        // Get the boundaries of the other rectangle
        double otherLeft = other.getX() - other.getWidth() / 2;
        double otherRight = other.getX() + other.getWidth() / 2;
        double otherTop = other.getY() - other.getHeight() / 2;
        double otherBottom = other.getY() + other.getHeight() / 2;

        // Check for overlap in the x and y axes
        boolean overlapX = thisLeft < otherRight && thisRight > otherLeft;
        boolean overlapY = thisTop < otherBottom && thisBottom > otherTop;

        // If both axes overlap, there is a collision
        return overlapX && overlapY;
    }

    public enum Type {
        RECTANGLE,
        TANK,
        DIED_TANK,
        ROCKET
    }
}
