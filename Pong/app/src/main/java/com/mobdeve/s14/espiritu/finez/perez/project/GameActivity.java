package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameActivity extends AppCompatActivity {
    // refer to PongActivity in Pong2DGame
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_canvas);

        final GameCanvas canvas = (GameCanvas)findViewById(R.id.gameCanvas);
    }
}