package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action to play the game
        FloatingActionButton playBtn = (FloatingActionButton)findViewById(R.id.fabPlay);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                TextView check = (TextView)findViewById(R.id.tvLoginCheck);
                if (sp.getString(KEYS.USER_STRING.name(), null) == null) {
                    check.setText("You must log in before playing.");
                    check.setVisibility(View.VISIBLE);
                    new CountDownTimer(1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            check.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(MainActivity.this, Profile.class));
                        }
                    }.start();
                } else {
                    startActivity(new Intent(MainActivity.this, GameActivity.class));
                }
            }
        });

        // Action to go to settings
        ImageButton setBtn = (ImageButton)findViewById(R.id.ibSettings);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });

        // Action to go to leaderboards
        ImageButton lbBtn = (ImageButton)findViewById(R.id.ibLeaderboards);
        lbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Leaderboard.class));
            }
        });
    }
}