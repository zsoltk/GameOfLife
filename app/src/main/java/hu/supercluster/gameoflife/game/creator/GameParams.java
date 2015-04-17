package hu.supercluster.gameoflife.game.creator;

import android.graphics.Point;

import hu.supercluster.gameoflife.game.cellularautomaton.Fill;

public class GameParams {
    private final Point displaySize;
    private final int cellSizeInPixels;
    private final Fill fill;
    private final int fps;

    public GameParams(Point displaySize, int cellSizeInPixels, Fill fill, int fps) {
        this.displaySize = displaySize;
        this.cellSizeInPixels = cellSizeInPixels;
        this.fill = fill;
        this.fps = fps;
    }

    public Point getDisplaySize() {
        return displaySize;
    }

    public int getCellSizeInPixels() {
        return cellSizeInPixels;
    }

    public int getGridSizeX() {
        return displaySize.x / cellSizeInPixels;
    }

    public int getGridSizeY() {
        return displaySize.y / cellSizeInPixels;
    }

    public Fill getFill() {
        return fill;
    }

    public int getFps() {
        return fps;
    }
}
