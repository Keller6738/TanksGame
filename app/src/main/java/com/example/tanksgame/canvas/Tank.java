package com.example.tanksgame.canvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.tanksgame.Color;

public class Tank extends CanvasComponent {
    private boolean m_mobility = false;
    private boolean toggleTurningDirection = false;
    private boolean isAlive = true;

    private static final int TURNING_RATE = 3;

    public Tank(Color color, double initX, double initY, int initAngle) {
        super(true, color, initX, initY, initAngle);
    }

    public boolean isMoving() {
        return m_mobility;
    }

    public boolean isAlive() {
        return isAlive;
    }

    void turn() {
        if (!toggleTurningDirection) {
            this.m_angle += TURNING_RATE;
            if (m_angle >= 360) {
                m_angle = 0; // Reset angle
            }
        } else {
            this.m_angle -= TURNING_RATE;
            if (m_angle <= 0) {
                m_angle = 360; // Reset angle
            }
        }
    }

    void toggleMobility() {
        m_mobility = !m_mobility;
        if (!m_mobility) {
            toggleTurningDirection = !toggleTurningDirection;
        }
    }

    void destroy() {
        isAlive = false;
    }

    @Override
    void draw(Canvas canvas, Bitmap tankBitmap) {
        // Save the canvas state
        canvas.save();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(tankBitmap, tankBitmap.getWidth() / 3, tankBitmap.getHeight() / 3, true);

        int scaledWidth = scaledBitmap.getWidth(), scaledHeight = scaledBitmap.getHeight();
        m_width = scaledWidth;
        m_height = scaledHeight;

        // Rotate the canvas around the center of the tank
        canvas.rotate(m_angle, (float) m_x, (float) m_y);

        canvas.drawBitmap(scaledBitmap, (float) m_x - (float) scaledWidth / 2, (float) m_y - (float) scaledHeight / 2, null);

        // Restore the canvas state
        canvas.restore();
    }
}
