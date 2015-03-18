package hu.supercluster.gameoflife.game.grid;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.*;

public class EndlessGridTest extends UnitTestSpecification {
    EndlessGrid grid;

    @Before
    public void setup() {
        grid = new EndlessGrid(3, 3);
    }

    @Test
    public void testAllCellsAreDeadByDefault() {
        for (int j = 0; j < grid.getSizeY(); j++) {
            for (int i = 0; i < grid.getSizeX(); i++) {
                assertThat(grid.isAlive(i, j)).isEqualTo(false);
            }
        }
    }

    @Test
    public void testGetSizeX() {
        assertThat(grid.getSizeX()).isEqualTo(3);
    }

    @Test
    public void testGetSizeY() {
        assertThat(grid.getSizeY()).isEqualTo(3);
    }

    @Test
    public void testNormalizeX() {
        assertThat(grid.normalizeX(-5)).isEqualTo(1);
    }

    @Test
    public void testNormalizeY() {
        assertThat(grid.normalizeY(4)).isEqualTo(1);
    }

    @Test
    public void testGetX() {
        grid.setCell(1, 1, true);
        assertThat(grid.isAlive(4, 4)).isEqualTo(true);
    }

    @Test
    public void testEquals() {
        EndlessGrid other = null;

        assertThat(grid).isEqualTo(grid);
        assertThat(grid.equals(null)).isNotEqualTo(true);
        assertThat(grid.equals(this)).isNotEqualTo(true);

        other = new EndlessGrid(1, 3);
        assertThat(grid).isNotEqualTo(other);

        other = new EndlessGrid(3, 1);
        assertThat(grid).isNotEqualTo(other);

        other = new EndlessGrid(3, 3);
        other.setCell(1, 1, true);
        assertThat(grid).isNotEqualTo(other);

        grid.setCell(1, 1, true);
        assertThat(grid).isEqualTo(other);
    }
}
