package hu.supercluster.gameoflife.game.cell;

import org.junit.Before;
import org.junit.Test;

import hu.supercluster.gameoflife.test.support.UnitTestSpecification;

import static org.fest.assertions.api.Assertions.assertThat;

public class ConwaysCellFactoryTest extends UnitTestSpecification {
    ConwaysCellFactory factory;

    @Before
    public void setup() {
        factory = new ConwaysCellFactory();
    }

    @Test
    public void testCreate() {
        ConwaysCell cell = factory.create();
        assertThat(cell.isDead()).isTrue();
    }
}
