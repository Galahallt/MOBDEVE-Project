package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.Random;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
    private GameController gameController;
    private Sounds sounds;

    private TextView tvStatus;
    private TextView tvScore;

    public int score = 0;

    private Player pPaddle;
    private Player oPaddle;
    private Ball gBall;

    private int canvasWidth;
    private int canvasHeight;
    private Paint canvasBounds;
    private Context context;

    SurfaceHolder sHolder;

    public static float BALL_SPEED = 15.0f;
    public static float PADDLE_SPEED = 15.0f;

    private float botMove;

    private boolean move = false;
    private float lastTouch;

    private SharedPreferences sp;

    public void initGameCanvas(Context cont, AttributeSet attr) {
        context = cont;
        sHolder = getHolder();
        sHolder.addCallback(this);

        gameController = new GameController(this.getContext(), sHolder, this, new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                tvStatus.setVisibility(msg.getData().getInt("visibility"));
                tvStatus.setText(msg.getData().getString("status"));
            }
        }, new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                tvScore.setText(msg.getData().getString("score"));
            }
        });

        TypedArray ta = cont.obtainStyledAttributes(attr, R.styleable.GameCanvas);
        int rHeight = ta.getInteger(R.styleable.GameCanvas_rHeight, 340);
        int rWidth = ta.getInteger(R.styleable.GameCanvas_rWidth, 100);
        int bRadius = ta.getInteger(R.styleable.GameCanvas_bRadius, 25);

        // Initialize player paddle
        Paint pPaint = new Paint();
        pPaint.setAntiAlias(true);
        pPaint.setColor(ContextCompat.getColor(context, R.color.pColor));
        pPaddle = new Player(rWidth, rHeight, pPaint);

        // Initialize opponent paddle
        Paint oPaint = new Paint();
        oPaint.setAntiAlias(true);
        oPaint.setColor(ContextCompat.getColor(context, R.color.oColor));
        oPaddle = new Player(rWidth, rHeight, oPaint);

        // Initialize game ball
        Paint bPaint = new Paint();
        bPaint.setAntiAlias(true);
        bPaint.setColor(ContextCompat.getColor(context, R.color.bColor));
        gBall = new Ball(bRadius, bPaint);

        // Bounds
        canvasBounds = new Paint();
        canvasBounds.setAntiAlias(true);
        canvasBounds.setColor(ContextCompat.getColor(context,R.color.tColor));
        canvasBounds.setStyle(Paint.Style.STROKE);
        canvasBounds.setStrokeWidth(15.f);

        // AI Move
        botMove = 0.8f;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameController.setRun(true);
        gameController.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        canvasWidth = width;
        canvasHeight = height;

        System.out.println("Width: " + canvasWidth);
        System.out.println("Height: " + canvasHeight);
        gameController.setNewRound();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameController.setRun(false);
        while (retry) {
            try {
                gameController.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(ContextCompat.getColor(context, R.color.tColor));
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, canvasBounds);

        gameController.setScore(String.valueOf(score));
        pPaddle.draw(canvas);
        oPaddle.draw(canvas);
        gBall.draw(canvas);
    }

    public GameCanvas(Context cont, AttributeSet attr) {
        super(cont, attr);
        initGameCanvas(cont, attr);
    }

    public GameCanvas(Context cont, AttributeSet attr, int styleAttr) {
        super(cont, attr, styleAttr);
        initGameCanvas(cont, attr);
    }

    private void botPlayer() {
        if (oPaddle.bounds.left > gBall.cx) {
            playerMove(oPaddle, oPaddle.bounds.left - PADDLE_SPEED, oPaddle.bounds.top);
        } else if (oPaddle.bounds.left + oPaddle.getPaddleHeight() < gBall.cx) {
            playerMove(oPaddle, oPaddle.bounds.left + PADDLE_SPEED, oPaddle.bounds.top);
        }
    }

    public void update(Canvas canvas) {
        if (collisionPlayer(pPaddle, gBall)) {
            if (sp.getBoolean(KEYS.SFX_KEY.name(), false)) {
                sounds.playPaddle();
            }
            handleCollision(pPaddle, gBall);
        } else if (collisionPlayer(oPaddle, gBall)) {
            if (sp.getBoolean(KEYS.SFX_KEY.name(), false)) {
                sounds.playPaddle();
            }
            handleCollision(oPaddle, gBall);
        } else if (collisionLeftRight()) {
            if (sp.getBoolean(KEYS.SFX_KEY.name(), false)) {
                sounds.playWall();
            }
            gBall.vel_x = -gBall.vel_x;
        } else if (collisionBottom()) {
            if (sp.getBoolean(KEYS.SFX_KEY.name(), false)) {
                sounds.playEnd();
            }
            gameController.setState(GameController.STATE_LOSE);
            return;
        } else if (collisionTop()) {
            if (sp.getBoolean(KEYS.SFX_KEY.name(), false)) {
                sounds.playEnd();
            }
            gameController.setState(GameController.STATE_WIN);
            return;
        }

        if (new Random(System.currentTimeMillis()).nextFloat() < botMove) {
            botPlayer();
        }

        gBall.moveBall(canvas);
    }

    public boolean collisionPlayer(Player paddle, Ball gBall) {
        return paddle.bounds.intersects (
                gBall.cx - gBall.getRadius(),
                gBall.cy - gBall.getRadius(),
                gBall.cx + gBall.getRadius(),
                gBall.cy + gBall.getRadius()
        );
    }

    private boolean collisionLeftRight() {
        return ((gBall.cx <= gBall.getRadius()) || (gBall.cx + gBall.getRadius() >= canvasWidth - 1));
    }

    private boolean collisionTop() {
        return gBall.cy <= gBall.getRadius();
    }

    private boolean collisionBottom() {
        return gBall.cy + gBall.getRadius() >= canvasHeight - 1;
    }

    private void handleCollision(Player paddle, Ball gBall) {
        gBall.vel_y = -gBall.vel_y * 1.15f;
        if (paddle == pPaddle) {
            gBall.cy = pPaddle.bounds.top - gBall.getRadius();
        } else if (paddle == oPaddle) {
            gBall.cy = oPaddle.bounds.bottom + gBall.getRadius();
            PADDLE_SPEED = PADDLE_SPEED * 1.15f;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameController.sensorOn()) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if (gameController.betweenRounds()) {
                        gameController.setState(GameController.STATE_RUNNING);
                    } else {
                        if (touchedPaddle(event, pPaddle)) {
                            move = true;
                            lastTouch = event.getX();
                        }
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (move) {
                        float x = event.getX();
                        float dx = x - lastTouch;
                        lastTouch = x;
                        movePaddle(dx, pPaddle);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    move = false;
                    break;

            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (gameController.betweenRounds()) {
                    gameController.setState(GameController.STATE_RUNNING);
                }
            }
        }
        return true;
    }

    private boolean touchedPaddle(MotionEvent event, Player paddle) {
        return paddle.bounds.contains(event.getX(), event.getY());
    }

    public GameController getGameController(GameActivity gAct) {
        gameController.setGameAct(gAct);
        return gameController;
    }

    public void movePaddle(float dx, Player paddle) {
        synchronized (sHolder) {
            playerMove(paddle, paddle.bounds.left + dx, paddle.bounds.top);
        }
    }

    public synchronized void playerMove(Player paddle, float left, float top) {
        if (left < 0) {
            left = 0;
        } else if (left + paddle.getPaddleHeight() >= canvasWidth){
            left = canvasWidth - paddle.getPaddleHeight() - 1;
        }

        if (top < 2) {
            top = 2;
        } else if (top + paddle.getPaddleWidth() >= canvasWidth - 2){
            top = canvasHeight - paddle.getPaddleWidth() - 2;
        }

        paddle.bounds.offsetTo(left, top);
    }

    public void setupCanvas() {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (sp.getBoolean(KEYS.SFX_KEY.name(), false))
            sounds = new Sounds(context);
        setBall();
        setPaddles();
    }

    private void setBall() {
        gBall.cx = canvasWidth / 2;
        gBall.cy = canvasHeight / 2;
        gBall.vel_x = (gBall.vel_x / Math.abs(gBall.vel_x) * BALL_SPEED);
        gBall.vel_y = (gBall.vel_y / Math.abs(gBall.vel_y) * BALL_SPEED);
    }

    private void setPaddles() {
        pPaddle.bounds.offsetTo((canvasWidth - pPaddle.getPaddleHeight()) / 2, canvasHeight - pPaddle.getPaddleWidth() - 2);
        oPaddle.bounds.offsetTo((canvasWidth - oPaddle.getPaddleHeight()) / 2, 2);
    }

    public void setScore (TextView view) {
        tvScore = view;
    }

    public void setStatus(TextView view) {
        tvStatus = view;
    }
}
