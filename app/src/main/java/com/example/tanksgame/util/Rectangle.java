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

    /**
     * Determines if two rectangles are colliding using the Separating Axis Theorem.
     *
     * @param other the rectangle that might collide with this rectangle
     * @return true if the rectangles are colliding, false otherwise
     */
    public boolean checkCollision(Rectangle other) {
        // Get the corners of both rectangles
        double[][] thisRectCorners = getCorners(m_x, m_y, m_width, m_height, m_angle);
        double[][] otherRectCorners = getCorners(other.m_x, other.m_y, other.m_width, other.m_height, other.m_angle);

        // Check for overlap on each axis of both rectangles
        return checkAxesForOverlap(thisRectCorners, otherRectCorners);
    }

    /**
     * Calculates the global coordinates of the corners of a rectangle after rotation.
     *
     * @param cx     the x-coordinate of the rectangle's center
     * @param cy     the y-coordinate of the rectangle's center
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param angle  the angle of rotation in degrees
     * @return a 2D array representing the global coordinates of the rectangle's corners
     */
    private static double[][] getCorners(double cx, double cy, double width, double height, double angle) {
        double rad = Math.toRadians(angle);

        // Half dimensions
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        // Corners relative to the center (unrotated)
        double[] cornersX = {-halfWidth, halfWidth, halfWidth, -halfWidth};
        double[] cornersY = {-halfHeight, -halfHeight, halfHeight, halfHeight};

        // Rotate the corners and calculate their global position
        double[][] corners = new double[4][2];
        for (int i = 0; i < 4; i++) {
            corners[i][0] = cx + cornersX[i] * Math.cos(rad) - cornersY[i] * Math.sin(rad);
            corners[i][1] = cy + cornersX[i] * Math.sin(rad) + cornersY[i] * Math.cos(rad);
        }
        return corners;
    }

    /**
     * Checks if two rectangles overlap on all relevant axes derived from their edges.
     *
     * @param rect1Corners the corners of the first rectangle
     * @param rect2Corners the corners of the second rectangle
     * @return true if the rectangles overlap on all axes, false otherwise
     */
    private static boolean checkAxesForOverlap(double[][] rect1Corners, double[][] rect2Corners) {
        // Get all axes (normals of edges) for both rectangles
        double[][] axes = getAxes(rect1Corners, rect2Corners);

        // Check projections on each axis
        for (double[] axis : axes) {
            double[] projection1 = projectRectangle(rect1Corners, axis);
            double[] projection2 = projectRectangle(rect2Corners, axis);

            // Check for no overlap
            if (projection1[1] < projection2[0] || projection2[1] < projection1[0]) {
                return false; // Separating axis found, no collision
            }
        }
        return true; // No separating axis found, rectangles collide
    }

    /**
     * Calculates the axes (normals of edges) to test for separation between two rectangles.
     *
     * @param rect1Corners the corners of the first rectangle
     * @param rect2Corners the corners of the second rectangle
     * @return a 2D array of unit vectors representing the axes
     */
    private static double[][] getAxes(double[][] rect1Corners, double[][] rect2Corners) {
        double[][] axes = new double[8][2]; // 4 axes for 2 rectangles
        int index = 0;

        // Calculate axes (normals of edges) for both rectangles
        for (int i = 0; i < 2; i++) {
            double[][] rectCorners = (i == 0) ? rect1Corners : rect2Corners;
            for (int j = 0; j < 4; j++) {
                double[] edge = {
                        rectCorners[(j + 1) % 4][0] - rectCorners[j][0],
                        rectCorners[(j + 1) % 4][1] - rectCorners[j][1]
                };
                axes[index][0] = -edge[1]; // Normal x
                axes[index][1] = edge[0];  // Normal y
                normalize(axes[index]);
                index++;
            }
        }
        return axes;
    }

    /**
     * Normalizes a vector to have a magnitude of 1.
     *
     * @param vector the vector to normalize
     */
    private static void normalize(double[] vector) {
        double length = Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1]);
        if (length != 0) {
            vector[0] /= length;
            vector[1] /= length;
        }
    }

    /**
     * Projects the corners of a rectangle onto an axis and returns the projection range.
     *
     * @param corners the corners of the rectangle
     * @param axis    the axis to project onto
     * @return an array containing the minimum and maximum projections
     */
    private static double[] projectRectangle(double[][] corners, double[] axis) {
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for (double[] corner : corners) {
            double projection = corner[0] * axis[0] + corner[1] * axis[1];
            if (projection < min) min = projection;
            if (projection > max) max = projection;
        }

        return new double[]{min, max};
    }

    public enum Type {
        RECTANGLE,
        TANK,
        DIED_TANK,
        ROCKET
    }
}
