package com.mobdeve.s14.espiritu.finez.perez.project;

import android.widget.CompoundButton;
import android.widget.Switch;
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
    private SharedPreferences.Editor spEditor;

    private Switch switchSFX;
    private Switch switchBGM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        this.sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.spEditor = this.sp.edit();

        this.switchSFX = findViewById(R.id.sSFX);
        this.switchBGM = findViewById(R.id.sBGM);

        loadData();

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

        switchBGM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    // turn on sounds
                    Sounds.mediaPlayer.start();
                }
                else {
                    // turn off sounds
                    Sounds.mediaPlayer.stop();
                    Sounds.mediaPlayer.release();
                }
            }
        });
    }

    // save sound configurations in shared preferences
    private void saveData() {
        this.spEditor.putBoolean(KEYS.SFX_KEY.name(), this.switchSFX.isChecked());
        this.spEditor.putBoolean(KEYS.BGM_KEY.name(), this.switchBGM.isChecked());

        this.spEditor.apply();
    }

    // load sound configurations in shared preferences
    private void loadData() {
        this.switchSFX.setChecked(this.sp.getBoolean(KEYS.SFX_KEY.name(), false));
        this.switchBGM.setChecked(this.sp.getBoolean(KEYS.BGM_KEY.name(), false));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.saveData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.saveData();
    }
}