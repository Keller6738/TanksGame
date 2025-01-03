package com.example.tanksgame.canvas;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static android.view.View.INVISIBLE;
import static com.example.tanksgame.canvas.MyCanvas.kBlueTank;
import static com.example.tanksgame.canvas.MyCanvas.kGreenTank;
import static com.example.tanksgame.canvas.MyCanvas.kRedTank;
import static com.example.tanksgame.canvas.MyCanvas.kYellowTank;
import static com.example.tanksgame.util.Color.BLUE;
import static com.example.tanksgame.util.Color.GREEN;
import static com.example.tanksgame.util.Color.RED;
import static com.example.tanksgame.util.Color.YELLOW;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.MenuActivity;
import com.example.tanksgame.R;

public class GameActivity extends AppCompatActivity {
    private MyCanvas m_canvas;

    private int tanksAmount;

    private View m_blueButton, m_redButton, m_greenButton, m_yellowButton;
    private View homeButton;

    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

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

        getWindow().getDecorView().setBackgroundResource(Math.random() <= 0.5 ? R.drawable.metal_background : R.drawable.sand_background);

        tanksAmount = 4;

        m_canvas = findViewById(R.id.canvas);
        m_canvas.setTanksAmount(tanksAmount);
        m_canvas.startGame();

        m_blueButton = findViewById(R.id.blueButton);
        m_blueButton.setOnTouchListener((view, event) -> {
            if (kBlueTank.isAlive()) {
                switch (event.getAction()) {
                    case ACTION_DOWN:
                        m_canvas.launchRocket(BLUE);
                        kBlueTank.toggleMobility();
                        view.performClick();
                        return true;
                    case ACTION_UP:
                    case ACTION_CANCEL:
                        kBlueTank.toggleMobility();
                        return true; // Consume event
                }
            }
            return false;
        });

        m_redButton = findViewById(R.id.redButton);
        m_redButton.setOnTouchListener((view, event) -> {
            if (kRedTank.isAlive()) {
                switch (event.getAction()) {
                    case ACTION_DOWN:
                        m_canvas.launchRocket(RED);
                        kRedTank.toggleMobility();
                        view.performClick();
                        return true;
                    case ACTION_UP:
                    case ACTION_CANCEL:
                        kRedTank.toggleMobility();
                        return true; // Consume event
                }
            }
            return false;
        });

        m_greenButton = findViewById(R.id.greenButton);
        m_yellowButton = findViewById(R.id.yellowButton);

        switch (tanksAmount) {
            case 2:
                m_greenButton.setVisibility(INVISIBLE);
                m_yellowButton.setVisibility(INVISIBLE);
                break;
            case 4:
                m_yellowButton.setOnTouchListener((view, event) -> {
                    if (kYellowTank.isAlive()) {
                        switch (event.getAction()) {
                            case ACTION_DOWN:
                                m_canvas.launchRocket(YELLOW);
                                kYellowTank.toggleMobility();
                                view.performClick();
                                return true;
                            case ACTION_UP:
                            case ACTION_CANCEL:
                                kYellowTank.toggleMobility();
                                return true; // Consume event
                        }
                    }
                    return false;
                });
            case 3:
                m_greenButton.setOnTouchListener((view, event) -> {
                    if (kGreenTank.isAlive()) {
                        switch (event.getAction()) {
                            case ACTION_DOWN:
                                m_canvas.launchRocket(GREEN);
                                kGreenTank.toggleMobility();
                                view.performClick();
                                return true;
                            case ACTION_UP:
                            case ACTION_CANCEL:
                                kGreenTank.toggleMobility();
                                return true; // Consume event
                        }
                    }
                    return false;
                });
                break;
        }

        if (tanksAmount == 3) {
            m_yellowButton.setVisibility(INVISIBLE);
        }

        homeButton = findViewById(R.id.btnHome);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuActivity.class);
            finish();
            startActivity(intent);
        });
    }
}