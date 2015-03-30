package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.cell.CellStateChangeCallback;

public interface CellularAutomaton<T extends Cell> {
    int getSizeX();
    int getSizeY();
    void reset();
    void randomFill(float probability, int cellState);
    void step();
    void step(int count);
    void setCell(int x, int y, T state);
    void setCurrentState(Grid<T> grid);
    Grid<T> getCurrentState();
    void setCellStateChangeCallback(CellStateChangeCallback callback);
}
