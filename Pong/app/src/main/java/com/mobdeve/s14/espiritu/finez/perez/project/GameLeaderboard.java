package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GameLeaderboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards);

        // Exit leaderboard page
        ImageButton lbBackBtn = (ImageButton)findViewById(R.id.ibLeaderboardsBack);
        lbBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                finish();
            }
        });
    }
}