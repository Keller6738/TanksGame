package com.example.tanksgame.canvas;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static android.view.View.INVISIBLE;

import static com.example.tanksgame.Color.BLUE;
import static com.example.tanksgame.Color.GREEN;
import static com.example.tanksgame.Color.RED;
import static com.example.tanksgame.Color.YELLOW;
import static com.example.tanksgame.canvas.MyCanvas.TANK_AT_EDGE_ERROR;
import static com.example.tanksgame.canvas.MyCanvas.kBlueTank;
import static com.example.tanksgame.canvas.MyCanvas.kGreenTank;
import static com.example.tanksgame.canvas.MyCanvas.kRedTank;
import static com.example.tanksgame.canvas.MyCanvas.kYellowTank;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.R;

public class GameActivity extends AppCompatActivity {
    private MyCanvas m_canvas;

    private int tanksAmount;

    private View m_blueButton;
    private View m_redButton;
    private View m_greenButton;
    private View m_yellowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tanksAmount = 2;

        m_canvas = findViewById(R.id.canvas);
        m_canvas.setTanksAmount(tanksAmount);
        m_canvas.startGame();

        m_blueButton = findViewById(R.id.blueButton);
        m_blueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case ACTION_DOWN:
                        m_canvas.launchRocket(new Rocket(BLUE, kBlueTank.getX(), kBlueTank.getY(), kBlueTank.getAngle()));
                        kBlueTank.toggleMobility();
                        kBlueTank.move(
                                m_canvas.getMobilityX(kBlueTank.getX(), kBlueTank.getAngle(), TANK_AT_EDGE_ERROR),
                                m_canvas.getMobilityY(kBlueTank.getY(), kBlueTank.getAngle(), TANK_AT_EDGE_ERROR)
                        );
                        return true;
                    case ACTION_UP:
                    case ACTION_CANCEL:
                        kBlueTank.toggleMobility();
                        return true; // Consume event
                }
                return false;
            }
        });

        m_redButton = findViewById(R.id.redButton);
        m_redButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case ACTION_DOWN:
                        m_canvas.launchRocket(new Rocket(RED, kRedTank.getX(), kRedTank.getY(), kRedTank.getAngle()));
                        kRedTank.toggleMobility();
                        kRedTank.move(
                                m_canvas.getMobilityX(kRedTank.getX(), kRedTank.getAngle(), TANK_AT_EDGE_ERROR),
                                m_canvas.getMobilityY(kRedTank.getY(), kRedTank.getAngle(), TANK_AT_EDGE_ERROR)
                        );
                        return true;
                    case ACTION_UP:
                    case ACTION_CANCEL:
                        kRedTank.toggleMobility();
                        return true; // Consume event
                }
                return false;
            }
        });

        m_greenButton = findViewById(R.id.greenButton);
        m_yellowButton = findViewById(R.id.yellowButton);

        switch (tanksAmount) {
            case 2:
                m_greenButton.setVisibility(INVISIBLE);
                m_yellowButton.setVisibility(INVISIBLE);
                break;
            case 4:
                m_yellowButton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case ACTION_DOWN:
                                m_canvas.launchRocket(new Rocket(YELLOW, kYellowTank.getX(), kYellowTank.getY(), kYellowTank.getAngle()));
                                kYellowTank.toggleMobility();
                                kYellowTank.move(
                                        m_canvas.getMobilityX(kYellowTank.getX(), kYellowTank.getAngle(), TANK_AT_EDGE_ERROR),
                                        m_canvas.getMobilityY(kYellowTank.getY(), kYellowTank.getAngle(), TANK_AT_EDGE_ERROR)
                                );
                                return true;
                            case ACTION_UP:
                            case ACTION_CANCEL:
                                kYellowTank.toggleMobility();
                                return true; // Consume event
                        }
                        return false;
                    }
                });
            case 3:
                m_greenButton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case ACTION_DOWN:
                                m_canvas.launchRocket(new Rocket(GREEN, kGreenTank.getX(), kGreenTank.getY(), kGreenTank.getAngle()));
                                kGreenTank.toggleMobility();
                                kGreenTank.move(
                                        m_canvas.getMobilityX(kGreenTank.getX(), kGreenTank.getAngle(), TANK_AT_EDGE_ERROR),
                                        m_canvas.getMobilityY(kGreenTank.getY(), kGreenTank.getAngle(), TANK_AT_EDGE_ERROR)
                                );
                                return true;
                            case ACTION_UP:
                            case ACTION_CANCEL:
                                kGreenTank.toggleMobility();
                                return true; // Consume event
                        }
                        return false;
                    }
                });
                break;
        }

        if (tanksAmount == 3) {
            m_yellowButton.setVisibility(INVISIBLE);
        }
    }
}