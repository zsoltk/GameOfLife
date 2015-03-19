package hu.supercluster.gameoflife.game.cellularautomaton;

import java.util.Random;

import hu.supercluster.gameoflife.game.grid.EndlessGridHandler;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.grid.GridHandler;
import hu.supercluster.gameoflife.game.transformer.GridTransformer;
import hu.supercluster.gameoflife.game.transformer.Rule;
import hu.supercluster.gameoflife.game.transformer.SimpleGridTransformer;

abstract class AbstractCellularAutomaton implements CellularAutomaton {
    protected final int gridSizeX;
    protected final int gridSizeY;
    final GridHandler gridHandler;
    final GridTransformer gridTransformer;
    final Rule rule;

    public AbstractCellularAutomaton(int gridSizeX, int gridSizeY) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.gridHandler = getGridHandler();
        this.gridTransformer = getGridTransformer();
        this.rule = getRule();
    }

    protected EndlessGridHandler getGridHandler() {
        return new EndlessGridHandler(gridSizeX, gridSizeY);
    }

    protected SimpleGridTransformer getGridTransformer() {
        return new SimpleGridTransformer();
    }

    abstract protected Rule getRule();

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
        Grid emptyGrid = gridHandler.createNew();
        gridHandler.setCurrent(emptyGrid);
    }

    @Override
    public void randomFill(float probability) {
        Grid emptyGrid = gridHandler.createNew();
        Random random = new Random();

        for (int j = 0; j < getSizeY(); j++) {
            for (int i = 0; i < getSizeX(); i++) {
                if (random.nextFloat() < probability) {
                    emptyGrid.setCell(i, j, true);
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
        Grid currentGrid = gridHandler.getCurrent();
        Grid newGrid = gridHandler.createNew();
        gridTransformer.transform(currentGrid, newGrid, rule);
        gridHandler.setCurrent(newGrid);
    }

    @Override
    public final void setCell(int x, int y) {
        Grid grid = getCurrentState();
        grid.setCell(x, y, true);
        setCurrentState(grid);
    }

    @Override
    public final void setCurrentState(Grid grid) {
        gridHandler.setCurrent(grid);
    }

    @Override
    public final Grid getCurrentState() {
        return gridHandler.getCurrent();
    }
}
