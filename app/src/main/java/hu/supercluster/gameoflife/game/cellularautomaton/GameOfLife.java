package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.ConwaysCell;
import hu.supercluster.gameoflife.game.cell.ConwaysCellFactory;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.transformer.ConwaysRule;
import hu.supercluster.gameoflife.game.transformer.Rule;
import hu.supercluster.gameoflife.game.transformer.SimpleGridTransformer;

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

    @Override
    protected void handleCellStateChanged(int x, int y, int newState) {
        super.handleCellStateChanged(x, y, newState);
        updateNeighborCounts(x, y, newState);
    }

    private void updateNeighborCounts(int x, int y, int newState) {
        Grid<ConwaysCell> grid = gridHandler.getCurrent();

        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                if (j == 0 && i == 0) {
                    continue;
                }

                ConwaysCell neighbor = grid.getCell(x + i, y + j);

                if (newState == Cell.STATE_ALIVE) {
                    neighbor.increaseNeighborCount();
                } else {
                    neighbor.decreaseNeighborCount();
                }

            }
        }
    }
}
