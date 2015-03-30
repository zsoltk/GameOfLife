package hu.supercluster.gameoflife.game.cell;

public class ConwaysCellFactory implements CellFactory<ConwaysCell> {
    @Override
    public ConwaysCell create() {
        return new ConwaysCell(ConwaysCell.STATE_DEAD);
    }
}
