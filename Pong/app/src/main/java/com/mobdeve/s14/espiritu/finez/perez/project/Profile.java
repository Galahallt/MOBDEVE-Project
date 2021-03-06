package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.PongDAOSQLImpl;
import com.mobdeve.s14.espiritu.finez.perez.project.dao.ProfileModel;

public class Profile extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvPrompt;

    private Button btnRegister;
    private Button btnLogin;

    private PongDAOSQLImpl dbHelper;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        this.dbHelper = new PongDAOSQLImpl(this);
        this.tvPrompt = findViewById(R.id.tvPrompt);

        // Register profile
        this.etUsername = findViewById(R.id.etUsername);
        this.etPassword = findViewById(R.id.etPassword);
        this.btnRegister = findViewById(R.id.btnRegister);
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                ProfileModel profile = dbHelper.checkExisting(user);

                if (profile != null) {
                    tvPrompt.setText(user + " already exists.");
                    tvPrompt.setTextColor(Color.YELLOW);
                } else {
                    dbHelper.addProfile(new ProfileModel(user, pass));
                    tvPrompt.setTextColor(Color.GREEN);
                    tvPrompt.setText(user + " has successfully registered!");
                }
                tvPrompt.setVisibility(View.VISIBLE);
            }
        });

        // Login profile
        this.btnLogin = findViewById(R.id.btnLogin);
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                ProfileModel profile = dbHelper.checkExisting(user);

                if (profile != null) {
                    if (profile.getPassword().equals(pass)) {
                        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        spEditor = sp.edit();
                        spEditor.putString(KEYS.USER_STRING.name(), user);
                        spEditor.putString(KEYS.PASS_STRING.name(), pass);
                        spEditor.apply();

                        tvPrompt.setTextColor(Color.GREEN);
                        tvPrompt.setText(user + " has successfully logged in!");

                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                            }
                            public void onFinish() {
                                finish();
                                startActivity(new Intent(Profile.this, LBProfile.class));
                            }
                        }.start();
                    } else {
                        tvPrompt.setTextColor(Color.YELLOW);
                        tvPrompt.setText("Invalid login credentials.");
                    }
                } else {
                    tvPrompt.setTextColor(Color.YELLOW);
                    tvPrompt.setText("Invalid login credentials.");
                }
                tvPrompt.setVisibility(View.VISIBLE);
            }
        });

        // Exit profile page
        ImageButton profBackBtn = (ImageButton)findViewById(R.id.ibProfileBack);
        profBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}