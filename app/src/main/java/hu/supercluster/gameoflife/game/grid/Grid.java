package hu.supercluster.gameoflife.game.grid;

import android.os.Parcelable;

import hu.supercluster.gameoflife.game.cell.Cell;

public interface Grid<T extends Cell> extends Parcelable {
    int getSizeX();
    int getSizeY();
    T getCell(int x, int y);
    void putCell(T cell);
}
