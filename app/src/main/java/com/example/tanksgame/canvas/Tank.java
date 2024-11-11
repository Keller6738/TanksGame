package com.example.tanksgame.canvas;

import static android.graphics.Color.rgb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.tanksgame.Color;

public class Tank extends CanvasComponent {
    private final Paint m_wheelsBrush;

    private boolean isMoving = false;
    private boolean toggleTurningDirection = false;

    private static final int TURNING_RATE = 3;

    public Tank(Color color, double initX, double initY, int initAngle) {
        super(true, color, initX, initY, initAngle);

        m_wheelsBrush = new Paint();
        m_wheelsBrush.setAntiAlias(true);
        m_wheelsBrush.setColor(rgb(0, 0, 0));
    }

    public boolean isMoving() {
        return isMoving;
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
        isMoving = !isMoving;
        if (!isMoving) {
            toggleTurningDirection = !toggleTurningDirection;
        }
    }

    @Override
    void draw(Canvas canvas, Bitmap tankBitmap) {
        // Save the canvas state
        canvas.save();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(tankBitmap, tankBitmap.getWidth() / 3, tankBitmap.getHeight() / 3, true);

        int scaledWidth = scaledBitmap.getWidth(), scaledHeight = scaledBitmap.getHeight();

        // Rotate the canvas around the center of the tank
        canvas.rotate(m_angle, (float) m_x, (float) m_y);

        canvas.drawBitmap(scaledBitmap, (float) m_x - (float) scaledWidth / 2, (float) m_y - (float) scaledHeight / 2, null);

        // Restore the canvas state
        canvas.restore();
    }
}
