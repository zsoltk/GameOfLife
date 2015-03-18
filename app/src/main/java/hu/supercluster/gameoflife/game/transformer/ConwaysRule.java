package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.grid.Grid;

public class ConwaysRule extends AbstractRule {
    @Override
    public boolean apply(Grid grid, int x, int y) {
        int n = countNeighbors(grid, x, y);

        if (isAlive(grid, x, y)) {
            return staysAlive(n);

        } else {
            return becomesAlive(n);
        }

    }

    private boolean staysAlive(int n) {
        if (diesOfLoneliness(n) || diesOfOverCrowding(n)) {
            return false;

        } else {
            return true;
        }
    }

    private boolean diesOfLoneliness(int n) {
        return n < 2;
    }

    private boolean diesOfOverCrowding(int n) {
        return n > 3;
    }

    private boolean becomesAlive(int n) {
        return n == 3;
    }
}
