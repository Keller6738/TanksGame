package com.example.tanksgame.canvas;

import static android.graphics.Color.rgb;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.tanksgame.Color;

public class Tank extends CanvasComponent {
    private final Paint m_wheelsPaint;

    private boolean isMoving = false;
    private boolean toggleTurningDirection = false;

    private static final float TANK_HEIGHT = 100; // Width of the tank body
    private static final float TANK_WIDTH = 45;
    private static final float WHEEL_WIDTH = 20; // Width of the tank body
    private static final float CANNON_LENGTH = 35; // Length of the cannon
    private static final float CANNON_WIDTH = 20; // Length of the cannon

    public Tank(Color color, double initX, double initY, int initAngle) {
        super(true, color, initX, initY, initAngle);

        m_wheelsPaint = new Paint();
        m_wheelsPaint.setAntiAlias(true);
        m_wheelsPaint.setColor(rgb(0, 0, 0));
    }

    public boolean isMoving() {
        return isMoving;
    }

    void turn() {
        if (!toggleTurningDirection) {
            this.m_angle += 2;
            if (m_angle >= 360) {
                m_angle = 0; // Reset angle
            }
        } else{
            this.m_angle -= 2;
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

    public double getCannonTip() {
        return m_x + TANK_HEIGHT / 2 + CANNON_LENGTH;
    }

    void draw(Canvas canvas) {
        // Save the canvas state
        canvas.save();

        // Calculate the center of the tank
        float topLeftX = (float) m_x - TANK_HEIGHT / 2;
        float topLeftY = (float) m_y + TANK_WIDTH / 2;
        float bottomRightX = (float) m_x + TANK_WIDTH / 2;
        float bottomRightY = (float) m_y - TANK_WIDTH / 2;

        // Rotate the canvas around the center of the tank
        canvas.rotate(m_angle, (float) m_x, (float) m_y);

        // Draw tank body
        canvas.drawRect(topLeftX, topLeftY,
                bottomRightX, bottomRightY, m_basicBrash);

        // Draw cannon
        canvas.drawRect(bottomRightX, (float) m_y + CANNON_WIDTH / 2,
                bottomRightX + CANNON_LENGTH, (float) m_y - CANNON_WIDTH / 2, m_basicBrash); // Adjust cannon position

        //Draw wheels
        canvas.drawRect(topLeftX, topLeftY + WHEEL_WIDTH,
                bottomRightX, topLeftY, m_wheelsPaint);

        canvas.drawRect(topLeftX, bottomRightY,
                bottomRightX, bottomRightY - WHEEL_WIDTH, m_wheelsPaint);

        // Restore the canvas state
        canvas.restore();
    }
}
