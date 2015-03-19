package hu.supercluster.gameoflife.app.activity.automaton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceHolder;

import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.grid.Grid;
import hugo.weaving.DebugLog;

class AutomatonThread extends Thread {
    private final CellularAutomaton automaton;
    private final SurfaceHolder surfaceHolder;
    private final int cellSizeInPixels;
    private final int timeForAFrame;
    private boolean isRunning;
    private final Paint paintAlive;
    private final Paint paintDead;
    private long cycleTime;

    public AutomatonThread(CellularAutomaton automaton, SurfaceHolder surfaceHolder, int cellSizeInPixels, int fps) {
        this.automaton = automaton;
        this.surfaceHolder = surfaceHolder;
        this.cellSizeInPixels = cellSizeInPixels;
        timeForAFrame = 1000 / fps;

        paintAlive = getPaint("#ff9900");
        paintDead = getPaint("#000000");
    }
    
    private Paint getPaint(String color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(color));
        
        return paint;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                cycle(canvas);
                sleepToKeepFps();

            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @DebugLog
    protected void cycle(Canvas canvas) {
        synchronized (surfaceHolder) {
            long t0 = System.currentTimeMillis();
            cycleCore(canvas);
            long t1 = System.currentTimeMillis();
            cycleTime = t1 - t0;
        }
    }

    @DebugLog
    private void cycleCore(Canvas canvas) {
        stepAutomaton();
        draw(canvas);
    }

    @DebugLog
    private void stepAutomaton() {
        automaton.step();
    }

    @DebugLog
    private void draw(Canvas canvas) {
        Grid grid = automaton.getCurrentState();

        for (int j = 0; j < grid.getSizeY(); j++) {
            for (int i = 0; i < grid.getSizeX(); i++) {
                paintCell(canvas, grid, j, i);
            }
        }
    }

    private void paintCell(Canvas canvas, Grid grid, int j, int i) {
        boolean cell = grid.isAlive(i, j);
        Paint paint = getPaint(cell);
        Rect rect = new Rect(
                i*cellSizeInPixels,
                j*cellSizeInPixels,
                (i+1)*cellSizeInPixels,
                (j+1)*cellSizeInPixels
        );
        canvas.drawRect(rect, paint);
    }

    private Paint getPaint(boolean isAlive) {
        return isAlive ? paintAlive : paintDead;
    }

    @DebugLog
    private void sleepToKeepFps() throws InterruptedException {
        long sleepTime = timeForAFrame - cycleTime;

        if (sleepTime > 0) {
            sleep(sleepTime);
        }
    }
}
