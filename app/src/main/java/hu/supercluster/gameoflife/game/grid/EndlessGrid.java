package hu.supercluster.gameoflife.game.grid;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.Overseer;
import hu.supercluster.gameoflife.game.cell.SimpleCell;
import hu.supercluster.gameoflife.game.event.CellStateChange;
import hu.supercluster.gameoflife.util.EventBus;

public class EndlessGrid<T extends Cell> implements Grid<T>, Overseer {
    private final CellFactory<T> cellFactory;
    protected final int sizeX;
    protected final int sizeY;
    protected final T[][] cells;
    protected final Set<Long> cellIds;

    public EndlessGrid(int sizeX, int sizeY, CellFactory<T> cellFactory) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.cellFactory = cellFactory;
        cells = (T[][]) new Cell[sizeY][sizeX];
        cellIds = new HashSet<>(sizeY * sizeX);
        createCells(sizeX, sizeY, cellFactory);
    }

    public EndlessGrid(Grid<T> other, CellFactory<T> cellFactory) {
        this(other.getSizeX(), other.getSizeY(), cellFactory);
        copyCells(other);
    }

    protected void createCells(int sizeX, int sizeY, CellFactory<T> cellFactory) {
        for (int j = 0; j < sizeY; j++) {
            for (int i = 0; i < sizeX; i++) {
                T cell = cellFactory.create(i, j);
                putCell(cell);
            }
        }
    }

    protected void copyCells(Grid<T> other) {
        for (int j = 0; j < sizeY; j++) {
            for (int i = 0; i < sizeX; i++) {
                putCell(other.getCell(i, j));
            }
        }
    }

    @Override
    public void putCell(T cell) {
        int y = normalizeY(cell.getY());
        int x = normalizeX(cell.getX());
        maintainIds(cell, x, y);
        cell.setOverseer(this);

        if (cells[y][x] != null) {
            cells[y][x].setOverseer(null);
        }

        cells[y][x] = cell;
    }

    private void maintainIds(T cell, int x, int y) {
        if (cells[y][x] != null) {
            cellIds.remove(cells[y][x].getId());
        }

        cellIds.add(cell.getId());
    }

    @Override
    public void onCellStateChanged(CellStateChange cellStateChange) {
        EventBus.getInstance().post(cellStateChange);

        if (cellIds.contains(cellStateChange.cell.getId())) {
            notifyNeighbors(cellStateChange.cell);
        }
    }

    private void notifyNeighbors(Cell cell) {
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                if (j == 0 && i == 0) {
                    continue;
                }

                Cell neighbor = getCell(cell.getX() + i, cell.getY() + j);
                neighbor.onNeighborStateChange(cell.getState());
            }
        }
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public T getCell(int x, int y) {
        return (T) cells[normalizeY(y)][normalizeX(x)];
    }

    int normalizeY(int y) {
        while (y < 0) {
            y += sizeY;
        }

        while (y >= sizeY) {
            y -= sizeY;
        }

        return y;
    }

    int normalizeX(int x) {
        while (x < 0) {
            x += sizeX;
        }

        while (x >= sizeX) {
            x -= sizeX;
        }

        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndlessGrid that = (EndlessGrid) o;

        if (sizeX != that.sizeX) return false;
        if (sizeY != that.sizeY) return false;

        for (int j = 0; j < getSizeY(); j++) {
            for (int i = 0; i < getSizeX(); i++) {
                Cell cell = getCell(i, j);
                Cell otherCell = that.getCell(i, j);

                if (!otherCell.equals(cell)) {
                    debugOnCellsNotEqual(cell, otherCell);
                    return false;
                }
            }
        }

        return true;
    }

    protected void debugOnCellsNotEqual(Cell cell, Cell otherCell) {
    }

    @Override
    public int hashCode() {
        int result = sizeX;
        result = 31 * result + sizeY;

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(cellFactory);
        dest.writeInt(this.sizeX);
        dest.writeInt(this.sizeY);

        final T dummy = (T) cells[0][0];
        dest.writeInt(cellFactory.flatten(dummy).length);

        for (int j = 0; j < getSizeY(); j++) {
            for (int i = 0; i < getSizeX(); i++) {
                dest.writeIntArray(cellFactory.flatten(cells[j][i]));
            }
        }
    }

    protected EndlessGrid(Parcel in) {
        this.cellFactory = (CellFactory<T>) in.readSerializable();
        this.sizeX = in.readInt();
        this.sizeY = in.readInt();
        int flattenedLength = in.readInt();

        cells = (T[][]) new Cell[sizeY][sizeX];
        cellIds = new HashSet<>(sizeY * sizeX);


        for (int j = 0; j < getSizeY(); j++) {
            for (int i = 0; i < getSizeX(); i++) {
                final int[] flattened = new int[flattenedLength];
                in.readIntArray(flattened);
                final T cell = cellFactory.inflate(flattened);
                putCell(cell);
            }
        }
    }

    public static final Creator<EndlessGrid> CREATOR = new Creator<EndlessGrid>() {
        public EndlessGrid createFromParcel(Parcel source) {
            return new EndlessGrid(source);
        }

        public EndlessGrid[] newArray(int size) {
            return new EndlessGrid[size];
        }
    };
}
