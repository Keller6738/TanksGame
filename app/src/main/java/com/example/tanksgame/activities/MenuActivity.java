package com.example.tanksgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.R;
import com.example.tanksgame.util.MusicManager;

public class MenuActivity extends AppCompatActivity {
    View m_startButton, m_exitButton, m_signOutButton;

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

        // Keep the music playing
        MusicManager.startMusic(this);

        m_startButton = findViewById(R.id.btnStart);
        m_exitButton = findViewById(R.id.btnExit);
        m_signOutButton = findViewById(R.id.btnSignOut);

        m_startButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameConfigActivity.class);
            startActivity(intent);
            finish();
        });

        m_exitButton.setOnClickListener(view -> {
            MusicManager.stopMusic();
            finish();
        });

        m_signOutButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}