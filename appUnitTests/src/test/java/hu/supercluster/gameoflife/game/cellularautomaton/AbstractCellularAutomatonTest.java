package hu.supercluster.gameoflife.game.cellularautomaton;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.ConwaysCell;
import hu.supercluster.gameoflife.game.cell.CellStateChangeCallback;
import hu.supercluster.gameoflife.game.cell.CellStateChange;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public class AbstractCellularAutomatonTest extends UnitTestSpecification {
    AbstractCellularAutomaton<ConwaysCell> automaton;

    @Before
    public void setup() {
        automaton = new GameOfLife(5, 5);
    }

    @Test
    public void testGetSizeX() {
        assertThat(automaton.getSizeX()).isEqualTo(5);
    }

    @Test
    public void testGetSizeY() {
        assertThat(automaton.getSizeY()).isEqualTo(5);
    }

    @Test
    public void testReset() {
        Grid<ConwaysCell> emptyGrid = automaton.getGridHandler().createNew();

        automaton.setCell(1, 1, new ConwaysCell(Cell.STATE_ALIVE));
        automaton.reset();
        assertThat(automaton.getCurrentState()).isEqualTo(emptyGrid);
    }

    @Test
    public void testStep4() {
        automaton.setCurrentState(createGlider(0));
        automaton.step(4);

        Grid<ConwaysCell> currentState = automaton.getCurrentState();
        Grid<ConwaysCell> expectedStateAfter4Steps = createGlider(1);

        assertThat(currentState).isEqualTo(expectedStateAfter4Steps);
    }

    private Grid<ConwaysCell> createGlider(int offset) {
        Grid<ConwaysCell> grid = automaton.getGridHandler().createNew();
        grid.getCell(0 + offset, 1 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(1 + offset, 2 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(2 + offset, 0 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(2 + offset, 1 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(2 + offset, 2 + offset).setState(Cell.STATE_ALIVE);

        return grid;
    }

    @Test
    public void testSetCell() {
        Grid<ConwaysCell> grid = automaton.getCurrentState();
        grid.getCell(1, 1).setState(Cell.STATE_ALIVE);

        automaton.setCell(1, 1, new ConwaysCell(Cell.STATE_ALIVE));
        assertThat(automaton.getCurrentState()).isEqualTo(grid);
    }

    @Test
    public void testSetCellStateChangeCallback() {
        final List<CellStateChange> cellStateChanges = new ArrayList<>();

        automaton.setCellStateChangeCallback(new CellStateChangeCallback() {
            @Override
            public void onCellStateChanged(CellStateChange cellStateChange) {
                cellStateChanges.add(cellStateChange);
            }
        });

        automaton.setCurrentState(createGlider(0));
        automaton.step();

        assertThat(cellStateChanges.size()).isEqualTo(4);

        CellStateChange change1 = cellStateChanges.get(0);
        CellStateChange change2 = cellStateChanges.get(1);
        CellStateChange change3 = cellStateChanges.get(2);
        CellStateChange change4 = cellStateChanges.get(3);

        assertThat(change1.x).isEqualTo(1);
        assertThat(change1.y).isEqualTo(0);
        assertThat(change1.cellState).isEqualTo(Cell.STATE_ALIVE);

        assertThat(change2.x).isEqualTo(2);
        assertThat(change2.y).isEqualTo(0);
        assertThat(change2.cellState).isEqualTo(Cell.STATE_DEAD);

        assertThat(change3.x).isEqualTo(0);
        assertThat(change3.y).isEqualTo(1);
        assertThat(change3.cellState).isEqualTo(Cell.STATE_DEAD);

        assertThat(change4.x).isEqualTo(3);
        assertThat(change4.y).isEqualTo(1);
        assertThat(change4.cellState).isEqualTo(Cell.STATE_ALIVE);
    }
}
