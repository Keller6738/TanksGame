package com.example.tanksgame.canvas;

import static android.graphics.Color.rgb;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.tanksgame.Color;

public class Rocket extends CanvasComponent {
    private final Paint m_topPaintBrush;

    public static final float ROCKET_LENGTH = 30; // Adjust as needed
    private static final float ROCKET_WIDTH = 20; // Adjust as needed

    public Rocket(Color color, double initX, double initY, int initAngle) {
        super(false, color, initX, initY, initAngle);

        m_topPaintBrush = new Paint();
        m_topPaintBrush.setColor(rgb(20, 30, 70));
        m_topPaintBrush.setAntiAlias(true);
    }

//    public boolean atEdge() {
//        return x > getWidth() || y > getHeight();
//    }

    @Override
    void draw(Canvas canvas) {
        // Save the canvas state
        canvas.save();

        canvas.rotate(m_angle, (float) m_x, (float) m_y);

        canvas.drawRect((float) m_x - ROCKET_LENGTH / 2, (float) m_y + ROCKET_WIDTH / 2,
                (float) m_x + ROCKET_LENGTH / 2, (float) m_y - ROCKET_WIDTH / 2, m_basicBrash);
        canvas.drawCircle((float) m_x + ROCKET_LENGTH / 2, (float) m_y, 10, m_topPaintBrush);

        // Restore the canvas state
        canvas.restore();
    }
}
