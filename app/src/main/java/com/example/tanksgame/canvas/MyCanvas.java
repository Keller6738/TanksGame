package com.example.tanksgame.canvas;

import static com.example.tanksgame.Color.BLUE;
import static com.example.tanksgame.Color.GREEN;
import static com.example.tanksgame.Color.RED;
import static com.example.tanksgame.Color.YELLOW;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.tanksgame.R;

import java.util.ArrayList;

public class MyCanvas extends View {
    private Tank[] m_tanks;
    private final ArrayList<Rocket> m_rockets;

    private Runnable m_tanksRunnable;
    private final Handler m_tanksHandler;

    private Runnable m_rocketsRunnable;
    private final HandlerThread m_rocketsThread = new HandlerThread("rockets thread");
    private final Handler m_rocketsHandler;

    private final Bitmap m_tankBitmap;
    private final Bitmap m_fireRocketBitmap;
    private final Bitmap m_rocketBitmap;

    private static final Tank kBlueTank = new Tank(BLUE, 100, 100, 0);
    private static final Tank kRedTank = new Tank(RED, 200, 200, 0);
    private static final Tank kGreenTank = new Tank(GREEN, 300, 300, 45);
    private static final Tank kYellowTank = new Tank(YELLOW, 400, 400, 45);

    private static final int TANK_AT_EDGE_ERROR = 25;
    private static final int ROCKET_AT_EDGE_ERROR = 25;

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_rockets = new ArrayList<>();

        m_tanksHandler = new Handler(Looper.getMainLooper());

        m_rocketsThread.start();
        m_rocketsHandler = new Handler(m_rocketsThread.getLooper());

        m_tankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        m_fireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_firerocket);
        m_rocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_rocket);
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

        m_tanksRunnable = () -> {
            for (Tank tank : m_tanks) {
                if (tank.isMoving()) {
                    tank.move(
                            getMobilityX(tank.getX(), tank.getAngle(), TANK_AT_EDGE_ERROR),
                            getMobilityY(tank.getY(), tank.getAngle(), TANK_AT_EDGE_ERROR)
                    );
                } else {
                    tank.turn();
                }
            }
            m_tanksHandler.postDelayed(m_tanksRunnable, 10);
        };

        m_tanksHandler.post(m_tanksRunnable);

        m_rocketsRunnable = () -> {
            boolean rocketMobilityX, rocketMobilityY;
            Rocket rocket;
            for (int i = 0; i < m_rockets.size(); i++) {
                rocket = m_rockets.get(i);
                rocketMobilityX = getMobilityX(rocket.getX(), rocket.getAngle(), ROCKET_AT_EDGE_ERROR);
                rocketMobilityY = getMobilityY(rocket.getY(), rocket.getAngle(), ROCKET_AT_EDGE_ERROR);
                rocket.move(rocketMobilityX, rocketMobilityY);
                if (!(rocketMobilityX && rocketMobilityY)) {
                    m_rockets.remove(i);
                }
            }
            invalidate();
            m_rocketsHandler.postDelayed(m_rocketsRunnable, 10);
        };

        m_rocketsHandler.post(m_rocketsRunnable);
    }

    private boolean getMobilityX(double x, int angle, int atEdgeError) {
        return !((x - atEdgeError <= 0 && (angle > 90 && angle < 270)) ||
                ((x + atEdgeError >= getWidth() && (angle < 90 || angle > 270))));

    }

    private boolean getMobilityY(double y, int angle, int atEdgeError) {
        return !((y - atEdgeError <= 0 && angle > 180) ||
                (y + atEdgeError >= getHeight() && angle < 180));
    }

    public void toggleTankMobility(int tank) {
        m_tanks[tank].toggleMobility();
    }

    public void launchRocket(Rocket rocket) {
        m_rockets.add(rocket);
    }

    public double getTankX(int tankNumber) {
        return m_tanks[tankNumber].getX();
    }

    public double getTankY(int tankNumber) {
        return m_tanks[tankNumber].getY();
    }

    public int getTankAngle(int tankNumber) {
        return m_tanks[tankNumber].getAngle();
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        for (Tank tank : m_tanks) {
            tank.draw(canvas, m_tankBitmap);
        }
        for (Rocket rocket : m_rockets) {
            if (rocket.checkTime()) {
                rocket.draw(canvas, m_fireRocketBitmap);
                Log.d("rocket", "true");
            } else {
                rocket.draw(canvas, m_rocketBitmap);
                rocket.timer();
                Log.d("rocket", "true");
            }
        }
    }
}
