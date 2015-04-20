package hu.supercluster.gameoflife.game.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.manager.GameParams;
import hu.supercluster.gameoflife.game.grid.Grid;
import hugo.weaving.DebugLog;

public class AutomatonView extends SurfaceView implements SurfaceHolder.Callback {
    private CellularAutomaton automaton;
    private GameParams params;
    private AutomatonThread thread;

    public AutomatonView(Context context) {
        super(context);
    }

    public AutomatonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutomatonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @DebugLog
    public void init(CellularAutomaton automaton, GameParams params) {
        this.automaton = automaton;
        this.params = params;

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        thread = new AutomatonThread(automaton, surfaceHolder, params);
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
        paintBlock(event);

        return true;
    }

    protected void paintBlock(MotionEvent event) {
        int x = Math.round(event.getX() / params.getCellSizeInPixels());
        int y = Math.round(event.getY() / params.getCellSizeInPixels());

        Grid grid = automaton.getCurrentState();
        grid.getCell(x, y).setState(Cell.STATE_ALIVE);
        grid.getCell(x + 1, y).setState(Cell.STATE_ALIVE);
        grid.getCell(x, y + 1).setState(Cell.STATE_ALIVE);
        grid.getCell(x + 1, y + 1).setState(Cell.STATE_ALIVE);
    }
}
