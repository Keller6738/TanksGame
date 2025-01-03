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

    protected Vector2d moveVector;

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
     * A function that moves the tank
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    void move(int screenWidth, int screenHeight) {
        move(screenWidth, screenHeight, null);
    }

    /**
     * A function that moves the tank if crashing
     *
     * @param screenWidth       the width of the screen
     * @param screenHeight      the height of the screen
     * @param crashingComponent the CanvasComponent which this component crashing into
     */
    void move(int screenWidth, int screenHeight, Rectangle crashingComponent) {
        moveVector = Vector2d.fromPollar(m_type == TANK ? 3 : 7, Math.toRadians(m_angle));
        boolean mobilityX = getMobilityX(screenWidth), mobilityY = getMobilityY(screenHeight);

        /*if (crashingComponent != null) {
            if (m_type == TANK) {
                if (crashingComponent.getType() == TANK) {
                    int deltaAngle = crashingComponent.getAngle() - m_angle;
                    if (deltaAngle == 90) {
                        moveVector = new Vector2d(0, 0);
                    } else {
                        int rotation = -(crashingComponent.getAngle() - (crashingComponent.getAngle() - 90));
                        moveVector = moveVector.rotateBy(rotation);
                        moveVector = new Vector2d(0, moveVector.getY());
                        moveVector = moveVector.rotateBy(-rotation);
                    }
                }
            }
        }*/

        if (!mobilityX) {
            moveVector = new Vector2d(0, moveVector.getY());
        }
        if (!mobilityY) {
            moveVector = new Vector2d(moveVector.getX(), 0);
        }

        this.m_x += moveVector.getX();
        this.m_y += moveVector.getY();
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
