package hu.supercluster.gameoflife.game.cell;

import java.io.Serializable;

public interface CellFactory<T extends Cell> extends Serializable {
    T create(int x, int y);
    int[] flatten(T cell);
    T inflate(int[] flattened);
}
