package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;

public interface CellularAutomaton<T extends Cell> {
    int getSizeX();
    int getSizeY();
    void reset();
    void randomFill(float probability, int cellState);
    void step();
    void step(int count);
    void putCell(T cell);
    void setCurrentState(Grid<T> grid);
    Grid<T> getCurrentState();
}
