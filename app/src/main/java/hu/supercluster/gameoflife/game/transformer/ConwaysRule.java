package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.ConwaysCell;
import hu.supercluster.gameoflife.game.grid.Grid;

public class ConwaysRule extends AbstractRule<ConwaysCell> {
    @Override
    public int apply(Grid<ConwaysCell> grid, int x, int y) {
        int n = countNeighbors(grid, x, y);

        if (current(grid, x, y).isAlive()) {
            return staysAlive(n);

        } else {
            return becomesAlive(n);
        }

    }

    private int staysAlive(int n) {
        if (diesOfLoneliness(n) || diesOfOverCrowding(n)) {
            return Cell.STATE_DEAD;

        } else {
            return Cell.STATE_ALIVE;
        }
    }

    private boolean diesOfLoneliness(int n) {
        return n < 2;
    }

    private boolean diesOfOverCrowding(int n) {
        return n > 3;
    }

    private int becomesAlive(int n) {
        if (getsBorn(n)) {
            return Cell.STATE_ALIVE;

        } else {
            return Cell.STATE_DEAD;
        }
    }

    private boolean getsBorn(int n) {
        return n == 3;
    }
}
