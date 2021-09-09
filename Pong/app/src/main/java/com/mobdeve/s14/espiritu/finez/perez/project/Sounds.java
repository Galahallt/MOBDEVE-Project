package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Sounds {
    private static SoundPool soundPool;
    private static int wallHit;
    private static int paddleHit;
    private static int endSound;

    public static MediaPlayer mediaPlayer;

    public Sounds(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        wallHit = soundPool.load(context, R.raw.wallhit, 1);
        paddleHit = soundPool.load(context, R.raw.paddlehit, 1);
        endSound = soundPool.load(context, R.raw.end, 1);
    }

    public void playWall() {
        soundPool.play(wallHit, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playPaddle() {
        soundPool.play(paddleHit, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playEnd() {
        soundPool.play(endSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
