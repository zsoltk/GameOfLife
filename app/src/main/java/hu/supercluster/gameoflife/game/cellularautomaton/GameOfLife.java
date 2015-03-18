package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.transformer.ConwaysRule;
import hu.supercluster.gameoflife.game.transformer.Rule;

public class GameOfLife extends AbstractCellularAutomaton {
    public GameOfLife(int gridSizeX, int gridSizeY) {
        super(gridSizeX, gridSizeY);
    }

    @Override
    protected Rule getRule() {
        return new ConwaysRule();
    }
}
