package com.example.tanksgame;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

public abstract class CanvasComponent extends View {
    protected Color kColor;
    protected double x, y;
    protected int angle;

    protected Paint paint;
    protected Runnable runnable;

    protected Handler handler;

    public CanvasComponent(Context context, AttributeSet attrs) {
        super(context, attrs);

        angle = 0;

        handler = new Handler(Looper.getMainLooper());
    }

    protected void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        dutyCycle(runnable);
    }

    public Color getColor() {
        return kColor;
    }

    public void setColor(Color color) {
        kColor = color;
    }

    public void setPaintColor() {
        switch (kColor) {
            case BLUE:
                paint.setColor(BLUE);
                break;
            case RED:
                paint.setColor(RED);
                break;
            case GREEN:
                paint.setColor(GREEN);
                break;
            case YELLOW:
                paint.setColor(YELLOW);
                break;
            default:
                paint.setColor(BLACK);
        }
    }

    public void setInitX(double x) {
        this.x = x;
    }

    public void setInitY(double y) {
        this.y = y;
    }

    protected void move() {
        // Convert the angle to radians for use with Math functions
        double angleInRadians = Math.toRadians(angle);

        // Calculate movement based purely on angle
        this.x += 10 * Math.cos(angleInRadians);
        this.y += 10 * Math.sin(angleInRadians);
    }

    protected void dutyCycle(Runnable runnable) {
        handler.post(runnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
}
