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
import com.example.tanksgame.util.Color;

import java.util.ArrayList;

public class MyCanvas extends View {
    private ArrayList<Tank> m_tanks;
    private ArrayList<Tank> m_tanksToRemove;
    private ArrayList<DiedTank> m_diedTanks;

    private int m_tanksAmount;

    private final ArrayList<Rocket> m_rockets;
    private ArrayList<Rocket> m_rocketsToRemove;

    private Runnable m_gameRunnable, m_winRunnable;
    private final Handler m_handler;

    public static Bitmap BLUE_TANK_BITMAP;
    public static Bitmap RED_TANK_BITMAP;
    public static Bitmap GREEN_TANK_BITMAP;
    public static Bitmap YELLOW_TANK_BITMAP;
    public static Bitmap DIED_TANK_BITMAP;

    public static Bitmap BLUE_FIRE_ROCKET_BITMAP;
    public static Bitmap BLUE_ROCKET_BITMAP;

    public static Bitmap RED_FIRE_ROCKET_BITMAP;
    public static Bitmap RED_ROCKET_BITMAP;

    public static Bitmap GREEN_FIRE_ROCKET_BITMAP;
    public static Bitmap GREEN_ROCKET_BITMAP;

    public static Bitmap YELLOW_FIRE_ROCKET_BITMAP;
    public static Bitmap YELLOW_ROCKET_BITMAP;

    public static Tank kBlueTank;
    public static Tank kRedTank;
    public static Tank kGreenTank;
    public static Tank kYellowTank;

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_rockets = new ArrayList<>();
        m_rocketsToRemove = new ArrayList<>();

        m_handler = new Handler(Looper.getMainLooper());

        BLUE_TANK_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        RED_TANK_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        GREEN_TANK_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.green_tank);
        YELLOW_TANK_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_tank);
        DIED_TANK_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.died_tank);

        BLUE_FIRE_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.blue_firerocket);
        BLUE_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.blue_rocket);

        RED_FIRE_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.red_firerocket);
        RED_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.red_rocket);

        GREEN_FIRE_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.green_firerocket);
        GREEN_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.green_rocket);

        YELLOW_FIRE_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_firerocket);
        YELLOW_ROCKET_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_rocket);
    }

    public void setTanksAmount(int tanksAmount) {
        kBlueTank = new Tank(BLUE, 830, 250, 0, BLUE_TANK_BITMAP);
        kRedTank = new Tank(RED, 250, 1950, 0, RED_TANK_BITMAP);
        kGreenTank = new Tank(GREEN, 830, 1950, 0, GREEN_TANK_BITMAP);
        kYellowTank = new Tank(YELLOW, 250, 250, 0, YELLOW_TANK_BITMAP);

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

        m_tanksAmount = tanksAmount;
    }

    void game() {
        m_gameRunnable = () -> {
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
                m_diedTanks.add(new DiedTank(tank.getX(), tank.getY(), tank.getAngle(), DIED_TANK_BITMAP));
                m_tanks.remove(tank);
            }
            m_tanksToRemove = new ArrayList<>();

            for (Rocket rocket : m_rocketsToRemove) {
                m_rockets.remove(rocket);
            }
            m_rocketsToRemove = new ArrayList<>();

            invalidate();

            if (!checkWin()) {
                m_handler.postDelayed(m_gameRunnable, 16);
            } else {
                m_handler.removeCallbacks(m_gameRunnable);
                win();
            }
        };

        m_handler.post(m_gameRunnable);
    }

    void launchRocket(Color tankColor) {
        Tank tank = null;
        for (Tank tank1 : m_tanks) {
            if (tank1.getColor() == tankColor) {
                tank = tank1;
            }
        }

        if (tank != null) {
            m_rockets.add(tank.getRocket());
        }
    }

    boolean checkWin() {
        return m_tanks.size() == 1;
    }

    void win() {
        m_winRunnable = () -> {
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

            for (Rocket rocket : m_rocketsToRemove) {
                m_rockets.remove(rocket);
            }
            m_rocketsToRemove = new ArrayList<>();

            m_tanks.get(0).turn();

            invalidate();

            m_handler.postDelayed(m_winRunnable, 16);
        };

        m_handler.post(m_winRunnable);
    }

    public int getTanksAmount() {
        return m_tanksAmount;
    }

    public Color getWinnerColor() {
        return checkWin() ? m_tanks.get(0).getColor() : null;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        for (Tank tank : m_tanks) {
            switch (tank.getColor()) {
                case BLUE:
                    kBlueTank.draw(canvas);
                    break;
                case RED:
                    kRedTank.draw(canvas);
                    break;
                case GREEN:
                    kGreenTank.draw(canvas);
                    break;
                case YELLOW:
                    kYellowTank.draw(canvas);
                    break;
            }
        }

        for (DiedTank tank : m_diedTanks) {
            tank.draw(canvas);
        }

        for (Rocket rocket : m_rockets) {
            switch (rocket.getColor()) {
                case BLUE:
                    if (rocket.checkTime()) {
                        rocket.changeBitmap(BLUE_FIRE_ROCKET_BITMAP);
                    } else {
                        rocket.timer();
                    }
                    break;
                case RED:
                    if (rocket.checkTime()) {
                        rocket.changeBitmap(RED_FIRE_ROCKET_BITMAP);
                    } else {
                        rocket.timer();
                    }
                    break;
                case GREEN:
                    if (rocket.checkTime()) {
                        rocket.changeBitmap(GREEN_FIRE_ROCKET_BITMAP);
                    } else {
                        rocket.timer();
                    }
                    break;
                case YELLOW:
                    if (rocket.checkTime()) {
                        rocket.changeBitmap(YELLOW_FIRE_ROCKET_BITMAP);
                    } else {
                        rocket.timer();
                    }
                    break;
            }
            rocket.draw(canvas);
        }
    }
}
