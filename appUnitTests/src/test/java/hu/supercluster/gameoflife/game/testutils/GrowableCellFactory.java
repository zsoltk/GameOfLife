package hu.supercluster.gameoflife.game.testutils;

import hu.supercluster.gameoflife.game.cell.CellFactory;

public class GrowableCellFactory implements CellFactory<GrowableCell> {
    @Override
    public GrowableCell create(int x, int y) {
        return new GrowableCell(x, y, GrowableCell.STATE_DEAD);
    }
}
