package com.example.tanksgame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

public class Rocket extends CanvasComponent {
    private static final float ROCKET_LENGTH = 70; // Adjust as needed
    private static final float ROCKET_WIDTH = 20; // Adjust as needed

    public Rocket(Context context, AttributeSet attrs) {
        super(context, attrs);

        tank = false;

        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("x, y", x + ", " + y);
                move();
                invalidate(); // Request redraw
                handler.postDelayed(this, 50);
            }
        };

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Save the canvas state
        canvas.save();

        // Calculate the center of the tank
        float centerX = (float) x + ROCKET_LENGTH / 2;
        float centerY = (float) y + ROCKET_WIDTH / 2;
        canvas.rotate(angle, centerX, centerY);

        // Draw the rocket as a line for simplicity
        float endX = (float) (x + ROCKET_LENGTH * Math.cos(Math.toRadians(angle)));
        float endY = (float) (y + ROCKET_WIDTH * Math.sin(Math.toRadians(angle)));
        canvas.drawRect((float) x, (float) y, endX, endY, paint);

        // Restore the canvas state
        canvas.restore();
    }
}
