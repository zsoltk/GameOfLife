package hu.supercluster.gameoflife.game.rule;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;

public abstract class AbstractRule<T extends Cell> implements Rule<T> {
    protected T current(Grid<T> grid, int x, int y) {
        return grid.getCell(x, y);
    }

    protected T northWest(Grid<T> grid, int x, int y) {
        return grid.getCell(x - 1, y - 1);
    }

    protected T north(Grid<T> grid, int x, int y) {
        return grid.getCell(x, y - 1);
    }

    protected T northEast(Grid<T> grid, int x, int y) {
        return grid.getCell(x + 1, y - 1);
    }

    protected T west(Grid<T> grid, int x, int y) {
        return grid.getCell(x - 1, y);
    }

    protected T east(Grid<T> grid, int x, int y) {
        return grid.getCell(x + 1, y);
    }

    protected T southWest(Grid<T> grid, int x, int y) {
        return grid.getCell(x - 1, y + 1);
    }

    protected T south(Grid<T> grid, int x, int y) {
        return grid.getCell(x, y + 1);
    }

    protected T southEast(Grid<T> grid, int x, int y) {
        return grid.getCell(x + 1, y + 1);
    }

    protected int countNeighbors(Grid<T> grid, int x, int y) {
        return
                (northWest(grid, x, y).isAlive() ? 1 : 0) +
                (north(grid, x, y).isAlive() ? 1 : 0) +
                (northEast(grid, x, y).isAlive() ? 1 : 0) +

                (west(grid, x, y).isAlive() ? 1 : 0) +
                (east(grid, x, y).isAlive() ? 1 : 0) +

                (southWest(grid, x, y).isAlive() ? 1 : 0) +
                (south(grid, x, y).isAlive() ? 1 : 0) +
                (southEast(grid, x, y).isAlive() ? 1 : 0)
        ;
    }
}
