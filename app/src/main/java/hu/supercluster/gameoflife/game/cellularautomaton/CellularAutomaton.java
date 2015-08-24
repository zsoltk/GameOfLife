package hu.supercluster.gameoflife.game.cellularautomaton;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.rule.Rule;

public interface CellularAutomaton<T extends Cell> {
    int getSizeX();
    int getSizeY();
    int getDefaultCellState();
    void reset();
    void randomFill(Fill fill);
    void step();
    void step(int count);
    Grid<T> getCurrentState();
    Rule<T> getRule();
    void setRule(Rule<T> rule);
}
