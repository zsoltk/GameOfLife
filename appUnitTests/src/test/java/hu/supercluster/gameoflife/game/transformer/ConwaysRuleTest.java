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
        Grid<ConwaysCell> result = transform(grid);
        Grid<ConwaysCell> expected = emptyGrid();

        testEqual(result, expected);
    }

    @Test
    public void testLonelyDies() {
        Grid<ConwaysCell> grid = emptyGrid();
        grid.getCell(1, 1).setState(Cell.STATE_ALIVE);
        Grid<ConwaysCell> result = transform(grid);
        Grid<ConwaysCell> expected = emptyGrid();

        testEqual(result, expected);
    }

    @Test
    public void testSimpleOscillator() {
        Grid<ConwaysCell> grid = horizontalOscillator();
        Grid<ConwaysCell> result = transform(grid);
        Grid<ConwaysCell> expected = verticalOscillator();

        testEqual(result, expected);
    }

    private Grid<ConwaysCell> emptyGrid() {
        return new EndlessGrid<>(5, 5, cellFactory);
    }

    private Grid<ConwaysCell> transform(Grid<ConwaysCell> grid) {
        Grid<ConwaysCell> result = emptyGrid();
        transformer.transform(grid, result, rule);

        return result;
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
