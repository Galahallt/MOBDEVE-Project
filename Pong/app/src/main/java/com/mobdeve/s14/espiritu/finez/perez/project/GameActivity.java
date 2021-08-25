package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_canvas);

        final GameCanvas canvas = (GameCanvas)findViewById(R.id.gameCanvas);
        canvas.setScore((TextView)findViewById(R.id.tvScore));
        canvas.setStatus((TextView)findViewById(R.id.tvStat));

        GameController gameController = canvas.getGameController();
    }
}