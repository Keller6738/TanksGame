package com.example.tanksgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

public abstract class CanvasComponent extends View {
    protected Color kColor;
    protected double x, y;
    protected int angle;

    protected Handler handler;

    public CanvasComponent(Context context, AttributeSet attrs) {
        super(context, attrs);

        angle = 0;

        handler = new Handler(Looper.getMainLooper());
    }

    public Color getColor() {
        return kColor;
    }

    protected void move() {
        // Convert the angle to radians for use with Math functions
        double angleInRadians = Math.toRadians(angle);

        // Calculate movement based purely on angle
        this.x += 10 * Math.cos(angleInRadians);
        this.y += 10 * Math.sin(angleInRadians);
    }

    protected void setColor(Color color) {
        kColor = color;
    }

    public void setInitX(double x) {
        this.x = x;
    }

    public void setInitY(double y) {
        this.y = y;
    }

    protected void dutyCycle(Runnable runnable) {
        handler.post(runnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
}
