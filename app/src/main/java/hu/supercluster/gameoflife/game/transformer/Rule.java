package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.grid.Grid;

public interface Rule {
    boolean apply(Grid grid, int x, int y);
}
