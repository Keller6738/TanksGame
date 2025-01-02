package com.example.tanksgame.canvas;

import static com.example.tanksgame.util.Rectangle.Type.ROCKET;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.tanksgame.util.Color;

public class Rocket extends CanvasComponent {
    private int m_timer;

    public Rocket(Color color, double initX, double initY, int initAngle) {
        super(ROCKET, color, initX, initY, initAngle);

        m_timer = 0;
    }

    void timer() {
        m_timer += 10;
    }

    boolean checkTime() {
        return m_timer >= 150;
    }

    @Override
    void draw(Canvas canvas, Bitmap rocketBitmap) {
        // Save the canvas state
        canvas.save();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(rocketBitmap, rocketBitmap.getWidth() / 4, rocketBitmap.getHeight() / 4, true);

        int scaledWidth = scaledBitmap.getWidth(), scaledHeight = scaledBitmap.getHeight();

        canvas.rotate(m_angle, (float) m_x, (float) m_y);

        canvas.drawBitmap(scaledBitmap, (float) m_x - (float) scaledWidth/ 2, (float) m_y - (float) scaledHeight / 2, null);

        // Restore the canvas state
        canvas.restore();
    }
}
