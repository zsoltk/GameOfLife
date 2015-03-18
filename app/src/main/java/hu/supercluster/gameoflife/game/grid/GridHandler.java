package hu.supercluster.gameoflife.game.grid;

public interface GridHandler {
    Grid getCurrent();
    Grid createNew();
    void setCurrent(Grid grid);
}
