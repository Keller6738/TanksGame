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
    private float circleX = 200; // Initial X position
    private float circleY = 200; // Initial Y position
    private static final float CIRCLE_RADIUS = 100; // Radius of the circle
    private Handler handler;
    private Runnable runnable;

    public CustomCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE); // Initial color
        paint.setAntiAlias(true);
        handler = new Handler(Looper.getMainLooper());

        // Start the periodic movement
        startAnimation();
    }

    private void startAnimation() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // Update the position of the circle
                circleX += 10; // Move to the right
                if (circleX > getWidth()) { // Reset if off-screen
                    circleX = 0;
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
        // Draw the circle with updated position
        canvas.drawCircle(circleX, circleY, CIRCLE_RADIUS, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Update circle position on touch (optional)
            updateCirclePosition(event.getX(), event.getY());
            return true;
        }
        return false;
    }

    public void updateCirclePosition(float x, float y) {
        circleX = x;
        circleY = y;
        invalidate(); // Re-trigger onDraw to reflect changes
    }
}
