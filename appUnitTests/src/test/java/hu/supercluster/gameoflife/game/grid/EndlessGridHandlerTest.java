package hu.supercluster.gameoflife.game.grid;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.SimpleCellFactory;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public class EndlessGridHandlerTest extends UnitTestSpecification {
    EndlessGridHandler gridHandler;
    private SimpleCellFactory cellFactory;

    @Before
    public void setup() {
        cellFactory = new SimpleCellFactory();
        gridHandler = new EndlessGridHandler(3, 3, cellFactory);
    }

    @Test
    public void testGetCurrentAfterCreation() {
        assertThat(gridHandler.getCurrent()).isEqualTo(new EndlessGrid(3, 3, cellFactory));
    }

    @Test
    public void testCreateNew() {
        assertThat(gridHandler.createNew()).isEqualTo(new EndlessGrid(3, 3, cellFactory));
    }

    @Test
    public void testSetCurrent() {
        Grid newGrid = gridHandler.createNew();
        newGrid.getCell(1, 1).setState(Cell.STATE_ALIVE);
        gridHandler.setCurrent(newGrid);

        assertThat(gridHandler.getCurrent()).isEqualTo(newGrid);
    }
}
