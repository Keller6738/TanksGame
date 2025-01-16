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

public class LoadingActivity extends AppCompatActivity {
    private final int TIME = 600;
    private ImageView image1, image2, image3, image4;
    private TextView loading;

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

        image1 = findViewById(R.id.img1);
        image2 = findViewById(R.id.img2);
        image3 = findViewById(R.id.img3);
        image4 = findViewById(R.id.img4);

        loading = findViewById(R.id.title);

        image1.setVisibility(INVISIBLE);
        image2.setVisibility(INVISIBLE);
        image3.setVisibility(INVISIBLE);
        image4.setVisibility(INVISIBLE);

        new Handler(Looper.getMainLooper()).post(() -> image1.setVisibility(VISIBLE));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            image1.setRotation(315);
            loading.setText(R.string.loading2);
        }, TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            image1.setVisibility(INVISIBLE);
            image2.setVisibility(VISIBLE);
            loading.setText(R.string.loading3);
        }, 2 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> image2.setRotation(45), 3 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            image2.setVisibility(INVISIBLE);
            image3.setVisibility(VISIBLE);
            loading.setText(R.string.loading1);
        }, 4 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            image3.setRotation(135);
            loading.setText(R.string.loading2);
        }, 5 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            image3.setVisibility(INVISIBLE);
            loading.setText(R.string.loading3);
            image4.setVisibility(VISIBLE);
        }, 6 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> image4.setRotation(225), 7 * TIME);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            finish();
            startActivity(intent);
        }, 8 * TIME);
    }
}
