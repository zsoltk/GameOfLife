package hu.supercluster.gameoflife.game.grid;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;

public class EndlessGrid<T extends Cell> implements Grid<T> {
    private final int sizeX;
    private final int sizeY;
    private final T[][] cells;

    public EndlessGrid(int sizeX, int sizeY, CellFactory<T> cellFactory) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        cells = (T[][]) new Cell[sizeY][sizeX];

        for (int j = 0; j < sizeY; j++) {
            for (int i = 0; i < sizeX; i++) {
                setCell(i, j, cellFactory.create());
            }
        }
    }

    public EndlessGrid(Grid<T> other, CellFactory<T> cellFactory) {
        this(other.getSizeX(), other.getSizeY(), cellFactory);

        for (int j = 0; j < sizeY; j++) {
            for (int i = 0; i < sizeX; i++) {
                setCell(i, j, other.getCell(i, j));
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

    @Override
    public void setCell(int x, int y, T cell) {
        cells[normalizeY(y)][normalizeX(x)] = cell;
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
