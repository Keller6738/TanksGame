package com.example.tanksgame.canvas;

import static com.example.tanksgame.util.Color.BLACK;
import static com.example.tanksgame.util.Rectangle.Type.DIED_TANK;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.tanksgame.util.Rectangle;

public class DiedTank extends CanvasComponent {
    /**
     * A constructor that takes initial setup parameters
     *
     * @param initX     The initial X pose of the component
     * @param initY     The initial Y pose of the component
     * @param initAngle The initial angle of the component
     */
    public DiedTank(double initX, double initY, int initAngle, Bitmap bitmap) {
        super(DIED_TANK, BLACK, initX, initY, initAngle, bitmap);
    }

    @Override
    void move(int screenWidth, int screenHeight) {
        move(screenWidth, screenHeight, null);
    }

    @Override
    void move(int screenWidth, int screenHeight, Rectangle crashingComponent) {}

    void draw(Canvas canvas) {
        // Save the canvas state
        canvas.save();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(m_bitmap, m_bitmap.getWidth() / 3, m_bitmap.getHeight() / 3, true);

        int scaledWidth = scaledBitmap.getWidth(), scaledHeight = scaledBitmap.getHeight();

        // Rotate the canvas around the center of the tank
        canvas.rotate(m_angle, (float) m_x, (float) m_y);

        canvas.drawBitmap(scaledBitmap, (float) m_x - (float) scaledWidth / 2, (float) m_y - (float) scaledHeight / 2, null);

        // Restore the canvas state
        canvas.restore();
    }
}
