package hu.supercluster.gameoflife.game.rule;

import org.junit.Before;
import org.junit.Test;


import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.SimpleCell;
import hu.supercluster.gameoflife.game.cell.SimpleCellFactory;
import hu.supercluster.gameoflife.game.grid.EndlessGrid;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.game.transformer.GridTransformer;
import hu.supercluster.gameoflife.game.transformer.SimpleGridTransformer;
import hu.supercluster.gameoflife.test.support.RobolectricTest;

import static org.fest.assertions.api.Assertions.assertThat;

public class ConwaysRuleTest extends RobolectricTest {
    CellFactory<SimpleCell> cellFactory;
    GridTransformer<SimpleCell> transformer;
    Rule<SimpleCell> rule;

    @Before
    public void setup() {
        cellFactory = new SimpleCellFactory();
        transformer = new SimpleGridTransformer<>();
        rule = new ConwaysRule();
    }

    @Test
    public void testAllDeadCellsRemainSo() {
        Grid<SimpleCell> grid = createEmptyGrid();
        transform(grid);
        Grid<SimpleCell> expected = createEmptyGrid();

        testEqual(grid, expected);
    }

    @Test
    public void testLonelyDies() {
        Grid<SimpleCell> grid = createEmptyGrid();
        grid.getCell(1, 1).setState(Cell.STATE_ALIVE);
        transform(grid);
        Grid<SimpleCell> expected = createEmptyGrid();

        testEqual(grid, expected);
    }

    @Test
    public void testSimpleOscillator() {
        Grid<SimpleCell> grid = horizontalOscillator();
        transform(grid);
        Grid<SimpleCell> expected = verticalOscillator();

        testEqual(grid, expected);
    }

    private void transform(Grid<SimpleCell> grid) {
        transformer.transform(grid, rule);
    }

    private Grid<SimpleCell> createEmptyGrid() {
        return new EndlessGrid<>(5, 5, cellFactory);
    }

    private Grid<SimpleCell> horizontalOscillator() {
        Grid<SimpleCell> grid = createEmptyGrid();
        grid.getCell(1, 2).setState(Cell.STATE_ALIVE);
        grid.getCell(2, 2).setState(Cell.STATE_ALIVE);
        grid.getCell(3, 2).setState(Cell.STATE_ALIVE);

        return grid;
    }

    private Grid<SimpleCell> verticalOscillator() {
        Grid<SimpleCell> grid = createEmptyGrid();
        grid.getCell(2, 1).setState(Cell.STATE_ALIVE);
        grid.getCell(2, 2).setState(Cell.STATE_ALIVE);
        grid.getCell(2, 3).setState(Cell.STATE_ALIVE);

        return grid;
    }

    private void testEqual(Grid<SimpleCell> grid1, Grid<SimpleCell> grid2) {
        assertThat(grid1).isEqualTo(grid2);
    }
}
