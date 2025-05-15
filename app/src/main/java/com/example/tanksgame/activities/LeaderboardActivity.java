package com.example.tanksgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.R;
import com.example.tanksgame.util.CustomAdapter;
import com.example.tanksgame.util.MusicManager;

public class LeaderboardActivity extends AppCompatActivity {
    // list view of scores
    private ListView lv;
    //go back to mainMenu
    private ImageView btnHome;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (!MenuActivity.mute) {
            // Keep the music playing
            MusicManager.startMusic(this);
        }

        lv = findViewById(R.id.lvStati);

        Intent intent = getIntent();
        Bundle extras;
        if (intent != null && intent.getExtras() != null) {
            extras = intent.getExtras();
            username = extras.getString("USERNAME");
        }

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener((view) -> {
            Intent changeActivityIntent = new Intent(this, MenuActivity.class);
            changeActivityIntent.putExtra("USERNAME", username);
            finish();
            startActivity(changeActivityIntent);
        });

        CustomAdapter adapter = new CustomAdapter(this, getResources());
        lv.setAdapter(adapter);
    }
}