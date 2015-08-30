package hu.supercluster.gameoflife.game.transformer;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.rule.Rule;
import hu.supercluster.gameoflife.game.testutils.EndlessGridWrapper;
import hu.supercluster.gameoflife.game.testutils.GrowableCell;
import hu.supercluster.gameoflife.game.testutils.GrowableCellFactory;
import hu.supercluster.gameoflife.game.testutils.SimpleIncrementingRule;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public abstract class AbstractGridTransformerTester extends UnitTestSpecification {
    GrowableCellFactory cellFactory;
    GridTransformer<GrowableCell> transformer;
    Rule<GrowableCell> rule;

    @Before
    public void setup() {
        cellFactory = new GrowableCellFactory();
        transformer = getTransformer();
        rule = new SimpleIncrementingRule();
    }

    abstract protected GridTransformer<GrowableCell> getTransformer();

    @Test
    public void testTransform5() {
        Grid<GrowableCell> grid = createEmptyGrid();
        for (int i = 0; i < 5; i++) {
            transformer.transform(grid, rule);
        }

        Grid<? extends Cell> expected = incrementEveryCellsStateBy(createEmptyGrid(), 5);
        assertThat(grid.equals(expected)).isTrue();
    }

    private Grid<GrowableCell> createEmptyGrid() {
        return new EndlessGridWrapper<>(5, 5, cellFactory);
    }

    private Grid<GrowableCell> incrementEveryCellsStateBy(Grid<GrowableCell> grid, int increment) {
        for (int y = 0; y < grid.getSizeY(); y++) {
            for (int x = 0; x < grid.getSizeX(); x++) {
                Cell cell = grid.getCell(x, y);
                cell.setState((byte) (cell.getState() + increment));
            }
        }

        return grid;
    }
}
