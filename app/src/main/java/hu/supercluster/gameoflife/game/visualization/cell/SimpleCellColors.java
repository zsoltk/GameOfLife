package hu.supercluster.gameoflife.game.visualization.cell;

import hu.supercluster.gameoflife.game.cell.Cell;

public class SimpleCellColors extends AbstractCellColors {
    public SimpleCellColors() {
        super();
        paintMap.put(Cell.STATE_DEAD, createPaint("#000000"));
        paintMap.put(Cell.STATE_ALIVE, createPaint("#ff9900"));
    }

}
