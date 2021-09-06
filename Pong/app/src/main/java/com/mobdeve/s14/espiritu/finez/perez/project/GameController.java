package com.mobdeve.s14.espiritu.finez.perez.project;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.View;

import com.mobdeve.s14.espiritu.finez.perez.project.dao.ProfileDAOSQLImpl;
import com.mobdeve.s14.espiritu.finez.perez.project.dao.ScoreModel;

public class GameController extends Thread {
    public static final int STATE_READY = 0;
    public static final int STATE_PAUSED = 1;
    public static final int STATE_RUNNING = 2;
    public static final int STATE_WIN = 3;
    public static final int STATE_LOSE = 4;

    private boolean vSensorOn;

    private final Context cont;
    private final SurfaceHolder sHolder;
    private final GameCanvas gameCanvas;
    private final Handler statHandler;
    private final Handler scorHandler;

    private boolean vRun = false;
    private int gameState;
    private Object runLock;

    private static final int FPS = 60;

    private GameActivity gameAct;

    public GameController(Context cont, SurfaceHolder sHolder, GameCanvas gameCanvas, Handler statHandler, Handler scorHandler) {
        this.cont = cont;
        this.sHolder = sHolder;
        this.gameCanvas = gameCanvas;
        this.statHandler = statHandler;
        this.scorHandler = scorHandler;
        runLock = new Object();
    }

    @Override
    public void run() {
        long nextTick = SystemClock.uptimeMillis();
        int skips = 1000 / FPS;
        while (vRun) {
            Canvas canvas = null;
            try {
                canvas = sHolder.lockCanvas(null);
                if (canvas != null) {
                    synchronized (sHolder) {
                        if (gameState == STATE_RUNNING) {
                            gameCanvas.update(canvas);
                        }
                        synchronized (runLock) {
                            if (vRun) {
                                gameCanvas.draw(canvas);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    sHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        nextTick += skips;
        long sleepT = nextTick - SystemClock.uptimeMillis();
        if (sleepT > 0) {
            try {
                Thread.sleep(sleepT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setState(int state) {
        synchronized (sHolder) {
            gameState = state;
            Resources r = cont.getResources();
            switch (gameState) {
                case STATE_READY:
                    setNewRound();
                break;

                case STATE_RUNNING:
                    hideStatus();
                break;

                case STATE_WIN:
                    setStatus(r.getString(R.string.nice));
                    gameCanvas.score++;
                    setNewRound();
                    break;

                case STATE_LOSE:
                    setStatus(r.getString(R.string.noob));
                    gameAct.gOver();
                    break;

                case STATE_PAUSED:
                    setStatus(r.getString(R.string.paused));
                    break;
            }
        }
    }

    public void setGameAct(GameActivity gAct) {
        this.gameAct = gAct;
    }

    public void setNewRound() {
        synchronized (sHolder) {
            gameCanvas.setupCanvas();
        }
    }

    public void setRun(boolean running) {
        synchronized (runLock) {
            vRun = running;
        }
    }

    public boolean sensorOn() {
        return vSensorOn;
    }

    public boolean betweenRounds() {
        return gameState != STATE_RUNNING;
    }

    private void setStatus(String status) {
        Message msg = statHandler.obtainMessage();
        Bundle bund = new Bundle();
        bund.putString("status", status);
        bund.putInt("visibility", View.VISIBLE);
        msg.setData(bund);
        statHandler.sendMessage(msg);
    }

    private void hideStatus() {
        Message msg = statHandler.obtainMessage();
        Bundle bund = new Bundle();
        bund.putInt("visibility", View.INVISIBLE);
        msg.setData(bund);
        statHandler.sendMessage(msg);
    }

    public void setScore(String gScore) {
        Message msg = statHandler.obtainMessage();
        Bundle bund = new Bundle();
        bund.putString("score", gScore);
        msg.setData(bund);
        scorHandler.sendMessage(msg);
    }
}
