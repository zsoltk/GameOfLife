package hu.supercluster.gameoflife.game.transformer;

import org.junit.Before;
import org.junit.Test;


import hu.supercluster.gameoflife.game.cell.Cell;
import hu.supercluster.gameoflife.game.cell.CellFactory;
import hu.supercluster.gameoflife.game.cell.ConwaysCell;
import hu.supercluster.gameoflife.game.cell.ConwaysCellFactory;
import hu.supercluster.gameoflife.game.grid.EndlessGrid;
import hu.supercluster.gameoflife.game.grid.Grid;
import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public class ConwaysRuleTest extends UnitTestSpecification {
    CellFactory<ConwaysCell> cellFactory;
    GridTransformer<ConwaysCell> transformer;
    Rule<ConwaysCell> rule;

    @Before
    public void setup() {
        cellFactory = new ConwaysCellFactory();
        transformer = new SimpleGridTransformer<>();
        rule = new ConwaysRule();
    }

    @Test
    public void testAllDeadCellsRemainSo() {
        Grid<ConwaysCell> grid = emptyGrid();
        transform(grid);
        Grid<ConwaysCell> expected = emptyGrid();

        testEqual(grid, expected);
    }

    @Test
    public void testLonelyDies() {
        Grid<ConwaysCell> grid = emptyGrid();
        grid.getCell(1, 1).setState(Cell.STATE_ALIVE);
        transform(grid);
        Grid<ConwaysCell> expected = emptyGrid();

        testEqual(grid, expected);
    }

    @Test
    public void testSimpleOscillator() {
        Grid<ConwaysCell> grid = horizontalOscillator();
        transform(grid);
        Grid<ConwaysCell> expected = verticalOscillator();

        testEqual(grid, expected);
    }

    private void transform(Grid<ConwaysCell> grid) {
        transformer.transform(grid, rule);
    }

    private Grid<ConwaysCell> emptyGrid() {
        return new EndlessGrid<>(5, 5, cellFactory);
    }

    private Grid<ConwaysCell> horizontalOscillator() {
        Grid<ConwaysCell> grid = emptyGrid();
        grid.getCell(1, 2).setState(Cell.STATE_ALIVE);
        grid.getCell(2, 2).setState(Cell.STATE_ALIVE);
        grid.getCell(3, 2).setState(Cell.STATE_ALIVE);

        return grid;
    }

    private Grid<ConwaysCell> verticalOscillator() {
        Grid<ConwaysCell> grid = emptyGrid();
        grid.getCell(2, 1).setState(Cell.STATE_ALIVE);
        grid.getCell(2, 2).setState(Cell.STATE_ALIVE);
        grid.getCell(2, 3).setState(Cell.STATE_ALIVE);

        return grid;
    }

    private void testEqual(Grid<ConwaysCell> grid1, Grid<ConwaysCell> grid2) {
        assertThat(grid1).isEqualTo(grid2);
    }
}
