package hu.supercluster.gameoflife.game.cell;

public class SimpleCellFactory implements CellFactory<SimpleCell> {
    @Override
    public SimpleCell create(int x, int y) {
        return new SimpleCell(x, y, SimpleCell.STATE_DEAD);
    }
}
