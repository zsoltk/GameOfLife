package hu.supercluster.gameoflife.game.grid;

import com.squareup.otto.Subscribe;

import java.util.HashSet;
import java.util.Set;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.CellStateChange;
import hu.supercluster.gameoflife.util.EventBus;

public class EndlessGrid<T extends Cell> implements Grid<T> {
    protected final int sizeX;
    protected final int sizeY;
    protected final T[][] cells;
    protected final Set<Long> cellIds;

    public EndlessGrid(int sizeX, int sizeY, CellFactory<T> cellFactory) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        cells = (T[][]) new Cell[sizeY][sizeX];
        cellIds = new HashSet<>(sizeY * sizeX);
        createCells(sizeX, sizeY, cellFactory);
        EventBus.getInstance().register(this);
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
        cells[y][x] = cell;
    }

    private void maintainIds(T cell, int x, int y) {
        if (cells[y][x] != null) {
            cellIds.remove(cells[y][x].getId());
        }

        cellIds.add(cell.getId());
    }

    @Subscribe
    public void onEvent(CellStateChange cellStateChange) {
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
                    System.out.println(cell + " <> " + otherCell);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = sizeX;
        result = 31 * result + sizeY;

        return result;
    }
}
