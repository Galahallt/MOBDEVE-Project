package com.mobdeve.s14.espiritu.finez.perez.project;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    public float cx;
    public float cy;
    public float vel_x;
    public float vel_y;

    private int radius;
    private Paint paint;

    public Ball (int radius, Paint paint) {
        this.radius = radius;
        this.paint = paint;
        this.vel_x = GameCanvas.BALL_SPEED;
        this.vel_y = GameCanvas.BALL_SPEED;
    }

    public void draw (Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
    }

    public void moveBall (Canvas canvas) {
        cx += vel_x;
        cy += vel_y;

        if (cx < radius) {
            cx = radius;
        } else if (cx + radius >= canvas.getWidth()) {
            cx = canvas.getWidth() - radius - 1;
        }
    }

    public int getRadius () {
        return radius;
    }
}
