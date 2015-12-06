package hu.supercluster.gameoflife.game.cell;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.test.RobolectricTest;

import static org.fest.assertions.api.Assertions.assertThat;

public class SimpleCellTest extends RobolectricTest {
    SimpleCell cell;

    @Before
    public void setup() {
        cell = new SimpleCell(0, 0, Cell.STATE_ALIVE);
    }

    @Test
    public void testGetState() {
        assertThat(cell.getState()).isEqualTo(Cell.STATE_ALIVE);
    }

    @Test
    public void testSetState() {
        cell.setState(Cell.STATE_DEAD);
        assertThat(cell.getState()).isEqualTo(Cell.STATE_DEAD);
    }

    @Test
    public void testIsAlive() {
        assertThat(cell.isAlive()).isTrue();
        cell.setState(Cell.STATE_DEAD);
        assertThat(cell.isAlive()).isFalse();
    }

    @Test
    public void testIsDead() {
        assertThat(cell.isDead()).isFalse();
        cell.setState(Cell.STATE_DEAD);
        assertThat(cell.isDead()).isTrue();
    }

    @Test
    public void testIncreaseNeighborCount() {
        assertThat(cell.neighborCount).isEqualTo(0);
        cell.increaseNeighborCount();
        assertThat(cell.neighborCount).isEqualTo(1);
    }

    @Test
    public void testDecreaseNeighborCount() {
        assertThat(cell.neighborCount).isEqualTo(0);
        cell.increaseNeighborCount();
        cell.increaseNeighborCount();
        cell.decreaseNeighborCount();
        assertThat(cell.neighborCount).isEqualTo(1);
    }

    @Test
    public void testOnNeighborBorn() {
        cell.onNeighborStateChange(Cell.STATE_ALIVE);
        cell.onNeighborStateChange(Cell.STATE_ALIVE);
        assertThat(cell.neighborCount).isEqualTo(2);
    }

    @Test
    public void testOnNeighborDies() {
        cell.onNeighborStateChange(Cell.STATE_ALIVE);
        cell.onNeighborStateChange(Cell.STATE_ALIVE);
        cell.onNeighborStateChange(Cell.STATE_ALIVE);
        cell.onNeighborStateChange(Cell.STATE_DEAD);
        assertThat(cell.neighborCount).isEqualTo(2);
    }

    @Test
    public void testEquals() {
        SimpleCell other = null;

        assertThat(cell).isEqualTo(cell);
        assertThat(cell.equals(other)).isFalse();
        assertThat(cell.equals(this)).isFalse();

        other = new SimpleCell(0, 0, Cell.STATE_DEAD);
        assertThat(cell.equals(other)).isFalse();

        other = new SimpleCell(0, 0, Cell.STATE_ALIVE);
        other.increaseNeighborCount();
        assertThat(cell.equals(other)).isFalse();

        cell.increaseNeighborCount();
        assertThat(cell.equals(other)).isTrue();
    }
}
