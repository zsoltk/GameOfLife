package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;

public interface Rule<T extends Cell> {
    int apply(Grid<T> grid, int x, int y);
}
