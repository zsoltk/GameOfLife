package hu.supercluster.gameoflife.game.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.squareup.otto.Subscribe;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.event.CellStateChange;
import hu.supercluster.gameoflife.game.event.Pause;
import hu.supercluster.gameoflife.game.event.Reset;
import hu.supercluster.gameoflife.game.event.Restart;
import hu.supercluster.gameoflife.game.event.Resume;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.manager.GameParams;
import hu.supercluster.gameoflife.game.painter.CellPainter;
import hu.supercluster.gameoflife.util.EventBus;

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
    private boolean paused;
    private long cycleTime;
    private Queue<CellStateChange> cellStateChanges;
    private Bitmap buffCanvasBitmap;
    private Canvas buffCanvas;

    public AutomatonThread(CellularAutomaton automaton, SurfaceHolder surfaceHolder, GameParams params) {
        cellStateChanges = new LinkedBlockingQueue<>();
        this.automaton = automaton;
        this.params = params;
        this.surfaceHolder = surfaceHolder;
        this.cellSizeInPixels = params.getCellSizeInPixels();
        timeForAFrame = 1000 / params.getFps();
        cellPainter = params.getCellPainter();
        paused = params.startPaused();
        createBuffCanvas();
        initialDraw();
    }

    protected void createBuffCanvas() {
        final Point displaySize = params.getDisplaySize();
        buffCanvasBitmap = Bitmap.createBitmap(displaySize.x, displaySize.y, Bitmap.Config.ARGB_8888);
        buffCanvas = new Canvas();
        buffCanvas.setBitmap(buffCanvasBitmap);
    }

    private void initialDraw() {
        final Grid grid = automaton.getCurrentState();

        for (int j = 0; j < grid.getSizeY(); j++) {
            for (int i = 0; i < grid.getSizeX(); i++) {
                paintCell(i, j, grid.getCell(i, j).getState());
            }
        }
    }

    private void paintCell(int i, int j, int cellState) {
        Point p = translate(new Point(i, j));
        int x = p.x;
        int y = p.y;

        Paint paint = cellPainter.getPaint(cellState);
        Rect rect = new Rect(
                x * cellSizeInPixels,
                y * cellSizeInPixels,
                (x + 1) * cellSizeInPixels,
                (y + 1) * cellSizeInPixels
        );

        buffCanvas.drawRect(rect, paint);
    }

    private Point translate(Point p) {
        switch (params.getScreenOrientation()) {
            case Surface.ROTATION_0: return p;
            case Surface.ROTATION_90: return new Point(p.y, automaton.getSizeX() - p.x);
            case Surface.ROTATION_180: return new Point(automaton.getSizeX() - p.x, automaton.getSizeY() - p.y);
            case Surface.ROTATION_270: return new Point(automaton.getSizeY() - p.y, p.x);

            default:
                throw new AssertionError();
        }
    }

    @Subscribe
    synchronized public void onEvent(CellStateChange cellStateChange) {
        cellStateChanges.add(cellStateChange);
    }

    @Subscribe
    synchronized public void onEvent(Pause event) {
        paused = true;
    }

    @Subscribe
    synchronized public void onEvent(Resume event) {
        paused = false;
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
        EventBus.getInstance().register(this);

        while (isRunning) {
            canvasCycle();
        }

        EventBus.getInstance().unregister(this);
    }

    protected void canvasCycle() {
        Canvas canvas = null;

        try {
            canvas = surfaceHolder.lockCanvas();
            gameCycle(canvas);

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            unlockCanvasAndPost(canvas);
        }
    }

    protected void gameCycle(Canvas canvas) throws InterruptedException {
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

    private void cycleCore(Canvas canvas) {
        handleFlags();

        if (!paused) {
            stepAutomaton();
        }

        draw(canvas);
    }

    private void handleFlags() {
        handleReset();
        handleRestart();
        resetFlags();
    }

    private void handleReset() {
        if (shouldReset) {
            clearCanvas(automaton.getDefaultCellState());
            automaton.reset();
        }
    }

    private void handleRestart() {
        if (shouldRestart) {
            clearCanvas(automaton.getDefaultCellState());
            automaton.reset();
            automaton.randomFill(params.getFill());
        }
    }

    private void clearCanvas(int state) {
        final Point displaySize = params.getDisplaySize();
        Rect rect = new Rect(0, 0, displaySize.x, displaySize.y);
        Paint paint = cellPainter.getPaint(state);
        buffCanvas.drawRect(rect, paint);
    }

    private void resetFlags() {
        shouldReset = false;
        shouldRestart = false;
    }

    private void stepAutomaton() {
        automaton.step();
    }

    private void draw(Canvas canvas) {
        for (CellStateChange change : cellStateChanges) {
            paintCell(change.x, change.y, change.stateSnapshot);
        }

        cellStateChanges.clear();
        canvas.drawBitmap(buffCanvasBitmap, 0, 0, null);
    }

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
