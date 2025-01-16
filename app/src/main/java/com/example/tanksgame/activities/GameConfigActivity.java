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
import com.example.tanksgame.canvas.GameActivity;

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

        m_2pButton = findViewById(R.id.twoPBtn);
        m_3pButton = findViewById(R.id.threePBtn);
        m_4pButton = findViewById(R.id.fourPBtn);

        m_homeButton = findViewById(R.id.btnHome);
        m_homeButton.setOnClickListener(view -> {
            Intent changeActivityIntent = new Intent(this, MenuActivity.class);
            finish();
            startActivity(changeActivityIntent);
        });

        Intent intent =new Intent(this, GameActivity.class);

        m_2pButton.setOnClickListener(view -> {
            intent.putExtra("players", 2);
            finish();
            startActivity(intent);
        });

        m_3pButton.setOnClickListener(view -> {
            intent.putExtra("players", 3);
            finish();
            startActivity(intent);
        });

        m_4pButton.setOnClickListener(view -> {
            intent.putExtra("players", 4);
            finish();
            startActivity(intent);
        });
    }
}