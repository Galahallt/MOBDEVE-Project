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
        this.radius = 25;
        this.paint = paint;
        this.vel_x = 15.0f;
        this.vel_y = 15.0f;
    }

    public void draw (Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
    }

    public void moveBall (Canvas canvas) {
        cx += vel_x;
        cy += vel_y;

        if (cy < radius) {
            cy = radius;
        } else if (cy + radius >= canvas.getHeight()) {
            cy = canvas.getHeight() - radius - 1;
        }
    }

    public int getRadius () {
        return radius;
    }
}
