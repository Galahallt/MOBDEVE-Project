package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LBProfile extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_profile);

        // Set user
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView user = (TextView)findViewById(R.id.tvProfileUsername);
        String username = sp.getString(KEYS.USER_STRING.name(), "username");
        user.setText(username + "'s Page");

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
