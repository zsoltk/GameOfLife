package hu.supercluster.gameoflife.game.cellularautomaton;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.game.grid.EndlessGrid;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public class AbstractCellularAutomatonTest extends UnitTestSpecification {
    AbstractCellularAutomaton automaton;

    @Before
    public void setup() {
        automaton = new GameOfLife(5, 5);
    }

    @Test
    public void testStep4() {
        automaton.setCurrentState(createGlider(0));
        automaton.step(4);

        Grid currentState = automaton.getCurrentState();
        Grid expectedStateAfter4Steps = createGlider(1);

        assertThat(currentState).isEqualTo(expectedStateAfter4Steps);
    }

    private Grid createGlider(int offset) {
        Grid grid = new EndlessGrid(automaton.getSizeX(), automaton.getSizeY());
        grid.setCell(0 + offset, 1 + offset, true);
        grid.setCell(1 + offset, 2 + offset, true);
        grid.setCell(2 + offset, 0 + offset, true);
        grid.setCell(2 + offset, 1 + offset, true);
        grid.setCell(2 + offset, 2 + offset, true);

        return grid;
    }

    @Test
    public void testSetCell() {
        Grid grid = automaton.getCurrentState();
        grid.setCell(1, 1, true);

        automaton.setCell(1, 1);
        assertThat(automaton.getCurrentState()).isEqualTo(grid);
    }

    @Test
    public void testReset() {
        Grid emptyGrid = automaton.getGridHandler().createNew();

        automaton.setCell(1, 1);
        automaton.reset();
        assertThat(automaton.getCurrentState()).isEqualTo(emptyGrid);
    }
}
