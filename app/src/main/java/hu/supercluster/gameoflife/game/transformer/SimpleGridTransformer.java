package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;

public class SimpleGridTransformer<T extends Cell> implements GridTransformer<T> {
    int stateChanges[][];

    @Override
    public final void transform(Grid<T> grid, Rule<T> rule) {
        stateChanges = new int[grid.getSizeY()][grid.getSizeX()];

        for (int j = 0; j < grid.getSizeY(); j++) {
            for (int i = 0; i < grid.getSizeY(); i++) {
                stateChanges[j][i] = rule.apply(grid, i, j);
            }
        }

        for (int j = 0; j < grid.getSizeY(); j++) {
            for (int i = 0; i < grid.getSizeY(); i++) {
                grid.getCell(i, j).setState(stateChanges[j][i]);
            }
        }
    }
}
