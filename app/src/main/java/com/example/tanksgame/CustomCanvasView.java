package com.example.tanksgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

public class CustomCanvasView extends View {
    private Paint paint;
    private float tankX = 100; // Initial X position of the tank
    private float tankY = 400; // Y position of the tank
    private static final float TANK_WIDTH = 200; // Width of the tank body
    private static final float TANK_HEIGHT = 100; // Height of the tank body
    private static final float TREAD_WIDTH = 40; // Width of the treads
    private static final float CANNON_LENGTH = 80; // Length of the cannon
    private float rotationAngle = 0; // Rotation angle for the cannon

    private Handler handler;
    private Runnable runnable;
    private boolean isMoving = false;

    public CustomCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN); // Color for the tank
        paint.setAntiAlias(true);
        handler = new Handler(Looper.getMainLooper());
        startAnimation();
    }

    private void startAnimation() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isMoving) {
                    tankX += 10; // Move to the right
                    if (tankX > getWidth()) { // Reset if off-screen
                        tankX = 0;
                    }
                } else {
                    rotationAngle += 5; // Rotate the rectangle
                    if (rotationAngle >= 360) {
                        rotationAngle = 0; // Reset angle
                    }
                }
                invalidate(); // Request redraw
                handler.postDelayed(this, 50); // Repeat every 100ms
            }
        };
        handler.post(runnable); // Start the animation
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Save the canvas state
        canvas.save();

        // Rotate around the center of the tank
        canvas.rotate(rotationAngle, tankX + TANK_WIDTH / 2, tankY + TANK_HEIGHT / 2);

        // Draw tank body
        paint.setColor(Color.GREEN);
        canvas.drawRect(tankX, tankY, tankX + TANK_WIDTH, tankY + TANK_HEIGHT, paint);

        // Draw tank treads
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(tankX, tankY + TANK_HEIGHT, tankX + TANK_WIDTH, tankY + TANK_HEIGHT + TREAD_WIDTH, paint);

        // Draw cannon
        paint.setColor(Color.GREEN);
        canvas.drawRect(tankX + TANK_WIDTH / 2 - 10, tankY - CANNON_LENGTH,
                tankX + TANK_WIDTH / 2 + 10, tankY, paint); // Draw cannon

        // Restore the canvas state
        canvas.restore();
    }

    public void setMoving(boolean moving) {
        isMoving = moving; // Update movement state
    }
}
