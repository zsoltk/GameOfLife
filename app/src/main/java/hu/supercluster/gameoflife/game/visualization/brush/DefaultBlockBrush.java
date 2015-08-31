package hu.supercluster.gameoflife.game.visualization.brush;

import hu.supercluster.gameoflife.game.cell.Cell;

public class DefaultBlockBrush extends AbstractBrush {
    @Override
    protected void doPaint() {
        paintWith(Cell.STATE_ALIVE);
        paint(0, 0);
        paint(0, 1);
        paint(1, 0);
        paint(1, 1);
    }
}
