package hu.supercluster.gameoflife.game.rule;

import java.io.Serializable;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;

public interface Rule<T extends Cell> extends Serializable {
    int apply(Grid<T> grid, int x, int y);
}
