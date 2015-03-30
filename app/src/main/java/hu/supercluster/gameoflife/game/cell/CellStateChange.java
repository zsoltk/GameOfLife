package hu.supercluster.gameoflife.game.cell;

public class CellStateChange {
    public final int x;
    public final int y;
    public final int cellState;

    public CellStateChange(int x, int y, int cellState) {
        this.x = x;
        this.y = y;
        this.cellState = cellState;
    }

    @Override
    public String toString() {
        return "CellStateChange{" +
                "x=" + x +
                ", y=" + y +
                ", cellState=" + cellState +
                '}';
    }
}