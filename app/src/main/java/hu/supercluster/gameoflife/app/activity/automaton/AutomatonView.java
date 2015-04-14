package hu.supercluster.gameoflife.app.activity.automaton;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hugo.weaving.DebugLog;

public class AutomatonView extends SurfaceView implements SurfaceHolder.Callback {
    CellularAutomaton automaton;
    private final int cellSizeInPixels;
    AutomatonThread thread;

    public AutomatonView(Context context, CellularAutomaton automaton, int cellSizeInPixels, int fps) {
        super(context);
        this.automaton = automaton;
        this.cellSizeInPixels = cellSizeInPixels;
        setAutomaton(automaton, cellSizeInPixels, fps);
    }

    @DebugLog
    public void setAutomaton(CellularAutomaton automaton, int cellSizeInPixels, int fps) {
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        thread = new AutomatonThread(automaton, surfaceHolder, cellSizeInPixels, fps);
    }

    @Override
    @DebugLog
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //
    }

    @Override
    @DebugLog
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
        waitForThreadToDie();
    }

    @DebugLog
    private void waitForThreadToDie() {
        while (true) {
            try {
                thread.join();
                break;

            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = Math.round(event.getX() / cellSizeInPixels);
        int y = Math.round(event.getY() / cellSizeInPixels);

        automaton.getCurrentState().getCell(x, y).setState(Cell.STATE_ALIVE);

        return true;
    }
}
