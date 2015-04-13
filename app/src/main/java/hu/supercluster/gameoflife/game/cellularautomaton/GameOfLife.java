package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.SimpleCell;
import hu.supercluster.gameoflife.game.cell.SimpleCellFactory;
import hu.supercluster.gameoflife.game.rule.ConwaysRule;
import hu.supercluster.gameoflife.game.rule.Rule;

public class GameOfLife extends AbstractCellularAutomaton<SimpleCell> {
    public GameOfLife(int gridSizeX, int gridSizeY) {
        super(gridSizeX, gridSizeY);
    }

    @Override
    protected CellFactory<SimpleCell> getFactory() {
        return new SimpleCellFactory();
    }

    @Override
    protected Rule getRule() {
        return new ConwaysRule();
    }
}
