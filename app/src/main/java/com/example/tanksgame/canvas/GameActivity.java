package com.example.tanksgame.canvas;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.tanksgame.canvas.MyCanvas.kBlueTank;
import static com.example.tanksgame.canvas.MyCanvas.kGreenTank;
import static com.example.tanksgame.canvas.MyCanvas.kRedTank;
import static com.example.tanksgame.canvas.MyCanvas.kYellowTank;
import static com.example.tanksgame.util.Color.BLUE;
import static com.example.tanksgame.util.Color.GREEN;
import static com.example.tanksgame.util.Color.RED;
import static com.example.tanksgame.util.Color.YELLOW;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.R;
import com.example.tanksgame.activities.LoginActivity;
import com.example.tanksgame.activities.MenuActivity;
import com.example.tanksgame.util.MusicManager;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private MyCanvas m_canvas;

    private int m_tanksAmount, m_timer = 0;

    private View m_blueButton, m_redButton, m_greenButton, m_yellowButton;
    private View m_redStart, m_blueStart, m_greenStart, m_yellowStart;
    private View m_blueWinner, m_redWinner, m_greenWinner, m_yellowWinner;
    private View m_homeButton, m_restartButton;

    private Runnable m_runnable;

    private Bundle extras;
    private String username;

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

        if (!MenuActivity.mute) {
            // Keep the music playing
            MusicManager.startMusic(this);
        }

        getWindow().getDecorView().setBackgroundResource(Math.random() <= 0.5 ? R.drawable.metal_background : R.drawable.sand_background);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            extras = intent.getExtras();
            m_tanksAmount = extras.getInt("players");
            username = extras.getString("USERNAME");
        }

        m_redStart = findViewById(R.id.redStart);
        m_blueStart = findViewById(R.id.blueStart);
        m_greenStart = findViewById(R.id.greenStart);
        m_yellowStart = findViewById(R.id.yellowStart);

        m_blueWinner = findViewById(R.id.blueWinner);
        m_redWinner = findViewById(R.id.redWinner);
        m_greenWinner = findViewById(R.id.greenWinner);
        m_yellowWinner = findViewById(R.id.yellowWinner);

        m_homeButton = findViewById(R.id.btnHome);
        m_homeButton.setOnClickListener(view -> {
            Intent changeActivityIntent = new Intent(this, MenuActivity.class);
            changeActivityIntent.putExtra("USERNAME", username);
            finish();
            startActivity(changeActivityIntent);
        });

        m_restartButton = findViewById(R.id.btnRestart);


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(this::startGame);

        m_runnable = () -> {
            if (m_canvas.checkWin()) {
                if (m_timer == 0) {
                    LoginActivity.dbHelper.updateRecord(
                            username,
                            Math.max(
                                    LoginActivity.dbHelper.getUsernameRecord(username),
                                    ((int) (m_canvas.getGameTime() * Math.pow(10, -3)))
                            )
                    );
                }

                m_blueButton.setOnTouchListener(GameActivity::onTouch);
                m_redButton.setOnTouchListener(GameActivity::onTouch);
                m_greenButton.setOnTouchListener(GameActivity::onTouch);
                m_yellowButton.setOnTouchListener(GameActivity::onTouch);

                switch (m_canvas.getWinnerColor()) {
                    case RED:
                        m_redWinner.setVisibility(VISIBLE);
                        break;
                    case BLUE:
                        m_blueWinner.setVisibility(VISIBLE);
                        break;
                    case GREEN:
                        m_greenWinner.setVisibility(VISIBLE);
                        break;
                    case YELLOW:
                        m_yellowWinner.setVisibility(VISIBLE);
                        break;
                }

                m_timer += 1;

                if (m_timer == 200) {
                    m_restartButton.setVisibility(VISIBLE);
                    m_restartButton.setOnClickListener(view -> {
                        finish();
                        startActivity(getIntent());
                    });
                    handler.removeCallbacks(m_runnable);
                }
            }

            handler.postDelayed(m_runnable, 16);
        };

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            private boolean firstTime = true;
            private Timer timer = new Timer();

            @Override
            public void handleOnBackPressed() {
                if (firstTime) {
                    // Custom action when the back button is pressed
                    Toast.makeText(GameActivity.this, R.string.exit_message, Toast.LENGTH_LONG).show();
                    firstTime = false;

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            firstTime = true;
                        }
                    }, 5000);
                } else {
                    // Optionally, you can finish the activity if needed
                    finish();
                }
            }
        });
    }

    private static boolean onTouch(View view, MotionEvent event) {
        return false;
    }

    private void startGame() {
        m_canvas = findViewById(R.id.canvas);
        m_canvas.setTanksAmount(m_tanksAmount);

        if (m_tanksAmount == 3) {
            m_greenStart.setVisibility(VISIBLE);
        }
        if (m_tanksAmount == 4) {
            m_yellowStart.setVisibility(VISIBLE);
        }

        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(() -> {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            m_redStart.setVisibility(INVISIBLE);
            m_blueStart.setVisibility(INVISIBLE);
            m_yellowStart.setVisibility(INVISIBLE);
            m_greenStart.setVisibility(INVISIBLE);
        });

        m_canvas.game();

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

        switch (m_tanksAmount) {
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

        if (m_tanksAmount == 3) {
            m_yellowButton.setVisibility(INVISIBLE);
        }

        m_restartButton.setVisibility(INVISIBLE);

        handler.post(m_runnable);
    }
}