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

    private final Bitmap m_blueTankBitmap;
    private final Bitmap m_redTankBitmap;
    private final Bitmap m_greenTankBitmap;
    private final Bitmap m_yellowTankBitmap;

    private final Bitmap m_blueFireRocketBitmap;
    private final Bitmap m_blueRocketBitmap;

    private final Bitmap m_redFireRocketBitmap;
    private final Bitmap m_redRocketBitmap;

    private final Bitmap m_greenFireRocketBitmap;
    private final Bitmap m_greenRocketBitmap;

    private final Bitmap m_yellowFireRocketBitmap;
    private final Bitmap m_yellowRocketBitmap;

    public static final Tank kBlueTank = new Tank(BLUE, 100, 100, 0);
    public static final Tank kRedTank = new Tank(RED, 200, 200, 0);
    public static final Tank kGreenTank = new Tank(GREEN, 300, 300, 0);
    public static final Tank kYellowTank = new Tank(YELLOW, 400, 400, 0);

    public static final int TANK_AT_EDGE_ERROR = 25;
    private static final int ROCKET_AT_EDGE_ERROR = 25;

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_rockets = new ArrayList<>();

        m_tanksHandler = new Handler(Looper.getMainLooper());

        m_rocketsThread.start();
        m_rocketsHandler = new Handler(m_rocketsThread.getLooper());

        m_blueTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        m_redTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        m_greenTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_tank);
        m_yellowTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_tank);

        m_blueFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_firerocket);
        m_blueRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_rocket);

        m_redFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_firerocket);
        m_redRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_rocket);

        m_greenFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_firerocket);
        m_greenRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_rocket);

        m_yellowFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_firerocket);
        m_yellowRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_rocket);
    }

    public void setTanksAmount(int tanksAmount) {
        m_tanks = new Tank[tanksAmount];
        m_tanks[0] = kBlueTank;
        m_tanks[1] = kRedTank;

        switch (tanksAmount) {
            case 2:
                break; //TODO: remove this case
            case 4:
                m_tanks[3] = kYellowTank;
            case 3:
                m_tanks[2] = kGreenTank;
                break;
        }
    }

    public void startGame() {
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
            m_tanksHandler.postDelayed(m_tanksRunnable, 16);
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
            m_rocketsHandler.postDelayed(m_rocketsRunnable, 16);
        };

        m_rocketsHandler.post(m_rocketsRunnable);
    }

    public boolean getMobilityX(double x, int angle, int atEdgeError) {
        return !((x - atEdgeError <= 0 && (angle > 90 && angle < 270)) ||
                ((x + atEdgeError >= getWidth() && (angle < 90 || angle > 270))));

    }

    public boolean getMobilityY(double y, int angle, int atEdgeError) {
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
            switch (tank.getColor()) {
                case BLUE:
                    m_tanks[0].draw(canvas, m_blueTankBitmap);
                    break;
                case RED:
                    m_tanks[1].draw(canvas, m_redTankBitmap);
                    break;
                case GREEN:
                    m_tanks[2].draw(canvas, m_greenTankBitmap);
                    break;
                case YELLOW:
                    m_tanks[3].draw(canvas, m_yellowTankBitmap);
                    break;
            }
        }
        Rocket rocket;
        for (int i = 0; i < m_rockets.size(); i++) {
            rocket = m_rockets.get(i);
            switch (rocket.getColor()) {
                case BLUE:
                    if (rocket.checkTime()) {
                        rocket.draw(canvas, m_blueFireRocketBitmap);
                    } else {
                        rocket.draw(canvas, m_blueRocketBitmap);
                        rocket.timer();
                    }
                    break;
                case RED:
                    if (rocket.checkTime()) {
                        rocket.draw(canvas, m_redFireRocketBitmap);
                    } else {
                        rocket.draw(canvas, m_redRocketBitmap);
                        rocket.timer();
                    }
                    break;
                case GREEN:
                    if (rocket.checkTime()) {
                        rocket.draw(canvas, m_greenFireRocketBitmap);
                    } else {
                        rocket.draw(canvas, m_greenRocketBitmap);
                        rocket.timer();
                    }
                    break;
                case YELLOW:
                    if (rocket.checkTime()) {
                        rocket.draw(canvas, m_yellowFireRocketBitmap);
                    } else {
                        rocket.draw(canvas, m_yellowRocketBitmap);
                        rocket.timer();
                    }
                    break;
            }
        }
    }
}
