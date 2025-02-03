package com.example.tanksgame.canvas;

import static com.example.tanksgame.util.Rectangle.Type.TANK;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.tanksgame.util.Color;
import com.example.tanksgame.util.Rectangle;
import com.example.tanksgame.util.Vector2d;

/**
 * A class represents a canvas component
 */
public abstract class CanvasComponent extends Rectangle {
    protected final Color kColor;
    protected Bitmap m_bitmap;

    protected Vector2d m_moveVector;

    private static final int TANK_AT_EDGE_ERROR = 25;
    private static final int ROCKET_AT_EDGE_ERROR = 25;

    /**
     * A constructor that takes initial setup parameters
     *
     * @param color     The color of the component
     * @param initX     The initial X pose of the component
     * @param initY     The initial Y pose of the component
     * @param initAngle The initial angle of the component
     */
    public CanvasComponent(Type type, Color color, double initX, double initY, int initAngle, Bitmap bitmap) {
        super(type, initX, initY, initAngle);
        kColor = color;
        m_bitmap = bitmap;
    }

    /**
     * @return Component color
     */
    public Color getColor() {
        return kColor;
    }

    /**
     * A function that moves the tank if crashing
     *
     * @param screenWidth       the width of the screen
     * @param screenHeight      the height of the screen
     */
    void move(int screenWidth, int screenHeight) {
        m_moveVector = Vector2d.fromPollar(m_type == TANK ? 3 : 7, Math.toRadians(m_angle));
        boolean mobilityX = getMobilityX(screenWidth), mobilityY = getMobilityY(screenHeight);

        if (!mobilityX) {
            m_moveVector = new Vector2d(0, m_moveVector.getY());
        }
        if (!mobilityY) {
            m_moveVector = new Vector2d(m_moveVector.getX(), 0);
        }

        this.m_x += m_moveVector.getX();
        this.m_y += m_moveVector.getY();
    }

    boolean getMobilityX(int width) {
        int atEdgeError = m_type == TANK ? TANK_AT_EDGE_ERROR : ROCKET_AT_EDGE_ERROR;

        return !((m_x - atEdgeError <= 0 && (m_angle > 90 && m_angle < 270)) ||
                ((m_x + atEdgeError >= width && (m_angle < 90 || m_angle > 270))));
    }

    boolean getMobilityY(int height) {
        int atEdgeError = m_type == TANK ? TANK_AT_EDGE_ERROR : ROCKET_AT_EDGE_ERROR;

        return !((m_y - atEdgeError <= 0 && m_angle > 180) ||
                (m_y + atEdgeError >= height && m_angle < 180));
    }

    /**
     * @param canvas The canvas surface
     */
    abstract void draw(Canvas canvas);
}
