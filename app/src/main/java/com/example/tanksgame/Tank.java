package com.example.tanksgame;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;

public class Tank extends CanvasComponent {
    private boolean isMoving = false;

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

        // Restore the canvas state
        canvas.restore();
    }
}
