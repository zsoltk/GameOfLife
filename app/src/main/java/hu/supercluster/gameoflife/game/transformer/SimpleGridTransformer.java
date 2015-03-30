package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;

public class SimpleGridTransformer<T extends Cell> implements GridTransformer<T> {
    @Override
    public final void transform(Grid<T> source, Grid<T> destination, Rule<T> rule) {
        for (int j = 0; j < source.getSizeY(); j++) {
            for (int i = 0; i < source.getSizeY(); i++) {
                applyRule(source, destination, rule, j, i);
            }
        }
    }

    protected void applyRule(Grid<T> source, Grid<T> destination, Rule<T> rule, int j, int i) {
        int oldState = source.getCell(i, j).getState();
        int newState = rule.apply(source, i, j);
        destination.getCell(i, j).setState(newState);

        if (newState != oldState) {
            onCellStateChanged(i, j, newState);
        }
    }

    protected void onCellStateChanged(int x, int y, int newState) {
    }
}
