package com.example.tanksgame;

import static android.graphics.Color.BLUE;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

public class Tank extends CanvasComponent {
    private boolean isMoving = false;
    private Paint paint;
    private final Runnable runnable;

    private static final float TANK_WIDTH = 75; // Width of the tank body
    private static final float TANK_HEIGHT = 100; // Height of the tank body
    private static final float CANNON_LENGTH = 40; // Length of the cannon

    public Tank(Context context, AttributeSet attrs) {
        super(context, attrs);

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

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(BLUE);
        paint.setAntiAlias(true);

        dutyCycle(runnable);
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
        paint.setColor(android.graphics.Color.GREEN);
        canvas.drawRect((float) x, (float) y,
                (float) x + TANK_WIDTH, (float) y + TANK_HEIGHT, paint);

        // Draw cannon
        paint.setColor(android.graphics.Color.GREEN);
        canvas.drawRect(centerX - 10, (float) y - CANNON_LENGTH,
                centerX + 10, (float) y, paint); // Adjust cannon position

        // Restore the canvas state
        canvas.restore();
    }
}
