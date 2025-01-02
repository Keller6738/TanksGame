package com.example.tanksgame.canvas;

import static com.example.tanksgame.util.Color.BLUE;
import static com.example.tanksgame.util.Color.GREEN;
import static com.example.tanksgame.util.Color.RED;
import static com.example.tanksgame.util.Color.YELLOW;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.tanksgame.R;

import java.util.ArrayList;

public class MyCanvas extends View {
    private ArrayList<Tank> m_tanks;
    private ArrayList<Tank> m_tanksToRemove;
    private ArrayList<DiedTank> m_diedTanks;

    private final ArrayList<Rocket> m_rockets;
    private final ArrayList<Rocket> m_rocketsToRemove;

    private Runnable m_runnable;
    private final Handler m_handler;

    private final Bitmap m_blueTankBitmap;
    private final Bitmap m_redTankBitmap;
    private final Bitmap m_greenTankBitmap;
    private final Bitmap m_yellowTankBitmap;
    private final Bitmap m_diedTankBitmap;

    private final Bitmap m_blueFireRocketBitmap;
    private final Bitmap m_blueRocketBitmap;

    private final Bitmap m_redFireRocketBitmap;
    private final Bitmap m_redRocketBitmap;

    private final Bitmap m_greenFireRocketBitmap;
    private final Bitmap m_greenRocketBitmap;

    private final Bitmap m_yellowFireRocketBitmap;
    private final Bitmap m_yellowRocketBitmap;

    public static Tank kBlueTank;
    public static Tank kRedTank;
    public static Tank kGreenTank;
    public static Tank kYellowTank;

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_rockets = new ArrayList<>();
        m_rocketsToRemove = new ArrayList<>();

        m_handler = new Handler(Looper.getMainLooper());

        m_blueTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        m_redTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        m_greenTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_tank);
        m_yellowTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_tank);
        m_diedTankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.died_tank);

        m_blueFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_firerocket);
        m_blueRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_rocket);

        m_redFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_firerocket);
        m_redRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_rocket);

        m_greenFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_firerocket);
        m_greenRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_rocket);

        m_yellowFireRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_firerocket);
        m_yellowRocketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_rocket);

        kBlueTank = new Tank(BLUE, 400, 800, 0);
        kRedTank = new Tank(RED, 800, 200, 0);
        kGreenTank = new Tank(GREEN, 400, 400, 0);
        kYellowTank = new Tank(YELLOW, 500, 200, 0);
    }

    public void setTanksAmount(int tanksAmount) {
        m_tanks = new ArrayList<>();
        m_tanks.add(kBlueTank);
        m_tanks.add(kRedTank);

        m_diedTanks = new ArrayList<>();
        m_tanksToRemove = new ArrayList<>();

        switch (tanksAmount) {
            case 4:
                m_tanks.add(kYellowTank);
            case 3:
                m_tanks.add(kGreenTank);
                break;
        }
    }

    public void startGame() {
        m_runnable = () -> {
            for (Rocket rocket : m_rockets) {
                rocket.move(getWidth(), getHeight());
                if (!(rocket.getMobilityX(getWidth()) && rocket.getMobilityY(getHeight()))) {
                    m_rocketsToRemove.add(rocket);
                }
            }

            for (DiedTank tank : m_diedTanks) {
                for (Rocket rocket : m_rockets) {
                    if (rocket.checkCollision(tank)) {
                        m_rocketsToRemove.add(rocket);
                    }
                }
            }

            for (Tank tank : m_tanks) {
                boolean crashing = false;
                /*if (!m_diedTanks.isEmpty()) {
                    for (DiedTank diedTank : m_diedTanks) {
                        crashing = tank.checkCollision(diedTank);
                        if (crashing) {
                            tank.move(getWidth(), getHeight(), diedTank);
                            break;
                        }
                    }
                }*/

                if (tank.isMoving() && !crashing) {
                    tank.move(getWidth(), getHeight());
                } else {
                    tank.turn();
                }

                for (Rocket rocket : m_rockets) {
                    if (rocket.checkCollision(tank) && tank.getColor() != rocket.getColor()) {
                        m_rocketsToRemove.add(rocket);
                        m_tanksToRemove.add(tank);
                    }
                }
            }

            for (Tank tank : m_tanksToRemove) {
                tank.kill();
                m_diedTanks.add(new DiedTank(tank.getX(), tank.getY(), tank.getAngle()));
                m_tanks.remove(tank);
            }
            m_tanksToRemove = new ArrayList<>();

            for (Rocket rocket : m_rocketsToRemove) {
                m_rockets.remove(rocket);
            }
            m_tanksToRemove = new ArrayList<>();

            invalidate();

            m_handler.postDelayed(m_runnable, 16);
        };

        m_handler.post(m_runnable);
    }

    void launchRocket(Rocket rocket) {
        m_rockets.add(rocket);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        for (Tank tank : m_tanks) {
            switch (tank.getColor()) {
                case BLUE:
                    kBlueTank.draw(canvas, m_blueTankBitmap);
                    break;
                case RED:
                    kRedTank.draw(canvas, m_redTankBitmap);
                    break;
                case GREEN:
                    kGreenTank.draw(canvas, m_greenTankBitmap);
                    break;
                case YELLOW:
                    kYellowTank.draw(canvas, m_yellowTankBitmap);
                    break;
            }
        }

        for (DiedTank tank : m_diedTanks) {
            tank.draw(canvas, m_diedTankBitmap);
        }

        for (Rocket rocket : m_rockets) {
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
