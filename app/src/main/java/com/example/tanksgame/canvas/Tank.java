package com.example.tanksgame.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

public class Tank extends CanvasComponent {
    private boolean isMoving = false;

    private final Paint wheelsPaint;

    private static final float TANK_WIDTH = 100; // Width of the tank body
    private static final float TANK_HEIGHT = 45;
    private static final float WHEEL_WIDTH = 20; // Width of the tank body
    private static final float CANNON_LENGTH = 35; // Length of the cannon
    private static final float CANNON_WIDTH = 20; // Length of the cannon

    public Tank(Context context, AttributeSet attrs) {
        super(context, attrs);

        tank = true;

        runnable = () -> {
            Log.d("tank", x + ", " + y + ", " + angle);
            if (isMoving) {
                move();
            } else {
                turn();
            }
            invalidate(); // Request redraw
            handler.postDelayed(runnable, 10);
        };


        wheelsPaint = new Paint();
        wheelsPaint.setAntiAlias(true);
        wheelsPaint.setColor(Color.rgb(0, 0, 0));

        init();
    }

    public void turn() {
        this.angle += 2;
        if (angle >= 360) {
            angle = 0; // Reset angle
        }
    }

    public void toggleMobility() {
        this.isMoving = !this.isMoving;
    }

    public double getCannonTip() {
        return x + TANK_WIDTH / 2 + CANNON_LENGTH;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Save the canvas state
        canvas.save();

        // Calculate the center of the tank
        float topLeftX = (float) x - TANK_WIDTH / 2;
        float topLeftY = (float) y + TANK_HEIGHT / 2;
        float bottomRightX = (float) x + TANK_HEIGHT / 2;
        float bottomRightY = (float) y - TANK_HEIGHT / 2;

        // Rotate the canvas around the center of the tank
        canvas.rotate(angle, (float) x, (float) y);

        // Draw tank body
        canvas.drawRect(topLeftX, topLeftY,
                bottomRightX, bottomRightY, paint);

        // Draw cannon
        canvas.drawRect(bottomRightX, (float) y + CANNON_WIDTH / 2,
                bottomRightX + CANNON_LENGTH, (float) y - CANNON_WIDTH / 2, paint); // Adjust cannon position

        //Draw wheels
        canvas.drawRect(topLeftX, topLeftY + WHEEL_WIDTH,
                bottomRightX, topLeftY, wheelsPaint);

        canvas.drawRect(topLeftX, bottomRightY,
                bottomRightX, bottomRightY - WHEEL_WIDTH, wheelsPaint);

        // Restore the canvas state
        canvas.restore();
    }
}