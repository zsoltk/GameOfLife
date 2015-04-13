package hu.supercluster.gameoflife.app.activity.automaton;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellStateChange;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.util.EventBus;
import hugo.weaving.DebugLog;

class AutomatonThread extends Thread {
    private final CellularAutomaton automaton;
    private final SurfaceHolder surfaceHolder;
    private final int cellSizeInPixels;
    private final int timeForAFrame;
    private boolean isRunning;
    private final Paint paintAlive;
    private final Paint paintExtra;
    private final Paint paintDead;
    private long cycleTime;
    private List<CellStateChange> cellStateChanges;
    private int cnt;
    private Bitmap buffCanvasBitmap;
    private Canvas buffCanvas;

    public AutomatonThread(CellularAutomaton automaton, SurfaceHolder surfaceHolder, int cellSizeInPixels, int fps) {
        EventBus.getInstance().register(this);
        cellStateChanges = new LinkedList<>();
        this.automaton = automaton;
        this.surfaceHolder = surfaceHolder;
        this.cellSizeInPixels = cellSizeInPixels;
        timeForAFrame = 1000 / fps;

        paintAlive = createPaint("#ff9900");
        paintExtra = createPaint("#99ff00");
        paintDead = createPaint("#000000");

        buffCanvasBitmap = Bitmap.createBitmap(automaton.getSizeX() * cellSizeInPixels, automaton.getSizeY() * cellSizeInPixels, Bitmap.Config.ARGB_8888);
        buffCanvas = new Canvas();
        buffCanvas.setBitmap(buffCanvasBitmap);
    }

    private Paint createPaint(String color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(color));
        
        return paint;
    }

    @Subscribe
    synchronized public void onEvent(CellStateChange cellStateChange) {
        cellStateChanges.add(cellStateChange);
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        this.automaton.randomFill(0.10f, Cell.STATE_ALIVE);

        while (isRunning) {
            Canvas canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    cycle(canvas);
                    sleepToKeepFps();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

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
        draw(canvas);
        stepAutomaton();
    }

    @DebugLog
    private void stepAutomaton() {
        automaton.step();
    }

    @DebugLog
    private void draw(Canvas canvas) {
//        canvas.drawColor(Color.BLACK);
//        Grid grid = automaton.getCurrentState();
//        for (int j = 0; j < grid.getSizeY(); j++) {
//            for (int i = 0; i < grid.getSizeX(); i++) {
//                Cell cell = grid.getCell(i, j);
//                if (cell.isAlive()) {
//                    paintCell(canvas, i, j, cell.getState());
//                }
//            }
//        }

        for (CellStateChange change : cellStateChanges) {
            paintCell(buffCanvas, change.x, change.y, change.stateSnapshot);
        }

        cellStateChanges.clear();
        canvas.drawBitmap(buffCanvasBitmap, 0, 0, null);
    }

    private void paintCell(Canvas canvas, int i, int j, int cellState) {
        Paint paint = getPaint(cellState);
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

    private Paint getPaint(int state) {
        return state == Cell.STATE_ALIVE ? paintAlive : paintDead;
    }

    @DebugLog
    private void sleepToKeepFps() throws InterruptedException {
        long sleepTime = timeForAFrame - cycleTime;

        if (sleepTime > 0) {
            sleep(sleepTime);
        }
    }
}
