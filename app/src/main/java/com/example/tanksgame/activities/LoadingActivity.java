package com.example.tanksgame.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.R;
import com.example.tanksgame.util.MusicManager;

public class LoadingActivity extends AppCompatActivity {
    private final int TIME = 600;
    private ImageView m_image1, m_image2, m_image3, m_image4;
    private TextView m_loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Start background music when the app launches
        MusicManager.startMusic(this);

        m_image1 = findViewById(R.id.img1);
        m_image2 = findViewById(R.id.img2);
        m_image3 = findViewById(R.id.img3);
        m_image4 = findViewById(R.id.img4);

        m_loadingView = findViewById(R.id.title);

        m_image1.setVisibility(INVISIBLE);
        m_image2.setVisibility(INVISIBLE);
        m_image3.setVisibility(INVISIBLE);
        m_image4.setVisibility(INVISIBLE);

        new Handler(Looper.getMainLooper()).post(() -> m_image1.setVisibility(VISIBLE));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            m_image1.setRotation(315);
            m_loadingView.setText(R.string.loading2);
        }, TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            m_image1.setVisibility(INVISIBLE);
            m_image2.setVisibility(VISIBLE);
            m_loadingView.setText(R.string.loading3);
        }, 2 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> m_image2.setRotation(45), 3 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            m_image2.setVisibility(INVISIBLE);
            m_image3.setVisibility(VISIBLE);
            m_loadingView.setText(R.string.loading1);
        }, 4 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            m_image3.setRotation(135);
            m_loadingView.setText(R.string.loading2);
        }, 5 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            m_image3.setVisibility(INVISIBLE);
            m_loadingView.setText(R.string.loading3);
            m_image4.setVisibility(VISIBLE);
        }, 6 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> m_image4.setRotation(225), 7 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivity(intent);
        }, 8 * TIME);
    }
}
