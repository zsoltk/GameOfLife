package hu.supercluster.gameoflife.game.event;

import hu.supercluster.gameoflife.game.cell.Cell;

public class CellStateChange {
    public final int x;
    public final int y;
    public final int stateSnapshot;
    public final Cell cell;

    public CellStateChange(Cell cell) {
        this.cell = cell;
        x = cell.getX();
        y = cell.getY();
        stateSnapshot = cell.getState();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellStateChange that = (CellStateChange) o;

        if (stateSnapshot != that.stateSnapshot) return false;
        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + stateSnapshot;
        return result;
    }
}