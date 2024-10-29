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
    private float rectX = 100; // Initial X position of the rectangle
    private float rectY = 200; // Y position of the rectangle
    private static final float RECT_WIDTH = 200; // Width of the rectangle
    private static final float RECT_HEIGHT = 100; // Height of the rectangle
    private Handler handler;
    private Runnable runnable;
    private boolean isMoving = false; // Track if the button is pressed
    private float rotationAngle = 0; // Rotation angle for the rectangle

    public CustomCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE); // Color for the rectangle
        paint.setAntiAlias(true);
        handler = new Handler(Looper.getMainLooper());
        startAnimation();
    }

    private void startAnimation() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isMoving) {
                    rectX += 10; // Move to the right
                    if (rectX > getWidth()) { // Reset if off-screen
                        rectX = 0;
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
        canvas.save();
        // Rotate around the center of the rectangle
        canvas.rotate(rotationAngle, rectX + RECT_WIDTH / 2, rectY + RECT_HEIGHT / 2);
        // Draw the rectangle
        canvas.drawRect(rectX, rectY, rectX + RECT_WIDTH, rectY + RECT_HEIGHT, paint);
        canvas.restore();
    }

    public void setMoving(boolean moving) {
        isMoving = moving; // Update movement state
    }
}
