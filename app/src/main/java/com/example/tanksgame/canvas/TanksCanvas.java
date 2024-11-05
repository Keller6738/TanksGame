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
import android.view.View;

public class TanksCanvas extends View {
    private Tank[] m_tanks;

    private Runnable m_runnable;
    private final Handler m_handler;

    private static final Tank kBlueTank = new Tank(BLUE, 100, 100, 45);
    private static final Tank kRedTank = new Tank(RED, 200, 200, 45);
    private static final Tank kGreenTank = new Tank(GREEN, 100, 100, 45);
    private static final Tank kYellowTank = new Tank(YELLOW, 100, 100, 45);

    public TanksCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_handler = new Handler(Looper.getMainLooper());
    }

    public void setTanksAmount(int tanksAmount) {
        m_tanks = new Tank[tanksAmount];
        m_tanks[0] = kBlueTank;
        m_tanks[1] = kRedTank;
        switch (tanksAmount) {
            case 4:
                m_tanks[3] = kBlueTank;
            case 3:
                m_tanks[2] = kBlueTank;
                break;
        }

        m_runnable = () -> {
            for (Tank tank : m_tanks) {
                if (tank.isMoving()) {
                    tank.move();
                } else {
                    tank.turn();
                }
            }
            invalidate();
            m_handler.postDelayed(m_runnable, 10);
        };

        m_handler.post(m_runnable);
    }

    public void toggleTanksMobility() {
        for (Tank tank : m_tanks) {
            tank.toggleMobility();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (Tank tank : m_tanks) {
            tank.draw(canvas);
        }
    }
}
