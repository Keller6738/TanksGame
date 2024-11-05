package com.example.tanksgame.canvas;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

import android.graphics.Paint;

import com.example.tanksgame.Color;

public abstract class CanvasComponent {
    protected final boolean isTank;
    protected final Color kColor;

    protected double m_x, m_y;
    protected int m_angle;

    protected final Paint m_basicBrash;

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

    public Color getColor() {
        return kColor;
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

    void move() {
        // Convert the angle to radians for use with Math functions
        double angleInRadians = Math.toRadians(m_angle);

        // Calculate movement based purely on angle
        this.m_x += (isTank ? 3 : 5) * Math.cos(angleInRadians);
        this.m_y += (isTank ? 3 : 5) * Math.sin(angleInRadians);
    }
}
