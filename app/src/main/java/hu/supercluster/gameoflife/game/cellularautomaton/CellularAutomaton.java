package hu.supercluster.gameoflife.game.cellularautomaton;

import android.os.Parcelable;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.rule.Rule;
import hu.supercluster.gameoflife.game.visualization.brush.Paintable;

public interface CellularAutomaton<T extends Cell> extends Parcelable, Paintable {
    int getSizeX();
    int getSizeY();
    int getDefaultCellState();
    void reset();
    void randomFill(Fill fill);
    void step();
    void step(int count);
    Grid<T> getCurrentState();
    void setState(Grid<T> grid);
    Rule<T> getRule();
    void setRule(Rule<T> rule);
}
