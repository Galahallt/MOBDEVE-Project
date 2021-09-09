package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.PongDAOSQLImpl;
import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

import java.util.ArrayList;

public class LBProfile extends AppCompatActivity {
    private ArrayList<ScoreModel> data;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    private LBProfileAdapter lbProfAdapter;
    private PongDAOSQLImpl dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_profile);

        this.data = new ArrayList<>();
        this.lbProfAdapter = new LBProfileAdapter(LBProfile.this, data);
        this.dbHelper = new PongDAOSQLImpl(this);

        // Set user
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView user = (TextView)findViewById(R.id.tvProfileUsername);
        String username = sp.getString(KEYS.USER_STRING.name(), "username");
        user.setText(username + "'s Page");

        // Set games played
        TextView gameCount = (TextView)findViewById(R.id.tvProfileTotalGames);
        gameCount.setText("Total games: " + dbHelper.getTotalGames(username));

        // Set user highest score
        TextView highScore = (TextView)findViewById(R.id.tvHighestProfScore);
        highScore.setText("Highest score: " + dbHelper.getUserHighestScore(username));

        // Set RecyclerView of Profile
        RecyclerView hist = (RecyclerView)findViewById(R.id.rvHistory);
        hist.setLayoutManager(new LinearLayoutManager(this));
        hist.setAdapter(this.lbProfAdapter);

        data = dbHelper.getAllUserScores(username);
        this.lbProfAdapter.setData(data);

        // Back to previous page
        ImageButton ibBack = (ImageButton)findViewById(R.id.ibLbProfileBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Logout
        Button btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spEditor = sp.edit();
                spEditor.clear();
                spEditor.apply();
                finish();
            }
        });
    }
}
