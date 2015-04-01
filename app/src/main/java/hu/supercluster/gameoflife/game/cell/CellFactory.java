package hu.supercluster.gameoflife.game.cell;

public interface CellFactory<T extends Cell> {
    T create(int x, int y);
}
