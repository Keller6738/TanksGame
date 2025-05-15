package com.example.tanksgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.R;
import com.example.tanksgame.util.MusicManager;

import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends AppCompatActivity {
    private View m_startButton, m_exitButton, m_signOutButton, m_muteButton, m_leaderboardButton;
    public static boolean mute = false;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle extras;
        if (intent != null && intent.getExtras() != null) {
            extras = intent.getExtras();
            username = extras.getString("USERNAME");
        }

        // Keep the music playing
        MusicManager.startMusic(this);

        m_startButton = findViewById(R.id.btnStart);
        m_exitButton = findViewById(R.id.btnExit);
        m_signOutButton = findViewById(R.id.btnSignOut);
        m_muteButton = findViewById(R.id.btnMute);
        m_leaderboardButton = findViewById(R.id.btnLeaderboard);

        m_startButton.setOnClickListener(view -> {
            Intent changeActivityIntent = new Intent(this, GameConfigActivity.class);
            changeActivityIntent.putExtra("USERNAME", username);
            startActivity(changeActivityIntent);
            finish();
        });

        m_exitButton.setOnClickListener(view -> {
            MusicManager.stopMusic();
            finish();
        });

        m_signOutButton.setOnClickListener(view -> {
            Intent changeActivityIntent = new Intent(this, LoginActivity.class);
            startActivity(changeActivityIntent);
            finish();
        });

        m_muteButton.setOnClickListener(view -> {
            if (!mute) {
                MusicManager.stopMusic();
                m_muteButton.setBackgroundResource(R.drawable.unmute);
            } else {
                MusicManager.startMusic(this);
                m_muteButton.setBackgroundResource(R.drawable.mute);
            }
            mute = !mute;
        });

        m_leaderboardButton.setOnClickListener((view) -> {
            Intent changeActivityIntent = new Intent(this, LeaderboardActivity.class);
            changeActivityIntent.putExtra("USERNAME", username);
            finish();
            startActivity(changeActivityIntent);
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            private boolean firstTime = true;
            private Timer timer = new Timer();

            @Override
            public void handleOnBackPressed() {
                if (firstTime) {
                    // Custom action when the back button is pressed
                    Toast.makeText(MenuActivity.this, R.string.exit_message, Toast.LENGTH_LONG).show();
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
}