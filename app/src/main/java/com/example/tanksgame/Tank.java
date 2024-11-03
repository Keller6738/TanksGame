package com.example.tanksgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;

public class Tank extends CanvasComponent {
    private boolean isMoving = false;

    private final Paint wheelsPaint;

    private static final float TANK_WIDTH = 55; // Width of the tank body
    private static final float TANK_HEIGHT = 100;
    private static final float WHEEL_WIDTH = 25; // Width of the tank body
    private static final float CANNON_LENGTH = 40; // Length of the cannon

    public Tank(Context context, AttributeSet attrs) {
        super(context, attrs);

        tank = true;

        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("x, y, angle", x + ", " + y + ", " + angle);
                if (isMoving) {
                    move();
                } else {
                    turn();
                }
                invalidate(); // Request redraw
                handler.postDelayed(this, 50);
            }
        };

        wheelsPaint = new Paint();
        wheelsPaint.setAntiAlias(true);
        wheelsPaint.setColor(Color.rgb(0, 0, 0));

        init();
    }

    public void turn() {
        this.angle += 5;
        if (angle >= 360) {
            angle = 0; // Reset angle
        }
    }

    public void toggleMobility() {
        this.isMoving = !this.isMoving;
    }

    public Point getCannonTip() {
        return new Point((int) (x + TANK_WIDTH / 2), (int) (y + TANK_HEIGHT / 2));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Save the canvas state
        canvas.save();

        // Calculate the center of the tank
        float centerX = (float) x + TANK_WIDTH / 2;
        float centerY = (float) y + TANK_HEIGHT / 2;

        // Rotate the canvas around the center of the tank
        canvas.rotate(angle + 90, centerX, centerY);

        // Draw tank body
        canvas.drawRect((float) x, (float) y,
                (float) x + TANK_WIDTH, (float) y + TANK_HEIGHT, paint);

        // Draw cannon
        canvas.drawRect(centerX - 10, (float) y - CANNON_LENGTH,
                centerX + 10, (float) y, paint); // Adjust cannon position

        //Draw wheels
        canvas.drawRect((float) x + TANK_WIDTH, (float) y,
                (float) x + TANK_WIDTH + WHEEL_WIDTH, (float) y + TANK_HEIGHT, wheelsPaint);

        canvas.drawRect((float) x, (float) y,
                (float) x - WHEEL_WIDTH, (float) y + TANK_HEIGHT, wheelsPaint);

        // Restore the canvas state
        canvas.restore();
    }
}
