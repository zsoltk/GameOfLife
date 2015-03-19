package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.grid.Grid;

public interface CellularAutomaton {
    int getSizeX();
    int getSizeY();
    void reset();
    void randomFill(float probability);
    void step();
    void step(int count);
    void setCell(int x, int y);
    void setCurrentState(Grid grid);
    Grid getCurrentState();
}
