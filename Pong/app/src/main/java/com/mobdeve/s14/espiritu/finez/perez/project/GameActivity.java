package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.PongDAOSQLImpl;
import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameActivity extends AppCompatActivity {
    GameController gc;

    public static Activity fa;
    private GameCanvas canvas;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    private PongDAOSQLImpl dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fa = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_canvas);

        this.dbHelper = new PongDAOSQLImpl(this);
        canvas = (GameCanvas)findViewById(R.id.gameCanvas);

        canvas.setScore((TextView)findViewById(R.id.tvScore));
        canvas.setStatus((TextView)findViewById(R.id.tvStat));
        gc = canvas.getGameController(this);

        if (Over.ov != null) {
            Over.ov.finish();
        }
    }

    public void gOver() {
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        startActivity(new Intent(GameActivity.this, Over.class));
        int score = canvas.score;
        String user = sp.getString(KEYS.USER_STRING.name(), "username");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();

        spEditor = sp.edit();
        spEditor.putInt(KEYS.SCORE_STRING.name(), score);
        spEditor.apply();

        dbHelper.addScore(new ScoreModel(user, score, format.format(date)));
    }
}