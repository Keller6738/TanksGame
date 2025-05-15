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
import com.example.tanksgame.canvas.GameActivity;
import com.example.tanksgame.util.MusicManager;

import java.util.Timer;
import java.util.TimerTask;

public class GameConfigActivity extends AppCompatActivity {
    private View m_2pButton, m_3pButton, m_4pButton;
    private View m_homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (!MenuActivity.mute) {
            // Keep the music playing
            MusicManager.startMusic(this);
        }

        m_2pButton = findViewById(R.id.twoPBtn);
        m_3pButton = findViewById(R.id.threePBtn);
        m_4pButton = findViewById(R.id.fourPBtn);

        m_homeButton = findViewById(R.id.btnHome);
        m_homeButton.setOnClickListener(view -> {
            Intent changeActivityIntent = new Intent(this, MenuActivity.class);
            changeActivityIntent.putExtra("USERNAME", getIntent().getExtras().getString("USERNAME", ""));
            finish();
            startActivity(changeActivityIntent);
        });

        Intent intent = new Intent(this, GameActivity.class);

        m_2pButton.setOnClickListener(view -> {
            intent.putExtra("players", 2);
            intent.putExtra("USERNAME", getIntent().getExtras().getString("USERNAME", ""));
            finish();
            startActivity(intent);
        });

        m_3pButton.setOnClickListener(view -> {
            intent.putExtra("players", 3);
            intent.putExtra("USERNAME", getIntent().getExtras().getString("USERNAME", ""));
            finish();
            startActivity(intent);
        });

        m_4pButton.setOnClickListener(view -> {
            intent.putExtra("players", 4);
            intent.putExtra("USERNAME", getIntent().getExtras().getString("USERNAME", ""));
            finish();
            startActivity(intent);
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            private boolean firstTime = true;
            private Timer timer = new Timer();

            @Override
            public void handleOnBackPressed() {
                if (firstTime) {
                    // Custom action when the back button is pressed
                    Toast.makeText(GameConfigActivity.this, R.string.exit_message, Toast.LENGTH_LONG).show();
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