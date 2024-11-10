package com.example.tanksgame.canvas;

import static android.graphics.Color.rgb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.tanksgame.Color;

public class Rocket extends CanvasComponent {
    private int m_timer;

    private final Paint m_topPaintBrush;

    public static final float ROCKET_LENGTH = 30; // Adjust as needed
    private static final float ROCKET_WIDTH = 20; // Adjust as needed

    public Rocket(Color color, double initX, double initY, int initAngle) {
        super(false, color, initX, initY, initAngle);

        m_topPaintBrush = new Paint();
        m_topPaintBrush.setColor(rgb(20, 30, 70));
        m_topPaintBrush.setAntiAlias(true);

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
