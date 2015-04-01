package hu.supercluster.gameoflife.game.grid;

import hu.supercluster.gameoflife.game.cell.Cell;

public interface Grid<T extends Cell> {
    int getSizeX();
    int getSizeY();
    T getCell(int x, int y);
    void putCell(T cell);
}
