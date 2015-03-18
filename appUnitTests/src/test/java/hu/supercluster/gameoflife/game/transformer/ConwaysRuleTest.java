package hu.supercluster.gameoflife.game.transformer;

import org.junit.Before;
import org.junit.Test;


import hu.supercluster.gameoflife.game.grid.EndlessGrid;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public class ConwaysRuleTest extends UnitTestSpecification {
    GridTransformer transformer;
    private ConwaysRule rule;

    @Before
    public void setup() {
        rule = new ConwaysRule();
        transformer = new SimpleGridTransformer();
    }

    @Test
    public void testAllDeadCellsRemainSo() {
        Grid grid = emptyGrid();
        Grid result = transform(grid);
        Grid expected = emptyGrid();

        testEqual(result, expected);
    }

    @Test
    public void testLonelyDies() {
        Grid grid = emptyGrid();
        grid.setCell(1, 1, true);
        Grid result = transform(grid);
        Grid expected = emptyGrid();

        testEqual(result, expected);
    }

    @Test
    public void testSimpleOscillator() {
        Grid grid = horizontalOscillator();
        Grid result = transform(grid);
        Grid expected = verticalOscillator();

        testEqual(result, expected);
    }

    private Grid emptyGrid() {
        return new EndlessGrid(5, 5);
    }

    private Grid transform(Grid grid) {
        Grid result = emptyGrid();
        transformer.transform(grid, result, rule);

        return result;
    }

    private Grid horizontalOscillator() {
        Grid grid = emptyGrid();
        grid.setCell(1, 2, true);
        grid.setCell(2, 2, true);
        grid.setCell(3, 2, true);

        return grid;
    }

    private Grid verticalOscillator() {
        Grid grid = emptyGrid();
        grid.setCell(2, 1, true);
        grid.setCell(2, 2, true);
        grid.setCell(2, 3, true);

        return grid;
    }

    private void testEqual(Grid grid1, Grid grid2) {
        assertThat(grid1).isEqualTo(grid2);
    }
}
