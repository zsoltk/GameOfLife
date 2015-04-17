package hu.supercluster.gameoflife.game.cellularautomaton;

import java.util.Random;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.grid.EndlessGridHandler;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.grid.GridHandler;
import hu.supercluster.gameoflife.game.transformer.GridTransformer;
import hu.supercluster.gameoflife.game.rule.Rule;
import hu.supercluster.gameoflife.game.transformer.ThreadedGridTransformer;

abstract class AbstractCellularAutomaton<T extends Cell> implements CellularAutomaton<T> {
    protected final int gridSizeX;
    protected final int gridSizeY;
    final GridHandler<T> gridHandler;
    final GridTransformer<T> gridTransformer;
    final Rule<T> rule;

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

    protected GridTransformer<T> getGridTransformer() {
        return new ThreadedGridTransformer<T>();
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
    public void randomFill(Fill fill) {
        Grid<T> emptyGrid = gridHandler.createNew();
        Random random = new Random();

        for (int j = 0; j < getSizeY(); j++) {
            for (int i = 0; i < getSizeX(); i++) {
                if (random.nextFloat() < fill.getPercentage()) {
                    emptyGrid.getCell(i, j).setState(fill.getState());
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
        gridTransformer.transform(gridHandler.getCurrent(), rule);
    }

    @Override
    public final void putCell(T cell) {
        Grid<T> grid = getCurrentState();
        grid.putCell(cell);
    }

    @Override
    public final void setCurrentState(Grid<T> grid) {
        gridHandler.setCurrent(grid);
    }

    @Override
    public final Grid<T> getCurrentState() {
        return gridHandler.getCurrent();
    }
}
