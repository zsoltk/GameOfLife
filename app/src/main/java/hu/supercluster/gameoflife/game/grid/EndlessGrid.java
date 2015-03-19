package hu.supercluster.gameoflife.game.grid;

public class EndlessGrid implements Grid {
    private final int sizeX;
    private final int sizeY;
    private final boolean[][] cells;

    public EndlessGrid(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        cells = new boolean[sizeY][sizeX];

        for (int j = 0; j < sizeY; j++) {
            for (int i = 0; i < sizeX; i++) {
                setCell(i, j, false);
            }
        }
    }

    public EndlessGrid(Grid other) {
        this(other.getSizeX(), other.getSizeY());

        for (int j = 0; j < sizeY; j++) {
            for (int i = 0; i < sizeX; i++) {
                setCell(i, j, other.isAlive(i, j));
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
    public boolean isAlive(int x, int y) {
        return cells[normalizeY(y)][normalizeX(x)];
    }

    @Override
    public void setCell(int x, int y, boolean isAlive) {
        cells[normalizeY(y)][normalizeX(x)] = isAlive;
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
                if (that.isAlive(i, j) != isAlive(i, j)) {
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
