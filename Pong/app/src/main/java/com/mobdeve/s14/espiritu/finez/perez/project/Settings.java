package com.mobdeve.s14.espiritu.finez.perez.project;

import android.media.MediaPlayer;
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
                saveData();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        switchBGM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // turn on sounds
                    Sounds.mediaPlayer = MediaPlayer.create(Settings.this, R.raw.gamemusic);
                    Sounds.mediaPlayer.setLooping(true);
                    Sounds.mediaPlayer.start();
                } else {
                    // turn off sounds
                    Sounds.mediaPlayer.stop();
                    Sounds.mediaPlayer.release();
                }
                saveData();
            }
        });

        switchSFX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveData();
            }
        });
    }

    // save sound configurations in shared preferences
    private void saveData() {
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        spEditor = sp.edit();

        spEditor.putBoolean(KEYS.SFX_KEY.name(), this.switchSFX.isChecked());
        spEditor.putBoolean(KEYS.BGM_KEY.name(), this.switchBGM.isChecked());

        spEditor.apply();
    }

    // load sound configurations in shared preferences
    private void loadData() {
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switchSFX.setChecked(sp.getBoolean(KEYS.SFX_KEY.name(), false));
        switchBGM.setChecked(sp.getBoolean(KEYS.BGM_KEY.name(), false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.saveData();
    }
}