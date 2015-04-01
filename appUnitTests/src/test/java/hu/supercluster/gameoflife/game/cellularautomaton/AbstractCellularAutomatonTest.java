package hu.supercluster.gameoflife.game.cellularautomaton;

import com.squareup.otto.Subscribe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.SimpleCell;
import hu.supercluster.gameoflife.game.cell.CellStateChange;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;
import hu.supercluster.gameoflife.util.EventBus;

import static org.fest.assertions.api.Assertions.assertThat;

public class AbstractCellularAutomatonTest extends UnitTestSpecification {
    AbstractCellularAutomaton<SimpleCell> automaton;
    List<CellStateChange> cellStateChanges;

    @Before
    public void setup() {
        automaton = new GameOfLife(5, 5);
        cellStateChanges = new ArrayList<>();
        EventBus.getInstance().register(this);
    }

    @After
    public void tearDown() {
        EventBus.getInstance().unregister(this);
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
        Grid<SimpleCell> emptyGrid = automaton.getGridHandler().createNew();

        automaton.putCell(new SimpleCell(0, 0, Cell.STATE_ALIVE));
        automaton.reset();
        assertThat(automaton.getCurrentState()).isEqualTo(emptyGrid);
    }

    @Test
    public void test4StepsShouldMoveGlider1UnitDiagonally() {
        automaton.setCurrentState(createGlider(automaton.getCurrentState(), 0));
        automaton.step(4);

        Grid<SimpleCell> currentState = automaton.getCurrentState();
        Grid<SimpleCell> expectedStateAfter4Steps = createGlider(automaton.getGridHandler().createNew(), 1);

        assertThat(currentState).isEqualTo(expectedStateAfter4Steps);
    }

    private Grid<SimpleCell> createGlider(Grid<SimpleCell> grid, int offset) {
        grid.getCell(0 + offset, 1 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(1 + offset, 2 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(2 + offset, 0 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(2 + offset, 1 + offset).setState(Cell.STATE_ALIVE);
        grid.getCell(2 + offset, 2 + offset).setState(Cell.STATE_ALIVE);

        return grid;
    }

    @Test
    public void testSetCell() {
        Grid<SimpleCell> grid = automaton.getCurrentState();
        grid.getCell(1, 1).setState(Cell.STATE_ALIVE);

        automaton.putCell(new SimpleCell(0, 0, Cell.STATE_ALIVE));
        assertThat(automaton.getCurrentState()).isEqualTo(grid);
    }

    @Subscribe
    public void onEvent(CellStateChange cellStateChange) {
        cellStateChanges.add(cellStateChange);
    }

    @Test
    public void testCellStateListener() {
        automaton.setCurrentState(createGlider(automaton.getCurrentState(), 0));
        automaton.step();

        assertThat(cellStateChanges.size()).isEqualTo(9);
        assertGliderCreationStateChangesCaptured();
        assertGliderStepStateChangesCaptured();
    }

    protected void assertGliderCreationStateChangesCaptured() {
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(0, 1, Cell.STATE_ALIVE)))).isEqualTo(true);
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(1, 2, Cell.STATE_ALIVE)))).isEqualTo(true);
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(2, 0, Cell.STATE_ALIVE)))).isEqualTo(true);
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(2, 1, Cell.STATE_ALIVE)))).isEqualTo(true);
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(2, 2, Cell.STATE_ALIVE)))).isEqualTo(true);
    }

    private void assertGliderStepStateChangesCaptured() {
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(1, 0, Cell.STATE_ALIVE)))).isEqualTo(true);
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(2, 0, Cell.STATE_DEAD)))).isEqualTo(true);
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(0, 1, Cell.STATE_DEAD)))).isEqualTo(true);
        assertThat(cellStateChanges.contains(new CellStateChange(new SimpleCell(3, 1, Cell.STATE_ALIVE)))).isEqualTo(true);
    }
}
