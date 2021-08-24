package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
    // refer to PongTable in Pong2DGame
    public static float BALL_SPEED = 15.0f;
    public static float PLAYER_PADDLE = 15.0f;

    public GameCanvas(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw (Canvas canvas) {

    }
}
