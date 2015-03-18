package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.grid.Grid;

public class SimpleGridTransformer implements GridTransformer {
    @Override
    public void transform(Grid source, Grid destination, Rule rule) {
        for (int j = 0; j < source.getSizeY(); j++) {
            for (int i = 0; i < source.getSizeY(); i++) {
                setNewValueForCell(source, destination, rule, j, i);
            }
        }
    }

    protected void setNewValueForCell(Grid source, Grid destination, Rule rule, int j, int i) {
        boolean isAlive = rule.apply(source, i, j);
        destination.setCell(i, j, isAlive);
    }
}
