package hu.supercluster.gameoflife.game.grid;

public interface Grid {
    int getSizeX();
    int getSizeY();
    boolean isAlive(int x, int y);
    void setCell(int x, int y, boolean isAlive);
}
