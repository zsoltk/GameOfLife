package hu.supercluster.gameoflife.game.transformer;

import hu.supercluster.gameoflife.game.grid.Grid;

public interface GridTransformer {
    public void transform(Grid source, Grid destination, Rule rule);
}
