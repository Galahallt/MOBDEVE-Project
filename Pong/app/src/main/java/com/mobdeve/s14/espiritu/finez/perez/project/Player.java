package com.mobdeve.s14.espiritu.finez.perez.project;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Player {
    private int paddleWidth;
    private int paddleHeight;
    private Paint paint;
    public RectF bounds;

    public Player (int paddleWidth, int paddleHeight, Paint paint) {
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
        this.paint = paint;
        bounds = new RectF(0, 0, paddleHeight, paddleWidth);
    }

    public void draw (Canvas canvas) { canvas.drawRoundRect(bounds, 5, 5, paint); }

    public int getPaddleWidth () { return paddleWidth; }

    public int getPaddleHeight () { return paddleHeight; }
}
