package hu.supercluster.gameoflife.game.transformer;

import org.junit.Before;

import hu.supercluster.gameoflife.game.cell.SimpleCellFactory;

public class ThreadedTransformerTest extends ConwaysRuleTest {
    @Before
    public void setup() {
        cellFactory = new SimpleCellFactory();
        transformer = new ThreadedGridTransformer<>();
        rule = new ConwaysRule();
    }
}
