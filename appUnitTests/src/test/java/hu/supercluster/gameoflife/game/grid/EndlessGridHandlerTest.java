package hu.supercluster.gameoflife.game.grid;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public class EndlessGridHandlerTest extends UnitTestSpecification {
    EndlessGridHandler gridHandler;

    @Before
    public void setup() {
        gridHandler = new EndlessGridHandler(3, 3);
    }

    @Test
    public void testGetCurrentAfterCreation() {
        assertThat(gridHandler.getCurrent()).isEqualTo(new EndlessGrid(3, 3));
    }

    @Test
    public void testCreateNew() {
        assertThat(gridHandler.createNew()).isEqualTo(new EndlessGrid(3, 3));
    }

    @Test
    public void testSetCurrent() {
        Grid newGrid = gridHandler.createNew();
        newGrid.setCell(1, 1, true);
        gridHandler.setCurrent(newGrid);

        assertThat(gridHandler.getCurrent()).isEqualTo(newGrid);
    }
}
