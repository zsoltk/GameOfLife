package hu.supercluster.gameoflife.game.cellularautomaton;

import com.squareup.otto.Subscribe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.CellStateChange;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.rule.Rule;
import hu.supercluster.gameoflife.game.testutils.GrowableCell;
import hu.supercluster.gameoflife.game.testutils.GrowableCellFactory;
import hu.supercluster.gameoflife.game.testutils.SimpleIncrementingRule;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;
import hu.supercluster.gameoflife.util.EventBus;

import static org.fest.assertions.api.Assertions.assertThat;

public class AbstractCellularAutomatonTest extends UnitTestSpecification {
    AbstractCellularAutomaton<GrowableCell> automaton;
    List<CellStateChange> cellStateChanges;

    @Before
    public void setup() {
        automaton = createAbstractCellularAutomaton();
        cellStateChanges = new ArrayList<>();
        EventBus.getInstance().register(this);
    }

    protected AbstractCellularAutomaton<GrowableCell> createAbstractCellularAutomaton() {
        return new AbstractCellularAutomaton<GrowableCell>(5, 5) {
            @Override
            protected CellFactory<GrowableCell> getFactory() {
                return new GrowableCellFactory();
            }

            @Override
            protected Rule<GrowableCell> getRule() {
                return new SimpleIncrementingRule();
            }
        };
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
        Grid<GrowableCell> emptyGrid = automaton.getGridHandler().createNew();

        automaton.putCell(new GrowableCell(0, 0, Cell.STATE_ALIVE));
        automaton.reset();
        assertThat(automaton.getCurrentState()).isEqualTo(emptyGrid);
    }

    @Test
    public void testSetCell() {
        Grid<GrowableCell> grid = automaton.getCurrentState();
        grid.getCell(1, 1).setState(Cell.STATE_ALIVE);

        automaton.putCell(new GrowableCell(0, 0, Cell.STATE_ALIVE));
        assertThat(automaton.getCurrentState()).isEqualTo(grid);
    }

    @Subscribe
    public void onEvent(CellStateChange cellStateChange) {
        cellStateChanges.add(cellStateChange);
    }
}
