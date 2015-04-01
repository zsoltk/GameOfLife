package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.ConwaysCell;
import hu.supercluster.gameoflife.game.cell.ConwaysCellFactory;
import hu.supercluster.gameoflife.game.transformer.ConwaysRule;
import hu.supercluster.gameoflife.game.transformer.Rule;

public class GameOfLife extends AbstractCellularAutomaton<ConwaysCell> {
    public GameOfLife(int gridSizeX, int gridSizeY) {
        super(gridSizeX, gridSizeY);
    }

    @Override
    protected CellFactory<ConwaysCell> getFactory() {
        return new ConwaysCellFactory();
    }

    @Override
    protected Rule getRule() {
        return new ConwaysRule();
    }
}
