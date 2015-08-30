package hu.supercluster.gameoflife.game.manager;

import android.graphics.Point;

import hu.supercluster.gameoflife.game.cellularautomaton.Fill;
import hu.supercluster.gameoflife.game.painter.CellPainter;

public class GameParams {
    private final int screenOrientation;
    private final Point displaySize;
    private int gridSizeX;
    private int gridSizeY;
    private final int cellSizeInPixels;
    private final Fill fill;
    private final CellPainter cellPainter;
    private final int fps;
    private final boolean startPaused;

    public GameParams(int screenOrientation, Point displaySize, int cellSizeInPixels, Fill fill, CellPainter cellPainter, int fps, boolean startPaused) {
        this.screenOrientation = screenOrientation;
        this.displaySize = displaySize;
        gridSizeX = displaySize.x / cellSizeInPixels;
        gridSizeY = displaySize.y / cellSizeInPixels;
        this.cellSizeInPixels = cellSizeInPixels;
        this.fill = fill;
        this.cellPainter = cellPainter;
        this.fps = fps;
        this.startPaused = startPaused;
    }

    public int getScreenOrientation() {
        return screenOrientation;
    }

    public Point getDisplaySize() {
        return displaySize;
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public int getCellSizeInPixels() {
        return cellSizeInPixels;
    }

    public Fill getFill() {
        return fill;
    }

    public CellPainter getCellPainter() {
        return cellPainter;
    }

    public int getFps() {
        return fps;
    }

    public boolean startPaused() {
        return startPaused;
    }
}
