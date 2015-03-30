package hu.supercluster.gameoflife.game.grid;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;

public class EndlessGridHandler<T extends Cell> implements GridHandler<T> {
    private final int x;
    private final int y;
    private final CellFactory<T> cellFactory;
    Grid<T> currentGrid;

    public EndlessGridHandler(int x, int y, CellFactory<T> cellFactory) {
        this.x = x;
        this.y = y;
        this.cellFactory = cellFactory;
        currentGrid = createNew();
    }

    @Override
    public Grid<T> getCurrent() {
        return currentGrid;
    }

    @Override
    public Grid<T> createNew() {
        return new EndlessGrid<T>(x, y, cellFactory);
    }

    @Override
    public void setCurrent(Grid<T> grid) {
        if (grid instanceof EndlessGrid) {
            currentGrid = grid;

        } else {
            currentGrid = new EndlessGrid<T>(grid, cellFactory);
        }
    }
}
