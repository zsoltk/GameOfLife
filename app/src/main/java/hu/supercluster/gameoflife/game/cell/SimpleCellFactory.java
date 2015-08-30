package hu.supercluster.gameoflife.game.cell;

public class SimpleCellFactory implements CellFactory<SimpleCell> {
    @Override
    public SimpleCell create(int x, int y) {
        return new SimpleCell(x, y, SimpleCell.STATE_DEAD);
    }

    @Override
    public int[] flatten(SimpleCell cell) {
        final int[] flattened = new int[4];
        flattened[0] = cell.x;
        flattened[1] = cell.y;
        flattened[2] = cell.state;
        flattened[3] = cell.neighborCount;

        return flattened;
    }

    @Override
    public SimpleCell inflate(int[] flattened) {
        final SimpleCell cell = new SimpleCell(
                flattened[0],
                flattened[1],
                flattened[2]
        );

        cell.neighborCount = flattened[3];

        return cell;
    }
}
