package com.mobdeve.s14.espiritu.finez.perez.project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Over extends AppCompatActivity {
    public static Activity ov;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ov = this;
        GameActivity.fa.finish();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        // Set current score
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String score = Integer.toString(sp.getInt(KEYS.SCORE_STRING.name(), 0));

        TextView curScore = (TextView)findViewById(R.id.tvYourScore);
        curScore.setText("Your score: " + score);

        // Replay
        ImageButton replayBtn = (ImageButton)findViewById(R.id.ibRetry);
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Over.this, GameActivity.class));
            }
        });

        // Home
        ImageButton homeBtn = (ImageButton)findViewById(R.id.ibGameOverHome);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
