package hu.supercluster.gameoflife.game.testutils;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.rule.Rule;

public class SimpleIncrementingRule implements Rule<GrowableCell> {
    @Override
    public int apply(Grid<GrowableCell> grid, int x, int y) {
        Cell cell = grid.getCell(x, y);

        return cell.getState() + 1;
    }
}
