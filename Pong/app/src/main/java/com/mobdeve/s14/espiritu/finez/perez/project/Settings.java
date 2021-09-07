package com.mobdeve.s14.espiritu.finez.perez.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Settings extends AppCompatActivity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Go back to main screen
        ImageButton setBackBtn = (ImageButton)findViewById(R.id.ibSettingsBack);
        setBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                finish();
            }
        });

        // Go to profile page
        FloatingActionButton userBtn = (FloatingActionButton)findViewById(R.id.fabUser);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (sp.getString(KEYS.USER_STRING.name(), null) == null) {
                    startActivity(new Intent(Settings.this, Profile.class));
                } else {
                    startActivity(new Intent(Settings.this, LBProfile.class));
                }
            }
        });

        // Exit app
        Button bExit = (Button)findViewById(R.id.bExit);
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}