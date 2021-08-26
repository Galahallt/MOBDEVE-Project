package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    GameController gc;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fa = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_canvas);

        final GameCanvas canvas = (GameCanvas)findViewById(R.id.gameCanvas);
        canvas.setScore((TextView)findViewById(R.id.tvScore));
        canvas.setStatus((TextView)findViewById(R.id.tvStat));

        gc = canvas.getGameController(this);
    }

    public void gOver() {
        startActivity(new Intent(GameActivity.this, Over.class));
    }
}