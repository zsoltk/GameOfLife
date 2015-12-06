package hu.supercluster.gameoflife.game.cell;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.test.RobolectricTest;

import static org.fest.assertions.api.Assertions.assertThat;

public class SimpleCellFactoryTest extends RobolectricTest {
    SimpleCellFactory factory;

    @Before
    public void setup() {
        factory = new SimpleCellFactory();
    }

    @Test
    public void testCellIsOfCorrectClass() {
        Cell cell = factory.create(0, 0);
        assertThat(cell.getClass().equals(SimpleCell.class)).isTrue();
    }

    @Test
    public void testCreatedCellsAreDeadByDefault() {
        SimpleCell cell = factory.create(0, 0);
        assertThat(cell.isDead()).isTrue();
    }
}
