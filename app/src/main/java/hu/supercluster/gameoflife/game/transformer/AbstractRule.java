package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.grid.Grid;

public abstract class AbstractRule implements Rule {
    protected boolean isAlive(Grid grid, int x, int y) {
        return grid.isAlive(x, y);
    }

    protected boolean northWest(Grid grid, int x, int y) {
        return grid.isAlive(x - 1, y - 1);
    }

    protected boolean north(Grid grid, int x, int y) {
        return grid.isAlive(x, y - 1);
    }

    protected boolean northEast(Grid grid, int x, int y) {
        return grid.isAlive(x + 1, y - 1);
    }

    protected boolean west(Grid grid, int x, int y) {
        return grid.isAlive(x - 1, y);
    }

    protected boolean east(Grid grid, int x, int y) {
        return grid.isAlive(x + 1, y);
    }

    protected boolean southWest(Grid grid, int x, int y) {
        return grid.isAlive(x - 1, y + 1);
    }

    protected boolean south(Grid grid, int x, int y) {
        return grid.isAlive(x, y + 1);
    }

    protected boolean southEast(Grid grid, int x, int y) {
        return grid.isAlive(x + 1, y + 1);
    }

    protected int countNeighbors(Grid grid, int x, int y) {
        return
                (northWest(grid, x, y) ? 1 : 0) +
                        (north(grid, x, y) ? 1 : 0) +
                        (northEast(grid, x, y) ? 1 : 0) +

                        (west(grid, x, y) ? 1 : 0) +
                        (east(grid, x, y) ? 1 : 0) +

                        (southWest(grid, x, y) ? 1 : 0) +
                        (south(grid, x, y) ? 1 : 0) +
                        (southEast(grid, x, y) ? 1 : 0)
                ;
    }
}
