package hu.supercluster.gameoflife.game.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.squareup.otto.Subscribe;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.event.CellStateChange;
import hu.supercluster.gameoflife.game.creator.GameParams;
import hu.supercluster.gameoflife.game.event.Reset;
import hu.supercluster.gameoflife.game.event.Restart;
import hu.supercluster.gameoflife.game.painter.CellPainter;
import hu.supercluster.gameoflife.util.EventBus;
import hugo.weaving.DebugLog;

public class AutomatonThread extends Thread {
    private final CellularAutomaton automaton;
    private final SurfaceHolder surfaceHolder;
    private final int cellSizeInPixels;
    private final int timeForAFrame;
    private final CellPainter cellPainter;
    private final GameParams params;
    private boolean isRunning;
    private boolean shouldReset;
    private boolean shouldRestart;
    private long cycleTime;
    private Queue<CellStateChange> cellStateChanges;
    private Bitmap buffCanvasBitmap;
    private Canvas buffCanvas;

    public AutomatonThread(CellularAutomaton automaton, SurfaceHolder surfaceHolder, GameParams params) {
        EventBus.getInstance().register(this);
        cellStateChanges = new LinkedBlockingDeque<>();
        this.automaton = automaton;
        this.params = params;
        this.surfaceHolder = surfaceHolder;
        this.cellSizeInPixels = params.getCellSizeInPixels();
        timeForAFrame = 1000 / params.getFps();
        cellPainter = params.getCellPainter();

        buffCanvasBitmap = Bitmap.createBitmap(automaton.getSizeX() * cellSizeInPixels, automaton.getSizeY() * cellSizeInPixels, Bitmap.Config.ARGB_8888);
        buffCanvas = new Canvas();
        buffCanvas.setBitmap(buffCanvasBitmap);
    }

    @Subscribe
    synchronized public void onEvent(CellStateChange cellStateChange) {
        cellStateChanges.add(cellStateChange);
    }

    @Subscribe
    synchronized public void onEvent(Reset event) {
        shouldReset = true;
    }

    @Subscribe
    synchronized public void onEvent(Restart event) {
        shouldRestart = true;
    }

    public void setRunning(boolean v) {
        this.isRunning = v;
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                oneGameCycle(canvas);

            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {
                unlockCanvasAndPost(canvas);
            }
        }
    }

    protected void oneGameCycle(Canvas canvas) throws InterruptedException {
        if (canvas != null) {
            measuredCycleCore(canvas);
            sleepToKeepFps();
        }
    }

    protected void measuredCycleCore(Canvas canvas) {
        synchronized (surfaceHolder) {
            long t0 = System.currentTimeMillis();
            cycleCore(canvas);
            long t1 = System.currentTimeMillis();
            cycleTime = t1 - t0;
        }
    }

    @DebugLog
    private void cycleCore(Canvas canvas) {
        handleFlags();
        stepAutomaton();
        draw(canvas);
    }

    private void handleFlags() {
        handleReset();
        handleRestart();
        resetFlags();
    }

    private void handleReset() {
        if (shouldReset) {
            automaton.reset();
        }
    }

    private void handleRestart() {
        if (shouldRestart) {
            automaton.reset();
            automaton.randomFill(params.getFill());
        }
    }

    private void resetFlags() {
        shouldReset = false;
        shouldRestart = false;
    }

    @DebugLog
    private void stepAutomaton() {
        automaton.step();
    }

    @DebugLog
    private void draw(Canvas canvas) {
        for (CellStateChange change : cellStateChanges) {
            paintCell(buffCanvas, change.x, change.y, change.stateSnapshot);
        }

        cellStateChanges.clear();
        canvas.drawBitmap(buffCanvasBitmap, 0, 0, null);
    }

    private void paintCell(Canvas canvas, int i, int j, int cellState) {
        Paint paint = cellPainter.getPaint(cellState);
        Rect rect = new Rect(
                i*cellSizeInPixels,
                j*cellSizeInPixels,
                (i+1)*cellSizeInPixels,
                (j+1)*cellSizeInPixels
        );

        if (canvas != null) {
            canvas.drawRect(rect, paint);
        }
    }

    @DebugLog
    private void sleepToKeepFps() throws InterruptedException {
        long sleepTime = timeForAFrame - cycleTime;

        if (sleepTime > 0) {
            sleep(sleepTime);
        }
    }

    private void unlockCanvasAndPost(Canvas canvas) {
        if (canvas != null) {
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
