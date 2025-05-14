package com.example.tanksgame.activities;

import static android.content.Intent.ACTION_BATTERY_CHANGED;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tanksgame.R;
import com.example.tanksgame.util.BatteryCheck;
import com.example.tanksgame.util.DatabaseHelper;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    Intent intent;

    // for login / register players card
    private Dialog dialog;
    private EditText editdUsername, editDEmail, editDPassword;
    private Button btnDlogin, btnDRegister;
    private TextView tvDMessage;

    // **** SQLite database
    public DatabaseHelper dbHelper;
    private Button btnLogin, btnRegister, btnExit;

    //SharedPreferences save user name in this phone
    private SharedPreferences sharedPreferences;
    private String savedUsername;

    private static boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // **** SQLite database
        dbHelper = new DatabaseHelper(this);
//        dbHelper.deleteAllUsers();

        // Retrieve the username from SharedPreferences
        this.sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        this.savedUsername = sharedPreferences.getString("USERNAME", "DefaultUser");  // "DefaultUser" is a fallback value

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        if (this.savedUsername.equals("DefaultUser")) {
            this.btnLogin.setText(R.string.login);
        } else {
            this.btnRegister.setText(R.string.sign_in);
        }

        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

        //battery
        if (isFirstTime) {
            //battery check
            registerReceiver(new BatteryCheck(), new IntentFilter(ACTION_BATTERY_CHANGED));
            isFirstTime = false;
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            private boolean firstTime = true;
            private Timer timer = new Timer();

            @Override
            public void handleOnBackPressed() {
                if (firstTime) {
                    // Custom action when the back button is pressed
                    Toast.makeText(LoginActivity.this, R.string.exit_message, Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == btnRegister.getId()) {
            createRegistrationDialog();
        }
        if (v.getId() == btnLogin.getId()) {
            createLoginDialog();
        }
        if (v.getId() == btnExit.getId()) {
            // This will finish the current activity and all activities in the task.
            finishAffinity();
        }
    }

    public void createLoginDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");
        dialog.setCancelable(true);

        tvDMessage = dialog.findViewById(R.id.tvMessage);
        tvDMessage.setVisibility(View.INVISIBLE);

        editdUsername = dialog.findViewById(R.id.editUsername);
        editDPassword = dialog.findViewById(R.id.editPassword);

        btnDlogin = dialog.findViewById(R.id.btnLogin);
        btnDlogin.setOnClickListener(v -> {

            String username = editdUsername.getText().toString().trim();

            String password = editDPassword.getText().toString().trim();

            // Validate fields
            if (username.isEmpty() || password.isEmpty()) {
                tvDMessage.setVisibility(View.VISIBLE);
                tvDMessage.setText(R.string.please_fill_all_fields);

                return;
            }

            boolean isRegistered = dbHelper.loginUserByUsername(username, password);
            tvDMessage.setVisibility(View.VISIBLE);
            if (isRegistered) {

                tvDMessage.setText(R.string.login_successful);
                // Save the username in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME", username);  // 'username' is a variable holding the user's name
                editor.apply(); // or editor.commit();

                intent = new Intent(LoginActivity.this, MenuActivity.class);

                intent.putExtra("USERNAME", username);

                startActivity(intent);
                finish();
            } else {
                tvDMessage.setText(R.string.login_failed);
            }

        });
        dialog.show();
    }

    public void createRegistrationDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.registration);
        dialog.setTitle("Registration");
        dialog.setCancelable(true);

        tvDMessage = dialog.findViewById(R.id.tvMessage);
        tvDMessage.setVisibility(View.INVISIBLE);

        editdUsername = dialog.findViewById(R.id.editUsername);
        editDPassword = dialog.findViewById(R.id.editPassword);
        editDEmail = dialog.findViewById(R.id.editEmail);

        btnDRegister = dialog.findViewById(R.id.btnRegister);
        btnDRegister.setOnClickListener(v -> {

            String username = editdUsername.getText().toString().trim();
            String email = editDEmail.getText().toString().trim();
            String password = editDPassword.getText().toString().trim();


            // Validate fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                tvDMessage.setVisibility(View.VISIBLE);
                tvDMessage.setText(R.string.please_fill_all_fields);

                return;
            }

            // Validate email format (contains '@' and '.com')
            if (!email.contains("@")) {
                tvDMessage.setVisibility(View.VISIBLE);
                tvDMessage.setText(R.string.invalid_email_format);
                return;
            }

            boolean isRegistered = dbHelper.registerUser(username, email, password);
            tvDMessage.setVisibility(View.VISIBLE);
            if (isRegistered) {
                tvDMessage.setText(R.string.registration_successful);
                new Handler(Looper.getMainLooper()).postDelayed(
                        () -> {
                            startActivity(new Intent(this, MenuActivity.class));
                            finish();
                        },
                        1000
                );
            } else {
                tvDMessage.setText(R.string.registration_failed);
            }
        });
        dialog.show();
    }
}
