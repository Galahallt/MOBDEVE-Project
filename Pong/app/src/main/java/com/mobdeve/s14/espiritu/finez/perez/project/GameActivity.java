package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.ProfileDAOSQLImpl;
import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

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

        if (Over.ov != null) {
            Over.ov.finish();
        }
    }

    public void gOver() {
        startActivity(new Intent(GameActivity.this, Over.class));
    }
}