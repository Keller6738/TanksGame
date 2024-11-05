package com.example.tanksgame.canvas;

import static com.example.tanksgame.Color.BLUE;
import static com.example.tanksgame.Color.GREEN;
import static com.example.tanksgame.Color.RED;
import static com.example.tanksgame.Color.YELLOW;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TanksCanvas extends View {
    private Tank[] m_tanks;

    private Runnable m_runnable;
    private final Handler m_handler;

    private static final Tank kBlueTank = new Tank(BLUE, 100, 100, 45);
    private static final Tank kRedTank = new Tank(RED, 200, 200, 45);
    private static final Tank kGreenTank = new Tank(GREEN, 300, 300, 45);
    private static final Tank kYellowTank = new Tank(YELLOW, 400, 400, 45);

    public TanksCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_handler = new Handler(Looper.getMainLooper());
    }

    public void setTanksAmount(int tanksAmount) {
        m_tanks = new Tank[tanksAmount];
        m_tanks[0] = kBlueTank;
        switch (tanksAmount) {
            case 2:
                m_tanks[1] = kRedTank;
                break; //TODO: remove this case
            case 4:
                m_tanks[3] = kYellowTank;
            case 3:
                m_tanks[2] = kGreenTank;
                break;
        }

        m_runnable = () -> {
            for (Tank tank : m_tanks) {
                if (tank.isMoving()) {
                    boolean movableY = !((tank.getY() <= 0 && tank.getAngle() > 180));
                    Log.d("movable y", "can move in y?" + movableY);
                    tank.move(
                            !((tank.getX() - 30 <= 0 && (tank.getAngle() > 90 && tank.getAngle() < 270)) ||
                                    ((tank.getX() + 30 >= getWidth() && (tank.getAngle() < 90 || tank.getAngle() > 270)))),
                            !((tank.getY() - 30 <= 0 && tank.getAngle() > 180) ||
                                    (tank.getY() + 30 >= getHeight() && tank.getAngle() < 180))
                    );
                } else {
                    tank.turn();
                }
            }
            invalidate();
            m_handler.postDelayed(m_runnable, 10);
        };

        m_handler.post(m_runnable);
    }

    public void toggleTankMobility(int tank) {
        m_tanks[tank].toggleMobility();
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Tank tank : m_tanks) {
            tank.draw(canvas);
        }
    }
}
