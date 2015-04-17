package hu.supercluster.gameoflife.game.creator;

import android.graphics.Point;

import hu.supercluster.gameoflife.game.cellularautomaton.Fill;

public class GameParamsBuilder {
    private Point displaySize;
    private int cellSizeInPixels;
    private Fill fill;
    private int fps;

    public static GameParamsBuilder create() {
        return new GameParamsBuilder();
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

    public GameParamsBuilder setFps(int fps) {
        this.fps = fps;
        return this;
    }

    public GameParams build() {
        return new GameParams(displaySize, cellSizeInPixels, fill, fps);
    }
}