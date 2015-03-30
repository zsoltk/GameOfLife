package hu.supercluster.gameoflife.game.cellularautomaton;

import java.util.Random;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.CellStateChangeCallback;
import hu.supercluster.gameoflife.game.grid.EndlessGridHandler;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.grid.GridHandler;
import hu.supercluster.gameoflife.game.transformer.GridTransformer;
import hu.supercluster.gameoflife.game.transformer.Rule;
import hu.supercluster.gameoflife.game.transformer.SimpleGridTransformer;
import hu.supercluster.gameoflife.game.cell.CellStateChange;

abstract class AbstractCellularAutomaton<T extends Cell> implements CellularAutomaton<T> {
    protected final int gridSizeX;
    protected final int gridSizeY;
    final GridHandler<T> gridHandler;
    final GridTransformer<T> gridTransformer;
    final Rule<T> rule;
    CellStateChangeCallback cellStateChangeCallback;

    public AbstractCellularAutomaton(int gridSizeX, int gridSizeY) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.gridHandler = getGridHandler();
        this.gridTransformer = getGridTransformer();
        this.rule = getRule();
    }

    protected EndlessGridHandler<T> getGridHandler() {
        return new EndlessGridHandler<T>(gridSizeX, gridSizeY, getFactory());
    }

    protected SimpleGridTransformer<T> getGridTransformer() {
        return new SimpleGridTransformer<T>() {
            @Override
            protected void onCellStateChanged(int x, int y, int newState) {
                handleCellStateChanged(x, y, newState);
            }
        };
    }

    protected void handleCellStateChanged(int x, int y, int newState) {
        if (cellStateChangeCallback != null) {
            cellStateChangeCallback.onCellStateChanged(new CellStateChange(x, y, newState));
        }
    }

    abstract protected CellFactory<T> getFactory();
    abstract protected Rule<T> getRule();

    @Override
    public final int getSizeX() {
        return gridSizeX;
    }

    @Override
    public final int getSizeY() {
        return gridSizeY;
    }

    @Override
    public void reset() {
        Grid<T> emptyGrid = gridHandler.createNew();
        gridHandler.setCurrent(emptyGrid);
    }

    @Override
    public void randomFill(float probability, int cellState) {
        Grid<T> emptyGrid = gridHandler.createNew();
        Random random = new Random();

        for (int j = 0; j < getSizeY(); j++) {
            for (int i = 0; i < getSizeX(); i++) {
                if (random.nextFloat() < probability) {
                    emptyGrid.getCell(i, j).setState(cellState);
                    handleCellStateChanged(i, j, cellState);
                }
            }
        }

        gridHandler.setCurrent(emptyGrid);
    }

    @Override
    public void step(int count) {
        for (int i = 0; i < count; i++) {
            step();
        }
    }

    @Override
    public void step() {
        Grid<T> currentGrid = gridHandler.getCurrent();
        Grid<T> newGrid = gridHandler.createNew();
        gridTransformer.transform(currentGrid, newGrid, rule);
        gridHandler.setCurrent(newGrid);
    }

    @Override
    public final void setCell(int x, int y, T cell) {
        Grid<T> grid = getCurrentState();
        grid.setCell(x, y, cell);
    }

    @Override
    public final void setCurrentState(Grid<T> grid) {
        gridHandler.setCurrent(grid);
    }

    @Override
    public final Grid<T> getCurrentState() {
        return gridHandler.getCurrent();
    }

    @Override
    public void setCellStateChangeCallback(CellStateChangeCallback callback) {
        this.cellStateChangeCallback = callback;
    }
}
