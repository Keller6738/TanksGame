package com.example.tanksgame.canvas;

import static com.example.tanksgame.canvas.MyCanvas.BLUE_ROCKET_BITMAP;
import static com.example.tanksgame.canvas.MyCanvas.GREEN_ROCKET_BITMAP;
import static com.example.tanksgame.canvas.MyCanvas.RED_ROCKET_BITMAP;
import static com.example.tanksgame.canvas.MyCanvas.YELLOW_ROCKET_BITMAP;
import static com.example.tanksgame.util.Rectangle.Type.TANK;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.tanksgame.util.Color;

public class Tank extends CanvasComponent {
    private boolean m_mobility = false;
    private boolean toggleTurningDirection = false;
    private boolean isAlive = true;

    private static final int TURNING_RATE = 3;

    public Tank(Color color, double initX, double initY, int initAngle, Bitmap bitmap) {
        super(TANK, color, initX, initY, initAngle, bitmap);
    }

    public boolean isMoving() {
        return m_mobility;
    }

    public boolean isAlive() {
        return isAlive;
    }

    void kill() {
        isAlive = false;
    }

    void turn() {
        if (isAlive) {
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
    }

    void toggleMobility() {
        m_mobility = !m_mobility;
        if (!m_mobility) {
            toggleTurningDirection = !toggleTurningDirection;
        }
    }

    Rocket getRocket() {
        Bitmap bitmap = null;
        switch (kColor) {
            case BLUE:
                bitmap = BLUE_ROCKET_BITMAP;
                break;
            case RED:
                bitmap = RED_ROCKET_BITMAP;
                break;
            case GREEN:
                bitmap = GREEN_ROCKET_BITMAP;
                break;
            case YELLOW:
                bitmap = YELLOW_ROCKET_BITMAP;
                break;
        }
        return new Rocket(kColor, m_x, m_y, m_angle, bitmap);
    }

    @Override
    void draw(Canvas canvas) {
        // Save the canvas state
        canvas.save();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(m_bitmap, m_bitmap.getWidth() / 3, m_bitmap.getHeight() / 3, true);

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
