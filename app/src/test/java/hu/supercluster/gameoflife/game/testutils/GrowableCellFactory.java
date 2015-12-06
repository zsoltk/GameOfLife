package hu.supercluster.gameoflife.game.testutils;

import hu.supercluster.gameoflife.game.cell.CellFactory;

public class GrowableCellFactory implements CellFactory<GrowableCell> {
    @Override
    public GrowableCell create(int x, int y) {
        return new GrowableCell(x, y, GrowableCell.STATE_DEAD);
    }

    @Override
    public int[] flatten(GrowableCell cell) {
        final int[] flattened = new int[4];
        flattened[0] = cell.x;
        flattened[1] = cell.y;
        flattened[2] = cell.state;

        return flattened;
    }

    @Override
    public GrowableCell inflate(int[] flattened) {
        final GrowableCell cell = new GrowableCell(
                flattened[0],
                flattened[1],
                flattened[2]
        );

        return cell;
    }
}
