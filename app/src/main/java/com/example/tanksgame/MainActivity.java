package com.example.tanksgame;

import static com.example.tanksgame.Color.BLUE;
import static com.example.tanksgame.canvas.Rocket.ROCKET_LENGTH;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tanksgame.canvas.Rocket;
import com.example.tanksgame.canvas.Tank;

public class MainActivity extends AppCompatActivity {
    private Tank tank;
    private Rocket rocket;

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

        tank = findViewById(R.id.tank);
        tank.setColor(BLUE);
        tank.configurePaintColor();
        tank.setInitX(50);
        tank.setInitY(200);

        View moveButton = findViewById(R.id.moveButton);

        moveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        launchRocket();
                        tank.toggleMobility(); // Start moving
                        return true; // Consume event
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        tank.toggleMobility(); // Stop moving
                        return true; // Consume event
                }
                return false; // Don't consume other events
            }
        });

        View fireButton = findViewById(R.id.fireButton);

        fireButton.setOnClickListener(v -> launchRocket());
    }

    private void launchRocket() {
        // Inflate the rocket view from rocket_layout.xml
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup mainLayout = findViewById(R.id.main); // Main layout to add the rocket to
        rocket = (Rocket) inflater.inflate(R.layout.rocket_layout, mainLayout, false);

        // Add the rocket to the main layout
        mainLayout.addView(rocket);

        // Position the rocket at the cannon tip
        double cannonTip = tank.getCannonTip();
        rocket.setInitX(cannonTip + ROCKET_LENGTH / 2);
        rocket.setInitY(tank.getCanvasY());
        rocket.setAngle(tank.getAngle());
        rocket.setColor(tank.getColor());
        rocket.configurePaintColor();

        // Make the rocket visible and start moving it
        rocket.setVisibility(View.VISIBLE);
    }
}