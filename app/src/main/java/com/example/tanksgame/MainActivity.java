package com.example.tanksgame;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static com.example.tanksgame.Color.BLUE;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.canvas.MyCanvas;
import com.example.tanksgame.canvas.Rocket;

public class MainActivity extends AppCompatActivity {
    MyCanvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        canvas = findViewById(R.id.canvas);
        canvas.setTanksAmount(1);

        View moveButton = findViewById(R.id.moveButton);

        moveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case ACTION_DOWN:
                        canvas.toggleTankMobility(0);
                        canvas.launchRocket(
                                new Rocket(
                                        BLUE,
                                        canvas.getTankX(0),
                                        canvas.getTankY(0),
                                        canvas.getTankAngle(0)
                                )
                        );
                        return true;
                    case ACTION_UP:
                    case ACTION_CANCEL:
                        canvas.toggleTankMobility(0);
                        return true; // Consume event
                }
                return false; // Don't consume other events
            }
        });
    }
}