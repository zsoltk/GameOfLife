package hu.supercluster.gameoflife.app.activity.main;

import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import hu.supercluster.gameoflife.app.activity.automaton.AutomatonView;
import hu.supercluster.gameoflife.game.cellularautomaton.CellularAutomaton;
import hu.supercluster.gameoflife.game.cellularautomaton.GameOfLife;
import hu.supercluster.gameoflife.game.cell.Cell;

@EBean
public class MainPresenter {
    public static final int CELL_SIZE_IN_PIXELS = 6;
    public static final int FPS = 15;

    @RootContext
    MainActivity activity;

    protected void addAutomatonView() {
        Point displaySize = getDisplaySize();
        CellularAutomaton automaton = getAutomaton(displaySize, CELL_SIZE_IN_PIXELS);
        SurfaceView surfaceView = getAutomatonView(automaton, CELL_SIZE_IN_PIXELS);
        activity.layout.addView(surfaceView, 0);
    }

    private CellularAutomaton getAutomaton(Point displaySize, int cellSizeInPixels) {
        CellularAutomaton automaton = new GameOfLife(
                displaySize.x / cellSizeInPixels,
                displaySize.y / cellSizeInPixels
        );

        automaton.randomFill(0.10f, Cell.STATE_ALIVE);

        return automaton;
    }

    private AutomatonView getAutomatonView(CellularAutomaton automaton, int cellSizeInPixels) {
        return new AutomatonView(activity, automaton, cellSizeInPixels, FPS);
    }

    private Point getDisplaySize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }
}
