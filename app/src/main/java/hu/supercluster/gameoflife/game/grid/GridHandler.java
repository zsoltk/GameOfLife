package hu.supercluster.gameoflife.game.grid;

import hu.supercluster.gameoflife.game.cell.Cell;

public interface GridHandler<T extends Cell> {
    Grid<T> getCurrent();
    Grid<T> createNew();
    void setCurrent(Grid<T> grid);
}
