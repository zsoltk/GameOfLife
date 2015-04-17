package hu.supercluster.gameoflife.game.painter;

import hu.supercluster.gameoflife.game.cell.Cell;

public class SimpleCellPainter extends AbstractCellPainter {
    public SimpleCellPainter() {
        super();
        paintMap.put(Cell.STATE_DEAD, createPaint("#000000"));
        paintMap.put(Cell.STATE_ALIVE, createPaint("#ff9900"));
    }

}
