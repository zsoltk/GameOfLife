package hu.supercluster.gameoflife.game.cellularautomaton;

public interface CellularAutomatonFactory {
    CellularAutomaton<?> create(int gridSizeX, int gridSizeY);
}
