package hu.supercluster.gameoflife.game.rule;

import java.util.Set;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.SimpleCell;
import hu.supercluster.gameoflife.game.grid.Grid;

public class NeighborCountBasedRule implements Rule<SimpleCell> {
    private final Set<Integer> survivalNbCounts;
    private final Set<Integer> creationNbCounts;

    public NeighborCountBasedRule(Set<Integer> survivalNbCounts, Set<Integer> creationNbCounts) {
        this.survivalNbCounts = survivalNbCounts;
        this.creationNbCounts = creationNbCounts;
    }

    @Override
    public int apply(Grid<SimpleCell> grid, int x, int y) {
        SimpleCell current = grid.getCell(x, y);
        int n = current.getNeighborCount();

        if (grid.getCell(x, y).isAlive()) {
            return staysAlive(n);

        } else {
            return getsCreated(n);
        }

    }

    private int staysAlive(int n) {
        if (survivalNbCounts.contains(n)) {
            return Cell.STATE_ALIVE;

        } else {
            return Cell.STATE_DEAD;
        }
    }

    private int getsCreated(int n) {
        if (creationNbCounts.contains(n)) {
            return Cell.STATE_ALIVE;

        } else {
            return Cell.STATE_DEAD;
        }
    }

    public Set<Integer> getSurvivalNbCounts() {
        return survivalNbCounts;
    }

    public Set<Integer> getCreationNbCounts() {
        return creationNbCounts;
    }
}
