package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;

public interface GridTransformer<T extends Cell> {
    public void transform(Grid<T> source, Grid<T> destination, Rule<T> rule);
}
