package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.PongDAOSQLImpl;
import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {
    private ArrayList<ScoreModel> data;

    private SharedPreferences sp;

    private LBAdapter lbAdapter;
    private PongDAOSQLImpl dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards);

        this.data = new ArrayList<>();
        this.lbAdapter = new LBAdapter(Leaderboard.this, data);
        this.dbHelper = new PongDAOSQLImpl(this);

        // Set RecyclerView of Leaderboard
        RecyclerView lb = (RecyclerView)findViewById(R.id.rvLeaderboads);
        lb.setLayoutManager(new LinearLayoutManager(this));
        lb.setAdapter(this.lbAdapter);
        data = dbHelper.getAllUserHighestScore();
        this.lbAdapter.setData(data);

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