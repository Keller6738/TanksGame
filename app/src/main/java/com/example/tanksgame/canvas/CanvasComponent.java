package com.example.tanksgame.canvas;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.tanksgame.Circle;
import com.example.tanksgame.util.Color;
import com.example.tanksgame.util.Vector2d;

/**
 * A class represents a canvas component
 */
public abstract class CanvasComponent {
    protected final boolean isTank;
    protected final Color kColor;

    protected double m_width, m_height;
    protected double m_x, m_y;
    protected int m_angle;

    protected Vector2d moveVector;

    protected final Paint m_basicBrash;

    public static final int TANK_AT_EDGE_ERROR = 25;
    private static final int ROCKET_AT_EDGE_ERROR = 25;

    /**
     * A constructor that takes initial setup parameters
     *
     * @param tank      A boolean value that checks if the component is tank
     * @param color     The color of the component
     * @param initX     The initial X pose of the component
     * @param initY     The initial Y pose of the component
     * @param initAngle The initial angle of the component
     */
    public CanvasComponent(boolean tank, Color color, double initX, double initY, int initAngle) {
        isTank = tank;
        kColor = color;
        m_x = initX;
        m_y = initY;
        m_angle = initAngle;

        m_basicBrash = new Paint();
        m_basicBrash.setAntiAlias(true);
        configurePaintColor();
    }

    private void configurePaintColor() {
        switch (kColor) {
            case BLUE:
                m_basicBrash.setColor(BLUE);
                break;
            case RED:
                m_basicBrash.setColor(RED);
                break;
            case GREEN:
                m_basicBrash.setColor(GREEN);
                break;
            case YELLOW:
                m_basicBrash.setColor(YELLOW);
                break;
            default:
                m_basicBrash.setColor(BLACK);
        }
    }

    /**
     * @return Component color
     */
    public Color getColor() {
        return kColor;
    }

    /**
     * @return X pose of the component
     */
    public double getX() {
        return m_x;
    }

    /**
     * @return Y pose of the component
     */
    public double getY() {
        return m_y;
    }

    /**
     * @return Angle of the component
     */
    public int getAngle() {
        return m_angle;
    }

    /**
     * @return Whether the component is tank
     */
    public boolean isTank() {
        return isTank;
    }

    /**
     * A function that check whether the component contains a point
     *
     * @param x The X variable of the point
     * @param y The Y variable of the point
     * @return Whether the component contains the point
     */
    public boolean contains(double x, double y) {
        // Translate point to rectangle's coordinate system
        double translatedX = x - m_x;
        double translatedY = y - m_y;

        double angleRadians = Math.toRadians(m_angle);
        // Rotate the point in the opposite direction of the rectangle's rotation
        double rotatedX = translatedX * Math.cos(-angleRadians) - translatedY * Math.sin(-angleRadians);
        double rotatedY = translatedX * Math.sin(-angleRadians) + translatedY * Math.cos(-angleRadians);

        // Check if the rotated point is within the bounds of an axis-aligned rectangle
        return Math.abs(rotatedX) <= m_width / 2 && Math.abs(rotatedY) <= m_height / 2;
    }

    /**
     * A function that check whether the component contains another component or part of it
     *
     * @param other Other canvas component
     * @return Whether the component contains the component or part of it
     */
    public boolean contains(CanvasComponent other) {
        double[] intersectionPoints = Circle.findIntersectionPoints(
                new Circle(m_x, m_y,
                        Math.sqrt(m_width * m_width + m_height * m_height) / 2),
                new Circle(other.m_x, other.m_y,
                        Math.sqrt(other.m_width * other.m_width + other.m_height * other.m_height) / 2)
        );

        return intersectionPoints.length != 0;
    }

    /**
     * A function that moves the tank
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    boolean move(int screenWidth, int screenHeight) {
        return move(screenWidth, screenHeight, null);
    }

    /**
     * A function that moves the tank if crashing crashing
     *
     * @param screenWidth       the width of the screen
     * @param screenHeight      the height of the screen
     * @param crashingComponent the CanvasComponent which this component crashing into
     * @return whether the component moved in two axes
     */
    boolean move(int screenWidth, int screenHeight, CanvasComponent crashingComponent) {
        moveVector = Vector2d.fromPollar(isTank ? 3 : 7, Math.toRadians(m_angle));
        if (crashingComponent != null) {
            if (isTank) {
            } else {
                if (crashingComponent.isTank) {
                    Tank tank = (Tank) crashingComponent;
                    tank.destroy();
                } else {
                    return false;
                }
            }
        } else {
            if (getMobilityX(screenWidth)) {
                this.m_x += moveVector.getX();
            } else {
                return false;
            }
            if (getMobilityY(screenHeight)) {
                this.m_y += moveVector.getY();
            } else {
                return false;
            }
        }

        return true;
    }

    boolean getMobilityX(int width) {
        int atEdgeError = isTank ? TANK_AT_EDGE_ERROR : ROCKET_AT_EDGE_ERROR;

        return !((m_x - atEdgeError <= 0 && (m_angle > 90 && m_angle < 270)) ||
                ((m_x + atEdgeError >= width && (m_y < 90 || m_angle > 270))));
    }

    boolean getMobilityY(int height) {
        int atEdgeError = isTank ? TANK_AT_EDGE_ERROR : ROCKET_AT_EDGE_ERROR;

        return !((m_y - atEdgeError <= 0 && m_angle > 180) ||
                (m_y + atEdgeError >= height && m_angle < 180));
    }

    /**
     * @param canvas The canvas surface
     * @param bitmap A bitmap represents the visualize of the component
     */
    abstract void draw(Canvas canvas, Bitmap bitmap);
}
