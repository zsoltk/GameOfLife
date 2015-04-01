package hu.supercluster.gameoflife.game.cell;

public class ConwaysCellFactory implements CellFactory<ConwaysCell> {
    @Override
    public ConwaysCell create(int x, int y) {
        return new ConwaysCell(x, y, ConwaysCell.STATE_DEAD);
    }
}
