package hu.supercluster.gameoflife.game.testutils;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.grid.EndlessGrid;
import hu.supercluster.gameoflife.game.grid.Grid;

public class EndlessGridWrapper<T extends Cell> extends EndlessGrid<T> {
    public EndlessGridWrapper(int sizeX, int sizeY, CellFactory<T> cellFactory) {
        super(sizeX, sizeY, cellFactory);
    }

    public EndlessGridWrapper(Grid<T> other, CellFactory<T> cellFactory) {
        super(other, cellFactory);
    }


    @Override
    public String toString() {
        return "EndlessGrid{" +
                "sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", cells=" + cellsToString() +
                '}';
    }

    private String cellsToString() {
        StringBuffer result = new StringBuffer();

        for (int j = 0; j < getSizeY(); j++) {
            for (int i = 0; i < getSizeX(); i++) {
                result.append(cells[j][i].getState());
            }

            result.append(" | ");
        }

        return result.toString();
    }
}
