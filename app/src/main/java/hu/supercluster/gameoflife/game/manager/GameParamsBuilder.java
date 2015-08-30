package hu.supercluster.gameoflife.game.manager;

import android.graphics.Point;

import hu.supercluster.gameoflife.game.cellularautomaton.Fill;
import hu.supercluster.gameoflife.game.painter.CellPainter;

public class GameParamsBuilder {
    private int screenOrientation;
    private Point displaySize;
    private int cellSizeInPixels;
    private Fill fill;
    private CellPainter cellPainter;
    private int fps;
    private boolean startPaused;

    public static GameParamsBuilder create() {
        return new GameParamsBuilder();
    }

    public GameParamsBuilder setScreenOrientation(int screenOrientation) {
        this.screenOrientation = screenOrientation;
        return this;
    }

    public GameParamsBuilder setDisplaySize(Point displaySize) {
        this.displaySize = displaySize;
        return this;
    }

    public GameParamsBuilder setCellSizeInPixels(int cellSizeInPixels) {
        this.cellSizeInPixels = cellSizeInPixels;
        return this;
    }

    public GameParamsBuilder setFill(Fill fill) {
        this.fill = fill;
        return this;
    }

    public GameParamsBuilder setCellPainter(CellPainter cellPainter) {
        this.cellPainter = cellPainter;
        return this;
    }

    public GameParamsBuilder setFps(int fps) {
        this.fps = fps;
        return this;
    }

    public GameParamsBuilder startPaused(boolean startPaused) {
        this.startPaused = startPaused;
        return this;
    }

    public GameParams build() {
        return new GameParams(screenOrientation, displaySize, cellSizeInPixels, fill, cellPainter, fps, startPaused);
    }
}