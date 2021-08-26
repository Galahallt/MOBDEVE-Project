package com.mobdeve.s14.espiritu.finez.perez.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Over extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameActivity.fa.finish();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
    }
}
