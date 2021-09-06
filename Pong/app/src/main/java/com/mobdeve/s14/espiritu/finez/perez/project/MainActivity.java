package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.ProfileDAOSQLImpl;
import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action to play the game
        FloatingActionButton playBtn = (FloatingActionButton)findViewById(R.id.fabPlay);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
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