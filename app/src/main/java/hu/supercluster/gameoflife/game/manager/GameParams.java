package hu.supercluster.gameoflife.game.manager;

import android.graphics.Point;

import hu.supercluster.gameoflife.game.cellularautomaton.Fill;
import hu.supercluster.gameoflife.game.visualization.cell.CellColors;

public class GameParams {
    private final int screenOrientation;
    private final Point displaySize;
    private int gridSizeX;
    private int gridSizeY;
    private final int cellSizeInPixels;
    private final Fill fill;
    private final CellColors cellColors;
    private final int fps;
    private final boolean startPaused;

    public GameParams(int screenOrientation, Point displaySize, int cellSizeInPixels, Fill fill, CellColors cellColors, int fps, boolean startPaused) {
        this.screenOrientation = screenOrientation;
        this.displaySize = displaySize;
        gridSizeX = displaySize.x / cellSizeInPixels;
        gridSizeY = displaySize.y / cellSizeInPixels;
        this.cellSizeInPixels = cellSizeInPixels;
        this.fill = fill;
        this.cellColors = cellColors;
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

    public CellColors getCellColors() {
        return cellColors;
    }

    public int getFps() {
        return fps;
    }

    public boolean startPaused() {
        return startPaused;
    }
}
