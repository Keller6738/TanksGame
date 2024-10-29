package com.example.tanksgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomCanvasView extends View {
    private Paint paint;
    private float circleX = 200; // Initial X position
    private float circleY = 200; // Initial Y position
    private static final float CIRCLE_RADIUS = 100; // Radius of the circle

    public CustomCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE); // Initial color
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the circle with updated position
        canvas.drawCircle(circleX, circleY, CIRCLE_RADIUS, paint);
    }

    public void updateCirclePosition(float x, float y) {
        circleX = x;
        circleY = y;
        invalidate(); // Re-trigger onDraw to reflect changes
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Update circle position on touch
            updateCirclePosition(event.getX(), event.getY());
            return true;
        }
        return false;
    }
}
