package hu.supercluster.gameoflife.game.rule;

import java.util.HashSet;
import java.util.Set;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.SimpleCell;
import hu.supercluster.gameoflife.game.grid.Grid;

public class NeighborCountBasedRule implements Rule<SimpleCell> {
    private final Set<Integer> survivalNbCounts;
    private final Set<Integer> creationNbCounts;

    public NeighborCountBasedRule(Set<Integer> survivalNbCounts, Set<Integer> creationNbCounts) {
        this.survivalNbCounts = new HashSet<>(survivalNbCounts);
        this.creationNbCounts = new HashSet<>(creationNbCounts);
    }

    public NeighborCountBasedRule(String rule) {
        String[] split = rule.split("/");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid rule string: " + rule);
        }

        try {
            survivalNbCounts = new HashSet<>();
            String survival = split[0];
            for (int i = 0; i < survival.length(); i++) {
                survivalNbCounts.add(Integer.valueOf(String.valueOf(survival.charAt(i))));
            }

            creationNbCounts = new HashSet<>();
            String creation = split[1];
            for (int i = 0; i < creation.length(); i++) {
                creationNbCounts.add(Integer.valueOf(String.valueOf(creation.charAt(i))));
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid rule string: " + rule);
        }
    }

    public NeighborCountBasedRule(NeighborCountBasedRule other) {
        this.survivalNbCounts = new HashSet<>(other.survivalNbCounts);
        this.creationNbCounts = new HashSet<>(other.creationNbCounts);
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

    public void toggleSurvivalForNbCount(int nbCount) {
        if (survivalNbCounts.contains(nbCount)) {
            survivalNbCounts.remove(nbCount);
        } else {
            survivalNbCounts.add(nbCount);
        }
    }

    public void toggleCreationForNbCount(int nbCount) {
        if (creationNbCounts.contains(nbCount)) {
            creationNbCounts.remove(nbCount);
        } else {
            creationNbCounts.add(nbCount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NeighborCountBasedRule that = (NeighborCountBasedRule) o;

        if (!survivalNbCounts.equals(that.survivalNbCounts)) return false;
        if (!creationNbCounts.equals(that.creationNbCounts)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = survivalNbCounts.hashCode();
        result = 31 * result + creationNbCounts.hashCode();

        return result;
    }
}
