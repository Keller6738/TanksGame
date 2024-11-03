package com.example.tanksgame.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

public class Rocket extends CanvasComponent {
    private final Paint topPaint;

    public static final float ROCKET_LENGTH = 30; // Adjust as needed
    private static final float ROCKET_WIDTH = 20; // Adjust as needed

    public Rocket(Context context, AttributeSet attrs) {
        super(context, attrs);

        tank = false;

        runnable = () -> {
                Log.d("x, y", x + ", " + y);
                move();
                invalidate(); // Request redraw
                handler.postDelayed(runnable, 10);
        };

        topPaint = new Paint();
        topPaint.setColor(Color.rgb(20, 30, 70));
        topPaint.setAntiAlias(true);

        init();
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Save the canvas state
        canvas.save();

        canvas.rotate(angle, (float) x, (float) y);

        canvas.drawRect((float) x - ROCKET_LENGTH / 2, (float) y + ROCKET_WIDTH / 2,
                (float) x + ROCKET_LENGTH / 2, (float) y - ROCKET_WIDTH / 2, paint);
        canvas.drawCircle((float) x + ROCKET_LENGTH / 2, (float) y, 10, topPaint);

        // Restore the canvas state
        canvas.restore();
    }
}
